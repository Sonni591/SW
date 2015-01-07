package gui;

import interfaces.IABCRepository;
import java.awt.Dimension;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDialog;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JSeparator;

public class FrameBerichteParameter {

	public static JDialog dialogFrame;
	private Dimension fixedFrameDimensions;

	public static JComboBox<String> cboWarengruppen;
	public static JComboBox<String> cboVertriebskanal1;
	public static JComboBox<String> cboVertriebskanal2;
	public static JCheckBox chckbxVertriebskanal;
	public static JRadioButton rdbtnChartOption1;
	public static JRadioButton rdbtnChartOption2;
	public static JRadioButton rdbtnChartOption3;
	public static JRadioButton rdbtnAbsatzInMenge;
	public static JRadioButton rdbtnAbsatzInWert;

	private IABCRepository repository;
	public FrameBerichteParameter(IABCRepository _repository) {
		repository = _repository;
	}
	/**
	 * @wbp.parser.entryPoint
	 */
	public void initialize() {
		// Erstellen des Hauptframes

		fixedFrameDimensions = new Dimension(300, 430);

		dialogFrame = new JDialog(MainWindow.frame,
				"Einstellungen der ABC-Berichte", true);
		dialogFrame.setSize(fixedFrameDimensions);
		dialogFrame.setMinimumSize(fixedFrameDimensions);
		dialogFrame.setMaximumSize(fixedFrameDimensions);
		dialogFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		dialogFrame.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panelHeader = new JPanel();
		panelHeader.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.GRAY));
		dialogFrame.getContentPane().add(panelHeader, BorderLayout.NORTH);

		JLabel lblHeaderTxt = new JLabel("Einstellungen der ABC Berichte");
		lblHeaderTxt.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblHeaderTxt.setHorizontalAlignment(SwingConstants.CENTER);

		ButtonGroup buttonGroupChartOptions = new ButtonGroup();

		// Radio Buttons für Auswahl der Chart Ausprägung
		rdbtnChartOption1 = new JRadioButton(
				"Verteilung von Bestand und Umsatz");
		rdbtnChartOption2 = new JRadioButton(
				"Verteilung von Bestand und Absatz");
		rdbtnChartOption3 = new JRadioButton(
				"Aufteilung der Artikel");
		rdbtnChartOption1.setSelected(true);
		buttonGroupChartOptions.add(rdbtnChartOption1);
		buttonGroupChartOptions.add(rdbtnChartOption2);
		buttonGroupChartOptions.add(rdbtnChartOption3);

		GroupLayout gl_panelHeader = new GroupLayout(panelHeader);
		gl_panelHeader.setHorizontalGroup(
			gl_panelHeader.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panelHeader.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblHeaderTxt, GroupLayout.PREFERRED_SIZE, 294, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panelHeader.setVerticalGroup(
			gl_panelHeader.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelHeader.createSequentialGroup()
					.addGap(15)
					.addComponent(lblHeaderTxt, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(19, Short.MAX_VALUE))
		);
		panelHeader.setLayout(gl_panelHeader);

		JPanel panelEinstellungen = new JPanel();
		dialogFrame.getContentPane().add(panelEinstellungen, BorderLayout.WEST);

		JLabel lblWarengruppe = new JLabel("Warengruppe:");

		cboWarengruppen = new JComboBox<String>();
		ArrayList<String> warengruppenList = repository.getWarengruppenBerichte();
		for (String s : warengruppenList) {
			cboWarengruppen.addItem(s);
		}

		JLabel lblVertriebskanal = new JLabel("Vertriebskanal:");

		ArrayList<String> vertriebskanaeleList = repository
				.getVertriebskanaleBerichte();

		cboVertriebskanal1 = new JComboBox<String>();
		for (String s : vertriebskanaeleList) {
			cboVertriebskanal1.addItem(s);
		}

		chckbxVertriebskanal = new JCheckBox("Vergleich mit Vertriebskanal:");
		chckbxVertriebskanal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// comboBox Vertriebskanal 2 nur bei aktivierter Checkbox
				if (chckbxVertriebskanal.isSelected()) {
					cboVertriebskanal2.setEnabled(true);
				} else {
					cboVertriebskanal2.setEnabled(false);
				}
			}
		});

		cboVertriebskanal2 = new JComboBox<String>();
		// initial deaktiviert
		cboVertriebskanal2.setEnabled(false);
		for (String s : vertriebskanaeleList) {
			cboVertriebskanal2.addItem(s);
		}
		
		JLabel lblAbsatzIn = new JLabel("Absatz in:");
		
		ButtonGroup buttonGroupAbsatz = new ButtonGroup();
		
		rdbtnAbsatzInMenge = new JRadioButton("Menge");
		rdbtnAbsatzInWert = new JRadioButton("Wert");
		
		rdbtnAbsatzInMenge.setSelected(true);
		
		buttonGroupAbsatz.add(rdbtnAbsatzInMenge);
		buttonGroupAbsatz.add(rdbtnAbsatzInWert);
		
		JSeparator separator = new JSeparator();
		
		JSeparator separator_1 = new JSeparator();

		GroupLayout gl_panelEinstellungen = new GroupLayout(panelEinstellungen);
		gl_panelEinstellungen.setHorizontalGroup(
			gl_panelEinstellungen.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelEinstellungen.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelEinstellungen.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelEinstellungen.createSequentialGroup()
							.addComponent(separator, GroupLayout.PREFERRED_SIZE, 287, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_panelEinstellungen.createSequentialGroup()
							.addGap(29)
							.addComponent(cboVertriebskanal2, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_panelEinstellungen.createSequentialGroup()
							.addGroup(gl_panelEinstellungen.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelEinstellungen.createSequentialGroup()
									.addComponent(lblWarengruppe)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(cboWarengruppen, 0, 176, Short.MAX_VALUE))
								.addGroup(gl_panelEinstellungen.createSequentialGroup()
									.addComponent(lblVertriebskanal)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(cboVertriebskanal1, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)))
							.addContainerGap(176, Short.MAX_VALUE))
						.addGroup(gl_panelEinstellungen.createSequentialGroup()
							.addComponent(chckbxVertriebskanal)
							.addContainerGap(237, Short.MAX_VALUE))
						.addGroup(gl_panelEinstellungen.createSequentialGroup()
							.addComponent(rdbtnAbsatzInMenge)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(rdbtnAbsatzInWert)
							.addContainerGap())
						.addGroup(gl_panelEinstellungen.createSequentialGroup()
							.addComponent(lblAbsatzIn)
							.addContainerGap(387, Short.MAX_VALUE))
						.addGroup(gl_panelEinstellungen.createSequentialGroup()
							.addComponent(rdbtnChartOption1)
							.addContainerGap(195, Short.MAX_VALUE))
						.addGroup(gl_panelEinstellungen.createSequentialGroup()
							.addComponent(rdbtnChartOption2)
							.addContainerGap(199, Short.MAX_VALUE))
						.addGroup(gl_panelEinstellungen.createSequentialGroup()
							.addComponent(rdbtnChartOption3)
							.addContainerGap(282, Short.MAX_VALUE))
						.addGroup(gl_panelEinstellungen.createSequentialGroup()
							.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 287, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(163, Short.MAX_VALUE))))
		);
		gl_panelEinstellungen.setVerticalGroup(
			gl_panelEinstellungen.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelEinstellungen.createSequentialGroup()
					.addGap(17)
					.addGroup(gl_panelEinstellungen.createParallelGroup(Alignment.BASELINE)
						.addComponent(cboWarengruppen, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblWarengruppe))
					.addGap(9)
					.addGroup(gl_panelEinstellungen.createParallelGroup(Alignment.BASELINE)
						.addComponent(cboVertriebskanal1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblVertriebskanal))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(chckbxVertriebskanal)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cboVertriebskanal2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(2)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rdbtnChartOption1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rdbtnChartOption2)
					.addGap(3)
					.addComponent(rdbtnChartOption3)
					.addGap(4)
					.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
					.addGap(4)
					.addComponent(lblAbsatzIn)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelEinstellungen.createParallelGroup(Alignment.BASELINE)
						.addComponent(rdbtnAbsatzInMenge)
						.addComponent(rdbtnAbsatzInWert))
					.addGap(37))
		);
		panelEinstellungen.setLayout(gl_panelEinstellungen);

		JPanel panelFooter = new JPanel();
		panelFooter.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0,
				0, 0)));
		dialogFrame.getContentPane().add(panelFooter, BorderLayout.SOUTH);
		panelFooter.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		JButton btnBerichteAnzeigen = new JButton("Anzeigen");
		btnBerichteAnzeigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Chart chart = new Chart(repository);
				chart.generateChart();
				
			}
		});
		panelFooter.add(btnBerichteAnzeigen);

		JButton btnFrameSchliessen = new JButton("Schlie\u00DFen");
		btnFrameSchliessen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Fenster schließen
				dialogFrame.setVisible(false);
				dialogFrame.dispose(); // JFrame Objekt zerstören
			}
		});
		panelFooter.add(btnFrameSchliessen);

		dialogFrame.setVisible(true);
	}
}
