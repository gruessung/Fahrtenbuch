package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Klassen.Datenbank;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6524428346900526062L;

	public Datenbank db = new Datenbank("fahrtenbuch.db", "", "");
	
	public JTabbedPane tb = new JTabbedPane();
	public JPanel panelWillkommen = new JPanel();
	public JPanel panelEingabe = new JPanel();
	public JPanel panelFahrer = new JPanel();
	public JPanel panelAusgabe = new JPanel();
	
	public JLabel lblWillkommen = new JLabel();
	
	public Boolean privat = false;
	
	//Eingabe
	public JLabel lblkmStart = new JLabel("KM Start: ");
	public JLabel lblkmEnde = new JLabel("KM Ende: ");
	public JLabel lblStart = new JLabel("Startort: ");
	public JLabel lblEnde = new JLabel("Endort: ");
	public JRadioButton rbPrivat = new JRadioButton("privat");
	public JRadioButton rbBeruf = new JRadioButton("geschäftlich");
	public JTextField txtkmStart = new JTextField(10);
	public JTextField txtkmEnde = new JTextField(20);
	public JTextField txtStart = new JTextField(20);
	public JTextField txtEnde = new JTextField(20);
	public JButton btnOK = new JButton("Speichern");
	public JComboBox<String> fahrer = new JComboBox<String>();
	public JLabel lbluhrzeit1 = new JLabel("Startzeit / Datum:");
	public JLabel lbluhrzeit2 = new JLabel("Endzeit / Datum:");
	public JLabel lblmonat = new JLabel("Monat:");
	public JLabel lblfahrer = new JLabel("Fahrer:");
	public JComboBox<String> monat = new JComboBox<String>();
	public Vector<String> monate = new Vector<String>();
	public JTextField u1 = new JTextField();
	public JTextField u2 = new JTextField();
	
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
		
		DefaultComboBoxModel<String> monatModel = new DefaultComboBoxModel<String>(monate);
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
			
			DefaultComboBoxModel<String> fahrerModel = new DefaultComboBoxModel<String>(fahrerVector);
			fahrer.setModel(fahrerModel);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		btnOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Speichern();
				
			}
		});
		
		rbPrivat.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				if (rbPrivat.isSelected())
				{
					rbBeruf.setSelected(false);
					txtStart.setEnabled(false);
					txtEnde.setEnabled(false);
					u1.setEnabled(false);
					u2.setEnabled(false);
					fahrer.setEnabled(false);
					monat.setEnabled(false);
				}
			}
		});
		
		rbBeruf.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				if (rbBeruf.isSelected())
				{
					rbPrivat.setSelected(false);
					txtStart.setEnabled(true);
					txtEnde.setEnabled(true);
					u1.setEnabled(true);
					u2.setEnabled(true);
					fahrer.setEnabled(true);
					monat.setEnabled(true);
				}
			}
		});
		
	}

	
	private void Speichern() {
		if (rbBeruf.isSelected() && rbPrivat.isSelected() == false)
		{
			privat = false;
		}
		else if (rbBeruf.isSelected() == false && rbPrivat.isSelected())
		{
			privat = true;
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Wählen Sie bitte nur eine der Optionen privat oder geschäftlich.");
			return;
		}
		
		String kmstart = txtkmStart.getText();
		String kmende = txtkmEnde.getText();
		
		if (kmstart.isEmpty() || kmende.isEmpty())
		{
			JOptionPane.showMessageDialog(this, "Bitte tragen Sie die Kilometer ein.");
			return;			
		}
		try
		{
			 Double.parseDouble(kmstart);
			 Double.parseDouble(kmende);
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(this, "Bitte tragen Sie die Kilometer in Zahlen ein.");
			return;	
		}
		
		String start = txtStart.getText();
		String ende = txtEnde.getText();
		
		if (start.isEmpty() || ende.isEmpty() && privat == true)
		{
			JOptionPane.showMessageDialog(this, "Bitte tragen Sie die Orte ein.");
			return;			
		}

		String su1 = u1.getText();
		String su2 = u2.getText();
		
		if (su1.isEmpty() || su2.isEmpty() && privat == true)
		{
			JOptionPane.showMessageDialog(this, "Bitte tragen Sie die Uhrzeit ein.");
			return;			
		}		
		System.out.println(monat.getSelectedItem());
		
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
