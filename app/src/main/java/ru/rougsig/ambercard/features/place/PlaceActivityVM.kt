package ru.rougsig.ambercard.features.place

import android.databinding.Bindable
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.stfalcon.androidmvvmhelper.mvvm.activities.ActivityViewModel
import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.common.models.Place
import ru.rougsig.ambercard.features.place.data.PlaceRepository

/**
 * Created by rougs on 11-Oct-17.
 */

class PlaceActivityVM(activity: PlaceActivity) : ActivityViewModel<PlaceActivity>(activity) {

    val place = ObservableField<Place>()
    val isLoaded = ObservableBoolean(false)

    override fun onResume() {
        super.onResume()
        PlaceRepository.get { onPlaceLoaded(it) }
    }

    private fun onPlaceLoaded(place: Place) {
        this.place.set(place)
        isLoaded.set(true)
    }
}