package basicData;

public class ResultData {
	
	/**
	 * This class will be a type of data
	 * It is the type of output message of the Control Agent
	 */
	
	private double powerRequested; //negative if output
	private int time; 
	private double cost;
	
	public ResultData(double powerRequested, int time, double cost) {
		super();
		this.powerRequested = powerRequested;
		this.time = time;
		this.cost = cost;
	}

	public double getPowerRequested() {
		return powerRequested;
	}

	public void setPowerRequested(double power) {
		this.powerRequested = power;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}
}
