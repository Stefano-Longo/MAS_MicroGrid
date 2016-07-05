package behaviours;

import java.io.IOException;
import java.util.ArrayList;

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
	String receivers = "";
	public SendPricesToTypeAgents(ACLMessage msg, String platform, String receivers){
		this.msg = msg;
		this.platform = platform;
		this.receivers = receivers;
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
			//output.setContent(msg.getContent());
			output.setContentObject(msg.getContentObject());
			output.setConversationId(msg.getConversationId());
			DFAgentDescription ad = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType(receivers);
			ad.addServices(sd);
			DFAgentDescription[] ca = DFService.search(this.myAgent, ad);

			for(int i=0; i < ca.length; i++) {
				output.addReceiver(ca[i].getName());
			}
			this.myAgent.send(output);
			
		} catch (UnreadableException | FIPAException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
