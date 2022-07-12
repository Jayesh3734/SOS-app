package com.example.jayesh.Fragments

import android.annotation.SuppressLint
import com.example.jayesh.R
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.jayesh.Models.Police
import com.example.jayesh.Adapters.PoliceAdapter
import com.google.android.gms.maps.model.LatLng
import org.json.JSONException


class PoliceFragment(var ctx: Context) : Fragment() {
    var police: ArrayList<Police>? = null
    var rvList: RecyclerView? = null
    var policeAdapter: PoliceAdapter? = null
    var progressDialog: ProgressDialog? = null
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        police = ArrayList<Police>()
        policeAdapter = PoliceAdapter(ctx, police!!)
        progressDialog = ProgressDialog(ctx)
        progressDialog!!.setMessage("Fetching your Location..")
        progressDialog!!.show()
        val progressRunnable = Runnable { progressDialog!!.cancel() }
        progressDialog!!.setOnCancelListener { }
        val handler = Handler()
        handler.postDelayed(progressRunnable, 1000)


//        Handler handler = new Handler();
//
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                progressDialog.show();
//            }
//        }, 1000);
        fetchStations()
    }

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.fragment_police, container, false)
        Log.d(TAG, "onCreateView: ")
        rvList = rootView.findViewById<View>(R.id.rvList) as RecyclerView
        rvList!!.layoutManager = LinearLayoutManager(ctx)
        rvList!!.addItemDecoration(DividerItemDecoration(ctx, LinearLayoutManager.VERTICAL))
        rvList!!.adapter = policeAdapter
        return rootView
    }

    @SuppressLint("NotifyDataSetChanged")
    fun fetchStations() {
        val jsonObjectRequest =
            JsonObjectRequest("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=28.6652,77.2324&radius=5000&type=police&key=AIzaSyBgEqbEuZ8LJdG7BmDXn3frx89AK1IVd0c",
                null,
                { response ->
                    try {
                        val jsonArray = response.getJSONArray("results")
                        for (i in 0 until jsonArray.length()) {
                            val loc = jsonArray.getJSONObject(i).getJSONObject("geometry")
                                .getJSONObject("location")
                            val name = jsonArray.getJSONObject(i).getString("name")
                            val address = jsonArray.getJSONObject(i).getString("vicinity")
                            val placeId = jsonArray.getJSONObject(i).getString("place_id")
                            val latlng = LatLng(loc.getDouble("lat"), loc.getDouble("lng"))
                            val dist = round(
                                distance(
                                    28.6652,
                                    77.2324,
                                    loc.getDouble("lat"),
                                    loc.getDouble("lng"),
                                    'K'
                                ), 2
                            ).toString() + " km away"
                            val item = Police(name, address, placeId, latlng, dist)
                            police!!.add(item)
                        }
                        policeAdapter!!.notifyDataSetChanged()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            ) { Toast.makeText(ctx, "Volley Error", Toast.LENGTH_SHORT).show() }
        val requestQueue = Volley.newRequestQueue(ctx)
        requestQueue.add(jsonObjectRequest)
    }

    companion object {
        const val TAG = "Police"
        fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double, unit: Char): Double {
            val theta = lon1 - lon2
            var dist =
                Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(
                    deg2rad(lat2)
                ) * Math.cos(deg2rad(theta))
            dist = Math.acos(dist)
            dist = rad2deg(dist)
            dist *= 60 * 1.1515
            if (unit == 'K') {
                dist *= 1.609344
            } else if (unit == 'N') {
                dist *= 0.8684
            }
            return dist
        }

        private fun deg2rad(deg: Double): Double {
            return deg * Math.PI / 180.0
        }

        private fun rad2deg(rad: Double): Double {
            return rad * 180 / Math.PI
        }

        fun round(value: Double, places: Int): Double {
            var vlu = value
            require(places >= 0)
            val factor = Math.pow(10.0, places.toDouble()).toLong()
            vlu *= factor
            val tmp = Math.round(vlu)
            return tmp.toDouble() / factor
        }
    }
}