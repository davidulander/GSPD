import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class LineFollower {
	private EV3LargeRegulatedMotor motorB;
	private EV3LargeRegulatedMotor motorC;
	private EV3ColorSensor lineSensor;
	private GraphicsLCD display;

	
	int tp = 200;					//tunable!! targeted motor speed
	float offset = 125;				//value read at the edge
	//float Pc = 0.015f;
	//float dT = 0.3f;
	float Kp = 0.60f; 						//0.65tunable!! initial estimate 11
	float Ki = 0.02f;					//tunable!! 0.8 
	float Kd = 1;						//tunable!! 80 KpPc / (8dT)
	float integral = 0;
	float derivative = 0;
	float lastError = 0;
	

	public LineFollower(Brick ev3) {
		motorB = new EV3LargeRegulatedMotor(ev3.getPort("B"));
		motorC = new EV3LargeRegulatedMotor(ev3.getPort("C"));
		lineSensor = new EV3ColorSensor(ev3.getPort("S4"));
		/*
		claw = new Claw(ev3);
		colorMeasure = new ColorMeasurement(ev3);
		ultraSonic = new DistanceSensor(ev3);
		*/
		
		//initiate the display
		display = ev3.getGraphicsLCD();
	}
	

	
	public float LineMeasurement() {
		
		SampleProvider blackLineSensor = lineSensor.getAmbientMode();
		int sampleSize = blackLineSensor.sampleSize();            
		float[] sample = new float[sampleSize];
		/*
		while (!Button.ESCAPE.isDown()) {
			display.drawString("Escape to exit!", 0, 40, GraphicsLCD.VCENTER |
						GraphicsLCD.LEFT);
		*/
		
		blackLineSensor.fetchSample(sample, 0);
		float lightValue = 25*100*sample[0];
		
		if (lightValue > 300) {
			lightValue = 300;
		} else if (lightValue < 10) {
			lightValue = 10;
		}
		
		/*
		//uncomment to see the light values on the display
		String sampleString = "value " + ": " + lightValue;
		display.drawString(sampleString, 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
		display.drawString("Escape to exit!", 0, 40, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
		Delay.msDelay(1000);
		display.clear();
		}
		*/
		return lightValue;
	}
	
	public void RestetParameters() {
		this.derivative = 0;
		this.integral = 0;
		this.lastError = 0;
	}
	
	public void PIDController() {
			
			float lightValue = LineMeasurement();
			
			float error = lightValue - offset;
			
			if (error == 0){
				integral = 0;
			}
			
			integral = (0.5f)*integral + error;
			derivative = error - lastError;
			float turn = Kp*error + Ki*integral + Kd*derivative;
			float powerB = tp + 0.8f*turn;
			float powerC = tp - 0.8f*turn;
			
			motorB.setSpeed(powerB);
			motorC.setSpeed(powerC);
			if (!motorB.isMoving() || !motorC.isMoving() ) {
				motorB.forward();
				motorC.forward();
			}
			
			lastError = error;
		//}
	}
	
	public void PickUpSpeed() {
		this.tp = 130;
	}
	
	public void NormalSpeed() {
		this.tp = 180;
	}
	
	public void StopMotors() {
		motorB.stop(true);
		motorC.stop(true);
	}
	
	public void LeftPickUp() {
		//motorB.rotate(50);
		motorB.setSpeed(150);
		motorC.setSpeed(120);
		
		motorC.rotate(-50, true);
		motorB.rotate(-250);
		//motorC.rotate(-100);
		Delay.msDelay(1000);
		/*
		motorB.backward();
		motorC.backward();
		Delay.msDelay(900);
		motorC.rotate(-100);
		*/
		motorB.setSpeed(160);
		motorC.setSpeed(180);
		motorB.forward();
		motorC.forward();
		
		Delay.msDelay(900);
		motorB.stop(true);
		motorC.stop(true);
	}
	
	public void LeftStore() {
		motorC.rotate(370);
	}
	
	public void Backward() {
		motorB.setSpeed(100);
		motorC.setSpeed(300);
		motorB.backward();
		motorC.backward();
		
		Delay.msDelay(850);
		motorB.stop(true);
		motorC.stop(true);
	}
	public void TurnRight() {
		//motorC.rotate(-610,true);
		//motorB.rotate(-100);
		motorB.setSpeed(100);
		motorC.setSpeed(350);
		motorB.backward();
		motorC.backward();
		
		Delay.msDelay(1000);
		motorB.stop(true);
		motorC.stop(true);
	}
	
	// all opened ports need to be closed!
	public void ClosePorts() {
		motorB.close();
		motorC.close();
		lineSensor.close();
	}
	/*
	public static void main(String[] args) {
		LineFollower test = new LineFollower(BrickFinder.getDefault());
		test.LineMeasurement();
		//Delay.msDelay(3000);
		/*
		while (test.ultraSonic.DistanceMeasurement()>0.2f) {
			test.PIDController();
		}
		test.StopMotors();
		
		
		 while (!test.colorMeasure.ColorID(7)) {
			test.PIDController();
		}
		
		//test.claw.OpenClaw();
		//test.claw.CloseClaw();
		//test.ClosePorts();
	*/
	

}
