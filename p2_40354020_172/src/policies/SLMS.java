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
 * In this policy, there is only one waiting line and many service posts.  When available, each post accepts a new customer. 
 * In case that there are more than one service post 
 */

//Single Line Multiple Servers

public class SLMS {
	
	ArrayQueue<Customer> listToCust;
	ArrayQueue<Customer> listToProcess = new ArrayQueue<>();
	
	ArrayList<ServicePost> listOfServicePost = new ArrayList<>();
	
	ArrayQueue<Customer> terminatedJobs;
	
	 public SLMS(ArrayQueue<Customer> listToProcess) {
		this.listToCust = listToProcess;
	}

	 
	public void result() {
		
		
		//set time
		int time = 0;
		
		//Creates and Initialize the amount of ServicePost wanted
		
		ServicePost servicePost1 = new ServicePost(); //aka cajero #1
		ServicePost servicePost2 = new ServicePost(); //aka cajero #2 
		
		
		
		//Adds the service post to my master list of service posts.
		
		listOfServicePost.add(servicePost1); //Anado cajero #1 a la lista de cajeros
		listOfServicePost.add(servicePost2);//Anado cajero #1 a la lista de cajeros 		
		
		while(!listToCust.isEmpty()||allSPBusy(listOfServicePost)) {
			
			//decreases the time of all customers 
			decreaseTime(listOfServicePost);
			
			/**
			 * ---Service-Completed Event---
			 * Verifico si se termino de service el customer en cada service Post 
			 */
			
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////			
			for(int i=0; i<listOfServicePost.size(); i++) {
				
				//verifica primero si en el service post hay alguien sino hay nadie pues no verifica el 
				//customer pq no tiene a nadie.
				
				if(!listOfServicePost.get(i).isAvailable()) {
					
					//busca el remaining time para verificar si es igual a cero
					//si es igual a cero pues quita el customer
					
					if(listOfServicePost.get(i).getCustomer().getRemainingTime()==0) {
					
						Customer p = listOfServicePost.get(i).removeCustomer();
						
						//le asigna al customer el departure time que es igual a time corriente
						
						p.setDepartureTime(time);
						
						System.out.println(p);
						
						//pone al customer en la lista de personas ya atendidas
						terminatedJobs.enqueue(p);
				}
			}
			}	//end of for
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
			
			/**
			 * Service-Starts Event
			 */
			
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

			while(!listToProcess.isEmpty()) {
				for(int i=0; i<listOfServicePost.size();i++) {
					if(listOfServicePost.get(i).isAvailable()) {
						listOfServicePost.get(i).setCustomer(listToProcess.dequeue());
					}
				}
			}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		
			/**
			 * Arrival Event
			 * Checks when people arrive. If their arrival time is equal to the time currently in the system
			 * they are added to the line of customers waiting to be served.
			 */
			
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					
			
			
			while(!listToCust.isEmpty() && listToCust.first().getArrivalTime()==time) {	
				Customer c = listToCust.dequeue();
				System.out.println("Entrando a ListToProcess=" + c);
			//	System.out.println(time);
				listToProcess.enqueue(c);
			}
			
			
			
			

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

			
	time++;	
			
}//end of while
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
				//Solo le hace decrease si el ServicePost tiene una persona atendiendolo
				if(!lista.get(i).isAvailable()) {
					
				lista.get(i).getCustomer().decreaseRemainingTime();
				}
			}
		
		
	}

	
	
	 
	

	
	
	

}
