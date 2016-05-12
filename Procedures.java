import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;

public class Procedures {
	private ColorMeasurement colorMeasure;
	private DistanceSensor ultraSonic;
	private Claw claw;
	private LineFollower lineFollower;
	
	public Procedures() {
		Brick ev3 = BrickFinder.getDefault();
		lineFollower = new LineFollower(ev3);
		claw = new Claw(ev3);
		colorMeasure = new ColorMeasurement(ev3);
		ultraSonic = new DistanceSensor(ev3);
		
		//initiate the display
		//display = ev3.getGraphicsLCD();
	}
	
	public void PickUpMode(int colorID) {
		lineFollower.PickUpSpeed();
		while (!colorMeasure.ColorID(colorID)) {
			lineFollower.PIDController();
		}
		lineFollower.TurnLeft();
		claw.OpenClaw();
		lineFollower.TurnRight();
	}
	
	public void NormalMode() {
		lineFollower.NormalSpeed();
	}
	
	public void ClosePorts() {
		lineFollower.ClosePorts();
		claw.ClosePorts();
		colorMeasure.ClosePorts();
		ultraSonic.ClosePorts();
	}
	/*
	public static void main(String[] args) {
		Procedures p = new Procedures();
		/*
		for (int i = 0; i<3000; i++) {
		 
			p.lineFollower.PIDController();
		}
		
		for (int i = 0; i<5000; i++) {
			p.lineFollower.PIDController();
		}
		p.ClosePorts();
		*/
		//p.PickUpMode(2);
	//}
	
}
