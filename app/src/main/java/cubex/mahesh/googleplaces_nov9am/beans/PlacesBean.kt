package cubex.mahesh.googleplaces_nov9am.beans

import com.google.gson.annotations.SerializedName

data class PlacesBean(@SerializedName("results")
                      val results: MutableList<ResultsItem>?)