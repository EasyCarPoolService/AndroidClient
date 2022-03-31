package com.example.easycarpoolapp

class NetworkConfig private constructor(){
    companion object{
        private val ip = "192.168.0.8"
        private val socketURL = "http://192.168.0.8:8080/mobileServer/websocket"

        public fun getIP() = ip
        public fun getSocketURL() = socketURL

    }//companion object
}

