package com.example.profileapplication4.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.profileapplication4.R
import com.example.profileapplication4.models.Employer
import com.example.profileapplication4.view.EmployerEmployeeActivity

class FoundEmployersAdapter(val context: Context) :
    RecyclerView.Adapter<FoundEmployersAdapter.ViewHolder>() {

    var employers: MutableList<Employer> = mutableListOf()

    fun updateEmployersList(employers: List<Employer>) {
        this.employers.clear()
        this.employers = employers as MutableList<Employer>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.found_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtSurName.setText(employers[position].name)
        Glide.with(context).asBitmap().load(employers[position].image_url).into(holder.imgView)

        holder.parent.setOnClickListener {
            val intent = Intent(context, EmployerEmployeeActivity::class.java)
            intent.putExtra("user_ref", employers[position].employer_id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return employers.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtSurName: TextView
        val imgView: ImageView
        val parent: CardView

        init {
            txtSurName = view.findViewById(R.id.txtNameSurname)
            parent = view.findViewById(R.id.parent)
            imgView = view.findViewById(R.id.circle_image_view)
        }
    }


}