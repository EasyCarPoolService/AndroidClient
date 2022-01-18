package com.example.easycarpoolapp

class NetworkConfig private constructor(){
    companion object{
        private val ip = "172.30.104.220"

        public fun getIP() : String{
            return ip
        }
    }
}