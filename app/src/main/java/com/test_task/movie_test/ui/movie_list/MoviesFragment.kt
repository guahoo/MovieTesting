package com.test_task.movie_test.ui.movie_list

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.test_task.movie_test.R
import com.test_task.movie_test.extensions.gone
import com.test_task.movie_test.extensions.show
import com.test_task.movie_test.ui.movie_list.list_items.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_review.*

import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private val viewModel: MovieViewModel by viewModels()
    private val movieAdapter = MovieAdapter()


    private val refreshListener = SwipeRefreshLayout.OnRefreshListener {
        viewModel.isRefreshing.postValue(true)
        //test_comment feature

        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.isRefreshing.postValue(false)
        }, 1000)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_review, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        lifecycleScope.launch {
            viewModel.getMovieList().observe(viewLifecycleOwner) {

                it?.let { pagingData ->
                    movieAdapter.submitData(lifecycle, pagingData)
                }
            }
        }


        rw_rest.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }



        viewModel.needShowErrorScreen.observe(viewLifecycleOwner) {
            if (it) error_layout.show() else error_layout.gone()
        }

        swiperefreshlayout.setOnRefreshListener(refreshListener)

        movieAdapter.addLoadStateListener { loadState ->
            // show empty list
            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading
            )
                progressDialog.isVisible = true
            else {
                progressDialog.isVisible = false
                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    Toast.makeText(requireContext(), it.error.toString(), Toast.LENGTH_LONG).show()
                }

            }
        }
        viewModel.isRefreshing.observe(viewLifecycleOwner) {
            swiperefreshlayout.isRefreshing = it
        }

    }

    override fun onResume() {

        viewModel.getMovieList().observe(viewLifecycleOwner) { pagingData ->
            pagingData.map {
                Log.v("Чарактер", "${it.display_title}")
            }
        }


        lifecycleScope.launch {
            viewModel.getMovieList().observe(requireActivity()) {

                it?.let { pagingData ->
                    movieAdapter.submitData(lifecycle, pagingData)
                }
            }
        }
        super.onResume()
    }


}