import java.io.IOException;
import java.net.UnknownHostException;

import lejos.hardware.lcd.GraphicsLCD;
import lejos.utility.Delay;

import java.io.IOException;
import java.net.UnknownHostException;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;

public class Test {
	public static void main(String[] args) throws UnknownHostException, IOException {
		Brick ev3 = BrickFinder.getDefault();
		GraphicsLCD display = ev3.getGraphicsLCD();
		Communication c = new Communication();
		c.getBox(0);
		Delay.msDelay(3000);
		c.sendMeassure("BW");
		Delay.msDelay(3000);
		c.sendMeassure("DW");
		Delay.msDelay(3000);
		c.sendMeassure("BC");
		Delay.msDelay(3000);
		c.sendMeassure("DC");
		}
}