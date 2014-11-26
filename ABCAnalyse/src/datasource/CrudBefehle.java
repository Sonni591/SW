package datasource;

public class CrudBefehle {

	//Bereich für Select-Befehle
	public static String selectABCEinteilung = "select * from ABCEinteilung order by Bezeichnung asc";
	public static String selectABCZuordnung = "select * from ABCZuordnung order by Kriterium1,Kriterium2,Kriterium3 asc";
	public static String selectAbsatz = "select * from Absatz";
	public static String selectABCResult = "select * from ABCResult;";
	
	public static String selectAbsatzDatenOrderedByUmsatz = "SELECT ArtikelNr, Sum(Umsatz) as \"Gesamt Umsatz\", Sum(Anzahl) as \"Gesamt Anzahl\", Sum(Menge) as \"Gesamt Menge\" FROM Absatz GROUP BY ArtikelNr Order By \"Gesamt Umsatz\" DESC;";
	//public static String selectUmsatzGrouped = "SELECT ArtikelNr, Sum(Umsatz) as \"Gesamt Umsatz\"FROM Absatz GROUP BY ArtikelNr Order By \"Gesamt Umsatz\" DESC;";
	//public static String selectAnzahlGrouped = "SELECT ArtikelNr, Sum(Anzahl) as \"Gesamt Anzahl\"FROM Absatz GROUP BY ArtikelNr Order By \"Gesamt Anzahl\" DESC;";
	//public static String selectMengeGrouped  = "SELECT ArtikelNr, Sum(Menge) as \"Gesamt Menge\"FROM Absatz GROUP BY ArtikelNr Order By \"Gesamt Menge\" DESC;";
	
	public static String selectEinteilungUmsatz ="SELECT Bezeichnung, AnteilA, AnteilB, AnteilC FROM ABCEinteilung WHERE BEZEICHNUNG = 'Umsatz'";
	public static String selectEinteilungMenge ="SELECT Bezeichnung, AnteilA, AnteilB, AnteilC FROM ABCEinteilung WHERE BEZEICHNUNG = 'Menge'";
	public static String selectEinteilungAuftragsanzahl ="SELECT Bezeichnung, AnteilA, AnteilB, AnteilC FROM ABCEinteilung WHERE BEZEICHNUNG = 'Auftragsanzahl'";
	//Bereich für Update-Befehle
	public static String updateEinteilungAnteilA = "update " + "ABCEinteilung set AnteilA = ? where Bezeichnung = ?";
	public static String updateEinteilungAnteilB = "update " + "ABCEinteilung set AnteilB = ? where Bezeichnung = ?";
	public static String updateEinteilungAnteilC = "update " + "ABCEinteilung set AnteilC = ? where Bezeichnung = ?";
	
	public static String updateABCZuordnung = "update ABCZuordnung set Zuordnung = ? where Kriterium1 = ? and Kriterium2 = ? and Kriterium3 = ?";
	public static String insertIntoABCResult = "INSERT INTO ABCResult VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
}
