package id.haweje.submission1_githubuser.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.haweje.submission1_githubuser.R
import id.haweje.submission1_githubuser.data.model.Users
import id.haweje.submission1_githubuser.databinding.ItemListUsersBinding

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.ListViewHolder>() {

    companion object {
        const val TAG = "Message"
    }
    private var onItemClickCallback: OnItemClickCallback? = null


    fun setOnItemClick (onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }
    inner class ListViewHolder(itemView : View):RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListUsersBinding.bind(itemView)
        internal fun bind(user: Users) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }

            Glide.with(itemView.context)
                .load(user.avatar_url)
                .centerCrop() .placeholder(R.drawable.github)
                .apply(RequestOptions().override(60,60))
                .into(binding.usersAvatar)
            binding.usersUsername.text = user.login
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_list_users, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    private val listUser = ArrayList<Users>()

    fun setList(user: ArrayList<Users>){
        listUser.clear()
        listUser.addAll(user)
        notifyDataSetChanged()
        Log.d(TAG, user.toString())
    }



    interface OnItemClickCallback{
        fun onItemClicked(data: Users)
    }
}

