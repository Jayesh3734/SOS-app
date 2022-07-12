package com.example.jayesh.Activities

import com.example.jayesh.R
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.jayesh.Fragments.ContactsFragment
import com.example.jayesh.Fragments.PoliceFragment
import com.example.jayesh.Fragments.ReportFragment
import com.example.jayesh.Fragments.TipsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    var bottomNavigationView: BottomNavigationView? = null
    var fragManager: FragmentManager? = null
    var fragTxn: FragmentTransaction? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragManager = supportFragmentManager
        val contactsFragment = ContactsFragment(this)
        fragTxn = fragManager!!.beginTransaction()
        fragTxn!!.replace(R.id.fragFrame, contactsFragment)
        fragTxn!!.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragTxn!!.commit()
        bottomNavigationView = findViewById<View>(R.id.bottom_navigation) as BottomNavigationView
        bottomNavigationView!!.setOnNavigationItemSelectedListener { item ->
            if (item.itemId == R.id.contacts) {
                fragManager = supportFragmentManager
                fragTxn = fragManager!!.beginTransaction()
                fragTxn!!.replace(R.id.fragFrame, contactsFragment)
                fragTxn!!.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                fragTxn!!.commit()
            }
            if (item.itemId == R.id.police) {
                fragManager = supportFragmentManager
                val policeFragment = PoliceFragment(this@MainActivity)
                fragTxn = fragManager!!.beginTransaction()
                fragTxn!!.replace(R.id.fragFrame, policeFragment)
                fragTxn!!.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                fragTxn!!.commit()
            }
            if (item.itemId == R.id.tips) {
                fragManager = supportFragmentManager
                val tipsFragment = TipsFragment(this@MainActivity)
                fragTxn = fragManager!!.beginTransaction()
                fragTxn!!.replace(R.id.fragFrame, tipsFragment)
                fragTxn!!.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                fragTxn!!.commit()
            }
            if (item.itemId == R.id.reportabuse) {
                fragManager = supportFragmentManager
                val reportFragment = ReportFragment(this@MainActivity)
                fragTxn = fragManager!!.beginTransaction()
                fragTxn!!.replace(R.id.fragFrame, reportFragment)
                fragTxn!!.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                fragTxn!!.commit()
            }
            true
        }
    }
}