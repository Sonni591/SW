package gui;

import interfaces.IABCRepository;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
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
import java.util.Vector;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import objects.Vertriebskanal;
import objects.Warengruppe;
import sqliteRepository.CrudBefehle;
import logic.ABCRechnung;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

@SuppressWarnings("deprecation")
public class PanelParameter extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	
	public PanelParameter(IABCRepository _repository)
	{
		repository = _repository;
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
		
		rdbtnVon = new JRadioButton("Von:");
		panelZeitraumAuswahl.add(rdbtnVon);
		
		txtVonDatum = new JTextField();
		panelZeitraumAuswahl.add(txtVonDatum);
		txtVonDatum.setEditable(false);
		txtVonDatum.setColumns(10);
		txtVonDatum.setText("2011-06-01");
		
				JCalendarButton btnVonDatum = new JCalendarButton();
				btnVonDatum.addPropertyChangeListener(new PropertyChangeListener() {
					
					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						if(evt.getNewValue() instanceof Date)
						{
							String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(evt.getNewValue());
							txtVonDatum.setText(formattedDate);
							rdbtnVon.setSelected(true);
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
				txtBisDatum.setText("2012-05-01");
				
						JCalendarButton btnBisDatum = new JCalendarButton();
						btnBisDatum.addPropertyChangeListener(new PropertyChangeListener() {
							
							@Override
							public void propertyChange(PropertyChangeEvent evt) {
								if(evt.getNewValue() instanceof Date)
								{
									String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(evt.getNewValue());
									txtBisDatum.setText(formattedDate);
									rdbtnVon.setSelected(true);
								}
								
							}
						});
						
						ButtonGroup buttonGroupZeitraum = new ButtonGroup();
						
						panelZeitraumAuswahl.add(btnBisDatum);
						buttonGroupZeitraum.add(rdbtnVon);
						
						
						JPanel panelJahresAuswahl = new JPanel();
						panelParameterZeitraum.add(panelJahresAuswahl, BorderLayout.SOUTH);
						
						rdbtnJahr = new JRadioButton("Jahr:");
						buttonGroupZeitraum.add(rdbtnJahr);
						
						//Dropdown-Box für die Jahresauswahl
						cboJahr = new JComboBox<Integer>();
						cboJahr.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								// Änderung der Combobox selektiert den Radiobutton Jahr
								rdbtnJahr.setSelected(true);
							}
						});
						
						
						for(int year=2000; year <= 2015; year++)
						{
							cboJahr.addItem(year);
						}
						
						// Initial wird der Zeitraum ausgewählt
						rdbtnVon.setSelected(true);
						
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
		
		JButton btnBerichte = new JButton("ABC Berichte generiern");
		btnBerichte.addActionListener(new ActionListener() {

//			public void actionPerformed(ActionEvent e) {
//				repository.generateBericht(10, 1);
//			}
			
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("starte generierung der Berichte");
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				
//				int[] vertriebskanaeleIDs;
//				int[] warengruppenIDs;
//				
//				if(PanelParameter.rdbtnAlleVertriebskanaele.isSelected()) {
//					vertriebskanaeleIDs = new int[] {0, 10, 20, 30};
//				}
//				else {
//					vertriebskanaeleIDs = new int[] {10};
//				}
//				
//				if(PanelParameter.rdbtnAlleWarengruppen.isSelected()) {
//					warengruppenIDs = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
//				}
//				else {
//					warengruppenIDs = new int[] {1};
//				}
				
//				// neue Berichte generieren
//				for(int v : vertriebskanaeleIDs) {
//					for (int w : warengruppenIDs) {
//						
//					}	
//				}
				
				ArrayList<Vertriebskanal> arrListVertriebskanaele = new ArrayList<Vertriebskanal>();
				ArrayList<Warengruppe> arrListWarengruppen = new ArrayList<Warengruppe>();
				
				// Vertriebskanäle
				if(PanelParameter.rdbtnAlleVertriebskanaele.isSelected()) {
					arrListVertriebskanaele = repository.getVertriebskanaeleObjects();
				}
				else {
					// ausgewählten Vertriebskanal lesen
					Vertriebskanal vertriebskanal = new Vertriebskanal();
					vertriebskanal.setBezeichnung((String) cboVertriebskanal.getSelectedItem());
					vertriebskanal.setLagerNr(repository.getVertriebsKanalID(vertriebskanal.getBezeichnung()));
					arrListVertriebskanaele.add(vertriebskanal);
				}
				
				if(PanelParameter.rdbtnAlleVertriebskanaele.isSelected()) {
					arrListWarengruppen = repository.getWarengruppenObjects();
				}
				else {
					// ausgewählte Warengruppe lesen
					Warengruppe warengruppe = new Warengruppe();
					warengruppe.setBezeichnung((String) cboWarengruppe.getSelectedItem());
					warengruppe.setWGNr(repository.getWarengruppeID(warengruppe.getBezeichnung()));
					arrListWarengruppen.add(warengruppe);
				}
				
				
				// Berichte löschen
				repository.deleteBerichte();
				
				for(Vertriebskanal v : arrListVertriebskanaele) {
					int vertriebskanalID = v.getLagerNr();
					for (Warengruppe w : arrListWarengruppen) {
						int warengruppenID = w.getWGNr();
						// Bericht für Vertriebskanal v und Warengruppe w generieren
						repository.generateBerichte(vertriebskanalID, warengruppenID);
					}
				}
				
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				System.out.println("ABC Berichte generiert");
			}
			
		});
		panelParameterFoot.add(btnBerichte);
		
		JButton btnBerechnen = new JButton("ABC Analyse berechnen");
		btnBerechnen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
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
				if(vonDatum.equals("") && bisDatum.equals("")) {
					JOptionPane.showMessageDialog(MainWindow.frame,
							"Bitte einen Zeitraum einschränken!",
							"Fehlerhafter Zeitraum",
							JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					ABCRechnung rechnung = new ABCRechnung(repository);
					if(!vonDatum.equals("") && !bisDatum.equals(""))
					{
						repository.insertABCInputTable(vonDatum, bisDatum);
						rechnung.CalculateABCResult();
						MainWindow.panelErgebnis.setTableData();
					}
				}
			
				// Mauszeiger Standard
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				
			}
		});
		panelParameterFoot.add(btnBerechnen);
		
		JPanel panelParameterVertriebskanal = new JPanel();
		panelParameterVertriebskanal.setBorder(new MatteBorder(0, 0, 1, 1, (Color) new Color(128, 128, 128)));
		this.add(panelParameterVertriebskanal, BorderLayout.WEST);
		
		JLabel lblVertriebskanalHeader = new JLabel("Vertriebskanal");
		
		//Edit Timo: Variablen am Anfang der Klasse initialisiert um sie in anderen Klassen sichtbar zu machen.
		ButtonGroup buttonGroupVertriebskanal = new ButtonGroup();
		rdbtnAlleVertriebskanaele = new JRadioButton("Alle Vertriebskanäle");
		rdbtnAlleVertriebskanaele.setSelected(true);
		buttonGroupVertriebskanal.add(rdbtnAlleVertriebskanaele);
		rdbtnVertriebskanalEinzel = new JRadioButton("Nur folgenden Vertriebskanal");
		buttonGroupVertriebskanal.add(rdbtnVertriebskanalEinzel);
		
		// Vertriebskanäle lesen
		ArrayList<String> vertriebskanaele = repository.getVertriebskanale();
	
		cboVertriebskanal = new JComboBox<String>();
		cboVertriebskanal.setEnabled(false);
		for(String s : vertriebskanaele) {
				cboVertriebskanal.addItem(s);
		}

		
		ChangeListener changeListenerBtnGroupVertriebskanal = new ChangeListener() {
		      public void stateChanged(ChangeEvent changEvent) {
		        if(rdbtnAlleVertriebskanaele.isSelected()) {
		        	cboVertriebskanal.setEnabled(false);
		        }
		        else if (rdbtnVertriebskanalEinzel.isSelected()) {
		        	cboVertriebskanal.setEnabled(true);
		        }
		      }
		    };
	
		rdbtnAlleVertriebskanaele.addChangeListener(changeListenerBtnGroupVertriebskanal);
		rdbtnVertriebskanalEinzel.addChangeListener(changeListenerBtnGroupVertriebskanal);
		
		GroupLayout gl_panelParameterVertriebskanal = new GroupLayout(panelParameterVertriebskanal);
		gl_panelParameterVertriebskanal.setHorizontalGroup(
			gl_panelParameterVertriebskanal.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelParameterVertriebskanal.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelParameterVertriebskanal.createParallelGroup(Alignment.LEADING, false)
						.addComponent(rdbtnAlleVertriebskanaele)
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
					.addComponent(rdbtnAlleVertriebskanaele)
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
		
		ButtonGroup buttonGroupWarengruppe = new ButtonGroup();
		
		JLabel lblWarengruppeHeader = new JLabel("Warengruppe");
		
		rdbtnAlleWarengruppen = new JRadioButton("Alle Warengruppen");
		rdbtnAlleWarengruppen.setSelected(true);
		
		
		rdbtnWarengruppeEinzel = new JRadioButton("Nur folgende Warengruppe");
		
		buttonGroupWarengruppe.add(rdbtnAlleWarengruppen);
		buttonGroupWarengruppe.add(rdbtnWarengruppeEinzel);
		
		cboWarengruppe = new JComboBox<String>();
		cboWarengruppe.setEnabled(false);
		
		ArrayList<String> warengruppenList = repository.getWarengruppen();
		for(String s : warengruppenList) {
			cboWarengruppe.addItem(s);
		}
		
		ChangeListener changeListenerBtnGroupWarengruppen = new ChangeListener() {
		      public void stateChanged(ChangeEvent changEvent) {
		        if(rdbtnAlleWarengruppen.isSelected()) {
		        	cboWarengruppe.setEnabled(false);
		        }
		        else if (rdbtnWarengruppeEinzel.isSelected()) {
		        	cboWarengruppe.setEnabled(true);
		        }
		      }
		    };
		
		rdbtnAlleWarengruppen.addChangeListener(changeListenerBtnGroupWarengruppen);
		rdbtnWarengruppeEinzel.addChangeListener(changeListenerBtnGroupWarengruppen);
		
		
		GroupLayout gl_panelParameterWarengruppe = new GroupLayout(panelParameterWarengruppe);
		gl_panelParameterWarengruppe.setHorizontalGroup(
			gl_panelParameterWarengruppe.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelParameterWarengruppe.createSequentialGroup()
					.addGroup(gl_panelParameterWarengruppe.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelParameterWarengruppe.createSequentialGroup()
							.addGap(5)
							.addComponent(lblWarengruppeHeader))
						.addGroup(gl_panelParameterWarengruppe.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panelParameterWarengruppe.createParallelGroup(Alignment.LEADING)
								.addComponent(rdbtnAlleWarengruppen, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)
								.addComponent(rdbtnWarengruppeEinzel, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panelParameterWarengruppe.createSequentialGroup()
									.addGap(29)
									.addComponent(cboWarengruppe, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panelParameterWarengruppe.setVerticalGroup(
			gl_panelParameterWarengruppe.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelParameterWarengruppe.createSequentialGroup()
					.addGap(5)
					.addComponent(lblWarengruppeHeader)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rdbtnAlleWarengruppen)
					.addGap(6)
					.addComponent(rdbtnWarengruppeEinzel)
					.addGap(6)
					.addComponent(cboWarengruppe, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(51, Short.MAX_VALUE))
		);
		panelParameterWarengruppe.setLayout(gl_panelParameterWarengruppe);
	}

//private ArrayList<String> getVertriebskanale() {
//		
//		ArrayList<String> vkList = new ArrayList<String>();
//		
//		ResultSet rsVertriebskanale = null;
//		try {
//			rsVertriebskanale = repository.getResult(
//					MainWindow.DBconnection, CrudBefehle.selectVertriebskanaele);
//		
//			while (rsVertriebskanale.next()) {
//
//				String bezeichnung = rsVertriebskanale
//						.getString("Bezeichnung");
//				vkList.add(bezeichnung);
//
//			}
//			return vkList;
//		
//		} catch (Exception e) {
//			System.err.println(e);
//			e.printStackTrace();
//			return null;
//		} finally {
//			try {
//				rsVertriebskanale.close();
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	
//	}
	
//private ArrayList<String> getWarengruppen() {
//		
//		ArrayList<String> wgList = new ArrayList<String>();
//		ResultSet rsWarengruppen = null;
//		try {
//			rsWarengruppen = repository.getResult(
//					MainWindow.DBconnection, CrudBefehle.selectWarengruppen);
//		
//			while (rsWarengruppen.next()) {
//
//				String bezeichnung = rsWarengruppen
//						.getString("Bezeichnung");
//				wgList.add(bezeichnung);
//
//			}
//			return wgList;
//		
//		} catch (Exception e) {
//			System.err.println(e);
//			e.printStackTrace();
//			return null;
//		} finally {
//			try {
//				rsWarengruppen.close();
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		
//	}
}
