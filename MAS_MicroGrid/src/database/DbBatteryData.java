package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import basicData.BatteryData;
import basicData.FlexibilityData;

public class DbBatteryData extends DbConnection	{

	public Boolean addBatteryData(BatteryData battery)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(battery.getDatetime().getTime());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		String query = "INSERT INTO BatteryDataHistory (Id, DateTime, SocObjective, Soc, CostKwh, InputPowerMax,"
				+ " OutputPowerMax, PowerRequested)"
				+ " VALUES ('"+battery.getIdBattery()+"','"+format.format(cal.getTime())+"',"+battery.getSocObjective()+","
						+battery.getSoc()+","+battery.getCostKwh()+","+battery.getInputPowerMax()+","
						+battery.getOutputPowerMax()+","+battery.getPowerRequested()+")";
		try {
			return stmt.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public BatteryData getLastBatteryData (int idBattery)
	{
		BatteryData data = new BatteryData();
		String query = "SELECT TOP 1 *"
				+ " FROM BatteryDataHistory"
				+ " WHERE RTRIM(IdBattery) = "+idBattery
				+ " ORDER BY DateTime DESC";
		System.out.println(query);
		try {
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next())
			{
				Calendar cal = Calendar.getInstance();
				cal.setTime(rs.getDate("DateTime"));
				
				data.setIdBattery(rs.getInt("IdBattery"));
				data.setDatetime(cal);
				data.setSocObjective(rs.getDouble("SocObjective"));
				data.setSoc(rs.getDouble("Soc"));
				data.setCostKwh(rs.getDouble("CostKwh"));
				data.setInputPowerMax(rs.getDouble("InputPowerMax"));
				data.setOutputPowerMax(rs.getDouble("OutputPowerMax"));
				data.setPowerRequested(rs.getDouble("PowerRequested"));
			}
			return data;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	/*public ArrayList<BatteryData> getLastBatteryData(String idAgent)
	{
		ArrayList<BatteryData> list = new ArrayList<BatteryData>();
		
		String queryString = "SELECT TOP 1 IdBattery, Soc, Capacity, BatteryInputMax, BatteryOutputMax, CostKwh"
				+ " FROM BatteryDataHistory AB JOIN AgentBattery A ON Id = IdBattery"
				+ " WHERE A.IdAgent = '"+idAgent+"'"
				+ " ORDER BY DateTime DESC";
		
		//System.out.println(queryString);
		ResultSet rs = stmt.executeQuery(queryString);
		
		while (rs.next()) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(rs.getDate(2));
			double maxInput = getMaxInput(rs.getDouble("Soc"), rs.getDouble("SocMax"),
					rs.getDouble("Capacity"), rs.getDouble("BatteryInputMax"));
			double maxOutput = getMaxOutput(rs.getDouble("Soc"), rs.getDouble("SocMin"), 
					rs.getDouble("Capacity"), rs.getDouble("BatteryOutputMax"));
			BatteryData batteryData = new BatteryData(rs.getString("IdBattery"), cal, rs.getDouble("SocObjective"),
					rs.getDouble("Soc"), rs.getDouble("CostKwh"), rs.getDouble("");
			
			double costKwh = rs.getDouble(6);
			double maxGain = 0;
			double myChoice;
			
			//for now random number between maxInput and maxOutput
			Random rand;
			double randomNum = ThreadLocalRandom.current().nextDouble(-maxInput, maxOutput+1);
			data = new FlexibilityData(rs.getString(1), msgData.get(0).getTime(), maxInput, maxOutput, costKwh, randomNum, maxGain);
		}
		
		list.add(data);
		return null;
	}*/
	
}
