package logic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import DBObjects.Artikel;
import datasource.CrudBefehle;
import datasource.CrudFunktionen;


public class ABCRechnung {
	private static Connection DBconnection = null;
	private ArrayList<Artikel> artikellist;
	
	public void getData(Connection _DBconnection){
		DBconnection = _DBconnection;
		artikellist = new ArrayList<Artikel>();
		try {
			ResultSet daten = CrudFunktionen.getResult(DBconnection, CrudBefehle.selectDaten);
			
			while (daten.next()) {
				Artikel a = new Artikel();
				a.Bezeichnung = daten.getString("Bezeichnung");
				a.ArtikelNr = daten.getString("ArtikelNr");
				a.EKPreis = (daten.getDouble("EKPreis"));
				a.WGNr = daten.getInt("WGNr");
				artikellist.add(a);
			}			
		} catch(SQLException e) {
			e.printStackTrace();
		}

		
			
	}

}
