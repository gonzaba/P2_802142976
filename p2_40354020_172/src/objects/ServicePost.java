package objects;

import classes.ArrayQueue;

/**
 * @author Barbara P. Gonzalez Rivera - 802-14-2976
 * @author Ramineh Lopez - 402-12-3657
 * ICOM4035 - 030
 */

public class ServicePost {

		//boolean that describe if the server is available
		//to serve a new customer
		boolean isAvailable = true;
		
		//person currently being served
		Customer person;
		
		//individual waiting list of the servicePost.
		//This won't be used for the first policy
		ArrayQueue<Customer> personalWaitingLine;
		
			
		//Setters	
	
		//Set the customer, at the same time changes the status
		//of the server to not available.
		public void setCustomer(Customer p) {
			
			isAvailable = false;
			person = p;
		}
		
		//removes customer from the service post
		//sets status of server to available
		public Customer removeCustomer() {
			isAvailable = true;
			Customer personLeaving = person;
			person = null;
			return personLeaving;
		}
		
		
		public void setPersonalWaitingLine(ArrayQueue<Customer> list) {	
			this.personalWaitingLine = list;	
		}
		
		
			
		//Getters
		
		public boolean isAvailable() {
			return isAvailable;
		}
		
		public Customer getCustomer() {
			return person;
		}
		
		public ArrayQueue<Customer> getPersonalWaitingLine(){
			return personalWaitingLine;
		}
		
			
}//end of ServicePost class
