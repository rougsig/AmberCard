package ru.rougsig.ambercard.features.place

import com.stfalcon.androidmvvmhelper.mvvm.activities.BindingActivity
import ru.rougsig.ambercard.BR
import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.databinding.ActivityPlaceFilterBinding


/**
 * Created by rougs on 17-Oct-17.
 */

class PlaceFilterActivity : BindingActivity<ActivityPlaceFilterBinding, PlaceFilterActivityVM>() {

    override fun onCreate(): PlaceFilterActivityVM {
        return PlaceFilterActivityVM(this)
    }

    override fun getVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_place_filter
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out_up)
    }
}