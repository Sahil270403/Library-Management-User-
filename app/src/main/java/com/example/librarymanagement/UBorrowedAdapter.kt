package com.example.librarymanagement

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.librarymanagement.models.issue_details
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class UBorrowedAdapter(private val userList: ArrayList<issue_details>, private val currentUserUid: String) : RecyclerView.Adapter<UBorrowedAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_book3, parent, false)
        return MyViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = userList[position]
        if (currentitem.uids == currentUserUid) {
            holder.BookName.text = currentitem.bookName
            holder.AuthorName.text = currentitem.authorName
            holder.branch.text = currentitem.branch
            holder.IssuerName.text = currentitem.userName
            holder.Rollno.text = currentitem.rollNo

            // Convert timestamp to date string
            val timestamp = currentitem.date?.get("date") as Long
            val date = Date(timestamp)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(date)
            holder.requestdate.text = formattedDate
        } else {
            holder.itemView.visibility = View.GONE // hide the view if uid doesn't match
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val BookName: TextView = itemView.findViewById(R.id.userName)
        val AuthorName: TextView = itemView.findViewById(R.id.AuthorName)
        val branch: TextView = itemView.findViewById(R.id.branch)
        val IssuerName: TextView = itemView.findViewById(R.id.IssuerName)
        val Rollno: TextView = itemView.findViewById(R.id.Rollno)
        val requestdate: TextView = itemView.findViewById(R.id.requestdate)
    }
}
