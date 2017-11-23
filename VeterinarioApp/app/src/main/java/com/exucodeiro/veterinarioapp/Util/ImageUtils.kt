package com.exucodeiro.veterinarioapp.Util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream

class ImageUtils {

    companion object {

        fun ImageView.loadUrl(url: String?) {
            if (url != null && url != "")
                Picasso.with(context).load(url).into(this)
        }

        fun ImageView.loadUrl(url: Int) {
            Picasso.with(context).load(url).into(this)
        }

        fun getImageUri(context: Context, inImage: Bitmap): Uri {
            val bytes = ByteArrayOutputStream()
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val path = MediaStore.Images.Media.insertImage(context.contentResolver, inImage, "Title", null)
            return Uri.parse(path)
        }

        fun getRealPathFromURI(context: Context, contentURI: Uri): String {
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

}