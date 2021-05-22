package com.example.profileapplication4.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.profileapplication4.R
import com.example.profileapplication4.models.ChatRoom
import com.example.profileapplication4.models.Employee
import com.example.profileapplication4.models.Employer
import com.google.firebase.firestore.FirebaseFirestore

class ChatRoomAdapter(val context: Context, val user_ref: String, val userType: String) :
    RecyclerView.Adapter<ChatRoomAdapter.ViewHolder>() {

    var chatRooms: MutableList<ChatRoom> = arrayListOf()

    fun updateChatRooms(chatRooms: List<ChatRoom>) {
        this.chatRooms.clear()
        this.chatRooms = chatRooms as MutableList<ChatRoom>
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.chat_room_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (chatRooms[position].first_creater_id.equals(user_ref)) {
            FirebaseFirestore.getInstance().collection(oppositeUserType())
                .document(chatRooms[position].second_creater_id).get().addOnCompleteListener {
                    if (it.isSuccessful) {
                        if (oppositeUserType().equals("employees")) {
                            val employee = it.result?.toObject(Employee::class.java)
                            holder.txt_user_name.setText(employee?.name)
                            Glide.with(context).asBitmap().load(employee?.image_url)
                                .into(holder.circle_image_view)
                        } else {
                            val employer = it.result?.toObject(Employer::class.java)
                            holder.txt_user_name.setText(employer?.name)
                            Glide.with(context).asBitmap().load(employer?.image_url)
                                .into(holder.circle_image_view)
                        }
                    }
                }
        }



    }

    override fun getItemCount(): Int {
        return chatRooms.size
    }

    private fun oppositeUserType(): String {
        if (userType.equals("employer")) {
            return "employees"
        }
        return "employers"
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var txt_user_name: TextView
        var circle_image_view: ImageView
        var trash_icon: ImageView

        init {
            txt_user_name = view.findViewById(R.id.txtNameSurName)
            circle_image_view = view.findViewById(R.id.circle_image_view)
            trash_icon = view.findViewById(R.id.trash_image_view)
        }

    }
}
