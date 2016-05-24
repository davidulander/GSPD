import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;
import lejos.utility.Delay;

public class ColorMeasurement {
	private EV3LargeRegulatedMotor motorB;
	private EV3LargeRegulatedMotor motorC;
	private EV3ColorSensor colorSensor;
	private GraphicsLCD display;
	private EV3MediumRegulatedMotor motorClaw;
	
	public ColorMeasurement(Brick ev3) {
		colorSensor = new EV3ColorSensor(ev3.getPort("S3"));
		//initiate the display
		display = ev3.getGraphicsLCD();
	}
	
	// measuring the color of the objects. 
	// 8 different colors, represented by 0,1,...,7
	// in order: (red, green, ??, yellow)
	public boolean ColorID(int colorID) {
		SampleProvider ColorIDSensor = colorSensor.getColorIDMode();
		SampleProvider ColorAvg = new MeanFilter(ColorIDSensor, 5);
		int sampleSize = ColorAvg.sampleSize();            
		float[] sample = new float[sampleSize];		
		ColorAvg.fetchSample(sample, 0);
		int ID = (int)sample[0];
			/*
			String sampleString = "value " + ": " + ID;
			display.drawString(sampleString, 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
			display.drawString("Escape to exit!", 0, 40, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
			Delay.msDelay(1000);
			display.clear();
			*/
			
		return colorID == ID;
		
	}
	
	// all opened ports need to be closed!
	public void ClosePorts() {
		colorSensor.close();
	}
	/*
	public static void main(String[] args) {
	ColorMeasurement ds = new ColorMeasurement(BrickFinder.getDefault());
	
	while(!Button.ESCAPE.isDown()) 
	{
		 ds.ColorID(3);	
	}
	ds.ClosePorts();
	*/
	

}
