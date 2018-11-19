package cubex.mahesh.googleplaces_nov9am

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import com.google.android.gms.location.places.ui.PlacePicker
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sBar.setOnSeekBarChangeListener(
            object:SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    tview.text = p1.toString()
                }
                override fun onStartTrackingTouch(p0: SeekBar?) {
                }
                override fun onStopTrackingTouch(p0: SeekBar?) {
                }
            })

            loc_pin.setOnClickListener {

                var placePicker = PlacePicker.IntentBuilder()
                startActivityForResult(
           placePicker.build(this@MainActivity),123)
            }


            getLocation( )
    }   //onCreate( )

    override fun onActivityResult(requestCode: Int,
                                  resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==123 && resultCode== Activity.RESULT_OK)
        {
            var place = PlacePicker.getPlace(
                this@MainActivity,data)
            lati.text = place.latLng.latitude.toString()
            longi.text = place.latLng.longitude.toString()
        }
    }


    @SuppressLint("MissingPermission")
    fun getLocation( )
    {

            var lManager = getSystemService(
                Context.LOCATION_SERVICE) as LocationManager
            lManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        1000.toLong(),
                        1.toFloat(),
                        object:LocationListener{
                            override fun onLocationChanged(l: Location?) {
                                        if(l!=null) {
                                            var lati_value = l.latitude
                                            var longi_value = l.longitude

                                            lati.text = lati_value.toString()
                                            longi.text = longi_value.toString()
                                        }

                            }

                            override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
                            }

                            override fun onProviderEnabled(p0: String?) {
                            }

                            override fun onProviderDisabled(p0: String?) {
                            }

                        }
            )

    }
}
