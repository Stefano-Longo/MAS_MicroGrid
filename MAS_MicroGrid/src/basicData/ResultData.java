package basicData;

<<<<<<< HEAD
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
=======
import java.util.Date;


public class ResultData {
	
	/**
	 * This class will be a type of data
	 * It is the type of input message in the system
	 */
	private int time = 3600; // 1 hour
	private double kw; // in KW
	private double cost; // in € for each kw of change producing less or more
	
	public ResultData(int time, double kw, double cost) {
		super();
		this.time = time;
		this.kw = kw;
		this.cost = cost;
	}
	
	public ResultData(){	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public double getKw() {
		return kw;
	}

	public void setKw(double kw) {
		this.kw = kw;
	}
	
	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	
	
>>>>>>> refs/remotes/origin/master
}
