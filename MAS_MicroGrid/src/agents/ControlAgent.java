package agents;

import behaviours.ReceiveMessages;

public class ControlAgent extends BaseAgent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void setup(){
		/**
		 * Takes in input the id (number) of the platform for which it is created
		 * 
		 * arriva un messaggio 
		 */
		registerDfAgent(this.getHap(), "ControlAgent");
		this.addBehaviour(new ReceiveMessages(this));
		
	}

}
