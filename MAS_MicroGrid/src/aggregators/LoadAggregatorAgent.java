package aggregators;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class LoadAggregatorAgent extends Agent {

	private static final long serialVersionUID = 1L;

	protected void setup(){
		/**
		 * L'agente si deve sottoscrivere a tutti gli agenti di tipo LoadAgent
		 */
		
		DFAgentDescription ad = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setName("Platform-1");
		sd.setType("LoadAggregatorAgent");
		ServiceDescription sd1 = new ServiceDescription();
		sd1.setName("Platform-1");
		sd1.setType("Aggregator");
		ad.addServices(sd1);
		ad.addServices(sd);
		
		try{
			DFService.register(this, ad);
			//System.out.println(this.getName() + ": ho registrato il servizio del BatteryAggregator");
			//this.addBehaviour(new ReceivePricesAndSendAll(this, 10000));
			//this.addBehaviour(new SaveData());
		}catch(FIPAException e){
			e.printStackTrace();
		}
		
		//this.addBehaviour(new ReceivePrice(this, 10000));
		
	}
}
