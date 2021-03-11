package snmp_app;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.Choice;
import java.awt.Dimension;

@SuppressWarnings({"serial"})
public class SNMP_APP extends JFrame {

	private JPanel contentPane;
	private SNMPTable table;
	private int updateInterval, routerIndex;
	private UpdateIntervalThred updateThread;
	private Choice choice;
	private String costomAddress;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SNMP_APP frame = new SNMP_APP(new Dimension(1400, 350), 10);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SNMP_APP(Dimension d, int updateIntervalInSec) {
		updateInterval = updateIntervalInSec;
		costomAddress = null;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2-d.getWidth()/2), (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2-d.getHeight()/2), (int)d.getWidth(), (int)d.getHeight());
		setTitle("SNMP Application");
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		int leftPanelWidth = 115;
		table = new SNMPTable();
		table.setBounds(0, 0, contentPane.getWidth()-leftPanelWidth, contentPane.getHeight());
		contentPane.add(table, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new GridLayout(3, 1));
		panel_1.setPreferredSize(new Dimension(leftPanelWidth, this.getHeight()/2));
		
		JButton b = new JButton();
		b.setText("Update");
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if (updateThread != null)
					updateThread.interrupt();
				updateThread = new UpdateIntervalThred();
				updateThread.start();
				
			}
		});
		panel_1.add(b);
		
		choice = new Choice();
		panel_1.add(choice);
		choice.add("PC");
		choice.add("R1");
		choice.add("R2");
		choice.add("R3");
		choice.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent item) {
				costomAddress = null;
				if (item.getItem().toString().equals("R1")) {
					routerIndex = 1;
				}else if (item.getItem().toString().equals("R2")) {
					routerIndex = 2;
				}else if (item.getItem().toString().equals("R3")){
					routerIndex = 3;
				}else {
					routerIndex = 0;
				}
				
				if (updateThread != null)
					updateThread.interrupt();
				updateThread = new UpdateIntervalThred();
				updateThread.start();
				
				RouteSet set = SNMPReader.read(routerIndex, costomAddress);
				//System.out.println(set.size());
				set.forEach(r->{
				//System.out.println(r.toString());
				});
				table.setData(set);
			}
		});
		
		JPanel costomAddressPanel = new JPanel();
		costomAddressPanel.setLayout(null);
		panel_1.add(costomAddressPanel);
		
		JTextField txtField = new JTextField();
		txtField.setBounds(0, 30, leftPanelWidth, 20);
		txtField.setText("192.168.122.100");
		
		Button go = new Button();
		go.setLabel("GO");
		go.setBounds(leftPanelWidth/2-15, 50, 30, 20);
		go.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				costomAddress = txtField.getText();
				if (costomAddress.equals("")) return;
				
				if (updateThread != null)
					updateThread.interrupt();
				updateThread = new UpdateIntervalThred();
				updateThread.start();				
			}
		});
		
		costomAddressPanel.add(txtField);
		costomAddressPanel.add(go);
	}
	
	private class UpdateIntervalThred extends Thread{
		
		@Override
		public void run() {
			//System.out.println("started");
			try {
				while(true) {
					RouteSet set = SNMPReader.read(routerIndex, costomAddress);
					table.setData(set);
					System.out.println(routerIndex + " | " + costomAddress);
					sleep(1000 * updateInterval);
				}
			} catch (InterruptedException e) {}
			//System.out.println("ended");
		}
	}
}
