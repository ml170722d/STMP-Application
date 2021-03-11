package snmp_app;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ireasoning.protocol.snmp.*;

public class SNMPReader {

	private static String R[]= {"192.168.122.100", "192.168.10.1", "192.168.20.1", "192.168.30.1"}, readCommunity = "si2019", writeCommunity = "si2019";
	private static int version = SnmpConst.SNMPV2, port = 161;
	private static String[] oids = {".1.3.6.1.2.1.15.6.1.4", ".1.3.6.1.2.1.15.6.1.5", ".1.3.6.1.2.1.15.6.1.7", ".1.3.6.1.2.1.15.6.1.9", 
			".1.3.6.1.2.1.15.6.1.10", ".1.3.6.1.2.1.15.6.1.11", ".1.3.6.1.2.1.15.6.1.12", ".1.3.6.1.2.1.15.6.1.13"},
			oidNames = {"Origin", "AS Path", "MED", "Automatic aggregate", "Aggregator AS", "Aggregator addr", "Local preference", "Best route"};
	
	public static RouteSet read(int routerIndex, String routerAddredss) {
		RouteSet set = new RouteSet();
		try {
			SnmpTarget target;
			if (routerAddredss != null)
				target = new SnmpTarget(routerAddredss, port, readCommunity, writeCommunity, version);
			else
				target = new SnmpTarget(R[routerIndex], port, readCommunity, writeCommunity, version);
			
			SnmpSession session = new SnmpSession(target);
			SnmpSession.loadMib2();
			
			//starting and waiting all threads
			ExecutorService es = Executors.newCachedThreadPool();
			for(int i = 0; i < oids.length; i++)
				es.execute(new GetTableThread(session, oids[i], oidNames[i], set));
			
			es.shutdown();
			boolean finished = es.awaitTermination(10, TimeUnit.SECONDS);
			if (!finished) throw new InterruptedException();
		
		}catch (IOException e) {
			System.out.println("Sessino wasn't created");
		} catch (InterruptedException e) {
			System.out.println("Thread was interrupted or thread(s) exceeded given working time");
		}
		
		return set;
	}
	
	
	private static class GetTableThread extends Thread {
		private String assignedAttribute;
		private RouteSet set;
		private SnmpVarBind[] table;
		
		public GetTableThread(SnmpSession session, String oid, String attr, RouteSet se) throws IOException {
			table = session.snmpGetTableColumn(oid);
			assignedAttribute = attr;
			set = se;
		}
		
		@Override
		public void run() {
			try {
				
				Pattern p = Pattern.compile(".*(192\\.168\\..*)\\.24\\.(.*)");
				
				for (int i = 0; i < table.length; i++) {
					
					Matcher m = p.matcher(table[i].getName().toString());
					if (m.matches()) {
						Route r = set.get(m.group(1) + " " + m.group(2));
						
						switch (assignedAttribute) {
						case "Origin":
							r.setOrigin(((SnmpInt)table[i].getValue()).getValue());
							break;
						case "AS Path":
							r.setASPath(((SnmpOctetString) table[i].getValue()).toString());
							break;
						case "MED":
							r.setMED(((SnmpInt)table[i].getValue()).getValue());
							break;
						case "Automatic aggregate":
							r.setAutomaticAggregate(((SnmpInt)table[i].getValue()).getValue());
							break;
						case "Aggregator AS":
							r.setAggregatorASPath(((SnmpInt)table[i].getValue()).getValue());
							break;
						case "Aggregator addr":
							r.setAggregatorAddress(((SnmpOctetString) table[i].getValue()).toString());
							break;
						case "Local preference":
							r.setLocalPreference(((SnmpInt)table[i].getValue()).getValue());
							break;
						case "Best route":
							r.setBestRoute(((SnmpInt)table[i].getValue()).getValue());
							break;
						default:
							throw new Exception();
						}
						
					}
				}
				
			}catch (Exception e) {
				System.out.println("Unknown OID");
			}
		}
	}
}

/*
 *	Thread[] ts = new Thread[oids.length];
 *	for (int i = 0; i < oids.length; i++) {
 *		ts[i] = new GetTableThread(session, oids[i], oidNames[i], set);
 *		ts[i].start();
 *	}
 *
 *	for(int i = 0; i < ts.length; i++) {
 *		ts[i].join();
 *	}
 */
