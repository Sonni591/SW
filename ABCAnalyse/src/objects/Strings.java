package objects;

public class Strings {
	// Strings DatenbankSpalten
	public static String Umsatz = "Umsatz";
	public static String Anzahl = "Anzahl";
	public static String Menge = "Menge";
	private static String Auftragsanzahl = "Auftragsanzahl";
	private static String Bezeichnung = "Bezeichnung";

	// Strings MainWindows.java
	public static String Parameter = "Parameter";
	public static String ABC_Einteilung = "ABC Einteilung";
	public static String ABC_Zuordnung = "ABC Zuordnung";
	public static String Ergebnis = "Ergebnis";

	// Strings PanelParameter.java
	public static String Von = "Von:";
	public static String Bis = "Bis:";
	public static String Zeitraum = "Zeitraum:";
	private static String Jahr = "Jahr: ";
	private static String ABC_Berichte_generiern = "ABC Berichte generiern";
	private static String ABC_Analyse_berechnen = "ABC Analyse berechnen";
	private static String Vertriebskanal = "Vertriebskanal:";
	private static String Alle_Vertriebskanaele = "Alle Vertriebskanäle";
	private static String Nur_folgenden_Vertriebskanal = "Nur folgenden Vertriebskanal";
	private static String Warengruppe = "Warengruppe:";
	private static String Alle_Warengruppen = "Alle Warengruppen";
	private static String Nur_folgende_Warengruppe = "Nur folgende Warengruppe";
	private static String starte_generierung_der_Berichte = "starte generierung der Berichte";
	private static String ABC_Berichte_generiert = "ABC Berichte generiert";
	private static String Bitte_einen_Zeitraum_einschraenken = "Bitte einen Zeitraum einschränken!";
	private static String Fehlerhafter_Zeitraum = "Fehlerhafter Zeitraum";

	// Strings PanelEinteilung.java
	private static String lbl1str = "<html>Bitte tragen Sie die prozentualen Grenzen &nbsp&nbsp&nbsp<p/> für die Einteilung in die Kategorien ein.</html>";
	private static String Speichern = "Speichern";
	private static String MsgError1 = "Speichern wegen ungültigen Werten nicht möglich.";
	private static String MsgError2 = "Speichern fehlgeschlagen";
	private static String MsgSchwellwertSuccess1 = "Die ABC Schwellwerte wurden aktualisiert";
	private static String MsgSchwellwertSuccess2 = "Schwellwerte aktualisiert";
	private static String MsgSchwellwertFail1 = "Die Umsatz Schwellwerte übersteigen die 100%";
	private static String MsgSchwellwertFail2 = "Schwellwerte zu hoch";
	private static String MsgInputFail1 = "Bitte geben Sie eine Zahl ein. Zeichen werden nicht akzeptiert!";
	private static String MsgInputFail2 = "Fehlerhafte Eingabe";
	
