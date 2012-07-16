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
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

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
	
	
	
	
	//Eingabe
	public Boolean privat = false;
	public JLabel lblkmStart = new JLabel("KM Start: ");
	public JLabel lblkmEnde = new JLabel("KM Ende: ");
	public JLabel lblStart = new JLabel("Startort: ");
	public JLabel lblEnde = new JLabel("Endort: ");
	public JRadioButton rbPrivat = new JRadioButton("privat");
	public JRadioButton rbBeruf = new JRadioButton("gesch�ftlich");
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
	
	
	
	String dbKmStart = null;
	
	//Fahrerverwaltung
	public JTable fahrertabelle = new JTable();
	public JButton fahrerAdd = new JButton("Hinzuf�gen");
	public JButton fahrerDel = new JButton("L�schen");
	public JScrollPane scroll = new JScrollPane();
	
	//Ausgabe
	public JScrollPane scrollOut = new JScrollPane();
	public JTable ausgabetabelle = new JTable();
	public JComboBox<String> monatsauswahl = new JComboBox<String>();
	public JButton refresh = new JButton("Aktualisieren");
	
	
	
	/**
	 * @param args
	 */
	public MainFrame()
	{
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(200,200);
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
		monate.add("M�rz");
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
			
			db.setRs(db.getSt().executeQuery("SELECT kmende FROM fahrten ORDER BY id limit 1 offset 1"));
			if (db.getRs().next())
				dbKmStart = db.getRs().getString("kmende");
			
			if (dbKmStart != null)
			{
				txtkmStart.setText(dbKmStart);
				txtkmStart.setEditable(false);
			}
			
			
			DefaultComboBoxModel<String> fahrerModel = new DefaultComboBoxModel<String>(fahrerVector);
			fahrer.setModel(fahrerModel);
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Kann Datenbank nicht �ffnen!");
			e.printStackTrace();
			System.exit(-1);
			
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
				}
			}
		});
		
		
		
		//Fahrerverwaltung
		fahrerRefresh();
		
		fahrerAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String neuerFahrer = null;
				String sql = null;
				try
				{
					neuerFahrer = JOptionPane.showInputDialog("Geben Sie den Namen ein.");
					if (db.getCn().isClosed())
						db.setupConnection();
					
					sql = "INSERT INTO fahrer (name) VALUES ('"+neuerFahrer+"');";
					db.getSt().executeUpdate(sql);
					fahrerRefresh();
				}
				catch (SQLException ex)
				{
					System.out.println(ex);
				}
				catch (Exception ex)
				{
					System.out.println("Abbruch");
				}
			}
		});
		
		fahrerDel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Integer delIndex = fahrertabelle.getSelectedRow();
				String delName = fahrertabelle.getValueAt(delIndex, 0).toString();
				String sql = "DELETE FROM fahrer WHERE name IN('"+delName+"');";
				try {
					if (db.getCn().isClosed())
						db.setupConnection();
					db.getSt().executeUpdate(sql);
					fahrerRefresh();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		
		//Ausgabe
		DefaultComboBoxModel<String> monatsauswahlModel = new DefaultComboBoxModel<String>(monate);
		monatsauswahl.setModel(monatsauswahlModel);
		AusgabeTabelle();
		
		monatsauswahl.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(arg0.paramString());
				AusgabeTabelle();
			}
		});
		
	}

	 
	private void AusgabeTabelle() {
		
		ausgabetabelle.removeAll();

		Integer privat = 0;
		Integer beruf = 0;
		String text = "";
		
		String ausgabesql = "SELECT * FROM fahrten WHERE monat IN('"+monatsauswahl.getSelectedItem().toString()+"');";
		System.out.println(ausgabesql);
		try
		{
			if (db.getCn().isClosed())
			{
				db.setupConnection();
			}
			
			db.setRs(db.getSt().executeQuery(ausgabesql));

			DefaultTableModel tm = new DefaultTableModel();
			ausgabetabelle.setModel(tm);		
			
			tm.addColumn("KM Start");
			tm.addColumn("KM Ende");
			tm.addColumn("Startort");
			tm.addColumn("Zielort");
			tm.addColumn("Abfahrt");
			tm.addColumn("Ankunft");
			tm.addColumn("Fahrer");
			tm.addColumn("Privat/Gesch�ftlich");
			
			while (db.getRs().next())
			{
				Vector<String> data = new Vector<String>();
				data.add(db.getRs().getString("kmstart"));
				data.add(db.getRs().getString("kmende"));
				data.add(db.getRs().getString("start"));
				data.add(db.getRs().getString("ende"));
				data.add(db.getRs().getString("zeitstart"));
				data.add(db.getRs().getString("zeitende"));
				data.add(db.getRs().getString("fahrer"));
				if (db.getRs().getString("privat").contains("1"))
				{
					data.add("privat");
					privat++;
				}
				else
				{
					data.add("gesch�ftlich");
					beruf++;
				}
				
				tm.addRow(data);
			}

			
		}
		catch (SQLException ex)
		{
			System.out.println(ex);
		}
		
//		if (privat > beruf)
		
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
			JOptionPane.showMessageDialog(this, "W�hlen Sie bitte nur eine der Optionen privat oder gesch�ftlich.");
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
		
		if ((start.isEmpty() || ende.isEmpty()) && privat == false)
		{
			JOptionPane.showMessageDialog(this, "Bitte tragen Sie die Orte ein.");
			return;			
		}

		String su1 = u1.getText();
		String su2 = u2.getText();
		
		if ((su1.isEmpty() || su2.isEmpty()) && privat == false)
		{
			JOptionPane.showMessageDialog(this, "Bitte tragen Sie die Uhrzeit/Datum ein.");
			return;			
		}		

		String sFahrer = fahrer.getSelectedItem().toString();
		if ((sFahrer.isEmpty() || sFahrer.trim() == "") && privat == false)
		{
			JOptionPane.showMessageDialog(this, "Bitte w�hlen Sie den Fahrer aus.");
			return;						
		}
		
		String sMonat = monat.getSelectedItem().toString();
		if (sMonat.isEmpty())
		{
			JOptionPane.showMessageDialog(this, "Bitte w�hlen Sie den Monat aus.");
			return;						
		}
		String prv = "0";
		if (privat == true)
		{
			prv = "1";
		}
		
		
		String sql = "";
		
		sql = "INSERT INTO fahrten (kmstart, kmende, start, ende, zeitstart, zeitende, privat, monat, fahrer)";
		sql += " VALUES ('"+kmstart+"','"+kmende+"','"+start+"','"+ende+"','"+su1+"','"+su2+"','"+prv+"','"+sMonat+"','"+sFahrer+"');";
		try {
			if (db.getCn().isClosed())
			{
				db.setupConnection();
			}
			db.getSt().executeUpdate(sql);
			JOptionPane.showMessageDialog(this, "Daten wurden erfasst.");
			
			txtkmStart.setText(kmende);
			txtkmEnde.setText("");
			txtStart.setText("");
			txtEnde.setText("");
			u1.setText("");
			u2.setText("");
			rbBeruf.setSelected(false);
			rbPrivat.setSelected(false);
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(sql);
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
		
		panelEingabe.add(Box.createVerticalGlue());
		panelEingabe.add(btnOK);
		panelEingabe.add(Box.createVerticalGlue());
		panelEingabe.add(Box.createVerticalGlue());
		
		
		//Fahrerverwaltung
		panelFahrer.setLayout(new BorderLayout());
		scroll.getViewport().add(fahrertabelle);
		panelFahrer.add(BorderLayout.CENTER, scroll);
		panelFahrer.add(BorderLayout.PAGE_START, fahrerAdd);
		panelFahrer.add(BorderLayout.PAGE_END, fahrerDel);
		
		//Ausgabe
		scrollOut.getViewport().add(ausgabetabelle);

		panelAusgabe.setLayout(new BorderLayout());
		panelAusgabe.add(BorderLayout.PAGE_START, monatsauswahl);
		panelAusgabe.add(BorderLayout.CENTER, scrollOut);
		
		
		
		//TabbedPane
		tb.addTab("Willkommen", panelWillkommen);
		tb.addTab("Eingabe", panelEingabe);
		tb.addTab("Fahrerverwaltung", panelFahrer);
		tb.addTab("Ausgabe", panelAusgabe);
		
		//ContentPane
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(BorderLayout.CENTER, tb);	
	}

	private void fahrerRefresh()
	{
		
		try {
			db.setRs(db.getSt().executeQuery("SELECT * FROM fahrer"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		DefaultTableModel tm = new DefaultTableModel();
		
		fahrertabelle.setModel(tm);		
		
		tm.addColumn("Name");
		try {
			while (db.getRs().next())
			{
				Vector<String> data = new Vector<String>();
				data.add(db.getRs().getString("name"));
				tm.addRow(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			JOptionPane.showMessageDialog(this, "Kann Datenbank nicht �ffnen!");
			e.printStackTrace();
			System.exit(-1);
			
		}
		


	}

}
