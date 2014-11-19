package logic;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;







import DBObjects.ABCEinteilung;
import DBObjects.Absatz;
import datasource.CrudBefehle;
import datasource.CrudFunktionen;


public class ABCRechnung {
	private static Connection DBconnection = null;
	private ArrayList<Absatz> artikellist;
	private int SumAnzahl = 0;
	private int SumMenge = 0;
	private double SumUmsatz = 0;
	
	public ABCRechnung(Connection _DBconnection){
		DBconnection = _DBconnection;
		ABCBerechnungUmsatz();
		ABCBerechnungAuftragsanzahl();
		ABCBerechnungMenge();
	}
	
	private ABCEinteilung getABCEinteilung(String kriterium){
		ABCEinteilung abceinteilung = new ABCEinteilung();
		try {
			ResultSet einteilung;
			if (kriterium == "Umsatz"){
				einteilung = CrudFunktionen.getResult(DBconnection, CrudBefehle.selectEinteilungUmsatz);
			}else if (kriterium == "Menge"){
				einteilung = CrudFunktionen.getResult(DBconnection, CrudBefehle.selectEinteilungMenge);
			}else {
				einteilung = CrudFunktionen.getResult(DBconnection, CrudBefehle.selectEinteilungAuftragsanzahl);
			}
			while (einteilung.next()) {
			abceinteilung.Bezeichnung = einteilung.getString("Bezeichnung");
			abceinteilung.AnteilA = einteilung.getInt("AnteilA");
			abceinteilung.AnteilB = einteilung.getInt("AnteilB");	
			abceinteilung.AnteilC = einteilung.getInt("AnteilC");
			}	
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return abceinteilung;
}
	public void ABCBerechnungUmsatz(){
		Writer fw = null;
		artikellist = new ArrayList<Absatz>();
		ABCEinteilung abcEinteilung;
		try {
			ResultSet daten = CrudFunktionen.getResult(DBconnection, CrudBefehle.selectUmsatzGrouped);
			abcEinteilung = getABCEinteilung("Umsatz");
			System.out.println(abcEinteilung.Bezeichnung +" " +abcEinteilung.AnteilA + " " +abcEinteilung.AnteilB +" " +abcEinteilung.AnteilC +" \n");
			
			while (daten.next()) {
				Absatz a = new Absatz();
				a.ArtikelNr = daten.getString("ArtikelNr");
				a.Umsatz = daten.getDouble("Gesamt Umsatz");
				SumUmsatz += a.Umsatz;
				artikellist.add(a);
			}			
		fw = new FileWriter( "fileWriterUmsatz.csv" );
		double prevUmsatzProzent = 0;
		int i = 0;
		for(Absatz a : artikellist){
			a.UmsatzProzent = ((double)a.Umsatz/(double)SumUmsatz)*100;
			
			a.UmsatzProzentKum = a.UmsatzProzent + prevUmsatzProzent;
			
			prevUmsatzProzent = a.UmsatzProzentKum;
			if(a.UmsatzProzentKum < abcEinteilung.AnteilA){
				a.UmsatzABCKennzahl = 'A';
			} else if (a.UmsatzProzentKum <abcEinteilung.AnteilA + abcEinteilung.AnteilB){
				a.UmsatzABCKennzahl = 'B';
			} else {
				a.UmsatzABCKennzahl = 'C';
			}
			  fw.append("\"" +  i + "\";\"" + a.ArtikelNr + "\";\"" + a.Umsatz + "\";\"" + a.UmsatzProzent + "\";\"" + a.UmsatzProzentKum + "\";\"" + a.UmsatzABCKennzahl + "\";");
			  fw.append( System.getProperty("line.separator") ); // e.g. "\n"
			i++;
		}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		catch ( IOException e ) {
		  System.err.println( "Konnte Datei nicht erstellen" );
		}
		finally {
		  if ( fw != null )
		    try { fw.close(); } catch ( IOException e ) { e.printStackTrace(); }
		}

		
			
	}

	
	
	public void ABCBerechnungMenge(){
		Writer fw = null;
		artikellist = new ArrayList<Absatz>();
		ABCEinteilung abcEinteilung;
		try {
			ResultSet daten = CrudFunktionen.getResult(DBconnection, CrudBefehle.selectMengeGrouped);
			abcEinteilung = getABCEinteilung("Menge");
			System.out.println(abcEinteilung.Bezeichnung +" " +abcEinteilung.AnteilA + " " +abcEinteilung.AnteilB +" " +abcEinteilung.AnteilC +" \n");
			
			while (daten.next()) {
				Absatz a = new Absatz();
				a.ArtikelNr = daten.getString("ArtikelNr");
				a.Menge = daten.getInt("Gesamt Menge");
				SumMenge += a.Menge;
				artikellist.add(a);
			}			
		fw = new FileWriter( "fileWriterMenge.csv" );
		double prevMengeProzent = 0;
		int i = 0;
		for(Absatz a : artikellist){
			a.MengeProzent = ((double)a.Menge/(double)SumMenge)*100;
			a.MengeProzentKum = a.MengeProzent + prevMengeProzent;
			prevMengeProzent = a.MengeProzentKum;
			if(a.MengeProzentKum < abcEinteilung.AnteilA){
				a.MengeABCKennzahl = 'A';
			} else if (a.MengeProzentKum <abcEinteilung.AnteilA + abcEinteilung.AnteilB){
				a.MengeABCKennzahl = 'B';
			} else {
				a.MengeABCKennzahl = 'C';
			}
			  fw.append("\"" +  i + "\";\"" + a.ArtikelNr + "\";\"" + a.Menge + "\";\"" + a.MengeProzent + "\";\"" + a.MengeProzentKum + "\";\"" + a.MengeABCKennzahl + "\";");
			  fw.append( System.getProperty("line.separator") ); // e.g. "\n"
			i++;
		}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		catch ( IOException e ) {
		  System.err.println( "Konnte Datei nicht erstellen" );
		}
		finally {
		  if ( fw != null )
		    try { fw.close(); } catch ( IOException e ) { e.printStackTrace(); }
		}
	}

		public void ABCBerechnungAuftragsanzahl(){
			Writer fw = null;
			artikellist = new ArrayList<Absatz>();
			ABCEinteilung abcEinteilung;
			try {
				ResultSet daten = CrudFunktionen.getResult(DBconnection, CrudBefehle.selectAnzahlGrouped);
				abcEinteilung = getABCEinteilung("Auftragsanzahl");
				System.out.println(abcEinteilung.Bezeichnung +" " +abcEinteilung.AnteilA + " " +abcEinteilung.AnteilB +" " +abcEinteilung.AnteilC +" \n");
				
				while (daten.next()) {
					Absatz a = new Absatz();
					a.Anzahl = daten.getInt("Gesamt Anzahl");
					a.ArtikelNr = daten.getString("ArtikelNr");
					SumAnzahl += a.Anzahl;
					artikellist.add(a);
				}			
			fw = new FileWriter( "fileWriterAnzahl.csv" );

			double prevAnzahlProzent = 0;
			int i = 0;
			for(Absatz a : artikellist){
				a.AnzahlProzent = ((double)a.Anzahl/(double)SumAnzahl)*100;
				a.AnzahlProzentKum = a.AnzahlProzent + prevAnzahlProzent;
				prevAnzahlProzent = a.AnzahlProzentKum;
				if(a.AnzahlProzentKum < abcEinteilung.AnteilA){
					a.AnzahlABCKennzahl = 'A';
				} else if (a.AnzahlProzentKum <abcEinteilung.AnteilA + abcEinteilung.AnteilB){
					a.AnzahlABCKennzahl = 'B';
				} else {
					a.AnzahlABCKennzahl = 'C';
				}
				  fw.append("\"" +  i + "\";\"" + a.ArtikelNr + "\";\"" + a.Anzahl + "\";\"" + a.AnzahlProzent + "\";\"" + a.AnzahlProzentKum + "\";\"" + a.AnzahlABCKennzahl + "\";");
				  fw.append( System.getProperty("line.separator") ); // e.g. "\n"
				i++;
			}
			} catch(SQLException e) {
				e.printStackTrace();
			}
			catch ( IOException e ) {
			  System.err.println( "Konnte Datei nicht erstellen" );
			}
			finally {
			  if ( fw != null )
			    try { fw.close(); } catch ( IOException e ) { e.printStackTrace(); }
			}

			
				
				
			
	}
}
