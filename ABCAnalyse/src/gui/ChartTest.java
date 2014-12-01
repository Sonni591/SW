package gui;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


public class ChartTest {
	public static void createchart(){
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		
		data.addValue(80, "C", "Umsatz");
		data.addValue(15, "B", "Umsatz");
		data.addValue(5, "A", "Umsatz");
		data.addValue(70, "C", "Menge");
		data.addValue(20, "B", "Menge");
		data.addValue(10, "A", "Menge");
		data.addValue(75, "C", "Absatz");
		data.addValue(20, "B", "Absatz");
		data.addValue(5, "A", "Absatz");
		// create a chart...
		JFreeChart chart = ChartFactory.createStackedBarChart(
				"ABC Analyse", 
				"Category", 
				"Score", 
				data, 
				PlotOrientation.VERTICAL, 
				true, 
				true, 
				false);
		// create and display a frame...
		ChartFrame frame = new ChartFrame("First", chart);
		frame.pack();
		frame.setVisible(true);
		
	}
}
