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
import DBObjects.Artikel;
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
		
		artikellist = new ArrayList<Absatz>();
		try {
			ResultSet daten = CrudFunktionen.getResult(DBconnection, CrudBefehle.selectUmsatzGrouped);
			ABCEinteilung abceinteilung = getABCEinteilung("Umsatz");
			System.out.println(abceinteilung.Bezeichnung +" " +abceinteilung.AnteilA + " " +abceinteilung.AnteilB +" " +abceinteilung.AnteilC +" \n");
			
			while (daten.next()) {
				Absatz a = new Absatz();
				//a.Anzahl = daten.getInt("Anzahl");
				a.ArtikelNr = daten.getString("ArtikelNr");
				//a.Datum = daten.getDate("Datum");
				//a.LagerNr = daten.getInt("LagerNr");
				//a.Menge = daten.getInt("Menge");
				a.Umsatz = daten.getDouble("Gesamt Umsatz");
				
				//SumAnzahl += a.Anzahl;
				//SumMenge += a.Menge;
				SumUmsatz += a.Umsatz;
				
				artikellist.add(a);
				
				
			}			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		Writer fw = null;
		try
		{
		
		fw = new FileWriter( "fileWriter.txt" );
		double prevMengeProzent = 0;
		double prevAnzahlProzent = 0;
		double prevUmsatzProzent = 0;
		int i = 0;
		for(Absatz a : artikellist){
			//a.MengeProzent = (double)a.Menge/(double)SumMenge;
			//a.MengeProzentKum = a.MengeProzent + prevMengeProzent;
			//prevMengeProzent = a.MengeProzentKum;
			//a.AnzahlProzent = (double)a.Anzahl/(double)SumAnzahl;
			//a.AnzahlProzentKum = a.AnzahlProzent + prevAnzahlProzent;
			//prevAnzahlProzent = a.AnzahlProzentKum;
			a.UmsatzProzent = (double)a.Umsatz/(double)SumUmsatz;
			
			a.UmsatzProzentKum = a.UmsatzProzent + prevUmsatzProzent;
			
			prevUmsatzProzent = a.UmsatzProzentKum;
			
			//System.out.println(i + " " + a.ArtikelNr + " " + a.Anzahl+  " " + a.AnzahlProzent + " " + a.AnzahlProzentKum);
			
			
			


			  
			  fw.append("\"" +  i + "\";\"" + a.ArtikelNr + "\";\"" + a.Umsatz + "\";\"" + a.UmsatzProzent + "\";\"" + a.UmsatzProzentKum + "\";");
			  fw.append( System.getProperty("line.separator") ); // e.g. "\n"

			i++;
		}
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
