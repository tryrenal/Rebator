package com.redveloper.akusisi.ui.dashboard

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.redveloper.akusisi.R as AkusisiR
import com.redveloper.akusisi.databinding.LayoutDashboardItemBinding
import com.redveloper.akusisi.ui.dashboard.model.SellerModel
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
                binding.layoutMonth.setBackgroundColor(setCollorView(it))
                binding.viewBottom.setBackgroundColor(setCollorView(it))
            }
        }
    }

    private fun setCollorView(date: Date): Int{
        val calender = Calendar.getInstance()
        calender.time = date
        val intMonth = calender.get(Calendar.MONTH)
        return when(intMonth){
            1 -> AkusisiR.color.color_january
            2 -> AkusisiR.color.color_february
            3 -> AkusisiR.color.color_march
            4 -> AkusisiR.color.color_april
            5 -> AkusisiR.color.color_may
            6 -> AkusisiR.color.color_june
            7 -> AkusisiR.color.color_july
            8 -> AkusisiR.color.color_august
            9 -> AkusisiR.color.color_september
            10 -> AkusisiR.color.color_october
            11-> AkusisiR.color.color_november
            12 -> AkusisiR.color.color_december
            else -> AkusisiR.color.color_december
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