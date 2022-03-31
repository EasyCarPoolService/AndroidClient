package com.example.easycarpoolapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*

class ImageFileManager(context : Context) {

    private val TAG : String = "ImageFileManager"
    private lateinit var BASEURL : String

    init{
        BASEURL = context.filesDir.toString()
    }

    //return 값은 생성된 파일의 경로
    public fun createImageFile(image : Bitmap) : File{

        //uuid+.jpg파일 형식으로 저장되는지 체크
        val targetPath = BASEURL+"/"+createUUID()
        var file : File = File(targetPath)

        var out : OutputStream? = null

        try{
            file.createNewFile()
            out = FileOutputStream(file)
            image.compress(Bitmap.CompressFormat.JPEG, 100, out)
        }catch (e : Exception){
            Log.e(TAG, e.message.toString())
        }finally {
            out?.close()
        }

        return File(targetPath)
    } //createImageFile


    private fun createUUID() : String = UUID.randomUUID().toString() + ".jpg"

}