package com.example.coroutinessample.extensions

import android.content.Context
import android.net.Uri
import android.widget.EditText
import android.provider.MediaStore
import androidx.room.util.CursorUtil.getColumnIndexOrThrow



fun EditText.checkField(errorMsg: String): Boolean {
    return if (this.text.isEmpty()) {
        error = errorMsg
        false
    } else true
}

fun Context.convertMediaUriToPath(uri: Uri?): String {
    val proj = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = uri?.let { contentResolver.query(it, proj, null, null, null) }
    val column_index = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
    cursor?.moveToFirst()
    val path = cursor?.getString(column_index?:0)
    cursor?.close()
    return path?:""
}