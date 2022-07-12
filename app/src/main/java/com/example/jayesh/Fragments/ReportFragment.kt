package com.example.jayesh.Fragments

import com.example.jayesh.R
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.createChooser
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class ReportFragment(var ctx: Context) : Fragment() {
    var bytearrayoutputstream: ByteArrayOutputStream? = null
    var file: File? = null
    var fileoutputstream: FileOutputStream? = null
    var ivCamera: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.fragment_report, container, false)
        ivCamera = rootView.findViewById<View>(R.id.iv_Camera) as ImageView
        ivCamera!!.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            var imageUri = Uri.fromFile(
                File(
                    Environment.getExternalStorageDirectory(),
                    "fname_" + System.currentTimeMillis().toString() + ".jpg"
                )
            )
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, file)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file))
            startActivityForResult(
                cameraIntent,
                CAMERA_REQUEST
            )
        }
        return rootView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            val photo = data?.extras!!["data"] as Bitmap?
//            image.setImageBitmap(photo)
            photo!!.compress(Bitmap.CompressFormat.PNG, 60, bytearrayoutputstream)
            file = File(Environment.getExternalStorageDirectory().toString() + "/SampleImage.png")
            try {
                fileoutputstream = FileOutputStream(file)
                fileoutputstream!!.write(bytearrayoutputstream!!.toByteArray())
                fileoutputstream!!.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            Toast.makeText(getContext(), "Image Saved Successfully", Toast.LENGTH_LONG).show()
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.type = "application/image"
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("jayeshkumavat42@gmail.com"))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "ALERT")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Its a worst Situation")
            emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
            emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///mnt/sdcard/Myimage.jpeg"))
        }
    }

    companion object {
        private const val CAMERA_REQUEST = 1888
    }
}
