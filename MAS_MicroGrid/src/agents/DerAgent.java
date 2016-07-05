package agents;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.AID;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import basicData.*;
import behaviours.ReceiveMessages;

public class DerAgent extends BaseAgent {
	
	/**
	 * Takes in input the ids (number) of the platforms for which it is created
	 * 
	 * Variable that represent the agent's state: 
	 * time series with how much it is producing, how much it will produce and when
	 * and the cost of the flexibility
	 * 
	 * this.getName() to match with the name in database to take all the data for load shedding and shifting 
	 */

	protected void setup(){
		
		registerDfAgent(this.getHap(), "DerAgent");
		addBehaviour(new ReceiveMessages(this));

	}
}
