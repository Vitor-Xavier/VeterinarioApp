package com.exucodeiro.veterinarioapp.Services

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelManager
import java.io.File
import java.io.FileInputStream

class UploadService {

    init {
        FuelManager.instance.basePath = "http://192.168.0.11:45455/"
        FuelManager.instance.baseHeaders = mapOf("Accept-Charset" to "UTF-8")
        FuelManager.instance.baseHeaders = mapOf("Connection" to "Keep-Alive")
        FuelManager.instance.baseHeaders = mapOf("Cache-Control" to "no-cache")
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "multipart/form-data")
    }

    fun enviarImagem(context: Context, path: String) : Boolean {
        val file = File(getRealPathFromURI(context, Uri.parse(path)))
        val bytesArray = ByteArray(file.length().toInt())

        val fis = FileInputStream(file)
        fis.read(bytesArray) //read file into bytes[]
        fis.close()
        try {
            var res = false
            Fuel.post("Upload").body(bytesArray)
                .response { request, response, result ->
                    val (data, error) = result
                    res = (error == null)
                }
            return res
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    private fun getRealPathFromURI(context: Context, contentURI: Uri): String {
        val result: String
        val cursor = context.contentResolver.query(contentURI, null, null, null, null)
        if (cursor == null) {
            result = contentURI.path
        } else {
            cursor!!.moveToFirst()
            val idx = cursor!!.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor!!.getString(idx)
            cursor!!.close()
        }
        return result
    }
}