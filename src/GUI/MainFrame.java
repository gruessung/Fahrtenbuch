package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JButton;
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
		panelEingabe.add(lblkmStart);
		panelEingabe.add(txtkmStart);
		panelEingabe.add(lblkmEnde);
		panelEingabe.add(txtkmEnde);
		panelEingabe.add(lblStart);
		panelEingabe.add(txtStart);
		panelEingabe.add(lblEnde);
		panelEingabe.add(txtEnde);
		panelEingabe.add(rbPrivat);
		panelEingabe.add(rbBeruf);
		
		panelEingabe.add(Box.createVerticalGlue());
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
