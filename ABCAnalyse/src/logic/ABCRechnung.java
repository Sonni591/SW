package logic;

import gui.MainWindow;
import interfaces.IABCRepository;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import objects.ABCEinteilung;
import objects.ABCResult;
import objects.ABCZuordnung;
import objects.Vertriebskanal;
import objects.Warengruppe;


public class ABCRechnung extends Thread{
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
				abcZurdnungTable.put(z.getKey(), z.getZuordnung());
			}
		}
	}
	
	private void createEinteilungTable(){
		ResultSet rs = repository.getEinteilungenResult();
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
	
	
    //Neue Methode welche die ABC-Result-Tabelle berechnet
	public void run() {
		try {
			
		final JDialog dlg = new JDialog(MainWindow.frame,
				"Berechne ABC Analyse", false);
		JProgressBar dpb = new JProgressBar(0, 500);
		dpb.setMaximum(100);
		dpb.setValue(0);
		JLabel lblDpbState = new JLabel("Starte ABC Berechnung");
		dlg.add(BorderLayout.CENTER, dpb);
		dlg.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dlg.setSize(300, 125);
		dlg.setLocationRelativeTo(null);
		dlg.setVisible(true);
		dlg.add(BorderLayout.NORTH, lblDpbState);
		
		dlg.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		
		// Ausgewählte Vertriebskanäle und Warengruppen lesen
		arrListVertriebskanaele = repository.getVertriebskanaeleObjects();
		
		arrListWarengruppen = repository.getWarengruppenObjects();
		
		// Output Test
		for(Vertriebskanal obj : arrListVertriebskanaele) {
			System.out.println(obj.getLagerNr() + " " + obj.getBezeichnung());
		}
		for(Warengruppe obj : arrListWarengruppen){
			System.out.println(obj.getWGNr() + " " + obj.getBezeichnung());
		}
		
		lblDpbState.setText("Bereche ABC Analyse");
		dpb.setValue(5);
		
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
					//entspricht Insgesamt 4*9*3=108 Durchlaeufen
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
		
		lblDpbState.setText("Daten in Datenbank einfügen");
		dpb.setValue(30);
		
		System.out.println("Berechnung erledigt nun nur noch Ergebnis in Tabelle einfügen");
		insertResultIntoDB();
		
		lblDpbState.setText("<html>Daten eingefügt </p> - D Artikel berechnen</html>");
		dpb.setValue(80);
		
		// Einträge der D Artikel in Input und Result-Tabelle eintragen
				repository.insertDArtikel();
		
		lblDpbState.setText("<html>D Artikel berechnet </p> - gleich ist die Analyse beendet</html>");
		dpb.setValue(93);
		
		lblDpbState.setText("Ergebnisanzeige aktualisieren");
		dpb.setValue(97);
		
		MainWindow.panelErgebnis.setTableData();
				
		System.out.println("Einfügen erfolgreich");
		lblDpbState.setText("Einfügen erfolgreich");
		dpb.setValue(100);
		
		dlg.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		
		dlg.setVisible(false);
		dlg.dispose();
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
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
		else if(percent >= einteilung.AnteilA && percent < (einteilung.AnteilA + einteilung.AnteilB))
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
		
		// Neue Einträge der ABC Artikel eintragen
		 for(String key: abcResultTable.keySet()){
			 ABCResult result = abcResultTable.get(key);
	         repository.insertABCResultTable(result.ArtikelNr, result.LagerNr, result.ABCK1, result.ABCK2, result.ABCK3, result.ABCKZ);
	        }

	}
}
