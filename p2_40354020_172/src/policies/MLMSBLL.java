package policies;

import java.util.ArrayList;

import classes.ArrayQueue;
import objects.Customer;
import objects.Result;
import objects.ServicePost2;

/**
 * @author Barbara P. Gonzalez Rivera - 802-14-2976
 * ICOM4035 - 030
 */


//Multiple Lines Multiple Servers

public class MLMSBLL {
	
	//list of customers for that file 
	ArrayQueue<Customer> listToCust;
	
	//to me set in the constructor down below.
	int numberOfServicePosts =0;
	
	//time when the last customer has been served completely
	int timeAllServicesCompleted = 0;
	
	//Average waiting time for all customers.
	int averageWaitingTime =0;
	
	//list of service posts available for that policy. To be set later on.
	ArrayList<ServicePost2> listOfServicePost = new ArrayList<>();
	
	//ArrayQueue that will hold the list of all jobs completed
	ArrayQueue<Customer> terminatedJobs = new ArrayQueue<>();
	
	//Object that will hold the information for the policy and return it
	//to the main to print it on the file.
	
	Result finalResult = new Result(0,0,0);
	
	
	/**
	 * 
	 * @param list of customers of that file 
	 * @param i the number of service posts available for that approach
	 */
	public MLMSBLL(ArrayQueue<Customer>list, int i) {
		this.listToCust = list;
		this.numberOfServicePosts = i;
	}
	
	
	public Result result() {
		
		
		//Creates and Initialize the amount of ServicePost wanted
		//Adds the service post to my master list of service posts.
		if(numberOfServicePosts==1) {
			ServicePost2 servicePost1 = new ServicePost2(); //aka cajero #1
			listOfServicePost.add(servicePost1); //Anado cajero #1 a la lista de cajeros
			ArrayList<Customer> listToProcess1 = new ArrayList<>();
			servicePost1.setPersonalWaitingLine(listToProcess1);
		}
		if(numberOfServicePosts==3) {
			
			ServicePost2 servicePost1 = new ServicePost2(); 
			listOfServicePost.add(servicePost1); 
			ArrayList<Customer> listToProcess1 = new ArrayList<>();
			servicePost1.setPersonalWaitingLine(listToProcess1);
			
			ServicePost2 servicePost2 = new ServicePost2(); 
			listOfServicePost.add(servicePost2); 
			ArrayList<Customer> listToProcess2 = new ArrayList<>();
			servicePost2.setPersonalWaitingLine(listToProcess2);
			
			ServicePost2 servicePost3 = new ServicePost2(); 
			listOfServicePost.add(servicePost3); 
			ArrayList<Customer> listToProcess3 = new ArrayList<>();
			servicePost3.setPersonalWaitingLine(listToProcess3);
		
		}
		if(numberOfServicePosts==5) {
		
			ServicePost2 servicePost1 = new ServicePost2(); 
			listOfServicePost.add(servicePost1); 
			ArrayList<Customer> listToProcess1 = new ArrayList<>();
			servicePost1.setPersonalWaitingLine(listToProcess1);
			
			ServicePost2 servicePost2 = new ServicePost2(); 
			listOfServicePost.add(servicePost2); 
			ArrayList<Customer> listToProcess2 = new ArrayList<>();
			servicePost2.setPersonalWaitingLine(listToProcess2);
			
			ServicePost2 servicePost3 = new ServicePost2(); 
			listOfServicePost.add(servicePost3); 
			ArrayList<Customer> listToProcess3 = new ArrayList<>();
			servicePost3.setPersonalWaitingLine(listToProcess3);
			
			ServicePost2 servicePost4 = new ServicePost2(); 
			listOfServicePost.add(servicePost4); 
			ArrayList<Customer> listToProcess4 = new ArrayList<>();
			servicePost4.setPersonalWaitingLine(listToProcess4);
			
			ServicePost2 servicePost5 = new ServicePost2(); 
			listOfServicePost.add(servicePost5); 
			ArrayList<Customer> listToProcess5 = new ArrayList<>();
			servicePost5.setPersonalWaitingLine(listToProcess5);
		
		}//end of setting up the number of service posts and their personal waiting lists
		
		
	
		//setting up the time where the program will start
		int time = listToCust.first().getArrivalTime();
		
		
		//While the list of customers is not empty, or all service posts are not available, meaning that
		//there is a services post that is taking care of a customer.
		//and all list to process from each individual line of each service post is not empty
		//continue this cycle.
		while(!(listToCust.isEmpty()) || !allAvailable(listOfServicePost) || !allListToProcessAreEmpty(listOfServicePost) ) {
			
			//Decrease the remaining time of each person being attended in
			//each service post.
			decreaseTime(listOfServicePost);
			

			/**
			 * ---Service-Completed Event---
			 * Verifies if the remaining time of the customer is equal to zero in
			 * each service post 
			 **/
			
			for(int i=0; i<listOfServicePost.size(); i++) {
				
				//Verifies first if the service post is occupied with someone
				//it its available then it won't enter the condition about
				//removing someone because it doesn't have a customer.
				if(!listOfServicePost.get(i).isAvailable()) {
					
					//Looks for service time and checks if its equal to 0
					//if its equal to 0 then it is removed from the service post
					if(listOfServicePost.get(i).getCustomer().getRemainingTime()==0) {
						
						Customer p = listOfServicePost.get(i).removeCustomer();
						//sets the departure time to the current time in the system.
						
						p.setDepartureTime(time);
					//	System.out.println("Departure" + p);
						
						//places the customers on the list of already serviced customers.
						terminatedJobs.enqueue(p);
					}
				}//end of if
			}	//end of for and end of Service Completed Event
			
			
			/**
			 * Transfer Event
			 */
			
			if(!(listOfServicePost.size()==1) && !allListToProcessAreEmpty(listOfServicePost) ) {
				transfer(listOfServicePost);
			}
			
			
			/**
			 * Service-Starts Event
			 */
			
				serviceStarts(time);
				
			/**
			 * Arrival Event
			 * Checks when people arrive. If their arrival time is equal to the time currently in the system
			 * they are added to the line of customers waiting to be served.
			 */		
			while(!listToCust.isEmpty() && listToCust.first().getArrivalTime()==time) {	
					Customer c = listToCust.dequeue();
					
					//System.out.println("Entering ListToProcess= " + c);
					
					nextAvailable(listOfServicePost, c);
					
			}//end of arrival event	
			
			serviceStarts(time);
			
			time++;
	System.out.println("Time= " +time );
		
		}//end of while
		
		timeAllServicesCompleted = time-1;
		finalResult.setTimeServicesCompleted(timeAllServicesCompleted);
		calculateAverageTime(terminatedJobs,finalResult);			
		
		return finalResult;
		
		
		
	}//end of result

