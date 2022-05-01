# Exploring - Sensors
1. Collect sensor data along with the timestamp for the following sensors and
store them in a room database in a separate table for each sensor. Have UI
control in terms of toggle buttons to start and stop collection. \
a. Motion sensor - Gyroscope, Linear acceleration \
b. Environmental sensor - Temperature, Light \
c. Positional sensor - Proximity, Orientation \
##### Note: Make sure you are collecting data only when the toggle button is ON and application is running in foreground and not when the toggle button is OFF or application is running in background/stopped. Incase your device does not have a particular sensor, pick another one from the same category.
2. Two charts [Line Chart], one for displaying the last 10 values
of proximity sensor, and other to display the last 10 average - values of
Linear acceleration (x line, y line and z line).
3. Display a toast to inform if a sensor is stopped or started.
4. Detect whether a device is stationary or not using data from the
Accelerometer sensor and display it on a textview.
5. Switch between day and night mode of your device based upon the Light
sensor data.
6. Create a location based discovery platform which will have two
functionalities "Add a Place" and "Find my places'’ respectively. In "Add a
Place" you have to get your current location and add a new place in the
database with basic details like Name, Latitude, Longitude and address. In
"Find my places" you have to retrieve 3 nearest places from the database
based on your current location and show the details of the places.
7. Start/Stop collecting sensor data when you wave over your mobile
phone.
8. Start collecting sensor data when the phone screen is placed
upwards and stop collecting data when the screen is placed downwards on
a surface.

## Screenshots:
<p>
  <img src="https://github.com/saurabh21077/ExploringSensorsApplication/blob/assignment-4/Screenshot_1.png" width="250" title="SS1">
  <img src="https://github.com/saurabh21077/ExploringSensorsApplication/blob/assignment-4/Screenshot_2.png" width="250" title="SS2">
  <img src="https://github.com/saurabh21077/ExploringSensorsApplication/blob/assignment-4/Screenshot_3.png" width="250" title="SS3">
  
</p>
