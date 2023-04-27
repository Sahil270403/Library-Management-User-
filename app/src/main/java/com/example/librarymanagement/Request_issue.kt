package com.example.librarymanagement

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.librarymanagement.databinding.ActivityRequestIssueBinding
import com.example.librarymanagement.models.issue_details
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue

class Request_issue : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRequestIssueBinding
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityRequestIssueBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bookName = intent.getStringExtra("BookName")
        val authorName = intent.getStringExtra("AuthorName")
        val branch = intent.getStringExtra("Branch")
        val description = intent.getStringExtra("Description")


        binding.registerBtn.setOnClickListener {

            val userName = binding.userName.text.toString().trim()
            val rollNo = binding.rollNo.text.toString().trim()
            val sBranch = binding.branchSpinner.selectedItem.toString().trim()
            val currentDate = HashMap<String, Any>()
            currentDate["date"] = ServerValue.TIMESTAMP

            auth = FirebaseAuth.getInstance()

            val uid = auth.currentUser!!.uid

            if(bookName!!.isEmpty() || authorName!!.isEmpty() || branch!!.isEmpty() || userName.isEmpty() || rollNo.isEmpty() || sBranch.isEmpty()){
                Toast.makeText(this,"Empty Input",Toast.LENGTH_SHORT).show()
            }
            else {
                database = FirebaseDatabase.getInstance().getReference("Issue_details_request")

                val issue_details = issue_details(bookName ,authorName,branch,userName,rollNo,sBranch,currentDate,uid)

                database.child(userName).setValue(issue_details).addOnSuccessListener {


                    Toast.makeText(this,"Request Sent", Toast.LENGTH_SHORT).show()
                    finish()

                }.addOnFailureListener{

                    Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()
                }            }



        }

    }
}

