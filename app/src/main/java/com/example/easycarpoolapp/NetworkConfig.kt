package com.example.easycarpoolapp

class NetworkConfig private constructor(){
    companion object{

        private val ip = "172.20.10.4"
        private val socketURL = "http://172.20.10.4:8080/mobileServer/websocket"

        public fun getIP() = ip
        public fun getSocketURL() = socketURL

    }//companion object
}