	///--------------------------  METHODS --------------------------  \\\
	
	
	/**
	 * 
	 * @param list of servicePosts
	 *This method takes care of transferring a person to a new line
	 */
	private void transfer(ArrayList<ServicePost2> list) {
		
		//the one with the priority to change lines
		//is the one with the lower ID number
		int lowestIDNumber = 0;

		int compare =0;
		
		//which service Post is located that customer with the lowest ID
		int index = 0;
		
		int j =0;
		
		//looks for the first list that is not empty to compare
		while(j<list.size()) {
			if(list.get(j).getPersonalWaitingLine().size()==0) {
			System.out.println("List" + j + "size="+ list.get(j).getPersonalWaitingLine().size());	
				j++;
			}
			else {
				//if it found the first list that is not empty it will take that J pass it to the
				//next step
				System.out.println("List" + j + "size="+ list.get(j).getPersonalWaitingLine().size());	
				index= j;
				break;
			}
		}
		System.out.println("j=" + j);
		if(j==list.size()) {
			//if J is equal to the size of service post list it means that
			//it went through every service post and all are empty
			//therefore none will be transfered.
		}
		else {
			
			lowestIDNumber = list.get(j).getPersonalWaitingLine().get(list.get(j).getPersonalWaitingLine().size()-1).getId();
				for(int x= j+1; x<list.size();x++) {
					if(list.get(x).getPersonalWaitingLine().size()==0) {
						continue;
					}
					else {
						compare = list.get(x).getPersonalWaitingLine().get( list.get(x).getPersonalWaitingLine().size()-1).getId();
						
						if(compare < lowestIDNumber) {
							lowestIDNumber = compare;
							index = x;	
						}
					}	
				}
			
				Customer clientToChange = new Customer();
				
					System.out.println(list.get(index).getPersonalWaitingLine().size());
					
					clientToChange = list.get(index).getPersonalWaitingLine().remove(list.get(index).getPersonalWaitingLine().size()-1);
					
					if(index==0) {
						System.out.println("here");
						nextAvailable(list,clientToChange);
					}
					else {
						System.out.println("Not here");
						nextAvailable2(list,clientToChange, index);
					}
		}//end of else
		
	
	}//end of transfer
	
	
	private void nextAvailable2(ArrayList<ServicePost2> list, Customer c, int index) {
	
	int comp1;
	int min1;
	int index1 = 0;
	int comp2;
	int min2;
	int index2 = index;
	
		if(list.size()==1) {
			list.get(0).getPersonalWaitingLine().add(c);
		}
		
		//if 1,2,3,4,5
	
		min1 = list.get(0).getPersonalWaitingLine().size();
		
		if(!list.get(0).isAvailable()) {
			min1= min1 +1;
		}
		
		for(int i=1;i<index;i++) {
			comp1 = list.get(i).getPersonalWaitingLine().size();
			if(!list.get(i).isAvailable()) {
				comp1= comp1 +1;
			}
			if(comp1<min1) {
				min1 = comp1;
				index1 =i;
			}	
		}
				
		min2 = list.get(index).getPersonalWaitingLine().size();
		
		if(!list.get(index).isAvailable()) {
			min2= min2 +1;
		}
		
		
		if(index==list.size()-1) {
			//do nothing because you are at the end of the list
		}
		
		else {
			for(int l=index+1;l<list.size();l++) {
				comp2 = list.get(l).getPersonalWaitingLine().size();
				if(!list.get(l).isAvailable()) {
					comp2 = comp2 +1;
				}
				if(comp2<min2) {
					min2 = comp2;
					index2 = l;
				}
			}
			
		}
		
		if(min1 <min2) {
			System.out.println("1Changin to SPList#" + index + " " + c);
			list.get(index1).getPersonalWaitingLine().add(c);
		}
		if(min2==min1) {
			System.out.println("2Changin to SPList#" + index + " " + c);
			list.get(index2).getPersonalWaitingLine().add(c);
		}
		else {
			System.out.println("3Changin to SPList#" + index + " " + c);
			list.get(index2).getPersonalWaitingLine().add(c);
		}
			
		
	}//nextAvailable2


