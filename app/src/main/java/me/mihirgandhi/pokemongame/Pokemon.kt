package me.mihirgandhi.pokemongame

import android.location.Location

/**
 * Created by mihir on 28/5/18.
 */

class Pokemon {
    var location:Location? = null
    var name:String? = null
    var description:String? = null
    var iconId:Int? = null
    var catched:Boolean? = false

    constructor(
            name:String,
            description:String,
            latitude:Double,
            longitude:Double,
            iconId:Int
    ){
        this.name = name
        this.description = description
        this.location = Location(name)
        this.location!!.latitude = latitude
        this.location!!.longitude = longitude
        this.iconId = iconId
    }
}