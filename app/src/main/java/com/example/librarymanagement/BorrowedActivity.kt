package com.example.librarymanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.librarymanagement.databinding.ActivityBorrowedBinding
import com.example.librarymanagement.models.issue_details
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class BorrowedActivity : AppCompatActivity() {
    private lateinit var dbref: DatabaseReference
    private lateinit var userRecyclerview: RecyclerView
    private lateinit var userArrayList: ArrayList<issue_details>
    private lateinit var binding: ActivityBorrowedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBorrowedBinding.inflate(layoutInflater)
        setContentView(binding.root)


        userRecyclerview = findViewById(R.id.sortNameRecyclerView2)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)

        userArrayList = arrayListOf<issue_details>()
        getUserData()
    }

    private fun getUserData() {
        val currentUserID = FirebaseAuth.getInstance().currentUser?.uid

        dbref = FirebaseDatabase.getInstance().getReference("Issue_details_request")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    userArrayList.clear()

                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(issue_details::class.java)
                        if (user?.uids == currentUserID) {
                            userArrayList.add(user!!)
                        }
                    }

                    userRecyclerview.adapter = UBorrowedAdapter(userArrayList, currentUserID!!)
                }
                binding.progressBar4.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }

}
