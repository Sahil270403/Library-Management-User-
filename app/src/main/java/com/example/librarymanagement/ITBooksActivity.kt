package com.example.librarymanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.librarymanagement.databinding.ActivityItbooksBinding
import com.example.librarymanagement.models.book_details
import com.google.firebase.database.*

class ITBooksActivity : AppCompatActivity() {
    private lateinit var database : DatabaseReference
    private lateinit var binding : ActivityItbooksBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BookAdapter
    private lateinit var userList: ArrayList<book_details>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItbooksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.ItbooksRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        userList = ArrayList<book_details>()
        readAllData()

        binding.readdataBtn.setOnClickListener {
            val userName : String = binding.etusername.text.toString().trim()
            if  (userName.isNotEmpty()){
                searchData(userName)
            }else{
                readAllData()
            }
        }
    }

    private fun readAllData() {
        database = FirebaseDatabase.getInstance().getReference("Book_details")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    userList.clear()
                    for (bookSnapshot in dataSnapshot.children) {
                        val AuthorName = bookSnapshot.child("authorName").value
                        val BookName = bookSnapshot.child("bookName").value
                        val Branch = bookSnapshot.child("branch").value
                        val Description = bookSnapshot.child("description").value

                        val book = book_details(BookName?.toString(), AuthorName?.toString(), Branch?.toString(), Description?.toString())
                        if(Branch == "IT"){
                            userList.add(book)
                        }
                    }

                    adapter = BookAdapter(userList)
                    recyclerView.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ITBooksActivity,"Failed to read data",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun searchData(userName: String) {
        database = FirebaseDatabase.getInstance().getReference("Book_details")
        database.child(userName).get().addOnSuccessListener {
            if (it.exists()){
                val AuthorName = it.child("authorName").value
                val BookName = it.child("bookName").value
                val Branch = it.child("branch").value
                val Description = it.child("description").value

                userList.clear()
                val book = book_details(BookName?.toString(), AuthorName?.toString(), Branch?.toString(), Description?.toString())
                userList.add(book)

                if(Branch == "IT"){
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this,"Successfuly Read",Toast.LENGTH_SHORT).show()
                    binding.etusername.text.clear()
                }else{
                    Toast.makeText(this,"Book Doesn't Exist",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Book Doesn't Exist",Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
        }
    }
}
