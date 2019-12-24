package com.amitss.weatherapp.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.amitss.weatherapp.R
import com.amitss.weatherapp.service.model.CitySearchModel

/**
 * Custom adapter with Filter autocomplete
 */
class CitySearchAdapter(context: Context, searchAPI: CitySearchModel) : BaseAdapter(), Filterable {

    var searchAPIModel: CitySearchModel = searchAPI
    private val mInflator: LayoutInflater = LayoutInflater.from(context)

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                if (filterResults.count == 0) {
                    notifyDataSetInvalidated()
                } else {
                    searchAPIModel = filterResults.values as CitySearchModel
                }
            }

            @SuppressLint("DefaultLocale")
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val queryString = charSequence?.toString()?.toLowerCase()

                val filterResults = FilterResults()
                filterResults.values = if (queryString == null || queryString.isEmpty())
                    filterResults.values = searchAPIModel
                else
                    (searchAPIModel.search_api)?.result?.filter {
                        it.areaName?.get(0)?.value?.toLowerCase()?.contains(queryString)!! ||
                                it.country?.get(0)?.value?.toLowerCase()?.contains(queryString)!!
                    }
                return filterResults
            }
        }
    }


    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            view = this.mInflator.inflate(R.layout.city_row_item, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        val areaName = (searchAPIModel.search_api)!!.result?.get(position)?.areaName?.get(0)?.value
        val countryName =
            (searchAPIModel.search_api)!!.result?.get(position)?.country?.get(0)?.value

        viewHolder.label.text = "$areaName, $countryName"

        return view!!
    }

    override fun getItem(position: Int): Any? {
        if (searchAPIModel.search_api?.result?.isEmpty()!!) {
            return 0
        }
        return (searchAPIModel.search_api!!).result?.get(position)
    }

    override fun getItemId(position: Int): Long {
        if (searchAPIModel.search_api?.result?.isEmpty()!!) {
            return 0
        }
        return position.toLong()
    }

    override fun getCount(): Int {
        if (searchAPIModel.search_api?.result?.isEmpty()!!) {
            return 0
        }
        return searchAPIModel.search_api?.result!!.size
    }

    private class ViewHolder(row: View) {
        val label: TextView = row.findViewById(R.id.textView) as TextView
    }

    fun setSearchModel(searchModel: CitySearchModel) {
        searchAPIModel = searchModel
    }
}
