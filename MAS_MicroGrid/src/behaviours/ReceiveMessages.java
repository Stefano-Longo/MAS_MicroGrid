package behaviours;

import agents.*;
import aggregators.*;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveMessages extends TickerBehaviour {

	public ReceiveMessages(Agent a) {
		super(a, 1000);
	}

	@Override
	protected void onTick() {
		
		MessageTemplate template = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
		ACLMessage msg = this.myAgent.receive(template); 
		if (msg!=null){
			System.out.println("entrato? ConvID: "+msg.getConversationId()+"  "+this.myAgent.getName());

			if(this.myAgent instanceof GridAgent && msg.getConversationId().equals("input"))
			{
				this.myAgent.addBehaviour(new CalculatePrices(msg));
			}
			else if(this.myAgent instanceof GridAgent && msg.getConversationId().equals("output"))
			{
				this.myAgent.addBehaviour(new GiveOutput());
			
			}
			else if(this.myAgent instanceof ControlAgent && msg.getConversationId().equals("input"))
			{
				this.myAgent.addBehaviour(new SendPricesToTypeAgents(msg, this.myAgent.getHap(),"Aggregator"));
			
			}
			else if(this.myAgent instanceof BatteryAgent && msg.getConversationId().equals("input"))
			{
				this.myAgent.addBehaviour(new BetteryFlexibilityBehaviour(msg));
			}
			
			else if(this.myAgent instanceof DerAggregatorAgent)
			{
				if(msg.getConversationId().equals("input"))
				{
					this.myAgent.addBehaviour(new SendPricesToTypeAgents(msg, this.myAgent.getHap(), "LoadAgent"));
				}
				else if(msg.getConversationId().equals("proposal"))
				{
					this.myAgent.addBehaviour(new AggregateLoadBehaviour(msg));
				}
				else if(msg.getConversationId().equals("response"))
				{
					this.myAgent.addBehaviour(new DisaggregateLoadBehaviour(msg));
				}
			}
			else if(this.myAgent instanceof BatteryAggregatorAgent)
			{
				if(msg.getConversationId().equals("input"))
				{
					this.myAgent.addBehaviour(new SendPricesToTypeAgents(msg, this.myAgent.getHap(), "BatteryAgent"));
				}
				else if(msg.getConversationId().equals("proposal"))
				{
					this.myAgent.addBehaviour(new AggregateBatteryBehaviour(msg));
				}
				else if(msg.getConversationId().equals("response"))
				{
					this.myAgent.addBehaviour(new DisaggregateBatteryBehaviour(msg));
				}
			}
			else if(this.myAgent instanceof DerAggregatorAgent)
			{
				if(msg.getConversationId().equals("input"))
				{
					this.myAgent.addBehaviour(new SendPricesToTypeAgents(msg, this.myAgent.getHap(), "DerAgent"));
				}
				else if(msg.getConversationId().equals("proposal"))
				{
					this.myAgent.addBehaviour(new AggregateDerBehaviour(msg));
				}
				else if(msg.getConversationId().equals("response"))
				{	
					this.myAgent.addBehaviour(new DisaggregateDerBehaviour(msg));
				}
			}
		}
	}
}
