package Start;

import java.io.File;

import javax.swing.JOptionPane;

import GUI.MainFrame;
import Klassen.Datenbank;

public class Start {

	
	
	public static void main(String[] args) {
		try
		{
			File file = new File("fahrtenbuch.db");
			if (file.exists() == false)
			{
				JOptionPane.showMessageDialog(null, "Es wurd keine Datenbank gefunden!");
				
			}
			else
			{
				new MainFrame();
			}
		}
		catch
		(Exception ex)
		{
			System.out.println(ex);
			JOptionPane.showMessageDialog(null, "Fehler beim Starten des Programms.");
		}
	}
	
}
 