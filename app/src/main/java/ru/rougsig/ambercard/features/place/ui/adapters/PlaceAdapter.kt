package ru.rougsig.ambercard.features.place.ui.adapters

import android.annotation.SuppressLint
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
import ru.rougsig.ambercard.common.repositories.CategoryRepository
import ru.rougsig.ambercard.features.place.models.CategoryModel
import ru.rougsig.ambercard.features.place.models.PlaceModel
import ru.rougsig.ambercard.utils.bindView
import javax.inject.Inject

/**
 * Created by rougs on 22-Oct-17.
 */
class PlaceAdapter(val array: List<PlaceModel>, val filter: Array<Int>) : RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {
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
            val categories = categoryRepository.getCategoriesByPlace(place)
            val data = if (!filter.isEmpty()) {
                categories.dropWhile { !filter.contains(it.id) }.first()
            } else
                categories.first()
            image.setImageURI(baseURL + data.icon)
            category.text = data.name
            if (place.discountMax > 0) {
                discount.text = "-${place.discountMax}%"
                discount.visible = true
            } else
                discount.visible = false
        }
    }

    override fun getItemCount() = array.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title by bindView<TextView>(R.id.title)
        val description by bindView<TextView>(R.id.description)
        val image by bindView<SimpleDraweeView>(R.id.category_img)
        val category by bindView<TextView>(R.id.category)
        val discount by bindView<TextView>(R.id.discount)
    }

    init {
        App.appComponent.inject(this)
    }
}