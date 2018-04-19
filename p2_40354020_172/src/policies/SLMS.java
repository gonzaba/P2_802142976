package policies;



import java.util.ArrayList;

import classes.ArrayQueue;
import objects.Customer;
import objects.ServicePost;


/**
 * 
 * @author B�rbara P. Gonz�lez Rivera - 802-14-2976
 * @author Ramineh Lopez - 402-12-3657
 * ICOM4035 - 030
 *
 */

//Single Line Multiple Servers

public class SLMS {
	
	ArrayQueue<Customer> listToCust;
	ArrayQueue<Customer> listToProcess;
	
	ArrayList<ServicePost> listOfServicePost = new ArrayList<>();
	
	ArrayQueue<Customer> terminatedJobs;
	
	 public SLMS(ArrayQueue<Customer> listToProcess) {
		this.listToCust = listToProcess;
	}

	 
	public void result() {
		
		//set time
		int time = 0;
		
		//Creates and Initialize the amount of ServicePost wanted
		ServicePost servicePost1 = new ServicePost();
		ServicePost servicePost2 = new ServicePost();
		
		//Adds the service post to my master list of service posts.
		listOfServicePost.add(servicePost1);
		listOfServicePost.add(servicePost2);
		
		
		while(!listToCust.isEmpty()||allSPBusy(listOfServicePost)) {
			
			//decreases the time of all customers 
			decreaseTime(listOfServicePost);
			
			//Verifico si se termino de service el customer en cada service Post
			//AKA Service-Completed Event
			
			for(int i=0; i<listOfServicePost.size(); i++) {
				if(listOfServicePost.get(i).getCustomer().getRemainingTime()==0) {
					
					Customer p = listOfServicePost.get(i).removeCustomer();
					p.setDepartureTime(time);
					terminatedJobs.enqueue(p);
				}
			}	
			
			//Service-Starts Event
			
		
			while(!listToProcess.isEmpty() && !allSPBusy(listOfServicePost)) {
				for(int i=0; i<listOfServicePost.size();i++) {
					if(listOfServicePost.get(i).isAvailable()) {
						listOfServicePost.get(i).setCustomer(listToProcess.dequeue());
					}
				}
			}
			
		
			//Arrival Event
					
			while(!listToCust.isEmpty() && listToCust.first().getArrivalTime()==time) {	
				listToProcess.enqueue(listToCust.dequeue());
			}
			time++;
		}
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
	
	/**
	 * 
	 * @param lista of servicePost
	 * This method decreases the remaining time of each customer being served
	 */
	public void decreaseTime(ArrayList<ServicePost> lista) {
		
		for(int i =0; i < lista.size(); i++) {
			lista.get(i).getCustomer().decreaseRemainingTime();
		}
		
	}

	
	
	 
	

	
	
	

}
