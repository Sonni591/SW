package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.sourceforge.jcalendarbutton.JCalendarButton;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JSeparator;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import logic.ABCRechnung;
import datasource.CrudBefehle;
import datasource.CrudFunktionen;

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
		
		JPanel panelParameterZeitraum = new JPanel();
		panelParameterZeitraum.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(128, 128, 128)));
		this.add(panelParameterZeitraum, BorderLayout.NORTH);
		panelParameterZeitraum.setLayout(new BorderLayout(0, 0));
		
		JPanel panelZeitraumHeader = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelZeitraumHeader.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panelParameterZeitraum.add(panelZeitraumHeader, BorderLayout.NORTH);
		
		JLabel lblZeitraum = new JLabel("Zeitraum");
		lblZeitraum.setVerticalAlignment(SwingConstants.BOTTOM);
		panelZeitraumHeader.add(lblZeitraum);
		lblZeitraum.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panelZeitraumAuswahl = new JPanel();
		panelParameterZeitraum.add(panelZeitraumAuswahl, BorderLayout.WEST);
		panelZeitraumAuswahl.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JRadioButton rdbtnVon = new JRadioButton("Von:");
		panelZeitraumAuswahl.add(rdbtnVon);
		
		txtVonDatum = new JTextField();
		panelZeitraumAuswahl.add(txtVonDatum);
		txtVonDatum.setEditable(false);
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
						
						ButtonGroup buttonGroupZeitraum = new ButtonGroup();
						
						panelZeitraumAuswahl.add(btnBisDatum);
						buttonGroupZeitraum.add(rdbtnVon);
						rdbtnVon.setSelected(true);
						
						JPanel panelJahresAuswahl = new JPanel();
						panelParameterZeitraum.add(panelJahresAuswahl, BorderLayout.SOUTH);
						
						JRadioButton rdbtnJahr = new JRadioButton("Jahr:");
						buttonGroupZeitraum.add(rdbtnJahr);
						
						//Dropdown-Box f��r die Jahresauswahl
						cboJahr = new JComboBox<Integer>();
						
						for(int year=2000; year <= 2015; year++)
						{
							cboJahr.addItem(year);
						}
						cboJahr.setSelectedItem(Calendar.getInstance().get(Calendar.YEAR));
						GroupLayout gl_panelJahresAuswahl = new GroupLayout(panelJahresAuswahl);
						gl_panelJahresAuswahl.setHorizontalGroup(
							gl_panelJahresAuswahl.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelJahresAuswahl.createSequentialGroup()
									.addGap(5)
									.addComponent(rdbtnJahr)
									.addGap(5)
									.addComponent(cboJahr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(328))
						);
						gl_panelJahresAuswahl.setVerticalGroup(
							gl_panelJahresAuswahl.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelJahresAuswahl.createSequentialGroup()
									.addGap(7)
									.addComponent(rdbtnJahr))
								.addGroup(gl_panelJahresAuswahl.createSequentialGroup()
									.addGap(5)
									.addComponent(cboJahr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						);
						panelJahresAuswahl.setLayout(gl_panelJahresAuswahl);
		
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
		btnBerechnen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ABCRechnung rechnung = new ABCRechnung(MainWindow.DBconnection);
				rechnung.start(/*TODO*/);
			}
		});
		panelParameterFoot.add(btnBerechnen);
		
		JPanel panelParameterVertriebskanal = new JPanel();
		panelParameterVertriebskanal.setBorder(new MatteBorder(0, 0, 1, 1, (Color) new Color(128, 128, 128)));
		this.add(panelParameterVertriebskanal, BorderLayout.WEST);
		
		JLabel lblVertriebskanalHeader = new JLabel("Vertriebskanal");
		
		ButtonGroup buttonGroupVertriebskanal = new ButtonGroup();
		JRadioButton rdbtnAlleVertriebskanle = new JRadioButton("Alle Vertriebskanäle");
		rdbtnAlleVertriebskanle.setSelected(true);
		buttonGroupVertriebskanal.add(rdbtnAlleVertriebskanle);
		JRadioButton rdbtnVertriebskanalEinzel = new JRadioButton("Nur folgenden Vertriebskanal");
		buttonGroupVertriebskanal.add(rdbtnVertriebskanalEinzel);
		
		// Vertriebskanäle lesen
		ArrayList<String> vertriebskanaele = getVertriebskanale();
	
		JComboBox<String> cboVertriebskanal = new JComboBox<String>();
		for(String s : vertriebskanaele) {
				cboVertriebskanal.addItem(s);
		}
	
		
		GroupLayout gl_panelParameterVertriebskanal = new GroupLayout(panelParameterVertriebskanal);
		gl_panelParameterVertriebskanal.setHorizontalGroup(
			gl_panelParameterVertriebskanal.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelParameterVertriebskanal.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelParameterVertriebskanal.createParallelGroup(Alignment.LEADING, false)
						.addComponent(rdbtnAlleVertriebskanle)
						.addComponent(lblVertriebskanalHeader)
						.addComponent(rdbtnVertriebskanalEinzel)
						.addGroup(gl_panelParameterVertriebskanal.createSequentialGroup()
							.addGap(29)
							.addComponent(cboVertriebskanal, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addContainerGap(69, Short.MAX_VALUE))
		);
		gl_panelParameterVertriebskanal.setVerticalGroup(
			gl_panelParameterVertriebskanal.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelParameterVertriebskanal.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblVertriebskanalHeader)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rdbtnAlleVertriebskanle)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rdbtnVertriebskanalEinzel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cboVertriebskanal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(47, Short.MAX_VALUE))
		);
		panelParameterVertriebskanal.setLayout(gl_panelParameterVertriebskanal);
		
		JPanel panelParameterWarengruppe = new JPanel();
		panelParameterWarengruppe.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.GRAY));
		add(panelParameterWarengruppe, BorderLayout.CENTER);
		
		JLabel lblWarengruppeHeader = new JLabel("Warengruppe");
		
		
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		
		ArrayList<String> wgList = getWarengruppen();
		for(String s : wgList) {
			listModel.addElement(s);
		}
		
		JList<String> listWarengruppen = new JList<String>(listModel);
	
		listWarengruppen.setVisibleRowCount(5);
		
		JScrollPane listScrollPane = new JScrollPane(listWarengruppen);
		
		
	
		Dimension d = listWarengruppen.getPreferredSize();
		d.width = 50;
		listScrollPane.setPreferredSize(d);
		
		
		//listScrollPane.setViewportView(listWarengruppen);
		
		
		
		listWarengruppen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		GroupLayout gl_panelParameterWarengruppe = new GroupLayout(panelParameterWarengruppe);
		gl_panelParameterWarengruppe.setHorizontalGroup(
			gl_panelParameterWarengruppe.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelParameterWarengruppe.createSequentialGroup()
					.addGap(5)
					.addComponent(lblWarengruppeHeader))
				.addGroup(gl_panelParameterWarengruppe.createSequentialGroup()
					.addGap(7)
					.addComponent(listScrollPane))
		);
		gl_panelParameterWarengruppe.setVerticalGroup(
			gl_panelParameterWarengruppe.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelParameterWarengruppe.createSequentialGroup()
					.addGap(5)
					.addComponent(lblWarengruppeHeader)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(listScrollPane)
					.addContainerGap(83, Short.MAX_VALUE))
		);
		panelParameterWarengruppe.setLayout(gl_panelParameterWarengruppe);
	}

	private ArrayList<String> getVertriebskanale() {
		
		ArrayList<String> vkList = new ArrayList<String>();
		
		ResultSet rsVertriebskanale = null;
		try {
			rsVertriebskanale = CrudFunktionen.getResult(
					MainWindow.DBconnection, CrudBefehle.selectVertriebskanaele);
		
			while (rsVertriebskanale.next()) {

				String bezeichnung = rsVertriebskanale
						.getString("Bezeichnung");
				vkList.add(bezeichnung);

			}
			return vkList;
		
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
			return null;
		} finally {
			try {
				rsVertriebskanale.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
private ArrayList<String> getWarengruppen() {
		
		ArrayList<String> wgList = new ArrayList<String>();
		
		ResultSet rsWarengruppen = null;
		try {
			rsWarengruppen = CrudFunktionen.getResult(
					MainWindow.DBconnection, CrudBefehle.selectWarengruppen);
		
			while (rsWarengruppen.next()) {

				String bezeichnung = rsWarengruppen
						.getString("Bezeichnung");
				wgList.add(bezeichnung);

			}
			return wgList;
		
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
			return null;
		} finally {
			try {
				rsWarengruppen.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
	}
}
