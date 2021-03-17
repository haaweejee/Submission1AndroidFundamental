package id.haweje.submission1_githubuser

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.haweje.submission1_githubuser.data.Users
import id.haweje.submission1_githubuser.databinding.ItemListUsersBinding

class UserListAdapter(private val listUser: MutableList<Users>) :
    RecyclerView.Adapter<UserListAdapter.ListViewHolder>() {
    inner class ListViewHolder(itemView : View):RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListUsersBinding.bind(itemView)

        internal fun bind(user: Users) {
            Glide.with(itemView.context)
                .load(user.avatar)
                .apply(RequestOptions().override(60,60))
                .into(binding.usersAvatar)
            binding.usersName.text = user.name
            binding.usersUsername.text = user.username
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_list_users, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]
        holder.bind(user)
            holder.itemView.setOnClickListener {
                val moveDetail = Intent(holder.itemView.context, DetailActivity::class.java)
                moveDetail.putExtra(DetailActivity.EXTRA_USERS, user)
                holder.itemView.context.startActivity(moveDetail)
            }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    private val TAG = "Users"
    private var users = listOf<Users>()
    fun searchData(users: List<Users>){
        this.users = users
        Log.d(TAG, users.toString())
        notifyDataSetChanged()
    }
}