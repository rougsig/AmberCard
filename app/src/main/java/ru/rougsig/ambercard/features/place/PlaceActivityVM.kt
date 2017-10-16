package ru.rougsig.ambercard.features.place

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.view.View
import com.stfalcon.androidmvvmhelper.mvvm.activities.ActivityViewModel
import ru.rougsig.ambercard.features.place.data.PlaceModel
import ru.rougsig.ambercard.features.place.data.PlaceRepository

/**
 * Created by rougs on 11-Oct-17.
 */

class PlaceActivityVM(activity: PlaceActivity) : ActivityViewModel<PlaceActivity>(activity) {

    val place = ObservableField<PlaceModel>()
    val isLoaded = ObservableBoolean(false)

    override fun onResume() {
        super.onResume()
    }

    private fun onPlaceLoaded(place: PlaceModel) {
        this.place.set(place)
        isLoaded.set(true)
    }

    fun onClickBack(view: View) = getActivity().finish()
}