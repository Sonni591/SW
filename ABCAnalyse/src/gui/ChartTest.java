package gui;
import interfaces.IABCRepository;

import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JFrame;

import objects.ABCEinteilung;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import sqliteRepository.CrudBefehle;

import javax.swing.BoxLayout;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.Component;


public class ChartTest extends JPanel{
	private IABCRepository repository;
	String selectedOption;
	JTable resultTable;

	public ChartTest(IABCRepository _repository) {
		repository = _repository;
	}
	/**
	 * @wbp.parser.entryPoint
	 */
	public void getDataForChart(){
		//TODO: Strings aus der GUI auslesen, die INT Werte dazu aus DB auslesen und übergeben.
		//String Vertriebskanal = (String) FrameBerichteParameter.cboVertriebskanal1.getSelectedItem();
		//String Warengruppe = (String) FrameBerichteParameter.cboWarengruppen.getSelectedItem();
		//Dummy Werte für Lager = 10 und Warengruppe = 1
		FrameBerichteParameter.dialogFrame.dispose();
		ResultSet ChartData = null;
		DefaultCategoryDataset ChartDataObjects = null;
		
		if(FrameBerichteParameter.rdbtnChartOption1.isSelected()){
			selectedOption = FrameBerichteParameter.rdbtnChartOption1.getLabel();
			ChartData = repository.selectChartOption1(10, 1);
			ChartDataObjects = new DefaultCategoryDataset();
			int AnzahlSum = 0;
			int BestandswertSum = 0;
			int UmsatzwertSum = 0;
			try {
				while(ChartData.next())
				{
					if(ChartData.getString(4).equals("SUM")){
					AnzahlSum = ChartData.getInt(1);
					BestandswertSum = ChartData.getInt(2);
					UmsatzwertSum = ChartData.getInt(3);
					break;
					}
				}
				ChartData = repository.selectChartOption1(10, 1);
				while(ChartData.next())
				{
					if(!ChartData.getString(4).equals("SUM")){
						ChartDataObjects.addValue((((float)ChartData.getInt(1)/(float)AnzahlSum)*100), ChartData.getString(4), "Anzahl Artikel");
						ChartDataObjects.addValue((((float)ChartData.getInt(2)/(float)BestandswertSum)*100), ChartData.getString(4), "Bestandswert");
						ChartDataObjects.addValue((((float)ChartData.getInt(3)/(float)UmsatzwertSum)*100), ChartData.getString(4), "Umsatzwert");
					}
				
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(FrameBerichteParameter.rdbtnChartOption2.isSelected()){
			selectedOption = FrameBerichteParameter.rdbtnChartOption2.getLabel();
			ChartData = repository.selectChartOption2(10, 1);
			ChartDataObjects = new DefaultCategoryDataset();
			int AnzahlSum = 0;
			int BestandStückSum = 0;
			int AbsatzSum = 0;
			try {
				while(ChartData.next())
				{
					if(ChartData.getString(4).equals("SUM")){
					AnzahlSum = ChartData.getInt(1);
					BestandStückSum = ChartData.getInt(2);
					AbsatzSum = ChartData.getInt(3);
					break;
					}
				}
				ChartData = repository.selectChartOption2(10, 1);
				while(ChartData.next())
				{
					if(!ChartData.getString(4).equals("SUM")){
						ChartDataObjects.addValue((((float)ChartData.getInt(1)/(float)AnzahlSum)*100), ChartData.getString(4), "Anzahl Artikel");
						ChartDataObjects.addValue((((float)ChartData.getInt(2)/(float)BestandStückSum)*100), ChartData.getString(4), "Bestand in Stück");
						ChartDataObjects.addValue((((float)ChartData.getInt(3)/(float)AbsatzSum)*100), ChartData.getString(4), "Jahres Absatz");
					}
				
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else{
			System.out.println("Option3");
			}
		createchart(ChartDataObjects);
	}
	public void createchart(DefaultCategoryDataset ChartData){
		DefaultCategoryDataset ds = ChartData;
	
		JFreeChart chart = ChartFactory.createStackedBarChart(
				"", 
				"Kategorie", 
				"Prozent", 
				ds, 
				PlotOrientation.VERTICAL, 
				true, 
				true, 
				false);
		//ChartPanel erstellen
		ChartPanel chartPanel = new ChartPanel(chart);
		
		//Neues Hauptfenster
		JFrame reportFrame;
		reportFrame = new JFrame();	
		reportFrame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		reportFrame.getContentPane().add(panel, BorderLayout.NORTH);
		
		//Titel setzen
		JLabel lblPlatzhalter = new JLabel(selectedOption);
		lblPlatzhalter.setFont(new Font("Tahoma", Font.BOLD, 18));
		panel.add(lblPlatzhalter);
		
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
		panel_2.setPreferredSize(new Dimension(600,250));
		panel_2.setBorder(new EmptyBorder(15,0,0,0));
		reportFrame.getContentPane().add(panel_2, BorderLayout.SOUTH);
		
		//Table erstellen, konfigurieren und mit Daten befüllen
		resultTable = new JTable() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				// alle Spalten nicht editierbar setzen
				return false;
			}
		};
		panel_2.setViewportView(resultTable);
		resultTable.setShowGrid(true);
		resultTable.setShowHorizontalLines(true);
		resultTable.setShowVerticalLines(true);
		resultTable.setGridColor(Color.LIGHT_GRAY);
		resultTable.getTableHeader().setReorderingAllowed(false);
		setTableData();
		
		//Fenster packen und anzeigen
		reportFrame.pack();
		reportFrame.setVisible(true);
		reportFrame.toFront();
		reportFrame.requestFocus();
		
	}
	public void setTableData() {
		ResultSet abcBericht = null;
		try {
			abcBericht = repository
					.getResult(CrudBefehle.getABCBericht);

			resultTable.setModel(buildTableModel(abcBericht));
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		} finally {
			try {
				abcBericht.close();
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
