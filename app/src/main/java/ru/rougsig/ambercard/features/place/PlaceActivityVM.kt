package ru.rougsig.ambercard.features.place

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.view.View
import android.widget.Toast
import com.stfalcon.androidmvvmhelper.mvvm.activities.ActivityViewModel
import ru.rougsig.ambercard.App.Companion.context
import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.features.place.data.PlaceModel
import ru.rougsig.ambercard.features.place.data.PlaceRepository

/**
 * Created by rougs on 11-Oct-17.
 */

class PlaceActivityVM(activity: PlaceActivity) : ActivityViewModel<PlaceActivity>(activity) {

    val place = ObservableField<PlaceModel>()
    val inLoading = ObservableBoolean(true)

    override fun onResume() {
        PlaceRepository.getPlaceById(
                activity.intent.extras.getInt(PlaceActivity.EXTRA_ID),
                this::onLoadedSuccess,
                this::onLoadedFailure,
                this::onLoadedUnauthorized
        )

        super.onResume()
    }

    private fun onLoadedSuccess(place: PlaceModel) {
        this.place.set(place)
        inLoading.set(false)
    }

    private fun onLoadedFailure() {
        inLoading.set(false)
        Toast.makeText(activity, context.getString(R.string.error_wtf), Toast.LENGTH_SHORT).show()
    }

    private fun onLoadedUnauthorized() {
        inLoading.set(false)
        Toast.makeText(activity, context.getString(R.string.error_wtf), Toast.LENGTH_SHORT).show()
    }

    fun onClickBack(view: View) = activity.finish()
}