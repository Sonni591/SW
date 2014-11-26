package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
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
import java.util.Calendar;
import java.util.Date;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

@SuppressWarnings("deprecation")
public class PanelParameter extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static JTextField txtVonDatum;
	public static JComboBox<Integer> cboJahr;
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
		btnBerichte.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				ChartTest chart = new ChartTest();
				chart.createchart();
			}
		});
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

		JCalendarButton btnVonDatum = new JCalendarButton();
		btnVonDatum.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getNewValue() instanceof Date)
				{
					String formattedDate = new SimpleDateFormat("dd.MM.yyyy").format(evt.getNewValue());
					txtVonDatum.setText(formattedDate);
				}
				
			}
		});
		panelZeitraumAuswahl.add(btnVonDatum);
		
		JLabel lblBis = new JLabel("bis:");
		panelZeitraumAuswahl.add(lblBis);
		
		txtBisDatum = new JTextField();
		panelZeitraumAuswahl.add(txtBisDatum);
		txtBisDatum.setEditable(false);
		txtBisDatum.setColumns(10);

		JCalendarButton btnBisDatum = new JCalendarButton();
		btnBisDatum.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getNewValue() instanceof Date)
				{
					String formattedDate = new SimpleDateFormat("dd.MM.yyyy").format(evt.getNewValue());
					txtBisDatum.setText(formattedDate);
				}
				
			}
		});
		panelZeitraumAuswahl.add(btnBisDatum);
		
		JPanel panelJahresAuswahl = new JPanel();
		panelParameterContent.add(panelJahresAuswahl, BorderLayout.WEST);
		
		JRadioButton rdbtnJahr = new JRadioButton("Jahr:");
		panelJahresAuswahl.add(rdbtnJahr);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(rdbtnVon);
		buttonGroup.add(rdbtnJahr);
		rdbtnVon.setSelected(true);
		
		//Dropdown-Box für die Jahresauswahl
		cboJahr = new JComboBox<Integer>();
		panelJahresAuswahl.add(cboJahr);
		
		for(int year=2000; year <= 2015; year++)
		{
			cboJahr.addItem(year);
		}
		cboJahr.setSelectedItem(Calendar.getInstance().get(Calendar.YEAR));
	}
}
