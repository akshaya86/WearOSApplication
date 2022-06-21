package com.example.wearableapp.presentation.adapter
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateMargins
import androidx.recyclerview.widget.RecyclerView
import com.example.wearableapp.R
import com.example.wearableapp.presentation.model.MenuItem


class MainMenuAdapter() : RecyclerView.Adapter<MainMenuAdapter.RecyclerViewHolder>() {
    private var dataSource = ArrayList<MenuItem>()
    private var selectedItem = -1
    lateinit var recylerview : RecyclerView

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

        if(position == itemCount-1){
            holder.itemView.visibility =View.INVISIBLE
        }

        setCenteredMargin(holder,position)

        holder.menuContainer.setOnClickListener {
            val previousItem = selectedItem
            selectedItem = position
            notifyItemChanged(previousItem)
            notifyItemChanged(position)

            if (mCallback != null) {
                mCallback?.onItemClicked(position)
            }
        }
    }

    override fun getItemCount() = dataSource.size

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recylerview = recyclerView
    }

    private fun setCenteredMargin(holder: RecyclerViewHolder, position: Int) {
        val layoutParams = holder.menuContainer.layoutParams as ViewGroup.MarginLayoutParams
        val rview = this.recylerview.layoutParams as ViewGroup.MarginLayoutParams

        if (position == 0) {
             layoutParams.updateMargins(top = rview.height/2 - layoutParams.height/2)
        }else if(position==2){
            layoutParams.updateMargins(bottom = layoutParams.height/2)
        }
    }

    class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var menuContainer: ConstraintLayout
        var menuItem: TextView

        init {
            menuContainer = view.findViewById(R.id.menu_container)
            menuItem = view.findViewById(R.id.menu_item)
        }

    }
}