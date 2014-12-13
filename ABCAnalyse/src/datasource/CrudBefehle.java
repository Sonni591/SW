package datasource;

public class CrudBefehle {

	//Bereich f��r Select-Befehle
	public static String selectABCEinteilung = "select * from ABCEinteilung order by Bezeichnung asc";
	public static String selectABCZuordnung = "select * from ABCZuordnung order by Kriterium1,Kriterium2,Kriterium3 asc";
	public static String selectArtikelABCZuordnung = "select Zuordnung from ABCZuordnung where Kriterium1 = ? AND Kriterium2 = ? AND Kriterium3 = ?";
	public static String selectAbsatz = "select * from Absatz";
	public static String selectABCResultView = "select abcR.artikelNr, wg.Bezeichnung, l.Bezeichnung, abcR.ABCK3 as 'Auf.Anz.', abcI.JahresAnzahl, abcR.ABCK2 as 'Auf.Mng', abcI.JahresMenge,"
											 + "abcR.ABCK1 as 'Ums.', abcI.JahresUmsatz, abcR.ABCKZ as 'ABC Zuo.' "
											 + " from ABCResult abcR"
											 + " join ABC_Input abcI on abcI.artikelNr = abcR.artikelNr and (abcI.lagerNr = abcR.lagerNr or abcR.lagerNr = 0)"
											 + " join Warengruppe wg on wg.wgNr = abcI.wgNr"
											 + " join Lager l on l.lagerNr = abcR.lagerNr;";
	public static String selectVertriebskanaele = "select * from Lager order by Bezeichnung asc";
	public static String selectWarengruppen = "select * from Warengruppe order by Bezeichnung asc";
	
	//ABC-Berechnung Selects
	public static String selectABCInputValues = " select a.artikelnr,"
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
	
	public static String selectSumOfCriteriaByStorehouseAndWaregroup = "select sum(JahresUmsatz) as SummeUmsatz, sum(JahresMenge) as SummeMenge, sum(JahresAnzahl) as SummeAnzahl from ABC_Input where LagerNr = ? and WGNr = ?";
	public static String selectSumOfCriteriaByWaregroup = "select sum(JahresUmsatz) as SummeUmsatz, sum(JahresMenge) as SummeMenge, sum(JahresAnzahl) as SummeAnzahl from ABC_Input where WGNr = ?";
	public static String selectABCInputByStorehouseAndWaregroupOrderCriteria = "select * from ABC_Input where LagerNr in (?) and WGNr = ? order by ";
	public static String selectABCInputByWaregroupOrderCriteria = "select * from ABC_Input where WGNr = ? order by ";
	
	//Bereich Update-Befehle
	public static String updateEinteilungAnteilA = "update " + "ABCEinteilung set AnteilA = ? where Bezeichnung = ?";
	public static String updateEinteilungAnteilB = "update " + "ABCEinteilung set AnteilB = ? where Bezeichnung = ?";
	public static String updateEinteilungAnteilC = "update " + "ABCEinteilung set AnteilC = ? where Bezeichnung = ?";
	
	public static String updateABCZuordnung = "update ABCZuordnung set Zuordnung = ? where Kriterium1 = ? and Kriterium2 = ? and Kriterium3 = ?";
	
	//Bereich Insert-Befehle
	public static String insertIntoABCResult = "INSERT INTO ABCResult VALUES (?, ?, ?, ?, ?, ?)";
	
	//Bereich Delete-Befehle
	public static String deleteABCInput = "delete from ABC_Input";
	public static String deleteABCResult = "delete from ABCResult";
}
