package gui;

import interfaces.IABCRepository;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.JButton;

import sqliteRepository.CrudBefehle;

public class PanelZuordnung extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JTable table;
	
	private IABCRepository repository;

	public PanelZuordnung(IABCRepository _repository) {
		repository = _repository;
		
		//Layout-Optione  --Start
		setLayout(new BorderLayout(0, 0));

		JPanel panelZuordnungHeader = new JPanel();
		add(panelZuordnungHeader, BorderLayout.NORTH);

		JLabel lblZuordnungHeader = new JLabel(
				"<html>Bitte fassen Sie die Einzelergebnisse der Analyse in ein Kennzeichen <p/> zusammen, welches mit dem Artikel gespeichert wird.</html>");
		panelZuordnungHeader.add(lblZuordnungHeader);

		JPanel panelZuordnungContent = new JPanel();
		add(panelZuordnungContent, BorderLayout.CENTER);
		panelZuordnungContent.setLayout(new BorderLayout(0, 0));
		
		//Tabelle erstllen mit nicht editierbaren Spalten
				table = new JTable() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public boolean isCellEditable(int row, int column) {
						if(column == 0 || column == 1 || column == 2)
						{
							return false;
						}
						return true;
					}
				};	
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);
		panelZuordnungContent.add(scrollPane);

		JPanel panelZuordnungFooter = new JPanel();
		add(panelZuordnungFooter, BorderLayout.SOUTH);
		panelZuordnungFooter.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		JButton btnSpeichern = new JButton("Speichern");
		//Layout-Optionen  --End
		
		//Event fuer das Speichern der Zuordnung
		btnSpeichern.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				updateABCZuordnung();
				JOptionPane.showMessageDialog(MainWindow.frame,
						"Das Speichern der ABC-Zuordnung war erfolgreich.",
						"Speichern erfolgreich",
						JOptionPane.INFORMATION_MESSAGE);

			}
		});
		panelZuordnungFooter.add(btnSpeichern);

		setTableData();
		setTableHeaderText();

		final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

		//Renderer um den Hintergrund der Tabelle zu gestalten
		TableCellRenderer cellRenderer = new TableCellRenderer() {

			@Override
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				Component c = DEFAULT_RENDERER.getTableCellRendererComponent(
						table, value, isSelected, hasFocus, row, column);

				if (column == 0 || column == 1 || column == 2) {
					c.setBackground(new Color(227,227,227));
				} else {
					c.setBackground(Color.WHITE);
				}
				// Setzen der Selektions-Farbe
				if (isSelected) {
					c.setBackground(table.getSelectionBackground());
				}
				return c;
			}

		};
		DEFAULT_RENDERER.setHorizontalAlignment(SwingConstants.CENTER);
		
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
		//Deaktiviert das verschieben der Spalten
		table.getTableHeader().setReorderingAllowed(false);
	}

	/**
	 * Methode zum setzen der Spaltennamen
	 */
	public void setTableHeaderText() {
		table.getColumnModel().getColumn(0).setHeaderValue("Umsatz");
		table.getColumnModel().getColumn(1).setHeaderValue("Anzahl");
		table.getColumnModel().getColumn(2).setHeaderValue("Menge");
		table.getColumnModel().getColumn(3).setHeaderValue("Zuordnung");
	}
	
	/**
	 * Methode welche die Tabellendaten aus der Datenbank laedt und lokale Daten einfuegt.
	 * Danach wird eine Methode zum Modellieren der Tabelle mit den Daten aufgerufen.
	 */
	public void setTableData() {
		JComboBox<String> cboZuordnung = new JComboBox<String>();
		cboZuordnung.setAlignmentX(CENTER_ALIGNMENT);
		cboZuordnung.addItem("A");
		cboZuordnung.addItem("B");
		cboZuordnung.addItem("C");
		ResultSet abcEinteilungResult = null;
		try {
			abcEinteilungResult = repository.getZuordnungenResult();
			
			table.setModel(buildTableModel(abcEinteilungResult));
			table.getColumnModel().getColumn(3)
					.setCellEditor(new DefaultCellEditor(cboZuordnung));
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

	/**
	 * Methode welche aus einem ResultSet eine Tabelle modelliert.
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static DefaultTableModel buildTableModel(ResultSet rs)
			throws SQLException {
		//Metadaten fuer die Spaltennamen
		ResultSetMetaData metaData = rs.getMetaData();

		//Namen der Spalten
		Vector<String> columnNames = new Vector<String>();
		int columnCount = metaData.getColumnCount();
		for (int column = 1; column <= columnCount; column++) {
			columnNames.add(metaData.getColumnName(column));
		}

		//Daten (Zeilen) der Tabelle
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

	/**
	 * Methode, welche die Daten aus der Tabelle der Oberflaeche liest und den update fuer die Datenbank aufruft
	 */
	private void updateABCZuordnung() {
		for (int row = 0; row < table.getRowCount(); row++) {
			String zuordnung = table.getModel().getValueAt(row, 3).toString();
			String kriterium1 = table.getModel().getValueAt(row, 0).toString();
			String kriterium2 = table.getModel().getValueAt(row, 1).toString();
			String kriterium3 = table.getModel().getValueAt(row, 2).toString();

			repository.updateABCZuordnung(zuordnung, kriterium1,
					kriterium2, kriterium3);
		}
	}
}
