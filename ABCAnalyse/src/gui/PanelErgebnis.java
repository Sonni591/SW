package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import datasource.CrudBefehle;
import datasource.CrudFunktionen;

import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PanelErgebnis extends JPanel {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private JTable resultTable;
	private JTextField txtArtikel;

	private JComboBox<String> cboVertriebskanal;

	private JComboBox<String> cboABCUmsatz;

	private JComboBox<String> cboABCAnzahl;

	private JComboBox<String> cboABCMenge;

	private JComboBox<String> cboABCGesamt;

	private JComboBox<String> cboWarengruppe;

	public PanelErgebnis() {
		setLayout(new BorderLayout(0, 0));

		JPanel panelErgebnisHeader = new JPanel();
		add(panelErgebnisHeader, BorderLayout.NORTH);

		JLabel lblArtikel = new JLabel("Artikel:");

		txtArtikel = new JTextField();
		txtArtikel.setColumns(10);
		txtArtikel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				setFilter();
			}
		});

		JLabel lblVertriebskanal = new JLabel("Vertriebskanal:");

		JLabel lblUmsatz = new JLabel("Umsatz:");

		JLabel lblAnzahl = new JLabel("Anzahl:");

		JLabel lblMenge = new JLabel("Menge:");

		String[] abcKlassfizierung = { "", "A", "B", "C" };

		cboVertriebskanal = new JComboBox<String>();
		// Vertriebskanäle lesen
		ArrayList<String> vertriebskanaele = CrudFunktionen.getVertriebskanale();
		cboVertriebskanal.addItem(""); // leere Auwahl hinzufügen;
		for (String s : vertriebskanaele) {
			cboVertriebskanal.addItem(s);
		}

		cboWarengruppe = new JComboBox<String>();
		// Warengruppen lesen
		ArrayList<String> warengruppen = CrudFunktionen.getWarengruppen();
		cboWarengruppe.addItem(""); // leere Auwahl hinzufügen;
		for (String s : warengruppen) {
			cboWarengruppe.addItem(s);
		}

		cboABCUmsatz = new JComboBox<String>();
		for (String s : abcKlassfizierung) {
			cboABCUmsatz.addItem(s);
		}

		cboABCAnzahl = new JComboBox<String>();
		for (String s : abcKlassfizierung) {
			cboABCAnzahl.addItem(s);
		}

		cboABCMenge = new JComboBox<String>();
		for (String s : abcKlassfizierung) {
			cboABCMenge.addItem(s);
		}

		cboABCGesamt = new JComboBox<String>();
		for (String s : abcKlassfizierung) {
			cboABCGesamt.addItem(s);
		}

		JButton btnSetzeFilter = new JButton("Filter setzen");
		btnSetzeFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setFilter();
			}
		});

		JButton btnResetFilter = new JButton("zur\u00FCcksetzen");
		btnResetFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetFilter();
			}
		});

		JLabel lblGesamt = new JLabel("Gesamt:");

		JLabel lblWarengruppe = new JLabel("Warengruppe:");

		JButton btnExportExcel = new JButton("Excel-Export");
		btnExportExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exportToExcel();
			}
		});
		
		JButton btnBerichte = new JButton("Berichte");
		btnBerichte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameBerichteParameter BerichteDialog = new FrameBerichteParameter();
				BerichteDialog.initialize();
			}
		});

		GroupLayout gl_panelErgebnisHeader = new GroupLayout(
				panelErgebnisHeader);
		gl_panelErgebnisHeader.setHorizontalGroup(
			gl_panelErgebnisHeader.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelErgebnisHeader.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelErgebnisHeader.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelErgebnisHeader.createSequentialGroup()
							.addGroup(gl_panelErgebnisHeader.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelErgebnisHeader.createSequentialGroup()
									.addComponent(lblArtikel)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(txtArtikel, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
								.addGroup(gl_panelErgebnisHeader.createSequentialGroup()
									.addComponent(lblVertriebskanal, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panelErgebnisHeader.createParallelGroup(Alignment.LEADING)
										.addComponent(cboWarengruppe, 0, 63, Short.MAX_VALUE)
										.addComponent(cboVertriebskanal, 0, 63, Short.MAX_VALUE))))
							.addGap(18))
						.addGroup(gl_panelErgebnisHeader.createSequentialGroup()
							.addComponent(lblWarengruppe)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGroup(gl_panelErgebnisHeader.createParallelGroup(Alignment.LEADING)
						.addComponent(lblGesamt)
						.addComponent(lblUmsatz)
						.addComponent(lblAnzahl)
						.addComponent(lblMenge))
					.addGroup(gl_panelErgebnisHeader.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panelErgebnisHeader.createSequentialGroup()
							.addGap(20)
							.addGroup(gl_panelErgebnisHeader.createParallelGroup(Alignment.LEADING)
								.addComponent(cboABCGesamt, 0, 0, Short.MAX_VALUE)
								.addComponent(cboABCMenge, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(cboABCAnzahl, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(cboABCUmsatz, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addGroup(gl_panelErgebnisHeader.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cboABCGesamt, 0, 0, Short.MAX_VALUE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelErgebnisHeader.createParallelGroup(Alignment.LEADING)
						.addComponent(btnResetFilter)
						.addComponent(btnSetzeFilter, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panelErgebnisHeader.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(btnBerichte, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnExportExcel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panelErgebnisHeader.setVerticalGroup(
			gl_panelErgebnisHeader.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelErgebnisHeader.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelErgebnisHeader.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblArtikel)
						.addComponent(txtArtikel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblUmsatz)
						.addComponent(cboABCUmsatz, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSetzeFilter))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelErgebnisHeader.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelErgebnisHeader.createSequentialGroup()
							.addGroup(gl_panelErgebnisHeader.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblAnzahl)
								.addComponent(cboABCAnzahl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnResetFilter))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panelErgebnisHeader.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblMenge)
								.addComponent(cboABCMenge, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnExportExcel)))
						.addGroup(gl_panelErgebnisHeader.createSequentialGroup()
							.addGroup(gl_panelErgebnisHeader.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblVertriebskanal)
								.addComponent(cboVertriebskanal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panelErgebnisHeader.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblWarengruppe)
								.addComponent(cboWarengruppe, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelErgebnisHeader.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblGesamt)
						.addComponent(cboABCGesamt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnBerichte))
					.addGap(17))
		);
		panelErgebnisHeader.setLayout(gl_panelErgebnisHeader);

		JPanel panelErgebnisContent = new JPanel();
		add(panelErgebnisContent, BorderLayout.CENTER);
		panelErgebnisContent.setLayout(new BorderLayout(0, 0));

		resultTable = new JTable();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(resultTable);
		panelErgebnisContent.add(scrollPane);

		resultTable.setShowGrid(true);
		resultTable.setShowHorizontalLines(true);
		resultTable.setShowVerticalLines(true);
		resultTable.setGridColor(Color.LIGHT_GRAY);

		JPanel panelErgebnisFooter = new JPanel();
		add(panelErgebnisFooter, BorderLayout.SOUTH);
		panelErgebnisFooter.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		// Deaktiviert das verschieben der Spalten
		resultTable.getTableHeader().setReorderingAllowed(false);

		getTableData();

		// Filtern der Tabelle - Test
		// enableSorting(resultTable);

	}

	public void getTableData() {
		ResultSet abcEinteilungResult = null;
		try {
			abcEinteilungResult = CrudFunktionen.getResult(
					MainWindow.DBconnection, CrudBefehle.selectABCResultView);

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
				// RowFilter<TableModel, Object> rowFilterVertriebskanal =
				// RowFilter.regexFilter(cboVertriebskanal.getSelectedItem().toString(),1);
				// RowFilter<TableModel, Object> rowFilterWarengruppe =
				// RowFilter.regexFilter(cboWarengruppe.getSelectedItem().toString(),999);
				// // TODO
				RowFilter<TableModel, Object> rowFilterABCUmsatz = RowFilter
						.regexFilter(cboABCUmsatz.getSelectedItem().toString(),
								2);
				RowFilter<TableModel, Object> rowFilterABCAnzahl = RowFilter
						.regexFilter(cboABCAnzahl.getSelectedItem().toString(),
								5);
				RowFilter<TableModel, Object> rowFilterABCMenge = RowFilter
						.regexFilter(cboABCMenge.getSelectedItem().toString(),
								8);
				RowFilter<TableModel, Object> rowFilterABCGesamt = RowFilter
						.regexFilter(cboABCGesamt.getSelectedItem().toString(),
								11);

				filters.add(rowFilterArtikel);
				// filters.add(rowFilterVertriebskanal);
				// filters.add(rowFilterWarengruppe);
				filters.add(rowFilterABCUmsatz);
				filters.add(rowFilterABCAnzahl);
				filters.add(rowFilterABCMenge);
				filters.add(rowFilterABCGesamt);

				compoundRowFilter = RowFilter.andFilter(filters);

			} catch (java.util.regex.PatternSyntaxException e) {
				return;
			}
			resultTable.setRowSorter(sorter);
			sorter.setModel(model);

			sorter.setRowFilter(compoundRowFilter);
		} else {
			// DefaultTableModel ist null!
			return;
		}
	}

	private void resetFilter() {

		// Filterkriterien zurücksetzten
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
	}

//	private ArrayList<String> getVertriebskanale() {
//
//		ArrayList<String> vkList = new ArrayList<String>();
//
//		ResultSet rsVertriebskanale = null;
//		try {
//			rsVertriebskanale = CrudFunktionen
//					.getResult(MainWindow.DBconnection,
//							CrudBefehle.selectVertriebskanaele);
//
//			while (rsVertriebskanale.next()) {
//
//				String bezeichnung = rsVertriebskanale.getString("Bezeichnung");
//				vkList.add(bezeichnung);
//
//			}
//			return vkList;
//
//		} catch (Exception e) {
//			System.err.println(e);
//			e.printStackTrace();
//			return null;
//		} finally {
//			try {
//				rsVertriebskanale.close();
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//
//	}
//
//	private ArrayList<String> getWarengruppen() {
//
//		ArrayList<String> wgList = new ArrayList<String>();
//
//		ResultSet rsWarengruppen = null;
//		try {
//			rsWarengruppen = CrudFunktionen.getResult(MainWindow.DBconnection,
//					CrudBefehle.selectWarengruppen);
//
//			while (rsWarengruppen.next()) {
//
//				String bezeichnung = rsWarengruppen.getString("Bezeichnung");
//				wgList.add(bezeichnung);
//
//			}
//			return wgList;
//
//		} catch (Exception e) {
//			System.err.println(e);
//			e.printStackTrace();
//			return null;
//		} finally {
//			try {
//				rsWarengruppen.close();
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//
//	}

	private void exportToExcel() {

		// Create a file chooser
		final JFileChooser fc;
		
        String path = System.getProperty("user.home"); 
        File file = new File(path.trim()); 

        fc = new JFileChooser(path); 
        fc.setSelectedFile(new File("ABCExport.xlsx"));;
        fc.setDialogType(JFileChooser.SAVE_DIALOG);  
        fc.setDialogTitle("Speichern unter..."); 

		//fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fc.showSaveDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			path = fc.getSelectedFile().toString();
			if(!path.endsWith(".xlsx"))
			{
				path = path + ".xlsx";
			}
			file = new File(path);
			ExcelThread p = new ExcelThread(resultTable, file);
			p.start();
		}
	}
}

