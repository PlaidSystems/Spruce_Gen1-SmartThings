
/**
 *  Copyright 2020 PlaidSystems
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 
 Copy of SST except no "remote" type from Aeon
 Also added Water Sensor Capability
 
 
 */
metadata {
	definition (name: "Spruce Controller SST", namespace: "plaidsystems", author: "plaidsystems") {
		capability "Switch"
        capability "Actuator"
        //capability "Switch Level"
        capability "Water Sensor"
        capability "Sensor"
        
        capability "Configuration"
        capability "Refresh"        
        capability "Health Check"
		                
        
        command "on"
        command "off"        
        command "childon"
        command "childoff"
        
        command "wet"
        command "dry"
        
        command "zon"
        command "zoff"
        command 'programOn'
        command 'programOff'
        command 'programWait'
        command 'programEnd'
        
        command "config"
        command "refresh"        
        command "rain"
        command "manual"
        command "manualTime"
        command "settingsMap"
        command "writeTime"
        command "writeType"        
        command "notify"
        command "updated"

		//new release
        fingerprint endpointId: "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18", profileId: "0104", deviceId: "0002", deviceVersion: "00", inClusters: "0000,0003,0004,0005,0006,000F", outClusters: "0003, 0019", manufacturer: "PLAID SYSTEMS", model: "PS-SPRZ16-01", deviceJoinName: "Spruce Controller"
        fingerprint endpointId: "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18", profileId: "0104", deviceId: "0002", deviceVersion: "00", inClusters: "0000,0003,0004,0005,0006,0009,000A,000F", outClusters: "0003, 0019", manufacturer: "PLAID SYSTEMS", model: "PS-SPRZ16-01", deviceJoinName: "Spruce Controller"
		fingerprint endpointId: "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18", profileId: "0104", deviceId: "0002", deviceVersion: "00", inClusters: "0000,0003,0004,0005,0006,0009,000A,000F", outClusters: "0003, 0006, 0019", manufacturer: "PLAID SYSTEMS", model: "PS-SPRWIFI16-01", deviceJoinName: "Spruce Controller WiFi"																																																																		   
		
	}

	preferences {
        input description: "If you have a rain sensor wired to the rain sensor input on the Spruce controller, turn it on here.", displayDuringSetup: true, type: "paragraph", element: "paragraph", title: "Rain Sensor"
        input "RainEnable", "bool", title: "Rain Sensor Attached?", required: false, displayDuringSetup: true
        input description: "Enable your zones below, turning on Display zones will show each zone independently in the main screen.", displayDuringSetup: true, type: "paragraph", element: "paragraph", title: "Enabled Zones"
        //input name: "zonedisplay", type: "bool", title: "Display zones independently", displayDuringSetup: true
        input name: "z1", type: "bool", title: "Enable Zone 1", displayDuringSetup: true
        input name: "z2", type: "bool", title: "Enable Zone 2", displayDuringSetup: true
        input name: "z3", type: "bool", title: "Enable Zone 3", displayDuringSetup: true
        input name: "z4", type: "bool", title: "Enable Zone 4", displayDuringSetup: true
        input name: "z5", type: "bool", title: "Enable Zone 5", displayDuringSetup: true
        input name: "z6", type: "bool", title: "Enable Zone 6", displayDuringSetup: true
        input name: "z7", type: "bool", title: "Enable Zone 7", displayDuringSetup: true
        input name: "z8", type: "bool", title: "Enable Zone 8", displayDuringSetup: true
        input name: "z9", type: "bool", title: "Enable Zone 9", displayDuringSetup: true
        input name: "z10", type: "bool", title: "Enable Zone 10", displayDuringSetup: true
        input name: "z11", type: "bool", title: "Enable Zone 11", displayDuringSetup: true
        input name: "z12", type: "bool", title: "Enable Zone 12", displayDuringSetup: true
        input name: "z13", type: "bool", title: "Enable Zone 13", displayDuringSetup: true
        input name: "z14", type: "bool", title: "Enable Zone 14", displayDuringSetup: true
        input name: "z15", type: "bool", title: "Enable Zone 15", displayDuringSetup: true
        input name: "z16", type: "bool", title: "Enable Zone 16", displayDuringSetup: true 
    }
	tiles(scale: 2) {        
		standardTile("switch", "device.switch", width: 2, height: 2) {		
            state "off", label: "off", action: "on"
            state "on", label: "on", action: "off"
        }
        standardTile("water", "device.water", width: 2, height: 2) {
			state "dry", icon:"st.alarm.water.dry", backgroundColor:"#ffffff", action: "wet"
			state "wet", icon:"st.alarm.water.wet", backgroundColor:"#00A0DC", action: "dry"
		}
        standardTile("wet", "device.water", inactiveLabel: false, decoration: "flat") {
			state "default", label:'Wet', action:"wet", icon: "st.alarm.water.wet"
		}         
		standardTile("dry", "device.water", inactiveLabel: false, decoration: "flat") {
			state "default", label:'Dry', action:"dry", icon: "st.alarm.water.dry"
		}
        childDeviceTiles("switches")
        main "switch"
        details(["switch", "water"])
	}
}

