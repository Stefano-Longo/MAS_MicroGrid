package behaviours;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import basicData.LoadData;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;

public class CalculateLoadFlexibilityBehaviour extends FSMBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private static final String STATE_A = "A";
	private static final String STATE_B = "B";
	private static final String STATE_C = "C";
	private static final String STATE_D = "D";


	public CalculateLoadFlexibilityBehaviour() {
		this.registerFirstState(new FirstState(), STATE_A);
		this.registerState(new UseLess(), STATE_B);
		this.registerState(new UseMore(), STATE_C);;
		this.registerLastState(new SaveChoice(), STATE_D);
		
		this.registerTransition(STATE_A, STATE_B, 0);
		this.registerTransition(STATE_A, STATE_C, 1);
		this.registerDefaultTransition(STATE_A, STATE_D);
		this.registerDefaultTransition(STATE_B, STATE_D);
		this.registerDefaultTransition(STATE_C, STATE_D);

	}

	
	private class FirstState extends OneShotBehaviour {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			ArrayList<LoadData> production = new ArrayList<LoadData>();
	    	String connectionUrl = "jdbc:sqlserver://172.17.200.1:1433;" +  
	    	         "databaseName=MicroGrid;user=Longo;password=Admin.Longo"; 
	    	
	    	Connection conn = null;  
	        Statement stmt = null;  
	        ResultSet rs = null;
			
	        try
			{
		        conn = DriverManager.getConnection(connectionUrl);
	        	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
		        stmt = conn.createStatement();
				String queryString = "select * from AgentLoad join AgentLoadManagement on Id = IdLoad";
				rs = stmt.executeQuery(queryString);
				System.out.println("CONNESSSOOOOOOO");
				while (rs.next()) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(rs.getDate(3));
					LoadData data = new LoadData(this.myAgent.getName(), this.myAgent.getHap(), cal, rs.getDouble(5), rs.getDouble(6), rs.getBoolean(7), rs.getBoolean(8));
					production.add(data);
				}
		    }
			catch(Exception e)
			{
		        e.printStackTrace();
		    }
			
			SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
			for(LoadData elem: production){
				System.out.println(format1.format(elem.getDateTime().getTime())+" "+elem.getCriticalConsumption()+" "+elem.getNonCriticalConsumption()+" "+elem.isSheddable()+" "+elem.isShiftable());
			}
		}
		
	}
	
	private class UseLess extends OneShotBehaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			
		}
		
	}
	
	private class UseMore extends OneShotBehaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			
		}
		
	}
	
	private class SaveChoice extends OneShotBehaviour {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void action() {
			
		}
		
	}
}
