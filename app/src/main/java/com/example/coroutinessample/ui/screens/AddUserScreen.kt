package com.example.coroutinessample.ui.screens

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.coroutinessample.R
import com.example.coroutinessample.extensions.checkField
import com.example.coroutinessample.extensions.convertMediaUriToPath
import com.example.coroutinessample.extensions.hideKeyboard
import com.example.coroutinessample.extensions.load
import com.example.coroutinessample.ui.base.BaseFragment
import kotlinx.android.synthetic.main.screen_add_user.*
import java.io.File
import java.io.IOException


class AddUserScreen : BaseFragment(R.layout.screen_add_user) {
    private val REQUEST_GALLERY = 0
    private val REQUEST_CAMERA = 4

    private lateinit var currentPhotoPath: String
    override fun onViewCreatedAfter() {
        init()
        mainVM.clearInfoInUI.observe(this, clearObserver)
    }

    private fun init() {
        image.load(mainVM.imageUrl)
        addButton.setOnClickListener {
            addUser()
        }
        selectGallery.setOnClickListener {
            if (checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE))
                choosePicture()
            else requestPermission(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                REQUEST_GALLERY
            )
        }
        takeCamera.setOnClickListener {
            if (checkPermission(Manifest.permission.CAMERA) && checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                takePicture()
            else requestPermission(
                arrayOf(
                    Manifest.permission.CAMERA
                ),
                REQUEST_CAMERA
            )
        }

    }

    private val clearObserver = Observer<Boolean> {
        name.setText("")
        mainVM.imageUrl = ""
        image.load(R.drawable.ic_person_black_24dp)
    }

    private fun addUser() {
        if (name.checkField("required field")) {
            val userName = name.text.toString()
            mainVM.addData(userName, mainVM.imageUrl)
            mainVM.scrollPager()
        }
    }

    private fun requestPermission(array: Array<String>, requestCode: Int) {
        requestPermissions(
            array,
            requestCode
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            when (requestCode) {
                REQUEST_GALLERY -> choosePicture()
                REQUEST_CAMERA -> takePicture()

            }
        }
    }

    private fun choosePicture() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(
            Intent.createChooser(
                intent,
                "Choose Picture"
            ), REQUEST_GALLERY
        )
    }

    private fun takePicture() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            context?.packageManager?.let {
                takePictureIntent.resolveActivity(it)?.also {
                    val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        null
                    }
                    photoFile?.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                            context!!,
                            "com.example.android.fileprovider",
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, REQUEST_CAMERA)
                    }
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val storageDir: File? = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG__", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK) {
            mainVM.imageUrl = context?.convertMediaUriToPath(data?.data).toString()
            Glide.with(image).load(data?.data).error(R.drawable.ic_person_black_24dp)
                .apply(RequestOptions.circleCropTransform())
                .into(image)
        } else {
            if (requestCode == REQUEST_CAMERA) {
                mainVM.imageUrl = currentPhotoPath
                Glide.with(image).load(currentPhotoPath).error(R.drawable.ic_person_black_24dp)
                    .apply(RequestOptions.circleCropTransform())
                    .into(image)
//                data?.extras?.get("data")
            }
        }
    }
}