package GUI;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
	
	/**
	 * @param args
	 */
	public MainFrame()
	{
		
		setVisible(true);
		
		createWidgets();
		addWidgets();
		
		pack();
		
		setMinimumSize(getPreferredSize());
	}

	private void createWidgets() {
		lblWillkommen.setText("Willkommen im Fahrtenbuch.");
		
	}

	private void addWidgets() {
		
		//Panel Willkommen
		panelWillkommen.setLayout(new BorderLayout());
		panelWillkommen.add(BorderLayout.PAGE_START, lblWillkommen);
		
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
