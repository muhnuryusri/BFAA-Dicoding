package com.bangkit.githubuser.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.githubuser.DetailActivity
import com.bangkit.githubuser.User
import com.bangkit.githubuser.adapter.UserAdapter
import com.bangkit.githubuser.databinding.FragmentFollowingBinding
import com.bangkit.githubuser.viewmodel.FollowingViewModel
import kotlinx.android.synthetic.main.activity_main.*

class FollowingFragment : Fragment() {
    private lateinit var adapter: UserAdapter
    private lateinit var followingViewModel: FollowingViewModel
    private lateinit var binding: FragmentFollowingBinding

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
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.rvFollowing.layoutManager = LinearLayoutManager(activity)
        binding.rvFollowing.adapter = adapter
        binding.rvFollowing.setHasFixedSize(true)

        adapter.setOnItemClickCallback(object :
                UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
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

        followingViewModel.getFollowingUsers().observe(viewLifecycleOwner, { userItems ->
            if (userItems != null) {
                adapter.setData(userItems)
                showLoading(false)
            }
        })
    }

    private fun showSelectedUser(user: User) {
        val moveIntent = Intent(this.activity, DetailActivity::class.java)
        moveIntent.putExtra(DetailActivity.EXTRA_USER, user)
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