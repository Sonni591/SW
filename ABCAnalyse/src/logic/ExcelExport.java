package logic;

import java.awt.BorderLayout;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelExport extends Thread {
	JTable resultTable;
	File file;

	public ExcelExport(JTable resultTable, File file) {
		this.resultTable = resultTable;
		this.file = file;
	}

	public void run() {
		try {
			final JDialog dlg = new JDialog();
			JProgressBar dpb = new JProgressBar(0, 500);
			dpb.setMaximum(3);
			dpb.setValue(0);
			dlg.add(BorderLayout.CENTER, dpb);
			JLabel lblDpbState = new JLabel("Lade Spaltennamen");
			dlg.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			dlg.setSize(300, 125);
			dlg.setLocationRelativeTo(null);
			dlg.setVisible(true);

			Workbook fWorkbook = new XSSFWorkbook();
			Sheet fSheet = fWorkbook.createSheet("newSheet");
			Font sheetTitleFont = fWorkbook.createFont();
			CellStyle cellStyle = fWorkbook.createCellStyle();

			sheetTitleFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			// sheetTitleFont.setColor();
			TableModel model = resultTable.getModel();
			dlg.add(BorderLayout.NORTH, lblDpbState);
			// Spaltennamen
			TableColumnModel tcm = resultTable.getColumnModel();
			Row fRow = fSheet.createRow(0);
			for (int j = 0; j < resultTable.getColumnCount(); j++) {
				Cell cell = fRow.createCell(j);
				cell.setCellValue(tcm
						.getColumn(resultTable.convertColumnIndexToModel(j))
						.getHeaderValue().toString());

			}
			lblDpbState.setText("Lade Daten");
			dpb.setValue(1);
			// Daten der Tabelle
			for (int i = 0; i < resultTable.getRowCount(); i++) {
				fRow = fSheet.createRow(i + 1);
				for (int j = 0; j < resultTable.getColumnCount(); j++) {
					Cell cell = fRow.createCell(j);
					cell.setCellValue(model.getValueAt(
							resultTable.convertRowIndexToModel(i),
							resultTable.convertColumnIndexToModel(j))
							.toString());
					cell.setCellStyle(cellStyle);
				}
			}
			lblDpbState.setText("Auf Festplatte Schreiben");
			dpb.setValue(2);
			FileOutputStream fileOutputStream;
			fileOutputStream = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(
					fileOutputStream);
			fWorkbook.write(bos);
			dpb.setValue(3);
			Thread.sleep(100);
			bos.close();
			fileOutputStream.close();
			System.out.println("Finished");
			dlg.setVisible(false);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}

