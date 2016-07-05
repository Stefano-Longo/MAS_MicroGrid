package basicData;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;


public class TimePowerPrice implements Serializable {
	
	/**
	 * This class will be a type of data
	 * It is the type of input message in the system
	 */
	private Calendar time = Calendar.getInstance();
	private double maxFlexibility; // in KW
	private double flexibilityPrice; //in € per kw of energy in input or output
	private double maxEnergy; //range in which I have that price, KW_min = 0
	private double energyPrice; // in € for each kw of change producing less or more
	
	public TimePowerPrice(Calendar time, double maxFlexibility, double flexibilityPrice, double maxEnergy, double energyPrice) {
		super();
		this.time = time;
		this.maxFlexibility = maxFlexibility;
		this.flexibilityPrice = flexibilityPrice;
		this.maxEnergy = maxEnergy;
		this.energyPrice = energyPrice;
	}
	
	public TimePowerPrice(){	}

	public Calendar getTime() {
		return time;
	}

	public void setTime(Calendar time) {
		this.time = time;
	}

	public double getMaxFlexibility() {
		return maxFlexibility;
	}

	public void setMaxFlexibility(double maxFlexibility) {
		this.maxFlexibility = maxFlexibility;
	}

	public double getFlexibilityPrice() {
		return flexibilityPrice;
	}

	public void setFlexibilityPrice(double flexibilityPrice) {
		this.flexibilityPrice = flexibilityPrice;
	}

	public double getMaxEnergy() {
		return maxEnergy;
	}

	public void setMaxEnergy(double maxEnergy) {
		this.maxEnergy = maxEnergy;
	}

	public double getEnergyPrice() {
		return energyPrice;
	}

	public void setEnergyPrice(double energyPrice) {
		this.energyPrice = energyPrice;
	}


	
}
