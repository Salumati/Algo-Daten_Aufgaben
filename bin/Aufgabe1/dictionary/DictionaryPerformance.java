package Aufgabe1.dictionary;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.sql.Time;
import java.sql.Timestamp;

public class DictionaryPerformance {
	/* Soll die CPU-Zeiten für die verschiedenen Anwendungsfälle der Wörterbuchdateien erstellen
	 * n = 8000 und n = 16000 
	 * aufbau
	 * erfolgreiche suche
	 * nicht erfolgreiche Suche
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
				
		// Deklariere Dictionaries:
		//SA1 bekommt 8.000 Einträge
		Dictionary<String, String> dictSA1 = new SortedArrayDictionary<String,String>();
		//SA2 bekommt 16.000 Einträge
		Dictionary<String, String> dictSA2 = new SortedArrayDictionary<String,String>();

		//dictHa1 bekommt 8.000 Einträge
		Dictionary<String, String> dictHa1 = new HashDictionary<>();
		//dictHa2 bekommt 16.000 Einträge
		Dictionary<String, String> dictHa2 = new HashDictionary<>();
		
		//TimeSTamps:
		Timestamp start;
		Timestamp end;
		
		
		// Baue das Dictionary auf
		System.out.println("Aufbau Dictionary:");
		try{
			
			Scanner fs = new Scanner(new File("C:\\Users\\Sarah\\eclipse-workspace\\Aufgaben_Algo&Da\\src\\Aufgabe1\\dictionary\\dtengl.txt"));
			
			//SortedArray n=16.000
			start = new Timestamp(System.nanoTime());
			while(fs.hasNext()) {
				String line = fs.nextLine();
				String[] words = line.split("\\s+");
				dictSA2.insert(words[0], words[1]);
			}
			end = new Timestamp(System.nanoTime());
			System.out.printf("Zeit SortedA n=16000: %dms%n", end.getTime()/1000000 - start.getTime()/1000000);
			fs.close();
			
			//SortedArray n=8.000
			fs = new Scanner(new File("C:\\Users\\Sarah\\eclipse-workspace\\Aufgaben_Algo&Da\\src\\Aufgabe1\\dictionary\\dtengl.txt"));
			start = new Timestamp(System.nanoTime());
			for(int i = 0; i <= 8000; i++) {
				String line = fs.nextLine();
				String[] words = line.split("\\s+");
				dictSA1.insert(words[0], words[1]);
			}
			end = new Timestamp(System.nanoTime());
			System.out.printf("Zeit SortedA n=8000: %dms%n", end.getTime()/1000000 - start.getTime()/1000000);

			fs.close();
			
			//HashDict n=16.000
			fs = new Scanner(new File("C:\\Users\\Sarah\\eclipse-workspace\\Aufgaben_Algo&Da\\src\\Aufgabe1\\dictionary\\dtengl.txt"));
			start = new Timestamp(System.nanoTime());
			while(fs.hasNext()) {
				String line = fs.nextLine();
				String[] words = line.split("\\s+");
				dictHa2.insert(words[0], words[1]);
			}
			end = new Timestamp(System.nanoTime());
			System.out.printf("Zeit HashDict n=16000: %dms%n", end.getTime()/1000000 - start.getTime()/1000000);
			fs.close();
			
			//HashDict n=8.000
			fs = new Scanner(new File("C:\\Users\\Sarah\\eclipse-workspace\\Aufgaben_Algo&Da\\src\\Aufgabe1\\dictionary\\dtengl.txt"));
			start = new Timestamp(System.nanoTime());
			for(int i = 0; i <= 8000; i++) {
				String line = fs.nextLine();
				String[] words = line.split("\\s+");
				//System.out.println(i);
				dictHa1.insert(words[0], words[1]);
			}
			end = new Timestamp(System.nanoTime());
			System.out.printf("Zeit HashDict n=8000: %dms%n%n", end.getTime()/1000000 - start.getTime()/1000000);
			fs.close();
		} catch(FileNotFoundException ex) {
			System.out.println("Falscher Dateipfad");
		}
		
		
		// Erfolgreiche Suche (jedes Wort im Wörterbuch muss eimal gesucht werden
		
		System.out.println("Erfolgreiche Suche:");
		
		//SortedArray n=16.000
		start = new Timestamp(System.nanoTime());
		for (Dictionary.Entry<String, String> e : dictSA2) {
			dictSA2.search(e.getKey());
		}
		end = new Timestamp(System.nanoTime());
		System.out.printf("Zeit SortedArray n=16000: %dms%n", end.getTime()/1000000 - start.getTime()/1000000);
		
		//SortedArray n=8.000
		start = new Timestamp(System.nanoTime());
		for (Dictionary.Entry<String, String> e : dictSA2) {
			dictSA1.search(e.getKey());
		}
		end = new Timestamp(System.nanoTime());
		System.out.printf("Zeit SotedArray n=8000: %dms%n", end.getTime()/1000000 - start.getTime()/1000000);
		
		//HashDictionary n=16.000
		start = new Timestamp(System.nanoTime());
		for (Dictionary.Entry<String, String> e : dictSA2) {
			dictHa2.search(e.getKey());
		}
		end = new Timestamp(System.nanoTime());
		System.out.printf("Zeit HashDict n=16000: %dms%n", end.getTime()/1000000 - start.getTime()/1000000);
		
		//HashDictionary n=8.000
		start = new Timestamp(System.nanoTime());
		for (Dictionary.Entry<String, String> e : dictSA2) {
			dictHa1.search(e.getKey());
		}
		end = new Timestamp(System.nanoTime());
		System.out.printf("Zeit HashDict n=8000: %dms%n%n", end.getTime()/1000000 - start.getTime()/1000000);
		
		
		//Nicht erfolgreiche Suche (jedes englische Wort im Wörterbuch einmal suchen)
		
		System.out.println("Erfolglose Suche");
		
		//SortedArray n=16.000
		start = new Timestamp(System.nanoTime());
		for (Dictionary.Entry<String, String> e : dictSA2) {
			dictSA2.search(e.getValue());
		}
		end = new Timestamp(System.nanoTime());
		System.out.printf("Zeit SortedArray n=16000: %dms%n", end.getTime()/1000000 - start.getTime()/1000000);
		
		//SortedArray n=8.000
		start = new Timestamp(System.nanoTime());
		for (Dictionary.Entry<String, String> e : dictSA2) {
			dictSA1.search(e.getValue());
		}
		end = new Timestamp(System.nanoTime());
		System.out.printf("Zeit SotedArray n=8000: %dms%n", end.getTime()/1000000 - start.getTime()/1000000);
		
		//HashDictionary n=16.000
		start = new Timestamp(System.nanoTime());
		for (Dictionary.Entry<String, String> e : dictSA2) {
			dictHa2.search(e.getValue());
		}
		end = new Timestamp(System.nanoTime());
		System.out.printf("Zeit HashDict n=16000: %dms%n", end.getTime()/1000000 - start.getTime()/1000000);
		
		//HashDictionary n=8.000
		start = new Timestamp(System.nanoTime());
		for (Dictionary.Entry<String, String> e : dictSA2) {
			dictHa1.search(e.getValue());
		}
		end = new Timestamp(System.nanoTime());
		System.out.printf("Zeit HashDict n=8000: %dms%n", end.getTime()/1000000 - start.getTime()/1000000);
	}

}
