package com.example.jayesh.Adapters


import android.app.AlertDialog
import com.example.jayesh.R
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jayesh.DBUtils.DBHelper
import com.example.jayesh.Fragments.ContactsFragment


class AllContactsHolder(itemView: View, ctx: Context?) :
    RecyclerView.ViewHolder(itemView) {
    var allContactsLetter: TextView
    var allContactsName: TextView
    var allContactsNumber: TextView
    var circle: ImageView
    var contactColor: View
    var view: View
    var mydb: DBHelper
    var gd: GradientDrawable

    init {
        allContactsName = itemView.findViewById<View>(R.id.allContactsName) as TextView
        allContactsNumber = itemView.findViewById<View>(R.id.allContactsNumber) as TextView
        allContactsLetter = itemView.findViewById<View>(R.id.contactLetter) as TextView
        contactColor = itemView.findViewById(R.id.contactColor)
        circle = itemView.findViewById<View>(R.id.circle) as ImageView
        gd = circle.background as GradientDrawable
        view = itemView
        mydb = DBHelper(ctx)
        view.setOnLongClickListener {
            val alertDialog: AlertDialog.Builder = AlertDialog.Builder(ctx)
            alertDialog.setTitle("Remove")
            alertDialog.setMessage("Are you sure you want remove this contact?")
            alertDialog.setPositiveButton("YES",
                DialogInterface.OnClickListener { dialog, which ->
                    mydb.deleteData(allContactsNumber.text.toString().trim { it <= ' ' })
//                    ContactsFragment.getOnDelete()?.onDel()
                })
            alertDialog.setNegativeButton("NO",
                DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
            alertDialog.show()
            true
        }
    }
}
