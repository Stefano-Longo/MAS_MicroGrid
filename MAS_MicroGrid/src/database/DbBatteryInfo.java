package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import basicData.BatteryInfo;

public class DbBatteryInfo extends DbConnection{

	public Boolean addBattery(BatteryInfo battery)
	{
		String query = "INSERT INTO Battery (IdAgent, IdPlatform, Capacity, Type, BatteryInputMax,"
				+ " BatteryOutputMax, SocMin, SocMax)"
				+ " VALUES ('"+battery.getIdAgent()+"','"+battery.getIdPlatform()+"',"
						+battery.getCapacity()+",'"+battery.getType()+"',"+battery.getBatteryInputMax()+","
						+battery.getBatteryOutputMax()+","+battery.getSocMin()+","+battery.getSocMax()+")";
		try {
			return stmt.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public BatteryInfo getBatteryByIdAgent (String idAgent)
	{
		BatteryInfo battery = new BatteryInfo();
		String query = "SELECT *"
				+ " FROM Battery"
				+ " WHERE IdAgent = '"+idAgent+"'";
		System.out.println(query);
		try {
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				battery.setIdBattery(rs.getInt("IdBattery"));
				battery.setIdAgent(rs.getString("IdAgent"));
				battery.setIdPlatform(rs.getString("IdPlatform"));
				battery.setCapacity(rs.getDouble("Capacity"));
				battery.setType(rs.getString("Type"));
				battery.setBatteryInputMax(rs.getDouble("BatteryInputMax"));
				battery.setBatteryOutputMax(rs.getDouble("BatteryOutputMax"));
				battery.setSocMin(rs.getDouble("SocMin"));
				battery.setSocMax(rs.getDouble("SocMax"));
			}
			return battery;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
