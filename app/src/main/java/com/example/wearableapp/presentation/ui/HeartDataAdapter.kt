package com.example.wearableapp.presentation.ui

import com.robinhood.spark.SparkAdapter

class HeartDataAdapter(private val heartData: ArrayList<Float>) : SparkAdapter() {

    override fun getCount() = heartData.size

    override fun getItem(index: Int) = heartData[index]

    override fun getY(index: Int): Float = heartData[index]
}