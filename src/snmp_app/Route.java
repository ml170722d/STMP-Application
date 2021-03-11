package snmp_app;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Route {
	/*
	 * attributes indexes:
	 * 0 -> Network address			s
	 * 1 -> Origin					i
	 * 2 -> AS Path					s
	 * 3 -> Next Hop				s
	 * 4 -> MED						i
	 * 5 -> Automatic Aggregator	i
	 * 6 -> Aggregator AS Path		i
	 * 7 -> Aggregator Address		s
	 * 8 -> Local Preference		i
	 * 9 -> Best Route				i
	 */
	
	private String NetworkAddress, ASPath, NextHop, AggregatorAddress;
	private int Origin, MED, AutomaticAggregate, AggregatorASPath, LocalPreference, BestRoute;
	
	public Route(String netAddr, String nextH) {
		NetworkAddress = netAddr;
		NextHop = nextH;
	}
	public String getRoute() {
		return getNetworkAddress() + " " + getNextHop();
	}
	public String getNetworkAddress() {
		return NetworkAddress;
	}
	public String getASPath() {
		StringBuilder st = new StringBuilder();
		
		Pattern p1 = Pattern.compile("(0x[0-9A-F]* 0x[0-9A-F]*| )(.*)"), p2 = Pattern.compile("0x([0-9A-F]*) 0x([0-9A-F]*)"),
				p3 = Pattern.compile("( |....)(.*)");
		Matcher m1 = p1.matcher(ASPath), m2, m3;
		int i = 0;
		while (m1.matches() && m1.group(0).length() != 0) {
			if (i > 1) {
				m2 = p2.matcher(m1.group(1));
					if (m2.matches())
						st.append(m2.group(1)+m2.group(2));
					else
						st.append(" ");
				}
			i++;
			m1=p1.matcher(m1.group(2));
		}

		m3 = p3.matcher(st.toString());
		st = new StringBuilder();
		while(m3.matches() && m3.group(0).length() != 0) {
			if (m3.matches())
				if (!m3.group(1).equals(" "))
					st.append(Integer.parseInt(m3.group(1)));
				else
					st.append(" ");
			m3=p3.matcher(m3.group(2));
		}
		
		return st.toString();
	}
	public String getNextHop() {
		return NextHop;
	}
	public String getAggregatorAddress() {
		return AggregatorAddress;
	}
	public String getOrigin() {
		switch (Origin) {
		case 1:
			return "ibgp";
		case 2:
			return "ebgp";
		case 3:
			return "incomplete";
		}
		return null;
	}
	public int getMED() {
		return MED;
	}
	public String getAutomaticAggregate() {
		switch (AutomaticAggregate) {
		case 1:
			return "notSpecific";
		case 2:
			return "Specific";
		}
		return null;
	}
	public int getAggregatorASPath() {
		return AggregatorASPath;
	}
	public int getLocalPreference() {
		return LocalPreference;
	}
	public int getBestRoute() {
		return BestRoute;
	}
	
	public synchronized void setASPath(String aSPath) {
		ASPath = aSPath;
	}
	public synchronized void setAggregatorAddress(String aggregatorAddress) {
		AggregatorAddress = aggregatorAddress;
	}
	public synchronized void setOrigin(int origin) {
		Origin = origin;
	}
	public synchronized void setMED(int mED) {
		MED = mED;
	}
	public synchronized void setAutomaticAggregate(int automaticAggregator) {
		AutomaticAggregate = automaticAggregator;
	}
	public synchronized void setAggregatorASPath(int aggregatorASPath) {
		AggregatorASPath = aggregatorASPath;
	}
	public synchronized void setLocalPreference(int localPreference) {
		LocalPreference = localPreference;
	}
	public synchronized void setBestRoute(int bestRoute) {
		BestRoute = bestRoute;
	}
	
	public boolean isBest() {
		return BestRoute == 1? true : false;
	}
	@Override
	public String toString() {
		return NetworkAddress + " | " + NextHop + " | " + Origin + " | " +  ASPath + " | " +  MED + " | " 
				+ AutomaticAggregate + " | " +  AggregatorASPath + " | " +  AggregatorAddress + " | "
				+ LocalPreference + " | " +  BestRoute;
	}
}
