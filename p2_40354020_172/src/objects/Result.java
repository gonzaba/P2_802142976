package objects;

import java.text.DecimalFormat;

public class Result {
	
	/**
	 * @author Barbara P. Gonzalez Rivera - 802-14-2976
	 * @author Ramineh Lopez - 402-12-3657
	 * ICOM4035 - 030
	 */
	
	/**
	 * This class is used to set up the printing format
	 * for a file that was successfully processed.
	 */
	
	
	    private int t1; //time when all services are completed
	    private float t2; //average waiting time for the input data
	    private float m; //average number of customers that reached the service post

	    
	    DecimalFormat df = new DecimalFormat("0.00");
	    
	    //constructors
	    public Result(int t1, int t2, float m) {
	        this.t1 = t1;
	        this.t2 = t2;
	        this.m = m;
	    }

	   

		//setters
	    public void setTimeServicesCompleted(int t1) {
	        this.t1 = t1;
	    }

	    public void setAverageWaitingTime(float totalTime) {
	        this.t2 = totalTime;
	    }

	    public void setAverageNumOfCust(float m) {
	    	this.m = m;
	    }
	    
	   
	    
	    
	    //Getters
	    public int getTimeServicesCompleted() {
	        return t1;
	    }
	    public float getAverageWaitingTime() {
	        return t2;
	    }
	    public float getAverageNumOfCust() {
	    	return m;
	    }
	 
	    public String toString() {
			return t1 + " " + df.format(t2) + " " + df.format(m); 
	    	
	    }
	

}
