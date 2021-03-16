package id.haweje.submission1_githubuser.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Users(
    val username: String = "",
    val name: String = "",
    val company: String = "",
    val location: String = "",
    val avatar: Int = 0,
    val repository: Int = 0,
    val follower: Int = 0,
    val following: Int = 0):Parcelable