package com.example.kotlinsample.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinsample.R
import com.example.kotlinsample.entity.UseCaseCategory

class UseCaseListAdapter(
) : RecyclerView.Adapter<UseCaseListAdapter.ViewHolder>() {

    private lateinit var onListItemClicked: (UseCaseCategory) -> Unit
    lateinit var dataSource: List<UseCaseCategory>

    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false) as TextView
        return ViewHolder(
            textView
        )
    }

    override fun getItemCount() = dataSource.size

    fun onListItemClicked(click: (UseCaseCategory) -> Unit){
        this.onListItemClicked = click
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = dataSource[position].categoryName
        holder.textView.setOnClickListener {
            onListItemClicked(this.dataSource[position])
        }
    }

    fun getItemDecoration(context: Context, @DrawableRes drawableId:Int): DividerItemDecoration {
        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(
            ContextCompat.getDrawable(
                context,
                drawableId
            )!!
        )
        return itemDecorator
    }
}