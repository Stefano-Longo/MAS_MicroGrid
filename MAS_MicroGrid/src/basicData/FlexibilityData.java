package basicData;

import java.io.Serializable;
import java.util.Calendar;

public class FlexibilityData implements Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idBattery;
	private Calendar analysisDatetime;
	private Calendar datetime;
	private double lowerLimit;
	private double upperLimit;
	private double costKwh; //media pesata
	private double desideredChoice; // positive or negative value which represent the choice the single agent
	private double maxGain;
 
	private String idBatteryAgent;
	

	/**
	 * Message that agents send to aggregator agents
	 * 
	 * @param idBattery
	 * @param analysisDatetime
	 * @param datetime
	 * @param lowerLimit
	 * @param upperLimit
	 * @param costKwh
	 * @param desideredChoice
	 * @param maxGain
	 */
	public FlexibilityData(int idBattery, Calendar analysisDatetime, Calendar datetime, double lowerLimit, double upperLimit, double costKwh, double desideredChoice, double maxGain) {
		this.idBattery = idBattery;
		this.analysisDatetime = analysisDatetime;
		this.datetime = datetime;
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
		this.costKwh = costKwh;
		this.desideredChoice = desideredChoice;
		this.maxGain = maxGain;
	}
	
	public Calendar getAnalysisDatetime() {
		return analysisDatetime;
	}

	public void setAnalysisDatetime(Calendar analysisDatetime) {
		this.analysisDatetime = analysisDatetime;
	}

	public void setIdBattery(int idBattery) {
		this.idBattery = idBattery;
	}

	public FlexibilityData() {}
	
	public double getCostKwh() {
		return costKwh;
	}

	public void setCostKwh(double costKwh) {
		this.costKwh = costKwh;
	}

	public Calendar getDatetime() {
		return datetime;
	}

	public void setDatetime(Calendar datetime) {
		this.datetime = datetime;
	}

	public double getMaxGain() {
		return maxGain;
	}

	public void setMaxGain(double maxGain) {
		this.maxGain = maxGain;
	}

	public int getIdBattery() {
		return idBattery;
	}

	public void setId(int idBattery) {
		this.idBattery = idBattery;
	}

	public double getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(double lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	public double getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(double upperLimit) {
		this.upperLimit = upperLimit;
	}

	public double getDesideredChoice() {
		return desideredChoice;
	}

	public void setDesideredChoice(double desideredChoice) {
		this.desideredChoice = desideredChoice;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getIdBatteryAgent() {
		return idBatteryAgent;
	}

	public void setIdBatteryAgent(String idBatteryAgent) {
		this.idBatteryAgent = idBatteryAgent;
	}
	
}
