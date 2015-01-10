package objects;

import java.sql.Date;

public class Absatz {
	public String ArtikelNr;
	public int LagerNr;
	public Date Datum;
	public int Menge;
	public int Anzahl;
	public double Umsatz;
	public double MengeProzent;
	public double MengeProzentKum;
	public String MengeABCKennzahl;
	public double AnzahlProzent;
	public double AnzahlProzentKum;
	public String AnzahlABCKennzahl;
	public double UmsatzProzent;
	public double UmsatzProzentKum;
	public String UmsatzABCKennzahl;
	public String ABCKennzahl;

	public String getArtikelNr() {
		return ArtikelNr;
	}

	public void setArtikelNr(String artikelNr) {
		ArtikelNr = artikelNr;
	}

	public int getLagerNr() {
		return LagerNr;
	}

	public void setLagerNr(int lagerNr) {
		LagerNr = lagerNr;
	}

	public Date getDatum() {
		return Datum;
	}

	public void setDatum(Date datum) {
		Datum = datum;
	}

	public int getMenge() {
		return Menge;
	}

	public void setMenge(int menge) {
		Menge = menge;
	}

	public int getAnzahl() {
		return Anzahl;
	}

	public void setAnzahl(int anzahl) {
		Anzahl = anzahl;
	}

	public double getUmsatz() {
		return Umsatz;
	}

	public void setUmsatz(double umsatz) {
		Umsatz = umsatz;
	}

	public double getMengeProzent() {
		return MengeProzent;
	}

	public void setMengeProzent(double mengeProzent) {
		MengeProzent = mengeProzent;
	}

	public double getMengeProzentKum() {
		return MengeProzentKum;
	}

	public void setMengeProzentKum(double mengeProzentKum) {
		MengeProzentKum = mengeProzentKum;
	}

	public String getMengeABCKennzahl() {
		return MengeABCKennzahl;
	}

	public void setMengeABCKennzahl(String mengeABCKennzahl) {
		MengeABCKennzahl = mengeABCKennzahl;
	}

	public double getAnzahlProzent() {
		return AnzahlProzent;
	}

	public void setAnzahlProzent(double anzahlProzent) {
		AnzahlProzent = anzahlProzent;
	}

	public double getAnzahlProzentKum() {
		return AnzahlProzentKum;
	}

	public void setAnzahlProzentKum(double anzahlProzentKum) {
		AnzahlProzentKum = anzahlProzentKum;
	}

	public String getAnzahlABCKennzahl() {
		return AnzahlABCKennzahl;
	}

	public void setAnzahlABCKennzahl(String anzahlABCKennzahl) {
		AnzahlABCKennzahl = anzahlABCKennzahl;
	}

	public double getUmsatzProzent() {
		return UmsatzProzent;
	}

	public void setUmsatzProzent(double umsatzProzent) {
		UmsatzProzent = umsatzProzent;
	}

	public double getUmsatzProzentKum() {
		return UmsatzProzentKum;
	}

	public void setUmsatzProzentKum(double umsatzProzentKum) {
		UmsatzProzentKum = umsatzProzentKum;
	}

	public String getUmsatzABCKennzahl() {
		return UmsatzABCKennzahl;
	}

	public void setUmsatzABCKennzahl(String umsatzABCKennzahl) {
		UmsatzABCKennzahl = umsatzABCKennzahl;
	}

	public String getABCKennzahl() {
		return ABCKennzahl;
	}

	public void setABCKennzahl(String aBCKennzahl) {
		ABCKennzahl = aBCKennzahl;
	}

}
