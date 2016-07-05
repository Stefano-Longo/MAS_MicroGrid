package agents;

import behaviours.CalculateLoadFlexibilityBehaviour;
import behaviours.ReceiveMessages;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.FSMBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class LoadAgent extends BaseAgent {

	protected void setup(){
		
		/**
		 * Takes as input the ids (number) of the platforms for which it is created
		 */
		
		registerDfAgent(this.getHap(), "LoadAgent");
		this.addBehaviour(new ReceiveMessages(this));
		
		// end registration
		
		FSMBehaviour fsm = new FSMBehaviour(this) {
			public int onEnd() {
				System.out.println("FSM behaviour completed.");
				myAgent.doDelete();
				return super.onEnd();
			}
		};
		/*
		// Register state A (first state)
		fsm.registerFirstState(new NamePrinter(), STATE_A);
		
		// Register state B
		fsm.registerState(new NamePrinter(), STATE_B);
		
		// Register state C
		fsm.registerState(new RandomGenerator(3), STATE_C);
		
		// Register state D
		fsm.registerState(new NamePrinter(), STATE_D);
		
		// Register state E
		fsm.registerState(new RandomGenerator(4), STATE_E);
		
		// Register state F (final state)
		fsm.registerLastState(new NamePrinter(), STATE_F);

		// Register the transitions
		fsm.registerDefaultTransition(STATE_A, STATE_B);
		fsm.registerDefaultTransition(STATE_B, STATE_C);
		fsm.registerTransition(STATE_C, STATE_C, 0);
		fsm.registerTransition(STATE_C, STATE_D, 1);
		fsm.registerTransition(STATE_C, STATE_A, 2);
		fsm.registerDefaultTransition(STATE_D, STATE_E);
		fsm.registerTransition(STATE_E, STATE_F, 3);
		fsm.registerDefaultTransition(STATE_E, STATE_B);
		*/
		this.addBehaviour(fsm);
	}
	
}