	/**
	 * 
	 * @param t queue of terminated jobs
	 * @param r object to hold the final results
	 * 
	 * This method calculates the average time of waiting and the value of M
	 */
	private void calculateAverageTime(ArrayQueue<Customer> t, Result r) {
		
		//computing time
		float totalTime = 0;
		float valor2 = 0;	
		float m1 =0;
		float totalTimeM =0;
		
		int count = t.size();
		
		while(!t.isEmpty()) {
			
			m1 = t.first().getM();
			valor2 = t.first().getWaitingTIme();
			//System.out.println(valor2);
			totalTimeM = m1 + totalTimeM;
			totalTime= valor2 + totalTime;
			
			//System.out.println("totalTime = "  +totalTime);
			t.dequeue();
		}//end of while
		
		totalTime= totalTime/ (float)count;
		
		totalTimeM = totalTimeM/ (float) count;
		
		r.setAverageNumOfCust(totalTimeM);
		
		r.setAverageWaitingTime(totalTime);
		
	}//end of calculateAverageTime


	private void nextAvailable(ArrayList<ServicePost2> list, Customer q) {
		
		int minNumberOfPersonsWaiting;
		
		if(list.size()==1) {
			list.get(0).getPersonalWaitingLine().add(q);
		}
		else {
			minNumberOfPersonsWaiting = list.get(0).getPersonalWaitingLine().size();
			
			if(!list.get(0).isAvailable()) {
				minNumberOfPersonsWaiting = minNumberOfPersonsWaiting +1;
			}
			
			int index = 0;
			
			for(int i=1;i<list.size();i++) {
				
		
				int comparator = list.get(i).getPersonalWaitingLine().size();
				//System.out.println("comparator="  +comparator + " " + "Min=" + minNumberOfPersonsWaiting);
					if(!list.get(i).isAvailable()) {
						comparator = comparator +1;
					}
				//	System.out.println("comparator="  +comparator + " " + "Min=" + minNumberOfPersonsWaiting);
					
				if(comparator< minNumberOfPersonsWaiting) {
					minNumberOfPersonsWaiting = comparator;
					index = i;
				}
			}
			System.out.println("Entering to SPList#" + index + " " + q);
			list.get(index).getPersonalWaitingLine().add(q);
			
		}//end of else
		
		
	}//end of nextAvailable

	
	
