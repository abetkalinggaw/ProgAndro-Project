package com.abetkalinggaw.miniproject2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
class NotesAdapter(val notes : ArrayList<Notes>, val onClick : OnClick) : RecyclerView.Adapter<NotesAdapter.MyHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notes, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bind(notes.get(position))
        holder.itemView.findViewById<Button>(R.id.btnDelete).setOnClickListener {
            onClick.delete(notes.get(position).key)
        }
        holder.itemView.setOnClickListener {
            onClick.edit(notes.get(position))
        }
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(note : Notes){
            itemView.findViewById<TextView>(R.id.tvNoteName).text = note.title
            itemView.findViewById<TextView>(R.id.tvNoteDescription).text = note.description
        }
    }

    interface OnClick {
        fun delete(key : String?)
        fun edit(note : Notes?)
    }
}