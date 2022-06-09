package com.example.wearableapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.wearableapp.presentation.model.MenuItem

class MainViewModel: ViewModel() {

    fun getMenuList(): ArrayList<MenuItem> {
        val menuItems: ArrayList<MenuItem> = ArrayList()
        for (i in 1..4){
            if(i==1)
                menuItems.add(MenuItem( "Measure HR "))
            if(i==2) menuItems.add(MenuItem( "Export HR Data "))
            if(i>2)
                menuItems.add(MenuItem( "Item "+i))

        }
        return menuItems
    }
}