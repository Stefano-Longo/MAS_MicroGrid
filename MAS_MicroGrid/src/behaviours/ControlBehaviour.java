package behaviours;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ControlBehaviour extends OneShotBehaviour {

	public ControlBehaviour(Agent a, long period) {
		super(a);
		// TODO Auto-generated constructor stub
	}

	/**
	 * need TIMEPOWERPRICE as input of this behaviour also
	 */
	
	@Override
	public void action() {
		/**
		 * waits for messages to arrive from the 3 aggregators and make the choice 
		 * In version 2.0 I can implement a "persistent delivery of ACL messages" 
		 * 
		 *
		 * Send the message with the result that is a single vector made by 3 values: 
		 * PERIOD (sec) - POWER (kw) - COST (€) -> Result.java
		 */
		
		MessageTemplate template = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
		ACLMessage msg = this.myAgent.receive(template); 

		//DEVO RICEVERE TUTTI E 3 I MESSAGGI, SE NON LI RICEVO ALLORA DEVO FARE QUALCOSA PER RECUPERARLI
		if (msg!=null){
			
		}
	}
	
	/**
	 * Checks if the list of what the agents decided to does not overcome the limit given as input 
	 * @return
	 */
	private boolean peakShaving()
	{
		boolean ok = false;
		return ok;
	}

}
