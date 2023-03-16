package com.example.librarymanagement.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.librarymanagement.BookRVAdapter
import com.example.librarymanagement.R
import com.example.librarymanagement.models.BookRVModal

class SearchFragment : Fragment() {

    lateinit var mRequestQueue: RequestQueue
    lateinit var booksList: ArrayList<BookRVModal>
    lateinit var loadingPB: ProgressBar
    lateinit var searchEdt: EditText
    lateinit var searchBtn: ImageButton
    lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val animationView = view.findViewById<LottieAnimationView>(R.id.animation_view)
        animationView.setAnimation(R.raw.booksearch)
        animationView.loop(true)
        animationView.playAnimation()


        loadingPB = view.findViewById(R.id.idLoadingPB)
        searchEdt = view.findViewById(R.id.idEdtSearchBooks)
        searchBtn = view.findViewById(R.id.idBtnSearch)
        mRecyclerView = view.findViewById(R.id.idRVBooks)

        searchBtn.setOnClickListener {
            loadingPB.visibility = View.VISIBLE
            if (searchEdt.text.toString().isNullOrEmpty()) {
                searchEdt.setError("Please enter search query")
            }
            getBooksData(searchEdt.getText().toString());
        }

        return view
    }

    private fun getBooksData(searchQuery: String) {
        booksList = ArrayList()

        mRequestQueue = Volley.newRequestQueue(requireContext())

        mRequestQueue.cache.clear()

        val url = "https://www.googleapis.com/books/v1/volumes?q=$searchQuery"


        val queue = Volley.newRequestQueue(requireContext())


        val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            loadingPB.setVisibility(View.GONE);
            try {
                val itemsArray = response.getJSONArray("items")
                for (i in 0 until itemsArray.length()) {
                    val itemsObj = itemsArray.getJSONObject(i)
                    val volumeObj = itemsObj.getJSONObject("volumeInfo")
                    val title = volumeObj.optString("title")
                    val subtitle = volumeObj.optString("subtitle")
                    val authorsArray = volumeObj.getJSONArray("authors")
                    val publisher = volumeObj.optString("publisher")
                    val publishedDate = volumeObj.optString("publishedDate")
                    val description = volumeObj.optString("description")
                    val pageCount = volumeObj.optInt("pageCount")
                    val imageLinks = volumeObj.optJSONObject("imageLinks")
                    val thumbnail = imageLinks!!.optString("thumbnail")
                    val previewLink = volumeObj.optString("previewLink")
                    val infoLink = volumeObj.optString("infoLink")
                    val saleInfoObj = itemsObj.optJSONObject("saleInfo")
                    val buyLink = saleInfoObj!!.optString("buyLink")

                    val authorsArrayList: ArrayList<String> = ArrayList()

                    if (authorsArray.length() != 0) {
                        for (j in 0 until authorsArray.length()) {
                            authorsArrayList.add(authorsArray.optString(i))
                        }
                    }

                    val bookInfo = BookRVModal(title, subtitle, authorsArrayList, publisher, publishedDate, description,
                        pageCount, thumbnail, previewLink, infoLink, buyLink)


                    booksList.add(bookInfo)

                    val adapter = BookRVAdapter(booksList, requireContext())

                    val layoutManager = GridLayoutManager(this.context, 3)

                    mRecyclerView.layoutManager = layoutManager
                    mRecyclerView.adapter = adapter
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }


        }, { error ->
            Toast.makeText(requireContext(), "No books found..", Toast.LENGTH_SHORT).show()
        })

        queue.add(request)
        val animationView = view?.findViewById<LottieAnimationView>(R.id.animation_view)

        animationView!!.visibility = View.GONE

    }

}