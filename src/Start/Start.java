package Start;

import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import GUI.MainFrame;

/**
 * Startklasse
 * 
 * Prüft, ob DB vorhanden ist und startet dann das Programm.
 * 
 * @author alexander, florian, christopher, michael
 * @since last week
 * @version 0.1
 *
 */
public class Start {

	
	
	public static void main(String[] args) {
		try
		{
			//Existiert die DB?
			File file = new File("fahrtenbuch.db");
			if (file.exists() == false)
			{
				JOptionPane.showMessageDialog(null, "Es wurd keine Datenbank gefunden!");
				
			}
			else
			{
				//Programm starten
				UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
				MainFrame f = new MainFrame();
				
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
 