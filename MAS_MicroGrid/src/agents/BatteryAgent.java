package agents;

import behaviours.ReceiveMessages;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class BatteryAgent extends BaseAgent {
	
	protected void setup(){
		/**
		 * Takes in input the ids (number) of the platforms for which it is created
		 */
		
<<<<<<< HEAD
		registerDfAgent(this.getHap(), "BatteryAgent");
=======
		registerDfAgent("inputt", "BatteryAgent");
>>>>>>> refs/remotes/origin/master
		this.addBehaviour(new ReceiveMessages(this));
		
	}
}
