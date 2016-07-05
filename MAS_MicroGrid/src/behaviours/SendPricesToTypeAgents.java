package behaviours;

import java.io.IOException;
import java.util.ArrayList;

import agents.BaseAgent;
import basicData.TimePowerPrice;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class SendPricesToTypeAgents extends OneShotBehaviour {

	/**
	 * Vedere come usare platform!!
	 */
	ACLMessage msg;
	String platform;
	String serviceType = "";
	public SendPricesToTypeAgents(ACLMessage msg, String platform, String serviceType){
		this.msg = msg;
		this.platform = platform;
		this.serviceType = serviceType;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void action() {

		/**
		 * Receive the message from the GridAgent, save the content in a variable
		 * and then it sends the same (?) message to the aggregators
		 */
		
		try {
			ArrayList<TimePowerPrice> content = (ArrayList<TimePowerPrice>)msg.getContentObject();
			
			ACLMessage output = new ACLMessage(ACLMessage.INFORM);
			output.setContentObject(msg.getContentObject());
			output.setConversationId(msg.getConversationId());
			DFAgentDescription[] ca = new BaseAgent().getAgentsbyServiceType(this.myAgent, serviceType);

			for(int i=0; i < ca.length; i++) {
				output.addReceiver(ca[i].getName());
			}
			this.myAgent.send(output);
			
		} catch (UnreadableException | IOException e) {
			e.printStackTrace();
		} 
	}

}
