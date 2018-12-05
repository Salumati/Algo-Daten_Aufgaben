
package Aufgabe1.dictionary;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class DictionaryTUI {
	/*
	 * TODO:
	 * Komandos einfuegen:
	 * -create Implementierung legt ein Dictionary an (ist voreingestellt)
	 * -read[n] Dateiname  Liest die ersten n Einträge ein
	 * -p Gibt alle Einträge auf konsole aus
	 * -s deutsch gibt das englische wort aus
	 * -i deutsch englisch fügt neues Wortpaar ein
	 * -r deutsch  löscht einen Eintrag
	 * -exit beendet das Programm
	 */
	private static Dictionary<String, String> dict;
	//JFileChooser fc;
	
	public static void main(String[] args) throws FileNotFoundException {
		
		/* Erstellen eines Dictionaries.*/
		System.out.println("Bitte um Eingabe:");
		Scanner sc = new Scanner(System.in);
		
		/*		String[] createD = dictionary.split("//s+");*/
		while(!sc.next().equals("create")) {
			System.out.printf("Falsche eingabe: Bitte erstelle erst eine Implementierung über:%n "
					+ "create Implementierung %n" +
						"Mögliche Implementierungen:%n"
						+ " SortedArrayDictionary  HashDictionary: (Eingabe HashD) %n");
			
		}
		String dictionary =sc.next(); //soll aufruf von create erlauben wenn kein eingabeparameter
		create(dictionary);
		System.out.println("neues Dictonary wurde erstellt");
		
		/* ARbeiten im Dictionary */
		
		while(sc.hasNextLine()) {
			String s = sc.nextLine();
			//System.out.println("dies ist s " + s );
			String[]parts = s.split("\\s+");
			//System.out.println(parts.length);
			System.out.println(parts[0]);
			
			
			if(parts[0].equals("i") && parts.length == 3) { //neuer Eintrag
					dict.insert(parts[1], parts[2]);
					System.out.println("Wort eingegeben");
			}else if(parts[0].equals("p")) { //alles Ausgeben
				System.out.println("Alle Worterpaare im Dictionary:");
				for (Dictionary.Entry<String, String> e : dict) {
					System.out.println(e.getKey() + ": " + dict.search(e.getKey()));
				}
			}else if(parts[0].equals("read")) { /* TODO: einlesen einer externen Datei erlauben*/
				System.out.println("should read something");
				/* Ok, pass auf: Scanner kann auch Dateien lesen, 
				 * es ist also einfacher einen Scanner so einzustellen, 
				 * dass er die Zeilen der Datei lesen soll anstatt den BufferReader anzpassen.
				 * 
				 * Wuerde dennoch mit JFileChooser die Datei waehlen, 
				 * ist besser fuer die bedinung.
				 * TODO: herausfinden wie man einen Filechooser richtig implementiert 
				 * und wie man durch ihn die Adresse des ausgewaehlten files widergeben kann
				 */
				/*final JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(aComponent);*/
				Scanner fs = new Scanner(open());
				if(parts.length == 2) {
					int linesToRead = Integer.parseInt(parts[1]);
					int i = 0;
					while(i < linesToRead && fs.hasNext()) {
						String line = fs.nextLine();
						String[] words = line.split("\\s+");
						dict.insert(words[0], words[1]);
						i++;
					}
				} else {
					while(fs.hasNext()) {
					dict.insert(fs.next(), fs.next());
					}
					
				}
				fs.close();
				
			}
			else if(parts[0].equals("s")) {// suchen eines Wortes
				System.out.println("Ausgabe Wort:");
				System.out.println(dict.search(parts[1]));
				
			}else if(parts[0].equals("r")) { //Löschen
				dict.remove(parts[1]);
				System.out.println("Eintrag gelöscht");
				
			}else if(parts[0].equals("exit")) {// Programm beenden
				System.out.println("Beendet");
				break;
			}else if(parts[0].equals("")) {
				System.out.println("null-Eingabe");
			}
					else {//Hilfestellung
				System.out.println("Fehlerhafte Eingabe");
				System.out.printf("Mögliche Eingaben: %np %ns Wort %ni wort1 wort2 %nr wort %nexit%n");
			}
		}
		sc.close();
		return;
	}
	
		private static File open() {
		final JFileChooser openDialog = new JFileChooser("Datei auswählen");
		openDialog.setDialogType(JFileChooser.OPEN_DIALOG);
		openDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		openDialog.setCurrentDirectory(new File("/home"));
		
		openDialog.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY) || evt.getPropertyName().equals(JFileChooser.DIRECTORY_CHANGED_PROPERTY)){
					final File file = (File) evt.getNewValue();
				}
				
			}
		});
		openDialog.setVisible(true);
		final int result = openDialog.showOpenDialog(null);
		if(result == JFileChooser.APPROVE_OPTION) {
			return openDialog.getSelectedFile();
		}
		return null;
	}
	
	private static void create(String s) {
		if(s.equals("HashD")) {
			dict = new HashDictionary<String, String>();
			System.out.println("HAshD wurde erstellt");
		}
		//if(s.equals("BinTreeD")) dict = new BinarySearchTree<String, String>();
		else {
			dict = new SortedArrayDictionary<String, String>();
			System.out.println("SortedArray wurde erstellt");
			}
		}
		private static void create() {
			dict = new HashDictionary<String, String>();
		}
	
}
