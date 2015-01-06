package sqliteRepository;

public class CrudBefehle {

	//Bereich für Select-Befehle
	public static String selectABCEinteilung = "select * from ABCEinteilung order by Bezeichnung asc";
	public static String selectABCZuordnung = "select * from ABCZuordnung order by Kriterium1,Kriterium2,Kriterium3 asc";
	public static String selectArtikelABCZuordnung = "select Zuordnung from ABCZuordnung where Kriterium1 = ? AND Kriterium2 = ? AND Kriterium3 = ?";
	public static String selectAbsatz = "select * from Absatz";
	public static String selectABCResultView = "select abcR.artikelNr, wg.Bezeichnung, l.Bezeichnung, abcR.ABCK3 as 'Auf.Anz.', abcI.JahresAnzahl, abcR.ABCK2 as 'Auf.Mng', abcI.JahresMenge,"
											 + "abcR.ABCK1 as 'Ums.', abcI.JahresUmsatz, abcR.ABCKZ as 'ABC Zuo.' "
											 + " from ABCResult abcR"
											 + " join ABC_Input abcI on abcI.artikelNr = abcR.artikelNr and abcI.lagerNr = abcR.lagerNr"
											 + " join Warengruppe wg on wg.wgNr = abcI.wgNr"
											 + " join Lager l on l.lagerNr = abcR.lagerNr;";
	public static String selectVertriebskanaele = "select * from Lager order by Bezeichnung asc";
	public static String selectWarengruppen = "select * from Warengruppe order by Bezeichnung asc";
	
	public static String selectSumOfCriteriaByStorehouseAndWaregroup = "select sum(JahresUmsatz) as SummeUmsatz, sum(JahresMenge) as SummeMenge, sum(JahresAnzahl) as SummeAnzahl from ABC_Input where LagerNr = ? and WGNr = ?";
	public static String selectABCInputByStorehouseAndWaregroupOrderCriteria = "select * from ABC_Input where LagerNr = ? and WGNr = ? order by ";
	
	public static String selectVertriebskanalID = "select LagerNr from Lager where Bezeichnung = ?";
	public static String selectWarengruppeID = "select WgNr from Warengruppe where Bezeichnung = ?";
	
	//Bereich Update-Befehle
	public static String updateEinteilungAnteilA = "update " + "ABCEinteilung set AnteilA = ? where Bezeichnung = ?";
	public static String updateEinteilungAnteilB = "update " + "ABCEinteilung set AnteilB = ? where Bezeichnung = ?";
	public static String updateEinteilungAnteilC = "update " + "ABCEinteilung set AnteilC = ? where Bezeichnung = ?";
	
	public static String updateABCZuordnung = "update ABCZuordnung set Zuordnung = ? where Kriterium1 = ? and Kriterium2 = ? and Kriterium3 = ?";
	
	//Bereich Insert-Befehle
	public static String insertIntoABCResult = "INSERT INTO ABCResult VALUES (?, ?, ?, ?, ?, ?)";
	
	//ABC-Berechnung Selects
	public static String insertABCInputValues = "insert into ABC_Input "
											    + "select ab.artikelnr,"
												+ "ab.lagernr,"
												+ "sum(ab.Umsatz) as JahresUmsatz,"
												+ "sum(ab.Menge) as JahresMenge,"
												+ "sum(ab.Anzahl) as JahresAnzahl,"
												+ "al.Bestand as Bestand,"
												+ "a.wgnr"
												+ " from Absatz ab"
												+ " join Artikel a on a.artikelnr = ab.artikelnr"
												+ " join artikelLager al on al.artikelnr = a.artikelnr and al.LagerNr = ab.LagerNr"
												+ " where ab.datum between ? and ?"
												+ " group by ab.artikelnr, ab.lagernr, a.wgnr, al.Bestand"
												+ " order by a.wgnr;";
	
	public static String insertWholeCompanyInput = "INSERT INTO ABC_Input (ArtikelNr, LagerNr, JahresUmsatz, JahresMenge, JahresAnzahl, Bestand, WgNr) "
												 + "select ArtikelNr, 0, sum(JahresUmsatz), sum(JahresMenge), sum(JahresAnzahl), "
												 + "sum(Bestand), WgNr "
												 + "from ABC_Input "
												 + "group by ArtikelNr,WgNr";
	
