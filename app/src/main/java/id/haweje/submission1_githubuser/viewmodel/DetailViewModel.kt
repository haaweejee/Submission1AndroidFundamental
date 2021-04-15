package id.haweje.submission1_githubuser.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.haweje.submission1_githubuser.api.RetrofitClient
import id.haweje.submission1_githubuser.data.db.FavoriteDatabase
import id.haweje.submission1_githubuser.data.db.FavoriteUser
import id.haweje.submission1_githubuser.data.db.FavoriteUserDao
import id.haweje.submission1_githubuser.data.model.DetailUsers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    companion object{
        const val FAIL = "Failure"
        const val TAG ="Message"
    }

    val detailUsers = MutableLiveData<DetailUsers>()
    private var favDao: FavoriteUserDao?
    private var favDb: FavoriteDatabase? = FavoriteDatabase.getDatabase(application)

    init {
        favDao = favDb?.favoriteUserDao()
    }

    fun setDetailUser(username: String){
        RetrofitClient.apiInstance
            .getDetailUser(username)
            .enqueue(object : Callback<DetailUsers> {
                override fun onResponse(call: Call<DetailUsers>, response: Response<DetailUsers>) {
                    if (response.isSuccessful){
                        detailUsers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUsers>, t: Throwable) {
                    t.message?.let { Log.d(FAIL, it) }
                }
            })
    }

    fun getUserDetail(): LiveData<DetailUsers> {
        return detailUsers

    }


    fun addToFavorite(username: String, id: Int, avatarUrl: String){
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(
                id,
                username,
                avatarUrl
            )
            favDao?.addToFavorite(user)
            Log.d(TAG, user.toString())
        }
    }

    suspend fun checkUser(id: Int) = favDao?.checkUser(id)

    fun removeFromFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            favDao?.removeFromFavorite(id)
            Log.d(TAG, id.toString())
        }
    }

}