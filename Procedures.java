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
		lineFollower.StopMotors();
		//communication
		lineFollower.PickUpSpeed();
		while (!colorMeasure.ColorID(colorID)) {
			lineFollower.PIDController();
		}
		lineFollower.StopMotors();
		lineFollower.TurnLeft();
		claw.OpenClaw();
		lineFollower.TurnRight();
	}
	
	public void DriveMode() {
		lineFollower.NormalSpeed();
		float dist = modeScanner.ModeMeasurement();
		while (!(dist<0.1f && dist>0.01f)) {
			lineFollower.PIDController();
			dist = modeScanner.ModeMeasurement();
		}
		lineFollower.StopMotors();
		//communication
	}
	
	public void StoreMode() {
		lineFollower.TurnLeft();
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
	
	public static void main(String[] args) {
		Procedures p = new Procedures();
		p.DriveMode();
		p.PickUpMode(0);
		
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