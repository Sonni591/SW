package gui;

import interfaces.IABCRepository;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.sourceforge.jcalendarbutton.JCalendarButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Color;

import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import objects.Vertriebskanal;
import objects.Warengruppe;
import logic.ABCRechnung;

@SuppressWarnings("deprecation")
public class PanelParameter extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Alle Auswahlfelder
	public static JTextField txtVonDatum;
	public static JComboBox<Integer> cboJahr;
	public static JTextField txtBisDatum;
	public static JComboBox<String> cboVertriebskanal;
	public static JComboBox<String> cboWarengruppe;
	public static JRadioButton rdbtnAlleVertriebskanaele;
	public static JRadioButton rdbtnVertriebskanalEinzel;
	public static JRadioButton rdbtnAlleWarengruppen;
	public static JRadioButton rdbtnWarengruppeEinzel;
	public static JRadioButton rdbtnVon;
	public static JRadioButton rdbtnJahr;

	private IABCRepository repository;

	public PanelParameter(IABCRepository _repository) {
		repository = _repository;

		// Estellen eines Events fuer die Auswahl der Vertriebskanaele
		ChangeListener changeListenerBtnGroupVertriebskanal = new ChangeListener() {
			public void stateChanged(ChangeEvent changEvent) {
				if (rdbtnAlleVertriebskanaele.isSelected()) {
					cboVertriebskanal.setEnabled(false);
				} else if (rdbtnVertriebskanalEinzel.isSelected()) {
					cboVertriebskanal.setEnabled(true);
				}
			}
		};

		// Estellen eines Events fuer die Auswahl der Wargengruppen
		ChangeListener changeListenerBtnGroupWarengruppen = new ChangeListener() {
			public void stateChanged(ChangeEvent changEvent) {
				if (rdbtnAlleWarengruppen.isSelected()) {
					cboWarengruppe.setEnabled(false);
				} else if (rdbtnWarengruppeEinzel.isSelected()) {
					cboWarengruppe.setEnabled(true);
				}
			}
		};

		// Layout-Option --Start
		this.setLayout(new BorderLayout(0, 0));

		JPanel panelParameterZeitraum = new JPanel();
		panelParameterZeitraum.setBorder(new MatteBorder(0, 0, 1, 0,
				(Color) new Color(128, 128, 128)));
		this.add(panelParameterZeitraum, BorderLayout.NORTH);
		panelParameterZeitraum.setLayout(new BorderLayout(0, 0));

		JPanel panelZeitraumHeader = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelZeitraumHeader.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panelParameterZeitraum.add(panelZeitraumHeader, BorderLayout.NORTH);

		JLabel lblZeitraum = new JLabel("Zeitraum:");
		lblZeitraum.setVerticalAlignment(SwingConstants.BOTTOM);
		panelZeitraumHeader.add(lblZeitraum);
		lblZeitraum.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel panelZeitraumAuswahl = new JPanel();
		panelParameterZeitraum.add(panelZeitraumAuswahl, BorderLayout.WEST);
		panelZeitraumAuswahl.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		rdbtnVon = new JRadioButton("Von:");
		panelZeitraumAuswahl.add(rdbtnVon);

		txtVonDatum = new JTextField();
		panelZeitraumAuswahl.add(txtVonDatum);
		txtVonDatum.setEditable(false);
		txtVonDatum.setColumns(10);
		txtVonDatum.setText("2011-06-01");

		JCalendarButton btnVonDatum = new JCalendarButton();
		panelZeitraumAuswahl.add(btnVonDatum);

		JLabel lblBis = new JLabel("bis:");
		panelZeitraumAuswahl.add(lblBis);

		txtBisDatum = new JTextField();
		panelZeitraumAuswahl.add(txtBisDatum);
		txtBisDatum.setEditable(false);
		txtBisDatum.setColumns(10);
		txtBisDatum.setText("2012-05-01");

		JCalendarButton btnBisDatum = new JCalendarButton();

		ButtonGroup buttonGroupZeitraum = new ButtonGroup();

		panelZeitraumAuswahl.add(btnBisDatum);
		buttonGroupZeitraum.add(rdbtnVon);

		JPanel panelJahresAuswahl = new JPanel();
		panelParameterZeitraum.add(panelJahresAuswahl, BorderLayout.SOUTH);

		rdbtnJahr = new JRadioButton("Jahr:");
		buttonGroupZeitraum.add(rdbtnJahr);

		// Dropdown-Box für die Jahresauswahl
		cboJahr = new JComboBox<Integer>();

		// Initial wird der Zeitraum ausgewählt
		rdbtnVon.setSelected(true);

		cboJahr.setSelectedItem(Calendar.getInstance().get(Calendar.YEAR));

		// GroupLayout
		GroupLayout gl_panelJahresAuswahl = new GroupLayout(panelJahresAuswahl);
		gl_panelJahresAuswahl
				.setHorizontalGroup(gl_panelJahresAuswahl.createParallelGroup(
						Alignment.LEADING)
						.addGroup(
								gl_panelJahresAuswahl
										.createSequentialGroup()
										.addGap(5)
										.addComponent(rdbtnJahr)
										.addGap(5)
										.addComponent(cboJahr,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addGap(328)));
		gl_panelJahresAuswahl.setVerticalGroup(gl_panelJahresAuswahl
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_panelJahresAuswahl.createSequentialGroup().addGap(7)
								.addComponent(rdbtnJahr))
				.addGroup(
						gl_panelJahresAuswahl
								.createSequentialGroup()
								.addGap(5)
								.addComponent(cboJahr,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)));
		panelJahresAuswahl.setLayout(gl_panelJahresAuswahl);

		JPanel panelParameterFoot = new JPanel();
		this.add(panelParameterFoot, BorderLayout.SOUTH);
		panelParameterFoot.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		JButton btnBerichte = new JButton("ABC Berichte generiern");
		panelParameterFoot.add(btnBerichte);

		JButton btnBerechnen = new JButton("ABC Analyse berechnen");
		panelParameterFoot.add(btnBerechnen);

		JPanel panelParameterVertriebskanal = new JPanel();
		panelParameterVertriebskanal.setBorder(new MatteBorder(0, 0, 1, 1,
				(Color) new Color(128, 128, 128)));
		this.add(panelParameterVertriebskanal, BorderLayout.WEST);

		JLabel lblVertriebskanalHeader = new JLabel("Vertriebskanal:");

		ButtonGroup buttonGroupVertriebskanal = new ButtonGroup();
		rdbtnAlleVertriebskanaele = new JRadioButton("Alle Vertriebskanäle");
		rdbtnAlleVertriebskanaele.setSelected(true);
		buttonGroupVertriebskanal.add(rdbtnAlleVertriebskanaele);
		rdbtnVertriebskanalEinzel = new JRadioButton(
				"Nur folgenden Vertriebskanal");
		buttonGroupVertriebskanal.add(rdbtnVertriebskanalEinzel);

		cboVertriebskanal = new JComboBox<String>();
		cboVertriebskanal.setEnabled(false);

		rdbtnAlleVertriebskanaele
				.addChangeListener(changeListenerBtnGroupVertriebskanal);
		rdbtnVertriebskanalEinzel
				.addChangeListener(changeListenerBtnGroupVertriebskanal);

		GroupLayout gl_panelParameterVertriebskanal = new GroupLayout(
				panelParameterVertriebskanal);
		gl_panelParameterVertriebskanal
				.setHorizontalGroup(gl_panelParameterVertriebskanal
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panelParameterVertriebskanal
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panelParameterVertriebskanal
														.createParallelGroup(
																Alignment.LEADING,
																false)
														.addComponent(
																rdbtnAlleVertriebskanaele)
														.addComponent(
																lblVertriebskanalHeader)
														.addComponent(
																rdbtnVertriebskanalEinzel)
														.addGroup(
																gl_panelParameterVertriebskanal
																		.createSequentialGroup()
																		.addGap(29)
																		.addComponent(
																				cboVertriebskanal,
																				0,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)))
										.addContainerGap(69, Short.MAX_VALUE)));
		gl_panelParameterVertriebskanal
				.setVerticalGroup(gl_panelParameterVertriebskanal
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panelParameterVertriebskanal
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(lblVertriebskanalHeader)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addComponent(rdbtnAlleVertriebskanaele)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addComponent(rdbtnVertriebskanalEinzel)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addComponent(cboVertriebskanal,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(47, Short.MAX_VALUE)));
		panelParameterVertriebskanal.setLayout(gl_panelParameterVertriebskanal);

		JPanel panelParameterWarengruppe = new JPanel();
		panelParameterWarengruppe.setBorder(new MatteBorder(0, 0, 1, 0,
				(Color) Color.GRAY));
		add(panelParameterWarengruppe, BorderLayout.CENTER);

		ButtonGroup buttonGroupWarengruppe = new ButtonGroup();

		JLabel lblWarengruppeHeader = new JLabel("Warengruppe:");

		rdbtnAlleWarengruppen = new JRadioButton("Alle Warengruppen");
		rdbtnAlleWarengruppen.setSelected(true);

		rdbtnWarengruppeEinzel = new JRadioButton("Nur folgende Warengruppe");

		buttonGroupWarengruppe.add(rdbtnAlleWarengruppen);
		buttonGroupWarengruppe.add(rdbtnWarengruppeEinzel);

		cboWarengruppe = new JComboBox<String>();
		cboWarengruppe.setEnabled(false);

		rdbtnAlleWarengruppen
				.addChangeListener(changeListenerBtnGroupWarengruppen);
		rdbtnWarengruppeEinzel
				.addChangeListener(changeListenerBtnGroupWarengruppen);

		GroupLayout gl_panelParameterWarengruppe = new GroupLayout(
				panelParameterWarengruppe);
		gl_panelParameterWarengruppe
				.setHorizontalGroup(gl_panelParameterWarengruppe
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panelParameterWarengruppe
										.createSequentialGroup()
										.addGroup(
												gl_panelParameterWarengruppe
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_panelParameterWarengruppe
																		.createSequentialGroup()
																		.addGap(5)
																		.addComponent(
																				lblWarengruppeHeader))
														.addGroup(
																gl_panelParameterWarengruppe
																		.createSequentialGroup()
																		.addContainerGap()
																		.addGroup(
																				gl_panelParameterWarengruppe
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								rdbtnAlleWarengruppen,
																								GroupLayout.PREFERRED_SIZE,
																								157,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								rdbtnWarengruppeEinzel,
																								GroupLayout.PREFERRED_SIZE,
																								216,
																								GroupLayout.PREFERRED_SIZE)
																						.addGroup(
																								gl_panelParameterWarengruppe
																										.createSequentialGroup()
																										.addGap(29)
																										.addComponent(
																												cboWarengruppe,
																												GroupLayout.PREFERRED_SIZE,
																												187,
																												GroupLayout.PREFERRED_SIZE)))))
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		gl_panelParameterWarengruppe
				.setVerticalGroup(gl_panelParameterWarengruppe
						.createParallelGroup(Alignment.LEADING).addGroup(
								gl_panelParameterWarengruppe
										.createSequentialGroup()
										.addGap(5)
										.addComponent(lblWarengruppeHeader)
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addComponent(rdbtnAlleWarengruppen)
										.addGap(6)
										.addComponent(rdbtnWarengruppeEinzel)
										.addGap(6)
										.addComponent(cboWarengruppe,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addContainerGap(51, Short.MAX_VALUE)));
		panelParameterWarengruppe.setLayout(gl_panelParameterWarengruppe);
		// Layout-Options --End

		// Setzen des ausgewaehlten Von-Datum in Textfeld
		btnVonDatum.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getNewValue() instanceof Date) {
					String formattedDate = new SimpleDateFormat("yyyy-MM-dd")
							.format(evt.getNewValue());
					txtVonDatum.setText(formattedDate);
					rdbtnVon.setSelected(true);
				}

			}
		});

		// Setzen des ausgewaehlten Bis-Datum in Textfeld
		btnBisDatum.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getNewValue() instanceof Date) {
					String formattedDate = new SimpleDateFormat("yyyy-MM-dd")
							.format(evt.getNewValue());
					txtBisDatum.setText(formattedDate);
					rdbtnVon.setSelected(true);
				}

			}
		});

		// Wenn Jahr ausgewaehlt, dann entsprechende Radio selektieren
		cboJahr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Änderung der Combobox selektiert den Radiobutton Jahr
				rdbtnJahr.setSelected(true);
			}
		});

		// Jahre 2000 bis 2015 in die Combobox hinzufuegen
		for (int year = 2000; year <= 2015; year++) {
			cboJahr.addItem(year);
		}

		// Ruft die Methode zum erzeugen der Berichte nach der Auswahl
		btnBerichte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateBerichte();
			}

		});

		// Event fuer das Berechnen der ABC-Analyse
		btnBerechnen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calculateABC();
			}
		});

		// Vertriebskanaele lesen
		ArrayList<String> vertriebskanaele = repository.getVertriebskanale();
		// Vertriebskanaele zur DropDown-Box hinzufuegen
		for (String s : vertriebskanaele) {
			// Gesamtunternehmen nicht mit auflisten
			if (!s.equals("Gesamtunternehmen")) {
				cboVertriebskanal.addItem(s);
			}
		}

		// Lesen aller Warengruppen
		ArrayList<String> warengruppenList = repository.getWarengruppen();
		// Warengruppen zur DropDown-Box hinzufuegen
		for (String s : warengruppenList) {
			cboWarengruppe.addItem(s);
		}

	}

	/**
	 * Liest die ausgewaehlten Vertriebskanaele und Warengruppen. Generiert
	 * anhand dieser und des repository die Berichte.
	 */
	private void generateBerichte() {
		System.out.println("starte generierung der Berichte");
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		ArrayList<Vertriebskanal> arrListVertriebskanaele = new ArrayList<Vertriebskanal>();
		ArrayList<Warengruppe> arrListWarengruppen = new ArrayList<Warengruppe>();

		// Vertriebskanäle
		if (PanelParameter.rdbtnAlleVertriebskanaele.isSelected()) {
			arrListVertriebskanaele = repository.getVertriebskanaeleObjects();
		} else {
			// ausgewählten Vertriebskanal lesen
			Vertriebskanal vertriebskanal = new Vertriebskanal();
			vertriebskanal.setBezeichnung((String) cboVertriebskanal
					.getSelectedItem());
			vertriebskanal.setLagerNr(repository
					.getVertriebsKanalID(vertriebskanal.getBezeichnung()));
			arrListVertriebskanaele.add(vertriebskanal);
		}

		if (PanelParameter.rdbtnAlleVertriebskanaele.isSelected()) {
			arrListWarengruppen = repository.getWarengruppenObjects();
		} else {
			// ausgewählte Warengruppe lesen
			Warengruppe warengruppe = new Warengruppe();
			warengruppe.setBezeichnung((String) cboWarengruppe
					.getSelectedItem());
			warengruppe.setWGNr(repository.getWarengruppeID(warengruppe
					.getBezeichnung()));
			arrListWarengruppen.add(warengruppe);
		}

		// Berichte löschen
		repository.deleteBerichte();

		// Fuer alle Vertriebskanaele und Warengruppen die Berichte generieren
		// (mit dem repository)
		for (Vertriebskanal v : arrListVertriebskanaele) {
			int vertriebskanalID = v.getLagerNr();
			for (Warengruppe w : arrListWarengruppen) {
				int warengruppenID = w.getWGNr();
				// Bericht für Vertriebskanal v und Warengruppe w
				// generieren
				repository.generateBerichte(vertriebskanalID, warengruppenID);
			}
		}

		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		System.out.println("ABC Berichte generiert");
	}

	/**
	 * Liest die Auswahl der Felder fuer die ABC-Berechnung. Macht die
	 * ABC-Berechnung anhand der Methoden der Logik.
	 */
	private void calculateABC() {
		// Mauszeiger auf Sanduhr setzen
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		String vonDatum = "";
		String bisDatum = "";

		// Datum aus Zeitraum von - bis bzw. der Combobox übernehmen
		if (rdbtnVon.isSelected()) {
			vonDatum = txtVonDatum.getText().toString();
			bisDatum = txtBisDatum.getText().toString();
		} else if (rdbtnJahr.isSelected()) {
			vonDatum = cboJahr.getSelectedItem().toString() + "-01-01";
			bisDatum = cboJahr.getSelectedItem().toString() + "-12-31";
		}

		// prüfe ob ein Zeitraum eingeschränkt wurde
		// Berechnung wird nur über einen Zeitraum ausgeführt
		if (vonDatum.equals("") && bisDatum.equals("")) {
			JOptionPane.showMessageDialog(MainWindow.frame,
					"Bitte einen Zeitraum einschränken!",
					"Fehlerhafter Zeitraum", JOptionPane.INFORMATION_MESSAGE);
		} else {
			ABCRechnung rechnung = new ABCRechnung(repository);
			if (!vonDatum.equals("") && !bisDatum.equals("")) {
				// Datum muss im Format YY.MM.DD hh:mm:ss:xxx vorliegen
				vonDatum = vonDatum + " 00:00:00.000";
				bisDatum = bisDatum + " 00:00:00.000";

				repository.insertABCInputTable(vonDatum, bisDatum);
				rechnung.CalculateABCResult();
				MainWindow.panelErgebnis.setTableData();
			}
		}

		// Mauszeiger Standard
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
}
