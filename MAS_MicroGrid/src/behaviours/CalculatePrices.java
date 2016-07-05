package behaviours;

import java.io.IOException;
import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import basicData.TimePowerPrice;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
	
public class CalculatePrices extends OneShotBehaviour {

	/**
	 * This class takes in input the prices for the next hour and calculate 
	 * the prices for the hours after the next one estimating them
	 */
	private static final long serialVersionUID = 1L;
	private ACLMessage msg;
	
	public CalculatePrices(ACLMessage msg){
		this.msg = msg;
	}

	@Override
	public void action() {
		/**
		 * Elaborate and calculate the prices for all the hours of the day and put it in a list
		 * Then add the list to the message's content
		 */
		ArrayList<TimePowerPrice> list = new ArrayList<TimePowerPrice>();
	    Calendar cal = Calendar.getInstance(); // creates calendar
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		System.out.println("Bho");
		try {
	    	//08/12/1992 15:00, 150, 0.35, 200, 0.5
	    	TimePowerPrice element = new TimePowerPrice();
			String[] msgs = msg.getContent().split(",");
			cal.setTime(format.parse(msgs[0]));
			element.setTime(cal);
			element.setMaxFlexibility(Double.parseDouble(msgs[1]));
			element.setFlexibilityPrice(Double.parseDouble(msgs[2]));
			element.setMaxEnergy(Double.parseDouble(msgs[3]));
			element.setEnergyPrice(Double.parseDouble(msgs[4]));
			list.add(element);
			
			for(int i = cal.get(Calendar.HOUR_OF_DAY); i<24; i++){
				cal.add(Calendar.HOUR_OF_DAY, 1);
				Random rn = new Random();
				/**
				 * Here there should be the forecast!!!
				 */
				//the new cost can be from 50% to 150% of the last cost
				DecimalFormat df = new DecimalFormat("#.##");
				df.setRoundingMode(RoundingMode.CEILING);
				double energyPrice = element.getEnergyPrice()*(rn.nextInt(10)+5)/10; 
				double flexibilityPrice = element.getFlexibilityPrice()*(rn.nextInt(10)+5)/10;
				TimePowerPrice e = new TimePowerPrice(cal, element.getMaxFlexibility(), flexibilityPrice, element.getMaxEnergy(), energyPrice);
				list.add(e);
			}

			// Create the new message which will be sent to the ControlAgent
			ACLMessage output = new ACLMessage(ACLMessage.INFORM);
			output.setContent("ciao");
			output.setContentObject(list);
			
			DFAgentDescription ad = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType("ControlAgent");
			ad.addServices(sd);
			DFAgentDescription[] ca = DFService.search(this.myAgent, ad);
			output.addReceiver(ca[0].getName()); 
			output.setConversationId("input");
			this.myAgent.send(output);
			
	    } catch (ParseException e1) {
			e1.printStackTrace();
		} catch (FIPAException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
