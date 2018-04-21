package objects;

public class Result {

	/**
	 * This class is used to set up the printing format
	 * for a file that was successfully processed.
	 */
	
	
	    private int t1; //time when all services are completed
	    private int t2; //average waiting time for the input data
	    private int m; //average number of customers that reached the service post

	    //constructors
	    public Result(int t1, int t2, int m) {
	        this.t1 = t1;
	        this.t2 = t2;
	        this.m = m;
	    }

	   

		//setters
	    public void setTimeServicesCompleted(int t1) {
	        this.t1 = t1;
	    }

	    public void setAverageWaitingTime(int t2) {
	        this.t2 = t2;
	    }

	    public void setAverageNumOfCust(int m) {
	    	this.m = m;
	    }
	    
	   
	    
	    
	    //Getters
	    public int getTimeServicesCompleted() {
	        return t1;
	    }
	    public int getAverageWaitingTime() {
	        return t2;
	    }
	    public int getAverageNumOfCust() {
	    	return m;
	    }
	 
	    public String toString() {
			return t1 + " " + t2 + " " + m; 
	    	
	    }
	

}
