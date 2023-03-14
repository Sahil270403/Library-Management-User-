package com.example.librarymanagement.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.librarymanagement.BookAdapter
import com.example.librarymanagement.R
import com.example.librarymanagement.models.book_details
import com.google.firebase.database.*

class HomeFragment : Fragment() {

    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var userArrayList : ArrayList<book_details>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        userRecyclerview = view.findViewById(R.id.sortNameRecyclerView)
        userRecyclerview.layoutManager = LinearLayoutManager(requireActivity())
        userRecyclerview.setHasFixedSize(true)

        userArrayList = arrayListOf<book_details>()
        getUserData()

        return view
    }

    private fun getUserData() {

        dbref = FirebaseDatabase.getInstance().getReference("Book_details")

        dbref.addValueEventListener(object : ValueEventListener {

    override fun onDataChange(snapshot: DataSnapshot) {

             if (snapshot.exists()){
             userArrayList.clear()

            for (userSnapshot in snapshot.children){

              val user = userSnapshot.getValue(book_details::class.java)
             userArrayList.add(user!!)
             }

             userRecyclerview.adapter = BookAdapter(userArrayList)
         }
    }


            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }
}
