#include "DHT.h"
#define DHTPIN 2     // what digital pin we're connected to
#define DHTTYPE DHT22   // DHT 22  (AM2302), AM2321

#include <Wire.h>
#include "Adafruit_SI1145.h"


#include <SoftwareSerial.h>
SoftwareSerial bluetooth(19, 18); // RX, TX



DHT dht(DHTPIN, DHTTYPE);

Adafruit_SI1145 uv = Adafruit_SI1145();

int x=0;

void setup() {
  // set the data rate for the SoftwareSerial port
  bluetooth.begin(9600);
  bluetooth.println("Temp and Brightness test!");
  bluetooth.println();

  dht.begin();
  
  if (! uv.begin()) {
    bluetooth.println("Didn't find Si1145");
    while (1);
  }
}

void loop() {
  //Temp Measurement 
  //Wait a few seconds between measurements.
  delay(2000);

  // Reading temperature or humidity takes about 250 milliseconds!
  // Sensor readings may also be up to 2 seconds 'old' (its a very slow sensor)
  // Read temperature as Celsius (the default)
  float temp = dht.readTemperature();

  // Check if any reads failed and exit early (to try again).
  if (isnan(temp)) {
    bluetooth.println("Failed to read from DHT sensor!");
    return;
  }
  bluetooth.println("===================");
  bluetooth.print("Temperature: ");
  bluetooth.print(temp);
  bluetooth.println(" *C ");



  //Light measurement
  bluetooth.println("===================");
  bluetooth.print("Vis: "); bluetooth.println(uv.readVisible());
  bluetooth.print("IR: "); bluetooth.println(uv.readIR());
  
  float UVindex = uv.readUV();
  // the index is multiplied by 100 so to get the
  // integer index, divide by 100!
  UVindex /= 100.0;  
  bluetooth.print("UV: ");  bluetooth.println(UVindex);

   
 }
