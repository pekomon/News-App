package com.example.pekomon.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.pekomon.newsapp.R
import com.example.pekomon.newsapp.ui.MainActivity
import com.example.pekomon.newsapp.ui.NewsViewModel
import com.example.pekomon.newsapp.ui.adapters.NewsAdapter
import com.example.pekomon.newsapp.util.Consts
import com.example.pekomon.newsapp.util.Consts.SEARCH_DELAY_MS
import com.example.pekomon.newsapp.util.Resource
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private val TAG = "SearchFragment"

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Consts.QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage
                    && isAtLastItem
                    && isNotAtBeginning
                    && isTotalMoreThanVisible
                    && isScrolling
            if (shouldPaginate) {
                viewModel.searchNews(searchEditText.text.toString())
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        setupRecyclerView()
        setupClickListener()
        setupObserver()

        var job: Job? = null
        searchEditText.addTextChangedListener {editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_DELAY_MS)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.searchNews(editable.toString())
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        searchNewsRecyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@SearchFragment.scrollListener)
        }
    }

    private fun setupObserver() {
        viewModel.searchNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    showProgressbar(false)
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles.toList())
                        val totalPages = newsResponse.totalResults / Consts.QUERY_PAGE_SIZE + 2  // Rounding + last page is empty
                        isLastPage = viewModel.searchNewsPage == totalPages
                        if (isLastPage) {
                            searchNewsRecyclerView.setPadding(0,0,0,0)
                        }
                    }
                }
                is Resource.Error -> {
                    showProgressbar(false)
                    response.message?.let { msg ->
                        Log.e(TAG, "Failed: $msg")
                    }
                }
                is Resource.Loading -> {
                    showProgressbar(true)
                }
            }
        })
    }

    private fun setupClickListener() {
        newsAdapter.setClickListener { article ->
            val bundle = Bundle().apply {
                putSerializable("article", article)
            }
            findNavController().navigate(R.id.action_searchFragment_to_articleFragment, bundle)
        }
    }

    private fun showProgressbar(show: Boolean) {
        searchProgressBar.visibility = if (show) View.VISIBLE else View.INVISIBLE
        isLoading = show
    }
}
