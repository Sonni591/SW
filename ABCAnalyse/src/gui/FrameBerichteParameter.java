package gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

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

import datasource.CrudFunktionen;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class FrameBerichteParameter {

	private JDialog dialogFrame;
	private Dimension fixedFrameDimensions;
	
	public static JComboBox<String> cboWarengruppen;
	public static JComboBox<String> cboVertriebskanal1;
	public static JComboBox<String> cboVertriebskanal2;
	public static JCheckBox chckbxVertriebskanal;
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void initialize() {
		//Erstellen des Hauptframes
		
		fixedFrameDimensions = new Dimension(300, 300);
		
		dialogFrame = new JDialog(MainWindow.frame, "Einstellungen der ABC-Berichte", true);
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
		lblHeaderTxt.setHorizontalAlignment(SwingConstants.LEFT);
		
		JLabel lblLetzteAnalyseTxt = new JLabel("Daten der letzten Analyse:");
		
		JLabel lblTestText = new JLabel("blablabla TO BE DONE");
		GroupLayout gl_panelHeader = new GroupLayout(panelHeader);
		gl_panelHeader.setHorizontalGroup(
			gl_panelHeader.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelHeader.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelHeader.createParallelGroup(Alignment.LEADING)
						.addComponent(lblHeaderTxt, GroupLayout.PREFERRED_SIZE, 386, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblLetzteAnalyseTxt)
						.addComponent(lblTestText))
					.addContainerGap(8, Short.MAX_VALUE))
		);
		gl_panelHeader.setVerticalGroup(
			gl_panelHeader.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelHeader.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblHeaderTxt, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblLetzteAnalyseTxt)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(lblTestText))
		);
		panelHeader.setLayout(gl_panelHeader);
		
		JPanel panelEinstellungen = new JPanel();
		dialogFrame.getContentPane().add(panelEinstellungen, BorderLayout.WEST);
		
		JLabel lblWarengruppe = new JLabel("Warengruppe:");
		
		cboWarengruppen = new JComboBox<String>();
		ArrayList<String> warengruppenList = CrudFunktionen.getWarengruppen();
		for(String s : warengruppenList) {
			cboWarengruppen.addItem(s);
		}
		
		JLabel lblVertriebskanal = new JLabel("Vertriebskanal:");
		
		
		ArrayList<String> vertriebskanaeleList = CrudFunktionen.getVertriebskanale();
		
		cboVertriebskanal1 = new JComboBox<String>();
		for(String s : vertriebskanaeleList) {
			cboVertriebskanal1.addItem(s);
		}
		
		chckbxVertriebskanal = new JCheckBox("Vergleich mit Vertriebskanal:");
		chckbxVertriebskanal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// comboBox Vertriebskanal 2 nur bei aktivierter Checkbox
				if(chckbxVertriebskanal.isSelected()) {
					cboVertriebskanal2.setEnabled(true);
				}
				else {
					cboVertriebskanal2.setEnabled(false);
				}
			}
		});
		
		cboVertriebskanal2 = new JComboBox<String>();
		// initial deaktiviert
		cboVertriebskanal2.setEnabled(false);
		for(String s : vertriebskanaeleList) {
			cboVertriebskanal2.addItem(s);
		}
		
		GroupLayout gl_panelEinstellungen = new GroupLayout(panelEinstellungen);
		gl_panelEinstellungen.setHorizontalGroup(
			gl_panelEinstellungen.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelEinstellungen.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelEinstellungen.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelEinstellungen.createSequentialGroup()
							.addGap(29)
							.addComponent(cboVertriebskanal2, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_panelEinstellungen.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panelEinstellungen.createSequentialGroup()
								.addGroup(gl_panelEinstellungen.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_panelEinstellungen.createSequentialGroup()
										.addComponent(lblWarengruppe)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(cboWarengruppen, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addGroup(gl_panelEinstellungen.createSequentialGroup()
										.addComponent(lblVertriebskanal)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(cboVertriebskanal1, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)))
								.addContainerGap(118, Short.MAX_VALUE))
							.addGroup(gl_panelEinstellungen.createSequentialGroup()
								.addComponent(chckbxVertriebskanal)
								.addContainerGap(123, Short.MAX_VALUE)))))
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
					.addGap(14))
		);
		panelEinstellungen.setLayout(gl_panelEinstellungen);
		
		JPanel panelFooter = new JPanel();
		panelFooter.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));
		dialogFrame.getContentPane().add(panelFooter, BorderLayout.SOUTH);
		panelFooter.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton btnBerichteAnzeigen = new JButton("Anzeigen");
		btnBerichteAnzeigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChartTest chart = new ChartTest();
				chart.createchart();
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
