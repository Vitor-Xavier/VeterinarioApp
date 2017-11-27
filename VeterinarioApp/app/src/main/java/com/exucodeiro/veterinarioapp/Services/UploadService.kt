package com.exucodeiro.veterinarioapp.Services

import android.content.Context
import android.net.Uri
import com.exucodeiro.veterinarioapp.Util.ImageUtils
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpPost
import java.io.File
import java.io.FileInputStream

class UploadService {

    init {
        FuelManager.instance.basePath = "http://veterinario-app.azurewebsites.net/"
        FuelManager.instance.baseHeaders = mapOf("Accept-Charset" to "UTF-8")
        FuelManager.instance.baseHeaders = mapOf("Connection" to "Keep-Alive")
        FuelManager.instance.baseHeaders = mapOf("Cache-Control" to "no-cache")
        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "multipart/form-data")
    }

    fun enviarImagem(context: Context, path: String, filename: String) : String? {
        val file = File(ImageUtils.getRealPathFromURI(context, Uri.parse(path)))
        val bytesArray = ByteArray(file.length().toInt())

        val fis = FileInputStream(file)
        fis.read(bytesArray) //read file into bytes[]
        fis.close()

        val (_, _, result) = "Upload/$filename".httpPost().body(bytesArray).responseString()
        val (data, error) = result

        val mapper = jacksonObjectMapper()
        val res = mapper.readValue<String>(data ?: "")

        return if (error == null) res else null
    }

}