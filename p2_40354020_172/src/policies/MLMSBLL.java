package policies;

import java.util.ArrayList;

import classes.ArrayQueue;
import objects.Customer;
import objects.Result;
import objects.ServicePost;

/**
 * @author Barbara P. Gonzalez Rivera - 802-14-2976
 * @author Ramineh Lopez - 402-12-3657
 * ICOM4035 - 030
 */

//Multiple Lines Multiple Servers

public class MLMSBLL {
	
	ArrayQueue<Customer> listToCust;
	
	int numberOfServicePosts = 0;
	
	int timeAllServicesCompleted = 0;
	
	int averageWaitingTime = 0;
	
	//list of service post available for that particular set up of 1, 3 or 5 service posts.
	ArrayList<ServicePost> listOfServicePost = new ArrayList<>();
	
	//Queue that will hold the list of customers that were served
	ArrayQueue<Customer> terminatedJobs = new ArrayQueue<>();
	
	//sets finalResult originally all to 0 to create a new object of that type. 
	//after the program finished running, the values of final Result will change.
	Result finalResult = new Result(0,0,0);
	
	/**
	 * 
	 * @param list of customers 
	 * @param i is the number of service post for htat particular set up
	 * (if its one server, 3 servers or 5 servers.
	 */
	public MLMSBLL(ArrayQueue<Customer> list, int i) {
		this.listToCust = list;
		this.numberOfServicePosts = i;
	}

	public Result result() {
		
				//Creates and Initialize the amount of ServicePost wanted
				//Adds the service post to my master list of service posts.
				if(numberOfServicePosts==1) {
					ServicePost servicePost1 = new ServicePost(); //aka Cashier #1
					listOfServicePost.add(servicePost1); //Add cashier to the list of cashiers/servers
					ArrayQueue<Customer> listToProcess1 = new ArrayQueue<>();
					servicePost1.setPersonalWaitingLine(listToProcess1);
				}
				if(numberOfServicePosts==3) {
					ServicePost servicePost1 = new ServicePost();
					listOfServicePost.add(servicePost1);
					ArrayQueue<Customer> listToProcess1 = new ArrayQueue<>();
					servicePost1.setPersonalWaitingLine(listToProcess1);
					
					ServicePost servicePost2 = new ServicePost(); 
					listOfServicePost.add(servicePost2); 
					ArrayQueue<Customer> listToProcess2 = new ArrayQueue<>();
					servicePost2.setPersonalWaitingLine(listToProcess2);
					
					ServicePost servicePost3 = new ServicePost(); 
					listOfServicePost.add(servicePost3);
					ArrayQueue<Customer> listToProcess3 = new ArrayQueue<>();
					servicePost3.setPersonalWaitingLine(listToProcess3);
				}
				if(numberOfServicePosts==5) {
					ServicePost servicePost1 = new ServicePost();
					listOfServicePost.add(servicePost1);
					ArrayQueue<Customer> listToProcess1 = new ArrayQueue<>();
					servicePost1.setPersonalWaitingLine(listToProcess1);
					
					ServicePost servicePost2 = new ServicePost(); 
					listOfServicePost.add(servicePost2); 
					ArrayQueue<Customer> listToProcess2 = new ArrayQueue<>();
					servicePost2.setPersonalWaitingLine(listToProcess2);
					
					ServicePost servicePost3 = new ServicePost(); 
					listOfServicePost.add(servicePost3);
					ArrayQueue<Customer> listToProcess3 = new ArrayQueue<>();
					servicePost3.setPersonalWaitingLine(listToProcess3);
					
					ServicePost servicePost4 = new ServicePost();
					listOfServicePost.add(servicePost4);
					ArrayQueue<Customer> listToProcess4 = new ArrayQueue<>();
					servicePost4.setPersonalWaitingLine(listToProcess4);
					
					ServicePost servicePost5 = new ServicePost(); 
					listOfServicePost.add(servicePost5); 
					ArrayQueue<Customer> listToProcess5 = new ArrayQueue<>();
					servicePost5.setPersonalWaitingLine(listToProcess5);
					
				}
				
				
				//set time
				int time = listToCust.first().getArrivalTime();	
				
				
				while(!(listToCust.isEmpty()) || !allAvailable(listOfServicePost) || !allListToProcessAreEmpty(listOfServicePost) ) {
								
					decreaseTime(listOfServicePost);
					
					
					/**
					 * ---Service-Completed Event---
					 * Verifies if the remaining time of the customer is equal to zero in
					 * each service post 
					 */
				
					for(int i=0; i<listOfServicePost.size(); i++) {
						//Verifies first if the service post is occupied with someone
						//it its available then it won't enter de condition about
						//removing someone because it doesn't have a customer.
						
						if(!listOfServicePost.get(i).isAvailable()) {
							//Looks for service time and checks if its equal to 0
							//if its equal to 0 then it is removed from the service post.
							if(listOfServicePost.get(i).getCustomer().getRemainingTime()==0) {
								
								Customer p = listOfServicePost.get(i).removeCustomer();
								//sets the depature time to the current time in the system.
								
								p.setDepartureTime(time);
								//System.out.println("Depature" + p);
								//places the customers on the list of already serviced customers.
								terminatedJobs.enqueue(p);
						}
					}
					}	//end of for
					
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
							//System.out.println("Entrando a ListToProcess= " + c);		
							nextAvailable(listOfServicePost, c);
					}
						
					serviceStarts(time);
					time++;
					System.out.println("Time = " +time);
						
						
				}//end of while
				
