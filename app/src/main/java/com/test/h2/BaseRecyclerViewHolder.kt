package com.test.h2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by karenlin on 2018/8/23.
 */
abstract class BaseRecyclerViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    constructor(@LayoutRes layoutResId: Int, parent: ViewGroup) :
            this(LayoutInflater.from(parent.context).inflate(layoutResId, parent, false))

    abstract fun bind(data: T)
}