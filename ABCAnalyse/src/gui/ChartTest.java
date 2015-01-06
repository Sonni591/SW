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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.FlowLayout;


public class ChartTest extends JPanel{
	private IABCRepository repository;
	String selectedOption;
	String selectedLager;
	String selectedWg;
	int selectedLagerId;
	int selectedWgId;
	JTable resultTable;
	ResultSet ChartData = null;
	int selectedOptionNr;

	public ChartTest(IABCRepository _repository) {
		repository = _repository;
	}
	/**
	 * @wbp.parser.entryPoint
	 */
	public void getDataForChart(){

		FrameBerichteParameter.dialogFrame.dispose();
		DefaultCategoryDataset ChartDataObjects = null;
		
		selectedLager = (String) FrameBerichteParameter.cboVertriebskanal1.getSelectedItem();
		selectedWg = (String) FrameBerichteParameter.cboWarengruppen.getSelectedItem();
		
		selectedLagerId = repository.getVertriebsKanalID(selectedLager);
		selectedWgId = repository.getWarengruppeID(selectedWg);

		if(FrameBerichteParameter.rdbtnChartOption1.isSelected()){
			selectedOptionNr = 1;
			selectedOption = FrameBerichteParameter.rdbtnChartOption1.getLabel();
			ChartData = repository.selectChartOption1(selectedLagerId, selectedWgId);
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
				ChartData = repository.selectChartOption1(selectedLagerId, selectedWgId);
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
			selectedOptionNr = 2;
			selectedOption = FrameBerichteParameter.rdbtnChartOption2.getLabel();
			ChartData = repository.selectChartOption2(selectedLagerId, selectedWgId);
			ChartDataObjects = new DefaultCategoryDataset();
			int AnzahlSum = 0;
			int BestandStueckSum = 0;
			int AbsatzSum = 0;
			try {
				while(ChartData.next())
				{
					if(ChartData.getString(4).equals("SUM")){
					AnzahlSum = ChartData.getInt(1);
					BestandStueckSum = ChartData.getInt(2);
					AbsatzSum = ChartData.getInt(3);
					break;
					}
				}
				ChartData = repository.selectChartOption2(selectedLagerId,selectedWgId);
				while(ChartData.next())
				{
					if(!ChartData.getString(4).equals("SUM")){
						ChartDataObjects.addValue((((float)ChartData.getInt(1)/(float)AnzahlSum)*100), ChartData.getString(4), "Anzahl Artikel");
						ChartDataObjects.addValue((((float)ChartData.getInt(2)/(float)BestandStueckSum)*100), ChartData.getString(4), "Bestand in St�ck");
						ChartDataObjects.addValue((((float)ChartData.getInt(3)/(float)AbsatzSum)*100), ChartData.getString(4), "Jahres Absatz");
					}
				
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else{
			selectedOptionNr = 3;
			selectedOption = FrameBerichteParameter.rdbtnChartOption3.getLabel();
			ChartData = repository.selectChartOption3(selectedLagerId, selectedWgId);
			ChartDataObjects = new DefaultCategoryDataset();
			int AnzahlSum = 0;
			int BestandStueckSum = 0;
			int AbsatzSum = 0;
			try {
				while(ChartData.next())
				{
					if(ChartData.getString(4).equals("SUM")){
					AnzahlSum = ChartData.getInt(1);
					BestandStueckSum = ChartData.getInt(2);
					AbsatzSum = ChartData.getInt(3);
					break;
					}
				}
				ChartData = repository.selectChartOption2(selectedLagerId,selectedWgId);
				while(ChartData.next())
				{
					if(!ChartData.getString(4).equals("SUM")){
						ChartDataObjects.addValue((((float)ChartData.getInt(1)/(float)AnzahlSum)*100), ChartData.getString(4), "Anzahl Artikel");
						ChartDataObjects.addValue((((float)ChartData.getInt(2)/(float)BestandStueckSum)*100), ChartData.getString(4), "Bestand in St�ck");
						ChartDataObjects.addValue((((float)ChartData.getInt(3)/(float)AbsatzSum)*100), ChartData.getString(4), "Jahres Absatz");
					}
				
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		lblPlatzhalter.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblPlatzhalter.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel lblwgString = new JLabel(selectedLager);
		lblwgString.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblwgString.setHorizontalAlignment(SwingConstants.CENTER);
		//panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		lblPlatzhalter.setFont(new Font("Tahoma", Font.BOLD, 18));
		panel.add(lblPlatzhalter);
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
		panel_2.setPreferredSize(new Dimension(600,250));
		panel_2.setBorder(new EmptyBorder(15,0,0,0));
		reportFrame.getContentPane().add(panel_2, BorderLayout.SOUTH);
		
		//Table erstellen, konfigurieren und mit Daten bef�llen
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
		
		resultTable.addMouseListener(new MouseAdapter() {
			   public void mouseClicked(MouseEvent e) {
			      if (e.getClickCount() == 2) {
			         JTable target = (JTable)e.getSource();
			         int row = target.getSelectedRow();
			         String classValue = resultTable.getValueAt(row, 0).toString();
			         String kriteriumValue = resultTable.getValueAt(row, 1).toString();
			         String lagerValue = resultTable.getValueAt(row, 2).toString();
			         String wgValue = resultTable.getValueAt(row, 3).toString();
			         System.out.println("Klasse: " + classValue);
			         System.out.println("Kriterium: " + kriteriumValue);
			         System.out.println("Lager: " + lagerValue);
			         System.out.println("Warengruppe: " + wgValue);
			         }
			   }
			});
		
		//Fenster packen und anzeigen
		reportFrame.pack();
		reportFrame.setVisible(true);
		reportFrame.toFront();
		reportFrame.requestFocus();
		
	}
	public void setTableData() {
		ResultSet abcBericht = null;
		try {
			if(selectedOptionNr == 1){
				abcBericht = repository.selectTableOption1(selectedLagerId, selectedWgId);
			}else if(selectedOptionNr == 2){
				abcBericht = repository.selectTableOption2(selectedLagerId, selectedWgId);
			}else if(selectedOptionNr == 3){
				abcBericht = repository.selectTableOption3(selectedLagerId, selectedWgId);
				
			}			

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
