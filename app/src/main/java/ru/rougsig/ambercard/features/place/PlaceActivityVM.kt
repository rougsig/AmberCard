package ru.rougsig.ambercard.features.place

import android.content.Context
import android.databinding.ObservableField
import android.graphics.Color
import android.support.v4.content.ContextCompat
import com.stfalcon.androidmvvmhelper.mvvm.activities.ActivityViewModel
import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.common.models.Place
import ru.rougsig.ambercard.features.place.data.PlaceRepository
import ru.rougsig.ambercard.utils.Loader

/**
 * Created by rougs on 11-Oct-17.
 */

class PlaceActivityVM(activity: PlaceActivity) : ActivityViewModel<PlaceActivity>(activity) {

    val place = ObservableField<Place>()

    override fun onResume() {
        super.onResume()
        PlaceRepository.getPlace(object: Loader<Place>{
            override fun onLoaded(item: Place) = onPlaceLoaded(item)
        })
    }

    fun onPlaceLoaded(place: Place) {
        this.place.set(place)
    }
}