//----------------------zigbee parse-------------------------------//

// Parse incoming device messages to generate events
def parse(String description) {	
	//log.debug "Parse description ${description}"
    def result = null
    def map = zigbee.parseDescriptionAsMap(description)
    //log.debug map
    
    def endpoint = ( map.sourceEndpoint == null ? hextoint(map.endpoint) : hextoint(map.sourceEndpoint) )
    def value = ( map.sourceEndpoint == null ? hextoint(map.value) : null )    
    def command = (value != null ? commandType(endpoint, map.clusterInt) : null)
    
    if (command != null) log.debug "${command} endpoint ${endpoint} value ${value} cluster ${map.clusterInt}"
    switch(command) {
      case "alarm":
        log.debug "alarm"
        //result = createEvent(name: "switch", value: "off", descriptionText: "Alarm on zone ${endpoint - 1}", isStateChange: true, displayed: true)
        break
      case "program":
      	log.debug "program"
        def onoff = (value == 1 ? "on" : "off")        
        result = createEvent(name: "switch", value: onoff)        
        break
      case "zone":      
      	def onoff = (value == 1 ? "on" : "off")
        def child = childDevices.find{it.deviceNetworkId == "${device.deviceNetworkId}.${endpoint}"}
        if(child) child.sendEvent(name: "switch", value: onoff, isStateChange: true, displayed: true)//.parse(onoff)
        break
      case "rainSensor":
      	def wetdry = (value == 1 ? "wet" : "dry")
        if (!RainEnable) wetdry = "dry"
        result = createEvent(name: "water", value: wetdry)
        //def child = childDevices.find{it.deviceNetworkId == "${device.deviceNetworkId}.18"}       
        //if(child) child.sendEvent(name: "water", value: wetdry, isStateChange: true, displayed: true)
        break
      case "refresh":
        log.debug "refresh"
        def child = childDevices.find{it.deviceNetworkId == "${device.deviceNetworkId}.19"}       
        if(child) child.sendEvent(name: "switch", value: "off", isStateChange: true, displayed: true)
        break
      default:
      	//log.debug "null"
        break
    }
    
	//log.debug "result: ${result}"
	return result
}


//--------------------end zigbee parse-------------------------------//

def installed() {	
	if (!childDevices) {
		createChildDevices()
        response(refresh() + configure())
	}
}

def updated() {
	log.debug "updated"
    removeChildDevices()
    createChildDevices()
    response(rain())
}



private void createChildDevices() {	
    log.debug "create children"
    /*
    //RainSensor
    def child = addChildDevice("Spruce Rain Sensor", "${device.deviceNetworkId}.18", device.hubId,
				[completedSetup: true, label: "Rain Sensor",
				 isComponent: true, componentName: "rainSensor", componentLabel: "Rain Sensor"])
                 log.debug "${child}"
    	child.sendEvent(name: "water", value: "dry", displayed: false)
    */
    //Refresh
    def child = addChildDevice("Spruce zone", "${device.deviceNetworkId}.19", device.hubId,
				[completedSetup: true, label: "Refresh",
				 isComponent: true, componentName: "Refresh", componentLabel: "Refresh"])
                 log.debug "${child}"
    	child.sendEvent(name: "switch", value: "off", displayed: false)
    
    //Schedule
    /*
    child = addChildDevice("Spruce zone", "${device.deviceNetworkId}.1", device.hubId,
				[completedSetup: true, label: "Schedule",
				 isComponent: true, componentName: "Schedule", componentLabel: "Schedule"])
                 log.debug "${child}"
    	child.sendEvent(name: "switch", value: "off", displayed: false)
    */
    //zones
    for (i in 1..16){
        if(settings."${"z${i}"}"){
        	def dni = i + 1            
            child = addChildDevice("Spruce zone", "${device.deviceNetworkId}.${dni}", device.hubId,
                    [completedSetup: true, label: "Spruce Zone${i}",
                     isComponent: false, componentName: "Zone${i}", componentLabel: "${device.displayName} ${i}"])
                     log.debug "${child}"
            	child.sendEvent(name: "switch", value: "off", displayed: false)
        }
    }
}

