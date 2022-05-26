package com.example.wearableapp.presentation.ui
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wearableapp.R


class MainMenuAdapter() : RecyclerView.Adapter<MainMenuAdapter.RecyclerViewHolder>() {
    private var dataSource = ArrayList<MenuItem>()

    interface AdapterCallback {
        fun onItemClicked(menuPosition: Int?)
    }

    private var mCallback: AdapterCallback? = null

    private var context: Context? = null

    constructor(context: Context, dataArgs: ArrayList<MenuItem>, callback: AdapterCallback) : this() {
        this.context =context
        this.dataSource = dataArgs
        this.mCallback = callback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewHolder {
        val view: View =
            LayoutInflater.from(parent.getContext())
                .inflate(
                    R.layout.layout,
                    parent,
                    false
                )

        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val (text) = dataSource[position]

        holder.menuItem.text = text
        holder.menuContainer.setOnClickListener {
            context?.resources?.let { it1 -> holder.menuItem.setBackgroundDrawable(context?.getDrawable(R.drawable.rounded_background)) }
            context?.resources?.let { it1 -> holder.menuItem.setTextColor(it1?.getColor(R.color.color_berry)) }
            if (mCallback != null) {
                mCallback?.onItemClicked(position)
            }
        }
    }

    override fun getItemCount() = dataSource.size

    class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var menuContainer: RelativeLayout
        var menuItem: TextView

        init {
            menuContainer = view.findViewById(R.id.menu_container)
            menuItem = view.findViewById(R.id.menu_item)
        }
    }
}