				timeAllServicesCompleted = time;
				
				finalResult.setTimeServicesCompleted(timeAllServicesCompleted);
				
				calculateAverageTime(terminatedJobs,finalResult);
							
		return finalResult;
		
		
	}//end of result

	/**
	 * 
	 * @param lista is the list of service post to check the status of each service post 
	 * and see if they are all available.
	 * @return a boolean statement that will indicate if its true that
	 * all service post are available.
	 */
	private boolean allAvailable(ArrayList<ServicePost> lista) {
		
		boolean areAvailable = true;
		
		for(int i=0; i<lista.size();i++) {
			
		if(!lista.get(i).isAvailable()) {
			areAvailable = false;
		}
		
		}
		return areAvailable;
	}
	
	
	/**
	 * 	Service Event. when one server is available, it will assign a new customer to the service post
	 * to begin the service of it.
	 * 
	 * It also sets the waiting time the customer had to wait to receive its service.
	 * @param time is the current time in the system.
	 */
	public void serviceStarts(int time) {
		
		for(int i=0; i<listOfServicePost.size();i++) {
			if(listOfServicePost.get(i).isAvailable() && !(listOfServicePost.get(i).getPersonalWaitingLine().size()==0)
					&& !(allListToProcessAreEmpty(listOfServicePost))) {
				listOfServicePost.get(i).setCustomer(listOfServicePost.get(i).getPersonalWaitingLine().dequeue());
				listOfServicePost.get(i).getCustomer().setWaitingTime(time-listOfServicePost.get(i).getCustomer().getArrivalTime());
			//	System.out.println("Entro a SP #" + i +"=" + listOfServicePost.get(i).getCustomer());
			}
		}
	}
	

	/**
	 * 
	 * @param lista of servicePost
	 * This method decreases the remaining time of each customer being served
	 */
	public void decreaseTime(ArrayList<ServicePost> lista) {
		//System.out.println("Size de lista=" +lista.size());
			for(int i =0; i <lista.size(); i++) {
				//System.out.println("i= "+ i);
				//Solo le hace decrease si el ServicePost tiene una persona atendiendolo
				if(!(lista.get(i).isAvailable())) {
					
				lista.get(i).getCustomer().decreaseRemainingTime();
				
			//	System.out.println("Decreasing" + listOfServicePost.get(i).getCustomer());
				}
			}
	}//end of decreseTime
	
	/**
	 * Calculates the average time of waiting of each customer.
	 * 
	 * @param terminatedJobs is the list of all customers after they have been 
	 * served.
	 * @param r is the object that will hold all the results to be written on the output
	 * file.
	 */
	public void calculateAverageTime(ArrayQueue<Customer> terminatedJobs, Result r) {
		
			//computing time
			float totalTime = 0;
			float valor2 = 0;	
			int count = terminatedJobs.size();
		
			//System.out.println("list Size: " +count);
			
			while(!(terminatedJobs.isEmpty())) {
				
				valor2 = terminatedJobs.first().getWaitingTIme();
				//System.out.println(valor2);
				totalTime= valor2 + totalTime;
				
				//System.out.println("totalTime = "  +totalTime);
				terminatedJobs.dequeue();
			}
			
			//System.out.println(totalTime/5);
			totalTime= totalTime/ (float)count;
			
			r.setAverageWaitingTime(totalTime);
			
			//System.out.print("Averange Time in System is: ");
			//System.out.print("Total time"+ totalTime);
			
	}//end of calculateAverageTime
	
	
	/**
	 * 
	 * @param lista is the list of Service Post to check each individual line
	 * each one has
	 * @return whenever all lines of all the service post are empty or not.
	 */
	public boolean allListToProcessAreEmpty(ArrayList<ServicePost> lista) {
		
		boolean isTrue = true;
		
		for(int i=0;i<lista.size();i++) {
		//	System.out.println("Size lista"+i+ "="+ lista.get(i).getPersonalWaitingLine().size());
			if(!(lista.get(i).getPersonalWaitingLine().isEmpty())) {
				isTrue = false;
			}
		}
		return isTrue;
	}
	
	
	/**
	 * 
	 * Verifies which of the service post has the line with most minimum people in it.
	 * @param lista is the list of Service Post
	 * @param c is the customer to place on the list with less people on it.
	 */
	public void nextAvailable(ArrayList<ServicePost> lista, Customer c) {
		
		//System.out.println(lista.size());
		int minNumberOfPersonsWaiting;
		
		if(lista.size()==1) {
			//System.out.println("Entrando a SP#0" + c);
			lista.get(0).getPersonalWaitingLine().enqueue(c);
		}
		else {
			
			
			minNumberOfPersonsWaiting = lista.get(0).getPersonalWaitingLine().size();
			int index = 0;
			
			
			for(int i=1;i<lista.size();i++) {
				
				//System.out.println(	minNumberOfPersonsWaiting + "vs " + lista.get(i).getPersonalWaitingLine().size());
				if(lista.get(i).getPersonalWaitingLine().size() < minNumberOfPersonsWaiting) {
					minNumberOfPersonsWaiting = lista.get(i).getPersonalWaitingLine().size();
					index = i;
				}
		
			}	
				//System.out.println("Entrando a SPList#" + index + " " + c);
			lista.get(index).getPersonalWaitingLine().enqueue(c);	
			
		}
		
	}//end of next available
	
	
	public void transfer(ArrayList<ServicePost> lista) {
		
		
		//System.out.println(lista.size());
		int minNumberOfPersonsWaiting, maxNumberOfPersonsWaiting;
		
		if(lista.size()==1) {
			//System.out.println("Entrando a SP#0" + c);
		}
		else {
			
			minNumberOfPersonsWaiting = lista.get(0).getPersonalWaitingLine().size();
			maxNumberOfPersonsWaiting = lista.get(0).getPersonalWaitingLine().size();
			
			int index, index2;
			
			for(int i=1;i<lista.size();i++) {
				
				//System.out.println(	minNumberOfPersonsWaiting + "vs " + lista.get(i).getPersonalWaitingLine().size());
				if(lista.get(i).getPersonalWaitingLine().size()>= maxNumberOfPersonsWaiting) {
					
					if(lista.get(i).getPersonalWaitingLine().size()== maxNumberOfPersonsWaiting) {
						lista.get(i).getPersonalWaitingLine();
					}
					else {
						maxNumberOfPersonsWaiting = lista.get(i).getPersonalWaitingLine().size();
						index2 = i;
					}
					
				}
				
				if(lista.get(i).getPersonalWaitingLine().size() < minNumberOfPersonsWaiting) {
					minNumberOfPersonsWaiting = lista.get(i).getPersonalWaitingLine().size();
					index = i;
				}
				
				
		
			}	
				//System.out.println("Entrando a SPList#" + index + " " + c);
			//lista.get(index).getPersonalWaitingLine().enqueue(c);	
			
		}
		
		
		
		
	}
	
	public void mCounter(ArrayList<Customer> lista) {
		
	}
		



	
	
	
	




}