private removeChildDevices() {
	log.debug "remove children"
	
    //get and delete children avoids duplicate children
    def children = getChildDevices()    
    if(children != null){
        children.each{
            deleteChildDevice(it.deviceNetworkId)
        }
    }       
}


//----------------------------------commands--------------------------------------//
//used for schedule
def notify(String val, String txt){
	log.debug "notify ${val} ${txt}"    
}

def programOn(){
	log.debug "programOn"
    //sendEvent(name: "switch", value: "programOn", descriptionText: "Program turned on")
    //if (device.latestValue("pause") != "closed") endpause()
    //sendEvent(name: "program", value: "programOn", descriptionText: "Program turned on", displayed: false) 
}

def programWait(){
	log.debug "programWait"
    //sendEvent(name: "program", value: "programWait", descriptionText: "Initializing Schedule")
}

def programEnd(){
	log.debug "programEnd"
	//sets switch to off and tells schedule switch is off/schedule complete with manaual
    //sendEvent(name: "program", value: "off", descriptionText: "Program manually turned off")
    //off() 
}
    
def programOff(){
	log.debug "programEnd"
    //sendEvent(name: "program", value: "off", descriptionText: "Program turned off", displayed: false)
    off()
}

def start(){
	log.debug "start"
    if (device.latestValue("pause") != "closed") endpause()
    on()
}

//new pause function
def pause(){
	log.debug "pause"
    //sendEvent(name: "pause", value: "open", descriptionText: "Paused", displayed: true)
	def pauseCmds = []
    pauseCmds.push("st wattr 0x${device.deviceNetworkId} 1 6 0x4002 0x21 {0000}")
    //send 0 time and off-> signal pause event
	return pauseCmds + "st cmd 0x${device.deviceNetworkId} 1 6 0 {}"
}

def endpause(){	
	log.debug "endpause"
    sendEvent(name: "pause", value: "closed", descriptionText: "Pause end", displayed: true)	
    //on()
}


//on & off redefined for Alexa to start manual schedule
def on() {    
    log.debug "Alexa on"    
    //schedule subscribes to programOn
    sendEvent(name: "switch", value: "on", descriptionText: "${device.displayName} on")
    sendEvent(name: "switch", value: "programOn", descriptionText: "Schedule on")           
}
def off() {
	log.debug "Alexa off"
    sendEvent(name: "switch", value: "off", descriptionText: "Alexa turned program off")
    zoff()        
}

// Commands to device
//program on/off
def zon() {
	"st cmd 0x${device.deviceNetworkId} 1 6 1 {}"
}
def zoff() {
	"st cmd 0x${device.deviceNetworkId} 1 6 0 {}" 
}

// Commands to children
def manual(){    
    def newManaul = 10    
    //if (device.latestValue("minutes")) newManaul = device.latestValue("minutes").toInteger()    
    log.debug "Manual Zone runtime ${newManaul} mins"    
    def manualTime = hex(newManaul)  
    
    def sendCmds = []
    sendCmds.push("st wattr 0x${device.deviceNetworkId} 1 6 0x4002 0x21 {00${manualTime}}")
    return sendCmds
}

def childOn(dni) {	
    def DNI = dni.replaceFirst("${device.deviceNetworkId}.","").toInteger()
    log.debug "child ${DNI} on"
    
    def command = commandType(DNI, 6)
    
    switch(command) {
      case "program":
        zoneon(DNI)
        break
      case "zone":
        zoneon(DNI)
        break
      case "rainSensor":
        log.debug "rainSensor"
        break
      case "refresh":
        refresh()
        break
    }
    
}

