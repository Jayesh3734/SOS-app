package com.example.jayesh.Models

import com.google.android.gms.maps.model.LatLng


class Police(
    var name: String,
    var address: String,
    var placeId: String,
    var latLng: LatLng,
    var distance: String
)