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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.SeekBar
import com.google.android.gms.location.places.Places
import com.google.android.gms.location.places.ui.PlacePicker
import cubex.mahesh.googleplaces_nov9am.beans.PlacesBean
import cubex.mahesh.googleplaces_nov9am.beans.ResultsItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.indiview.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
var places_list: MutableList<ResultsItem>? = null
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

            getPlaces.setOnClickListener {

                var r = Retrofit.Builder().
                    addConverterFactory(GsonConverterFactory.create()).
                    baseUrl("https://maps.googleapis.com/").
                    build()
              var api =   r.create(PlacesAPI::class.java)
              var call = api.getPlaces(
                  "${lati.text.toString()},${longi.text.toString()}",
                  tview.text.toString(),
                  sp1.selectedItem.toString())
                call.enqueue(object:Callback<PlacesBean>{
                    override fun onFailure(call: Call<PlacesBean>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<PlacesBean>, response: Response<PlacesBean>) {
                    var pbean =   response.body()
                     places_list = pbean!!.results
                     var myAdapter = object : BaseAdapter() {
                         override fun getView(pos: Int, p1: View?, p2: ViewGroup?): View {
                       var inflater = LayoutInflater.from(this@MainActivity)
                       var v = inflater.inflate(R.layout.indiview,null)
                             v.name.text = places_list!!.get(pos).name
                             v.vicinity.text = places_list!!.get(pos).vicinity
                             v.rating.text = places_list!!.get(pos).rating.toString()
                             v.loc.setOnClickListener {
                                    var i = Intent(this@MainActivity,
                                                        MapsActivity::class.java)
                                    i.putExtra("lati",places_list!!.get(pos).
                                                                geometry.location.lat)
                                    i.putExtra("longi",places_list!!.get(pos).
                                                                geometry.location.lng)
                                    i.putExtra("our_lati",
                                        lati.text.toString().toDouble())
                                 i.putExtra("our_longi",
                                     longi.text.toString().toDouble())
                                    i.putExtra("from_single",true)
                                    startActivity(i)
                             }
                             return v
                         }
                         override fun getItem(p0: Int): Any = 0
                         override fun getItemId(p0: Int): Long =0
                         override fun getCount(): Int = places_list!!.size
                     }    // BaseAdapter
                    lview.adapter = myAdapter
                    }

                })

            }

        showAllOnMap.setOnClickListener {
            var i = Intent(this@MainActivity,
                MapsActivity::class.java)
            i.putExtra("from_single",false)
            i.putExtra("our_lati",
                lati.text.toString().toDouble())
            i.putExtra("our_longi",
                longi.text.toString().toDouble())
            startActivity(i)

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
