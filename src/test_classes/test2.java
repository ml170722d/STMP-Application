package test_classes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*Pattern p = Pattern.compile(".*(192\\.168\\..*)\\.24\\.(.*)");
		Matcher m = p.matcher(".10.424.42.42.424.2.424.192.168.10.0.24.0.0.0.0");
		if (m.matches()) {
			System.out.println("m *> " + m.group(1) + " <> " + m.group(2));
		}else {
			System.out.println("bad");
		}
		
		Pattern pp = Pattern.compile("(.*) (.*)");
		Matcher mm = pp.matcher("192.168.10.0 0.0.0.0");
		if (mm.matches()) {
			System.out.println("mm *> " + mm.group(1) + " <> " + mm.group(2));
		}else {
			System.out.println("bad");
		}*/
		
		Pattern p3 = Pattern.compile("(0x[0-9A-F]* 0x[0-9A-F]*| )(.*)"), p4 = Pattern.compile("0x([0-9A-F]*) 0x([0-9A-F]*)"),
				p5 = Pattern.compile("( |....)(.*)");
		Matcher m3 = p3.matcher("0x00 0x00 0x00 0x01 0x00 0x02"), m4, m5;
		StringBuilder st = new StringBuilder();
		int i = 0;
		while (m3.matches() && m3.group(0).length() != 0) {
			
			if (i > 1) {
			m4 = p4.matcher(m3.group(1));
				if (m4.matches())
					st.append(m4.group(1)+m4.group(2));
				else
					st.append(" ");
			}
			i++;
			m3=p3.matcher(m3.group(2));
		}
		System.out.println(st.toString());
		
		m5 = p5.matcher(st.toString());
		st = new StringBuilder();
		while(m5.matches() && m5.group(0).length() != 0) {
			if (m5.matches())
				if (!m5.group(1).equals(" "))
					st.append(Integer.parseInt(m5.group(1)));
				else
					st.append(" ");
			m5=p5.matcher(m5.group(2));
		}
		System.out.println(st.toString());
		System.out.println("fin");
	}

}
