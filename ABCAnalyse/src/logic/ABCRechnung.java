package logic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DBObjects.Artikel;
import datasource.CrudBefehle;
import datasource.CrudFunktionen;
import datasource.DBConnector;

public class ABCRechnung {
	private static Connection DBconnection = null;
	private ArrayList<Artikel> artikellist;
	
	public void getData(Connection _DBconnection){
		DBconnection = _DBconnection;
		artikellist = new ArrayList();
		try {
			ResultSet daten = CrudFunktionen.getResult(DBconnection, CrudBefehle.selectDaten);
			
			while (daten.next()) {
				Artikel a = new Artikel();
				a.Bezeichnung = daten.getString("Bezeichnung");
				a.ArtikelNr = daten.getString("ArtikelNr");
				//a.EKPreis = daten.get
				a.WGNr = daten.getInt("WGNr");
				artikellist.add(a);
			}
			int i = 0;
			System.out.println("-------");
			for(Artikel a: artikellist){
				System.out.println(a.ArtikelNr + " " + a.Bezeichnung + " "  + a.EKPreis + "\n");
				i++;
				if(i>100)
					break;
			}
			ResultSet d = CrudFunktionen.getResult(DBconnection, CrudBefehle.test);
			
		} catch(SQLException e) {
			e.printStackTrace();
		}

		
			
	}

}
