package cubex.mahesh.googleplaces_nov9am

import cubex.mahesh.googleplaces_nov9am.beans.PlacesBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlacesAPI {

    @GET("maps/api/place/nearbysearch/json?key=AIzaSyDdCGdR2cnWw0AB0LeN3jOTjKmkKag4tew")
    fun getPlaces(@Query("location") l:String,
                 @Query("radius") r:String,
                 @Query("type")  t:String) :Call<PlacesBean>
}