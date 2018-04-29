package objects;

import java.util.ArrayList;

import classes.ArrayQueue;

/**
 * @author Barbara P. Gonzalez Rivera - 802-14-2976
 * @author Ramineh Lopez - 402-12-3657
 * ICOM4035 - 030
 */

/**
 * 
 * This class will handle what is technically a the cashier in the simulation.
 * It can verify if its available, who is it serving and the line it currently has 
 * for that service post.
 *
 */

public class ServicePost2 {

		//boolean that describe if the server is available
		//to serve a new customer
		boolean isAvailable = true;
		
		//person currently being served
		Customer person;
		
		//individual waiting list of the servicePost.
		//This won't be used for the first policy
		
		ArrayList<Customer> personalWaitingLine;
			
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
		
		
		public void setPersonalWaitingLine(ArrayList<Customer> list) {	
			this.personalWaitingLine = list;	
		}
		
		
			
		//Getters
		
		//Getter to that says is the server is available to take a new customer or not
		public boolean isAvailable() {
			return isAvailable;
		}
		
		//getter to get the customer that the server is currently serving
		public Customer getCustomer() {
			return person;
		}
		
		//getter to ge the list the current server has 
		public ArrayList<Customer> getPersonalWaitingLine(){
			return personalWaitingLine;
		}
		
			
}//end of ServicePost class
