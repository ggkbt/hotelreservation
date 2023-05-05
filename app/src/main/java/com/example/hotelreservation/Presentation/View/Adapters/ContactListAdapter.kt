package com.example.hotelreservation.Presentation.View.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelreservation.Presentation.Repository.Model.Contact
import com.example.hotelreservation.databinding.ContactListElementBinding

class ContactListAdapter(private val contacts: List<Contact>) :
    RecyclerView.Adapter<ContactListAdapter.ContactViewHolder>() {

    private var listener: ((Contact) -> Unit)? = null

    inner class ContactViewHolder(private val binding: ContactListElementBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(contact: Contact) {
                with(binding) {
                    contactName.text = contact.name
                    contactPhone.text = contact.phoneNumber ?: "Номер телефона отсутсвует"
                    contactEmail.text = contact.email ?: "Email отсутствует"
                    root.setOnClickListener {
                        listener?.invoke(contact)
                    }
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding =
            ContactListElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    fun setOnItemClickListener(listener: (Contact) -> Unit) {
        this.listener = listener
    }
}


