package gui;
import interfaces.IABCRepository;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import sqliteRepository.DBConnector;
import sqliteRepository.SqliteRepository;

import java.sql.Connection;

import logic.ABCRechnung;


public class MainWindow {

	public static JFrame frame;
	private IABCRepository repository;

	//Einzelne Tabpages
	public static		PanelParameter panelParameter;
	public static		PanelEinteilung panelEinteilung;
	public static		PanelZuordnung panelZuordnung;
	public static		PanelErgebnis panelErgebnis;
	
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
		//Zuerst zur Datenbank verbinden
		repository = new SqliteRepository();
		//Initialisieren aller Komponenten
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//Erstellen des Hauptframes
		frame = new JFrame();
		frame.setSize(700, 400);
		frame.setMinimumSize(new Dimension(700, 400));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//TabbedPane fuer die einzelnen Tabpages
		JTabbedPane tabPageContainer = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabPageContainer);
		
		//Einzelne Tabpages
		 panelParameter = new PanelParameter(repository);
		 panelEinteilung = new PanelEinteilung(repository);
		 panelZuordnung = new PanelZuordnung(repository);
		 panelErgebnis = new PanelErgebnis(repository);
		
		//Hinzufuegen der einzelnen TabPages
		tabPageContainer.addTab("Parameter", panelParameter);
		tabPageContainer.addTab("ABC Einteilung", panelEinteilung);
		tabPageContainer.addTab("ABC Zuordnung", panelZuordnung);
		tabPageContainer.addTab("Ergebnis", panelErgebnis);
	}
}
