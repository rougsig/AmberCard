package ru.rougsig.ambercard.features.place

import android.support.v7.widget.RecyclerView
import com.stfalcon.androidmvvmhelper.mvvm.activities.ActivityViewModel
import android.support.v7.widget.LinearLayoutManager;
import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.features.place.data.PlaceModel
import ru.rougsig.ambercard.features.place.helpers.PlaceAdapter
import ru.rougsig.ambercard.helpers.RawJson
import ru.rougsig.ambercard.utils.JsonParser

/**
 * Created by rougs on 16-Oct-17.
 */

class ListPlaceActivityVM(activity: ListPlaceActivity) : ActivityViewModel<ListPlaceActivity>(activity) {
    override fun onResume() {
        super.onResume()
        val recycler = activity.findViewById<RecyclerView>(R.id.recycler)
        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(activity)
        val item = JsonParser.parser.adapter(PlaceModel::class.java).fromJson(RawJson.ab_one)!!
        recycler.adapter = PlaceAdapter(listOf(
                item, item, item, item, item, item, item, item
        ))
    }
}