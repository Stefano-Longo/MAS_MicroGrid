package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;
import basicData.FlexibilityData;
import jade.core.Agent;

public class DbAggregatorBattery extends DbConnection {

	/**
	 * 
	 * @param idAgent
	 * @param data
	 * @return
	 */
	public Boolean addFlexibilityBatteryMessage (String idAgent, FlexibilityData data)
	{
		ArrayList<FlexibilityData> list = new ArrayList<FlexibilityData>();
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		String query = "INSERT INTO BatteryAggregatorData (IdAgent, IdBattery, AnalysisDateTime,"
				+ " DateTime, LowerLimit, UpperLimit, CostKwh, DesideredChoice, Gain)"
				+ " VALUES ('"+idAgent+"',"+data.getIdBattery()+",'"+format.format(data.getAnalysisDatetime().getTime())+"','"
				+ format.format(data.getDatetime().getTime())+"',"+data.getLowerLimit()+","+data.getUpperLimit()+","
				+ data.getCostKwh()+","+data.getDesideredChoice()+","+data.getMaxGain()+")";
		try {
			return stmt.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	/**
	 * 
	 * @param idAgent
	 * @param dateTime
	 * @return
	 */
	public ArrayList<FlexibilityData> aggregateMessageReceived (String idAgent, Calendar dateTime)
	{
		ArrayList<FlexibilityData> list = new ArrayList<FlexibilityData>();
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		String query = "SELECT AnalysisDatetime, Datetime, SUM(LowerLimit) as LowerLimit, SUM(UpperLimit) as UpperLimit,"
				+ " AVG(CostKwh) as CostKwh, SUM(DesideredChoice) as DesideredChoice, SUM(Gain) as Gain"
				+ " FROM BatteryAggregatorData"
				+ " WHERE IdAgent = '"+idAgent+"'"
				+ " AND AnalysisDateTime == '"+format.format(dateTime.getTime())+"'"
				+ " GROUP BY DateTime";
		System.out.println(query);
		try {
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next())
			{
				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(rs.getDate("AnalysisDateTime"));
				
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(rs.getDate("DateTime"));
				
				FlexibilityData data = new FlexibilityData(rs.getInt("IdBattery"), cal1, cal2, 
						rs.getDouble("LowerLimit"), rs.getDouble("UpperLimit"), rs.getDouble("CostKwh"), 
						rs.getDouble("DesideredChoice"), rs.getDouble("MaxGain"));
				list.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 
	 * @param idAgent
	 * @param dateTime
	 * @return 
	 */
	public int countMessagesReceived (String idAgent)
	{
		String query = "SELECT COUNT(*) as Count"
    			+ " FROM BatteryAggregatorData"
    			+ " WHERE IdAgent = '"+idAgent+"'"
    			+ " AND AnalysisDateTime in (SELECT Max(AnalysisDateTime)" 
											+"FROM BatteryAggregatorData)";
		try{
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next())
			{
				return rs.getInt("Count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 
	 * @param myAgent
	 * @return
	 */
	public ArrayList<FlexibilityData> getBatteryChoice(String myAgent)
	{
		ArrayList<FlexibilityData> list = new ArrayList<FlexibilityData>();
		String query = "SELECT B.IdAgent, IdBattery, LowerLimit, UpperLimit, CostKwh, DesideredChoice, MaxGain"
				+ " FROM BatteryAggregatorData A JOIN Battery B ON A.IdBattery = B.IdBattery"
				+ " WHERE A.IdAgent='"+myAgent+"'"
				+ " AND AnalysisDateTime in (SELECT MAX(AnalysisDateTime)"
											+" FROM BatteryAggregatorData";
		ResultSet rs;
		try {
			rs = stmt.executeQuery(query);
			while(rs.next())
			{
				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(rs.getDate("AnalysisDateTime"));
				
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(rs.getDate("DateTime"));
				FlexibilityData data = new FlexibilityData(rs.getInt("IdBattery"), cal1, cal2, 
						rs.getDouble("LowerLimit"), rs.getDouble("UpperLimit"), rs.getDouble("CostKwh"), 
						rs.getDouble("DesideredChoice"), rs.getDouble("MaxGain"));
				data.setIdBatteryAgent(rs.getString("IdAgent"));
				list.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
