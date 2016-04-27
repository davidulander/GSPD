#include "DHT.h"
#define DHTPIN 2     // what digital pin we're connected to
#define DHTTYPE DHT22   // DHT 22  (AM2302), AM2321

#include <Wire.h>
#include "Adafruit_SI1145.h"

DHT dht(DHTPIN, DHTTYPE);

Adafruit_SI1145 uv = Adafruit_SI1145();

void setup() {
  bjas bajs
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
    Serial.println("Failed to read from DHT sensor!");
    return;
  }
  Serial.println("===================");
  Serial.print("Temperature: ");
  Serial.print(temp);
  Serial.println(" *C ");



  //Light measurement
  ijokl,ö
