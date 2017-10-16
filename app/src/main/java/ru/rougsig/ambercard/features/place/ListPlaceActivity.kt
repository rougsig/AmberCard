package ru.rougsig.ambercard.features.place

import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.BR

import com.stfalcon.androidmvvmhelper.mvvm.activities.BindingActivity

import ru.rougsig.ambercard.databinding.ActivityListPlaceBinding


/**
 * Created by rougs on 16-Oct-17.
 */

class ListPlaceActivity : BindingActivity<ActivityListPlaceBinding, ListPlaceActivityVM>() {

    override fun onCreate(): ListPlaceActivityVM {
        return ListPlaceActivityVM(this)
    }

    override fun getVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_list_place
    }

}