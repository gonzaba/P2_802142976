package objects;



/**
 * @author Barbara P. Gonzalez Rivera - 802-14-2976
 * @author Ramineh Lopez - 402-12-3657
 * ICOM4035 - 030
 */

public class Customer {
			private int id;
			private int arrivalTime;
			private int serviceTime;
			private int remainingTime;
			private int departureTime;
			private int waitingTime;
			private int m;
			
			
		//Setters	
			
		/**
 		* 
 		* @param i set the id of the costumer to i
 		*/
			
		public void setId(int i) {
			id = i;
		}
		
		/**
		* 
		* @param t set the arrival time of the customer to t
		* 
		*/
		
		public void setArrivalTime(int t) {
			arrivalTime = t;
		}
		
		/**
		 * 
		 * @param s set the service time it takes customer to be serviced
		 */
		
		public void setServiceTime(int s) {
			serviceTime = s;
		}
		
		/**
		 * 
		 * @param d set the depatureTime of the customer
		 */
		public void setDepartureTime(int d) {
			departureTime = d;
		}
		/**
		 * 
		 * @param r set the amount of time it takes for order to be completed
		 */
		public void setRemainingTime(int r) {
			remainingTime = r;
		}
		
		public void setWaitingTime(int t) {
			waitingTime = t;
		}
		
		public void setM(int n) {
			this.m = n;
		}
		
		//Getters
		
		public int getId() {
			return id;
		}
		
		public int getArrivalTime() {
			return arrivalTime;
		}
		
		public int getServiceTime() {
			return serviceTime;
		}
		
		public int getDepartureTime() {
			return departureTime;
		}
		
		public int getRemainingTime() {
			return remainingTime;
		}
		
		public int getWaitingTIme() {
			return waitingTime;
		}
		
		public int getM() {
			return m;
		}
		
		/**
		 * decreases the remaining time by one number
		 */
		public void decreaseRemainingTime() {
			remainingTime--;
		}
		
		 public String toString() { 
			 return "ID = " + id + "  Arrival Time = " + arrivalTime + "  Remaining Time = " + remainingTime + "  Departure Time = " + departureTime + " Waiting Time= " + waitingTime + " M="+m; 				
		 }
		 
		 public void increaseM() {
			 m++;
		 }
		
	}//end of Customer class





