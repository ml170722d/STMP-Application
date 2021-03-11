package test_classes;

import snmp_app.Route;
import snmp_app.RouteSet;


public class test3 {
	@SuppressWarnings("unlikely-arg-type")
	public static void main(String[] args) {
		RouteSet rs = new RouteSet();
		
		System.out.println(rs.isEmpty());
		System.out.println(rs.size());
		Route r = rs.get("192.168.10.0 0.0.0.0");
		System.out.println(rs.isEmpty());
		System.out.println(rs.size());
		System.out.println(r.getNetworkAddress() + " | " + r.getNextHop());
		rs.get("192.168.20.0 0.0.0.0");
		System.out.println(rs.size());
		rs.get("192.168.30.0 0.0.0.0");
		System.out.println(rs.size());
		
		System.out.println(rs.contains("192.168.10.0 0.0.0.0") + " | " + rs.get("192.168.10.0 0.0.0.0").getNetworkAddress() + " <> " + rs.get("192.168.10.0 0.0.0.0").getNextHop());
		System.out.println(rs.size());
		System.out.println(rs.contains("192.168.20.0 0.0.0.0") + " | " + rs.get("192.168.20.0 0.0.0.0").getNetworkAddress() + " <> " + rs.get("192.168.20.0 0.0.0.0").getNextHop());
		System.out.println(rs.size());
		System.out.println(rs.contains("192.168.30.0 0.0.0.0") + " | " + rs.get("192.168.30.0 0.0.0.0").getNetworkAddress() + " <> " + rs.get("192.168.30.0 0.0.0.0").getNextHop());
		System.out.println(rs.size());
		
	}
}
