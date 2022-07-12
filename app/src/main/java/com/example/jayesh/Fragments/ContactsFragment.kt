package com.example.jayesh.Fragments

import com.example.jayesh.R
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.speech.RecognizerIntent
import android.telephony.SmsManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jayesh.Adapters.AllContactsAdapter
import com.example.jayesh.DBUtils.ContactDetails
import com.example.jayesh.DBUtils.DBHelper
import com.example.jayesh.Interfaces.OnDelete
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*


class ContactsFragment(var ctx: Context) : Fragment() {
    var allContactsRv: RecyclerView? = null
    var allContactsAdapter: AllContactsAdapter? = null
    var mydbHelper: DBHelper? = null
    private val REQ_CODE_SPEECH_INPUT = 100
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mydbHelper = DBHelper(ctx)
        allContactsArrayList = ArrayList()
        allContactsArrayList!!.clear()
        val res = mydbHelper!!.allData
        while (res.moveToNext()) {
            Log.d(TAG, "onClick: " + res.getString(1))
            Log.d(TAG, "onClick: " + res.getString(2))
            Log.d(TAG, "onClick: " + "-----------------------")
            allContactsArrayList!!.add(ContactDetails(res.getString(1), res.getString(2), null))
        }
        allContactsAdapter = AllContactsAdapter(
            ctx,
            allContactsArrayList!!
        )
    }

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.fragment_contacts, container, false)
        val fabMessgae = rootView.findViewById<View>(R.id.fab_SendMessage) as FloatingActionButton
        fabMessgae.setOnClickListener { promptSpeechInput() }
        val fab = rootView.findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
            startActivityForResult(intent, PICK_CONTACT)
        }
        allContactsRv = rootView.findViewById(R.id.rvList)
//        allContactsRv.setLayoutManager(LinearLayoutManager(ctx))
//        allContactsRv.setAdapter(allContactsAdapter)
        onDelete = object : OnDelete {
            override fun onDel() {
                displayContacts(rootView)
            }
        }
        return rootView
    }

    private fun promptSpeechInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(
            RecognizerIntent.EXTRA_PROMPT,
            "Say something"
        )
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT)
        } catch (a: ActivityNotFoundException) {
            Toast.makeText(
                ctx,
                "Sorry! Your device doesn\\'t support speech input",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resultCode, data)
        when (reqCode) {
            PICK_CONTACT -> if (resultCode == Activity.RESULT_OK) {
                val contactData = data!!.data
                val cr: ContentResolver = getContext().getContentResolver()
                val c = cr.query(contactData!!, null, null, null, null)
                if (c!!.moveToFirst()) {
                    var cNumber: String? = null
                    val id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
                    val hasPhone =
                        c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                    if (hasPhone.equals("1", ignoreCase = true)) {
                        val phones = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                            null, null
                        )
                        phones!!.moveToFirst()
                        cNumber = phones.getString(phones.getColumnIndex("data1"))
                        Log.d(
                            TAG,
                            "onActivityResult: $cNumber"
                        )
                    }
                    val name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    Log.d(TAG, "onActivityResult: $name")
                    val x = mydbHelper!!.insertData(name, cNumber, null)
                    allContactsArrayList!!.add(
                        ContactDetails(
                            name,
                            cNumber!!, null
                        )
                    )
                    allContactsAdapter!!.notifyDataSetChanged()
                    if (!x) {
                        Toast.makeText(ctx, "Contact Not Available", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            REQ_CODE_SPEECH_INPUT -> {
                if (resultCode == Activity.RESULT_OK && null != data) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
//                    Toast.makeText(this, result!![0], Toast.LENGTH_SHORT).show()
                    var mailMessage = result!![0]
                    sendMessage("9898989898", result[0])
                    sendMessage("1010101010", result[0].toString() + " JK ")
                    sendMessage("1101101101", result[0].toString() + " JK ")
                }
            }
        }
    }

    fun sendMessage(phno: String?, msg: String): Boolean {
        return try {
            if (phno == null) {
                false
            } else {
                val smsmanager = SmsManager.getDefault()
                smsmanager.sendTextMessage(
                    phno, null,
                    "$msg\nI am at Mumbai, Maharashtra, India", null, null
                )
                Toast.makeText(ctx, "Message Sent", Toast.LENGTH_SHORT).show()
                true
            }
        } catch (e: Exception) {
            Toast.makeText(ctx, "MessgAct Ecxp", Toast.LENGTH_SHORT).show()
            false
        }
    }

    fun displayContacts(rootView: View) {
        allContactsArrayList!!.clear()
        val res = mydbHelper!!.allData
        while (res.moveToNext()) {
            Log.d(TAG, "onClick: " + res.getString(1))
            Log.d(TAG, "onClick: " + res.getString(2))
            Log.d(TAG, "onClick: " + "-----------------------")
            allContactsArrayList!!.add(ContactDetails(res.getString(1), res.getString(2), null))
        }
        allContactsRv = rootView.findViewById<View>(R.id.rvList) as RecyclerView
        allContactsAdapter = AllContactsAdapter(
            ctx,
            allContactsArrayList!!
        )
        allContactsRv!!.layoutManager = LinearLayoutManager(ctx)
        allContactsRv!!.adapter = allContactsAdapter
        if (res.count == 0) {
            Toast.makeText(ctx, "No Numbers Added", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        var onDelete: OnDelete? = null
        var allContactsArrayList: ArrayList<ContactDetails>? = null
        const val TAG = "Yolo"
        const val PICK_CONTACT = 1001
        const val REQ_CODE = 1991
    }
}
