package com.amitss.weatherapp.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amitss.weatherapp.R
import com.amitss.weatherapp.database.entity.CityEntity
import com.amitss.weatherapp.view.callback.IItemClickListener
import kotlinx.android.synthetic.main.row_list_item.view.*

/**
 * Custom City List Adapter.
 */
class CityListAdapter(private var context: Context, private var cityList: ArrayList<CityEntity>) :
    RecyclerView.Adapter<CityListAdapter.CityListViewHolder>() {

    // CityListAdapter properties
    private var cityListModel = ArrayList<CityEntity>(cityList)
    private var itemClickListener: IItemClickListener<CityEntity>? = null

    fun setCityValue(updatedCityList: ArrayList<CityEntity>) {
        cityListModel.clear()
        this.cityListModel.addAll(updatedCityList)
    }

    fun setListener(itemClickListener: IItemClickListener<CityEntity>) {
        this.itemClickListener = itemClickListener
    }

    fun removeListener() {
        this.itemClickListener = null
    }

    class CityListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val parent = itemView.cvRowParent!!
        val ivPic = itemView.ivIcon!!
        val cityName = itemView.tvName!!
        val countryName = itemView.tvMessage!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityListViewHolder {
        return CityListViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.city_list_row_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return cityListModel.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CityListViewHolder, position: Int) {
        val countryName = cityListModel[position].country
        val cityName = cityListModel[position].areaName
        val lat = cityListModel[position].latitude
        val long = cityListModel[position].longitude
        val region = cityListModel[position].region

        holder.ivPic.setBackgroundResource(R.drawable.ic_weather)
        holder.cityName.text = "$cityName (Latitude = $lat Longitude = $long)"
        holder.countryName.text = "$countryName ($region)"

        holder.parent.setOnClickListener {
            if (itemClickListener != null) {
                itemClickListener?.onItemClick(cityList[position])
            }
        }

    }

}