package datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
}
