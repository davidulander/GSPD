import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
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
    //private EV3ColorSensor lineSensor;
    private EV3UltrasonicSensor ultraSonic;
    private GraphicsLCD display;
    private EV3MediumRegulatedMotor motorClaw;
    
    public SensorTest() {
        Brick ev3 = BrickFinder.getDefault();
        //open ports. Open ports need to be closed before program ends
        motorB = new EV3LargeRegulatedMotor(ev3.getPort("B"));
        motorC = new EV3LargeRegulatedMotor(ev3.getPort("C"));
        motorClaw = new EV3MediumRegulatedMotor(ev3.getPort("A"));
        ultraSonic = new EV3UltrasonicSensor(ev3.getPort("S2"));
        colorSensor = new EV3ColorSensor(ev3.getPort("S3"));
        //lineSensor = new EV3ColorSensor(ev3.getPort("S4"));
        
        //initiate the display
        display = ev3.getGraphicsLCD();
    }
    
    //measuring the distance with ultra sonic sensor
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
    
    // measuring the color of the objects.
    // 8 different colors, represented by 0,1,...,7
    // in order: (NONE, BLACK, BLUE, GREEN, YELLOW, RED, WHITE, BROWN)
    public void ColorMeasurement() {
        SampleProvider ColorIDSensor = colorSensor.getColorIDMode();
        int sampleSize = ColorIDSensor.sampleSize();
        float[] sample = new float[sampleSize];
        
        while (!Button.ESCAPE.isDown()) {
            ColorIDSensor.fetchSample(sample, 0);
            String sampleString = "value " + ": " + sample[0];
            display.drawString(sampleString, 0, 0, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
            display.drawString("Escape to exit!", 0, 40, GraphicsLCD.VCENTER | GraphicsLCD.LEFT);
            Delay.msDelay(1000);
            display.clear();
        }
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
    
    public void OpenClaw() {
        motorClaw.setSpeed(400);
        motorClaw.rotate(800);
    }
    
    public void CloseClaw() {
        motorClaw.setSpeed(400);
        motorClaw.rotate(-800);
    }
    
    // all opened ports need to be closed!
    public void ClosePorts() {
        motorB.close();
        motorC.close();
        motorClaw.close();
        ultraSonic.close();
        colorSensor.close();
        //lineSensor.close();
    }
    
    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
        
        SensorTest test = new SensorTest();
        //test.ForwardMotors(400);
        //Delay.msDelay(2000);
        //test.StopMotors();
        //test.TurnRight90();
        //test.TurnLeft90();
        //Delay.msDelay(2000);
        //test.StopMotors();
        //test.DistanceMeasurement();
        //test.OpenClaw();
        //test.CloseClaw();
        //test.ColorMeasurement();
        //test.LineMeasurement();
        //Delay.msDelay(4000);
        test.ClosePorts();
    }
}
