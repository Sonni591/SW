package logic;

import gui.MainWindow;
import gui.PanelParameter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import objects.ABCEinteilung;
import objects.ABCZuordnung;
import objects.Absatz;
import objects.Strings;
import datasource.CrudBefehle;
import datasource.CrudFunktionen;

public class ABCRechnung {
	private static Connection DBconnection = null;
	private ArrayList<Absatz> artikellist;
	private ArrayList<ABCZuordnung> zuordnungen;
	private int SumAnzahl = 0;
	private int SumMenge = 0;
	private double SumUmsatz = 0;

	public ABCRechnung(Connection _DBconnection) {
		DBconnection = _DBconnection;
	}

	public void start(/* Parameter f�r Auswahl */) {
		if (PanelParameter.rdbtnAlleVertriebskanle.isSelected()) {
			// Funktion wenn alle Kan�le gw�hlt wurde
			System.out.println("Alle Kan�le");
		} else {
			// Funktion wenn nur einer gew�hlt wurde
			String VtKanal = (String) PanelParameter.cboVertriebskanal.getSelectedItem();
			String Warengruppe = (String) PanelParameter.listWarengruppen.getSelectedValue();
			int lagernr = getVertriebsKanal(VtKanal);
			int wgnr = getWarenGruppe(Warengruppe);
			System.out.println(Warengruppe + wgnr);
			

		}

		// getAbsatzDaten(Strings.Umsatz);
		// GesamtABCBerechnung();
	}

