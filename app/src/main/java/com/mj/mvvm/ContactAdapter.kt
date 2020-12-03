package com.mj.mvvm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mj.mvvm.Data.Contact

class ContactAdapter(
    val contactItemClick: (Contact) -> Unit,
    val contactItemLongClickListener: (Contact) -> Unit
) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    private var contacts: List<Contact> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_contact, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(holder: ContactAdapter.ViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val tv_name = itemView.findViewById<TextView>(R.id.item_tv_name)
        private val tv_number = itemView.findViewById<TextView>(R.id.item_tv_number)
        private val tv_init = itemView.findViewById<TextView>(R.id.item_tv_initial)

        fun bind(contact: Contact) {
            tv_name.text = contact.name
            tv_number.text = contact.number
            tv_init.text = contact.initial.toString()

            itemView.setOnClickListener {
                contactItemClick(contact)
            }

            itemView.setOnLongClickListener {
                contactItemLongClickListener(contact)
                true
            }
        }

    }

    fun setContacts(contacts: List<Contact>) {
        this.contacts = contacts
        notifyDataSetChanged()
    }
}