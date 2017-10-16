package ru.rougsig.ambercard.features.place

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.view.View
import com.stfalcon.androidmvvmhelper.mvvm.activities.ActivityViewModel
import ru.rougsig.ambercard.features.place.data.PlaceModel

/**
 * Created by rougs on 11-Oct-17.
 */

class PlaceActivityVM(activity: PlaceActivity) : ActivityViewModel<PlaceActivity>(activity) {

    val place = ObservableField<PlaceModel>()
    val inLoading = ObservableBoolean(false)

    override fun onResume() {
        super.onResume()
    }

    private fun onPlaceLoaded(place: PlaceModel) {
        this.place.set(place)
        inLoading.set(true)
    }

    fun onClickBack(view: View) = activity.finish()
}