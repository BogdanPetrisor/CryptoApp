package com.example.cryptoapp.crypto

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.cryptoapp.R

class GridAdapter(context:Context, private val items: List<TagsModel>):ArrayAdapter<TagsModel>(context,0,items) {
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false)
        view.findViewById<TextView>(R.id.item_text).text = items[position].name
        return view
    }
}