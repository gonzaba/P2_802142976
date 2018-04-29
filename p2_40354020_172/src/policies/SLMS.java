package policies;



import java.util.ArrayList;

import classes.ArrayQueue;
import objects.Customer;
import objects.Result;
import objects.ServicePost;


/**
 * 
 * @author Barbara P. Gonzalez Rivera - 802-14-2976
 * @author Ramineh Lopez - 402-12-3657
 * ICOM4035 - 030
 * In this policy, there is only one waiting line and many service posts.  When available, each post accepts a new customer. 
 * In case that there are more than one service post 
 */

//Single Line Multiple Servers

public class SLMS {
	
	ArrayQueue<Customer> listToCust;
	ArrayQueue<Customer> listToProcess = new ArrayQueue<>();
	ArrayList<ServicePost> listOfServicePost = new ArrayList<>();
	ArrayQueue<Customer> terminatedJobs = new ArrayQueue<>();
	
	int timeAllServicesCompleted = 0;
	
	int averageWaitingTime = 0;
	
	int numberOfServicePosts = 0;
	
	Result finalResult = new Result(0,0,0);
	
	
	 public SLMS(ArrayQueue<Customer> list, int i) {
		this.listToCust = list;
		this.numberOfServicePosts = i;
	 }

	 
	public Result result() {
		
		//Creates and Initialize the amount of ServicePost wanted
		//Adds the service post to my master list of service posts.
		if(numberOfServicePosts==1) {
			ServicePost servicePost1 = new ServicePost(); //aka cajero #1
			listOfServicePost.add(servicePost1); //Anado cajero #1 a la lista de cajeros
		}
		if(numberOfServicePosts==3) {
			ServicePost servicePost1 = new ServicePost();
			listOfServicePost.add(servicePost1);
			
			ServicePost servicePost2 = new ServicePost(); 
			listOfServicePost.add(servicePost2); 
			
			ServicePost servicePost3 = new ServicePost(); 
			listOfServicePost.add(servicePost3);
		}
		if(numberOfServicePosts==5) {
			ServicePost servicePost1 = new ServicePost(); 
			listOfServicePost.add(servicePost1); 
			ServicePost servicePost2 = new ServicePost(); 
			listOfServicePost.add(servicePost2); 
			ServicePost servicePost3 = new ServicePost(); 
			listOfServicePost.add(servicePost3); 
			ServicePost servicePost4 = new ServicePost(); 
			listOfServicePost.add(servicePost4); 
			ServicePost servicePost5 = new ServicePost();
			listOfServicePost.add(servicePost5);
		}
		
		
		
		//set time
		int time = listToCust.first().getArrivalTime();
		
		//System.out.println(listToCust.first().getArrivalTime());

		
		while(!listToCust.isEmpty() || allSPBusy(listOfServicePost) || !allAvailable(listOfServicePost)) {
						
			//System.out.println("List no esta vacia:" + !listToCust.isEmpty());
			//System.out.println("Todos SP are busy: " + allSPBusy(listOfServicePost));
			//System.out.println("Todos estan available" + allAvailable(listOfServicePost));
			
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
					//	System.out.println("Depature:" + p);
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
				listToProcess.enqueue(c);
				
				}
			
		serviceStarts(time);
			
			
			time++;	
	//System.out.println("Time = " + time);
			//System.out.println("List no esta vacia:" + !listToCust.isEmpty());
			//System.out.println("Todos SP are busy: " + allSPBusy(listOfServicePost));
			//System.out.println("Todos estan available" + allAvailable(listOfServicePost));
			
			
			
			
		}// end of while
		
		
				
		timeAllServicesCompleted = time;
		
		finalResult.setTimeServicesCompleted(timeAllServicesCompleted-1);
		
		
		
		calculateAverageTime(terminatedJobs,finalResult);
		
		//System.out.println(finalResult);
		
		return finalResult;
		
	}//end of result
	
	/**
	 * 
	 * @param lista is the list of service post available on this simulation
	 * @return if all service posts are busy or not
	 */
	public boolean allSPBusy(ArrayList<ServicePost> lista) {
		
		boolean areBusy = true;
		
		for(int i=0; i<lista.size();i++) {
			
		if(lista.get(i).isAvailable()) {
			areBusy = false;
		}
		
		}
		return areBusy;
	}
	
	public boolean allAvailable(ArrayList<ServicePost> lista) {
		
		boolean areAvailable = true;
		
		for(int i=0; i<lista.size();i++) {
			
		if(!lista.get(i).isAvailable()) {
			areAvailable = false;
		}
		
		}
		return areAvailable;
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
				
			//System.out.println("Decreasing" + listOfServicePost.get(i).getCustomer());
				}
			}
		
		
	}
	
	public void calculateAverageTime(ArrayQueue<Customer> terminatedJobs, Result r) {
		
		//computing time
		float totalTime = 0;
		float valor2 = 0;
		
		
		int count = terminatedJobs.size();
		
	//System.out.println("list Size: " +count);
		
		while(!(terminatedJobs.isEmpty())) {
			
			valor2 = terminatedJobs.first().getWaitingTIme();
			
			totalTime= valor2 + totalTime;
			
			//System.out.println("totalTime = "  +totalTime);
			terminatedJobs.dequeue();
		}
		
		//System.out.println(totalTime/5);
		totalTime= totalTime/ (float)count;
		
		
		r.setAverageWaitingTime(totalTime);
		
		//System.out.print("Averange Time in System is: ");
		
		//System.out.print(totalTime);
	}
	
	

	public void serviceStarts(int time) {
		
		while(!listToProcess.isEmpty() && !(allSPBusy(listOfServicePost)==true)) {
			for(int i=0; i<listOfServicePost.size();i++) {
				if(listOfServicePost.get(i).isAvailable() && !(listToProcess.isEmpty())) {
					listOfServicePost.get(i).setCustomer(listToProcess.dequeue());
					listOfServicePost.get(i).getCustomer().setWaitingTime(time-listOfServicePost.get(i).getCustomer().getArrivalTime());
					//System.out.println(listOfServicePost.get(i).getCustomer());
				//System.out.println("Entro a SP #" + i +"=" + listOfServicePost.get(i).getCustomer());
				}
			}
		}
	}

	
	
	
			
			
			
	

	
	
	 
	

	
	
	

}
