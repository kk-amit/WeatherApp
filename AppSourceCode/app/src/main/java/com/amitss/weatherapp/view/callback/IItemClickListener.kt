package com.amitss.weatherapp.view.callback

/**
 * Callback on List item selected
 */
interface IItemClickListener<T> {

    /**
     * Call when item selected.
     *
     * @param T Selected Item
     */
    fun onItemClick(item: T)

}