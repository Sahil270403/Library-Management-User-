package com.example.librarymanagement

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.librarymanagement.databinding.ActivityRequestIssueBinding
import com.example.librarymanagement.models.issue_details
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue

//class Request_issue : AppCompatActivity() {
//    private lateinit var binding: ActivityRequestIssueBinding
//    private lateinit var database : DatabaseReference
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding =ActivityRequestIssueBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val bookName = intent.getStringExtra("BookName")
//        val authorName = intent.getStringExtra("AuthorName")
//        val branch = intent.getStringExtra("Branch")
//        val description = intent.getStringExtra("Description")
//
//
//        binding.registerBtn.setOnClickListener {
//
//            val userName = binding.userName.text.toString()
//            val rollNo = binding.rollNo.text.toString()
//            val sBranch = binding.branch.text.toString()
//
//            database = FirebaseDatabase.getInstance().getReference("Issue_details")
//
//            val issue_details = issue_details(bookName ,authorName,branch,userName,rollNo,sBranch)
//
//            database.child(userName).setValue(issue_details).addOnSuccessListener {
//
//
//                Toast.makeText(this,"Request Sent", Toast.LENGTH_SHORT).show()
//                finish()
//
//            }.addOnFailureListener{
//
//                Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//    }
//}
class Request_issue : AppCompatActivity() {
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

            val userName = binding.userName.text.toString()
            val rollNo = binding.rollNo.text.toString()
            val sBranch = binding.branch.text.toString()
            val currentDate = HashMap<String, Any>()
            currentDate["date"] = ServerValue.TIMESTAMP
            database = FirebaseDatabase.getInstance().getReference("Issue_details")

            val issue_details = issue_details(bookName ,authorName,branch,userName,rollNo,sBranch,currentDate)


//            issue_details.date = currentDate

            database.child(userName).setValue(issue_details).addOnSuccessListener {


                Toast.makeText(this,"Request Sent", Toast.LENGTH_SHORT).show()
                finish()

            }.addOnFailureListener{

                Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()
            }
        }

    }
}