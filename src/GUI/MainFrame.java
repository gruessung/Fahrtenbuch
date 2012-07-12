package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import Klassen.Datenbank;

public class MainFrame extends JFrame {

	public Datenbank db = new Datenbank("fahrtenbuch.db", "", "");
	
	JTabbedPane tb = new JTabbedPane();
	JPanel panelWillkommen = new JPanel();
	JPanel panelEingabe = new JPanel();
	JPanel panelFahrer = new JPanel();
	JPanel panelAusgabe = new JPanel();
	
	JLabel lblWillkommen = new JLabel();
	
	//Eingabe
	JLabel lblkmStart = new JLabel("KM Start: ");
	JLabel lblkmEnde = new JLabel("KM Ende: ");
	JLabel lblStart = new JLabel("Startort: ");
	JLabel lblEnde = new JLabel("Endort: ");
	JRadioButton rbPrivat = new JRadioButton("privat");
	JRadioButton rbBeruf = new JRadioButton("geschäftlich");
	JTextField txtkmStart = new JTextField(10);
	JTextField txtkmEnde = new JTextField(20);
	JTextField txtStart = new JTextField(20);
	JTextField txtEnde = new JTextField(20);
	JButton btnOK = new JButton("Speichern");
	JComboBox fahrer = new JComboBox();
	JLabel lbluhrzeit1 = new JLabel("Startzeit:");
	JLabel lbluhrzeit2 = new JLabel("Endzeit:");
	JLabel lblmonat = new JLabel("Monat:");
	JLabel lblfahrer = new JLabel("Fahrer:");
	JComboBox monat = new JComboBox();
	Vector<String> monate = new Vector();
	JTextField u1 = new JTextField();
	JTextField u2 = new JTextField();
	
	/**
	 * @param args
	 */
	public MainFrame()
	{
		
		setVisible(true);
		setTitle("Fahrtenbuch");
		createWidgets();
		addWidgets();
		setPreferredSize(new Dimension(600,400));
		pack();
		
		
	}

	private void createWidgets() {
		
		monate.add("Januar");
		monate.add("Februar");
		monate.add("März");
		monate.add("April");
		monate.add("Mai");
		monate.add("Juni");
		monate.add("Juli");
		monate.add("August");
		monate.add("September");
		monate.add("Oktober");
		monate.add("November");
		monate.add("Dezember");
		
		DefaultComboBoxModel monatModel = new DefaultComboBoxModel(monate);
		monat.setModel(monatModel);
		
		lblWillkommen.setText("Willkommen im Fahrtenbuch.");
		
		try {
			if (db.getCn().isClosed())
			{
				db.setupConnection();
			}
			
			db.setRs(db.getSt().executeQuery("SELECT * FROM fahrer"));
			
			Vector<String> fahrerVector = new Vector<String>();
			
			while (db.getRs().next())
			{
				fahrerVector.add(db.getRs().getString("name"));
			}
			
			DefaultComboBoxModel fahrerModel = new DefaultComboBoxModel(fahrerVector);
			fahrer.setModel(fahrerModel);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void addWidgets() {
		
		//Panel Willkommen
		panelWillkommen.setLayout(new BorderLayout());
		panelWillkommen.add(BorderLayout.PAGE_START, lblWillkommen);
		
		//Panel Eingabe
		panelEingabe.setLayout(new GridLayout(0,2));
		panelEingabe.add(lblkmStart);
		panelEingabe.add(txtkmStart);
		panelEingabe.add(lblkmEnde);
		panelEingabe.add(txtkmEnde);
		panelEingabe.add(lblStart);
		panelEingabe.add(txtStart);
		panelEingabe.add(lblEnde);
		panelEingabe.add(txtEnde);
		panelEingabe.add(lbluhrzeit1);
		panelEingabe.add(u1);
		panelEingabe.add(lbluhrzeit2);
		panelEingabe.add(u2);
		panelEingabe.add(rbPrivat);
		panelEingabe.add(rbBeruf);
		panelEingabe.add(lblfahrer);
		panelEingabe.add(fahrer);
		panelEingabe.add(lblmonat);
		panelEingabe.add(monat);
		
		
		panelEingabe.add(btnOK);
		panelEingabe.add(Box.createVerticalGlue());
		panelEingabe.add(Box.createVerticalGlue());
		
		//TabbedPane
		tb.addTab("Willkommen", panelWillkommen);
		tb.addTab("Eingabe", panelEingabe);
		tb.addTab("Fahrerverwaltung", panelFahrer);
		tb.addTab("Ausgabe", panelAusgabe);
		
		//ContentPane
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(BorderLayout.CENTER, tb);
		
	}

	

}
