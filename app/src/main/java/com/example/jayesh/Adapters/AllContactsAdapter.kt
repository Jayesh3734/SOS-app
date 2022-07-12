package com.example.jayesh.Adapters


import com.example.jayesh.R
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jayesh.DBUtils.ContactDetails


class AllContactsAdapter(var ctx: Context, var allContactsArrayList: ArrayList<ContactDetails>) :
    RecyclerView.Adapter<AllContactsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllContactsHolder {
        val li = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView: View = li.inflate(R.layout.all_contact_sample, parent, false)
        return AllContactsHolder(itemView, ctx)
    }

    override fun onBindViewHolder(holder: AllContactsHolder, position: Int) {
        val contactDetails = allContactsArrayList[position]
        with(holder) {
            allContactsName.text = contactDetails.name
            allContactsNumber.text = contactDetails.number
        }
        holder.allContactsLetter.setText(contactDetails.getName().toString());
        if (position % 6 == 0) {
            holder.gd.setColor(Color.parseColor("#FF0000"))
        }
        if (position % 6 == 1) {
            holder.gd.setColor(Color.BLUE)
        }
        if (position % 6 == 2) {
            holder.gd.setColor(Color.parseColor("#008000"))
        }
        if (position % 6 == 3) {
            holder.gd.setColor(Color.parseColor("#FFA500"))
        }
        if (position % 6 == 4) {
            holder.gd.setColor(Color.parseColor("#800000"))
        }
        if (position % 6 == 5) {
            holder.gd.setColor(Color.parseColor("#191970"))
        }
    }

    override fun getItemCount(): Int {
        return allContactsArrayList.size
    }

    fun Updatedata(contacts: ArrayList<ContactDetails>) {
        allContactsArrayList = contacts
        notifyDataSetChanged()
    }
}

