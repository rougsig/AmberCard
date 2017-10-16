package ru.rougsig.ambercard.features.place

import android.content.Intent
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

class PlaceListActivityVM(listActivity: PlaceListActivity) : ActivityViewModel<PlaceListActivity>(listActivity) {
    override fun onResume() {
        super.onResume()
        val recycler = activity.findViewById<RecyclerView>(R.id.recycler)
        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = PlaceAdapter(listOf(
                JsonParser.parser.adapter(PlaceModel::class.java).fromJson(RawJson.ab_one)!!.apply { name = "1" },
                JsonParser.parser.adapter(PlaceModel::class.java).fromJson(RawJson.ab_one)!!.apply { name = "2" },
                JsonParser.parser.adapter(PlaceModel::class.java).fromJson(RawJson.ab_one)!!.apply { name = "3" },
                JsonParser.parser.adapter(PlaceModel::class.java).fromJson(RawJson.ab_one)!!.apply { name = "4" },
                JsonParser.parser.adapter(PlaceModel::class.java).fromJson(RawJson.ab_one)!!.apply { name = "5" },
                JsonParser.parser.adapter(PlaceModel::class.java).fromJson(RawJson.ab_one)!!.apply { name = "6" },
                JsonParser.parser.adapter(PlaceModel::class.java).fromJson(RawJson.ab_one)!!.apply { name = "7" },
                JsonParser.parser.adapter(PlaceModel::class.java).fromJson(RawJson.ab_one)!!.apply { name = "8" }
        ), { place ->
            val intent = Intent(activity, PlaceActivity::class.java)
            intent.putExtra(PlaceActivity.EXTRA_ID, place.id)
            activity.startActivity(intent)
        })
    }
}