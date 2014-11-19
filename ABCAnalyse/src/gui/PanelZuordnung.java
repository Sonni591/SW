package gui;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import datasource.CrudBefehle;
import datasource.CrudFunktionen;

public class PanelZuordnung extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JTable table;

	public PanelZuordnung() {
		setLayout(new BorderLayout(0, 0));

		JPanel panelZuordnungHeader = new JPanel();
		add(panelZuordnungHeader, BorderLayout.NORTH);

		JLabel lblZuordnungHeader = new JLabel(
				"<html>Bitte fassen Sie die Einzelergebnisse der Analyse in ein Kennzeichen <p/> zusammen, welches mit dem Artikel gespeichert wird.</html>");
		panelZuordnungHeader.add(lblZuordnungHeader);

		JPanel panelZuordnungContent = new JPanel();
		add(panelZuordnungContent, BorderLayout.CENTER);
		panelZuordnungContent.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();

		table = new JTable();
		scrollPane.setViewportView(table);
		panelZuordnungContent.add(scrollPane);

		JPanel panelZuordnungFooter = new JPanel();
		add(panelZuordnungFooter, BorderLayout.SOUTH);

		getTableData();

		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table
				.getTableHeader().getDefaultRenderer();
		headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		for (int columnIndex = 0; columnIndex < table.getColumnCount(); columnIndex++) {
			table.getColumnModel().getColumn(columnIndex)
					.setCellRenderer(cellRenderer);
		}

		table.setShowGrid(true);
		table.setShowHorizontalLines(true);
		table.setShowVerticalLines(true);
		table.setGridColor(Color.LIGHT_GRAY);
	}

	public void getTableData() {
		DefaultTableModel dtm = new DefaultTableModel();
		ResultSet abcEinteilungResult = null;
		try {
			abcEinteilungResult = CrudFunktionen.getResult(
					MainWindow.DBconnection, CrudBefehle.selectABCZuordnung);

			table.setModel(buildTableModel(abcEinteilungResult));
			dtm.fireTableDataChanged();
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

