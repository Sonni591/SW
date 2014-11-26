package datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import objects.Absatz;

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
	
	public static void updateABCResult(Connection connection, ArrayList<Absatz> liste)
{
PreparedStatement updateStatement = null;
try {
connection.setAutoCommit(false);
connection.prepareStatement("DELETE FROM ABCResult").executeUpdate();
for(Absatz a : liste){
updateStatement = connection.prepareStatement(CrudBefehle.insertIntoABCResult);
updateStatement.setString(1, a.ArtikelNr);
updateStatement.setString(2, "");
updateStatement.setString(3, String.valueOf(a.UmsatzABCKennzahl));
updateStatement.setString(4, String.valueOf(a.UmsatzProzent));
updateStatement.setString(5, String.valueOf(a.UmsatzProzentKum));
updateStatement.setString(6, String.valueOf(a.MengeABCKennzahl));
updateStatement.setString(7, String.valueOf(a.MengeProzent));
updateStatement.setString(8, String.valueOf(a.MengeProzentKum));
updateStatement.setString(9, String.valueOf(a.AnzahlABCKennzahl));
updateStatement.setString(10, String.valueOf( a.AnzahlProzent));
updateStatement.setString(11, String.valueOf(a.AnzahlProzentKum));
updateStatement.setString(12, String.valueOf(a.ABCKennzahl));
updateStatement.executeUpdate();
}
connection.commit();
} catch (SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
}
}