	public static String generateABCBerichte = "INSERT INTO ABCBerichte "
											 + "select abcR.ABCK1 as BerichtKZ, 1 as KriteriumID, abcR.LagerNr as LagerNr, abcI.WgNr as WGNR, count(abcR.ArtikelNr) as ANZAHL,"
											 + "sum(abcI.JahresUmsatz) as UMSATZ, sum(abcI.JahresMenge) as MENGE, sum(abcI.Bestand) as BESTAND, "
											 + "sum((abcI.JahresMenge * EKPreis)) as MengeWert, sum((abcI.Bestand * a.EKPreis)) as BestandsWert "
											 + "from ABC_Input abcI "
											 + "join ABCResult abcR on abcI.ArtikelNr = abcR.ArtikelNr and abcI.LagerNr = abcR.LagerNr "
											 + "join Artikel a on a.ArtikelNr = abcR.ArtikelNr "
											 + "where abcR.LagerNr in (?) "
											 + "  and abcI.WgNr in (?)"
											 + "group by abcR.ABCK1, 1,abcR.LagerNr, abcI.WgNr "
											 + "UNION "
											 + "select abcR.ABCK2 as BerichtKZ, 2 as KriteriumID, abcR.LagerNr as LagerNr, abcI.WgNr as WGNR, count(abcR.ArtikelNr) as ANZAHL, "
											 + "sum(abcI.JahresUmsatz) as UMSATZ, sum(abcI.JahresMenge) as MENGE, sum(abcI.Bestand) as BESTAND, " 
											 + "sum((abcI.JahresMenge * EKPreis)) as MengeWert, sum((abcI.Bestand * a.EKPreis)) as BestandsWert "
											 + "from ABC_Input abcI "
											 + "join ABCResult abcR on abcI.ArtikelNr = abcR.ArtikelNr and abcI.LagerNr = abcR.LagerNr "
											 + "join Artikel a on a.ArtikelNr = abcR.ArtikelNr "
											 + "where abcR.LagerNr in (?) "
											 + "  and abcI.WgNr in (?)"
											 + "group by abcR.ABCK2, 2,abcR.LagerNr, abcI.WgNr "
											 + "UNION "
											 + "select abcR.ABCK3 as BerichtKZ, 3 as KriteriumID, abcR.LagerNr as LagerNr, abcI.WgNr as WGNR, count(abcR.ArtikelNr) as ANZAHL, "
											 + "sum(abcI.JahresUmsatz) as UMSATZ, sum(abcI.JahresMenge) as MENGE, sum(abcI.Bestand) as BESTAND, "
											 + "sum((abcI.JahresMenge * EKPreis)) as MengeWert, sum((abcI.Bestand * a.EKPreis)) as BestandsWert "
											 + "from ABC_Input abcI "
											 + "join ABCResult abcR on abcI.ArtikelNr = abcR.ArtikelNr and abcI.LagerNr = abcR.LagerNr "
											 + "join Artikel a on a.ArtikelNr = abcR.ArtikelNr "
											 + "where abcR.LagerNr in (?) "
											 + "  and abcI.WgNr in (?)"
											 + "group by abcR.ABCK3, 3,abcR.LagerNr, abcI.WgNr "
											 + "order by KriteriumID ";
	
	public static String generateSUMBerichteLine = "INSERT INTO ABCBerichte "
												 + "select 'SUM' as BerichtKZ, abcB.KriteriumID, abcB.LagerNr, abcB.WgNr as WGNR, sum(abcB.AnzahlArtikel) as ANZAHL, " 
												 + "sum(abcB.JahresUmsatz) as UMSATZ, sum(abcB.JahresMenge) as MENGE, sum(abcB.Bestand) as BESTAND, "
												 + "sum(abcB.JahresMengeWert) as MengeWert, sum(abcB.BestandsWert) as BestandsWert "
												 + "from ABCBerichte abcB "
												 + "where abcB.LagerNr in (?) "
												 + "  and abcB.WgNr in (?)"
												 + "group by abcB.KriteriumID, abcB.LagerNr, abcB.WgNr;";
	
	//Bereich Delete-Befehle
	public static String deleteABCInput = "delete from ABC_Input";
	public static String deleteABCResult = "delete from ABCResult";
	public static String deleteABCBerichte = "delete from ABCBerichte";
	
	//Bereich Chart-Befehle
	public static String getChartOption1 = "select AnzahlArtikel, Bestandswert, JahresUmsatz, BerichtKZ, LagerNr, WgNr From ABCBerichte where LagerNr = ? AND WgNr = ?";
	public static String getChartOption2 = "select AnzahlArtikel, Bestand, JahresMenge, BerichtKZ, LagerNr, WgNr From ABCBerichte where LagerNr = ? AND WgNr = ?";
	public static String getChartOption3 = "select AnzahlArtikel, Bestandswert, JahresUmsatz, BerichtKZ, LagerNr, WgNr From ABCBerichte where LagerNr = ? AND WgNr = ?";
	
	public static String getTableOption1 = "select BerichtKZ, AnzahlArtikel, Bestandswert, JahresUmsatz From ABCBerichte where LagerNr = ? AND WgNr = ? AND KriteriumID = 1";
	public static String getTableOption2 = "select BerichtKZ, AnzahlArtikel, Bestand, JahresMenge From ABCBerichte where LagerNr = ? AND WgNr = ? AND KriteriumID = 3";
	public static String getTableOption3 = "select BerichtKZ, AnzahlArtikel, Bestandswert, JahresUmsatz, BerichtKZ, LagerNr, WgNr From ABCBerichte where LagerNr = ? AND WgNr = ?";
	
	public static String getWgId = "select WGNr from Warengruppe where bezeichnung = ?";
	public static String getLagerId = "select lagernr from lager where bezeichnung = ? "; 
	
	public static String getABCBericht = "select * from ABCBerichte";
	
}
