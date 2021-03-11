package snmp_app;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings({"serial" , "unlikely-arg-type"})
public class RouteSet extends LinkedList<Route>{

	@Override
	public boolean contains(Object o) {
		if (o instanceof String) {
			String s = (String) o;
			for (int i = 0; i < size(); i++) {
				if (get(i).getRoute().equals(s)) return true;
			}
			
			//if doesn't contain, create empty attribute route and add to set of routes
			Pattern p = Pattern.compile("(.*) (.*)");
			Matcher m = p.matcher(s);
			if (m.matches()) {
				Route r = new Route(m.group(1), m.group(2));
				add(r);
			}
			
			return contains(s);
		}
		return false;
	}
	
	public Route get(String s) {
		
		if (contains(s)) {
			for (int i = 0; i < size(); i++) {
				if (get(i).getRoute().equals(s)) return get(i);
			}
		}
		return null;
	}
	
}
