package com.redveloper.akusisi.ui.dashboard

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.redveloper.akusisi.R as AkusisiR
import com.redveloper.akusisi.databinding.LayoutDashboardItemBinding
import com.redveloper.akusisi.ui.dashboard.model.SellerModel
import com.redveloper.akusisi.utils.ColorUtils
import java.text.SimpleDateFormat
import java.util.*

class DashboardAdapter: ListAdapter<SellerModel, DashboardAdapter.ViewHolder>(DiffUtilCallback()) {

    var itemSelected: ((tiktokId: String?) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutDashboardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val binding: LayoutDashboardItemBinding): RecyclerView.ViewHolder(binding.root){
        init{
            binding.root.setOnClickListener {
                itemSelected?.invoke(getItem(adapterPosition).tiktokId)
            }
        }
        @SuppressLint("SimpleDateFormat")
        fun bind(data: SellerModel){
            binding.tvTiktokId.text = data.tiktokId
            binding.tvSellerName.text = data.sellerName
            val month = SimpleDateFormat("MMMM").format(data.date)
            binding.tvMonth.text = month
            binding.tvCity.text = data.cityName
            binding.tvDistrict.text = data.districtName

            data.date?.let {
                binding.layoutMonth.setCardBackgroundColor(ContextCompat.getColor(itemView.context, ColorUtils.setCollorView(it)))
                binding.viewBottom.setBackgroundColor(ContextCompat.getColor(itemView.context, ColorUtils.setCollorView(it)))
            }
        }
    }



    private class DiffUtilCallback: DiffUtil.ItemCallback<SellerModel>(){
        override fun areItemsTheSame(oldItem: SellerModel, newItem: SellerModel): Boolean {
            return oldItem.tiktokId == newItem.tiktokId
        }

        override fun areContentsTheSame(oldItem: SellerModel, newItem: SellerModel): Boolean {
            return false
        }
    }
}