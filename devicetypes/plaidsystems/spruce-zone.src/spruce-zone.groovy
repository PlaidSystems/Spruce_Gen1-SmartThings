/**
 *  Spruce Controller zone child *
 *  Copyright 2017 Plaid Systems
 *
 *	Author: NC
 *	Date: 2017-6
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
 -------------6-2017 update---------------
 * child for composite Spruce Controller
 * 
 */
 
 metadata {
	definition (name: "Spruce zone", namespace: "plaidsystems", author: "Plaid Systems") {
		capability "Actuator"
        capability "Switch"        
        capability "Health Check"
        
        command "on"
        command "off"        
	}    
    tiles {
        standardTile("switch", "device.switch", width: 2, height: 2) {		
            state "off", label: "off", action: "on"
            state "on", label: "on", action: "off"
        }        
        main "switch"
        details(["switch"])
    }
}

def installed() {
	log.trace "Executing installed"
	initialize()
}

def updated() {
	log.trace "Executing updated"
	initialize()
}

private initialize() {
	log.trace "Executing initialize"
    
	sendEvent(name: "switch", value: "off", descriptionText: "initialize off", displayed: false)
    sendEvent(name: "DeviceWatch-DeviceStatus", value: "online")
	sendEvent(name: "healthStatus", value: "online")
	sendEvent(name: "DeviceWatch-Enroll", value: [protocol: "cloud", scheme:"untracked"].encodeAsJson(), displayed: false)
}

def parse(String onoff) {
	log.debug "Child Desc: ${onoff}"
    sendEvent(name: "switch", value: onoff)    
}

def on(){
	log.debug "${device.deviceNetworkId} on"
    parent.childOn(device.deviceNetworkId)
}

def off(){
	log.debug "${device.deviceNetworkId} off"
    parent.childOff(device.deviceNetworkId)
}