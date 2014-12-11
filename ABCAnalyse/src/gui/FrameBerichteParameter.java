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
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class FrameBerichteParameter {

	private JFrame frame;
	private Dimension fixedFrameDimensions;
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void initialize() {
		//Erstellen des Hauptframes
		
		fixedFrameDimensions = new Dimension(300, 300);
		
		frame = new JFrame();
		frame.setSize(fixedFrameDimensions);
		frame.setMinimumSize(fixedFrameDimensions);
		frame.setMaximumSize(fixedFrameDimensions);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panelHeader = new JPanel();
		panelHeader.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.GRAY));
		frame.getContentPane().add(panelHeader, BorderLayout.NORTH);
		
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
		frame.getContentPane().add(panelEinstellungen, BorderLayout.WEST);
		
		JLabel lblWarengruppe = new JLabel("Warengruppe:");
		
		JComboBox cboWarengruppen = new JComboBox();
		
		JLabel lblVertriebskanal = new JLabel("Vertriebskanal:");
		
		JComboBox cboVertriebskanal1 = new JComboBox();
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Vergleich mit Vertriebskanal:");
		
		JComboBox cboVertriebskanal2 = new JComboBox();
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
								.addComponent(chckbxNewCheckBox)
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
					.addComponent(chckbxNewCheckBox)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cboVertriebskanal2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(14))
		);
		panelEinstellungen.setLayout(gl_panelEinstellungen);
		
		JPanel panelFooter = new JPanel();
		panelFooter.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));
		frame.getContentPane().add(panelFooter, BorderLayout.SOUTH);
		panelFooter.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton btnBerichteAnzeigen = new JButton("Anzeigen");
		panelFooter.add(btnBerichteAnzeigen);
		
		JButton btnFrameSchliessen = new JButton("Schlie\u00DFen");
		btnFrameSchliessen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Fenster schließen
				frame.setVisible(false);
				frame.dispose(); // JFrame Objekt zerstören
			}
		});
		panelFooter.add(btnFrameSchliessen);
		
		frame.setVisible(true);
	}
}
