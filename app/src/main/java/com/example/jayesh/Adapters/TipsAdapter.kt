package com.example.jayesh.Adapters

import com.example.jayesh.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView


class TipsAdapter(var context: Context, var tips: ArrayList<String>) :
    BaseAdapter() {
    override fun getCount(): Int {
        return tips.size
    }

    override fun getItem(pos: Int): Any {
        return pos
    }

    override fun getItemId(pos: Int): Long {
        return pos.toLong()
    }

    override fun getView(pos: Int, view: View, parent: ViewGroup): View {
        val li = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val textView = view.findViewById<View>(R.id.text) as TextView
        textView.text = tips[pos]
        return view
    }
}
