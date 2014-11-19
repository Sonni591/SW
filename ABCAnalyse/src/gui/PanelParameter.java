package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class PanelParameter extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static JTextField txtVonDatum;
	public static JTextField txtJahr;
	public static JTextField txtBisDatum;
	
	public PanelParameter()
	{
		this.setLayout(new BorderLayout(0, 0));
		
		JPanel panelParameterHeader = new JPanel();
		this.add(panelParameterHeader, BorderLayout.NORTH);
		panelParameterHeader.setLayout(new BoxLayout(panelParameterHeader, BoxLayout.X_AXIS));
		
		JLabel lblZeitraum = new JLabel("Zeitraum");
		lblZeitraum.setHorizontalAlignment(SwingConstants.CENTER);
		panelParameterHeader.add(lblZeitraum);
		
		JPanel panelParameterFoot = new JPanel();
		this.add(panelParameterFoot, BorderLayout.SOUTH);
		panelParameterFoot.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton btnBerichte = new JButton("Berichte");
		panelParameterFoot.add(btnBerichte);
		
		JButton btnBerechnen = new JButton("Berechnen");
		panelParameterFoot.add(btnBerechnen);
		
		JPanel panelParameterContent = new JPanel();
		this.add(panelParameterContent, BorderLayout.CENTER);
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
}
