package ru.rougsig.ambercard.features.place

import com.stfalcon.androidmvvmhelper.mvvm.activities.BindingActivity
import ru.rougsig.ambercard.BR
import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.databinding.ActivityPlaceBinding


/**
 * Created by rougs on 11-Oct-17.
 */

class PlaceActivity : BindingActivity<ActivityPlaceBinding, PlaceActivityVM>() {

    override fun onCreate(): PlaceActivityVM {
        return PlaceActivityVM(this)
    }

    override fun getVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_place
    }
}