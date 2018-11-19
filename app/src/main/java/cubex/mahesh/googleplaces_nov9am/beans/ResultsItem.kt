package cubex.mahesh.googleplaces_nov9am.beans

import com.google.gson.annotations.SerializedName

data class ResultsItem(@SerializedName("name")
                       val name: String = "",
                       @SerializedName("rating")
                       val rating: Double = 0.0,
                       @SerializedName("geometry")
                       val geometry: Geometry,
                       @SerializedName("vicinity")
                       val vicinity: String = "")