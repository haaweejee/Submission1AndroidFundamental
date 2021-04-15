package id.haweje.submission1_githubuser.ui.home

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBar.DISPLAY_SHOW_CUSTOM
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.haweje.submission1_githubuser.R
import id.haweje.submission1_githubuser.adapter.UserListAdapter
import id.haweje.submission1_githubuser.data.model.Users
import id.haweje.submission1_githubuser.databinding.ActivityMainBinding
import id.haweje.submission1_githubuser.ui.detail.DetailActivity
import id.haweje.submission1_githubuser.ui.favorite.FavoriteActivity
import id.haweje.submission1_githubuser.ui.settings.SettingsActivity
import id.haweje.submission1_githubuser.viewmodel.UserViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var searchViewModel : UserViewModel
    private lateinit var listAdapter : UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            customView = actionBarsetBold()
            displayOptions = DISPLAY_SHOW_CUSTOM
            setDisplayShowHomeEnabled(true)
        }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        listAdapter = UserListAdapter()
        listAdapter.notifyDataSetChanged()
        listAdapter.setOnItemClick(object : UserListAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Users) {
                Intent(this@MainActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailActivity.EXTRA_AVATAR, data.avatar_url)
                    startActivity(it)
                }
            }
        })

        searchViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            UserViewModel::class.java)


        binding.apply {
            rvUsers.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUsers.adapter = listAdapter

            etQuery.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null && query.isNotEmpty()) {
                        searchUser()
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }

        searchViewModel.getSearchUser().observe(this,{
            if (it != null) {
                showLoading(false)
                listAdapter.setList(it)
                binding.frameLayoutMain.visibility = View.GONE
                binding.rvUsers.visibility = View.VISIBLE

                if (listAdapter.itemCount == 0){
                    binding.frameLayoutMain.visibility = View.VISIBLE
                    binding.imageLogo.setImageResource(R.drawable.octocat)
                    binding.welcomeTitle.text = getString(R.string.data_not_found)
                    binding.welcomeDesc.text = getString(R.string.go_search_other_user)
                }
            }
        })
    }

    private fun searchUser(){
        binding.apply {
            val query = etQuery.query.toString()
            if (query.isEmpty()) return
            binding.frameLayoutMain.visibility = View.GONE
            binding.rvUsers.visibility = View.GONE
            showLoading(true)
            searchViewModel.setSearchUser(query)

        }
    }


    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }




    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.homemenu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settingsPage -> {
                intent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intent)
            }
            R.id.favoritePage -> {
                intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun actionBarsetBold() : TextView{
        return TextView(this).apply {
            text = getString(R.string.people)

            val params = ActionBar.LayoutParams(
                    ActionBar.LayoutParams.WRAP_CONTENT,
                    ActionBar.LayoutParams.WRAP_CONTENT
            )
            layoutParams = params

            params.gravity = Gravity.START

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setTextAppearance(
                        android.R.style.TextAppearance_Material_Widget_ActionBar_Title
                )
            }else{
                setTextSize(TypedValue.COMPLEX_UNIT_SP,17F)
                setTypeface(null,Typeface.BOLD)
            }

            setTextColor(Color.WHITE)

            typeface = Typeface.SERIF


            setTypeface(typeface,Typeface.BOLD)
        }
    }


}