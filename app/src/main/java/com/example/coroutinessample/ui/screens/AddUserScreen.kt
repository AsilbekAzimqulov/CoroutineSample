package com.example.coroutinessample.ui.screens

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.coroutinessample.R
import com.example.coroutinessample.extensions.checkField
import com.example.coroutinessample.extensions.convertMediaUriToPath
import com.example.coroutinessample.ui.base.BaseFragment
import kotlinx.android.synthetic.main.screen_add_user.*


class AddUserScreen : BaseFragment(R.layout.screen_add_user) {
    private val REQUEST_GALLERY = 0
    private val REQUEST_CAMERA = 0
    private var imageUrl = ""
    override fun onViewCreatedAfter() {
        addButton.setOnClickListener {
            addUser()
        }
        selectGallery.setOnClickListener {
            if (checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE))
                choosePicture()
            else requestPermission()
        }
        takeCamera.setOnClickListener {

        }
        mainVM.clearInfoInUI.observe(this, clearObserver)
    }

    private val clearObserver = Observer<Boolean> {
        name.setText("")
        imageUrl = ""
    }

    private fun addUser() {
        if (name.checkField("required field")) {
            val userName = name.text.toString()
            mainVM.addData(userName, imageUrl)
            mainVM.scrollPager()
        }

    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            activity!!,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
            1
        )
    }

    private fun checkPermission(request: String): Boolean {
        context?.let {
            return ContextCompat.checkSelfPermission(
                it,
                request
            ) == PackageManager.PERMISSION_GRANTED
        }
        return false
    }

    private fun choosePicture() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(
                intent,
                "Choose Picture"
            ), REQUEST_CAMERA
        )
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK) {
            imageUrl = context?.convertMediaUriToPath(data?.data).toString()
            Glide.with(image).load(data?.data).error(R.drawable.ic_person_black_24dp)
                .apply(RequestOptions.circleCropTransform())
                .into(image)
        } else {
            if (requestCode == REQUEST_CAMERA) {

            }
        }
    }
}