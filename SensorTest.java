import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class SensorTest {
	private EV3LargeRegulatedMotor motorB;
	private EV3LargeRegulatedMotor motorC;
	private EV3ColorSensor colorSensor;
	private EV3ColorSensor lineSensor;
	private EV3UltrasonicSensor ultraSonic;
	private GraphicsLCD display;
	
	public SensorTest() {
		Brick ev3 = BrickFinder.getDefault();
		//open ports. Open ports need to be closed before program ends
		motorB = new EV3LargeRegulatedMotor(ev3.getPort("B"));
		motorC = new EV3LargeRegulatedMotor(ev3.getPort("C"));
		ultraSonic = new EV3UltrasonicSensor(ev3.getPort("S2"));
		
		//initiate the display
		display = ev3.getGraphicsLCD();
		//display.drawString("Starting Program", 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
		//Delay.msDelay(2000);
		

	}
	
	public void DistanceMeasurement() {
		SampleProvider distance = ultraSonic.getDistanceMode();
		float[] sample = new float[distance.sampleSize()];
		
		//SampleProvider average = new MeanFilter(distance, 5);
		while (!Button.ESCAPE.isDown()) {
			distance.fetchSample(sample, 0);
			String sampleString = "value " + ": " + sample[0];
			display.drawString(sampleString, 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
			display.drawString("Escape to exit!", 0, 40, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
			Delay.msDelay(1000);
			display.clear();
		}
	}
	
	public void ColorMeasurement() {
		SampleProvider RGBSensor = colorSensor.getColorIDMode();
		int sampleSize = RGBSensor.sampleSize();            
		float[] sample = new float[sampleSize];
		RGBSensor.fetchSample(sample, 0);
		colorSensor.close();
	}
	
	public void LineMeasurement() {
		SampleProvider blackLineSensor = lineSensor.getColorIDMode();
		int sampleSize = blackLineSensor.sampleSize();            
		float[] sample = new float[sampleSize];
		blackLineSensor.fetchSample(sample, 0);
		lineSensor.close();
	}
	
	public void ForwardMotors(int speed) {
		motorB.setSpeed(speed);
		motorC.setSpeed(speed);
		motorB.forward();
		motorC.forward();
	}
	
	public void StopMotors() {
		motorB.stop(true);
		motorC.stop(true);
	}

	
	public void TurnRight90() {
		motorB.rotate(380);
	}
	
	public void TurnLeft90() {
		motorC.rotate(380);
	}
	
	// all opened ports need to be closed!
	public void ClosePorts() {
		motorB.close();
		motorC.close();
		ultraSonic.close();
	}
	
	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		
		SensorTest test = new SensorTest();
		//test.ForwardMotors(500);
		//Delay.msDelay(2000);
		//test.StopMotors();
		//test.TurnRight90();
		//test.TurnLeft90();
		//Delay.msDelay(2000);
		//test.StopMotors();
		
		test.DistanceMeasurement();
		test.ClosePorts();
	}
}

