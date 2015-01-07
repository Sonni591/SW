package interfaces;

import java.sql.ResultSet;
import java.util.ArrayList;
import objects.ABCZuordnung;
import objects.Bericht;
import objects.Vertriebskanal;
import objects.Warengruppe;

public interface IABCRepository {

	public abstract ResultSet getResult(String selectBefehl);

	public abstract void updateABCEinteilung(String updateBefehl, int prozent,
			String bezeichnung);

	public abstract void updateABCZuordnung(String updateBefehl,
			String zuordnung, String kriterium1, String kriterium2,
			String kriterium3);

	public abstract void insertABCInputTable(String beginDate, String endDate);

	public abstract void insertABCResultTable(String ArtikelNr, int LagerNr,
			String abcK1, String abcK2, String abcK3, String abcKz);

	public abstract void deleteABCResultTable();

	public abstract void deleteBerichte();
	
	public abstract void generateBerichte(int lagerNr, int wgNr);

	public abstract ResultSet selectABCInputByStorehouseAndWaregroup(
			int lagerNr, int wgNr, String criteria);

	public abstract ResultSet selectSumOfEachCriteria(int lagerNr, int wgNr);

	public abstract ArrayList<String> getVertriebskanale();

	public abstract ArrayList<String> getWarengruppen();

	public abstract ArrayList<Warengruppe> getWarengruppenObjects();

	public abstract ArrayList<Vertriebskanal> getVertriebskanaeleObjects();

	public abstract ArrayList<Bericht> getBerichte(/*TODO*/);

	public abstract ArrayList<ABCZuordnung> getZuordnungen();

	public abstract int getVertriebsKanalID(String vertriebskanal);
	
	public abstract int getWarengruppeID(String warengruppe);
	
	public abstract ResultSet getChartData(int lagerNr, int wgNr, int kriteriumID);
	
}