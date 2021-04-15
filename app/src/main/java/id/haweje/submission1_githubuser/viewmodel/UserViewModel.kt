package id.haweje.submission1_githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.haweje.submission1_githubuser.api.RetrofitClient
import id.haweje.submission1_githubuser.data.model.Users
import id.haweje.submission1_githubuser.data.model.UsersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel: ViewModel() {

    companion object{
        const val RESPONSE = "response code"
        const val FAIL = "Failure"
    }

    val listUsers = MutableLiveData<ArrayList<Users>>()
    val followingUsers = MutableLiveData<ArrayList<Users>>()
    val followersUser = MutableLiveData<ArrayList<Users>>()


    fun setSearchUser(query: String){
        RetrofitClient.apiInstance
            .getSearchUser(query)
            .enqueue(object : Callback<UsersResponse>{
                override fun onResponse(
                    call: Call<UsersResponse>,
                    response: Response<UsersResponse>
                ) {
                    if (response.isSuccessful){
                        listUsers.postValue(response.body()?.items)
                        Log.d(RESPONSE, response.code().toString())
                    }
                }

                override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                    t.message?.let { Log.d(FAIL, it) }
                }
            })
    }

    fun getSearchUser(): LiveData<ArrayList<Users>>{
        return listUsers
    }


    fun setDetailFollowing(username: String){
        RetrofitClient.apiInstance
                .getFollowingUser(username)
                .enqueue(object : Callback<ArrayList<Users>>{
                    override fun onResponse(call: Call<ArrayList<Users>>, response: Response<ArrayList<Users>>) {
                        followingUsers.postValue(response.body())
                        Log.d(RESPONSE, response.code().toString())
                    }

                    override fun onFailure(call: Call<ArrayList<Users>>, t: Throwable) {
                        t.message?.let { Log.d(FAIL, it) }
                    }
                })
    }

    fun getDetailFollowing() : LiveData<ArrayList<Users>>{
        return followingUsers
    }

    fun setDetailFollower(username: String){
        RetrofitClient.apiInstance
                .getFollowersUser(username)
                .enqueue(object : Callback<ArrayList<Users>>{
                    override fun onResponse(call: Call<ArrayList<Users>>, response: Response<ArrayList<Users>>) {
                        followersUser.postValue(response.body())
                        Log.d(RESPONSE, response.code().toString())
                    }

                    override fun onFailure(call: Call<ArrayList<Users>>, t: Throwable) {
                        t.message?.let { Log.d(FAIL, it) }
                    }
                })
    }

    fun getDetailFollower() : LiveData<ArrayList<Users>>{
        return followersUser
    }
}