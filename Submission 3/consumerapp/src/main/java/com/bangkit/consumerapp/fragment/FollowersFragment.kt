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
import com.bangkit.consumerapp.adapter.FollowersAdapter
import com.bangkit.consumerapp.databinding.FragmentFollowersBinding
import com.bangkit.consumerapp.retrofit.FollowersResponse
import com.bangkit.consumerapp.viewmodel.FollowersViewModel
import kotlinx.android.synthetic.main.fragment_followers.*

class FollowersFragment : Fragment() {

    companion object {
        private const val ARG_USERNAME = "username"

        fun newInstance(username: String?): FollowersFragment {
            val fragment = FollowersFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var adapter: FollowersAdapter
    private lateinit var followersViewModel: FollowersViewModel
    private lateinit var binding: FragmentFollowersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading(true)
        getFollowersViewModel()
        showRecyclerList()
    }

    private fun showRecyclerList() {
        adapter = FollowersAdapter()
        adapter.notifyDataSetChanged()

        binding.rvFollowers.layoutManager = LinearLayoutManager(activity)
        binding.rvFollowers.adapter = adapter
        binding.rvFollowers.setHasFixedSize(true)

        adapter.setOnItemClickCallback(object :
                FollowersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FollowersResponse) {
                showSelectedUser(data)
            }
        })
    }

    private fun getFollowersViewModel() {
        if (arguments != null) {
            val username = arguments?.getString(ARG_USERNAME)
            followersViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel::class.java)

            if (username != null) followersViewModel.setFollowersUser(username)
        }

        followersViewModel.getFollowersUser().observe(viewLifecycleOwner, { userItems ->
            if (userItems != null) {
                adapter.setFollowersData(userItems)
                showLoading(false)
            }
        })
    }

    private fun showSelectedUser(user: FollowersResponse) {
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