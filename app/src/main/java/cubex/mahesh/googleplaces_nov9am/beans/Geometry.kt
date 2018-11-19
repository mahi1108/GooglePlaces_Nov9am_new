package cubex.mahesh.googleplaces_nov9am.beans

import com.google.gson.annotations.SerializedName

data class Geometry(@SerializedName("location")
                    val location: Location)