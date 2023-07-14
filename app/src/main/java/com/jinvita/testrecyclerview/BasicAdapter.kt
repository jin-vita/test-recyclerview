package com.jinvita.testrecyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jinvita.testrecyclerview.databinding.ItemBasicBinding

class BasicAdapter(private val items: MutableList<BasicObject> = mutableListOf()) :
    RecyclerView.Adapter<BasicAdapter.ViewHolder>() {
    interface Listener {
        fun onItemClick(holder: ViewHolder, view: View, position: Int)
    }

    lateinit var listener: Listener

    fun setOnItemClickListener(callback: (item: BasicObject) -> Unit) {
        listener = object : Listener {
            override fun onItemClick(holder: ViewHolder, view: View, position: Int) =
                position.let { if (it != -1) callback(items[it]) }
        }
    }

    inner class ViewHolder(private val binding: ItemBasicBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener { listener.onItemClick(this, it, adapterPosition) }
        }

        fun setItem(item: BasicObject) = with(binding) {
            textTitle.text = item.name
            textDescription.text = item.description
            btnRemove.setOnClickListener { adapterPosition.let { if (it != -1) remove(it) } }
        }
    }

    fun add(item: BasicObject) {
        items.add(item)
        notifyItemInserted(items.lastIndex)
    }

    fun remove(index: Int) {
        items.removeAt(index)
        notifyItemRemoved(index)
    }

    fun clear() = repeat(items.size) { remove(0) }

    @SuppressLint("NotifyDataSetChanged")
    fun update(newDataList: List<BasicObject>) {
        items.clear()
        newDataList.forEach { items.add(it.copy()) }
        notifyDataSetChanged()
    }

    fun getItem(index: Int): BasicObject = items[index]

    override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.setItem(items[position])
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemBasicBinding.inflate(LayoutInflater.from(parent.context), parent, false))
}
