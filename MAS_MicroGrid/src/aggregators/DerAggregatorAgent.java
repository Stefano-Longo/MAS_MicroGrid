package aggregators;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class DerAggregatorAgent extends Agent {

	protected void setup(){
		/**
		 * L'agente si deve sottoscrivere a tutti gli agenti di tipo DerAgent
		 */
		DFAgentDescription ad = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setName("Platform-1");
		sd.setType("DerAggregatorAgent");
		ServiceDescription sd1 = new ServiceDescription();
		sd1.setName("Platform-1");
		sd1.setType("Aggregator");
		ad.addServices(sd1);
		ad.addServices(sd);
		
		try{
			DFService.register(this, ad);
		}catch(FIPAException e){
			e.printStackTrace();
		}
		//this.addBehaviour(new ReceivePrice(this, 10000));

	}
}
