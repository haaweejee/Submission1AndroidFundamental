package id.bangkit2021.consumerapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Users(
    val id : Int,
    val login: String,
    val avatar_url : String
    ):Parcelable