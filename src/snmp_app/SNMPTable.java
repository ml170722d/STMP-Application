package snmp_app;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SNMPTable extends JPanel {

	private int width, height;
	JPanel nameRow, data;
	
	public SNMPTable() {
		setLayout(null);
		
		nameRow = new JPanel();
		nameRow.setLayout(new GridLayout(1, 9));
		nameRow.setBackground(Color.WHITE);
		
		JLabel networkLabel = new JLabel(), nextHopLabel = new JLabel(), originLabel = new JLabel(), asPathlLabel = new JLabel(), 
				medlLabel = new JLabel(), autoAggrlLabel = new JLabel(), aggrASLabel = new JLabel(), aggrAddrLabel = new JLabel(),
				locPreflLabel = new JLabel();
		
		networkLabel.setText("Network");
		nextHopLabel.setText("Next Hop");
		originLabel.setText("Origin");
		//?
		asPathlLabel.setText("AS Path");		
		medlLabel.setText("MED");		
		autoAggrlLabel.setText("AutoAggr");		
		aggrASLabel.setText("AggrAS");		
		aggrAddrLabel.setText("AggrAddr");		
		locPreflLabel.setText("LocPref");
		
		nameRow.add(networkLabel);
		nameRow.add(nextHopLabel);
		
		nameRow.add(autoAggrlLabel);
		nameRow.add(aggrASLabel);
		nameRow.add(aggrAddrLabel);
		
		nameRow.add(originLabel);
		nameRow.add(locPreflLabel);
		nameRow.add(medlLabel);
		nameRow.add(asPathlLabel);
		
		add(nameRow);
		
		
		data = new JPanel();
		data.setBackground(Color.WHITE);
		
		add(data);
	}
	
	public void setData(RouteSet set) {
		int y = 0;
		data.removeAll();
		
		data.setLayout(null);
		for (int i = 0; i < set.size(); i++) {
			JPanel routeDesc = new JPanel();
			routeDesc.setBounds(0, y, data.getWidth(), 20);
			routeDesc.setLayout(new GridLayout(1, 9));
			
			Route route = set.get(i);
			//
			JLabel networkLabel = new JLabel(), nextHopLabel = new JLabel(), originLabel = new JLabel(), asPathlLabel = new JLabel(), 
					medlLabel = new JLabel(), autoAggrlLabel = new JLabel(), aggrASLabel = new JLabel(), aggrAddrLabel = new JLabel(),
					locPreflLabel = new JLabel();
			
			networkLabel.setText(route.getNetworkAddress());
			nextHopLabel.setText(route.getNextHop());
			originLabel.setText(route.getOrigin() + "");
			//?
			asPathlLabel.setText(route.getASPath());		
			medlLabel.setText(route.getMED() + "");		
			autoAggrlLabel.setText(route.getAutomaticAggregate() + "");		
			aggrASLabel.setText(route.getAggregatorASPath() + "");		
			aggrAddrLabel.setText(route.getAggregatorAddress());		
			locPreflLabel.setText(route.getLocalPreference() + "");
			
			if (route.isBest()) {
				networkLabel.setForeground(Color.RED);
				nextHopLabel.setForeground(Color.RED);
				originLabel.setForeground(Color.RED);
				asPathlLabel.setForeground(Color.RED);
				medlLabel.setForeground(Color.RED);
				autoAggrlLabel.setForeground(Color.RED);
				aggrASLabel.setForeground(Color.RED);
				aggrAddrLabel.setForeground(Color.RED);
				locPreflLabel.setForeground(Color.RED);
			}
			
			routeDesc.add(networkLabel);
			routeDesc.add(nextHopLabel);
			
			routeDesc.add(autoAggrlLabel);
			routeDesc.add(aggrASLabel);
			routeDesc.add(aggrAddrLabel);
			
			routeDesc.add(originLabel);
			routeDesc.add(locPreflLabel);
			routeDesc.add(medlLabel);
			routeDesc.add(asPathlLabel);
			
			routeDesc.setBackground(Color.WHITE);
			data.add(routeDesc);
			y+=20;
			//
		}
		data.repaint();
		data.revalidate();
	}
	
	@Override
	public void setBounds(int x, int y, int w, int h) {
		width = w;
		height = h;
		nameRow.setBounds(x, y, width, 20);
		data.setBounds(x, y+20, width, height-20);
		super.setBounds(x, y, width, height);
	}
	
	@Override
	public void setBackground(Color bg) {
		if (nameRow != null)
			nameRow.setBackground(bg);
		if (data != null)
			data.setBackground(bg);
		super.setBackground(bg);
	}
}
