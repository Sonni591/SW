package gui;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.awt.BorderLayout;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import java.awt.FlowLayout;

import javax.swing.JButton;

import datasource.crudFunktionen;
import datasource.dbConnector;
import datasource.selectBefehle;

import java.awt.GridBagLayout;


public class MainWindow {

	private JFrame frame;
	private JTextField txtVonDatum;
	private JTextField txtJahr;
	private JTextField txtBisDatum;
	private static JFormattedTextField txtUmsatzA;
	private static JFormattedTextField txtUmsatzB;
	private static JFormattedTextField txtUmsatzC;
	private static JFormattedTextField txtAnzahlA;
	private static JFormattedTextField txtAnzahlB;
	private static JFormattedTextField txtAnzahlC;
	private static JFormattedTextField txtMengeA;
	private static JFormattedTextField txtMengeB;
	private static JFormattedTextField txtMengeC;
	
	private static Connection DBconnection = null;

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
		DBconnection = dbConnector.connectSqLite();
		getABCEinteilung();
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}
	
	private static void getABCEinteilung()
	{
		try {
			ResultSet abcEinteilungResult = crudFunktionen.getResult(DBconnection, selectBefehle.selectABCEinteilung);
			
			while (abcEinteilungResult.next()) {
				 
				String Bezeichnung = abcEinteilungResult.getString("Bezeichnung");

				switch(Bezeichnung)
				{
					case "Umsatz":
						txtUmsatzA.setText(String.valueOf(abcEinteilungResult.getInt("AnteilA")));
						txtUmsatzB.setText(String.valueOf(abcEinteilungResult.getInt("AnteilB")));
						txtUmsatzC.setText(String.valueOf(abcEinteilungResult.getInt("AnteilC")));
						break;
					case "Menge":
						txtMengeA.setText(String.valueOf(abcEinteilungResult.getInt("AnteilA")));
						txtMengeB.setText(String.valueOf(abcEinteilungResult.getInt("AnteilB")));
						txtMengeC.setText(String.valueOf(abcEinteilungResult.getInt("AnteilC")));
						break;
					case "Auftragsanzahl":
						txtAnzahlA.setText(String.valueOf(abcEinteilungResult.getInt("AnteilA")));
						txtAnzahlB.setText(String.valueOf(abcEinteilungResult.getInt("AnteilB")));
						txtAnzahlC.setText(String.valueOf(abcEinteilungResult.getInt("AnteilC")));
						break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//Erstellen des Hauptframes
		frame = new JFrame();
		frame.setSize(700, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//TabbedPane fuer die einzelnen Tabpages
		JTabbedPane tabPageContainer = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabPageContainer);
		
		//Einzelne Tabpages
		JPanel panelParameter = new JPanel();
		JPanel panelEinteilung = new JPanel();
		JPanel panelZuordnung = new JPanel();
		JPanel panelErgebnis = new JPanel();
		
		//Hinzufuegen der einzelnen TabPages
		tabPageContainer.addTab("Parameter", panelParameter);
		tabPageContainer.addTab("ABC Einteilung", panelEinteilung);
		tabPageContainer.addTab("ABC Zuordnung", panelZuordnung);
		tabPageContainer.addTab("Ergebnis", panelErgebnis);
		
		//Setzt alle Komponenten fuer den Parameter-Tab
		setParameterLayout(panelParameter);
		
		//Setzt alle Komponenten fuer den Einteilungs-Tab
		setLayoutEinteilung(panelEinteilung);
		
	}
	
	/**
	 * Setzt das Layout fuer den Parameter-Tab
	 */
	private void setParameterLayout(JPanel panelParameter)
	{
		panelParameter.setLayout(new BorderLayout(0, 0));
		
		JPanel panelParameterHeader = new JPanel();
		panelParameter.add(panelParameterHeader, BorderLayout.NORTH);
		panelParameterHeader.setLayout(new BoxLayout(panelParameterHeader, BoxLayout.X_AXIS));
		
		JLabel lblZeitraum = new JLabel("Zeitraum");
		lblZeitraum.setHorizontalAlignment(SwingConstants.CENTER);
		panelParameterHeader.add(lblZeitraum);
		
		JPanel panelParameterFoot = new JPanel();
		panelParameter.add(panelParameterFoot, BorderLayout.SOUTH);
		panelParameterFoot.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton btnBerichte = new JButton("Berichte");
		panelParameterFoot.add(btnBerichte);
		
		JButton btnBerechnen = new JButton("Berechnen");
		panelParameterFoot.add(btnBerechnen);
		
		JPanel panelParameterContent = new JPanel();
		panelParameter.add(panelParameterContent, BorderLayout.CENTER);
		panelParameterContent.setLayout(new BorderLayout(0, 0));
		
		JPanel panelZeitraumAuswahl = new JPanel();
		panelParameterContent.add(panelZeitraumAuswahl, BorderLayout.NORTH);
		panelZeitraumAuswahl.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JRadioButton rdbtnVon = new JRadioButton("Von:");
		panelZeitraumAuswahl.add(rdbtnVon);
		
		txtVonDatum = new JTextField();
		panelZeitraumAuswahl.add(txtVonDatum);
		txtVonDatum.setColumns(10);
		
		JLabel lblBis = new JLabel("bis:");
		panelZeitraumAuswahl.add(lblBis);
		
		txtBisDatum = new JTextField();
		panelZeitraumAuswahl.add(txtBisDatum);
		txtBisDatum.setColumns(10);
		
		JPanel panelJahresAuswahl = new JPanel();
		panelParameterContent.add(panelJahresAuswahl, BorderLayout.WEST);
		
		JRadioButton rdbtnJahr = new JRadioButton("Jahr:");
		panelJahresAuswahl.add(rdbtnJahr);
		
		txtJahr = new JTextField();
		panelJahresAuswahl.add(txtJahr);
		txtJahr.setColumns(10);
	}
	
	/**
	 * Setzt das Layout fuer den Einteilungs-Tab
	 */
	private void setLayoutEinteilung(JPanel panelEinteilung)
	{
		try {
			panelEinteilung.setLayout(new BorderLayout(0, 0));
			
			JPanel panelEinteilungHeader = new JPanel();
			panelEinteilung.add(panelEinteilungHeader, BorderLayout.NORTH);
			panelEinteilungHeader.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
			
			JLabel lblEinteilung = new JLabel("Einteilung");
			panelEinteilungHeader.add(lblEinteilung);
			
			JPanel panelEinteilungContent = new JPanel();
			panelEinteilung.add(panelEinteilungContent, BorderLayout.CENTER);
			
			NumberFormat percentFormat = NumberFormat.getNumberInstance();
			
			GridBagLayout gbl_panelEinteilungContent = new GridBagLayout();
			gbl_panelEinteilungContent.columnWidths = new int[]{100, 100, 100, 100, 0};
			gbl_panelEinteilungContent.rowHeights = new int[]{36, 40, 36, 40, 36, 40, 36, 0};
			gbl_panelEinteilungContent.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_panelEinteilungContent.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
			panelEinteilungContent.setLayout(gbl_panelEinteilungContent);
			
			JLabel lblFiller = new JLabel("");
			GridBagConstraints gbc_lblFiller = new GridBagConstraints();
			gbc_lblFiller.fill = GridBagConstraints.BOTH;
			gbc_lblFiller.insets = new Insets(0, 0, 5, 5);
			gbc_lblFiller.gridx = 0;
			gbc_lblFiller.gridy = 0;
			panelEinteilungContent.add(lblFiller, gbc_lblFiller);
			
			JLabel lblA = new JLabel("A");
			lblA.setHorizontalAlignment(SwingConstants.CENTER);
			GridBagConstraints gbc_lblA = new GridBagConstraints();
			gbc_lblA.fill = GridBagConstraints.BOTH;
			gbc_lblA.insets = new Insets(0, 0, 5, 5);
			gbc_lblA.gridx = 1;
			gbc_lblA.gridy = 0;
			panelEinteilungContent.add(lblA, gbc_lblA);
			
			JLabel lblB = new JLabel("B");
			lblB.setHorizontalAlignment(SwingConstants.CENTER);
			GridBagConstraints gbc_lblB = new GridBagConstraints();
			gbc_lblB.fill = GridBagConstraints.BOTH;
			gbc_lblB.insets = new Insets(0, 0, 5, 5);
			gbc_lblB.gridx = 2;
			gbc_lblB.gridy = 0;
			panelEinteilungContent.add(lblB, gbc_lblB);
			
			JLabel lblC = new JLabel("C");
			lblC.setHorizontalAlignment(SwingConstants.CENTER);
			GridBagConstraints gbc_lblC = new GridBagConstraints();
			gbc_lblC.fill = GridBagConstraints.BOTH;
			gbc_lblC.insets = new Insets(0, 0, 5, 0);
			gbc_lblC.gridx = 3;
			gbc_lblC.gridy = 0;
			panelEinteilungContent.add(lblC, gbc_lblC);
			
			JLabel lblUmsatz = new JLabel("Umsatz:");
			GridBagConstraints gbc_lblUmsatz = new GridBagConstraints();
			gbc_lblUmsatz.fill = GridBagConstraints.BOTH;
			gbc_lblUmsatz.insets = new Insets(0, 0, 5, 5);
			gbc_lblUmsatz.gridx = 0;
			gbc_lblUmsatz.gridy = 2;
			panelEinteilungContent.add(lblUmsatz, gbc_lblUmsatz);
			
	        txtUmsatzA = new JFormattedTextField(percentFormat);
	        GridBagConstraints gbc_txtUmsatzA = new GridBagConstraints();
	        gbc_txtUmsatzA.fill = GridBagConstraints.BOTH;
	        gbc_txtUmsatzA.insets = new Insets(0, 0, 5, 5);
	        gbc_txtUmsatzA.gridx = 1;
	        gbc_txtUmsatzA.gridy = 2;
	        panelEinteilungContent.add(txtUmsatzA, gbc_txtUmsatzA);
			
	        txtUmsatzB = new JFormattedTextField(percentFormat);
	        txtUmsatzB.setMaximumSize(new Dimension(20,10));
	        GridBagConstraints gbc_txtUmsatzB = new GridBagConstraints();
	        gbc_txtUmsatzB.fill = GridBagConstraints.BOTH;
	        gbc_txtUmsatzB.insets = new Insets(0, 0, 5, 5);
	        gbc_txtUmsatzB.gridx = 2;
	        gbc_txtUmsatzB.gridy = 2;
	        panelEinteilungContent.add(txtUmsatzB, gbc_txtUmsatzB);
			
	        txtUmsatzC = new JFormattedTextField(percentFormat);
			txtUmsatzC.setMaximumSize(new Dimension(20,10));
			GridBagConstraints gbc_txtUmsatzC = new GridBagConstraints();
			gbc_txtUmsatzC.fill = GridBagConstraints.BOTH;
			gbc_txtUmsatzC.insets = new Insets(0, 0, 5, 0);
			gbc_txtUmsatzC.gridx = 3;
			gbc_txtUmsatzC.gridy = 2;
			panelEinteilungContent.add(txtUmsatzC, gbc_txtUmsatzC);
			
			JLabel lblAnzahl = new JLabel("Anzahl:");
			GridBagConstraints gbc_lblAnzahl = new GridBagConstraints();
			gbc_lblAnzahl.fill = GridBagConstraints.BOTH;
			gbc_lblAnzahl.insets = new Insets(0, 0, 5, 5);
			gbc_lblAnzahl.gridx = 0;
			gbc_lblAnzahl.gridy = 4;
			panelEinteilungContent.add(lblAnzahl, gbc_lblAnzahl);
			
			txtAnzahlA = new JFormattedTextField(percentFormat);
			txtAnzahlA.setMaximumSize(new Dimension(20,10));
			GridBagConstraints gbc_txtAnzahlA = new GridBagConstraints();
			gbc_txtAnzahlA.fill = GridBagConstraints.BOTH;
			gbc_txtAnzahlA.insets = new Insets(0, 0, 5, 5);
			gbc_txtAnzahlA.gridx = 1;
			gbc_txtAnzahlA.gridy = 4;
			panelEinteilungContent.add(txtAnzahlA, gbc_txtAnzahlA);
			
			txtAnzahlB = new JFormattedTextField(percentFormat);
			txtAnzahlB.setMaximumSize(new Dimension(20,10));
			GridBagConstraints gbc_txtAnzahlB = new GridBagConstraints();
			gbc_txtAnzahlB.fill = GridBagConstraints.BOTH;
			gbc_txtAnzahlB.insets = new Insets(0, 0, 5, 5);
			gbc_txtAnzahlB.gridx = 2;
			gbc_txtAnzahlB.gridy = 4;
			panelEinteilungContent.add(txtAnzahlB, gbc_txtAnzahlB);
			
			txtAnzahlC = new JFormattedTextField(percentFormat);
			txtAnzahlC.setMaximumSize(new Dimension(20,10));
			GridBagConstraints gbc_txtAnzahlC = new GridBagConstraints();
			gbc_txtAnzahlC.fill = GridBagConstraints.BOTH;
			gbc_txtAnzahlC.insets = new Insets(0, 0, 5, 0);
			gbc_txtAnzahlC.gridx = 3;
			gbc_txtAnzahlC.gridy = 4;
			panelEinteilungContent.add(txtAnzahlC, gbc_txtAnzahlC);
			
			JLabel lblMenge = new JLabel("Menge:");
			GridBagConstraints gbc_lblMenge = new GridBagConstraints();
			gbc_lblMenge.fill = GridBagConstraints.BOTH;
			gbc_lblMenge.insets = new Insets(0, 0, 0, 5);
			gbc_lblMenge.gridx = 0;
			gbc_lblMenge.gridy = 6;
			panelEinteilungContent.add(lblMenge, gbc_lblMenge);
			
			txtMengeA = new JFormattedTextField(percentFormat);
			txtMengeA.setMaximumSize(new Dimension(20,10));
			GridBagConstraints gbc_txtMengeA = new GridBagConstraints();
			gbc_txtMengeA.fill = GridBagConstraints.BOTH;
			gbc_txtMengeA.insets = new Insets(0, 0, 0, 5);
			gbc_txtMengeA.gridx = 1;
			gbc_txtMengeA.gridy = 6;
			panelEinteilungContent.add(txtMengeA, gbc_txtMengeA);
			
			txtMengeB = new JFormattedTextField(percentFormat);
			txtMengeB.setMaximumSize(new Dimension(20,10));
			GridBagConstraints gbc_txtMengeB = new GridBagConstraints();
			gbc_txtMengeB.fill = GridBagConstraints.BOTH;
			gbc_txtMengeB.insets = new Insets(0, 0, 0, 5);
			gbc_txtMengeB.gridx = 2;
			gbc_txtMengeB.gridy = 6;
			panelEinteilungContent.add(txtMengeB, gbc_txtMengeB);
			
			txtMengeC = new JFormattedTextField(percentFormat);
			txtMengeC.setMaximumSize(new Dimension(20,10));
			GridBagConstraints gbc_txtMengeC = new GridBagConstraints();
			gbc_txtMengeC.fill = GridBagConstraints.BOTH;
			gbc_txtMengeC.gridx = 3;
			gbc_txtMengeC.gridy = 6;
			panelEinteilungContent.add(txtMengeC, gbc_txtMengeC);
			
			JPanel panelEinteilungFoot = new JPanel();
			panelEinteilung.add(panelEinteilungFoot, BorderLayout.SOUTH);
			panelEinteilungFoot.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
			
			JButton btnSpeichern = new JButton("Speichern");
			panelEinteilungFoot.add(btnSpeichern);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void updateEinteilung()
	{
		 Connection connection = null;    
	
	     try 
	     {  
	         Class.forName("org.sqlite.JDBC");  
	         connection = DriverManager.getConnection("jdbc:sqlite:SwDB.db");  
	         PreparedStatement updateUmsatz = null;
	         
	         String updateString =
	        	        "update " + "ABCEinteilung " +
	        	        "set AnteilA = ? where Bezeichnung = ?";
	         connection.setAutoCommit(false);
	         updateUmsatz = connection.prepareStatement(updateString);
	         //updateUmsatz.setInt(1, Integer.parseInt(txtUmsatzA.getText()));
	         updateUmsatz.setString(2, "Umsatz");
	         updateUmsatz.executeUpdate();
	         connection.commit();
	     } 
	     catch (Exception e) 
	     {  
	         e.printStackTrace();  
	     }
	     finally 
	     {  
	         try 
	         {    
	             connection.close();  
	         } 
	         catch (Exception e) 
	         {  
	             e.printStackTrace();  
	         }  
	     }  
	}
}
