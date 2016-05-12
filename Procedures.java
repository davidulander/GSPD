import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;

public class Procedures {
	private ColorMeasurement colorMeasure;
	private DistanceSensor ultraSonic;
	private Claw claw;
	private LineFollower lineFollower;
	private ModeSensor modeScanner;
	private int side;
	
	public Procedures() {
		Brick ev3 = BrickFinder.getDefault();
		lineFollower = new LineFollower(ev3);
		claw = new Claw(ev3);
		colorMeasure = new ColorMeasurement(ev3);
		ultraSonic = new DistanceSensor(ev3);
		modeScanner = new ModeSensor(ev3);
		//initiate the display
		//display = ev3.getGraphicsLCD();
	}
	
	public void PickUpMode(int colorID) {
		lineFollower.PickUpSpeed();
		while (!colorMeasure.ColorID(colorID)) {
			lineFollower.PIDController();
		}
		lineFollower.StopMotors();
		lineFollower.TurnLeft();
		claw.OpenClaw();
		lineFollower.TurnRight();
	}
	
	public void NormalMode() {
		lineFollower.NormalSpeed();
	}
	
	public void StoreMode() {
		lineFollower.StopMotors();
		lineFollower.TurnLeft();
		claw.OpenClaw();
		lineFollower.TurnRight();
	}
	public boolean PickUp() {
		
		return true;
	}
	
	public void Position() {
		side = 0;
		if (ultraSonic.DistanceMeasurement() < 0.2f) {
			side++;
		}
	}
	public boolean Store() {
		return (ultraSonic.DistanceMeasurement() < 0.3f);
	}
	
	
	
	
	
	public void ClosePorts() {
		lineFollower.ClosePorts();
		claw.ClosePorts();
		colorMeasure.ClosePorts();
		ultraSonic.ClosePorts();
	}
	
	public static void main(String[] args) {
		Procedures p = new Procedures();
		
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
