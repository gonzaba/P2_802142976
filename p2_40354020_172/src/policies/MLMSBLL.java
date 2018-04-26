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
	
	ArrayList<ServicePost> listOfServicePost = new ArrayList<>();
	
	ArrayQueue<Customer> terminatedJobs = new ArrayQueue<>();
	
	Result finalResult = new Result(0,0,0);
	
	
	public MLMSBLL(ArrayQueue<Customer> list, int i) {
		this.listToCust = list;
		this.numberOfServicePosts = i;
	}

	public Result result() {
		
				//Creates and Initialize the amount of ServicePost wanted
				//Adds the service post to my master list of service posts.
				if(numberOfServicePosts==1) {
					ServicePost servicePost1 = new ServicePost(); //aka cajero #1
					listOfServicePost.add(servicePost1); //Anado cajero #1 a la lista de cajeros
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
								
				//System.out.println(allSPBusy(listOfServicePost));
				
				//System.out.println(allAvailable(listOfServicePost));
				
				
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
						System.out.println("Depature" + p);
							//places the customers on the list of already serviced customers.
						
							terminatedJobs.enqueue(p);
					}
				}
				}	//end of for
				
				/**
				 * Transfer Event
				 */
				
				if(!(listOfServicePost.size()==1)) {
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
						System.out.println("Entrando a ListToProcess= " + c);
						
						nextAvailable(listOfServicePost, c);
					}
					
					serviceStarts(time);
					time++;
				System.out.println("Time = " +time);
					
					
				}//end of while
				
				timeAllServicesCompleted = time;
				finalResult.setTimeServicesCompleted(timeAllServicesCompleted-1);
				calculateAverageTime(terminatedJobs,finalResult);
							
		
		
		return finalResult;
		
		
	}//end of result

	private boolean allAvailable(ArrayList<ServicePost> lista) {
		
		boolean areAvailable = true;
		
		for(int i=0; i<lista.size();i++) {
			
		if(!lista.get(i).isAvailable()) {
			areAvailable = false;
		}
		
		}
		return areAvailable;
	}

	public void serviceStarts(int time) {
		
	//	System.out.println("All are empty"+ allListToProcessAreEmpty(listOfServicePost));
		
	//	System.out.println("All are busy " + allSPBusy(listOfServicePost));
		
		for(int i=0; i<listOfServicePost.size();i++) {
			if(listOfServicePost.get(i).isAvailable() && !(listOfServicePost.get(i).getPersonalWaitingLine().size()==0)
					&& !(allListToProcessAreEmpty(listOfServicePost))) {
				listOfServicePost.get(i).setCustomer(listOfServicePost.get(i).getPersonalWaitingLine().dequeue());
				listOfServicePost.get(i).getCustomer().setWaitingTime(time-listOfServicePost.get(i).getCustomer().getArrivalTime());
		System.out.println("Entro a SP #" + i +"=" + listOfServicePost.get(i).getCustomer());
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
				
			System.out.println("Decreasing" + listOfServicePost.get(i).getCustomer());
				}
			}
	}//end of decreseTime
	
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
	
	public void nextAvailable(ArrayList<ServicePost> lista, Customer c) {
		
		int minNumberOfPersonsWaiting;
		
		if(lista.size()==1) {
			//System.out.println("Entrando a SP#0" + c);
			lista.get(0).getPersonalWaitingLine().enqueue(c);
		}
		else {
			
			minNumberOfPersonsWaiting = lista.get(0).getPersonalWaitingLine().size();
			
			if(!lista.get(0).isAvailable()) {
				minNumberOfPersonsWaiting = minNumberOfPersonsWaiting +1;
			}
			
			int index = 0;
			for(int i=1;i<lista.size();i++) {
				//System.out.println(	minNumberOfPersonsWaiting + "vs " + lista.get(i).getPersonalWaitingLine().size());
				int comparadar = lista.get(i).getPersonalWaitingLine().size();
				if(!lista.get(i).isAvailable()) {
					comparadar = comparadar +1;
				}
				if(comparadar < minNumberOfPersonsWaiting) {
					minNumberOfPersonsWaiting = comparadar;
					index = i; 
				}
			}	
			System.out.println("Entrando a SPList#" + index + " " + c);
				lista.get(index).getPersonalWaitingLine().enqueue(c);	
		}//end of else
				
	}//end of next Available
	
		


public void transfer(ArrayList<ServicePost> lista) {

	//the one with the priority to change lines
	//is the one with the lower ID number
	int lowestIDNumber = 0;
	
	int compare =0;
	
	//which service Post is located that customer with the lowest ID
	int index = 0;
	
	
	int j= 0;
	
	//busca la primera lista no vacia para comparar
	while(j<lista.size()) {	
		if(lista.get(j).getPersonalWaitingLine().size()==0) {
			j++;
			//System.out.println(j);
		}
		else {
			//if it found the first list that is not empty it will take that J pass it to the
			//next step
			break;
		}
	}
	
	if(j==lista.size()) {
		//if J is equal to the size of service post list it means that
		//it went through every service post and all are empty
		//therefore none will be transfered.
		}//
	
	else {
		
		lowestIDNumber = lista.get(j).getPersonalWaitingLine().peakAtLast().getId();
		
		for(int i=j+1; i<lista.size();i++) {
		
		if(lista.get(i).getPersonalWaitingLine().size()==0) {
			break;
		}
			
		compare =	lista.get(i).getPersonalWaitingLine().peakAtLast().getId();
		
		//compares the last person of each line to check who has priority to change to a new list/
		if(compare<lowestIDNumber) {
			lowestIDNumber = lista.get(i).getPersonalWaitingLine().peakAtLast().getId();
			index = i;
		}
		
	}//end of for
	
		Customer clientToChange = new Customer();
		//extract the person to change from one lsit to another.
		clientToChange = lista.get(index).getPersonalWaitingLine().deque();
		
		//put the customer on the next available place that the line is short.
		
		// NOTE: this nextAvailable method must be modified in order to 
		//cumplir con los requerimientos del projecto que el profesor cambio ayer en la noche.
		
		nextAvailable(lista,clientToChange);
	}//end of else
	
}//end of transfer




	
	




}
