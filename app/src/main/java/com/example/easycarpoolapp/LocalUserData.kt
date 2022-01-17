package com.example.easycarpoolapp


//singleton object
class LocalUserData private constructor(){

    companion object{
        private var email : String? =null
        private var nickname : String? = null
        private var token : String? = null
        private var gender : String? = null
        private var instance: LocalUserData? = null

        //singleton객체 생성은 SplashActivity가 실행될때 반드시 수행해야함 -> 이후의 모든서비스에서 static방식으로 메서드를 사용
        public fun createInstance() {
            if(instance == null){
                instance = LocalUserData()
            }
        }

        public fun login(_token : String?, _email:String?, _nickname : String?, _gender : String?){
            token = _token
            email = _email
            nickname = _nickname
            gender = _gender
        }

        public fun logout(){
            token = null
            email = null
            nickname = null
            gender = null
        }

        public fun getEmail() : String? = email
        public fun getNickname() : String? = nickname
        public fun getToken() : String? = token
        public fun getGender() : String? = gender

    }
}