package com.example.test.ui.catalog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.test.R

class CustomAdapter(private val context: Context, private val dataList: List<Item>) : BaseAdapter() {
    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): Any {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_category, parent, false)

        val item: Item = dataList[position]

        val itemImage: ImageView = view.findViewById(R.id.item_image)
        itemImage.setImageResource(item.imageResource)

        val itemName: TextView = view.findViewById(R.id.name_item_tv)
        itemName.text = item.name

        return view
    }
}