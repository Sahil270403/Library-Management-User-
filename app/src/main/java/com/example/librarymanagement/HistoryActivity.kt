package com.example.librarymanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.librarymanagement.databinding.ActivityHistoryBinding
import com.example.librarymanagement.databinding.ActivityReturnedBinding
import com.example.librarymanagement.models.issued_books
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HistoryActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var userArrayList : ArrayList<issued_books>
    private lateinit var binding : ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userRecyclerview = binding.sortNameRecyclerView4
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)

        userArrayList = arrayListOf<issued_books>()
        getUserData()
    }
    private fun getUserData() {
        val currentUserID = FirebaseAuth.getInstance().currentUser?.uid
        dbref = FirebaseDatabase.getInstance().getReference("Returned_Books")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){
                    userArrayList.clear()

                    for (userSnapshot in snapshot.children){

                        val user = userSnapshot.getValue(issued_books::class.java)
                        if (user?.uids == currentUserID) {
                            userArrayList.add(user!!)
                        }
                    }

                    userRecyclerview.adapter = UBookAdapter(userArrayList)
                }
                binding.progressBar4.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }
}