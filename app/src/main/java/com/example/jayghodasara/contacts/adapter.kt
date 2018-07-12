package com.example.jayghodasara.contacts

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class adapter(var context: Context, var list: ArrayList<user>) : RecyclerView.Adapter<adapter.Vholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vholder {

        var v: View = LayoutInflater.from(parent.context).inflate(R.layout.items, parent, false)

        return Vholder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Vholder, position: Int) {

        var user: user = list[position]
        holder.name.text = user.name
        holder.number.text = user.number
    }


    inner class Vholder(view: View) : RecyclerView.ViewHolder(view) {

        var name: TextView = view.findViewById(R.id.textView1)
        var number: TextView = view.findViewById(R.id.number)
    }
}