	/**
	 * 
	 * @param time the customer began its service
	 * 
	 * This method is used to begin serving one customer
	 */
	private void serviceStarts(int time) {
		
		for(int i=0; i<listOfServicePost.size();i++) {
			
			if(listOfServicePost.get(i).isAvailable() && !(listOfServicePost.get(i).getPersonalWaitingLine().size()==0) && !(allListToProcessAreEmpty(listOfServicePost))) {
				
				Customer h = new Customer();
				
				//remove the first customer of the waiting list of that particular server
				h = listOfServicePost.get(i).getPersonalWaitingLine().remove(0);
				
				checkM(h,listOfServicePost);
				
				listOfServicePost.get(i).setCustomer(h);
				listOfServicePost.get(i).getCustomer().setWaitingTime(time-listOfServicePost.get(i).getCustomer().getArrivalTime());
				
					System.out.println("Entro a SP #" + i +"=" + listOfServicePost.get(i).getCustomer());
						
					
			}
			
		}//end of for
		
	}//end of serviceStarts

	
	
	/**
	 * 
	 * @param h customer that started receiving his service
	 * @param list of service post
	 * 
	 * This method checks everyone waiting in their lines and if 
	 * the customer that just started receiving service 
	 * has a higher ID number than the ones waiting.
	 * Then add one to the customer waiting that wasn't served before
	 * the h one.
	 */
	
	private void checkM(Customer customer , ArrayList<ServicePost2> list) {
		int id = customer.getId();
		
		for(int i=0; i<list.size();i++) {
			
			if(list.get(i).getPersonalWaitingLine().size()==0) {
				//Do nothing because its empty
			}
			else {
				
				for(int j=0; j<list.get(i).getPersonalWaitingLine().size();j++) {
					if(id>list.get(i).getPersonalWaitingLine().get(j).getId()) { 
						list.get(i).getPersonalWaitingLine().get(j).increaseM();
					}//end of if
				}//end of for
				
			}//end of else
		}//end of for
		
	}//end of checkM


	/**
	 * 
	 * @param list of servicePost
	 * This method decreases the remaining time of each customer being served
	 */
	private void decreaseTime(ArrayList<ServicePost2> list) {
		for(int i =0; i <list.size(); i++) {
			
			//Will only execute this method is the service post is serving someone.
			if(!(list.get(i).isAvailable())) {
				list.get(i).getCustomer().decreaseRemainingTime();
				System.out.println("Decreasing" + listOfServicePost.get(i).getCustomer());
			}
			
		}
	
		
	}//end of decreaseTime


	/**
	 * 
	 * @param list of service post to check
	 * @return is all the servers lines are empty
	 */
	private boolean allListToProcessAreEmpty(ArrayList<ServicePost2> list) {
		
		boolean allAreEmpty = true;
		
		for(int i=0; i<list.size();i++) {
			if(!(list.get(i).getPersonalWaitingLine().isEmpty())) {
				allAreEmpty = false;
			}
		}
		
		return allAreEmpty;
		
	}//end of allListToProcessAreEmpty

	
	

	/**
	 * 
	 * @param list of service post
	 * @return if all services post are available or not.
	 */
	private boolean allAvailable(ArrayList<ServicePost2> list) {
		
		boolean areAvailable = true;
		
		//iterates over the list of service posts
		for(int i=0; i<list.size(); i++) {
			
			//if there is one service post that is not available then
			//change the boolean to false 
			
			if(!list.get(i).isAvailable()) {
				areAvailable = false;
			}
			
		}
		return areAvailable;

	}//end of allAvailable;
	
	
	
	
	
	
	
}//end of MLMSBLL