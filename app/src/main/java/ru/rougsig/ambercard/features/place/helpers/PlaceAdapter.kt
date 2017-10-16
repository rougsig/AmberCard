package ru.rougsig.ambercard.features.place.helpers

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.rougsig.ambercard.databinding.PlaceItemBinding
import ru.rougsig.ambercard.features.place.data.PlaceModel

/**
 * Created by rougs on 16-Oct-17.
 */
class PlaceAdapter(private val array: List<PlaceModel>, private val onClick: (place: PlaceModel) -> Unit) : RecyclerView.Adapter<PlaceAdapter.PlaceItemViewHolder>() {
    override fun getItemCount() = array.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PlaceItemBinding.inflate(inflater, parent, false)
        return PlaceItemViewHolder(binding.root, array, onClick)
    }

    override fun onBindViewHolder(holder: PlaceItemViewHolder, position: Int) {
        holder.binding.place = array[position]
    }

    class PlaceItemViewHolder(itemView: View, array: List<PlaceModel>, onClick: (place: PlaceModel) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val binding: PlaceItemBinding = DataBindingUtil.bind(itemView)

        init {
            itemView.setOnClickListener { onClick(array[layoutPosition]) }
        }
    }
}