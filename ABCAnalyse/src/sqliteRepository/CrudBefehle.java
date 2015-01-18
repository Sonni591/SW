package sqliteRepository;

public class CrudBefehle {

	//Bereich für Select-Befehle
	public static String selectABCEinteilung = "select * from ABCEinteilung order by Bezeichnung asc";
	public static String selectABCZuordnung = "select * from ABCZuordnung order by Kriterium1,Kriterium2,Kriterium3 asc";
	public static String selectArtikelABCZuordnung = "select Zuordnung from ABCZuordnung where Kriterium1 = ? AND Kriterium2 = ? AND Kriterium3 = ?";
	public static String selectAbsatz = "select * from Absatz";
	public static String selectABCResultView = "select abcR.artikelNr, wg.Bezeichnung as 'Warengruppe', l.Bezeichnung as 'Vertriebskanal', abcR.ABCK3 as 'Auf.Anz.', abcI.JahresAnzahl, abcR.ABCK2 as 'Auf.Mng', abcI.JahresMenge,"
											 + "abcR.ABCK1 as 'Ums', round(abcI.JahresUmsatz,2) as JahresUmsatz, abcR.ABCKZ as 'ABC Zuo.', abcI.Bestand as 'Bestand' "
											 + " from ABCResult abcR"
											 + " join ABC_Input abcI on abcI.artikelNr = abcR.artikelNr and abcI.lagerNr = abcR.lagerNr"
											 + " join Warengruppe wg on wg.wgNr = abcI.wgNr"
											 + " join Lager l on l.lagerNr = abcR.lagerNr;";
	public static String selectVertriebskanaele = "select * from Lager order by Bezeichnung asc";
	public static String selectWarengruppen = "select * from Warengruppe order by Bezeichnung asc";
	
	public static String selectVertriebskanaeleBerichte = "select distinct l.Bezeichnung "
														+ "	from ABCBerichte abcR "
														+ "join Lager l on abcR.LagerNr = l.LagerNr order by Bezeichnung asc";
	public static String selectWarengruppenBerichte = "select distinct wg.Bezeichnung "
													+ "	from ABCBerichte abcR "
													+ "join Warengruppe wg on abcR.WGNr = wg.WGNr order by Bezeichnung asc";
	
	
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
	
	public static String insertDArtikelInput = "insert into ABC_Input (ArtikelNr, LagerNr, JahresUmsatz, JahresMenge, JahresAnzahl, Bestand, WgNr)"
												+ " select a.ArtikelNr, al.LagerNr, 0, 0, 0, al.Bestand, a.WgNr"
												+ " from Artikel a"
												+ " join ArtikelLager al on a.ArtikelNr = al.ArtikelNr"
												+ " where a.ArtikelNr not in ( select a.ArtikelNr from ABC_Input )";	
	
//	public static String insertDArtikelInputGesamtunternehmen = "insert into ABC_Input (ArtikelNr, LagerNr, JahresUmsatz, JahresMenge, JahresAnzahl, Bestand, WgNr)"
//												+ " select a.ArtikelNr, 0, 0, 0, 0, al.Bestand, a.WgNr"
//												+ " from Artikel a"
//												+ " join ArtikelLager al on a.ArtikelNr = al.ArtikelNr"
//												+ " where a.ArtikelNr not in ( select a.ArtikelNr from ABC_Input ai and ai.LagerNr <> 0)";
	
	public static String insertDArtikelInputGesamtunternehmen = "insert into ABC_Input (ArtikelNr, LagerNr, JahresUmsatz, JahresMenge, JahresAnzahl, Bestand, WgNr)"
																+ " select ai.ArtikelNr, 0, 0, 0, 0, sum(ai.Bestand), ai.WgNr"
																+ " from ABC_Input ai"
																+ " where JahresUmsatz = 0 and JahresMenge = 0 and JahresAnzahl = 0"
																+ " and ai.LagerNr <> 0"
																+ " and not exists  (select ai2.ArtikelNr "
																+ "						from ABC_Input ai2 "
																+ "						where ai2.ArtikelNr = ai.ArtikelNr "
																+ "						and ai2.LagerNr = 0)"
																+ " group by ai.ArtikelNr, ai.WgNr"
																+ " order by ai.ArtikelNr, ai.WgNr";
	
	public static String insertDArtikelResult = "insert into ABCResult (ArtikelNr, LagerNr, ABCK1, ABCK2, ABCK3, ABCKZ)"
												+ " select a.ArtikelNr, al.LagerNr, 'D', 'D', 'D', 'D'"
												+ " from Artikel a"
												+ " join ArtikelLager al on a.ArtikelNr = al.ArtikelNr"
												+ " where a.ArtikelNr not in ( select a.ArtikelNr from ABC_Input )";
	
//	public static String insertDArtikelResultGesamtunternehmen = "insert into ABCResult (ArtikelNr, LagerNr, ABCK1, ABCK2, ABCK3, ABCKZ)"
//												+ " select distinct a.ArtikelNr, 0, 'D', 'D', 'D', 'D'"
//												+ " from Artikel a"
//												+ " join ArtikelLager al on a.ArtikelNr = al.ArtikelNr"
//												+ " where a.ArtikelNr not in ( select a.ArtikelNr from ABC_Input ai )";
	
	public static String insertDArtikelResultGesamtunternehmen  = "insert into ABCResult (ArtikelNr, LagerNr, ABCK1, ABCK2, ABCK3, ABCKZ)"
												+ " select ArtikelNr, 0, 'D', 'D', 'D', 'D'" 
												+ " from ABC_Input ai"
												+ " where LagerNr = 0 and JahresUmsatz = 0 and JahresMenge = 0 and JahresAnzahl = 0"
												+ " and not exists  (select ai.ArtikelNr "
												+ "						from ABCResult ar "
												+ "						where ai.ArtikelNr = ar.ArtikelNr "
												+ "						and ar.LagerNr = 0)";
 
	
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
	
	public static String generateABCBerichteWGAlle = "Insert INTO ABCBerichte "
												+ " select abcB.BerichtKZ, abcB.KriteriumID, abcB.LagerNr, 0 as WGNr, "
												+ " sum(abcB.AnzahlArtikel), sum(abcB.JahresUmsatz), sum(abcB.JahresMenge), sum(abcB.Bestand), sum(abcB.JahresMengeWert), sum(abcB.BestandsWert) "
												+ " from ABCBerichte abcB"
//												+ " where BerichtKZ <> 'SUM' "
												+ " group by abcB.BerichtKZ, abcB.KriteriumID, abcB.LagerNr ";
	
	//Bereich Delete-Befehle
	public static String deleteABCInput = "delete from ABC_Input";
	public static String deleteABCResult = "delete from ABCResult";
	public static String deleteABCBerichte = "delete from ABCBerichte";
//	public static String deleteFalscheDArtikel = "delete from ABC_Input where LagerNr = 0 and JahresUmsatz = 0 and JahresMenge = 0 and JahresAnzahl = 0";
	
	//Bereich Chart-Befehle
	public static String getChartData = "select BerichtKZ, KriteriumID, LagerNr, WgNr, AnzahlArtikel, round(JahresUmsatz,2) as 'JahresUmsatz', JahresMenge, Bestand, JahresMengeWert, Bestandswert"
										+ " FROM ABCBerichte"
										+ " WHERE LagerNr = ? AND WgNr = ? and KriteriumID = ?";
	
	public static String getWgId = "select WGNr from Warengruppe where bezeichnung = ?";
	public static String getLagerId = "select lagernr from lager where bezeichnung = ? ";
	
}
