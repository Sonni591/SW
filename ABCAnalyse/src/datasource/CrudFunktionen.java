package datasource;

import gui.MainWindow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import objects.Absatz;
import objects.Vertriebskanal;
import objects.Warengruppe;

public class CrudFunktionen {
	
	public static ResultSet getResult(Connection connection, String selectBefehl)
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
	
	public static void updateABCEinteilung(Connection connection, String updateBefehl, int prozent, String bezeichnung)
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
	
	public static void updateABCZuordnung(Connection connection, String updateBefehl, String zuordnung, 
										  String kriterium1, String kriterium2, String kriterium3)
	{
		PreparedStatement updateStatement = null;
		try {
			connection.setAutoCommit(false);
			updateStatement = connection.prepareStatement(updateBefehl);
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
	
	public static void insertABCInputTable(Connection connection, String beginDate, String endDate)
	{
			PreparedStatement insertStatement = null, insertStatementWholeC = null;
			try {
				connection.setAutoCommit(false);
				//Erst Tablleninhalt loeschen
				Statement statement = connection.createStatement();
				statement.executeUpdate(CrudBefehle.deleteABCInput);
				//Jetzt kann die Tabelle neu befuellt werden
				insertStatement = connection.prepareStatement(CrudBefehle.insertABCInputValues);
				insertStatement.setString(1, beginDate);
				insertStatement.setString(2, endDate);
				insertStatement.executeUpdate();
				connection.commit();
				insertStatementWholeC = connection.prepareStatement(CrudBefehle.insertWholeCompanyInput);
				insertStatementWholeC.executeUpdate();
				connection.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public static void insertABCResultTable(Connection connection, String ArtikelNr, int LagerNr, String abcK1, String abcK2, String abcK3, String abcKz)
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
	
	public static void deleteABCResultTable(Connection connection)
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
	
	public static ResultSet selectABCInputByStorehouseAndWaregroup(Connection connection, int lagerNr, int wgNr, String criteria)
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
	
	public static ResultSet selectSumOfEachCriteria(Connection connection, int lagerNr, int wgNr)
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
	
	public static ArrayList<String> getVertriebskanale() {

		ArrayList<String> vkList = new ArrayList<String>();

		ResultSet rsVertriebskanale = null;
		try {
			rsVertriebskanale = CrudFunktionen
					.getResult(MainWindow.DBconnection,
							CrudBefehle.selectVertriebskanaele);

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
	
	public static ArrayList<String> getWarengruppen() {

		ArrayList<String> wgList = new ArrayList<String>();

		ResultSet rsWarengruppen = null;
		try {
			rsWarengruppen = CrudFunktionen.getResult(MainWindow.DBconnection,
					CrudBefehle.selectWarengruppen);

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
	
	public static ArrayList<Warengruppe> getWarengruppenObjects() {
		
		ArrayList<Warengruppe> wgList = new ArrayList<Warengruppe>();

		ResultSet rsWarengruppen = null;
		try {
			rsWarengruppen = CrudFunktionen.getResult(MainWindow.DBconnection,
					CrudBefehle.selectWarengruppen);

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
	
	public static ArrayList<Vertriebskanal> getVertriebskanaeleObjects() {
		
		ArrayList<Vertriebskanal> vtkList = new ArrayList<Vertriebskanal>();

		ResultSet rsVertriebskanaele = null;
		try {
			rsVertriebskanaele = CrudFunktionen.getResult(MainWindow.DBconnection,
					CrudBefehle.selectVertriebskanaele);

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
	
	
}


