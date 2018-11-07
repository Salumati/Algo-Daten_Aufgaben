package Aufgabe1.dictionary;

import java.awt.BorderLayout;
import java.awt.Insets;
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
	 * -read[n] Dateiname  Liest die ersten n Eintr�ge ein
	 * -p Gibt alle Eintr�ge auf konsole aus
	 * -s deutsch gibt das englische wort aus
	 * -i deutsch englisch f�gt neues Wortpaar ein
	 * -r deutsch  l�scht einen Eintrag
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
			System.out.printf("Falsche eingabe: Bitte erstelle erst eine Implementierung �ber:%n "
					+ "create Implementierung %n" +
						"M�gliche Implementierungen:%n"
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
				if(parts.length == 2) {
					try{
						
						Scanner fs = new Scanner(new File("C:\\Users\\Sarah\\eclipse-workspace\\Aufgaben_Algo&Da\\src\\Aufgabe1\\dictionary\\dtengl.txt"));
						
						int linesToRead = Integer.parseInt(parts[1]);
						int i = 0;
						while(i < linesToRead && fs.hasNext()) {
							String line = fs.nextLine();
							String[] words = line.split("\\s+");
							dict.insert(words[0], words[1]);
							i++;
						}
						fs.close();
					} catch(FileNotFoundException ex) {
						System.out.println("Falscher Dateipfad");
					}
				} else {
					try{
						Scanner fs = new Scanner(new File("C:\\Users\\Sarah\\eclipse-workspace\\Aufgaben_Algo&Da\\src\\Aufgabe1\\dictionary\\dictionary\\dtengl.txt"));
						while(fs.hasNext()) {
						dict.insert(fs.next(), fs.next());
						}
						fs.close();
					} catch(FileNotFoundException ex) {
						System.out.println("Falscher Dateipfad");
					}
				}
				
			}
			else if(parts[0].equals("s")) {// suchen eines Wortes
				System.out.println("Ausgabe Wort:");
				System.out.println(dict.search(parts[1]));
				
			}else if(parts[0].equals("r")) { //L�schen
				dict.remove(parts[1]);
				System.out.println("Eintrag gel�scht");
				
			}else if(parts[0].equals("exit")) {// Programm beenden
				System.out.println("Beendet");
				break;
			}else if(parts[0].equals("")) {
				System.out.println("null-Eingabe");
			}
					else {//Hilfestellung
				System.out.println("Fehlerhafte Eingabe");
				System.out.printf("M�gliche Eingaben: %np %ns Wort %ni wort1 wort2 %nr wort %nexit%n");
			}
		}
		sc.close();
		return;
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
	
	/*if(parts.length == 2) {
	try {
		int linesToRead = Integer.parseInt(parts[1]);
		JFileChooser jFC = new JFileChooser();
		jFC.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int returnVal = jFC.showOpenDialog(jFC);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			BufferedReader bufRead = new BufferedReader(new FileReader(jFC.getSelectedFile()));
			
			String line;
			int i = 0;
			while(i < linesToRead && bufRead.readLine() != null) {
				line = bufRead.readLine();
				
				String[] lineParts = line.split("\\s+");
				dict.insert(lineParts[0], lineParts[1]);
				i++;
			}
		}
		
	} catch(Exception x) {
		System.out.println("Keine G�ltige Zahl.");
		break;
	}
} else {
	JFileChooser jFC = new JFileChooser();
	jFC.setFileSelectionMode(JFileChooser.FILES_ONLY);
	int returnVal = jFC.showOpenDialog(jFC);
	try {
		
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			BufferedReader bufRead = new BufferedReader(new FileReader(jFC.getSelectedFile()));
			String line;
			while(bufRead.readLine() != null) {
				line = bufRead.readLine();
				String[] lineParts = line.split("\\s+");
				dict.insert(lineParts[0], lineParts[1]);
			}
			
		}
	} catch (Exception fnf) {
		System.out.println("Datei konnte nicht gefunden werden: " + fnf);
	}
	
}*/
	
	
	
}
