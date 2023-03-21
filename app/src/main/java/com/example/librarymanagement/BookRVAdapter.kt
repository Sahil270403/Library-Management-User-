package com.example.librarymanagement

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.librarymanagement.models.BookRVModal
import com.squareup.picasso.Picasso


class BookRVAdapter(

    private var bookList: ArrayList<BookRVModal>,
    private var ctx: Context
) : RecyclerView.Adapter<BookRVAdapter.BookViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookRVAdapter.BookViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.book_item,
            parent, false
        )

        return BookRVAdapter.BookViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BookRVAdapter.BookViewHolder, position: Int) {
        val bookInfo = bookList.get(position)

        Glide.with(ctx)
            .load(bookInfo.thumbnail)
            .placeholder(R.drawable.books)
            .error(R.drawable.book)
            .into(holder.bookIV)
        holder.bookTitleTV.text = bookInfo.title
        holder.bookPagesTV.text = "Pages : " + bookInfo.pageCount


        holder.itemView.setOnClickListener {
            val i = Intent(ctx, BookDetailsActivity::class.java)
            i.putExtra("title", bookInfo.title)
            i.putExtra("subtitle", bookInfo.subtitle)
            i.putExtra("authors", bookInfo.authors)
            i.putExtra("publisher", bookInfo.publisher)
            i.putExtra("publishedDate", bookInfo.publishedDate)
            i.putExtra("description", bookInfo.description)
            i.putExtra("pageCount", bookInfo.pageCount)
            i.putExtra("thumbnail", bookInfo.thumbnail)
            i.putExtra("previewLink", bookInfo.previewLink)
            i.putExtra("infoLink", bookInfo.infoLink)
            i.putExtra("buyLink", bookInfo.buyLink)

            ctx.startActivity(i)
        }

    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val bookTitleTV: TextView = itemView.findViewById(R.id.idTVBookName)
        val bookPagesTV: TextView = itemView.findViewById(R.id.idTVBookPages)
        val bookIV: ImageView = itemView.findViewById(R.id.idIVBook)
    }
}

