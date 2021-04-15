package id.haweje.submission1_githubuser.ui.detail.following

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.haweje.submission1_githubuser.R
import id.haweje.submission1_githubuser.adapter.UserListAdapter
import id.haweje.submission1_githubuser.databinding.FragmentFollowingBinding
import id.haweje.submission1_githubuser.ui.detail.DetailActivity
import id.haweje.submission1_githubuser.viewmodel.UserViewModel


open class FollowingFragment : Fragment(R.layout.fragment_following) {
    private var _binding: FragmentFollowingBinding? = null
    private lateinit var userViewModel : UserViewModel
    private lateinit var followingAdapter: UserListAdapter
    private lateinit var username: String
    private val binding get() = _binding


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = arguments?.getString(DetailActivity.EXTRA_USERNAME).toString()
        _binding = FragmentFollowingBinding.bind(view)

        followingAdapter = UserListAdapter()
        followingAdapter.notifyDataSetChanged()

        binding?.apply {
            rvFollowing.layoutManager = LinearLayoutManager(view.context)
            rvFollowing.adapter = followingAdapter
            rvFollowing.setHasFixedSize(true)
        }

        showLoading(true)
        userViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            UserViewModel::class.java)
        userViewModel.setDetailFollowing(username)
        userViewModel.getDetailFollowing().observe(viewLifecycleOwner,{
            if (it!=null){
                showLoading(false)
                followingAdapter.setList(it)
        }
    })

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.progressBar?.visibility = View.GONE
        }
    }

}