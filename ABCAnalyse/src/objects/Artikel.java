package objects;

public class Artikel {
	public String ArtikelNr;
	public String Bezeichnung;
	public double EKPreis;
	public int WGNr;

	public String getArtikelNr() {
		return ArtikelNr;
	}

	public void setArtikelNr(String artikelNr) {
		ArtikelNr = artikelNr;
	}

	public String getBezeichnung() {
		return Bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		Bezeichnung = bezeichnung;
	}

	public double getEKPreis() {
		return EKPreis;
	}

	public void setEKPreis(double eKPreis) {
		EKPreis = eKPreis;
	}

	public int getWGNr() {
		return WGNr;
	}

	public void setWGNr(int wGNr) {
		WGNr = wGNr;
	}

}
