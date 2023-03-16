package com.example.librarymanagement

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide

class BookDetailsActivity : AppCompatActivity() {

    lateinit var titleTV: TextView
    lateinit var subtitleTV: TextView
    lateinit var publisherTV: TextView
    lateinit var descTV: TextView
    lateinit var pageTV: TextView
    lateinit var publisherDateTV: TextView
    lateinit var previewBtn: Button
    lateinit var buyBtn: Button
    lateinit var bookIV: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)

        titleTV = findViewById(R.id.idTVTitle)
        subtitleTV = findViewById(R.id.idTVSubTitle)
        publisherTV = findViewById(R.id.idTVpublisher)
        descTV = findViewById(R.id.idTVDescription)
        pageTV = findViewById(R.id.idTVNoOfPages)
        publisherDateTV = findViewById(R.id.idTVPublishDate)
        previewBtn = findViewById(R.id.idBtnPreview)
        buyBtn = findViewById(R.id.idBtnBuy)
        bookIV = findViewById(R.id.idIVbook)

        val title = getIntent().getStringExtra("title")
        val subtitle = getIntent().getStringExtra("subtitle")
        val publisher = getIntent().getStringExtra("publisher")
        val publishedDate = getIntent().getStringExtra("publishedDate")
        val description = getIntent().getStringExtra("description")
        val pageCount = getIntent().getIntExtra("pageCount", 0)
        val thumbnail = getIntent().getStringExtra("thumbnail")
        val previewLink = getIntent().getStringExtra("previewLink")
        val infoLink = getIntent().getStringExtra("infoLink")
//        val buyLink = getIntent().getStringExtra("buyLink")


        titleTV.setText(title)
        subtitleTV.setText(subtitle)
        publisherTV.setText(publisher)
        publisherDateTV.setText("Published On : " + publishedDate)
        descTV.setText(description)
        pageTV.setText("No Of Pages : " + pageCount)

        Glide.with(this).load(thumbnail).centerCrop().placeholder(R.drawable.books)
            .error(R.drawable.book)
            .into(bookIV)

        previewBtn.setOnClickListener {
            if (previewLink.isNullOrEmpty()) {

                Toast.makeText(this@BookDetailsActivity, "No preview Link present", Toast.LENGTH_SHORT).show()
            }

            val uri: Uri = Uri.parse(previewLink)
            val i = Intent(Intent.ACTION_VIEW, uri)
            startActivity(i)
        }

        buyBtn.setOnClickListener {
            if (infoLink.isNullOrEmpty()) {

                Toast.makeText(
                    this@BookDetailsActivity,
                    "No buy page present for this book",
                    Toast.LENGTH_SHORT
                ).show()
            }

            val uri = Uri.parse(infoLink)
            val i = Intent(Intent.ACTION_VIEW, uri)
            startActivity(i)
        }

    }
}