class ExcelThread extends Thread {
	JTable resultTable;
	File file;

	ExcelThread(JTable resultTable, File file) {
		this.resultTable = resultTable;
		this.file = file;	
	}

	public void run() {
		try {
			final JDialog dlg = new JDialog();
			JProgressBar dpb = new JProgressBar(0, 500);
			dpb.setMaximum(3);
			dpb.setValue(0);
			dlg.add(BorderLayout.CENTER, dpb);
			JLabel lblDpbState = new JLabel("Lade Spaltennamen");
			dlg.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			dlg.setSize(300, 125);
			dlg.setLocationRelativeTo(null);
			dlg.setVisible(true);

			Workbook fWorkbook = new XSSFWorkbook();
			Sheet fSheet = fWorkbook.createSheet("newSheet");
			Font sheetTitleFont = fWorkbook.createFont();
			CellStyle cellStyle = fWorkbook.createCellStyle();

			sheetTitleFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			// sheetTitleFont.setColor();
			TableModel model = resultTable.getModel();
			dlg.add(BorderLayout.NORTH, lblDpbState);
			// Spaltennamen
			TableColumnModel tcm = resultTable.getColumnModel();
			Row fRow = fSheet.createRow(0);
			for (int j = 0; j < resultTable.getColumnCount(); j++) {
				Cell cell = fRow.createCell(j);
				cell.setCellValue(tcm
						.getColumn(resultTable.convertColumnIndexToModel(j))
						.getHeaderValue().toString());

			}
			lblDpbState.setText("Lade Daten");
			dpb.setValue(1);
			// Daten der Tabelle
			for (int i = 0; i < resultTable.getRowCount(); i++) {
				fRow = fSheet.createRow(i + 1);
				for (int j = 0; j < resultTable.getColumnCount(); j++) {
					Cell cell = fRow.createCell(j);
					cell.setCellValue(model.getValueAt(
							resultTable.convertRowIndexToModel(i),
							resultTable.convertColumnIndexToModel(j))
							.toString());
					cell.setCellStyle(cellStyle);
				}
			}
			lblDpbState.setText("Auf Festplatte Schreiben");
			dpb.setValue(2);
			FileOutputStream fileOutputStream;
			fileOutputStream = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(
					fileOutputStream);
			fWorkbook.write(bos);
			dpb.setValue(3);
			Thread.sleep(100);
			bos.close();
			fileOutputStream.close();
			System.out.println("Finished");
			 dlg.setVisible(false);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
