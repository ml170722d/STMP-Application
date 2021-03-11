package test_classes;

import java.io.IOException;

import com.ireasoning.protocol.snmp.*;

public class test1 {

	private static String R[]= {"", "192.168.10.1", "192.168.20.1", "192.168.30.1"}, readCommunity = "si2019", writeCommunity = "si2019";
	private static int version = SnmpConst.SNMPV2, port = 161;
	private static String[] oids = {".1.3.6.1.2.1.15.6.1.4", ".1.3.6.1.2.1.15.6.1.5", ".1.3.6.1.2.1.15.6.1.6", ".1.3.6.1.2.1.15.6.1.7", ".1.3.6.1.2.1.15.6.1.9", 
			".1.3.6.1.2.1.15.6.1.10", ".1.3.6.1.2.1.15.6.1.11", ".1.3.6.1.2.1.15.6.1.12", ".1.3.6.1.2.1.15.6.1.13"},
			oidNames = {"Origin", "AS Path","Next Hop", "MED", "Automatic aggregate", "Aggregator AS", "Aggregator addr", "Local preference", "Best route"};
	
	public static void main(String[] args) {
		try {
			SnmpTarget target = new SnmpTarget(R[1], port, readCommunity, writeCommunity, version);
			SnmpSession session = new SnmpSession(target);
			
			SnmpSession.loadMib2();
			for (int j = 0; j < oids.length; j++) {
				System.out.println(oidNames[j]);
				SnmpVarBind[] tableModel = session.snmpGetTableColumn(oids[j]);
				
				System.out.println("ima " + tableModel.length + " redova");
				
				for (int i=0; i < tableModel.length; i++) {
					System.out.println(tableModel[i].getName() + " | " + tableModel[i].getValue());
					
					if (tableModel[i].getValue() instanceof SnmpOctetString) {
						SnmpOctetString o = (SnmpOctetString) tableModel[i].getValue();
						String n = o.toString();
						System.out.println("***" + n);
						if (n instanceof String) {
							System.out.println("eureka");
						}
					}
					if (tableModel[i].getValue() instanceof SnmpInt) {
						int a = ((SnmpInt)tableModel[i].getValue()).getValue();
						System.out.println("a = " + a);
					}
				}
				System.out.println("-----------------------------------------");
			}
			
			
			
			session.close();
		}catch (IOException e) {
			System.out.println("Greska");
		}
			
	}
}
