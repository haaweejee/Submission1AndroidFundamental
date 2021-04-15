package id.bangkit2021.consumerapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.bangkit2021.consumerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var favAdapter : UserListAdapter
    private lateinit var favoriteViewModel : FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        favAdapter = UserListAdapter()
        favAdapter.notifyDataSetChanged()
        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        binding.apply {
            rvFav.setHasFixedSize(true)
            rvFav.layoutManager = LinearLayoutManager(this@MainActivity)
            rvFav.adapter = favAdapter
        }

        favoriteViewModel.setFavoriteUser(this)

        favoriteViewModel.getFavoriteUser().observe(this, {
            if (it != null){
                favAdapter.setList(it)
            }
        })
    }
}