def childOff(dni) {
	def DNI = dni.replaceFirst("${device.deviceNetworkId}.","").toInteger()
    log.debug "child ${DNI} off"
    
    def command = commandType(DNI, 6)
    
    switch(command) {
      case "program":
        zoneoff(DNI)
        break
      case "zone":
        zoneoff(DNI)
        break
      case "rainSensor":
        log.debug "rainSensor"
        break
      case "refresh":
        log.debug "refresh"
        def child = childDevices.find{it.deviceNetworkId == "${device.deviceNetworkId}.19"}       
    	if(child) child.sendEvent(name: "switch", value: "off", isStateChange: true, displayed: true)
        break
    }
}

def commandType(DNI, cluster){
	if (cluster == 9) return "alarm"
    //else if (cluster == 15 && DNI == 18) return "refresh"
	else if (DNI == 1) return "program"
    else if (DNI in 2..17) return "zone"
    else if (DNI == 18) return "rainSensor"
    else if (DNI == 19) return "refresh"
}

def zoneon(DNI) {	
    return manual() + zigbee.command(6, 1, "", [destEndpoint:DNI])    
}

def zoneoff(DNI) {    
    return zigbee.command(6, 0, "", [destEndpoint:DNI])
}

//------------------end commands----------------------------------//

//write switch time settings map
def settingsMap(WriteTimes, attrType){
	log.debug WriteTimes    
	
    def i = 1
    def runTime
    def sendCmds = []
    while(i <= 17){
    	  
    	if (WriteTimes."${i}"){        	
        	runTime = hex(Integer.parseInt(WriteTimes."${i}"))
        	log.debug "${i} : $runTime"
		
        	if (attrType == 4001) sendCmds.push("st wattr 0x${device.deviceNetworkId} ${i} 0x06 0x4001 0x21 {00${runTime}}")
        	else sendCmds.push("st wattr 0x${device.deviceNetworkId} ${i} 0x06 0x4002 0x21 {00${runTime}}")
            sendCmds.push("delay 600")
        }
        i++
    }    
    return sendCmds
}

//send switch time
def writeType(wEP, cycle){
	log.debug "wt ${wEP} ${cycle}"
    "st wattr 0x${device.deviceNetworkId} ${wEP} 0x06 0x4001 0x21 {00" + hex(cycle) + "}"
    }
//send switch off time
def writeTime(wEP, runTime){    
    "st wattr 0x${device.deviceNetworkId} ${wEP} 0x06 0x4002 0x21 {00" + hex(runTime) + "}"
    }

//set reporting and binding
def configure() {
	// Device-Watch allows 2 check-in misses from device, checks every 2 hours
    sendEvent(name: "DeviceWatch-DeviceStatus", value: "online")
	sendEvent(name: "healthStatus", value: "online")
	sendEvent(name: "DeviceWatch-Enroll", value: 2* 60 * 60, displayed: false, data: [protocol: "zigbee", hubHardwareId: device.hub.hardwareID])
	    
    config()    
}

