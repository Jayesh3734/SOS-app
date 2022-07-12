package com.example.jayesh.Activities

import com.example.jayesh.R
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


public class EmergencyActivity : AppCompatActivity() {
    var tvName: TextView? = null
    var tvmsg:TextView? = null
    var btnClose: Button? = null
    var mp: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency)
        val msg = intent.getStringExtra("msg")
        tvmsg = findViewById<View>(R.id.tv_msg) as TextView
        tvmsg!!.text = msg
        btnClose = findViewById<View>(R.id.btn_Close) as Button
        mp = MediaPlayer.create(this, R.raw.siren)
        mp!!.start()
        btnClose!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                mp!!.stop()
                finish()
            }
        })
    }
}

