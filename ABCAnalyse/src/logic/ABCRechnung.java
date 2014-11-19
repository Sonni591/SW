package logic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



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
	
	public void getData(){
		
		artikellist = new ArrayList<Absatz>();
		try {
			ResultSet daten = CrudFunktionen.getResult(DBconnection, CrudBefehle.selectAbsatz);
			
			while (daten.next()) {
				Absatz a = new Absatz();
				a.Anzahl = daten.getInt("Anzahl");
				a.ArtikelNr = daten.getString("ArtikelNr");
				a.Datum = daten.getDate("Datum");
				a.LagerNr = daten.getInt("LagerNr");
				a.Menge = daten.getInt("Menge");
				a.Umsatz = daten.getDouble("Umsatz");
				
				SumAnzahl += a.Anzahl;
				SumMenge += a.Menge;
				SumUmsatz += a.Umsatz;
				artikellist.add(a);
				
				
			}			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		double prevMengeProzent = 0;
		double prevAnzahlProzent = 0;
		double prevUmsatzProzent = 0;
		int i = 0;
		for(Absatz a : artikellist){
			a.MengeProzent = (double)a.Menge/(double)SumMenge;
			a.MengeProzentKum = a.MengeProzent + prevMengeProzent;
			prevMengeProzent = a.MengeProzentKum;
			a.AnzahlProzent = (double)a.Anzahl/(double)SumAnzahl;
			a.AnzahlProzentKum = a.AnzahlProzent + prevAnzahlProzent;
			prevAnzahlProzent = a.AnzahlProzentKum;
			a.UmsatzProzent = (double)a.Umsatz/(double)SumUmsatz;
			a.UmsatzProzentKum = a.UmsatzProzent + prevUmsatzProzent;
			prevUmsatzProzent = a.UmsatzProzentKum;
			
			System.out.println(i + " " + a.ArtikelNr + " " + a.Anzahl+  " " + a.AnzahlProzent + " " + a.AnzahlProzentKum);
			i++;
		}

		
			
	}

}
