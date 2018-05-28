package me.mihirgandhi.pokemongame

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.lang.Exception
import kotlin.concurrent.thread

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    val LOACTION_PERMISSION_REQUESTCODE = 123
    var myLocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        checkPermission()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

       /* // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions()
                .position(sydney)
                .title("You")
                .snippet("This is your locatiom")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mario)))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16f))*/
    }

    fun checkPermission() {


        if (Build.VERSION.SDK_INT >= 23) {

            if (ActivityCompat
                            .checkSelfPermission(
                                    this,
                                    android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                        LOACTION_PERMISSION_REQUESTCODE)
                return
            }

        }
        getMyPosition()

    }

    fun getMyPosition() {

        Toast.makeText(this,"User location access on",Toast.LENGTH_LONG).show()
        //TODO: Will implement later

        var myLocation= MyLocationListener()

        var locationManager=getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3,3f,myLocation)


        var myLocationUpdate = UpdateLocationThread()
        myLocationUpdate.start()


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        when (requestCode) {
            LOACTION_PERMISSION_REQUESTCODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getMyPosition()
                } else {
                    Toast.makeText(this, "we cannot access your locaction", Toast.LENGTH_LONG).show()
                }
            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    inner class MyLocationListener:LocationListener {

        constructor(){
            myLocation = Location("Start")
            myLocation!!.latitude = 0.0
            myLocation!!.longitude = 0.0
        }

        override fun onLocationChanged(p0: Location?) {
            myLocation = p0
        }

        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onProviderEnabled(p0: String?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onProviderDisabled(p0: String?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    inner class UpdateLocationThread:Thread{

        constructor():super(){

        }

        override fun run() {

            while (true){
                try {

                    runOnUiThread {
                        mMap!!.clear()
                        // Add a marker on player and move the camera
                        val playerLocation = LatLng(myLocation!!.latitude, myLocation!!.longitude)
                        mMap!!.addMarker(MarkerOptions()
                                .position(playerLocation)
                                .title("You")
                                .snippet("This is your locatiom")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mario)))
                        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(playerLocation, 14f))

                    }
                    Thread.sleep(1000)
                }catch (ex:Exception){

                }
            }


        }
    }
}
