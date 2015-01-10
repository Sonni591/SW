package objects;

public class Bericht {
	private String berichtKZ;
	private int lagerNr;
	private int wgNr;
	private double anzahlArtikel;
	private double jahresUmsatz;
	private double jahresMenge;
	private double bestand;
	private double jahresMengeWert;
	private double bestandsWert;

	public String getBerichtKZ() {
		return berichtKZ;
	}

	public void setBerichtKZ(String berichtKZ) {
		this.berichtKZ = berichtKZ;
	}

	public int getLagerNr() {
		return lagerNr;
	}

	public void setLagerNr(int lagerNr) {
		this.lagerNr = lagerNr;
	}

	public int getWgNr() {
		return wgNr;
	}

	public void setWgNr(int wgNr) {
		this.wgNr = wgNr;
	}

	public double getAnzahlArtikel() {
		return anzahlArtikel;
	}

	public void setAnzahlArtikel(double anzahlArtikel) {
		this.anzahlArtikel = anzahlArtikel;
	}

	public double getJahresUmsatz() {
		return jahresUmsatz;
	}

	public void setJahresUmsatz(double jahresUmsatz) {
		this.jahresUmsatz = jahresUmsatz;
	}

	public double getJahresMenge() {
		return jahresMenge;
	}

	public void setJahresMenge(double jahresMenge) {
		this.jahresMenge = jahresMenge;
	}

	public double getBestand() {
		return bestand;
	}

	public void setBestand(double bestand) {
		this.bestand = bestand;
	}

	public double getJahresMengeWert() {
		return jahresMengeWert;
	}

	public void setJahresMengeWert(double jahresMengeWert) {
		this.jahresMengeWert = jahresMengeWert;
	}

	public double getBestandsWert() {
		return bestandsWert;
	}

	public void setBestandsWert(double bestandsWert) {
		this.bestandsWert = bestandsWert;
	}

}
