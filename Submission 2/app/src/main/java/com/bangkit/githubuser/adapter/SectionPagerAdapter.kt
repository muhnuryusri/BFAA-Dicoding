package com.bangkit.githubuser.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bangkit.githubuser.fragment.FollowersFragment
import com.bangkit.githubuser.fragment.FollowingFragment

class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var username: String? = null

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowingFragment.newInstance(username)
            1 -> fragment = FollowersFragment.newInstance(username)
        }
        return fragment as Fragment
    }
    override fun getItemCount(): Int {
        return 2
    }
}