package com.bchoi.signalstrengthspike.entities

data class ConnectionStatus(val name: String, val signalLevel: Int, val bssid: String, val frequency: Int, val dbm: Int)
