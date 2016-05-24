import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.utility.Delay;

import java.util.Date;
import java.util.Timer;

public class Procedures {
	private ColorMeasurement colorMeasure;
	//private DistanceSensor ultraSonic;
	private Claw claw;
	private LineFollower lineFollower;
	private ModeSensor modeScanner;
	private int side = 0;
	private boolean pickUp = false;
	private int foo = 0;
	
	public Procedures() {
		Brick ev3 = BrickFinder.getDefault();
		lineFollower = new LineFollower(ev3);
		claw = new Claw(ev3);
		colorMeasure = new ColorMeasurement(ev3);
		//ultraSonic = new DistanceSensor(ev3);
		modeScanner = new ModeSensor(ev3);
		//Communication c = new Communication();
		//initiate the display
		//display = ev3.getGraphicsLCD();
	}
	public boolean PickUp() {
		
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
		
		/*
		boolean isGreen = colorMeasure.ColorID(1);
		if (isGreen) {
			return false;
		} else {
			return true;
		}
		*/
	}
	
	public void PickUpMode(int colorID) {
		//lineFollower.NormalSpeed();
		// lägg till lineFollower.RestetParameters();
		while (!PickUp()) {
			lineFollower.PIDController();
		}
		//lineFollower.StopMotors();
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
		lineFollower.RestetParameters();
		while (PickUp()) {
			lineFollower.PIDController();
		}
		lineFollower.NormalSpeed();
		//modeScanner.ResetStop();
		//claw.OpenClaw();
		//Delay.msDelay(2000);
	}
	
	public void DriveMode0() {
		lineFollower.RestetParameters();
		lineFollower.NormalSpeed();
		float dist = modeScanner.ModeMeasurement();
		
		long startTime = System.currentTimeMillis();
		long elapsedTime = 0L;

		while (elapsedTime < 3*1000) {
			lineFollower.PIDController();
			dist = modeScanner.ModeMeasurement();
			side++;
		    elapsedTime = (new Date()).getTime() - startTime;
		}
		modeScanner.Enable();
	}
	
	public void DriveMode1() {
		lineFollower.NormalSpeed();
		lineFollower.RestetParameters();
		float dist = modeScanner.ModeMeasurement();
		while (!(dist<0.05f && dist>0.001f)) {
			lineFollower.PIDController();
			dist = modeScanner.ModeMeasurement();
			side++;
		}
		modeScanner.Disable();
	}
	
	
	public void StoreMode() {	
		lineFollower.StopMotors(); //ny
		lineFollower.LeftStore();
		claw.OpenClaw();
		lineFollower.TurnRight();
		lineFollower.RestetParameters();
		
		long startTime = System.currentTimeMillis();
		long elapsedTime = 0L;

		while (elapsedTime < 2*1000) {
			lineFollower.PIDController();
			//dist = modeScanner.ModeMeasurement();
			side++;
		    elapsedTime = (new Date()).getTime() - startTime;
		}
	}


	public void ClosePorts() {
		lineFollower.ClosePorts();
		claw.ClosePorts();
		colorMeasure.ClosePorts();
		//ultraSonic.ClosePorts();
		modeScanner.ClosePorts();
	}
	
	public static void main(String[] args) {
		Procedures p = new Procedures();
		//while (!Button.ESCAPE.isDown()) {
		
		
		//
		//p.DriveMode0();
	
		for (int i = 0; i < 8; i++) {
			
			if (i % 4 == 0) {
				//p.lineFollower.StopMotors();
				//error
				p.PickUpMode(0);
			}
			
			p.DriveMode0();
			p.DriveMode1();
			
			
			
			if (i + 1 == 2 || i+1 == 8) {
				p.lineFollower.StopMotors();
				p.StoreMode();
				//p.lineFollower.StopMotors(); || i+1 == 11 || i+1 == 16
				// problems
			//}
			}
		}
		
		
		p.ClosePorts();
		 
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
