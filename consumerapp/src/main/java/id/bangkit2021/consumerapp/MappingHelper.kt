package id.bangkit2021.consumerapp

import android.database.Cursor

object MappingHelper {
    fun mapCursortoArrayList(cursor: Cursor?) : ArrayList<Users>{
        val list = ArrayList<Users>()
        if (cursor != null){
            while (cursor.moveToNext()){
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumns.ID))
                val username = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumns.USERNAME))
                val avatarUrl = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumns.AVATAR_URL))
                list.add(
                    Users(
                        id,
                        username,
                        avatarUrl
                    )
                )
            }
        }
        return list
    }
}