	private ABCEinteilung getABCEinteilung(String kriterium) {
		ABCEinteilung abceinteilung = new ABCEinteilung();
		try {
			ResultSet einteilung = null;
			if (kriterium == Strings.Umsatz) {
				einteilung = CrudFunktionen.getResult(DBconnection,
						CrudBefehle.selectEinteilungUmsatz);
			} else if (kriterium == Strings.Menge) {
				einteilung = CrudFunktionen.getResult(DBconnection,
						CrudBefehle.selectEinteilungMenge);
			} else if (kriterium == Strings.Anzahl) {
				einteilung = CrudFunktionen.getResult(DBconnection,
						CrudBefehle.selectEinteilungAuftragsanzahl);
			} else {
				// FEHLER
			}
			while (einteilung.next()) {
				abceinteilung.Bezeichnung = einteilung.getString("Bezeichnung");
				abceinteilung.AnteilA = einteilung.getInt("AnteilA");
				abceinteilung.AnteilB = einteilung.getInt("AnteilB");
				abceinteilung.AnteilC = einteilung.getInt("AnteilC");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return abceinteilung;
	}

	private void setABCZuordnung() {
		int count = 0;
		zuordnungen = new ArrayList<ABCZuordnung>();
		try {
			ResultSet resultZuordnung = null;
			resultZuordnung = CrudFunktionen.getResult(DBconnection,
					CrudBefehle.selectABCZuordnung);
			while (resultZuordnung.next()) {
				ABCZuordnung abcZuordnung = new ABCZuordnung();
				abcZuordnung.Kriterium1 = resultZuordnung
						.getString("Kriterium1");
				abcZuordnung.Kriterium2 = resultZuordnung
						.getString("Kriterium2");
				abcZuordnung.Kriterium3 = resultZuordnung
						.getString("Kriterium3");
				abcZuordnung.Zuordnung = resultZuordnung.getString("Zuordnung");
				zuordnungen.add(abcZuordnung);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (Absatz a : artikellist) {
			count++;
			for (ABCZuordnung z : zuordnungen) {
				if (z.Kriterium1.compareTo(a.UmsatzABCKennzahl) == 0) {
					if (z.Kriterium2.compareTo(a.AnzahlABCKennzahl) == 0) {
						if (z.Kriterium3.compareTo(a.MengeABCKennzahl) == 0) {
							a.ABCKennzahl = z.Zuordnung;
							break;
						}
					}
				}
			}
		}
	}

	private void getAbsatzDaten(String orderBy) {
		artikellist = new ArrayList<Absatz>();
		ResultSet daten = null;
		if (orderBy == Strings.Umsatz) {
			daten = CrudFunktionen.getResult(DBconnection,
					CrudBefehle.selectAbsatzDatenOrderedByUmsatz);
		} else if (orderBy == Strings.Anzahl) {
			//
		} else if (orderBy == Strings.Menge) {
			//
		}
		try {
			while (daten.next()) {
				Absatz a = new Absatz();
				a.ArtikelNr = daten.getString("ArtikelNr");
				a.Umsatz = daten.getDouble("Gesamt Umsatz");
				SumUmsatz += a.Umsatz;
				a.Anzahl = daten.getInt("Gesamt Anzahl");
				SumAnzahl += a.Anzahl;
				a.Menge = daten.getInt("Gesamt Menge");
				SumMenge += a.Menge;
				artikellist.add(a);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void GesamtABCBerechnung() {
		ABCBerechnungUmsatz();
		Collections.sort(artikellist, new Comparator<Absatz>() {
			@Override
			public int compare(Absatz a1, Absatz a2) {
				return Integer.compare(a2.Menge, a1.Menge);
			}
		});
		ABCBerechnungMenge();
		Collections.sort(artikellist, new Comparator<Absatz>() {
			@Override
			public int compare(Absatz a1, Absatz a2) {
				return Integer.compare(a2.Anzahl, a1.Anzahl);
			}
		});
		ABCBerechnungAuftragsanzahl();
		setABCZuordnung();
		CrudFunktionen.updateABCResult(DBconnection, artikellist);
	}

	public void ABCBerechnungUmsatz() {
		ABCEinteilung abcEinteilung;
		abcEinteilung = getABCEinteilung(Strings.Umsatz);
		double prevUmsatzProzent = 0;
		for (Absatz a : artikellist) {
			a.UmsatzProzent = ((double) a.Umsatz / (double) SumUmsatz) * 100;
			a.UmsatzProzentKum = a.UmsatzProzent + prevUmsatzProzent;
			prevUmsatzProzent = a.UmsatzProzentKum;
			if (a.UmsatzProzentKum < abcEinteilung.AnteilA) {
				a.UmsatzABCKennzahl = "A";
			} else if (a.UmsatzProzentKum < abcEinteilung.AnteilA
					+ abcEinteilung.AnteilB) {
				a.UmsatzABCKennzahl = "B";
			} else {
				a.UmsatzABCKennzahl = "C";
			}
		}
	}

	public void ABCBerechnungMenge() {
		ABCEinteilung abcEinteilung;
		abcEinteilung = getABCEinteilung(Strings.Menge);
		double prevMengeProzent = 0;
		for (Absatz a : artikellist) {
			a.MengeProzent = ((double) a.Menge / (double) SumMenge) * 100;
			a.MengeProzentKum = a.MengeProzent + prevMengeProzent;
			prevMengeProzent = a.MengeProzentKum;
			if (a.MengeProzentKum < abcEinteilung.AnteilA) {
				a.MengeABCKennzahl = "A";
			} else if (a.MengeProzentKum < abcEinteilung.AnteilA
					+ abcEinteilung.AnteilB) {
				a.MengeABCKennzahl = "B";
			} else {
				a.MengeABCKennzahl = "C";
			}
		}

	}

	public void ABCBerechnungAuftragsanzahl() {
		ABCEinteilung abcEinteilung;
		abcEinteilung = getABCEinteilung(Strings.Anzahl);
		double prevAnzahlProzent = 0;
		for (Absatz a : artikellist) {
			a.AnzahlProzent = ((double) a.Anzahl / (double) SumAnzahl) * 100;
			a.AnzahlProzentKum = a.AnzahlProzent + prevAnzahlProzent;
			prevAnzahlProzent = a.AnzahlProzentKum;
			if (a.AnzahlProzentKum < abcEinteilung.AnteilA) {
				a.AnzahlABCKennzahl = "A";
			} else if (a.AnzahlProzentKum < abcEinteilung.AnteilA
					+ abcEinteilung.AnteilB) {
				a.AnzahlABCKennzahl = "B";
			} else {
				a.AnzahlABCKennzahl = "C";
			}
		}
	}

	public int getVertriebsKanal(String VertriebsKanalString) {
		if (VertriebsKanalString.equals("L�beck")) {
			return 20;
		} else if (VertriebsKanalString.equals("Kiel")) {
			return 30;
		} else if (VertriebsKanalString.equals("Flensburg")) {
			return 10;
		} else
			return 0;
	}
	public int getWarenGruppe(String Warengruppe){
		ResultSet daten = null;
			daten = CrudFunktionen.getResult(DBconnection,CrudBefehle.selectWarengruppen);
		try {
			while (daten.next()) {
				if(daten.getString("Bezeichnung").equals(Warengruppe)){
					return daten.getInt("WGNr");
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
