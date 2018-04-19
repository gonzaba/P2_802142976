package objects;




/**
 * 
 * @author Bárbara P. González Rivera - 802-14-2976
 * @author Ramineh Lopez - 402-12-3657
 * ICOM4035 - 030
 *
 */

public class ServicePost {

		
		boolean isAvailable = true;
		private Customer person;
			
		//Setters	
	
		public void setCustomer(Customer p) {
			
			isAvailable = false;
			person = p;
		}
		
		public Customer removeCustomer() {
			isAvailable = true;
			Customer personLeaving = person;
			person = null;
			return personLeaving;
		}
			
		//Getters
		
		public boolean isAvailable() {
			return isAvailable;
		}
		
		public Customer getCustomer() {
			return person;
		}
		
			
}//end of ServicePost class
