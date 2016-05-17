import java.io.IOException;
import java.net.UnknownHostException;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.utility.Delay;

public class Procedures {
	private ColorMeasurement colorMeasure;
	private DistanceSensor ultraSonic;
	private Claw claw;
	private LineFollower lineFollower;
	private ModeSensor modeScanner;
	private int side = 0;
	private boolean pickUp = false;
	
	public Procedures() {
		Brick ev3 = BrickFinder.getDefault();
		lineFollower = new LineFollower(ev3);
		claw = new Claw(ev3);
		colorMeasure = new ColorMeasurement(ev3);
		ultraSonic = new DistanceSensor(ev3);
		modeScanner = new ModeSensor(ev3);
		//Communication c = new Communication();
		//initiate the display
		//display = ev3.getGraphicsLCD();
	}
	public boolean PickUp() {
		/*
		boolean isGreen = colorMeasure.ColorID(1);
		if (isGreen && !pickUp) {
			pickUp = !pickUp;
			return pickUp;
		} else if (isGreen && pickUp) {
			pickUp = !pickUp;
			return pickUp;
		} else {
			return pickUp;
		}
		*/
		
		
		boolean isGreen = colorMeasure.ColorID(1);
		if (isGreen) {
			return false;
		} else {
			return true;
		}
		
	}
	
	public void PickUpMode(int colorID) {
		lineFollower.StopMotors();
		side = 0;
		//communication
		lineFollower.PickUpSpeed();
		while (!colorMeasure.ColorID(colorID)) {
			lineFollower.PIDController();
		}
		lineFollower.StopMotors();
		lineFollower.LeftPickUp();
		claw.CloseClaw();
		Delay.msDelay(100);
		lineFollower.Backward();
		while (PickUp()) {
			lineFollower.PIDController();
		}
		//modeScanner.ResetStop();
		//claw.OpenClaw();
		//Delay.msDelay(2000);
	}
	
	public void DriveMode0() {
		lineFollower.NormalSpeed();
		for (int i = 0; i < 1700; i++) {
			lineFollower.PIDController();
		}
	}
	public void DriveMode1() {
		lineFollower.NormalSpeed();
		float dist = modeScanner.ModeMeasurement();
		while (!(dist<0.08f && dist>0.001f)) {
			lineFollower.PIDController();
			dist = modeScanner.ModeMeasurement();
			side++;
		}
		lineFollower.StopMotors();
		//wait for communication
		Delay.msDelay(2000);
	}
	
	
	public void StoreMode() {	
		lineFollower.LeftStore();
		claw.OpenClaw();
		lineFollower.TurnRight();
	}
	
	/*
	public void Position() {
		side = 0;
		if (ultraSonic.DistanceMeasurement() < 0.2f) {
			side++;
		}
	}
	*/

	public void ClosePorts() {
		lineFollower.ClosePorts();
		claw.ClosePorts();
		colorMeasure.ClosePorts();
		ultraSonic.ClosePorts();
		modeScanner.ClosePorts();
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		Procedures p = new Procedures();
		//while (!Button.ESCAPE.isDown()) {
		
		Communication c = new Communication();
		
		int a = c.getBox(0);
		int b = c.getBox(1);

		Brick ev3 = BrickFinder.getDefault();
		GraphicsLCD display = ev3.getGraphicsLCD();
		display.drawString("The product is: " + a, 0, 40, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
		Delay.msDelay(3000);
		display.clear();
		display.drawString("I should go: " + b, 0, 40, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
		Delay.msDelay(3000);
		display.clear();
		
		p.PickUpMode(a);
		for (int i=0; i < b; i++) {
			p.DriveMode0();
			p.DriveMode1();
			
		}
		
		p.StoreMode();
		p.DriveMode0();
		p.ClosePorts();
		 
		/*
		p.PickUpMode(3);
		
		p.DriveMode0();
		p.StoreMode();
		p.ClosePorts();
		*/
		/*
		while (p.modeScanner.ModeMeasurement()<0.1f) {
		 
			p.lineFollower.PIDController();
		}
		p.lineFollower.StopMotors();
		p.ClosePorts();
		/*
		for (int i = 0; i<5000; i++) {
			p.lineFollower.PIDController();
		}
		
		*/
		//p.PickUpMode(2);
				
	}
	
}