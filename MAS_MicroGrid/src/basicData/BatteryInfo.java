package basicData;

public class BatteryInfo {

	/**
	 * Class connected to AgentBattery
	 */
	
	private int idBattery;
	private String idAgent;
	private String idPlatform;
	private double capacity;
	private String type;
	private double batteryInputMax;
	private double batteryOutputMax;
	private double socMin;
	private double socMax;
	
	public BatteryInfo(int idBattery, String idAgent, String idPlatform, double capacity, String type,
			double batteryInputMax, double batteryOutputMax, double socMin, double socMax) {
		super();
		this.idBattery = idBattery;
		this.idAgent = idAgent;
		this.idPlatform = idPlatform;
		this.capacity = capacity;
		this.type = type;
		this.batteryInputMax = batteryInputMax;
		this.batteryOutputMax = batteryOutputMax;
		this.socMin = socMin;
		this.socMax = socMax;
	}

	public BatteryInfo() { }
	
	public int getIdBattery() {
		return idBattery;
	}

	public void setIdBattery(int idBattery) {
		this.idBattery = idBattery;
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

	public double getCapacity() {
		return capacity;
	}

	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getBatteryInputMax() {
		return batteryInputMax;
	}

	public void setBatteryInputMax(double batteryInputMax) {
		this.batteryInputMax = batteryInputMax;
	}

	public double getBatteryOutputMax() {
		return batteryOutputMax;
	}

	public void setBatteryOutputMax(double batteryOutputMax) {
		this.batteryOutputMax = batteryOutputMax;
	}

	public double getSocMin() {
		return socMin;
	}

	public void setSocMin(double socMin) {
		this.socMin = socMin;
	}

	public double getSocMax() {
		return socMax;
	}

	public void setSocMax(double socMax) {
		this.socMax = socMax;
	}
	
	
	
	
}
