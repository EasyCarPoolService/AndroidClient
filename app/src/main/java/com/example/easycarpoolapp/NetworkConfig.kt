package com.example.easycarpoolapp

class NetworkConfig private constructor(){
    companion object{

        private val ip = "192.168.45.200"
        private val socketURL = "http://192.168.45.200:8080/mobileServer/websocket"

        public fun getIP() = ip
        public fun getSocketURL() = socketURL

    }
}