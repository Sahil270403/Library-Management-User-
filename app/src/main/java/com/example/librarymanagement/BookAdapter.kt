package com.example.librarymanagement

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.librarymanagement.models.book_details

class BookAdapter(private val userList : ArrayList<book_details>): RecyclerView.Adapter<BookAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = userList[position]

        holder.BookName.text = currentitem.bookName
        holder.AuthorName.text = currentitem.authorName
        holder.branch.text = currentitem.branch

    }

    override fun getItemCount(): Int {

        return userList.size
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val BookName : TextView = itemView.findViewById(R.id.BookName)
        val AuthorName : TextView = itemView.findViewById(R.id.AuthorName)
        val branch : TextView = itemView.findViewById(R.id.branch)

    }

}