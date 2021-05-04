package com.bangkit.consumerapp.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.consumerapp.DetailActivity
import com.bangkit.consumerapp.adapter.FollowingAdapter
import com.bangkit.consumerapp.databinding.FragmentFollowingBinding
import com.bangkit.consumerapp.retrofit.FollowingResponse
import com.bangkit.consumerapp.viewmodel.FollowingViewModel
import kotlinx.android.synthetic.main.fragment_following.*

class FollowingFragment : Fragment() {
    companion object {
        private const val ARG_USERNAME = "username"

        fun newInstance(username: String?): FollowingFragment {
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var adapter: FollowingAdapter
    private lateinit var followingViewModel: FollowingViewModel
    private lateinit var binding: FragmentFollowingBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading(true)
        getFollowingViewModel()
        showRecyclerList()
    }

    private fun showRecyclerList() {
        adapter = FollowingAdapter()
        adapter.notifyDataSetChanged()

        binding.rvFollowing.layoutManager = LinearLayoutManager(activity)
        binding.rvFollowing.adapter = adapter
        binding.rvFollowing.setHasFixedSize(true)

        adapter.setOnItemClickCallback(object :
                FollowingAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FollowingResponse) {
                showSelectedUser(data)
            }
        })
    }

    private fun getFollowingViewModel() {
        if (arguments != null) {
            val username = arguments?.getString(ARG_USERNAME)
            followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)

            if (username != null) followingViewModel.setFollowingUser(username)
        }

        followingViewModel.getFollowingUser().observe(viewLifecycleOwner, { userItems ->
            if (userItems != null) {
                adapter.setFollowingData(userItems)
                showLoading(false)
            }
        })
    }

    private fun showSelectedUser(user: FollowingResponse) {
        val moveIntent = Intent(this.activity, DetailActivity::class.java)
        moveIntent.putExtra(DetailActivity.EXTRA_USER, user.username)
        startActivity(moveIntent)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            shimmerView.startShimmer()
            shimmerView.visibility = View.VISIBLE
        } else {
            shimmerView.stopShimmer()
            shimmerView.visibility = View.GONE
        }
    }
}