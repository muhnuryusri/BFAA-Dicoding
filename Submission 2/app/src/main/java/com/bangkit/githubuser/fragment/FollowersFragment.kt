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
import com.bangkit.githubuser.databinding.FragmentFollowersBinding
import com.bangkit.githubuser.viewmodel.FollowersViewModel
import kotlinx.android.synthetic.main.activity_main.*

class FollowersFragment : Fragment() {
    private lateinit var adapter: UserAdapter
    private lateinit var followersViewModel: FollowersViewModel
    private lateinit var binding: FragmentFollowersBinding

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
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.rvFollowers.layoutManager = LinearLayoutManager(activity)
        binding.rvFollowers.adapter = adapter
        binding.rvFollowers.setHasFixedSize(true)

        adapter.setOnItemClickCallback(object :
                UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
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

        followersViewModel.getFollowersUsers().observe(viewLifecycleOwner, { userItems ->
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