def config(){

	String zigbeeId = swapEndianHex(device.hub.zigbeeId)
	log.debug "Configuring Reporting and Bindings ${device.deviceNetworkId} ${device.zigbeeId}"
    
    def configCmds = [	
        //program on/off        
        "zdo bind 0x${device.deviceNetworkId} 1 1 6 {${device.zigbeeId}} {}", "delay 1000",
        "zdo bind 0x${device.deviceNetworkId} 1 1 0x09 {${device.zigbeeId}} {}", "delay 1000",        
        "zdo bind 0x${device.deviceNetworkId} 1 1 0x0F {${device.zigbeeId}} {}", "delay 1000",
        //zones 1-8
        "zdo bind 0x${device.deviceNetworkId} 2 1 0x0F {${device.zigbeeId}} {}", "delay 1000",
        "zdo bind 0x${device.deviceNetworkId} 3 1 0x0F {${device.zigbeeId}} {}", "delay 1000",
		"zdo bind 0x${device.deviceNetworkId} 4 1 0x0F {${device.zigbeeId}} {}", "delay 1000",
        "zdo bind 0x${device.deviceNetworkId} 5 1 0x0F {${device.zigbeeId}} {}", "delay 1000",
        "zdo bind 0x${device.deviceNetworkId} 6 1 0x0F {${device.zigbeeId}} {}", "delay 1000",
        "zdo bind 0x${device.deviceNetworkId} 7 1 0x0F {${device.zigbeeId}} {}", "delay 1000",
        "zdo bind 0x${device.deviceNetworkId} 8 1 0x0F {${device.zigbeeId}} {}", "delay 1000",        
        "zdo bind 0x${device.deviceNetworkId} 9 1 0x0F {${device.zigbeeId}} {}", "delay 1000",
        //zones 9-16
        "zdo bind 0x${device.deviceNetworkId} 10 1 0x0F {${device.zigbeeId}} {}", "delay 1000",
        "zdo bind 0x${device.deviceNetworkId} 11 1 0x0F {${device.zigbeeId}} {}", "delay 1000",
		"zdo bind 0x${device.deviceNetworkId} 12 1 0x0F {${device.zigbeeId}} {}", "delay 1000",
        "zdo bind 0x${device.deviceNetworkId} 13 1 0x0F {${device.zigbeeId}} {}", "delay 1000",
        "zdo bind 0x${device.deviceNetworkId} 14 1 0x0F {${device.zigbeeId}} {}", "delay 1000",
        "zdo bind 0x${device.deviceNetworkId} 15 1 0x0F {${device.zigbeeId}} {}", "delay 1000",
        "zdo bind 0x${device.deviceNetworkId} 16 1 0x0F {${device.zigbeeId}} {}", "delay 1000",        
        "zdo bind 0x${device.deviceNetworkId} 17 1 0x0F {${device.zigbeeId}} {}", "delay 1000",
        //rain sensor
        "zdo bind 0x${device.deviceNetworkId} 18 1 0x0F {${device.zigbeeId}} {}",
        
        "zcl global send-me-a-report 6 0 0x10 1 0 {01}", "delay 500",
        "send 0x${device.deviceNetworkId} 1 1", "delay 500",
        
        "zcl global send-me-a-report 0x0F 0x55 0x10 1 0 {01}", "delay 500",
        "send 0x${device.deviceNetworkId} 1 1", "delay 500",
       
        "zcl global send-me-a-report 0x0F 0x55 0x10 1 0 {01}", "delay 500",
        "send 0x${device.deviceNetworkId} 1 2", "delay 500",
        
        "zcl global send-me-a-report 0x0F 0x55 0x10 1 0 {01}", "delay 500",
        "send 0x${device.deviceNetworkId} 1 3", "delay 500",
        
        "zcl global send-me-a-report 0x0F 0x55 0x10 1 0 {01}", "delay 500",
        "send 0x${device.deviceNetworkId} 1 4", "delay 500",
        
        "zcl global send-me-a-report 0x0F 0x55 0x10 1 0 {01}", "delay 500",
        "send 0x${device.deviceNetworkId} 1 5", "delay 500",
        
        "zcl global send-me-a-report 0x0F 0x55 0x10 1 0 {01}", "delay 500",
        "send 0x${device.deviceNetworkId} 1 6", "delay 500",
        
        "zcl global send-me-a-report 0x0F 0x55 0x10 1 0 {01}", "delay 500",
        "send 0x${device.deviceNetworkId} 1 7", "delay 500",
        
        "zcl global send-me-a-report 0x0F 0x55 0x10 1 0 {01}", "delay 500",
        "send 0x${device.deviceNetworkId} 1 8", "delay 500",      
        
        
        "zcl global send-me-a-report 0x0F 0x55 0x10 1 0 {01}", "delay 500",
        "send 0x${device.deviceNetworkId} 1 9", "delay 500",
       
        "zcl global send-me-a-report 0x0F 0x55 0x10 1 0 {01}", "delay 500",
        "send 0x${device.deviceNetworkId} 1 10", "delay 500",
        
        "zcl global send-me-a-report 0x0F 0x55 0x10 1 0 {01}", "delay 500",
        "send 0x${device.deviceNetworkId} 1 11", "delay 500",
        
        "zcl global send-me-a-report 0x0F 0x55 0x10 1 0 {01}", "delay 500",
        "send 0x${device.deviceNetworkId} 1 12", "delay 500",
        
        "zcl global send-me-a-report 0x0F 0x55 0x10 1 0 {01}", "delay 500",
        "send 0x${device.deviceNetworkId} 1 13", "delay 500",
        
        "zcl global send-me-a-report 0x0F 0x55 0x10 1 0 {01}", "delay 500",
        "send 0x${device.deviceNetworkId} 1 14", "delay 500",
        
        "zcl global send-me-a-report 0x0F 0x55 0x10 1 0 {01}", "delay 500",
        "send 0x${device.deviceNetworkId} 1 15", "delay 500",
        
        "zcl global send-me-a-report 0x0F 0x55 0x10 1 0 {01}", "delay 500",
        "send 0x${device.deviceNetworkId} 1 16", "delay 500",
        
        "zcl global send-me-a-report 0x0F 0x55 0x10 1 0 {01}", "delay 500",
        "send 0x${device.deviceNetworkId} 1 17", "delay 500",
        
        "zcl global send-me-a-report 0x0F 0x55 0x10 1 0 {01}", "delay 500",
        "send 0x${device.deviceNetworkId} 1 18", "delay 500",
        
        "zcl global send-me-a-report 0x09 0x00 0x21 1 0 {00}", "delay 500",
        "send 0x${device.deviceNetworkId} 1 1", "delay 500"
	]
    return configCmds + rain()
}

