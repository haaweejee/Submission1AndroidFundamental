package id.haweje.submission1_githubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import id.haweje.submission1_githubuser.data.Users
import id.haweje.submission1_githubuser.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_USERS = "users"
    }

    private lateinit var binding: ActivityDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportBar()
        getUsersData()

    }

    private fun supportBar(){
        val users = intent.getParcelableExtra<Users>(EXTRA_USERS) as Users
        supportActionBar?.title = users.username
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }



    private fun getUsersData(){
        val users = intent.getParcelableExtra<Users>(EXTRA_USERS) as Users
        binding.usersAvatar.setImageResource(users.avatar)
        binding.usersUsername.text = users.username
        binding.usersName.text = users.name
        binding.usersCompany.text = users.company
        binding.usersLocation.text = users.location
        binding.usersFollowers.text = users.follower.toString()
        binding.usersFollowing.text = users.following.toString()
        binding.usersRepotories.text = users.repository.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detailmenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.btnShare ->{
                val users = intent.getParcelableExtra<Users>(EXTRA_USERS) as Users
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "Follow ${users.username} on Github")
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
}