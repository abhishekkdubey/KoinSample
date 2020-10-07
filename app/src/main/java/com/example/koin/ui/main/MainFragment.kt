package com.example.koin.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koin.R
import com.example.koin.db.User
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter
    private var userList: List<User> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        return inflater.inflate(R.layout.main_fragment, container, false)
    }


    override fun onResume() {
        super.onResume()
        shimmerFrameLayout.startShimmer()
    }

    override fun onPause() {
        shimmerFrameLayout.stopShimmer()
        super.onPause()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = UserAdapter(userList)
        listView.adapter = adapter
        viewModel.getUsers(false).observe(viewLifecycleOwner, Observer {
            when {
                it.startsWith("Error:", true) -> {
                    shimmerFrameLayout.startShimmer()
                    shimmerFrameLayout.visibility = View.GONE
                    listView.visibility = View.GONE
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.userLiveData.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                shimmerFrameLayout.startShimmer()
                shimmerFrameLayout.visibility = View.GONE
                listView.visibility = View.VISIBLE
                userList = it
                adapter.setData(userList)
                adapter.notifyDataSetChanged()
            }
        })

        listView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = listView.layoutManager as LinearLayoutManager
                if (layoutManager.findLastVisibleItemPosition() == userList.size - 1) {
                    loadNextUserList()
                }
            }
        })

    }

    private fun loadNextUserList() {
        if (viewModel.hasMoreUsers()) {
            viewModel.getUsers(true)
        }

    }

}
