package com.example.pekomon.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.pekomon.newsapp.R
import com.example.pekomon.newsapp.ui.MainActivity
import com.example.pekomon.newsapp.ui.NewsViewModel
import com.example.pekomon.newsapp.ui.adapters.NewsAdapter
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
        }
    }

    private fun setupObserver() {
        viewModel.searchNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    showProgressbar(false)
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
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
    }
}
