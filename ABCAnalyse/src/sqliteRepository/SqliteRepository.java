package sqliteRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import objects.ABCZuordnung;
import objects.Vertriebskanal;
import objects.Warengruppe;
import interfaces.IABCRepository;

public class SqliteRepository implements IABCRepository{
	private Connection connection = null;
	
	//Herstellen der Verbindung zur SQLite Datenbank
	public SqliteRepository(){
		connection = DBConnector.connectSqLite();
	}
	
	
	/* (non-Javadoc)
	 * @see sqliteRepository.IABCRepository#getResult(java.lang.String)
	 */
	
	@Override
	public ResultSet getABCResultData(){
		return getResult(CrudBefehle.selectABCResultView);
	}
	
	@Override
	public ResultSet getZuordnungenResult(){
		return getResult(CrudBefehle.selectABCZuordnung);
	}
	
	@Override
	public ResultSet getEinteilungenResult(){
		return getResult(CrudBefehle.selectABCEinteilung);
	}
	
	
	/* (non-Javadoc)
	 * @see sqliteRepository.IABCRepository#updateABCEinteilung(java.lang.String, int, java.lang.String)
	 */
	@Override
	public void updateABCEinteilung(String updateBefehl, int prozent, String bezeichnung)
	{
		PreparedStatement updateStatement = null;
		try {
			connection.setAutoCommit(false);
			updateStatement = connection.prepareStatement(updateBefehl);
			updateStatement.setInt(1, prozent);
			updateStatement.setString(2, bezeichnung);
			updateStatement.executeUpdate();
	        connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see sqliteRepository.IABCRepository#updateABCZuordnung(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void updateABCZuordnung(String zuordnung, 
										  String kriterium1, String kriterium2, String kriterium3)
	{
		PreparedStatement updateStatement = null;
		try {
			connection.setAutoCommit(false);
			updateStatement = connection.prepareStatement(CrudBefehle.updateABCZuordnung);
			updateStatement.setString(1, zuordnung);
			updateStatement.setString(2, kriterium1);
			updateStatement.setString(3, kriterium2);
			updateStatement.setString(4, kriterium3);
			updateStatement.executeUpdate();
	        connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see sqliteRepository.IABCRepository#insertABCInputTable(java.lang.String, java.lang.String)
	 */
	@Override
	public void insertABCInputTable(String beginDate, String endDate)
	{
		// Datum muss im Format YY.MM.DD hh:mm:ss:xxx vorliegen
		
		PreparedStatement insertStatement = null, insertStatementWholeC = null;
			try {
				connection.setAutoCommit(false);
				//Erst Tablleninhalt loeschen
				Statement statement = connection.createStatement();
				statement.executeUpdate(CrudBefehle.deleteABCInput);
				
				//Jetzt kann die Tabelle neu befuellt werden
				// einzelne Filialen
				insertStatement = connection.prepareStatement(CrudBefehle.insertABCInputValues);
				insertStatement.setString(1, beginDate);
				insertStatement.setString(2, endDate);
				insertStatement.executeUpdate();
				connection.commit();
				// Gesamtunternehmen
				insertStatementWholeC = connection.prepareStatement(CrudBefehle.insertWholeCompanyInput);
				insertStatementWholeC.executeUpdate();
				connection.commit();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	/* (non-Javadoc)
	 * @see sqliteRepository.IABCRepository#insertABCResultTable(java.lang.String, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void insertABCResultTable(String ArtikelNr, int LagerNr, String abcK1, String abcK2, String abcK3, String abcKz)
	{
		PreparedStatement insertStatement = null;
		try {
			connection.setAutoCommit(false);
			//Jetzt kann die Tabelle neu befuellt werden
			insertStatement = connection.prepareStatement(CrudBefehle.insertIntoABCResult);
			insertStatement.setString(1, ArtikelNr);
			insertStatement.setInt(2, LagerNr);
			insertStatement.setString(3, abcK1);
			insertStatement.setString(4, abcK2);
			insertStatement.setString(5, abcK3);
			insertStatement.setString(6, abcKz);
			insertStatement.executeUpdate();
			connection.commit();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see sqliteRepository.IABCRepository#insertDArtikel
	 */
	@Override
	public void insertDArtikel()
	{
		PreparedStatement insertStatementInput = null, insertStatementResult = null,
				insertStatementInputGesamtunternehmen = null, insertStatementResultGesamtunternehmen = null;
		try {
			connection.setAutoCommit(false);
						
			// D Artikel in ABC Result schreiben
			insertStatementResult = connection.prepareStatement(CrudBefehle.insertDArtikelResult);
			insertStatementResult.executeUpdate();
			connection.commit();
			
			// D Artikel in ABC Input schreiben
			insertStatementInput = connection.prepareStatement(CrudBefehle.insertDArtikelInput);
			insertStatementInput.executeUpdate();
			connection.commit();

			// D Artikel in ABC Input schreiben (für Gesamtunternehmen)
			insertStatementInputGesamtunternehmen = connection.prepareStatement(CrudBefehle.insertDArtikelInputGesamtunternehmen);
			insertStatementInputGesamtunternehmen.executeUpdate();
			
			// D Artikel in ABC Result schreiben (für Gesamtunternehmen)
			insertStatementResultGesamtunternehmen = connection.prepareStatement(CrudBefehle.insertDArtikelResultGesamtunternehmen);
			insertStatementResultGesamtunternehmen.executeUpdate();
			connection.commit();
						
			connection.commit();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see sqliteRepository.IABCRepository#deleteABCResultTable()
	 */
	@Override
	public void deleteABCResultTable()
	{
		Statement statement;
		try {
			statement = connection.createStatement();
			statement.executeUpdate(CrudBefehle.deleteABCResult);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see sqliteRepository.IABCRepository#deleteBerichte()
	 */
	@Override
	public void deleteBerichte()
	{
		try {
			connection.setAutoCommit(false);
			Statement deleteStatement = connection.createStatement();
			deleteStatement.executeUpdate(CrudBefehle.deleteABCBerichte);
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/* (non-Javadoc)
	 * @see sqliteRepository.IABCRepository#generateBerichte(int, int)
	 */
	@Override
	public void generateBerichte(int lagerNr, int wgNr)
	{
		PreparedStatement insertStatement = null;
		PreparedStatement insertStatementSumLine = null;

		try {

			connection.setAutoCommit(false);
			// ABC Berichte generieren
			// (noch ohne Warengruppe "alle" und ohne SUM-Zeilen)
			insertStatement = connection.prepareStatement(CrudBefehle.generateABCBerichte);
			insertStatement.setInt(1, lagerNr);
			insertStatement.setInt(2, wgNr);
			insertStatement.setInt(3, lagerNr);
			insertStatement.setInt(4, wgNr);
			insertStatement.setInt(5, lagerNr);
			insertStatement.setInt(6, wgNr);
			insertStatement.executeUpdate();
			connection.commit();
			
			
			//Summen der einzelnen Kriterien
			insertStatementSumLine = connection.prepareStatement(CrudBefehle.generateSUMBerichteLine);
			insertStatementSumLine.setInt(1, lagerNr);
			insertStatementSumLine.setInt(2, wgNr);
			insertStatementSumLine.executeUpdate();
			connection.commit();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/* (non-Javadoc)
	 * @see sqliteRepository.IABCRepository#generateBerichteWGAlle()
	 */
	@Override
	public void generateBerichteWGAlle()
	{
		PreparedStatement insertStatementWGAlle = null;
		
		try {

			// Berichte für alle Warengruppen generieren
			connection.setAutoCommit(false);
			insertStatementWGAlle = connection.prepareStatement(CrudBefehle.generateABCBerichteWGAlle);
			insertStatementWGAlle.executeUpdate();
			connection.commit();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see sqliteRepository.IABCRepository#selectABCInputByStorehouseAndWaregroup(int, int, java.lang.String)
	 */
	@Override
	public ResultSet selectABCInputByStorehouseAndWaregroup(int lagerNr, int wgNr, String criteria)
	{
		PreparedStatement selectStatement = null;
		try{
			selectStatement = connection.prepareStatement(CrudBefehle.selectABCInputByStorehouseAndWaregroupOrderCriteria + criteria + " desc");
			selectStatement.setInt(1, lagerNr);
			selectStatement.setInt(2, wgNr);
			ResultSet rs = selectStatement.executeQuery();
			return rs;
		}catch (SQLException ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see sqliteRepository.IABCRepository#selectSumOfEachCriteria(int, int)
	 */
	@Override
	public ResultSet selectSumOfEachCriteria(int lagerNr, int wgNr)
	{
		PreparedStatement selectStatement = null;
		try{
			selectStatement = connection.prepareStatement(CrudBefehle.selectSumOfCriteriaByStorehouseAndWaregroup);
			selectStatement.setInt(1, lagerNr);
			selectStatement.setInt(2, wgNr);
			ResultSet rs = selectStatement.executeQuery();
			return rs;
		}catch (SQLException ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see sqliteRepository.IABCRepository#getVertriebskanale()
	 */
	@Override
	public ArrayList<String> getVertriebskanale() {

		ArrayList<String> vkList = new ArrayList<String>();

		ResultSet rsVertriebskanale = null;
		try {
			rsVertriebskanale = getResult(CrudBefehle.selectVertriebskanaele);

			while (rsVertriebskanale.next()) {

				String bezeichnung = rsVertriebskanale.getString("Bezeichnung");
				vkList.add(bezeichnung);

			}
			return vkList;

		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
			return null;
		} finally {
			try {
				rsVertriebskanale.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	/* (non-Javadoc)
	 * @see sqliteRepository.IABCRepository#getVertriebskanaleBerichte()
	 */
	@Override
	public ArrayList<String> getVertriebskanaleBerichte() {

		ArrayList<String> vkList = new ArrayList<String>();

		ResultSet rsVertriebskanale = null;
		try {
			rsVertriebskanale = getResult(CrudBefehle.selectVertriebskanaeleBerichte);

			while (rsVertriebskanale.next()) {

				String bezeichnung = rsVertriebskanale.getString("Bezeichnung");
				vkList.add(bezeichnung);

			}
			return vkList;

		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
			return null;
		} finally {
			try {
				rsVertriebskanale.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	/* (non-Javadoc)
	 * @see sqliteRepository.IABCRepository#getWarengruppen()
	 */
	@Override
	public ArrayList<String> getWarengruppen() {

		ArrayList<String> wgList = new ArrayList<String>();

		ResultSet rsWarengruppen = null;
		try {
			rsWarengruppen = getResult(CrudBefehle.selectWarengruppen);

			while (rsWarengruppen.next()) {

				String bezeichnung = rsWarengruppen.getString("Bezeichnung");
				wgList.add(bezeichnung);

			}
			return wgList;

		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
			return null;
		} finally {
			try {
				rsWarengruppen.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	/* (non-Javadoc)
	 * @see sqliteRepository.IABCRepository#getWarengruppenObjects()
	 */
	@Override
	public ArrayList<Warengruppe> getWarengruppenObjects() {
		
		ArrayList<Warengruppe> wgList = new ArrayList<Warengruppe>();

		ResultSet rsWarengruppen = null;
		try {
			rsWarengruppen = getResult(CrudBefehle.selectWarengruppen);

			while (rsWarengruppen.next()) {

				Warengruppe obj = new Warengruppe();
				obj.setWGNr(rsWarengruppen.getInt("WGNr"));
				obj.setBezeichnung(rsWarengruppen.getString("Bezeichnung"));
			
				wgList.add(obj);

			}
			return wgList;

		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
			return null;
		} finally {
			try {
				rsWarengruppen.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/* (non-Javadoc)
	 * @see sqliteRepository.IABCRepository#getWarengruppenBerichte()
	 */
	@Override
	public ArrayList<String> getWarengruppenBerichte() {

		ArrayList<String> wgList = new ArrayList<String>();

		ResultSet rsWarengruppen = null;
		try {
			rsWarengruppen = getResult(CrudBefehle.selectWarengruppenBerichte);

			while (rsWarengruppen.next()) {

				String bezeichnung = rsWarengruppen.getString("Bezeichnung");
				wgList.add(bezeichnung);

			}
			return wgList;

		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
			return null;
		} finally {
			try {
				rsWarengruppen.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	
	/* (non-Javadoc)
	 * @see sqliteRepository.IABCRepository#getVertriebskanaeleObjects()
	 */
	@Override
	public ArrayList<Vertriebskanal> getVertriebskanaeleObjects() {
		
		ArrayList<Vertriebskanal> vtkList = new ArrayList<Vertriebskanal>();

		ResultSet rsVertriebskanaele = null;
		try {
			rsVertriebskanaele = getResult(CrudBefehle.selectVertriebskanaele);

			while (rsVertriebskanaele.next()) {

				Vertriebskanal obj = new Vertriebskanal();
				obj.setLagerNr(rsVertriebskanaele.getInt("LagerNr"));
				obj.setBezeichnung(rsVertriebskanaele.getString("Bezeichnung"));
			
				vtkList.add(obj);

			}
			return vtkList;

		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
			return null;
		} finally {
			try {
				rsVertriebskanaele.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see sqliteRepository.IABCRepository#getZuordnungen()
	 */
	@Override
	public ArrayList<ABCZuordnung> getZuordnungen(){
		ArrayList<ABCZuordnung> zuordnungen = new ArrayList<ABCZuordnung>();
	ResultSet rs = getResult(CrudBefehle.selectABCZuordnung);
	try {
		while(rs.next())
		{
			ABCZuordnung zuordnung = new ABCZuordnung();
			zuordnung.setKey(rs.getString(1) + rs.getString(2) + rs.getString(3));
			zuordnung.setZuordnung(rs.getString(4));
			zuordnungen.add(zuordnung);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}
	finally{
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	return zuordnungen;
	}
	
	
	/* (non-Javadoc)
	 * @see sqliteRepository.IABCRepository#getVertriebsKanalID(String)
	 */
	@Override
	public int getVertriebsKanalID(String vertriebskanal) {
		PreparedStatement selectStatement = null;
		try{
			selectStatement = connection.prepareStatement(CrudBefehle.selectVertriebskanalID);
			selectStatement.setString(1, vertriebskanal);
			ResultSet rs = selectStatement.executeQuery();	
			return rs.getInt(1);
		}catch (SQLException ex){
			ex.printStackTrace();
			return -1;
		}
	}
	
	/* (non-Javadoc)
	 * @see sqliteRepository.IABCRepository#getWarengruppeID(String)
	 */
	@Override
	public int getWarengruppeID(String warengruppe) {
		
		// Sonderfall: Warengruppe "alle"
		// (dieser ist nicht in der Datenbank vorhanden)
		if(warengruppe.equals("alle")) {
			return 0;
		}
		
		PreparedStatement selectStatement = null;
		try{
			selectStatement = connection.prepareStatement(CrudBefehle.selectWarengruppeID);
			selectStatement.setString(1, warengruppe);
			ResultSet rs = selectStatement.executeQuery();	
			return rs.getInt(1);
		}catch (SQLException ex){
			ex.printStackTrace();
			return -1;
		}
	}

	
	/* (non-Javadoc)
	 * @see sqliteRepository.IABCRepository#getChartData(int, int, int)
	 */
	public ResultSet getChartData(int lagerNr, int wgNr, int kriteriumID)
	{
		PreparedStatement selectStatement = null;
		try{
			selectStatement = connection.prepareStatement(CrudBefehle.getChartData);
			selectStatement.setInt(1, lagerNr);
			selectStatement.setInt(2, wgNr);
			selectStatement.setInt(3, kriteriumID);
			ResultSet rs = selectStatement.executeQuery();
			return rs;
		}catch (SQLException ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	//Standardfunktion um einen SQL-Befehl and die Datenbank zu schicken und ein ResultSet zu erhalten
	private ResultSet getResult(String selectBefehl)
	{
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(selectBefehl);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
