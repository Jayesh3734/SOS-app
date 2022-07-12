package com.example.jayesh.Adapters

import com.example.jayesh.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jayesh.Models.Police


class PoliceAdapter(var context: Context, policeArrayList: ArrayList<Police>) :
    RecyclerView.Adapter<PoliceAdapter.ListItemHolder>() {
    var policeArrayList: ArrayList<Police>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemHolder {
        val li = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = li.inflate(R.layout.list_item_police, parent, false)
        return ListItemHolder(view)
    }

    override fun onBindViewHolder(holder: ListItemHolder, position: Int) {
        val item: Police = policeArrayList[position]
        holder.pname.text = item.name
        holder.address.text = item.address
        holder.distance.text = item.distance
    }

    override fun getItemCount(): Int {
        return policeArrayList.size
    }

    inner class ListItemHolder(var mainView: View) : RecyclerView.ViewHolder(
        mainView
    ) {
        var pname: TextView = itemView.findViewById<View>(R.id.stationName) as TextView
        var address: TextView = itemView.findViewById<View>(R.id.address) as TextView
        var distance: TextView = itemView.findViewById<View>(R.id.distance) as TextView

    }

    init {
        this.policeArrayList = policeArrayList
    }
}
