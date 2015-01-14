package gui;

import interfaces.IABCRepository;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.io.File;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFileChooser;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import logic.ExcelExport;

public class PanelErgebnis extends JPanel {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	private JTable resultTable;
	private JLabel lblCountRows;

	//Felder fuer die Filterung der Tabelle
	private JTextField txtArtikel;
	private JComboBox<String> cboVertriebskanal;
	private JComboBox<String> cboABCUmsatz;
	private JComboBox<String> cboABCAnzahl;
	private JComboBox<String> cboABCMenge;
	private JComboBox<String> cboABCGesamt;
	private JComboBox<String> cboWarengruppe;

	//Lokales Repository
	private IABCRepository repository;

	public PanelErgebnis(IABCRepository _repository) {
		repository = _repository;
		
		//Layout-Optionen  --Start
		setLayout(new BorderLayout(0, 0));

		JPanel panelErgebnisHeader = new JPanel();
		add(panelErgebnisHeader, BorderLayout.NORTH);

		JLabel lblArtikel = new JLabel("Artikel:");

		txtArtikel = new JTextField();
		txtArtikel.setColumns(10);

		JLabel lblVertriebskanal = new JLabel("Vertriebskanal:");
		JLabel lblUmsatz = new JLabel("Umsatz:");
		JLabel lblAnzahl = new JLabel("Anzahl:");
		JLabel lblMenge = new JLabel("Menge:");

		//Dropdowns fuer Filterung
		cboVertriebskanal = new JComboBox<String>();
		cboWarengruppe = new JComboBox<String>();
		cboABCUmsatz = new JComboBox<String>();
		cboABCAnzahl = new JComboBox<String>();
		cboABCMenge = new JComboBox<String>();
		cboABCGesamt = new JComboBox<String>();
		
		JButton btnSetzeFilter = new JButton("Filter setzen");
		JButton btnResetFilter = new JButton("zur\u00FCcksetzen");

		JLabel lblGesamt = new JLabel("Gesamt:");
		JLabel lblWarengruppe = new JLabel("Warengruppe:");

		JButton btnExportExcel = new JButton("Excel-Export");
		JButton btnBerichte = new JButton("Berichte");

		//GroupLayout
		GroupLayout gl_panelErgebnisHeader = new GroupLayout(
				panelErgebnisHeader);
		gl_panelErgebnisHeader
				.setHorizontalGroup(gl_panelErgebnisHeader
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panelErgebnisHeader
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panelErgebnisHeader
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_panelErgebnisHeader
																		.createSequentialGroup()
																		.addGroup(
																				gl_panelErgebnisHeader
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								gl_panelErgebnisHeader
																										.createSequentialGroup()
																										.addComponent(
																												lblArtikel)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												txtArtikel,
																												GroupLayout.DEFAULT_SIZE,
																												111,
																												Short.MAX_VALUE))
																						.addGroup(
																								gl_panelErgebnisHeader
																										.createSequentialGroup()
																										.addComponent(
																												lblVertriebskanal,
																												GroupLayout.PREFERRED_SIZE,
																												94,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addGroup(
																												gl_panelErgebnisHeader
																														.createParallelGroup(
																																Alignment.LEADING)
																														.addComponent(
																																cboWarengruppe,
																																0,
																																63,
																																Short.MAX_VALUE)
																														.addComponent(
																																cboVertriebskanal,
																																0,
																																63,
																																Short.MAX_VALUE))))
																		.addGap(18))
														.addGroup(
																gl_panelErgebnisHeader
																		.createSequentialGroup()
																		.addComponent(
																				lblWarengruppe)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)))
										.addGroup(
												gl_panelErgebnisHeader
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(lblGesamt)
														.addComponent(lblUmsatz)
														.addComponent(lblAnzahl)
														.addComponent(lblMenge))
										.addGroup(
												gl_panelErgebnisHeader
														.createParallelGroup(
																Alignment.TRAILING)
														.addGroup(
																gl_panelErgebnisHeader
																		.createSequentialGroup()
																		.addGap(20)
																		.addGroup(
																				gl_panelErgebnisHeader
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								cboABCGesamt,
																								0,
																								0,
																								Short.MAX_VALUE)
																						.addComponent(
																								cboABCMenge,
																								0,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								cboABCAnzahl,
																								0,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								cboABCUmsatz,
																								0,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)))
														.addGroup(
																gl_panelErgebnisHeader
																		.createSequentialGroup()
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				cboABCGesamt,
																				0,
																				0,
																				Short.MAX_VALUE)))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_panelErgebnisHeader
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																btnResetFilter)
														.addComponent(
																btnSetzeFilter,
																GroupLayout.PREFERRED_SIZE,
																128,
																GroupLayout.PREFERRED_SIZE)
														.addGroup(
																gl_panelErgebnisHeader
																		.createParallelGroup(
																				Alignment.TRAILING,
																				false)
																		.addComponent(
																				btnBerichte,
																				Alignment.LEADING,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addComponent(
																				btnExportExcel,
																				Alignment.LEADING,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)))
										.addContainerGap()));
		gl_panelErgebnisHeader
				.setVerticalGroup(gl_panelErgebnisHeader
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panelErgebnisHeader
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panelErgebnisHeader
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblArtikel)
														.addComponent(
																txtArtikel,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(lblUmsatz)
														.addComponent(
																cboABCUmsatz,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																btnSetzeFilter))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_panelErgebnisHeader
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_panelErgebnisHeader
																		.createSequentialGroup()
																		.addGroup(
																				gl_panelErgebnisHeader
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								lblAnzahl)
																						.addComponent(
																								cboABCAnzahl,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								btnResetFilter))
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addGroup(
																				gl_panelErgebnisHeader
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								lblMenge)
																						.addComponent(
																								cboABCMenge,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								btnExportExcel)))
														.addGroup(
																gl_panelErgebnisHeader
																		.createSequentialGroup()
																		.addGroup(
																				gl_panelErgebnisHeader
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								lblVertriebskanal)
																						.addComponent(
																								cboVertriebskanal,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addGroup(
																				gl_panelErgebnisHeader
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								lblWarengruppe)
																						.addComponent(
																								cboWarengruppe,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_panelErgebnisHeader
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(lblGesamt)
														.addComponent(
																cboABCGesamt,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																btnBerichte))
										.addGap(17)));
		panelErgebnisHeader.setLayout(gl_panelErgebnisHeader);

		JPanel panelErgebnisContent = new JPanel();
		add(panelErgebnisContent, BorderLayout.CENTER);
		panelErgebnisContent.setLayout(new BorderLayout(0, 0));

		//Erstellen einer lokalen Tabelle welche nicht bearbeitbar ist
		resultTable = new JTable() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				// alle Spalten nicht editierbar setzen
				return false;
			}
		};

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(resultTable);
		panelErgebnisContent.add(scrollPane);
		
		
		//Optionen fuer die Anzeige der Tabelle
		resultTable.setShowGrid(true);
		resultTable.setShowHorizontalLines(true);
		resultTable.setShowVerticalLines(true);
		resultTable.setGridColor(Color.LIGHT_GRAY);

		JPanel panelErgebnisFooter = new JPanel();
		add(panelErgebnisFooter, BorderLayout.SOUTH);
		panelErgebnisFooter.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		lblCountRows = new JLabel("Zeilen: ");
		panelErgebnisFooter.add(lblCountRows);
		// Deaktiviert das verschieben der Spalten
		resultTable.getTableHeader().setReorderingAllowed(false);
		//Layout-Optionen   --End
		
		//Event fuer Klick des Filters
		btnSetzeFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setFilter();
			}
		});
		
		//Event um Filter zurueckzusetzen
		btnResetFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetFilter();
			}
		});
		
		//Event fuer den Export des Ergebnisses in Excel
		btnExportExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exportToExcel();
			}
		});
		
		//Event zum oeffnen des Berichteframes
		btnBerichte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameBerichteParameter BerichteDialog = new FrameBerichteParameter(
						repository);
				BerichteDialog.initialize();
			}
		});
		
		//Klassifizierung der ABC-Analyse
		String[] abcKlassfizierung = { "", "A", "B", "C", "D" };
		
		//Event fuer Artikel-Textbox zur Filterung
		txtArtikel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				setFilter();
			}
		});
		
		// Warengruppen lesen
		ArrayList<String> warengruppen = repository.getWarengruppen();
		cboWarengruppe.addItem(""); // leere Auwahl hinzufügen;
		for (String s : warengruppen) {
			cboWarengruppe.addItem(s);
		}
		
		// Vertriebskanaele lesen
		ArrayList<String> vertriebskanaele = repository.getVertriebskanale();
		cboVertriebskanal.addItem(""); // leere Auwahl hinzufügen;
		for (String s : vertriebskanaele) {
			cboVertriebskanal.addItem(s);
		}
		
		//Klassifizierungen fuer den Umsatz
		for (String s : abcKlassfizierung) {
			cboABCUmsatz.addItem(s);
		}
		
		//Klassifizierung fuer die Anzahl
		for (String s : abcKlassfizierung) {
			cboABCAnzahl.addItem(s);
		}
		
		//Klassifizierung fuer die Menge
		for (String s : abcKlassfizierung) {
			cboABCMenge.addItem(s);
		}
		
		//Gesamtklassifizierung
		for (String s : abcKlassfizierung) {
			cboABCGesamt.addItem(s);
		}

		//Das Ergebnis der Tabelle setzen
		setTableData();
		
	}

	// Setter für Filterauswahl von außerhalb
	
	public void setVertriebskanal(String vertriebskanal) {
		cboVertriebskanal.setSelectedItem(vertriebskanal);
	}

	public void setWarengruppe(String warengruppe) {
		cboWarengruppe.setSelectedItem(warengruppe);
	}
	
	public void setABCUmsatz(String ABCKz) {
		cboABCUmsatz.setSelectedItem(ABCKz);
	}

	public void setABCAnzahl(String ABCKz) {
		cboABCAnzahl.setSelectedItem(ABCKz);
	}

	public void setABCMenge(String ABCKz) {
		cboABCMenge.setSelectedItem(ABCKz);
	}

	
	
	/**
	 * Laedt das Ergebnis aus der Tabelle und uebergibt dieses an die Methode, welche die Tabelle modelliert.
	 */
	public void setTableData() {
		ResultSet abcEinteilungResult = null;
		try {
			abcEinteilungResult = repository.getABCResultData();
			
			//Tabelle anhand der Daten modellieren
			resultTable.setModel(buildTableModel(abcEinteilungResult));
			
			// Sorter für die Tabelle setzen gleichzeitig sortierung zurücksetzen
	        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>();
			resultTable.setRowSorter(sorter);
			DefaultTableModel model = (DefaultTableModel) resultTable.getModel();
			sorter.setModel(model);
			sorter.setRowFilter(null);
			
			// Spalten 4 aufwärts mittig setzen
			setTableRenderer();
			
			//Anzahl der Zeilen setzen
			lblCountRows.setText("Zeilen: " + resultTable.getRowCount());
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
	 * TableRenderer setzten, welche gewisse Spalten (ab Spalte 4) mittig setzt
	 */
	public void setTableRenderer() {
//		///////////
		
		final DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer();

		TableCellRenderer cellRenderer = new TableCellRenderer() {
	
			@Override
			public Component getTableCellRendererComponent(JTable table,
					Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				Component c = defaultRenderer.getTableCellRendererComponent(
						table, value, isSelected, hasFocus, row, column);

				return c;
				}
		};
		defaultRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		
		for (int columnIndex = 3; columnIndex < resultTable.getColumnCount(); columnIndex++) {
			resultTable.getColumnModel().getColumn(columnIndex)
					.setCellRenderer(cellRenderer);
		}
		
//		DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) resultTable.getTableHeader().getDefaultRenderer();
//		headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		///////////
	}

	/**
	 * Modelliert eine Tabelle mit den Spaltennamen anhand eines ResultSets
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static DefaultTableModel buildTableModel(ResultSet rs)
			throws SQLException {

		//Laden der Metadaten fuer die Spaltennamen
		ResultSetMetaData metaData = rs.getMetaData();

		//Spaltennamen
		Vector<String> columnNames = new Vector<String>();
		int columnCount = metaData.getColumnCount();
		for (int column = 1; column <= columnCount; column++) {
			columnNames.add(metaData.getColumnName(column));
		}

		//Zeilen (Daten) der Tabelle
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		while (rs.next()) {
			Vector<Object> vector = new Vector<Object>();
			for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				vector.add(rs.getObject(columnIndex));
			}
			data.add(vector);
		}
//		return new DefaultTableModel(data, columnNames);
		return new DefaultTableModel(data, columnNames){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			// Um ein korrektes sortieren zu ermöglichen, müssen die Typen der Spalten korrekt definiert sein
            @Override
            public Class<?> getColumnClass( int column ) {
                switch( column ){
                    case 0: return String.class;
                    case 1: return String.class;
                    case 2: return String.class;
                    case 3: return String.class;
                    case 4: return Integer.class;
                    case 5: return String.class;
                    case 6: return Integer.class;
                    case 7: return String.class;
                    case 8: return Double.class;
                    case 9: return String.class;
                    default: return Object.class;
                }
            }
         };
	}

	/**
	 * Methode zum Filtern der Ergbnistabelle
	 */
	private void setFilter() {
		DefaultTableModel model = (DefaultTableModel) resultTable.getModel();
		if (model != null) {

			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>();

			RowFilter<TableModel, Object> compoundRowFilter = null;

			try {
				// ArrayList für alle Filter
				ArrayList<RowFilter<TableModel, Object>> filters = new ArrayList<RowFilter<TableModel, Object>>();
				// einzelne Filter definieren
				RowFilter<TableModel, Object> rowFilterArtikel = RowFilter
						.regexFilter(txtArtikel.getText(), 0);
				RowFilter<TableModel, Object> rowFilterVertriebskanal = RowFilter
						.regexFilter(cboVertriebskanal.getSelectedItem()
								.toString(), 2);
				RowFilter<TableModel, Object> rowFilterWarengruppe = RowFilter
						.regexFilter(cboWarengruppe.getSelectedItem()
								.toString(), 1);
				RowFilter<TableModel, Object> rowFilterABCUmsatz = RowFilter
						.regexFilter(cboABCUmsatz.getSelectedItem().toString(),
								7);
				RowFilter<TableModel, Object> rowFilterABCAnzahl = RowFilter
						.regexFilter(cboABCAnzahl.getSelectedItem().toString(),
								3);
				RowFilter<TableModel, Object> rowFilterABCMenge = RowFilter
						.regexFilter(cboABCMenge.getSelectedItem().toString(),
								5);
				RowFilter<TableModel, Object> rowFilterABCGesamt = RowFilter
						.regexFilter(cboABCGesamt.getSelectedItem().toString(),
								9);

				//Hinzufuegen aller Filter zur Liste
				filters.add(rowFilterArtikel);
				filters.add(rowFilterVertriebskanal);
				filters.add(rowFilterWarengruppe);
				filters.add(rowFilterABCUmsatz);
				filters.add(rowFilterABCAnzahl);
				filters.add(rowFilterABCMenge);
				filters.add(rowFilterABCGesamt);

				//Setzen eine And-Filters fuer die Liste alle Filter
				compoundRowFilter = RowFilter.andFilter(filters);

			} catch (java.util.regex.PatternSyntaxException e) {
				return;
			}
			
			//Setzen des zusammengesetzten Filters
			resultTable.setRowSorter(sorter);
			sorter.setModel(model);

			sorter.setRowFilter(compoundRowFilter);

			lblCountRows.setText("Zeilen: " + resultTable.getRowCount());
		} else {
			// DefaultTableModel ist null!
			return;
		}
	}

	/**
	 * Setzt den Filter der Tabelle zurueck
	 */
	private void resetFilter() {

		// Filterkriterien Initialisierungswert setzen
		txtArtikel.setText("");
		cboVertriebskanal.setSelectedIndex(0);
		cboWarengruppe.setSelectedIndex(0);
		cboABCUmsatz.setSelectedIndex(0);
		cboABCAnzahl.setSelectedIndex(0);
		cboABCMenge.setSelectedIndex(0);
		cboABCGesamt.setSelectedIndex(0);

		// JTable Filter zurücksetzten
		DefaultTableModel model = (DefaultTableModel) resultTable.getModel();
		if (model != null) {
			TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>();
			resultTable.setRowSorter(sorter);
			sorter.setModel(model);
			sorter.setRowFilter(null);
		} else {
			// DefaultTableModel is null!
			return;
		}
		
		lblCountRows.setText("Zeilen: " + resultTable.getRowCount());
	}

	/**
	 * Oeffnet einen Dateiauswahl-Dialog zum auswaehlen eines Speicherorts der Excel-Datei
	 * Ruft eine Klasse in der Logik auf, welche anhand der Tabelle eine Excel-Datei erstellt
	 */
	private void exportToExcel() {

		//Dateiauswahl-Dialog
		final JFileChooser fc;

		String path = System.getProperty("user.home");
		File file = new File(path.trim());

		fc = new JFileChooser(path);
		fc.setSelectedFile(new File("ABCExport.xlsx"));
		;
		fc.setDialogType(JFileChooser.SAVE_DIALOG);
		fc.setDialogTitle("Speichern unter...");

		int returnVal = fc.showSaveDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			path = fc.getSelectedFile().toString();
			if (!path.endsWith(".xlsx")) {
				path = path + ".xlsx";
			}
			file = new File(path);
			//Logik-Klasse zum Exportieren der Tabelle in eine Excel-Datei
			ExcelExport p = new ExcelExport(resultTable, file);
			p.start();
		}
	}
	
	public void setFilterFromExternal() {
		MainWindow.panelErgebnis.setFilter();
	}
	
	public void resetFilterFromExternal() {
		MainWindow.panelErgebnis.resetFilter();
	}
}
