package agents;

import behaviours.ReceiveMessages;

public class GridAgent extends BaseAgent {
	/**
	 * Takes in input the id (number) of the platform for which it is created
	 * 
	 * Get data and send to control agent a list (TimePowerPrice)
	 * with all the prices: ACTUAL PRICE (the real one for the next hour) 
	 * and FUTURE PRICE (the estimated for the next hours)
	 */
	private static final long serialVersionUID = 1L;
	
	
	protected void setup(){
		
		registerDfAgent(this.getHap(), "GridAgent");
		this.addBehaviour(new ReceiveMessages(this));
		
	}
	
	
}
