package id.haweje.submission1_githubuser.ui.detail

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import id.haweje.submission1_githubuser.R
import id.haweje.submission1_githubuser.adapter.SectionsPagerAdapter
import id.haweje.submission1_githubuser.databinding.ActivityDetailBinding
import id.haweje.submission1_githubuser.viewmodel.DetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.ln
import kotlin.math.pow

class DetailActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_USERNAME = "username"
        const val EXTRA_ID = "id"
        const val EXTRA_AVATAR = "avatar"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.txt_followers,
            R.string.txt_following)
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var userViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportBar()
        showTabLayout()

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID,0)
        val avatar = intent.getStringExtra(EXTRA_AVATAR)

        userViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)


        username?.let { userViewModel.setDetailUser(it) }
        userViewModel.getUserDetail().observe(this, {
            if (it != null){
                binding.usersBio.text = it.bio
                binding.usersName.text = it.name
                binding.usersLocation.text = it.location
                binding.usersCompany.text = it.company
                binding.usersWeb.text = it.blog
                binding.usersRepotories.text = getFormatedNumber(it.public_repos.toLong())
                binding.usersFollowing.text = getFormatedNumber(it.following.toLong())
                binding.usersFollowers.text = getFormatedNumber(it.followers.toLong())
                Glide.with(this)
                        .load(it.avatar_url)
                        .centerCrop()
                        .placeholder(R.drawable.github)
                        .into(binding.usersAvatar)
            }
        })

        var checkedFav = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = userViewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        checkedFav = true
                        binding.btnFav.isChecked = true
                    } else {
                        checkedFav = false
                        binding.btnFav.isChecked = false
                    }
                }
            }
        }

        binding.btnFav.setOnClickListener {
            checkedFav = !checkedFav
            if (checkedFav) {
                if (username != null && avatar != null) {
                    userViewModel.addToFavorite(username,id, avatar)
                }
            }else{
                userViewModel.removeFromFavorite(id)
            }
            binding.btnFav.isChecked = checkedFav
        }
    }


    private fun supportBar(){
        supportActionBar?.apply {
            customView = actionBarsetBold()
            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detailmenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.btnShare ->{
                val username = intent.getStringExtra(EXTRA_USERNAME)
                var htmlUrl = ""
                val shareIntent = Intent().apply {
                    userViewModel.getUserDetail().observe(this@DetailActivity,{
                        if (it!=null){
                            htmlUrl = it.html_url
                        }
                    })
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "Follow $username on Github $htmlUrl")
                    type = "text/plain"
                }
                startActivity(shareIntent)
                true
            }

            else -> super.onOptionsItemSelected(item)

        }
    }

    override fun onSupportNavigateUp():Boolean{
        onBackPressed()
        return true
    }

    private fun showTabLayout(){
        val username = intent.getStringExtra(EXTRA_USERNAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, bundle)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager){ tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun getFormatedNumber(count: Long): String {
        if (count < 1000) return "" + count
        val exp = (ln(count.toDouble()) / ln(1000.0)).toInt()
        return String.format("%.1f %c", count / 1000.0.pow(exp.toDouble()), "kMGTPE"[exp - 1])
    }

    private fun actionBarsetBold() : TextView {
        return TextView(this).apply {
            val username = intent.getStringExtra(EXTRA_USERNAME)
            text = username
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
                setTypeface(null, Typeface.BOLD)
            }
            setTextColor(Color.WHITE)
            typeface = Typeface.SERIF
            setTypeface(typeface, Typeface.BOLD)
        }
    }

}
