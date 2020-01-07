package com.example.managertransactio.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.managertransactio.R
import com.example.managertransactio.model.SpinnerTransaction

class SpinnerCustomAdapter (var context : Context, var typeTrans : ArrayList<SpinnerTransaction>) : BaseAdapter() {

    var layoutInflater : LayoutInflater = LayoutInflater.from(context)
    class ViewHolder(row : View){
        var textviewType : TextView
        var imageSpinner : ImageView

        init {
            textviewType = row.findViewById(R.id.tv_item_type)
            imageSpinner = row.findViewById(R.id.img_spinner)
        }
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view : View?
        var viewholder :ViewHolder
        if (convertView == null){

            view = layoutInflater.inflate(R.layout.item_spinner_organ, null)
            viewholder = ViewHolder(view)
            view.tag = viewholder
        }else{
            view = convertView
            viewholder = convertView.tag as ViewHolder

        }
        var typeTran : SpinnerTransaction = getItem(position) as SpinnerTransaction
        viewholder.textviewType.text = typeTran.type
        viewholder.imageSpinner.setImageResource(typeTran.image)
        return view as View
    }

    override fun getItem(position: Int): Any {
        return typeTrans.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return typeTrans.size
    }

}