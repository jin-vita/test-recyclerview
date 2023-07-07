package com.jinvita.testrecyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jinvita.testrecyclerview.databinding.ItemBasicBinding

class BasicAdapter(private var items: List<BasicObject> = listOf()) :
    RecyclerView.Adapter<BasicAdapter.ViewHolder>() {
    interface Listener {
        fun onItemClick(holder: ViewHolder, view: View, position: Int)
    }

    lateinit var listener: Listener

    fun setOnItemClickListener(callback: (item: BasicObject) -> Unit) {
        listener = object : Listener {
            override fun onItemClick(holder: ViewHolder, view: View, position: Int) {
                if (position == -1) return
                callback(items[position])
            }
        }
    }

    inner class ViewHolder(private val binding: ItemBasicBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener { listener.onItemClick(this, it, adapterPosition) }
        }

        fun setItem(item: BasicObject) = with(binding) {
            textTitle.text = item.name
            textDescription.text = item.description
            btnRemove.setOnClickListener { remove(adapterPosition) }
        }
    }

    fun add(item: BasicObject) {
        val list = items.toMutableList()
        list.add(item)
        items = list
        notifyItemInserted(items.lastIndex)
    }

    fun remove(index: Int) {
        if (index == -1 || items.isEmpty()) return
        val list = items.toMutableList()
        list.removeAt(index)
        items = list
        notifyItemRemoved(index)
    }

    fun clear() = repeat(items.size) { remove(0) }

    @SuppressLint("NotifyDataSetChanged")
    fun update(newDataList: List<BasicObject>) {
        items = newDataList
        notifyDataSetChanged()
    }

    fun getItem(index: Int): BasicObject = items[index]

    override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.setItem(items[position])
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemBasicBinding.inflate(LayoutInflater.from(parent.context), parent, false))
}
