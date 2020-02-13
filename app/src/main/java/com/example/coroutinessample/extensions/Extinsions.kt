package com.example.coroutinessample.extensions

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.widget.EditText
import android.provider.MediaStore
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.room.util.CursorUtil.getColumnIndexOrThrow
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.coroutinessample.R


fun EditText.checkField(errorMsg: String): Boolean {
    return if (this.text.isEmpty()) {
        error = errorMsg
        false
    } else true
}

fun Context.convertMediaUriToPath(uri: Uri?): String {
    val proj = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = uri?.let { contentResolver.query(it, proj, null, null, null) }
    cursor?.moveToFirst()
    val column_index = cursor?.getColumnIndexOrThrow(proj[0])
    val path = cursor?.getString(column_index?:0)
    cursor?.close()
    return path?:""
}

fun ImageView.load(url:String){
    Glide.with(this).load(url).apply(RequestOptions.circleCropTransform()).error(R.drawable.ic_person_black_24dp).into(this)
}

fun ImageView.load( drawable:Int){
    Glide.with(this).load(drawable).apply(RequestOptions.circleCropTransform()).error(R.drawable.ic_person_black_24dp).into(this)
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

