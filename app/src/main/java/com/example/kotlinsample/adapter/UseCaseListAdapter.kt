package com.example.kotlinsample.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinsample.R
import com.example.kotlinsample.entity.UseCase

class UseCaseListAdapter(
) : ListAdapter<UseCase, UseCaseListAdapter.UseCaseViewHolder>(UseCaseItemDiffCallback()) {

    private lateinit var onListItemClicked: (UseCase) -> Unit
    lateinit var dataSource: List<UseCase>

    class UseCaseViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UseCaseViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false) as TextView
        return UseCaseViewHolder(
            textView
        )
    }

    override fun getItemCount() = dataSource.size

    fun onListItemClicked(click: (UseCase) -> Unit){
        this.onListItemClicked = click
    }

    override fun onBindViewHolder(holder: UseCaseViewHolder, position: Int) {
        holder.textView.text = dataSource[position].name
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

    class UseCaseItemDiffCallback : DiffUtil.ItemCallback<UseCase>() {
        override fun areItemsTheSame(oldItem: UseCase, newItem: UseCase): Boolean {
            return oldItem.name == newItem.name // 判断是否为同一项（唯一标识）
        }

        override fun areContentsTheSame(oldItem: UseCase, newItem: UseCase): Boolean {
            return oldItem == newItem // 判断内容是否相同（需重写 equals()）
        }
    }
}