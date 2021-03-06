package gui;

import interfaces.IABCRepository;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import objects.Strings;
import sqliteRepository.CrudBefehle;

public class PanelEinteilung extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Textfelder mit den Einteilungen
	private static JFormattedTextField txtUmsatzA;
	private static JFormattedTextField txtUmsatzB;
	private static JFormattedTextField txtUmsatzC;
	private static JFormattedTextField txtAnzahlA;
	private static JFormattedTextField txtAnzahlB;
	private static JFormattedTextField txtAnzahlC;
	private static JFormattedTextField txtMengeA;
	private static JFormattedTextField txtMengeB;
	private static JFormattedTextField txtMengeC;

	//Repository fuer die Datenbankverbindung
	private IABCRepository repository;
	/**
	 * 
	 */
	public PanelEinteilung(IABCRepository _repository) {
		repository = _repository;
		
		//Event fuer das Aufrufen der pruefenden Methode der Einteilung
		KeyListener keyListener = new KeyListener() {
		      public void keyPressed(KeyEvent keyEvent) {
		        
		      }

		      public void keyReleased(KeyEvent keyEvent) {
		    	  if(keyEvent.getKeyCode() != KeyEvent.VK_ENTER)
		    	  proofPercentValues();
		      }

		      public void keyTyped(KeyEvent keyEvent) {
		      }
		};

		//Layout-Optionen   --Start
		this.setLayout(new BorderLayout(0, 0));

		JPanel panelEinteilungHeader = new JPanel();
		this.add(panelEinteilungHeader, BorderLayout.NORTH);
		panelEinteilungHeader.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JLabel lblEinteilung = new JLabel(Strings.getLbl1str());
		panelEinteilungHeader.add(lblEinteilung);

		JPanel panelEinteilungContent = new JPanel();
		this.add(panelEinteilungContent, BorderLayout.CENTER);

		NumberFormat percentFormat = NumberFormat.getNumberInstance();

		GridBagLayout gbl_panelEinteilungContent = new GridBagLayout();
		gbl_panelEinteilungContent.columnWidths = new int[] { 70, 100, 100,
				100, 0 };
		gbl_panelEinteilungContent.rowHeights = new int[] { 36, 5, 36, 40, 36,
				40, 36, 0 };
		gbl_panelEinteilungContent.columnWeights = new double[] { 0.0, 0.0,
				0.0, 0.0, Double.MIN_VALUE };
		gbl_panelEinteilungContent.rowWeights = new double[] { 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelEinteilungContent.setLayout(gbl_panelEinteilungContent);

		JLabel lblFiller = new JLabel("");
		GridBagConstraints gbc_lblFiller = new GridBagConstraints();
		gbc_lblFiller.fill = GridBagConstraints.BOTH;
		gbc_lblFiller.insets = new Insets(0, 0, 5, 5);
		gbc_lblFiller.gridx = 0;
		gbc_lblFiller.gridy = 0;
		panelEinteilungContent.add(lblFiller, gbc_lblFiller);

		JLabel lblA = new JLabel("A");
		lblA.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblA = new GridBagConstraints();
		gbc_lblA.fill = GridBagConstraints.BOTH;
		gbc_lblA.insets = new Insets(0, 0, 5, 5);
		gbc_lblA.gridx = 1;
		gbc_lblA.gridy = 0;
		panelEinteilungContent.add(lblA, gbc_lblA);

		JLabel lblB = new JLabel("B");
		lblB.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblB = new GridBagConstraints();
		gbc_lblB.fill = GridBagConstraints.BOTH;
		gbc_lblB.insets = new Insets(0, 0, 5, 5);
		gbc_lblB.gridx = 2;
		gbc_lblB.gridy = 0;
		panelEinteilungContent.add(lblB, gbc_lblB);

		JLabel lblC = new JLabel("C");
		lblC.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblC = new GridBagConstraints();
		gbc_lblC.fill = GridBagConstraints.BOTH;
		gbc_lblC.insets = new Insets(0, 0, 5, 0);
		gbc_lblC.gridx = 3;
		gbc_lblC.gridy = 0;
		panelEinteilungContent.add(lblC, gbc_lblC);

		JLabel lblUmsatz = new JLabel(Strings.getUmsatz());
		GridBagConstraints gbc_lblUmsatz = new GridBagConstraints();
		gbc_lblUmsatz.fill = GridBagConstraints.BOTH;
		gbc_lblUmsatz.insets = new Insets(0, 0, 5, 5);
		gbc_lblUmsatz.gridx = 0;
		gbc_lblUmsatz.gridy = 2;
		panelEinteilungContent.add(lblUmsatz, gbc_lblUmsatz);

		txtUmsatzA = new JFormattedTextField(percentFormat);
		GridBagConstraints gbc_txtUmsatzA = new GridBagConstraints();
		gbc_txtUmsatzA.fill = GridBagConstraints.BOTH;
		gbc_txtUmsatzA.insets = new Insets(0, 0, 5, 5);
		gbc_txtUmsatzA.gridx = 1;
		gbc_txtUmsatzA.gridy = 2;
		panelEinteilungContent.add(txtUmsatzA, gbc_txtUmsatzA);

		txtUmsatzB = new JFormattedTextField(percentFormat);
		GridBagConstraints gbc_txtUmsatzB = new GridBagConstraints();
		gbc_txtUmsatzB.fill = GridBagConstraints.BOTH;
		gbc_txtUmsatzB.insets = new Insets(0, 0, 5, 5);
		gbc_txtUmsatzB.gridx = 2;
		gbc_txtUmsatzB.gridy = 2;
		panelEinteilungContent.add(txtUmsatzB, gbc_txtUmsatzB);

		txtUmsatzC = new JFormattedTextField(percentFormat);
		txtUmsatzC.setEditable(false);
		txtUmsatzC.setBackground(new Color(227,227,227));
		GridBagConstraints gbc_txtUmsatzC = new GridBagConstraints();
		gbc_txtUmsatzC.fill = GridBagConstraints.BOTH;
		gbc_txtUmsatzC.insets = new Insets(0, 0, 5, 0);
		gbc_txtUmsatzC.gridx = 3;
		gbc_txtUmsatzC.gridy = 2;
		panelEinteilungContent.add(txtUmsatzC, gbc_txtUmsatzC);

		JLabel lblAnzahl = new JLabel("Anzahl:");
		GridBagConstraints gbc_lblAnzahl = new GridBagConstraints();
		gbc_lblAnzahl.fill = GridBagConstraints.BOTH;
		gbc_lblAnzahl.insets = new Insets(0, 0, 5, 5);
		gbc_lblAnzahl.gridx = 0;
		gbc_lblAnzahl.gridy = 4;
		panelEinteilungContent.add(lblAnzahl, gbc_lblAnzahl);

		txtAnzahlA = new JFormattedTextField(percentFormat);
		GridBagConstraints gbc_txtAnzahlA = new GridBagConstraints();
		gbc_txtAnzahlA.fill = GridBagConstraints.BOTH;
		gbc_txtAnzahlA.insets = new Insets(0, 0, 5, 5);
		gbc_txtAnzahlA.gridx = 1;
		gbc_txtAnzahlA.gridy = 4;
		panelEinteilungContent.add(txtAnzahlA, gbc_txtAnzahlA);

		txtAnzahlB = new JFormattedTextField(percentFormat);
		GridBagConstraints gbc_txtAnzahlB = new GridBagConstraints();
		gbc_txtAnzahlB.fill = GridBagConstraints.BOTH;
		gbc_txtAnzahlB.insets = new Insets(0, 0, 5, 5);
		gbc_txtAnzahlB.gridx = 2;
		gbc_txtAnzahlB.gridy = 4;
		panelEinteilungContent.add(txtAnzahlB, gbc_txtAnzahlB);

		txtAnzahlC = new JFormattedTextField(percentFormat);
		txtAnzahlC.setEditable(false);
		txtAnzahlC.setBackground(new Color(227,227,227));
		GridBagConstraints gbc_txtAnzahlC = new GridBagConstraints();
		gbc_txtAnzahlC.fill = GridBagConstraints.BOTH;
		gbc_txtAnzahlC.insets = new Insets(0, 0, 5, 0);
		gbc_txtAnzahlC.gridx = 3;
		gbc_txtAnzahlC.gridy = 4;
		panelEinteilungContent.add(txtAnzahlC, gbc_txtAnzahlC);

		JLabel lblMenge = new JLabel(Strings.getMenge());
		GridBagConstraints gbc_lblMenge = new GridBagConstraints();
		gbc_lblMenge.fill = GridBagConstraints.BOTH;
		gbc_lblMenge.insets = new Insets(0, 0, 0, 5);
		gbc_lblMenge.gridx = 0;
		gbc_lblMenge.gridy = 6;
		panelEinteilungContent.add(lblMenge, gbc_lblMenge);

		txtMengeA = new JFormattedTextField(percentFormat);
		GridBagConstraints gbc_txtMengeA = new GridBagConstraints();
		gbc_txtMengeA.fill = GridBagConstraints.BOTH;
		gbc_txtMengeA.insets = new Insets(0, 0, 0, 5);
		gbc_txtMengeA.gridx = 1;
		gbc_txtMengeA.gridy = 6;
		panelEinteilungContent.add(txtMengeA, gbc_txtMengeA);

		txtMengeB = new JFormattedTextField(percentFormat);
		GridBagConstraints gbc_txtMengeB = new GridBagConstraints();
		gbc_txtMengeB.fill = GridBagConstraints.BOTH;
		gbc_txtMengeB.insets = new Insets(0, 0, 0, 5);
		gbc_txtMengeB.gridx = 2;
		gbc_txtMengeB.gridy = 6;
		panelEinteilungContent.add(txtMengeB, gbc_txtMengeB);

		txtMengeC = new JFormattedTextField(percentFormat);
		txtMengeC.setEditable(false);
		txtMengeC.setBackground(new Color(227,227,227));
		GridBagConstraints gbc_txtMengeC = new GridBagConstraints();
		gbc_txtMengeC.fill = GridBagConstraints.BOTH;
		gbc_txtMengeC.gridx = 3;
		gbc_txtMengeC.gridy = 6;
		panelEinteilungContent.add(txtMengeC, gbc_txtMengeC);

		JPanel panelEinteilungFoot = new JPanel();
		this.add(panelEinteilungFoot, BorderLayout.SOUTH);
		panelEinteilungFoot.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		//Layout-Optionen --Ende
		
		JButton btnSpeichern = new JButton(Strings.getSpeichern());
		btnSpeichern.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(proofPercentValues())
				{
					updateABCEinteilung();
				}
				else
				{
					JOptionPane.showMessageDialog(MainWindow.frame,
							Strings.getMsgError1(),
						Strings.getMsgError2(), JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		panelEinteilungFoot.add(btnSpeichern);

		// Schwellwerte aus der Datenbank laden
		getABCEinteilung();

		txtUmsatzA.addKeyListener(keyListener);
		txtUmsatzB.addKeyListener(keyListener);
		txtAnzahlA.addKeyListener(keyListener);
		txtAnzahlB.addKeyListener(keyListener);
		txtMengeA.addKeyListener(keyListener);
		txtMengeB.addKeyListener(keyListener);
	}

	/**
	 * Lädt die Schwellwerte aus der Datenbank und zeigt diese in den
	 * Textfeldern an
	 */
	private void getABCEinteilung() {
		try {
			ResultSet abcEinteilungResult = repository.getEinteilungenResult();

			while (abcEinteilungResult.next()) {

				String Bezeichnung = abcEinteilungResult
						.getString(Strings.getBezeichnung());

				switch (Bezeichnung) {
				case "Umsatz":
					txtUmsatzA.setText(String.valueOf(abcEinteilungResult
							.getInt("AnteilA")));
					txtUmsatzB.setText(String.valueOf(abcEinteilungResult
							.getInt("AnteilB")));
					txtUmsatzC.setText(String.valueOf(abcEinteilungResult
							.getInt("AnteilC")));
					break;
				case "Menge":
					txtMengeA.setText(String.valueOf(abcEinteilungResult
							.getInt("AnteilA")));
					txtMengeB.setText(String.valueOf(abcEinteilungResult
							.getInt("AnteilB")));
					txtMengeC.setText(String.valueOf(abcEinteilungResult
							.getInt("AnteilC")));
					break;
				case "Auftragsanzahl":
					txtAnzahlA.setText(String.valueOf(abcEinteilungResult
							.getInt("AnteilA")));
					txtAnzahlB.setText(String.valueOf(abcEinteilungResult
							.getInt("AnteilB")));
					txtAnzahlC.setText(String.valueOf(abcEinteilungResult
							.getInt("AnteilC")));
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Aktualisiert die Schwellwerte der ABC-Einteilung in der Datenbank
	 */
	private void updateABCEinteilung() {
		// Umsatz
		repository.updateABCEinteilung(CrudBefehle.updateEinteilungAnteilA,
				Integer.parseInt(txtUmsatzA.getText()), Strings.getUmsatz());
		repository.updateABCEinteilung(CrudBefehle.updateEinteilungAnteilB,
				Integer.parseInt(txtUmsatzB.getText()), Strings.getUmsatz());
		repository.updateABCEinteilung(CrudBefehle.updateEinteilungAnteilC,
				Integer.parseInt(txtUmsatzC.getText()), Strings.getUmsatz());
		// Menge
		repository.updateABCEinteilung(CrudBefehle.updateEinteilungAnteilA,
				Integer.parseInt(txtMengeA.getText()), Strings.getMenge());
		repository.updateABCEinteilung(CrudBefehle.updateEinteilungAnteilB,
				Integer.parseInt(txtMengeB.getText()), Strings.getMenge());
		repository.updateABCEinteilung(CrudBefehle.updateEinteilungAnteilC,
				Integer.parseInt(txtMengeC.getText()), Strings.getMenge());
		// Auftragsanzahl
		repository.updateABCEinteilung(CrudBefehle.updateEinteilungAnteilA,
				Integer.parseInt(txtAnzahlA.getText()), Strings.getAuftragsanzahl());
		repository.updateABCEinteilung(CrudBefehle.updateEinteilungAnteilB,
				Integer.parseInt(txtAnzahlB.getText()), Strings.getAuftragsanzahl());
		repository.updateABCEinteilung(CrudBefehle.updateEinteilungAnteilC,
				Integer.parseInt(txtAnzahlC.getText()), Strings.getAuftragsanzahl());

		JOptionPane.showMessageDialog(MainWindow.frame,
		Strings.getMsgSchwellwertSuccess1(),
				Strings.getMsgSchwellwertSuccess2(), JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Prueft, ob die eingegebenen Werte korrekt sind, bedeutet A & B kleiner als 100
	 * und die eingegebenen Werte sind Zahlen
	 */
	private static boolean proofPercentValues() {
		boolean isSaveable = true;
		try {
			// Umsatz
			if (validate(txtUmsatzA.getText(), txtUmsatzB.getText(), Strings.getUmsatz())) {
				int UmsatzA = Integer.parseInt(txtUmsatzA.getText());
				int UmsatzB = Integer.parseInt(txtUmsatzB.getText());
				if ((UmsatzA + UmsatzB) > 100) {
					JOptionPane.showMessageDialog(MainWindow.frame,
							Strings.getMsgSchwellwertFail1(),
							Strings.getMsgSchwellwertFail2(), JOptionPane.ERROR_MESSAGE);
					isSaveable = false;
				} else {
					txtUmsatzC.setText(String
							.valueOf(100 - (UmsatzA + UmsatzB)));
				}
			} else {
				txtUmsatzC.setText("0");
				isSaveable = false;
			}
			// Menge
			if (validate(txtMengeA.getText(), txtMengeB.getText(), Strings.getMenge())) {
				int MengeA = Integer.parseInt(txtMengeA.getText());
				int MengeB = Integer.parseInt(txtMengeB.getText());
				if ((MengeA + MengeB) > 100) {
					JOptionPane.showMessageDialog(null,
							Strings.getMsgSchwellwertFail1(),
							Strings.getMsgSchwellwertFail2(), JOptionPane.ERROR_MESSAGE);
					isSaveable = false;
				} else {
					txtMengeC.setText(String.valueOf(100 - (MengeA + MengeB)));
				}
			} else {
				txtMengeC.setText("0");
				isSaveable = false;
			}
			// Anzahl
			if (validate(txtAnzahlA.getText(), txtAnzahlB.getText(), Strings.getAuftragsanzahl())) {
				int AnzahlA = Integer.parseInt(txtAnzahlA.getText());
				int AnzahlB = Integer.parseInt(txtAnzahlB.getText());
				if ((AnzahlA + AnzahlB) > 100) {
					JOptionPane
							.showMessageDialog(
									MainWindow.frame,
									Strings.getMsgSchwellwertFail1(),
									Strings.getMsgSchwellwertFail2(), JOptionPane.ERROR_MESSAGE);
					isSaveable = false;
				} else {
					txtAnzahlC.setText(String
							.valueOf(100 - (AnzahlA + AnzahlB)));
				}
			} else {
				txtAnzahlC.setText("0");
				isSaveable = false;
			}
			return isSaveable;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * Prueft die Kriterien A & B ob eine Zahl enthalten ist, ansonsten falscher Wert
	 * @param kriteriumA
	 * @param kriteriumB
	 * @param kriteriumsBezeichnung
	 * @return
	 */
	private static boolean validate(String kriteriumA, String kriteriumB, String kriteriumsBezeichnung) {
		if(!kriteriumA.equals("") && !kriteriumB.equals(""))
		{
			try {
				Integer.parseInt(kriteriumA);
				Integer.parseInt(kriteriumB);
				return true;
			} catch (Exception ex) {
				JOptionPane
				.showMessageDialog(
						MainWindow.frame,
						Strings.getMsgInputFail1() +"(" + kriteriumsBezeichnung + ")",
						Strings.getMsgInputFail2(),
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return false;
	}
}