	//Only Getter and Setter from here on
	public static String getUmsatz() {
		return Umsatz;
	}
	public static void setUmsatz(String umsatz) {
		Umsatz = umsatz;
	}
	public static String getAnzahl() {
		return Anzahl;
	}
	public static void setAnzahl(String anzahl) {
		Anzahl = anzahl;
	}
	public static String getMenge() {
		return Menge;
	}
	public static void setMenge(String menge) {
		Menge = menge;
	}
	public static String getAuftragsanzahl() {
		return Auftragsanzahl;
	}
	public static void setAuftragsanzahl(String auftragsanzahl) {
		Auftragsanzahl = auftragsanzahl;
	}
	public static String getBezeichnung() {
		return Bezeichnung;
	}
	public static void setBezeichnung(String bezeichnung) {
		Bezeichnung = bezeichnung;
	}
	public static String getParameter() {
		return Parameter;
	}
	public static void setParameter(String parameter) {
		Parameter = parameter;
	}
	public static String getABC_Einteilung() {
		return ABC_Einteilung;
	}
	public static void setABC_Einteilung(String aBC_Einteilung) {
		ABC_Einteilung = aBC_Einteilung;
	}
	public static String getABC_Zuordnung() {
		return ABC_Zuordnung;
	}
	public static void setABC_Zuordnung(String aBC_Zuordnung) {
		ABC_Zuordnung = aBC_Zuordnung;
	}
	public static String getErgebnis() {
		return Ergebnis;
	}
	public static void setErgebnis(String ergebnis) {
		Ergebnis = ergebnis;
	}
	public static String getVon() {
		return Von;
	}
	public static void setVon(String von) {
		Von = von;
	}
	public static String getBis() {
		return Bis;
	}
	public static void setBis(String bis) {
		Bis = bis;
	}
	public static String getZeitraum() {
		return Zeitraum;
	}
	public static void setZeitraum(String zeitraum) {
		Zeitraum = zeitraum;
	}
	public static String getJahr() {
		return Jahr;
	}
	public static void setJahr(String jahr) {
		Jahr = jahr;
	}
	public static String getABC_Berichte_generiern() {
		return ABC_Berichte_generiern;
	}
	public static void setABC_Berichte_generiern(String aBC_Berichte_generiern) {
		ABC_Berichte_generiern = aBC_Berichte_generiern;
	}
	public static String getABC_Analyse_berechnen() {
		return ABC_Analyse_berechnen;
	}
	public static void setABC_Analyse_berechnen(String aBC_Analyse_berechnen) {
		ABC_Analyse_berechnen = aBC_Analyse_berechnen;
	}
	public static String getVertriebskanal() {
		return Vertriebskanal;
	}
	public static void setVertriebskanal(String vertriebskanal) {
		Vertriebskanal = vertriebskanal;
	}
	public static String getAlle_Vertriebskanaele() {
		return Alle_Vertriebskanaele;
	}
	public static void setAlle_Vertriebskanaele(String alle_Vertriebskanaele) {
		Alle_Vertriebskanaele = alle_Vertriebskanaele;
	}
	public static String getNur_folgenden_Vertriebskanal() {
		return Nur_folgenden_Vertriebskanal;
	}
	public static void setNur_folgenden_Vertriebskanal(
			String nur_folgenden_Vertriebskanal) {
		Nur_folgenden_Vertriebskanal = nur_folgenden_Vertriebskanal;
	}
	public static String getWarengruppe() {
		return Warengruppe;
	}
	public static void setWarengruppe(String warengruppe) {
		Warengruppe = warengruppe;
	}
	public static String getAlle_Warengruppen() {
		return Alle_Warengruppen;
	}
	public static void setAlle_Warengruppen(String alle_Warengruppen) {
		Alle_Warengruppen = alle_Warengruppen;
	}
	public static String getNur_folgende_Warengruppe() {
		return Nur_folgende_Warengruppe;
	}
	public static void setNur_folgende_Warengruppe(String nur_folgende_Warengruppe) {
		Nur_folgende_Warengruppe = nur_folgende_Warengruppe;
	}
	public static String getStarte_generierung_der_Berichte() {
		return starte_generierung_der_Berichte;
	}
	public static void setStarte_generierung_der_Berichte(
			String starte_generierung_der_Berichte) {
		Strings.starte_generierung_der_Berichte = starte_generierung_der_Berichte;
	}
	public static String getABC_Berichte_generiert() {
		return ABC_Berichte_generiert;
	}
	public static void setABC_Berichte_generiert(String aBC_Berichte_generiert) {
		ABC_Berichte_generiert = aBC_Berichte_generiert;
	}
	public static String getBitte_einen_Zeitraum_einschraenken() {
		return Bitte_einen_Zeitraum_einschraenken;
	}
	public static void setBitte_einen_Zeitraum_einschraenken(
			String bitte_einen_Zeitraum_einschraenken) {
		Bitte_einen_Zeitraum_einschraenken = bitte_einen_Zeitraum_einschraenken;
	}
	public static String getFehlerhafter_Zeitraum() {
		return Fehlerhafter_Zeitraum;
	}
	public static void setFehlerhafter_Zeitraum(String fehlerhafter_Zeitraum) {
		Fehlerhafter_Zeitraum = fehlerhafter_Zeitraum;
	}
	public static String getLbl1str() {
		return lbl1str;
	}
	public static void setLbl1str(String lbl1str) {
		Strings.lbl1str = lbl1str;
	}
	public static String getSpeichern() {
		return Speichern;
	}
	public static void setSpeichern(String speichern) {
		Speichern = speichern;
	}
	public static String getMsgError1() {
		return MsgError1;
	}
	public static void setMsgError1(String msgError1) {
		MsgError1 = msgError1;
	}
	public static String getMsgError2() {
		return MsgError2;
	}
	public static void setMsgError2(String msgError2) {
		MsgError2 = msgError2;
	}
	public static String getMsgSchwellwertSuccess1() {
		return MsgSchwellwertSuccess1;
	}
	public static void setMsgSchwellwertSuccess1(String msgSchwellwertSuccess1) {
		MsgSchwellwertSuccess1 = msgSchwellwertSuccess1;
	}
	public static String getMsgSchwellwertSuccess2() {
		return MsgSchwellwertSuccess2;
	}
	public static void setMsgSchwellwertSuccess2(String msgSchwellwertSuccess2) {
		MsgSchwellwertSuccess2 = msgSchwellwertSuccess2;
	}
	public static String getMsgSchwellwertFail1() {
		return MsgSchwellwertFail1;
	}
	public static void setMsgSchwellwertFail1(String msgSchwellwertFail1) {
		MsgSchwellwertFail1 = msgSchwellwertFail1;
	}
	public static String getMsgSchwellwertFail2() {
		return MsgSchwellwertFail2;
	}
	public static void setMsgSchwellwertFail2(String msgSchwellwertFail2) {
		MsgSchwellwertFail2 = msgSchwellwertFail2;
	}
	public static String getMsgInputFail1() {
		return MsgInputFail1;
	}
	public static void setMsgInputFail1(String msgInputFail1) {
		MsgInputFail1 = msgInputFail1;
	}
	public static String getMsgInputFail2() {
		return MsgInputFail2;
	}
	public static void setMsgInputFail2(String msgInputFail2) {
		MsgInputFail2 = msgInputFail2;
	}
	
	
}
	

	