//PING is used by Device-Watch in attempt to reach the Device
def ping() {
	log.debug "device health ping"    
    return zigbee.onOffRefresh()
}

def rain() {
    log.debug "Rain sensor: ${RainEnable}"
    //if (RainEnable) sendEvent(name: "rainsensor", value: "enable", descriptionText: "${device.displayName} rain sensor is enabled", isStateChange: true)
    //else sendEvent(name: "rainsensor", value: "disable", descriptionText: "${device.displayName} rain sensor is disabled", isStateChange: true)
    
    if (RainEnable) "st wattr 0x${device.deviceNetworkId} 18 0x0F 0x51 0x10 {01}"
    else "st wattr 0x${device.deviceNetworkId} 18 0x0F 0x51 0x10 {00}"
    /*
    def childDevice = childDevices.find{it.deviceNetworkId == "${device.deviceNetworkId}.18"}        
    if(childDevice) {
        childDevice.sendEvent(name: "water", value: "dry", displayed: false)
    }
    */
}

def refresh() {

	log.debug "refresh pressed"
    def child = childDevices.find{it.deviceNetworkId == "${device.deviceNetworkId}.19"}       
    if(child) child.sendEvent(name: "switch", value: "on", isStateChange: true, displayed: true)
        
    def refreshCmds = [	    
        
        "st rattr 0x${device.deviceNetworkId} 1 0x0F 0x55", "delay 500",
        
        "st rattr 0x${device.deviceNetworkId} 2 0x0F 0x55", "delay 500",
        "st rattr 0x${device.deviceNetworkId} 3 0x0F 0x55", "delay 500",
        "st rattr 0x${device.deviceNetworkId} 4 0x0F 0x55", "delay 500",
        "st rattr 0x${device.deviceNetworkId} 5 0x0F 0x55", "delay 500",
        "st rattr 0x${device.deviceNetworkId} 6 0x0F 0x55", "delay 500",        
        "st rattr 0x${device.deviceNetworkId} 7 0x0F 0x55", "delay 500",
        "st rattr 0x${device.deviceNetworkId} 8 0x0F 0x55", "delay 500",        
        "st rattr 0x${device.deviceNetworkId} 9 0x0F 0x55", "delay 500",
        
        "st rattr 0x${device.deviceNetworkId} 10 0x0F 0x55", "delay 500",
        "st rattr 0x${device.deviceNetworkId} 11 0x0F 0x55", "delay 500",
        "st rattr 0x${device.deviceNetworkId} 12 0x0F 0x55", "delay 500",
        "st rattr 0x${device.deviceNetworkId} 13 0x0F 0x55", "delay 500",
        "st rattr 0x${device.deviceNetworkId} 14 0x0F 0x55", "delay 500",        
        "st rattr 0x${device.deviceNetworkId} 15 0x0F 0x55", "delay 500",
        "st rattr 0x${device.deviceNetworkId} 16 0x0F 0x55", "delay 500",
        "st rattr 0x${device.deviceNetworkId} 17 0x0F 0x55", "delay 500",
        
        "st rattr 0x${device.deviceNetworkId} 18 0x0F 0x51","delay 500",
 	
    ]
    
    return refreshCmds
}

//parse hex string and make integer
private hextoint(String hex) {
	Long.parseLong(hex, 16).toInteger()
}

private hex(value) {
	new BigInteger(Math.round(value).toString()).toString(16)
}

private String swapEndianHex(String hex) {
    reverseArray(hex.decodeHex()).encodeHex()
}

private byte[] reverseArray(byte[] array) {
    int i = 0;
    int j = array.length - 1;
    byte tmp;
    while (j > i) {
        tmp = array[j];
        array[j] = array[i];
        array[i] = tmp;
        j--;
        i++;
    }
    return array
}