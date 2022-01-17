package com.example.easycarpoolapp

class NetworkConfig private constructor(){
    companion object{
        private val ip = "192.168.0.5"

        public fun getIP() : String{
            return ip
        }
    }
}