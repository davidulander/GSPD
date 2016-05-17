
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Control;
import javafx.scene.layout.StackPane;

public class mapView {

int dataX = 5;
int dataY = 5;
	
public StackPane map(int X, int Y){
	
	dataX = X;
	dataY = Y;
	
	StackPane map = new StackPane();
	
	 final NumberAxis xAxis = new NumberAxis(1,10,1);
     final NumberAxis yAxis = new NumberAxis(1,10,1);
     
     
     
     //creating the chart
     
     
     ScatterChart<Number, Number> lineChart = new ScatterChart<Number,Number>(xAxis, yAxis);
             
     lineChart.setTitle("RoboMap");
     
     
     //defining a series
     XYChart.Series series = new XYChart.Series();
     
     //populating the series with data
     series.getData().add(new XYChart.Data(dataX, dataY));
     
     
     
     map.getChildren().add(lineChart);
     lineChart.getData().add(series);
     lineChart.setLegendVisible(false);
     xAxis.setLabel("Current Location: "+dataX+"X"+" , "+dataY+"Y");
     lineChart.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
     lineChart.setPrefSize(500, 500);
     lineChart.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
     
    return map;
}

}
