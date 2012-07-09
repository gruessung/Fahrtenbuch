package GUI;

import javax.swing.JFrame;

import Klassen.Datenbank;

public class MainFrame extends JFrame {

	public Datenbank db = new Datenbank("fahrtenbuch.db", "", "");
	
	/**
	 * @param args
	 */
	public MainFrame()
	{
		setSize(200,200);
		setVisible(true);
	}

}
