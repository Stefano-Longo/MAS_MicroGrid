package behaviours;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class AggregateLoadBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ACLMessage msg;
	public AggregateLoadBehaviour(ACLMessage msg) {
		this.msg = msg;
	}
	
	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}
}
