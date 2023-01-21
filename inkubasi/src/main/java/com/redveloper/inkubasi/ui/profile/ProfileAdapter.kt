package com.redveloper.inkubasi.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.redveloper.inkubasi.databinding.CardMenuInkubasiBinding
import com.redveloper.inkubasi.ui.profile.model.MenuProfile
import com.redveloper.inkubasi.ui.profile.model.MenuProfileEnum
import com.redveloper.rebator.utils.gone
import com.redveloper.rebator.R as AppR

class ProfileAdapter: ListAdapter<MenuProfile, ProfileAdapter.ViewHolder>(DiffUtilCallback()) {
    var menuSelected: ((menu: MenuProfileEnum) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = CardMenuInkubasiBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val binding: CardMenuInkubasiBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                menuSelected?.invoke(getItem(adapterPosition).menu)
            }
        }
        fun bind(data: MenuProfile){
            binding.tvMenu.text = data.text
            binding.icMenu.setImageDrawable(ContextCompat.getDrawable(itemView.context, data.image))

            if (data.menu == MenuProfileEnum.LOGOUT){
                binding.tvMenu.setTextColor(ContextCompat.getColor(itemView.context, AppR.color.red))
                binding.icArrow.gone()
            }
        }
    }

    private class DiffUtilCallback: DiffUtil.ItemCallback<MenuProfile>(){
        override fun areItemsTheSame(oldItem: MenuProfile, newItem: MenuProfile): Boolean {
            return oldItem.menu == newItem.menu
        }

        override fun areContentsTheSame(oldItem: MenuProfile, newItem: MenuProfile): Boolean {
            return false
        }
    }
}