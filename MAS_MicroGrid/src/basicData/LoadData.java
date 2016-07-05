package basicData;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class LoadData implements Serializable {
	
	/**
	 * This class will be a type of data
	 */
	private static final long serialVersionUID = 1L;
	
	private String idAgent;
	private String idPlatform;
	private Calendar datetime = Calendar.getInstance();
	private double criticalConsumption; // in KW
	private double nonCriticalConsumption; 
	private boolean sheddable = false;
	private boolean shiftable = false;
	
	public LoadData(String idAgent, String idPlatform, Calendar datetime, double criticalConsumption, double nonCriticalConsumption, boolean sheddable, boolean shiftable) {
		super();
		this.idAgent = idAgent;
		this.idPlatform = idPlatform;
		this.datetime = datetime;
		this.criticalConsumption = criticalConsumption;
		this.nonCriticalConsumption = nonCriticalConsumption;
		this.sheddable = sheddable;
		this.shiftable = shiftable;
	}

	public String getIdAgent() {
		return idAgent;
	}

	public void setIdAgent(String idAgent) {
		this.idAgent = idAgent;
	}

	public String getIdPlatform() {
		return idPlatform;
	}

	public void setIdPlatform(String idPlatform) {
		this.idPlatform = idPlatform;
	}

	public Calendar getDateTime() {
		return datetime;
	}

	public void setDateTime(Calendar datetime) {
		this.datetime = datetime;
	}

	public double getCriticalConsumption() {
		return criticalConsumption;
	}

	public void setCriticalConsumption(double criticalConsumption) {
		this.criticalConsumption = criticalConsumption;
	}

	public double getNonCriticalConsumption() {
		return nonCriticalConsumption;
	}

	public void setNonCriticalConsumption(double nonCriticalConsumption) {
		this.nonCriticalConsumption = nonCriticalConsumption;
	}

	public boolean isSheddable() {
		return sheddable;
	}

	public void setSheddable(boolean sheddable) {
		this.sheddable = sheddable;
	}

	public boolean isShiftable() {
		return shiftable;
	}

	public void setShiftable(boolean shiftable) {
		this.shiftable = shiftable;
	}
	
	
}
