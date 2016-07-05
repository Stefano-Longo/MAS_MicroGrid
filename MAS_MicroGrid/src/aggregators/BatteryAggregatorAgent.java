package aggregators;

import behaviours.ReceiveMessages;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class BatteryAggregatorAgent extends Agent {

	/**
	 * L'agente si deve sottoscrivere a tutti gli agenti di tipo BatteryAgent
	 */
	private static final long serialVersionUID = 1L;

	protected void setup(){
		
		
		DFAgentDescription ad = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setName("Platform-1");
		sd.setType("BatteryAggregatorAgent");
		ServiceDescription sd1 = new ServiceDescription();
		sd1.setName("Platform-1"); // 1 needs to be given with a variable
		sd1.setType("Aggregator");
		ad.addServices(sd1);
		ad.addServices(sd);
		
		try{
			DFService.register(this, ad);
		}catch(FIPAException e){
			e.printStackTrace();
		}
		
		this.addBehaviour(new ReceiveMessages(this));
	
	}
	
}
