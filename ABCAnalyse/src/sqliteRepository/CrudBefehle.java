package sqliteRepository;

public class CrudBefehle {

	//Bereich f��r Select-Befehle
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
	public static String selectABCInputByStorehouseAndWaregroupOrderCriteria = "select * from ABC_Input where LagerNr in (?) and WGNr = ? order by ";
	
	//Bereich Update-Befehle
	public static String updateEinteilungAnteilA = "update " + "ABCEinteilung set AnteilA = ? where Bezeichnung = ?";
	public static String updateEinteilungAnteilB = "update " + "ABCEinteilung set AnteilB = ? where Bezeichnung = ?";
	public static String updateEinteilungAnteilC = "update " + "ABCEinteilung set AnteilC = ? where Bezeichnung = ?";
	
	public static String updateABCZuordnung = "update ABCZuordnung set Zuordnung = ? where Kriterium1 = ? and Kriterium2 = ? and Kriterium3 = ?";
	
	//Bereich Insert-Befehle
	public static String insertIntoABCResult = "INSERT INTO ABCResult VALUES (?, ?, ?, ?, ?, ?)";
	
	//ABC-Berechnung Selects
	public static String insertABCInputValues = "insert into ABC_Input "
											    + "select a.artikelnr,"
												+ "al.lagernr,"
												+ "sum(ab.Umsatz) as JahresUmsatz,"
												+ "sum(ab.Menge) as JahresMenge,"
												+ "sum(ab.Anzahl) as JahresAnzahl,"
												+ "sum(al.Bestand) as Bestand,"
												+ "a.wgnr"
												+ " from Absatz ab"
												+ " join Artikel a on a.artikelnr = ab.artikelnr"
												+ " join artikelLager al on al.artikelnr = a.artikelnr"
												+ " where ab.datum between ? and ?"
												+ " group by a.artikelnr, al.lagernr, a.wgnr"
												+ " order by a.wgnr;";
	
	public static String insertWholeCompanyInput = "INSERT INTO ABC_Input (ArtikelNr, LagerNr, JahresUmsatz, JahresMenge, JahresAnzahl, Bestand, WgNr) "
												 + "select ArtikelNr, 0, sum(JahresUmsatz), sum(JahresMenge), sum(JahresAnzahl), "
												 + "sum(Bestand), WgNr "
												 + "from ABC_Input "
												 + "group by ArtikelNr,WgNr";
	
	public static String generateABCBerichte = "INSERT INTO ABCBerichte "
			 								 + "select abcR.ABCKZ, abcR.LagerNr, abcI.WgNr, sum(abcI.JahresAnzahl), sum(abcI.JahresUmsatz), sum(abcI.JahresMenge), " 
											 + "sum(abcI.Bestand), sum(abcI.JahresMenge * a.EKPreis), sum(abcI.Bestand * a.EKPreis) "
											 + "from ABCResult abcR "
											 + "join ABC_Input abcI on abcI.ArtikelNr = abcR.ArtikelNr and abcI.LagerNr = abcR.LagerNr "
											 + "join Artikel a on a.ArtikelNr = abcI.ArtikelNr "
											 + "where abcI.LagerNr = ? "
											 + "and abcI.WgNr = ? "
											 + "group by abcR.ABCKZ, abcR.LagerNr, abcI.WgNr "
											 + "UNION "
											 + "select 'SUM', abcR.LagerNr, abcI.WgNr, sum(abcI.JahresAnzahl), sum(abcI.JahresUmsatz), sum(abcI.JahresMenge), "
											 + "sum(abcI.Bestand), sum(abcI.JahresMenge * a.EKPreis), sum(abcI.Bestand * a.EKPreis) "
											 + "from ABCResult abcR "
											 + "join ABC_Input abcI on abcI.ArtikelNr = abcR.ArtikelNr and abcI.LagerNr = abcR.LagerNr "
											 + "join Artikel a on a.ArtikelNr = abcI.ArtikelNr "
											 + "where abcI.LagerNr = ? "
											 + "and abcI.WgNr = ? "
											 + "group by abcR.LagerNr, abcI.WgNr;";
	
	//Bereich Delete-Befehle
	public static String deleteABCInput = "delete from ABC_Input";
	public static String deleteABCResult = "delete from ABCResult";
	public static String deleteABCBerichte = "delete from ABCBerichte";
}
