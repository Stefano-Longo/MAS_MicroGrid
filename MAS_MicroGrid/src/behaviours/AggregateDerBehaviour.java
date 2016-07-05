package behaviours;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class AggregateDerBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ACLMessage msg;
	public AggregateDerBehaviour(ACLMessage msg) 
	{
		this.msg = msg;
	}
	
	public void action() 
	{
		
	}
}
