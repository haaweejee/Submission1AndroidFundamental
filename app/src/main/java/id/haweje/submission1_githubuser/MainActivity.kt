package id.haweje.submission1_githubuser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import id.haweje.submission1_githubuser.data.Users
import id.haweje.submission1_githubuser.databinding.ActivityMainBinding
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private var listUser: ArrayList<Users> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.title = "People"

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        showList()
        readJson()

    }


    private fun showList(){
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = UserListAdapter(listUser)
        binding.rvUsers.setHasFixedSize(true)
    }
    private fun readJson(){
        val json: String?
        try {
            json = assets.open("githubuser.json").bufferedReader().use { it.readText() }

            val objectUser = JSONObject(json)
            val users = objectUser.getJSONArray("users")

            for (i in 0 until users.length()){
              with(users.getJSONObject(i)){
                  listUser.add(
                      Users(
                          name = getString("name"),
                          username = getString("username"),
                          avatar = resources.getIdentifier(getString("avatar"), null, packageName),
                          company = getString("company"),
                          location = getString("location"),
                          follower = getInt("follower"),
                          repository = getInt("repository"),
                          following = getInt("following")
                      )
                  )
              }
            }

        }catch (e: IOException){
            e.printStackTrace()
        }

    }
}