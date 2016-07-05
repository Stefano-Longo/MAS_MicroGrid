package behaviours;

import java.io.IOException;
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

}
