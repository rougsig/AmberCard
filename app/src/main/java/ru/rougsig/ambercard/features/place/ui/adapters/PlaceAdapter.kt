package ru.rougsig.ambercard.features.place.ui.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.pawegio.kandroid.visible
import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.common.App
import ru.rougsig.ambercard.common.api.baseURL
import ru.rougsig.ambercard.common.di.modules.LocationModule
import ru.rougsig.ambercard.common.repositories.CategoryRepository
import ru.rougsig.ambercard.features.place.models.PlaceModel
import ru.rougsig.ambercard.features.place.ui.activities.PlaceActivity
import ru.rougsig.ambercard.utils.bindView
import ru.rougsig.ambercard.utils.getDistance
import ru.rougsig.ambercard.utils.startActivityWithAnimation
import javax.inject.Inject

/**
 * Created by rougs on 22-Oct-17.
 */
class PlaceAdapter(val array: List<PlaceModel>) : RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {
    @Inject
    lateinit var categoryRepository: CategoryRepository

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = array[position]

        with(holder) {
            title.text = place.name
            description.text = place.description
            val data = categoryRepository.getCategoriesByPlace(place).first()
            image.setImageURI(baseURL + data.icon)
            category.text = data.name
            if (place.discountMax > 0) {
                discount.text = "-${place.discountMax}%"
                discount.visible = true
            } else
                discount.visible = false
            if (LocationModule.location != null) {
                distance.text = getDistance(place.latitude, place.longitude)
            } else
                distance.visible = false
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, PlaceActivity::class.java)
                intent.putExtra(PlaceActivity.PLACE_ID_EXTRA, place.id)
                itemView.context.startActivityWithAnimation(intent, R.anim.slide_in_left, R.anim.fade_out)
            }
        }
    }

    override fun getItemCount() = array.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title by bindView<TextView>(R.id.name)
        val description by bindView<TextView>(R.id.description)
        val image by bindView<SimpleDraweeView>(R.id.category_img)
        val category by bindView<TextView>(R.id.category)
        val discount by bindView<TextView>(R.id.discount)
        val distance by bindView<TextView>(R.id.distance)
    }

    init {
        App.appComponent.inject(this)
    }
}