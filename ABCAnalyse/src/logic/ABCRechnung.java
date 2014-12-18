package logic;

import gui.MainWindow;
import interfaces.IABCRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import objects.ABCEinteilung;
import objects.ABCResult;
import objects.ABCZuordnung;
import objects.Vertriebskanal;
import objects.Warengruppe;
import sqliteRepository.CrudBefehle;


public class ABCRechnung {
	IABCRepository repository;
	Hashtable<String, ABCResult> abcResultTable = new Hashtable<String, ABCResult>();
	Hashtable<String, String> abcZurdnungTable = new Hashtable<String, String>();
	Hashtable<String, ABCEinteilung> abcEinteilungTable = new Hashtable<String, ABCEinteilung>();
	
	ArrayList<Vertriebskanal> arrListVertriebskanaele = new ArrayList<Vertriebskanal>();
	ArrayList<Warengruppe> arrListWarengruppen = new ArrayList<Warengruppe>();
	
	public ABCRechnung(IABCRepository _repository){
		repository = _repository;
		createZuordnungTable();
		createEinteilungTable();
	}

	private void createZuordnungTable(){
		ArrayList<ABCZuordnung> abcZ = repository.getZuordnungen();
		if(abcZ != null){
			for(ABCZuordnung z : abcZ){
				abcZurdnungTable.put(z.key, z.zuordnung);
			}
		}
	}
	
