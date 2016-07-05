package agents;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class BaseAgent extends Agent {
	
	
	protected void registerDfAgent(String platform, String type)
	{
		DFAgentDescription ad = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setName("Platform-"+platform);
		sd.setType(type);
		ad.addServices(sd);
		
		try{
			DFService.register(this, ad);
		}catch(FIPAException e){
			e.printStackTrace();
		}
	}
	
	/*protected void takeDown()
	{
		if (subDF == null)
		{
			try {
				DFService.deregister(this);
			} catch (FIPAException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			try {
				DFService.deregister(this, this.subDF);
			} catch (FIPAException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}*/

}
