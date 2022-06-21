package com.example.wearableapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.wearableapp.presentation.model.MenuItem

class MainViewModel: ViewModel() {

    fun getMenuList(): ArrayList<MenuItem> {
        val menuItems: ArrayList<MenuItem> = ArrayList()
        for (i in 0..3){
            if(i==0)
                menuItems.add(MenuItem( "Measure HR "))
            if(i==1) menuItems.add(MenuItem( "Export HR data "))
            if(i>1)
                menuItems.add(MenuItem( "Action "))

        }
        return menuItems
    }
}