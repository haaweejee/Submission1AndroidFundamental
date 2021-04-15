package id.haweje.submission1_githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import id.haweje.submission1_githubuser.data.db.FavoriteDatabase
import id.haweje.submission1_githubuser.data.db.FavoriteUser
import id.haweje.submission1_githubuser.data.db.FavoriteUserDao

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private var favDao: FavoriteUserDao?
    private var favDb: FavoriteDatabase? = FavoriteDatabase.getDatabase(application)

    init {
        favDao = favDb?.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>?{
        return favDao?.getFavoriteUser()
    }

}