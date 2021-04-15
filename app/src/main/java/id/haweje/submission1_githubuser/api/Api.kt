package id.haweje.submission1_githubuser.api

import id.haweje.submission1_githubuser.data.model.DetailUsers
import id.haweje.submission1_githubuser.data.model.Users
import id.haweje.submission1_githubuser.data.model.UsersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: token 3658a3e645e8348bfec711d0c77ff589958c6572")
    fun getSearchUser(
        @Query("q")query: String
    ):Call<UsersResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 3658a3e645e8348bfec711d0c77ff589958c6572")
    fun getDetailUser(
            @Path("username") username: String
    ): Call<DetailUsers>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 3658a3e645e8348bfec711d0c77ff589958c6572")
    fun getFollowersUser(
            @Path("username")username: String
    ): Call<ArrayList<Users>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 3658a3e645e8348bfec711d0c77ff589958c6572")
    fun getFollowingUser(
            @Path("username")username: String
    ): Call<ArrayList<Users>>
}