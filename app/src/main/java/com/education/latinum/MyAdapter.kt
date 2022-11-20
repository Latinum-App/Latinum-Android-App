package com.education.latinum

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val vocabList : ArrayList<Vocabulary>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyAdapter.MyViewHolder, position: Int) {

        val vocabulary: Vocabulary = vocabList[position]
        holder.word.text = vocabulary.Word
        holder.translation.text = vocabulary.Translation

    }

    override fun getItemCount(): Int {

        return vocabList.size

    }

    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val word: TextView = itemView.findViewById(R.id.tvword)
        var translation: TextView = itemView.findViewById(R.id.tvtranslation)

    }
}