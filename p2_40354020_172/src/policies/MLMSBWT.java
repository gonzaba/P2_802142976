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

public class MLMSBWT {
	
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
	public MLMSBWT(ArrayQueue<Customer>list, int i) {
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
//	System.out.println("Time= " +time );
		
		}//end of while
		
		timeAllServicesCompleted = time-1;
		finalResult.setTimeServicesCompleted(timeAllServicesCompleted);
		calculateAverageTime(terminatedJobs,finalResult);			
		
		return finalResult;
		
		
		
	}//end of result

	///--------------------------  METHODS --------------------------  \\\
	
	
	
	
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
		
		if(list.size()==1) {
			list.get(0).getPersonalWaitingLine().add(q);
		}
		else if(allListToProcessAreEmpty(list)){
			
			int minNumberOfPersonsWaiting = 0;
			
			if(!list.get(0).isAvailable()) {
				minNumberOfPersonsWaiting = list.get(0).getCustomer().getRemainingTime() ;
			}
			
			int ind = 0;
			
			for(int i=1;i<list.size();i++) {
				
				int comparator = 0;
					if(!list.get(i).isAvailable()) {
						comparator = list.get(i).getCustomer().getRemainingTime();
					}
				if(comparator< minNumberOfPersonsWaiting) {
					minNumberOfPersonsWaiting = comparator;
					ind = i;
				}
			}
			System.out.println("Entering to SPList#" + ind + " " + q);
			list.get(ind).getPersonalWaitingLine().add(q);
			
			
		}
		else {			
			int index=0;
			int min = 0;
			if(!list.get(0).getPersonalWaitingLine().isEmpty()) {
				if(!list.get(0).isAvailable()) {
					min = list.get(0).getCustomer().getRemainingTime();
				}
				for(int w =0; w<list.get(0).getPersonalWaitingLine().size();w++) {
					min = min +list.get(0).getPersonalWaitingLine().get(w).getRemainingTime();
				}
			}
			
			int compare = 0;
	//		System.out.println(list.size());
			for(int e = 1; e<list.size(); e++) {
				
				if(!list.get(e).getPersonalWaitingLine().isEmpty()) {
					if(!list.get(e).isAvailable()) {
						compare = list.get(e).getCustomer().getRemainingTime();
					}
					for(int w =0; w<list.get(e).getPersonalWaitingLine().size();w++) {
						compare = compare +list.get(e).getPersonalWaitingLine().get(w).getRemainingTime();
					}
				}
				
				if(compare<min) {
					min = compare;
					index = e;
				}
			}
			
			//		System.out.println("Entering to SPList#" + index + " " + q);
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
				
		//	System.out.println("Entro a SP #" + i +"=" + listOfServicePost.get(i).getCustomer());
						
					
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
				break;
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