package agents;

import basicData.TimePowerPrice;
import behaviours.ControlBehaviour;
import behaviours.ReceiveMessages;
import jade.core.Agent;

import java.io.IOException;
import java.util.*;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class ControlAgent extends BaseAgent {

	protected void setup(){
		/**
		 * Takes in input the id (number) of the platform for which it is created
		 * 
		 * arriva un messaggio 
		 */
		registerDfAgent("inputt", "ControlAgent");
		this.addBehaviour(new ReceiveMessages(this));
		
	}

}
