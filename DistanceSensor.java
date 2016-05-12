import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;
import lejos.utility.Delay;

public class DistanceSensor {
	private EV3UltrasonicSensor ultraSonic;
	private GraphicsLCD display;
	
	public DistanceSensor(Brick ev3) {
		ultraSonic = new EV3UltrasonicSensor(ev3.getPort("S2"));
		display = ev3.getGraphicsLCD();
	}
	
	public float DistanceMeasurement() {
		SampleProvider distance = ultraSonic.getDistanceMode();
		SampleProvider distanceAvg = new MeanFilter(distance, 5);
		float[] sample = new float[distanceAvg.sampleSize()];
		//distanceAvg.fetchSample(sample, 0);
		//while (!Button.ESCAPE.isDown()) {
			distanceAvg.fetchSample(sample, 0);
			/*
			String sampleString = "value " + ": " + sample[0];
			display.drawString(sampleString, 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
			display.drawString("Escape to exit!", 0, 40, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
			Delay.msDelay(1000);
			display.clear();
			*/
			
		return sample[0];
	}
	public void ClosePorts() {
		ultraSonic.close();
	}
	/*
	public static void main(String[] args) {
		DistanceSensor ds = new DistanceSensor(BrickFinder.getDefault());
		ds.DistanceMeasurement();
		ds.ClosePorts();
	}
	*/
}
