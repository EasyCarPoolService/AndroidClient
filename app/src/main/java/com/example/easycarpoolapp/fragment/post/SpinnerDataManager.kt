package com.example.easycarpoolapp.fragment.post

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class SpinnerDataManager (val context : Context){
    private lateinit var districtArr : ArrayList<Array<String>>
    init {
        readTXT()   // 최초 csv파일 로드
    }

    private fun readTXT(){

        districtArr = ArrayList()
        val inputStream : InputStream = context.assets.open("district.txt")
        val inputStreamReader : InputStreamReader = InputStreamReader(inputStream)
        val bufferedReader : BufferedReader = BufferedReader(inputStreamReader)

        var line : String? =null
        var splitToken = ","

        while (bufferedReader.readLine().also { line = it } != null) {
            val row: Array<String> = line!!.split(splitToken).toTypedArray()
            districtArr.add(row)
        }
        for(row in districtArr){
            Log.e("row", row[0].toString())
        }


    }//readTXT



    public fun findSiDatas(): List<String> {
        val siDatas = ArrayList<String>()
        for(district in districtArr){
            siDatas.add(district[0])
        }

        return siDatas.distinct()
    }//findSiDatas


    public fun findGuDatas(selectedSi:String): List<String> {
        val guDatas = ArrayList<String>()
        for(district in districtArr){
            if(district[0].equals(selectedSi)){
                guDatas.add(district[1])
            }
        }
        return guDatas.distinct()
    }//findGu

    public fun findDongDatas(selected_si:String, selected_gu:String): List<String> {
        val dongDatas = ArrayList<String>()
        for(district in districtArr){
            if(district[0].equals(selected_si) && district[1].equals(selected_gu)){
                dongDatas.add(district[2])
            }
        }
        return dongDatas.distinct()
    }//findDong

}