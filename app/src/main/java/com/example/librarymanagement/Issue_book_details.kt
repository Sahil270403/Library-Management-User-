package com.example.librarymanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.librarymanagement.databinding.ActivityIssueBookDetailsBinding
import com.google.firebase.database.DatabaseReference

class Issue_book_details : AppCompatActivity() {

    private lateinit var binding: ActivityIssueBookDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIssueBookDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bookName = intent.getStringExtra("BookName")
        val authorName = intent.getStringExtra("AuthorName")
        val branch = intent.getStringExtra("Branch")
        val description = intent.getStringExtra("Description")

        binding.idTVTitle.text = bookName
        binding.idTVSubTitle.text = authorName
        binding.idTVbranch.text = branch
        binding.idTVDescription.text = description

        binding.idBtnIssue.setOnClickListener {
            val intent = Intent(this, Request_issue::class.java)
            intent.putExtra("BookName", bookName)
            intent.putExtra("AuthorName", authorName)
            intent.putExtra("Branch", branch)
            intent.putExtra("Description", description)
            startActivity(intent)
        }

    }
}