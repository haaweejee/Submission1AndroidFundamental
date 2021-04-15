package id.haweje.submission1_githubuser.ui.favorite

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.haweje.submission1_githubuser.R
import id.haweje.submission1_githubuser.adapter.UserListAdapter
import id.haweje.submission1_githubuser.data.db.FavoriteUser
import id.haweje.submission1_githubuser.data.model.Users
import id.haweje.submission1_githubuser.databinding.ActivityFavoriteBinding
import id.haweje.submission1_githubuser.ui.detail.DetailActivity
import id.haweje.submission1_githubuser.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favAdapter : UserListAdapter
    private lateinit var favoriteViewModel : FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            customView = actionBarsetBold()
            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        favAdapter = UserListAdapter()
        favAdapter.notifyDataSetChanged()

        favAdapter.setOnItemClick(object : UserListAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Users) {
                Intent(this@FavoriteActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailActivity.EXTRA_AVATAR, data.avatar_url)
                    startActivity(it)
                }
            }
        })

        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)


        binding.apply {
            rvFav.setHasFixedSize(true)
            rvFav.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFav.adapter = favAdapter
        }

        favoriteViewModel.getFavoriteUser()?.observe(this, {
            if (it != null){
                binding.rvFav.visibility = View.VISIBLE
                binding.frameLayoutMain.visibility = View.GONE
                val list = mapList(it)
                favAdapter.setList(list)

                if (favAdapter.itemCount == 0){
                    binding.frameLayoutMain.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun mapList(users: List<FavoriteUser>) : ArrayList<Users> {
        val listUsers = ArrayList<Users>()
        for(user in users){
            val userMapped = Users(
                user.id,
                user.login,
                user.avatar_url
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }

    private fun actionBarsetBold() : TextView {
        return TextView(this).apply {
            text = getString(R.string.favorite)

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
            } else {
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 17F)
                setTypeface(null, Typeface.BOLD)
            }

            setTextColor(Color.WHITE)

            typeface = Typeface.SERIF


            setTypeface(typeface, Typeface.BOLD)
        }
    }

    override fun onSupportNavigateUp():Boolean{
        onBackPressed()
        return true
    }
}