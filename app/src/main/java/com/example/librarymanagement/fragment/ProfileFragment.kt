package com.example.librarymanagement.fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.librarymanagement.BorrowedActivity
import com.example.librarymanagement.HistoryActivity
import com.example.librarymanagement.R
import com.example.librarymanagement.ReturnedActivity
import com.example.librarymanagement.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        Glide.with(this)
            .load(auth.currentUser!!.photoUrl)
            .error(R.drawable.book)
            .into(binding.imageView2)

        val mail = auth.currentUser!!.email
        val name  = auth.currentUser!!.displayName

        binding.mailname.text = "$name"
        binding.mailid.text = "$mail"

        binding.cardvid2.setOnClickListener {
            val intent  = Intent(activity,BorrowedActivity::class.java)
            startActivity(intent)
        }
        binding.cardvid3.setOnClickListener {
            val intent  = Intent(activity,ReturnedActivity::class.java)
            startActivity(intent)
        }
        binding.cardvid4.setOnClickListener {
            val intent  = Intent(activity,HistoryActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
