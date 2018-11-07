package Aufgabe1.dictionary;

import java.util.Scanner;


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
	
	public static void main(String[] args) {
		
		/* Erstellen eines Dictionaries.*/
		System.out.println("Bitte um Eingabe:");
		Scanner sc = new Scanner(System.in);
		String st = null; //soll aufruf von create erlauben wenn kein eingabeparameter
		while(!sc.next().equals("create")) {
			System.out.printf("Falsche eingabe: Bitte erstelle erst eine Implementierung über:%n "
					+ "create Implementierung %n" +
						"Mögliche Implementierungen:%n"
						+ " SortedArrayDictionary HashDictionary %n");
			
		}
		
		if (sc.next().equals("HashD") || sc.next().equals("BinTreeD")) {
			st = sc.next();
		} // soll später create(dictArt) erlauben
		create();
		
		/* ARbeiten im Dictionary */
		
		while(sc.hasNext()) {
			String s = sc.next();
			
			if(s.equals("i")) { //neuer Eintrag
					dict.insert(sc.next(), sc.next());
					System.out.println("Wort eingegeben");
			}else if(s.equals("p")) { //alles Ausgeben
				System.out.println("Alle Worterpaare im Dictionary:");
				for (Dictionary.Entry<String, String> e : dict) {
					System.out.println(e.getKey() + ": " + dict.search(e.getKey()));
				}
			}else if(s.equals("read")) { /* TODO: einlesen einer externen Datei erlauben*/
				int i;
				if (sc.hasNextInt()) {
					i = sc.nextInt();
				} else i = -1;
				System.out.println(i);
				
			}
			else if(s.equals("s")) {// suchen eines Wortes
				System.out.println("Ausgabe Wort:");
				System.out.println(dict.search(sc.next()));
				
			}else if(s.equals("r")) { //Löschen
				dict.remove(sc.next());
				System.out.println("Eintrag gelöscht");
				
			}else if(s.equals("exit")) {// Programm beenden
				System.out.println("Beendet");
				break;
			} else {//Hilfestellung
				System.out.printf("Mögliche eingaben: %np %ns Wort %ni wort1 wort2 %nr wort %nexit");
			}
		}
		sc.close();
	}
	
	private static void create(String s) {
		if(s.equals("HashD")) dict = new HashDictionary<String, String>();
		//if(s.equals("BinTreeD")) dict = new BinarySearchTree<String, String>();
		else dict = new SortedArrayDictionary<String, String>();
	}
	private static void create() {
		dict = new HashDictionary<String, String>();
	}
	
	
	
}
