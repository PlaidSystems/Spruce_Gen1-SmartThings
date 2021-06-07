# Spruce_Gen1-SmartThings
Gen1 Device and SmartApp for new Samsung SmartThings app

# These Device Handlers and Schedule will work with the new Samsung SmartThings APP

The migration to the newest Samsing app has caused the Gen1 Spruce Controller device tile to break.
The Spruce Scheduler is also no longer visible for new installs in the app.

### v3 Latest 5-18-2021
- MAJOR change to device handler
- Update Spruce Controller to ST published device, but with minor update to facilitate scheduler
- Update Spruce Scheduler to work with updated device

Importing this code into the SmartThings IDE will bring back functionality as listed below:
- The new Spruce Controller device looks and works like the version listed under the brand "Spruce Irrigation"- 
- Schedules can be setup using the classic Spruce Scheduler
- Individual zones can be used in automations

# Import the Device Handlers and SmartApp into your account

Follow the instructions from the following link to import the Device Handlers and Smartapp into your SmartThings account. The instructions and video are from https://konnected.io, **Spruce is in no way affiliated with Konnected**
- https://help.konnected.io/support/solutions/articles/32000022409-smartthings-enable-github-integration#
- Video: https://help.konnected.io/support/solutions/articles/32000022474-video-konnected-smartthings-installation-and-setup

You will add the GitHub Repository:
- Owner: PlaidSystems
- Name: Spruce_Gen1-SmartThings
- Branch: Master

Once complete, **Update from Repo**

# Updating Device and SmartApp code

The steps outlined in the links above use Github to “pull” the code into your account. Your SmartThings account will keep track of changes in the code, but will need to be told to “update”. This is done by repeating the Update from Repo button in the Device Handler and SmartApps pages of the SmartThings IDE at graph.api.smartthings.com/

You may **Update from Repo** anytime to get the latest code.

# Setup

1. The device handler code **Spruce Controller SST** must be copied/imported into your IDE account and published following the instructions above.
2. The smartapp code **Spruce Scheduler SST** must be copied/imported into your IDE account and published following the instructions above.
3. Remove the Spruce Controller from the app, from the Spruce Device, go to **Edit** -> **Delete Device**
4. Add a new device with **Add Device** -> **Scan nearby** and then start the search on the Spruce controller
5. The app will find **Spruce Controller**
6. Device setup instructions are now the same as the SmartThings released version and covered here: https://gen1.support.spruceirrigation.com/knowledge-base/smartthings2020-spruce-controller-gen1/

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
