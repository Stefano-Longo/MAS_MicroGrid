package behaviours;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import basicData.BatteryData;
import basicData.BatteryInfo;
import basicData.FlexibilityData;
import basicData.LoadData;
import basicData.TimePowerPrice;
import database.DbBatteryData;
import database.DbBatteryInfo;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import utils.GeneralData;

public class BetteryFlexibilityBehaviour extends OneShotBehaviour {

	/**
	 * Never go under 20% of SOC
	 * Never go over 95% of SOC
	 */
	private static final long serialVersionUID = 1L;
	private int timeSlot = new GeneralData().timeSlot; //15 minuti -> da vedere come usare un valore globale
	private ACLMessage msg;
	ArrayList<TimePowerPrice> msgData = null; 

	public BetteryFlexibilityBehaviour(ACLMessage msg) {
		try {
			this.msg = msg;
			this.msgData = (ArrayList<TimePowerPrice>)msg.getContentObject();
		} catch (UnreadableException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void action() {
		/**
		 * Takes in input the price for the next hours
		 * 
		 * Sends a message to the BatteryAggregatorAgent containing the energy he can give/take
		 * for the next hour depending on the electricity-price and on the flexibility-price,
		 * on the capacity and on the current soc
		 * 
		 * This has the objective SOC at 50% at the beginning but then he needs to update this value
		 * learning from the past. How?
		 * 
		 * Then waits for the response from BatteryAggregatorAgent (are you sure? Or call another behaviour?)
		 */
		
		
		
		
		/**
		 * Calculate the max output and max input for the battery
		 */
		
		//BatteryInfo newBattery = new BatteryInfo(3, "BATT3@172.18.82.128:1099/JADE", "172.18.82.128:1099/JADE", 300, "litio", 200, 200, 20, 95);
		//new DbBatteryInfo().addBattery(newBattery);
		
		
				
		BatteryInfo batteryInfo = new DbBatteryInfo().getBatteryByIdAgent(this.myAgent.getName());
		BatteryData batteryData = new DbBatteryData().getLastBatteryData(batteryInfo.getIdBattery());
		
		double maxInput = getMaxInput(batteryData.getSoc(), batteryInfo.getSocMax(), batteryInfo.getCapacity(), 
				batteryInfo.getBatteryInputMax());
		double maxOutput = getMaxOutput(batteryData.getSoc(), batteryInfo.getSocMin(), batteryInfo.getCapacity(), 
				batteryInfo.getBatteryOutputMax());
		
    	/**
		 * define what the battery wants to do and the flexibility and the gain it has doing that
		 * use getSocObjective, or anyway another function
		 */
		Random rand;
		double desideredChoice = ThreadLocalRandom.current().nextDouble(-maxInput, maxOutput+1);
		double maxGain  = desideredChoice*(msgData.get(0).getEnergyPrice());
		
		
		/*
		 * PRIORITIES!! Think about it!
		 * 
		 * How to calculate desideredChoice :
		 *  - Reaching the SocObjective (33% of final value)
		 *  - Be ready for the next requests -> if the requests for the next periods usually (the majority)
		 *  	are of request of power from batteries, then I need to charge now (33% of final value)
		 *  - Depending on the prices of electricity and the price of flexibility. If it's much more convenient
		 *  	to sell now than another time during the day, so sell (33% of final value)
		 *  DesideredChoice: medium of these 3 values!!
		 *  
		 * MaxGain is the gain of the agent to do that Choice:
		 *  - Need to multiply desideredChoice for a value given by the percentages seen above. 
		 */
		
		ArrayList<FlexibilityData> list = new ArrayList<FlexibilityData>();
		FlexibilityData data = new FlexibilityData();
		Calendar cal = Calendar.getInstance();
		cal.setTime(batteryData.getDatetime().getTime());
		cal.add(Calendar.SECOND, new GeneralData().timeSlot);
		
		data = new FlexibilityData(batteryInfo.getIdBattery(), cal, batteryData.getDatetime(), -maxInput,
    			maxOutput, batteryData.getCostKwh(), desideredChoice, maxGain);
		list.add(data);
		
		ACLMessage response = this.msg.createReply();
		response.setPerformative(ACLMessage.INFORM);
		response.setConversationId("proposal");
		try {
			response.setContentObject(list);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.myAgent.send(response);
    	/**
		 * Aggiungere le previsioni per le prossime ore
		 */
    	
    	/*
				double costKwh = rs.getDouble(6);
				double maxGain = 0;
				double myChoice;
				
				//for now random number between maxInput and maxOutput
				Random rand;
				double randomNum = ThreadLocalRandom.current().nextDouble(-maxInput, maxOutput+1);
				data = new FlexibilityData(rs.getString(1), msgData.get(0).getTime(), maxInput, maxOutput, costKwh, randomNum, maxGain);
			}
			
			list.add(data);
			
			

			ACLMessage response = this.msg.createReply();
			response.setPerformative(ACLMessage.INFORM);
			response.setConversationId("proposal");
			response.setContentObject(list);
			this.myAgent.send(response);
        } 
        catch (SQLException | IOException | ClassNotFoundException e) 
        {
			e.printStackTrace();
		}
	*/
		
	}
	
	
		
	private double getMaxInput(double soc, double socMax, double capacity, double maxInputBattery)
	{
        if (soc >= socMax)
            return 0;
        double maxBatteryInputPercentage = (socMax - soc) * capacity * (60/timeSlot) / 100;
        if (maxBatteryInputPercentage > maxInputBattery)
        {
        	return maxInputBattery;
        }
    	return maxBatteryInputPercentage;
    }
	
	private double getMaxOutput(double soc, double socMin, double capacity, double maxOutputBattery)
	{
        if (soc <= socMin)
            return 0;
        double maxBatteryOutputPercentage = (soc - socMin) * capacity * (60 / timeSlot) / 100;

        if (maxBatteryOutputPercentage > maxOutputBattery)
        {
        	return maxOutputBattery;
        }
    	return maxBatteryOutputPercentage;
    }
		
	private double getSocObjective (){ //60% for now
		double socObjective = 0;
		

		/**
		 * BatteryDataHistory serve solo per autoaddestrare il soc obiettivo
		 * Ma se decide tutto il controller il soc obiettivo non viene preso in considerazione?
		 */
		/*ArrayList<BatteryData> BatteryDataHistory = new ArrayList<BatteryData>();
		
    	String connectionUrl = "jdbc:sqlserver://172.17.200.1:1433;" +  
    	         "databaseName=MicroGrid;user=Longo;password=Admin.Longo"; 
    	
    	Connection conn = null;  
        Statement stmt = null;  
        ResultSet rs = null;
		try
		{
	        conn = DriverManager.getConnection(connectionUrl);
        	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
	        stmt = conn.createStatement();
			String queryString = "select * from BatteryDataHistory JOIN AgentBattery ON Id = IdBattery"
					+ " WHERE AgentBattery.IdAgent = "+this.myAgent.getName()
					+ " GROUP BY DATEPART(YEAR, DT.[Date]), DATEPART(MONTH, DT.[Date]), DATEPART(DAY, DT.[Date]), DATEPART(HOUR, DT.[Date]),(DATEPART(MINUTE, DT.[Date]) / 15)";
			//raggruppa i dati per ogni 15 minuti
			rs = stmt.executeQuery(queryString);
			while (rs.next()) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(rs.getDate(4));
				BatteryData data = new BatteryData(rs.getInt(1), rs.getString(2), rs.getString(3), 
						cal, rs.getDouble(5), rs.getDouble(6), rs.getDouble(7), rs.getDouble(8), rs.getDouble(9), rs.getDouble(10));
				BatteryDataHistory.add(data);
			}
	    }catch(Exception e){
	    	
	    }*/
		
		return socObjective;
	}

}
