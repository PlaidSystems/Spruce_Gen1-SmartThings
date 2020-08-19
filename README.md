# Spruce_Gen1-Samsung_SmartThings
Devices and SmartApps for new Samsung SmartThings app

# These Device Handlers and Schedule will work with the new Samsung SmartThings APP
# This is a preliminary release!!

The migration to the newest Samsing app has caused the Gen1 Spruce Controller device tile to break.
The Spruce Scheduler is also no longer visible for new installs in the app.

Importing this code into the SmartThings IDE will bring back functionality as listed below:
- Each zone will be populated as a seperate device
- Individual zones can be turned on and off
- Schedules can be setup and will run automatically or can be started manually
- Individual zones can be used in automations

Known issues:
- Toggling a zone on or off does not refresh correctly, and shows "spinning/thinking" icon, however the zone does activate and toggling into the zone tile or out of the zone tile will "refresh" the state
- Apple iOS zone tile does not show "on", however inside the zone device screen will show "on" and "off" correctly

# Experienced User Setup

This is the short version for those familiar with adding custom device and smartapps to SmartThings

1. The device code must be copied/imported into your IDE account and published
2. The smartapp code must be copied/imported into your IDE account and published
3. Remove the Spruce Controller from the app
4. Add a new device with **Add Device** -> **Scan nearby** and then start the search on the Spruce controller
5. The app will find **Spruce Controller**
6. In the new Spruce Controller device, go to the preferences, select all Zones that you would like to use
7. Each zone will populate a new device in the app
8. If you have an exisiting Spruce Schedule installed from the Classic app, you may continue to use it. See section below for **Schedule Setup**

# Schedule Setup

You may continue to use your schedule carried over from the Classic app.  Or you may install a new schedule.

To use your existing schedule:
1. Open the schedule from the **SmartApps** menu in the new app
2. Go to **Schedule settings**
3. Set **Spruce Irrigation Controller** to the new Spruce Controller device.  **Do not select a zone device, the schedule will not run**
4. Press Next and Done to save.

To ceate a new schedule:
1. Go to **SmartApps**->**+** menu in the new app
2. Scroll down until you find **Spruce Scheduler SST**
3. Proceed to setup the same as the Classic app version.
