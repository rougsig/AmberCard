package ru.rougsig.ambercard.features.place.helpers

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.rougsig.ambercard.databinding.PlaceFilterItemBinding
import ru.rougsig.ambercard.features.place.data.CategoryModel

/**
 * Created by rougs on 16-Oct-17.
 */
class FilterCategoryAdapter(private val array: List<CategoryModel>, val filter: ArrayList<Int>) : RecyclerView.Adapter<FilterCategoryAdapter.CategoryItemViewHolder>() {
    override fun getItemCount() = array.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PlaceFilterItemBinding.inflate(inflater, parent, false)
        return CategoryItemViewHolder(binding.root, array, filter)
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        val category = array[position]
        holder.binding.category = category
    }

    class CategoryItemViewHolder(itemView: View, array: List<CategoryModel>, filter: ArrayList<Int>) : RecyclerView.ViewHolder(itemView) {
        val binding: PlaceFilterItemBinding = DataBindingUtil.bind(itemView)

        init {
            itemView.setOnClickListener {
                if (!binding.checkbox.isChecked) {
                    filter.add(array[layoutPosition].id)
                } else {
                    filter.remove(array[layoutPosition].id)
                }
                binding.checkbox.toggle()
            }
        }
    }
}