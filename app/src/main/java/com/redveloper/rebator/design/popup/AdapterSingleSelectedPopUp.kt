package com.redveloper.rebator.design.popup

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.redveloper.rebator.databinding.DesignSingleSelectedItemBinding
import com.redveloper.rebator.utils.gone
import com.redveloper.rebator.utils.visible

class AdapterSingleSelectedPopUp : ListAdapter<Pair<String, String>, AdapterSingleSelectedPopUp.ViewHolder>(DiffUtilCallback()) {

    private var checkedPosition: Int = -1
    var selectionChanged: ((String?) -> Unit)? = null
    var selectionItem: HashSet<String> = hashSetOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = DesignSingleSelectedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val binding: DesignSingleSelectedItemBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                binding.icChecklist.visible()
                if (checkedPosition != adapterPosition){
                    notifyItemChanged(checkedPosition)
                    checkedPosition = adapterPosition
                }
                selectionItem = hashSetOf(getItem(adapterPosition).first)
                selectionChanged?.invoke(getItem(adapterPosition).first)
            }
        }
        fun bind(data: Pair<String, String>){
            binding.tvItem.text = data.second
            if (checkedPosition == -1){
                binding.icChecklist.gone()
            } else {
                if (checkedPosition == adapterPosition){
                    binding.icChecklist.visible()
                } else {
                    binding.icChecklist.gone()
                }
            }
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