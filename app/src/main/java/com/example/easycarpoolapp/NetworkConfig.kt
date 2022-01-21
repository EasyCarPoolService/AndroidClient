package com.example.easycarpoolapp

class NetworkConfig private constructor(){
    companion object{
        private val ip = "172.30.1.28"

        public fun getIP() : String{
            return ip
        }
    }
}