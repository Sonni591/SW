import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JTabbedPane;

import java.awt.CardLayout;

import javax.swing.JLabel;


import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import java.awt.Panel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;


public class MainWindow {

	private JFrame frame;
	private JTextField txtVonDatum;
	private JTextField txtBisDatum;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		connectSqLite();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//Erstellen des Hauptframes
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Panel auf dem alle weiteren Controls liegen k��nnen
		JPanel mainPanel = new JPanel();
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new CardLayout(0, 0));
		
		//TabbedPane f��r die einzelnen Tabpages
		JTabbedPane tabPageContainer = new JTabbedPane(JTabbedPane.TOP);
		tabPageContainer.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		mainPanel.add(tabPageContainer, "name_3337526559634");
		
		//Einzelne Tabpages
		JPanel panelParameter = new JPanel();
		JPanel panelEinteilung = new JPanel();
		JPanel panelZuordnung = new JPanel();
		JPanel panelErgebnis = new JPanel();
		
		//Hinzuf��gen der einzelnen TabPages
		tabPageContainer.addTab("Parameter", panelParameter);
		tabPageContainer.addTab("ABCEinteilung", panelEinteilung);
		tabPageContainer.addTab("ABCZuordnung", panelZuordnung);
		tabPageContainer.addTab("Ergebnis", panelErgebnis);
		//Setzen eines absoluten Layouts
		panelParameter.setLayout(null);
		
		//Label f��r Zeitraum
		JLabel lblZeitraum = new JLabel("Zeitraum");
		lblZeitraum.setBounds(24, 6, 61, 16);
		panelParameter.add(lblZeitraum);
		
		//RadioButton Zeitraum
		JRadioButton rdVom = new JRadioButton("vom:");
		rdVom.setBounds(34, 34, 71, 23);
		panelParameter.add(rdVom);
		
		//Textfeld f��r Von-Datum
		txtVonDatum = new JTextField();
		txtVonDatum.setBounds(100, 33, 104, 25);
		panelParameter.add(txtVonDatum);
		txtVonDatum.setColumns(10);
		
		//RadioButton Jahr
		JRadioButton rdJahr = new JRadioButton("Jahr:");
		rdJahr.setBounds(44, 69, 61, 23);
		panelParameter.add(rdJahr);
		
		//Dropdown-Box f��r Datumsauswahl
		JComboBox<String> cbJahr = new JComboBox<String>();
		cbJahr.addItem(new String("2011"));
		cbJahr.addItem(new String("2012"));
		cbJahr.addItem(new String("2013"));
		cbJahr.setBounds(110, 69, 94, 27);
		panelParameter.add(cbJahr);
		
		//Ein Radiobutton-Gruppe erstellen
		ButtonGroup rdGroup = new ButtonGroup();
		rdGroup.add(rdVom);
		rdGroup.add(rdJahr);
		
		JLabel lblBis = new JLabel("bis:");
		lblBis.setBounds(216, 38, 61, 16);
		panelParameter.add(lblBis);
		
		txtBisDatum = new JTextField();
		txtBisDatum.setBounds(247, 32, 134, 28);
		panelParameter.add(txtBisDatum);
		txtBisDatum.setColumns(10);
		
		Panel buttonBar = new Panel();
		buttonBar.setBounds(0, 182, 429, 50);
		panelParameter.add(buttonBar);
		buttonBar.setLayout(null);
		
		JButton btnStart = new JButton("Start");
		btnStart.setBounds(306, 15, 117, 29);
		buttonBar.add(btnStart);
		
		JButton btnBerichte = new JButton("Berichte");
		btnBerichte.setBounds(177, 15, 117, 29);
		buttonBar.add(btnBerichte);
	}
	
	public void connectSqLite()
	{
		
		Connection connection = null;  
	     ResultSet resultSet = null;  
	     Statement statement = null;  

	     try 
	     {  
	         Class.forName("org.sqlite.JDBC");  
	         connection = DriverManager.getConnection("jdbc:sqlite:SwDB.db");  
	         statement = connection.createStatement();  
	         resultSet = statement  
	                 .executeQuery("SELECT * FROM Warengruppe");  
	         while (resultSet.next()) 
	         {  
	             System.out.println("EMPLOYEE NAME:"  
	                     + resultSet.getString("Bezeichnung"));  
	         }  
	     } 
	     catch (Exception e) 
	     {  
	         e.printStackTrace();  
	     }
	     finally 
	     {  
	         try 
	         {  
	             resultSet.close();  
	             statement.close();  
	             connection.close();  
	         } 
	         catch (Exception e) 
	         {  
	             e.printStackTrace();  
	         }  
	     }  
	}
}
