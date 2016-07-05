package behaviours;

import java.io.IOException;
<<<<<<< HEAD
import java.util.ArrayList;

import agents.BaseAgent;
import basicData.FlexibilityData;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import database.DbAggregatorBattery;

public class AggregateBatteryBehaviour extends OneShotBehaviour {

	/**
	 * save the data arrived by messages in the db and checks if the number of mex received is the 
	 * same of the number of battery agents. If it's the same, then send the total to the control behaviour.
	 * 
	 * 
	 * it saves the real data for the next hour (period of time) and sends to the control agent everything 
	 * taking this "everything" from the db
	 */
	private static final long serialVersionUID = 1L;
	
	ACLMessage msg;
	public AggregateBatteryBehaviour(ACLMessage msg) 
	{
		this.msg = msg;
	}

	public void action() 
	{
		try 
		{
			@SuppressWarnings("unchecked")
			ArrayList<FlexibilityData> msgData = (ArrayList<FlexibilityData>)msg.getContentObject();
			
			// Insert the message in the database
			for (int i=0; i < msgData.size(); i++){
				new DbAggregatorBattery().addFlexibilityBatteryMessage(this.myAgent.getName(), msgData.get(i));
			}
			int messagesReceived = new DbAggregatorBattery().countMessagesReceived(this.myAgent.getName());
			int batteryAgents = new BaseAgent().getAgentsbyServiceType(this.myAgent, "BatteryAgent").length;

			
			if (messagesReceived == batteryAgents)
			{
				/**
				 * I have all the messages that I was waiting for so now I can
				 * send the message to ControlAgent
				 */
				
				ArrayList<FlexibilityData> result = new DbAggregatorBattery().
						aggregateMessageReceived(this.myAgent.getName(), msgData.get(0).getAnalysisDatetime());
				
				ACLMessage output = new ACLMessage(ACLMessage.INFORM);			
				output.setContentObject(result);
				output.setConversationId("proposal");
				output.addReceiver(new BaseAgent().getAgentsbyServiceType(this.myAgent, "ControlAgent")[0].getName());
				
			}
		} 
		catch (UnreadableException | IOException e) 
		{
			e.printStackTrace();
		}
	}

	
		/**
		 * waits until the BatteryAgents answer and then sum their result. 
		 * If some of them didn't answer after (n)seconds then send again the initial message.
		 */
		
=======
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import basicData.FlexibilityData;
import basicData.TimePowerPrice;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class AggregateBatteryBehaviour extends OneShotBehaviour {

	/**
	 * save the data arrived by messages in the db and checks if the number of mex received is the 
	 * same of the number of battery agents. If it's the same, then send the total to the control behaviour.
	 * 
	 * 
	 * it saves the real data for the next hour (period of time) and sends to the control agent everything 
	 * taking this "everything" from the db
	 */
	private static final long serialVersionUID = 1L;
	
	ACLMessage msg;
	public AggregateBatteryBehaviour(ACLMessage msg) {
		this.msg = msg;
	}

	@Override
	public void action() {
		
		try {
			ArrayList<FlexibilityData> msgData = (ArrayList<FlexibilityData>)msg.getContentObject();
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			
			String connectionUrl = "jdbc:sqlserver://172.17.200.1:1433;" +  
	    	         "databaseName=MicroGrid;user=Longo;password=Admin.Longo"; 
			Connection conn = DriverManager.getConnection(connectionUrl);
        	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
			Statement stmt = conn.createStatement();
	    	String queryCheck = "SELECT COUNT(*) "
	    			+ "FROM AggregatorBattery "
	    			+ "WHERE IdAggregatorBattery = "+this.myAgent.getName()
	    			+ "AND DateTime == '"+format.format(msgData.get(0).getDatetime().getTime())+"'"
	    			+ "AND Estimation == true"
	    			+ "GROUP BY DateTime";
	    	System.out.println(queryCheck);
	    	ResultSet rs = stmt.executeQuery(queryCheck);
			int messagesArrived = 0;
			while (rs.next()) {
				messagesArrived = rs.getInt(1);
			}
			
			
			// get all batteryagents in my platform
			int batteryAgents = getAgentsbyServiceType("BatteryAgent").length;
			
			if (messagesArrived < batteryAgents){
				boolean estimation = false; //the first one is real value, the others are estimations of the future
				for (int i=0; i < msgData.size(); i++){
					
					estimation = i==0 ? false : true;
					String queryString = "INSERT INTO AggregatorBattery (IdAggregatorBattery, IdBattery, "
							+ "LowerLimit, UpperLimit, CostKwh, DesideredChoice, Gain, Estimation)"
							+ "VALUES ('"+this.myAgent.getName()+"',"+msgData.get(i).getIdBattery()+","+msgData.get(i).getDatetime().getTime()
							+ msgData.get(i).getLowerLimit()+","+msgData.get(i).getUpperLimit()+","+msgData.get(i).getCostKwh()+","
							+ msgData.get(i).getDesideredChoice()+","+msgData.get(i).getMaxGain()+","+estimation+")";
					
					System.out.println(queryString);
					stmt.execute(queryString);
				}
			}
			else
			{
				/**
				 * I have all the messages that I was waiting for so now I can
				 * send the message to ControlAgent
				 */
				
				ArrayList<FlexibilityData> result = new ArrayList<FlexibilityData>();
				String query = "SELECT Datetime, SUM(LowerLimit) as LowerLimit, SUM(UpperLimit) as UpperLimit,"
						+ " AVG(CostKwh) as CostKwh, SUM(DesideredChoice) as DesideredChoice, SUM(Gain) as Gain"
						+ " FROM AggregatorBattery"
						+ " WHERE IdAggregatorBattery = '"+this.myAgent.getName()+"'"
						+ " AND DateTime >= '"+format.format(msgData.get(0).getDatetime().getTime())+"'"
						+ " GROUP BY DateTime";
				ResultSet res = stmt.executeQuery(query);
				while(res.next()){
					Calendar cal = Calendar.getInstance();
					cal.setTime(rs.getDate(4));
					FlexibilityData data = new FlexibilityData(5, cal, res.getDouble("LowerLimit"), 
							res.getDouble("UpperLimit"), res.getDouble("CostKwh"), res.getDouble("DesideredChoice"), res.getDouble("Gain"));
					result.add(data);
				}
				/**
				 * cycle that takes all the values SUM aggregated by datetime from the db, 
				 * put them into the result variable and send it to the control agent
				 */
				ACLMessage output = new ACLMessage(ACLMessage.INFORM);			
				output.setContentObject(result);
				output.setConversationId("proposal");
				output.addReceiver(getAgentsbyServiceType("ControlAgent")[0].getName());
				
			}
		} catch (UnreadableException | SQLException | ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	
		/**
		 * waits until the BatteryAgents answer and then sum their result. 
		 * If some of them didn't answer after (n)seconds then send again the initial message.
		 */
		
	private DFAgentDescription[] getAgentsbyServiceType (String serviceType)
	{
		DFAgentDescription ad = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType(serviceType);
		ad.addServices(sd);
		DFAgentDescription[] ca = null;
		try {
			ca = DFService.search(this.myAgent, ad);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		return ca;
	}

>>>>>>> refs/remotes/origin/master
}
