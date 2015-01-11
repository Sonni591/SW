package gui;
import interfaces.IABCRepository;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import objects.Strings;
import sqliteRepository.SqliteRepository;

public class MainWindow {

	public static JFrame frame;
	private static IABCRepository repository;

	//Einzelne Register der Anwendung
	public static	PanelParameter panelParameter;
	public static	PanelEinteilung panelEinteilung;
	public static	PanelZuordnung panelZuordnung;
	public static	PanelErgebnis panelErgebnis;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//Zuerst zur Datenbank verbinden
					repository = new SqliteRepository();
					//Initialisieren aller Komponenten
					initialize();
					MainWindow.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private static void initialize() {
		//Erstellen des Hauptframes
		frame = new JFrame();
		frame.setSize(700, 400);
		frame.setMinimumSize(new Dimension(700, 400));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//TabbedPane fuer die einzelnen Register
		JTabbedPane tabPageContainer = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabPageContainer);
		
		//Einzelne Register initialisieren und das Repository bereitstellen
		 panelParameter = new PanelParameter(repository);
		 panelEinteilung = new PanelEinteilung(repository);
		 panelZuordnung = new PanelZuordnung(repository);
		 panelErgebnis = new PanelErgebnis(repository);
		
		//Hinzufuegen der einzelnen Register
		tabPageContainer.addTab(Strings.getParameter(), panelParameter);
		tabPageContainer.addTab(Strings.getABC_Einteilung(), panelEinteilung);
		tabPageContainer.addTab(Strings.getABC_Zuordnung(), panelZuordnung);
		tabPageContainer.addTab(Strings.getErgebnis(), panelErgebnis);
	}
}
