package gui;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import datasource.CrudBefehle;
import datasource.CrudFunktionen;

	public class PanelErgebnis extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JTable resultTable;
		public PanelErgebnis() {
			setLayout(new BorderLayout(0, 0));
			
			JPanel panelErgebnisHeader = new JPanel();
			add(panelErgebnisHeader, BorderLayout.NORTH);
			
			JLabel lblFilter = new JLabel("Hier sollen irgendwann die Filter sein.");
			panelErgebnisHeader.add(lblFilter);
			
			JPanel panelErgebnisContent = new JPanel();
			add(panelErgebnisContent, BorderLayout.CENTER);
			panelErgebnisContent.setLayout(new BorderLayout(0, 0));
			
			resultTable = new JTable();
			panelErgebnisContent.add(resultTable);
			
			getTableData();
			
		}
		
		
		public void getTableData() {
			JComboBox<String> cboZuordnung = new JComboBox<String>();
			cboZuordnung.addItem("A");
			cboZuordnung.addItem("B");
			cboZuordnung.addItem("C");
			ResultSet abcEinteilungResult = null;
			try {
				abcEinteilungResult = CrudFunktionen.getResult(
						MainWindow.DBconnection, CrudBefehle.selectABCResult);

				resultTable.setModel(buildTableModel(abcEinteilungResult));
			} catch (Exception e) {
				System.err.println(e);
				e.printStackTrace();
			} finally {
				try {
					abcEinteilungResult.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		public static DefaultTableModel buildTableModel(ResultSet rs)
				throws SQLException {

			ResultSetMetaData metaData = rs.getMetaData();

			// names of columns
			Vector<String> columnNames = new Vector<String>();
			int columnCount = metaData.getColumnCount();
			for (int column = 1; column <= columnCount; column++) {
				columnNames.add(metaData.getColumnName(column));
			}

			// data of the table
			Vector<Vector<Object>> data = new Vector<Vector<Object>>();
			while (rs.next()) {
				Vector<Object> vector = new Vector<Object>();
				for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
					vector.add(rs.getObject(columnIndex));
				}
				data.add(vector);
			}
			return new DefaultTableModel(data, columnNames);
		}

	}

