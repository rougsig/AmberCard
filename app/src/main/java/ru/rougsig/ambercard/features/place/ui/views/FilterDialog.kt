package ru.rougsig.ambercard.features.place.ui.views

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.common.App
import ru.rougsig.ambercard.common.api.baseURL
import ru.rougsig.ambercard.common.repositories.CategoryRepository
import ru.rougsig.ambercard.features.place.models.CategoryModel
import ru.rougsig.ambercard.utils.bindView
import javax.inject.Inject

/**
 * Created by rougs on 22-Oct-17.
 */
class FilterDialog(context: Context, val applyFilter: (List<CategoryModel>) -> Unit) : AlertDialog(context, R.style.FilterDialog) {
    @Inject
    lateinit var categoryRepository: CategoryRepository
    lateinit var items: ArrayList<CategoryModel>
    private val btnClose by bindView<AppCompatImageButton>(R.id.btn_close)
    private val recycler by bindView<RecyclerView>(R.id.recycler)
    val filter = ArrayList<CategoryModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setCanceledOnTouchOutside(false)
        setContentView(R.layout.dialog_filter)

        btnClose.setOnClickListener { dismiss() }

        items = ArrayList(categoryRepository.getAllCategory())
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = FilterAdapter(items)

        setOnDismissListener {
            items.sortWith(Comparator { o1, o2 ->
                val o1InFilter = filter.contains(o1)
                val o2InFilter = filter.contains(o2)
                if (o1InFilter && o2InFilter)
                    0
                else if (o1InFilter)
                    -1
                else
                    1
            })
            recycler.adapter = FilterAdapter(items)
            if (!filter.isEmpty())
                applyFilter(filter)
        }
    }

    inner class FilterAdapter(val array: List<CategoryModel>) : RecyclerView.Adapter<FilterAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.filter_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val data = array[position]

            with(holder) {
                image.setImageURI(baseURL + data.icon)
                name.text = data.name

                card.setOnClickListener {
                    if (checkBox.isChecked)
                        filter.remove(data)
                    else
                        filter.add(data)
                    checkBox.toggle()
                }

                if (filter.contains(data))
                    checkBox.toggle()
            }
        }

        override fun getItemCount() = array.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val image by bindView<SimpleDraweeView>(R.id.category_img)
            val name by bindView<TextView>(R.id.category_text)
            val checkBox by bindView<AppCompatCheckBox>(R.id.checkbox)
            val card by bindView<CardView>(R.id.card)
        }
    }

    init {
        App.appComponent.inject(this)
    }
}