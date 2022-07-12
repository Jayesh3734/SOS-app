package com.example.jayesh.Fragments

import com.example.jayesh.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.example.jayesh.Adapters.TipsAdapter


class TipsFragment(var ctx: Context) : Fragment() {
    var tips: ArrayList<String>? = null
    var listView: ListView? = null
    var tipsAdapter: TipsAdapter? = null
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tips = ArrayList()
        tips!!.add("The first, and probably most important, component in self-defense is awareness.")
        tips!!.add("Learn to trust your sixth sense and use it to your full advantage.")
        tips!!.add("Take Self Defense Training.")
        tips!!.add("Escape is always the best  option.")
        tips!!.add("It is important to understand that you CAN and SHOULD defend yourself physically.")
        tips!!.add("Never depend on any self-defense tool or weapon to stop an attacker.")
        tips!!.add("Never, ever open your door unless you either are certain you know who’s on the other side.")
        tips!!.add("Be prepared when you Travel.")
        tipsAdapter = TipsAdapter(ctx, tips!!)
    }

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.fragment_tips, container, false)
        listView = rootView.findViewById<View>(R.id.lvTips) as ListView
        listView!!.adapter = tipsAdapter
        return rootView
    }
}
