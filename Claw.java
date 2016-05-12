import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.motor.EV3MediumRegulatedMotor;

public class Claw {
	private EV3MediumRegulatedMotor motorClaw;
	
	public Claw(Brick ev3) {
		motorClaw = new EV3MediumRegulatedMotor(ev3.getPort("A"));
}
	public void OpenClaw() {
		motorClaw.setSpeed(400);
		motorClaw.rotate(900);
	}
	
	public void CloseClaw() {
		motorClaw.setSpeed(400);
		motorClaw.rotate(-900);
	}
	
	public void ClosePorts() {
		motorClaw.close();
	}
}
