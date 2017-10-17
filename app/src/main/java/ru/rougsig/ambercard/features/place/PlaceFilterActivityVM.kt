package ru.rougsig.ambercard.features.place

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.stfalcon.androidmvvmhelper.mvvm.activities.ActivityViewModel
import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.features.place.data.CategoryRepository
import ru.rougsig.ambercard.features.place.helpers.FilterCategoryAdapter
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by rougs on 17-Oct-17.
 */

class PlaceFilterActivityVM(activity: PlaceFilterActivity) : ActivityViewModel<PlaceFilterActivity>(activity) {
    private var recycler: RecyclerView? = null
    val filter: ArrayList<Int> = ArrayList()
    override fun onStart() {
        if (recycler == null) {
            recycler = activity.findViewById(R.id.recycler)
            recycler!!.layoutManager = LinearLayoutManager(activity)
            recycler!!.adapter = FilterCategoryAdapter(CategoryRepository.getAllCategory(), filter)
        }
    }

    fun submitFilter(view: View) = submitFilter()
    private fun submitFilter() {
        val intent = Intent()
        intent.putIntegerArrayListExtra(PlaceListActivityVM.FILTER_DATA, ArrayList(filter.map { it }))
        if (filter.isEmpty())
            activity.setResult(Activity.RESULT_CANCELED, intent)
        else
            activity.setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onBackKeyPress(): Boolean {
        submitFilter()
        return false
    }
}