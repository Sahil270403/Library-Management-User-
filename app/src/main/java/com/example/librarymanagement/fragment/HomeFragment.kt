package com.example.librarymanagement.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.librarymanagement.*
import com.example.librarymanagement.R
import com.example.librarymanagement.models.book_details
import com.google.firebase.database.*

class HomeFragment : Fragment() {

    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var userArrayList : ArrayList<book_details>
    private lateinit var ProgressBar:ProgressBar
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        ProgressBar = view.findViewById(R.id.progressBar)
        ProgressBar.visibility = View.VISIBLE

        userRecyclerview = view.findViewById(R.id.sortNameRecyclerView)
        userRecyclerview.layoutManager = LinearLayoutManager(requireActivity())
        userRecyclerview.setHasFixedSize(true)

        userArrayList = arrayListOf<book_details>()
        getUserData()

        val cseBookCard = view.findViewById<CardView>(R.id.CSEbook)
        val itBookCard = view.findViewById<CardView>(R.id.ITbook)
        val eceBookCard = view.findViewById<CardView>(R.id.ECEbook)



        cseBookCard.setOnClickListener {
            val intent = Intent(activity, CSEBooksActivity::class.java)
            startActivity(intent)
        }


        itBookCard.setOnClickListener {
            val intent = Intent(activity, ITBooksActivity::class.java)
            startActivity(intent)
        }

        eceBookCard.setOnClickListener {
            val intent = Intent(activity, ECEBooksActivity::class.java)
            startActivity(intent)
        }
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
        ProgressBar.visibility = View.GONE
    }


            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }
}
