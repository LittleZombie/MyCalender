package com.test.h2

import android.view.View
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes
import androidx.recyclerview.widget.RecyclerView
import java.util.*

abstract class BaseRecyclerViewAdapter<T> : RecyclerView.Adapter<BaseRecyclerViewHolder<T>>() {

    private val itemsLock = Any()
    private val items = ArrayList<T>()
    private var lastPosition: Int = -1
    private var animation: Int = -1

    override fun onBindViewHolder(holder: BaseRecyclerViewHolder<T>, position: Int) {
        onBind(holder, position)
        setAnimation(holder.itemView, position)
    }

    override fun getItemCount(): Int {
        synchronized(itemsLock) {
            return items.size
        }
    }

    override fun onViewDetachedFromWindow(viewHolder: BaseRecyclerViewHolder<T>) {
        super.onViewDetachedFromWindow(viewHolder)
        viewHolder.itemView.clearAnimation()
    }

    abstract fun onBind(viewHolder: BaseRecyclerViewHolder<T>, position: Int)

    fun getItems(): List<T> = items

    fun setItems(items: List<T>) {
        synchronized(itemsLock) {
            this.items.apply {
                clear()
                addAll(items)
            }
        }
    }

    fun setItem(position: Int, item: T) {
        synchronized(itemsLock) {
            this.items.set(position, item)
        }
    }

    fun getItem(position: Int): T? {
        synchronized(itemsLock) {
            return items.getOrNull(position)
        }
    }

    fun addItem(item: T) {
        synchronized(itemsLock) {
            this.items.add(item)
        }
    }

    fun addItems(items: List<T>) {
        synchronized(itemsLock) {
            this.items.addAll(items)
        }
    }

    fun addItems(position: Int, items: List<T>) {
        synchronized(itemsLock) {
            this.items.addAll(position, items)
        }
    }

    fun removeItem(position: Int) {
        synchronized(itemsLock) {
            items.removeAt(position)
        }
    }

    fun removeItem(item: T) {
        synchronized(itemsLock) {
            items.remove(item)
        }
    }

    fun removeItems(items: List<T>) {
        synchronized(itemsLock) {
            this.items.removeAll(items)
        }
    }

    fun clearItems() {
        synchronized(itemsLock) {
            items.clear()
        }
    }

    fun setAnimation(@AnimRes animation: Int) {
        this.animation = animation
    }

    protected fun getItemPosition(viewHolder: RecyclerView.ViewHolder): Int =
        viewHolder.layoutPosition

    private fun setAnimation(view: View?, position: Int) {
        view?.let {
            if (position > lastPosition) {
                lastPosition = position
                if (animation >= 0) {
                    it.startAnimation(AnimationUtils.loadAnimation(it.context, animation))
                }
            }
        }
    }
}