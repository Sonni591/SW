package gui;
import interfaces.IABCRepository;

import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.BoxLayout;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Chart extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private IABCRepository repository;
	String headerText;
	String selectedLager;
	String selectedWg;
	int selectedLagerId;
	int selectedWgId;
	int kriteriumID;
	JTable resultTable;
	ResultSet ChartData = null;
	int selectedOptionNr;
	DefaultCategoryDataset ChartDataObjects;
	JFrame reportFrame;
	
	public Chart(IABCRepository _repository) {
		repository = _repository;
	}
	
	
	public void generateChart() {
		
		// Parameter aus der Auswahl übernehmen
		getParameter();
		// Tabelle mit den Daten aufbauen
		setTableData();
		// Chart aufbauen
		setChart();
		// Fenster initialisieren
		initialise();
		
		// Auswahlframe für Berichte schließen
		FrameBerichteParameter.dialogFrame.dispose();
		
	}
	
	public void initialise() {
		
		DefaultCategoryDataset ds = ChartDataObjects;
	
		JFreeChart chart = ChartFactory.createStackedBarChart(
				"", 
				"Kategorie", 
				"Prozent", 
				ds, 
				PlotOrientation.VERTICAL, 
				true, 
				true, 
				false);
//		ChartPanel erstellen
		ChartPanel chartPanel = new ChartPanel(chart);
		
		//Neues Hauptfenster
//		JFrame reportFrame;
		reportFrame = new JFrame();	
		reportFrame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		reportFrame.getContentPane().add(panel, BorderLayout.NORTH);
		
		//Titel setzen
		JLabel lblHeaderText = new JLabel(headerText);
		lblHeaderText.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblHeaderText.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel lblwgString = new JLabel(selectedLager);
		lblwgString.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblwgString.setHorizontalAlignment(SwingConstants.CENTER);
		//panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		lblHeaderText.setFont(new Font("Tahoma", Font.BOLD, 16));
		panel.add(lblHeaderText);
		panel.add(lblwgString);
		JLabel lbllagerString = new JLabel(selectedWg);
		lbllagerString.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(lbllagerString);
		
		//Center Panel mit Chart
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GRAY);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		panel_1.setPreferredSize(new Dimension(600,400));
		panel_1.add(chartPanel);
		reportFrame.getContentPane().add(panel_1, BorderLayout.CENTER);
		
		//South Panel mit Table
		JScrollPane panel_2 = new JScrollPane();
		panel_2.setBackground(Color.WHITE);
		panel_2.setPreferredSize(new Dimension(600,110));
		panel_2.setBorder(new EmptyBorder(15,0,0,0));
		reportFrame.getContentPane().add(panel_2, BorderLayout.SOUTH);
		
		panel_2.setViewportView(resultTable);
		resultTable.setShowGrid(true);
		resultTable.setShowHorizontalLines(true);
		resultTable.setShowVerticalLines(true);
		resultTable.setGridColor(Color.LIGHT_GRAY);
		resultTable.getTableHeader().setReorderingAllowed(false);
		
		resultTable.addMouseListener(new MouseAdapter() {
			   public void mouseClicked(MouseEvent e) {
			      if (e.getClickCount() == 2) {
			    	  setFilterErgebnisPanel((JTable)e.getSource());
			         }
			   }
			});
		
		//Fenster packen und anzeigen
		reportFrame.pack();
		reportFrame.setVisible(true);
		reportFrame.toFront();
		reportFrame.requestFocus();
		
	}
	
	public void getParameter() {
		// selektierte Lager und Warengruppe übernehmen
		selectedLager = (String) FrameBerichteParameter.cboVertriebskanal1.getSelectedItem();
		selectedWg = (String) FrameBerichteParameter.cboWarengruppen.getSelectedItem();
		
		// IDs zur gewählten Lager und Warengruppe
		selectedLagerId = repository.getVertriebsKanalID(selectedLager);
		selectedWgId = repository.getWarengruppeID(selectedWg);
		
		// KriteriumID in Anhängigkeit der ausgewählten Verteilung lesen
		// Auswahl: Verteilung von Bestand und Umsatz
		if(FrameBerichteParameter.rdbtnChartOption1.isSelected()){
			kriteriumID = 1;	
			headerText = FrameBerichteParameter.rdbtnChartOption1.getText();
		}
		// Auswahl: Verteilung von Bestand und Absatz
		else if(FrameBerichteParameter.rdbtnChartOption2.isSelected()){
			kriteriumID = 2;	
			headerText = FrameBerichteParameter.rdbtnChartOption2.getText();
		}
		// Auswahl: Aufteilung der Artikel
		else if(FrameBerichteParameter.rdbtnChartOption3.isSelected()){
			kriteriumID = 3;	
			headerText = FrameBerichteParameter.rdbtnChartOption3.getText();
		}
	}
	
	public void setTableData() {
		
		//Table erstellen
		resultTable = new JTable() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				// alle Spalten nicht editierbar setzen
				return false;
			}
		};
		
		// Tabelle mit Daten befüllen
		ResultSet berichtsDaten = null;
		// Daten lesen
		berichtsDaten = repository.getChartData(selectedLagerId, selectedWgId, kriteriumID);
		try {
			// Tabelle unter dem Chart aufbauen
			resultTable.setModel(buildTableModel(berichtsDaten));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
			
	
	public static DefaultTableModel buildTableModel(ResultSet rs)
			throws SQLException {

		ResultSetMetaData metaData = rs.getMetaData();

		/////////////////////////////////
		// Spalten der Berichtstabelle //
		/////////////////////////////////
		
		Vector<String> columnNames = new Vector<String>();
		columnNames.add(metaData.getColumnName(1)); //BerichtKZ
		columnNames.add(metaData.getColumnName(5)); //AnzahlArtikel
		
		// Auswahl: Verteilung von Bestand und Umsatz
		if(FrameBerichteParameter.rdbtnChartOption1.isSelected()){
			// Absatz in Menge
			if(FrameBerichteParameter.rdbtnAbsatzInMenge.isSelected()) {
				columnNames.add(metaData.getColumnName(8)); // Bestand
				columnNames.add(metaData.getColumnName(6)); // Jahresumsatz
			}
			// Absatz in Wert
			else if (FrameBerichteParameter.rdbtnAbsatzInWert.isSelected()) {
				columnNames.add(metaData.getColumnName(10)); // BestandsWert
				columnNames.add(metaData.getColumnName(6)); // JahresUmsatz
			}
				
		}
		// Auswahl: Verteilung von Bestand und Absatz
		else if(FrameBerichteParameter.rdbtnChartOption2.isSelected()){
			// Absatz in Menge
			if(FrameBerichteParameter.rdbtnAbsatzInMenge.isSelected()) {
				columnNames.add(metaData.getColumnName(8)); // Bestand
				columnNames.add(metaData.getColumnName(7)); // JahresMenge		
			}
			// Absatz in Wert
			else if (FrameBerichteParameter.rdbtnAbsatzInWert.isSelected()) {
				columnNames.add(metaData.getColumnName(10)); // BestandsWert
				columnNames.add(metaData.getColumnName(9)); // JahresMengeWert		
			}		
		}
		// Auswahl: Aufteilung der Artikel
		else if(FrameBerichteParameter.rdbtnChartOption3.isSelected()){
			// Absatz in Menge
			if(FrameBerichteParameter.rdbtnAbsatzInMenge.isSelected()) {
				columnNames.add(metaData.getColumnName(8)); // Bestand
				columnNames.add(metaData.getColumnName(7)); // JahresMenge		
			}
			// Absatz in Wert
			else if (FrameBerichteParameter.rdbtnAbsatzInWert.isSelected()) {
				columnNames.add(metaData.getColumnName(10)); // BestandsWert
				columnNames.add(metaData.getColumnName(9)); // JahresMengeWert		
			}		
		}
		
		/////////////////////////////////
		// Daten der Bestandstabelle //
		/////////////////////////////////
		
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		while (rs.next()) {
			Vector<Object> vector = new Vector<Object>();
			
			vector.add(rs.getObject(1)); // BerichtKZ
			vector.add(rs.getObject(5)); // AnzahlArtikel
			
			// Auswahl: Verteilung von Bestand und Umsatz
			if(FrameBerichteParameter.rdbtnChartOption1.isSelected()){
				// Absatz in Menge
				if(FrameBerichteParameter.rdbtnAbsatzInMenge.isSelected()) {
					vector.add(rs.getObject(8)); // Bestand
					vector.add(rs.getObject(6));  // Jahresumsatz
				}
				// Absatz in Wert
				else if (FrameBerichteParameter.rdbtnAbsatzInWert.isSelected()) {
					vector.add(rs.getObject(10)); // BestandsWert
					vector.add(rs.getObject(6)); // JahresUmsatz
				}
					
			}
			// Auswahl: Verteilung von Bestand und Absatz
			else if(FrameBerichteParameter.rdbtnChartOption2.isSelected()){
				// Absatz in Menge
				if(FrameBerichteParameter.rdbtnAbsatzInMenge.isSelected()) {
					vector.add(rs.getObject(8)); // Bestand
					vector.add(rs.getObject(7));  // JahresMenge
				}
				// Absatz in Wert
				else if (FrameBerichteParameter.rdbtnAbsatzInWert.isSelected()) {
					vector.add(rs.getObject(10)); // BestandsWert
					vector.add(rs.getObject(9)); // JahresMengeWert		
				}		
			}
			// Auswahl: Aufteilung der Artikel
			else if(FrameBerichteParameter.rdbtnChartOption3.isSelected()){
				// Absatz in Menge
				if(FrameBerichteParameter.rdbtnAbsatzInMenge.isSelected()) {
					vector.add(rs.getObject(8)); // Bestand
					vector.add(rs.getObject(7));  // JahresMenge	
				}
				// Absatz in Wert
				else if (FrameBerichteParameter.rdbtnAbsatzInWert.isSelected()) {
					vector.add(rs.getObject(10)); // BestandsWert
					vector.add(rs.getObject(9)); // JahresMengeWert
				}		
			}
			// Zeile zur Tabelle hinzufügen
			data.add(vector);	

		}
		
		return new DefaultTableModel(data, columnNames);
	}
	
	public void setChart() {
		
		ChartDataObjects = new DefaultCategoryDataset();
		
		// Werte der Summen-Zeile
		double anzahlSum = Double.parseDouble(resultTable.getValueAt(3, 1).toString());
		double wert2Sum =  Double.parseDouble(resultTable.getValueAt(3, 2).toString());
		double wert3Sum =  Double.parseDouble(resultTable.getValueAt(3, 3).toString());
		
		// Für jede Zeile die Balkenanteile für Balken 1, 2, 3 berechnen
		for(int i=0; i<3; i++) {
			ChartDataObjects.addValue((Double.parseDouble(resultTable.getValueAt(i, 1).toString())/(double)anzahlSum*100), 
									   (String) resultTable.getValueAt(i, 0) , 
									   (String) resultTable.getColumnModel().getColumn(1).getHeaderValue());
			ChartDataObjects.addValue((Double.parseDouble(resultTable.getValueAt(i, 2).toString())/(double)wert2Sum*100),
										(String) resultTable.getValueAt(i, 0), 
										(String) resultTable.getColumnModel().getColumn(2).getHeaderValue());
			ChartDataObjects.addValue((Double.parseDouble(resultTable.getValueAt(i, 3).toString())/(double)wert3Sum*100),
										(String) resultTable.getValueAt(i, 0),
										(String) resultTable.getColumnModel().getColumn(3).getHeaderValue());

		}
			
	}

	private void setFilterErgebnisPanel(JTable table) {
        
		int row = table.getSelectedRow();
        
		// Filter zurücksetzen
        MainWindow.panelErgebnis.resetFilterFromExternal();
		
        // Vertriebskanal setzen
        MainWindow.panelErgebnis.setVertriebskanal(selectedLager);
        // Warengruppe setzen
        MainWindow.panelErgebnis.setWarengruppe(selectedWg);
        
        // ABC in Anhängigkeit der ausgewählten Verteilung lesen
 		// Auswahl: Verteilung von Bestand und Umsatz
 		if(FrameBerichteParameter.rdbtnChartOption1.isSelected()){
 			MainWindow.panelErgebnis.setABCUmsatz(resultTable.getValueAt(row, 0).toString());
 		}
 		// Auswahl: Verteilung von Bestand und Absatz
 		else if(FrameBerichteParameter.rdbtnChartOption2.isSelected()){
 			MainWindow.panelErgebnis.setABCMenge(resultTable.getValueAt(row, 0).toString());
 		}
 		// Auswahl: Aufteilung der Artikel
 		else if(FrameBerichteParameter.rdbtnChartOption3.isSelected()){
 			MainWindow.panelErgebnis.setABCAnzahl(resultTable.getValueAt(row, 0).toString());
 		}
        
        // Filter setzen
        MainWindow.panelErgebnis.setFilterFromExternal();
        
        // Hauptfenster in den Vordergrund bringen
		MainWindow.frame.toFront();
		MainWindow.frame.requestFocus();   
        
	}
	
}