package basicData;

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

	
	
}
