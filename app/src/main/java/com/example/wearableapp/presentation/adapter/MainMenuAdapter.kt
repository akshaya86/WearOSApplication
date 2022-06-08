package com.example.wearableapp.presentation.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.wearableapp.R
import com.example.wearableapp.presentation.model.MenuItem


class MainMenuAdapter() : RecyclerView.Adapter<MainMenuAdapter.RecyclerViewHolder>() {
    private var dataSource = ArrayList<MenuItem>()
    private var selectedItem = -1

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
        context?.resources?.let {
            //holder.menuContainer.setBackgroundDrawable(context?.getDrawable(R.drawable.rounded_bg))
            //holder.menuItem.setTextColor(it.getColor(R.color.color_black))
        }

        if(selectedItem == position) {
            context?.resources?.let {
              //  holder.menuContainer.setBackgroundDrawable(context?.getDrawable(R.drawable.rounded_background))
               // holder.menuItem.setTextColor(it.getColor(R.color.color_berry))
            }
        }

        if(position == itemCount-1){
            holder.itemView.visibility =View.INVISIBLE
        }

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

    class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var menuContainer: ConstraintLayout
        var menuItem: TextView

        init {
            menuContainer = view.findViewById(R.id.menu_container)
            menuItem = view.findViewById(R.id.menu_item)
        }
    }
}