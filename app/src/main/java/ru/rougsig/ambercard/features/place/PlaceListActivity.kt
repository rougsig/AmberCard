package ru.rougsig.ambercard.features.place

import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.BR

import com.stfalcon.androidmvvmhelper.mvvm.activities.BindingActivity
import ru.rougsig.ambercard.databinding.ActivityPlaceListBinding


/**
 * Created by rougs on 16-Oct-17.
 */

class PlaceListActivity : BindingActivity<ActivityPlaceListBinding, PlaceListActivityVM>() {

    override fun onCreate(): PlaceListActivityVM {
        return PlaceListActivityVM(this)
    }

    override fun getVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_place_list
    }

}