import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class LineFollower {
	private EV3LargeRegulatedMotor motorB;
	private EV3LargeRegulatedMotor motorC;
	private EV3ColorSensor lineSensor;
	private GraphicsLCD display;
	

	
	public LineFollower() {
		Brick ev3 = BrickFinder.getDefault();
		motorB = new EV3LargeRegulatedMotor(ev3.getPort("B"));
		motorC = new EV3LargeRegulatedMotor(ev3.getPort("C"));
		lineSensor = new EV3ColorSensor(ev3.getPort("S4"));
		
		//initiate the display
		display = ev3.getGraphicsLCD();
	}
	//not finished
	//method for measuring the brightness of the edge of the black line
	//white = 0.12 and black = 0.02 the edge (half white half black) = 0.05
	//if we measures the right side of the line. An increasing value should
	//steer the robot to the right and vice versa.
	public float LineMeasurement() {
		
		SampleProvider blackLineSensor = lineSensor.getAmbientMode();
		int sampleSize = blackLineSensor.sampleSize();            
		float[] sample = new float[sampleSize];
		
		blackLineSensor.fetchSample(sample, 0);
		float lightValue = sample[0]*100-2;
		
		if (lightValue > 10) {
			lightValue = 10;
		} else if (lightValue < 0) {
			lightValue = 0;
		}
		/* 
		uncomment to see the light values on the display
		String sampleString = "value " + ": " + lightValue;
		display.drawString(sampleString, 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
		display.drawString("Escape to exit!", 0, 40, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
		Delay.msDelay(1000);
		display.clear();
		*/		
		return lightValue;
	}
	
	
	
	public void PIDController() {
		int tp = 200;					//tunable!! targeted motor speed
		float offset = 5;				//value read at the edge
		float Kp = 10; 					//tunable!! initial estimate
		float Ki = 1;					//tunable!! 
		float Kd = 80;					//tunable!!
		float integral = 0;
		float derivative = 0;
		float lastError = 0;
				
		while (!Button.ESCAPE.isDown()) {
			display.drawString("Escape to exit!", 0, 40, GraphicsLCD.VCENTER |
					GraphicsLCD.LEFT);
			
			float lightValue = LineMeasurement();
			
			float error = lightValue - offset;
			
			if (error == 0){
				integral = 0;
			}
			
			integral = (3/4)*integral + error;
			derivative = error - lastError;
			float turn = Kp*error + Ki*integral + Kd*derivative;
			float powerB = tp + turn;
			float powerC = tp - turn;
			
			motorB.setSpeed(powerB);
			motorC.setSpeed(powerC);
			if (!motorB.isMoving() || !motorC.isMoving() ) {
				motorB.forward();
				motorC.forward();
			}
			lastError = error;
		}

	}
	
	
	// all opened ports need to be closed!
	public void ClosePorts() {
		motorB.close();
		motorC.close();
		lineSensor.close();
	}
	
	public static void main(String[] args) {
		LineFollower test = new LineFollower();
		//test.LineMeasurement();
		//Delay.msDelay(3000);
		test.PIDController();
		test.ClosePorts();
		
	}

}
