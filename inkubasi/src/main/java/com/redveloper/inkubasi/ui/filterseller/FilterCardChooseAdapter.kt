package com.redveloper.inkubasi.ui.filterseller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.redveloper.inkubasi.databinding.LayoutCardChooseBinding
import com.redveloper.inkubasi.R as InkubasiR
import com.redveloper.rebator.R as RebatorR

class FilterCardChooseAdapter: ListAdapter<Pair<String, String>, FilterCardChooseAdapter.ViewHolder>(DiffUtilCallback()) {

    var itemSelected = HashSet<Pair<String, String>>()
    var selectionChanged: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutCardChooseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val binding: LayoutCardChooseBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                val data = getItem(adapterPosition)
                if (itemSelected.contains(data)){
                    itemSelected.remove(data)
                    binding.layoutCard.setCardBackgroundColor(ContextCompat.getColor(itemView.context, RebatorR.color.grey))
                } else{
                    itemSelected.add(getItem(adapterPosition))
                    binding.layoutCard.setCardBackgroundColor(ContextCompat.getColor(itemView.context, InkubasiR.color.green))
                }
                selectionChanged?.invoke()
            }
        }
        fun bind(data: Pair<String, String>){
            binding.tvCardChoose.text = data.second
        }
    }

    private class DiffUtilCallback: DiffUtil.ItemCallback<Pair<String, String>>(){
        override fun areItemsTheSame(
            oldItem: Pair<String, String>,
            newItem: Pair<String, String>
        ): Boolean {
            return oldItem.first == newItem.first
        }

        override fun areContentsTheSame(
            oldItem: Pair<String, String>,
            newItem: Pair<String, String>
        ): Boolean {
            return false
        }
    }
}