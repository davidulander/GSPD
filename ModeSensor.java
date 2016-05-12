import lejos.hardware.Brick;
import lejos.hardware.Button;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;
import lejos.utility.Delay;

public class ModeSensor {
	private EV3UltrasonicSensor modeSonic;
	private GraphicsLCD display;
	
	public ModeSensor(Brick ev3) {
		modeSonic = new EV3UltrasonicSensor(ev3.getPort("S1"));
		display = ev3.getGraphicsLCD();
	}
	
	public float ModeMeasurement() {
		SampleProvider distance = modeSonic.getDistanceMode();
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
			
		//}	
		return sample[0];
	}
	public void ClosePorts() {
		modeSonic.close();
	}
}
