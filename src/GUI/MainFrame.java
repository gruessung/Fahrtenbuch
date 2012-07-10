package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;

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
	JLabel lblStart = new JLabel("Start: ");
	JLabel lblEnde = new JLabel("Ende: ");
	JRadioButton rbPrivat = new JRadioButton("privat");
	JRadioButton rbBeruf = new JRadioButton("geschäftlich");
	
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
		lblWillkommen.setText("Willkommen im Fahrtenbuch.");
		
	}

	private void addWidgets() {
		
		//Panel Willkommen
		panelWillkommen.setLayout(new BorderLayout());
		panelWillkommen.add(BorderLayout.PAGE_START, lblWillkommen);
		
		//Panel Eingabe
		panelEingabe.setLayout(new GridLayout(0,2));
		
		
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