	private void createEinteilungTable(){
		ResultSet rs = repository.getResult(CrudBefehle.selectABCEinteilung);
		try {
			while(rs.next())
			{
				String key = rs.getString(2);
				ABCEinteilung einteilung = new ABCEinteilung();
				einteilung.AnteilA = rs.getInt(3);
				einteilung.AnteilB = rs.getInt(4);
				einteilung.AnteilC = rs.getInt(5);
				abcEinteilungTable.put(key, einteilung);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// TODO
	//
	/*public void getSelectedGUIItems() {
		if (PanelParameter.rdbtnAlleVertriebskanaele.isSelected()) {
			// Funktion wenn alle Kan‰le gw‰hlt wurde
			System.out.println("Alle Kan‰le");
			
			arrListVertriebskanaele = repository.getVertriebskanaeleObjects();
			
			arrListWarengruppen = repository.getWarengruppenObjects();
			
		} 
		else {
			// ausgewählten Vertriebskanal lesen
			Vertriebskanal vertriebskanal = new Vertriebskanal();
			vertriebskanal.setBezeichnung((String) PanelParameter.cboVertriebskanal.getSelectedItem());
			vertriebskanal.setLagerNr(getVertriebsKanal(vertriebskanal.getBezeichnung()));
			arrListVertriebskanaele.add(vertriebskanal);
			
			// ausgewählte Warengruppe lesen
			Warengruppe warengruppe = new Warengruppe();
			warengruppe.setBezeichnung((String) PanelParameter.cboWarengruppe.getSelectedItem());
			warengruppe.setWGNr(getWarenGruppe(warengruppe.getBezeichnung()));
			arrListWarengruppen.add(warengruppe);
		}

	}*/
	
    //Neue Methode welche die ABC-Result-Tabelle berechnet
	public void CalculateABCResult()
	{
		
		// Ausgewählte Vertriebskanäle und Warengruppen lesen
		arrListVertriebskanaele = repository.getVertriebskanaeleObjects();
		
		arrListWarengruppen = repository.getWarengruppenObjects();
		//getSelectedGUIItems();
		
		// Output Test
		for(Vertriebskanal obj : arrListVertriebskanaele) {
			System.out.println(obj.getLagerNr() + " " + obj.getBezeichnung());
		}
		for(Warengruppe obj : arrListWarengruppen){
			System.out.println(obj.getWGNr() + " " + obj.getBezeichnung());
		}
		
		//Fuer jedes Lager
		for(Vertriebskanal obj : arrListVertriebskanaele)
		{
			int lagerNr = obj.getLagerNr();
			
			//Fuer jede Warengruppe
			for(Warengruppe wgObj : arrListWarengruppen)
			{
				int wgNr = wgObj.getWGNr();
				
				ArrayList<Double> criteriaSumValues = getSumOfEachCriteria(repository.selectSumOfEachCriteria(lagerNr, wgNr));
				//Fuer jedes Kriterium
				for(int criteria = 1; criteria <= 3; criteria++)
				{
					//entsprich Insgesamt 4*9*3=108 Durchlaeufen
					try
					{
						ResultSet rs = null;
						switch(criteria){
						//Umsatz
						case 1: 
							rs = repository.selectABCInputByStorehouseAndWaregroup(lagerNr, wgNr, "JahresUmsatz");
							accumulate(rs, criteriaSumValues, lagerNr, "Umsatz");
							break;
						//Menge
						case 2:
							rs = repository.selectABCInputByStorehouseAndWaregroup(lagerNr, wgNr, "JahresMenge");
							accumulate(rs, criteriaSumValues, lagerNr, "Menge");
							break;
						//Anzahl
						case 3:
							rs = repository.selectABCInputByStorehouseAndWaregroup(lagerNr, wgNr, "JahresAnzahl");
							accumulate(rs, criteriaSumValues, lagerNr, "Anzahl");
							break;
						}
					}catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
			}
		}
		System.out.println("Berechnung erledigt nun nur noch ergebnis in Tabelle einfügen");
		insertResultIntoDB();
		System.out.println("Einfügen erfolgreich");

		
	}
	
	//Kumulieren
	private void accumulate(ResultSet rs, ArrayList<Double> criteriaSumValues, int lagerNr, String description)
	{
		try {
			switch(description)
			{
				case "Umsatz":
					double umsatzSum = criteriaSumValues.get(0);
					double umsatzAccumulated = 0;
					while(rs.next())
					{
						umsatzAccumulated += rs.getDouble(3);
						ABCResult result = new ABCResult();
						result.ArtikelNr = rs.getString(1);
						result.LagerNr = lagerNr;
						result.ABCK1 = getCriteriaByPercent((umsatzAccumulated/umsatzSum)*100, "Umsatz");
						String key = rs.getString(1) + lagerNr;
						abcResultTable.put(key,result);
					}
					break;
				case "Menge":
					double mengeSum = criteriaSumValues.get(1);
					double mengeAccumulated = 0;
					while(rs.next())
					{
						mengeAccumulated += rs.getDouble(4);
						ABCResult result = abcResultTable.get(rs.getString(1)+lagerNr);
						result.ABCK2 = getCriteriaByPercent((mengeAccumulated/mengeSum)*100, "Menge");
					}
					break;
				case "Anzahl":
					double anzahlSum = criteriaSumValues.get(2);
					double anzahlAccumulated = 0;
					while(rs.next())
					{
						anzahlAccumulated += rs.getDouble(5);
						ABCResult result = abcResultTable.get(rs.getString(1)+lagerNr);
						result.ABCK3 = getCriteriaByPercent((anzahlAccumulated/anzahlSum)*100, "Auftragsanzahl");
						result.ABCKZ = getZuordnung(result.ABCK1, result.ABCK2, result.ABCK3);
					}
					break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Holt die Summen von den drei Kriterien fuer die Prozentbestimmung
	 * */
	private ArrayList<Double> getSumOfEachCriteria(ResultSet rs)
	{
		ArrayList<Double> criteriaSumValues= new ArrayList<Double>();
		
		try {
			while(rs.next())
			{
				criteriaSumValues.add(rs.getDouble(1)); //Umsatz
				criteriaSumValues.add(rs.getDouble(2)); //Menge
				criteriaSumValues.add(rs.getDouble(3)); //Anzahl
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return criteriaSumValues;
	}
	
	//Liefert die Klassifikation anhand der Prozent
	private String getCriteriaByPercent(double percent, String Description)
	{
		String criteria;
		ABCEinteilung einteilung = abcEinteilungTable.get(Description);
		if(percent < einteilung.AnteilA)
		{
		 criteria = "A";
		}
		else if(percent > einteilung.AnteilA && percent < (einteilung.AnteilA + einteilung.AnteilB))
		{
			criteria = "B";
		}
		else
		{
			criteria = "C";
		}
		return criteria;
	}
	
	private String getZuordnung(String kriterium1, String kriterium2,String kriterium3)
	{
		return abcZurdnungTable.get(kriterium1+kriterium2+kriterium3).toString();
	}
	
	private void insertResultIntoDB()
	{
		//Erst aktuellen Tabelleninhalt loeschen
		repository.deleteABCResultTable();
		 for(String key: abcResultTable.keySet()){
			 ABCResult result = abcResultTable.get(key);
	         repository.insertABCResultTable(result.ArtikelNr, result.LagerNr, result.ABCK1, result.ABCK2, result.ABCK3, result.ABCKZ);
	        }
	}
	
	/*public int getVertriebsKanal(String VertriebsKanalString) {
		if (VertriebsKanalString.equals("Lübeck")) {
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
			daten = repository.getResult(CrudBefehle.selectWarengruppen);
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
	}*/

}
