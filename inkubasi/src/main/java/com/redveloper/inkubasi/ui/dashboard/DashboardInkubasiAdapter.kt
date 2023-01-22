package com.redveloper.inkubasi.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.redveloper.inkubasi.databinding.LayoutDashboardItemBinding
import com.redveloper.inkubasi.ui.dashboard.model.DashboardModel
import com.redveloper.rebator.utils.image.load
import com.redveloper.rebator.utils.mapper.StatusSellerMapper

class DashboardInkubasiAdapter : ListAdapter<DashboardModel, DashboardInkubasiAdapter.ViewHolder>(DiffUtilCallback()){

    var itemSelected: ((tiktokId: String?) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutDashboardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val binding: LayoutDashboardItemBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                itemSelected?.invoke(getItem(adapterPosition).tiktokId)
            }
        }
        fun bind(data: DashboardModel){
            data.photoUrl?.let { binding.imgSeller.load(it) }
            binding.tvTiktokId.text = data.tiktokId
            binding.tvSellerName.text = data.sellerName
            binding.tvCity.text = data.cityName
            binding.tvDistrict.text = data.districtName
            binding.tvStatus.text = data.status?.let { StatusSellerMapper.getValueOfStatus(it) }
        }
    }

    private class DiffUtilCallback: DiffUtil.ItemCallback<DashboardModel>(){
        override fun areItemsTheSame(oldItem: DashboardModel, newItem: DashboardModel): Boolean {
            return oldItem.tiktokId == newItem.tiktokId
        }

        override fun areContentsTheSame(oldItem: DashboardModel, newItem: DashboardModel): Boolean {
            return false
        }
    }
}