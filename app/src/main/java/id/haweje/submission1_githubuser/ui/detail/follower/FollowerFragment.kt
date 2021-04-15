package id.haweje.submission1_githubuser.ui.detail.follower

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.haweje.submission1_githubuser.R
import id.haweje.submission1_githubuser.adapter.UserListAdapter
import id.haweje.submission1_githubuser.databinding.FragmentFollowerBinding
import id.haweje.submission1_githubuser.ui.detail.DetailActivity
import id.haweje.submission1_githubuser.viewmodel.UserViewModel

class FollowerFragment : Fragment(R.layout.fragment_follower) {

    private var _binding: FragmentFollowerBinding? = null
    private lateinit var userViewModel : UserViewModel
    private lateinit var followerAdapter: UserListAdapter
    private lateinit var username: String
    private val binding get() = _binding


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = arguments?.getString(DetailActivity.EXTRA_USERNAME).toString()
        _binding = FragmentFollowerBinding.bind(view)

        followerAdapter = UserListAdapter()
        followerAdapter.notifyDataSetChanged()

        binding?.apply {
            rvFollower.layoutManager = LinearLayoutManager(view.context)
            rvFollower.adapter = followerAdapter
            rvFollower.setHasFixedSize(true)
        }

        showLoading(true)
        userViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            UserViewModel::class.java)
        userViewModel.setDetailFollower(username)
        userViewModel.getDetailFollower().observe(viewLifecycleOwner,{
            if (it!=null){
                showLoading(false)
                followerAdapter.setList(it)
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