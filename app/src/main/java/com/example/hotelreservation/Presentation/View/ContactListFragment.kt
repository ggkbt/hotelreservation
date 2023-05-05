package com.example.hotelreservation.Presentation.View

import android.Manifest.permission.READ_CONTACTS
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotelreservation.Presentation.Repository.Model.Contact
import com.example.hotelreservation.Presentation.View.Adapters.ContactListAdapter
import com.example.hotelreservation.databinding.ContactListFragmentBinding

class ContactListFragment : Fragment() {

    private lateinit var binding: ContactListFragmentBinding
    private lateinit var adapter: ContactListAdapter

    companion object {
        private const val REQUEST_CODE_READ_CONTACTS = 123
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ContactListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(READ_CONTACTS),
                REQUEST_CODE_READ_CONTACTS
            )
        } else {
            setUpRecyclerView()
        }
    }

    private fun getContacts(): List<Contact> {
        val contacts = mutableListOf<Contact>()
        val cursor = requireActivity().contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME + " ASC"
        )
        cursor?.let {
            while (it.moveToNext()) {
                var name = ""
                var phone: String? = null
                var email: String? = null
                val idIndex = it.getColumnIndex(ContactsContract.Contacts._ID)
                val nameIndex = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                if (idIndex >= 0) {
                    val contactId = it.getString(idIndex)
                    val phoneCursor = requireActivity().contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        arrayOf(contactId),
                        null
                    )
                    val emailCursor = requireActivity().contentResolver.query(
                        ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                        arrayOf(contactId),
                        null
                    )
                    val phoneIndex =
                        phoneCursor?.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    val emailIndex =
                        emailCursor?.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)
                    if (nameIndex >= 0) {
                        name = it.getString(nameIndex)
                    }
                    if (phoneIndex != null && phoneCursor.moveToFirst()) {
                        phone = phoneCursor.getString(phoneIndex)
                    }
                    if (emailIndex != null && emailCursor.moveToFirst()) {
                        email = emailCursor.getString(emailIndex)
                    }
                    phoneCursor?.close()
                    emailCursor?.close()
                }
                contacts.add(Contact(name, phone, email))
            }
            it.close()
        }
        return contacts


    }


    private fun setUpRecyclerView() {
        adapter = ContactListAdapter(getContacts())
        binding.contactList.adapter = adapter
        binding.contactList.layoutManager = LinearLayoutManager(context)
        adapter.setOnItemClickListener { contact ->
            val navController = findNavController()
            navController.previousBackStackEntry?.savedStateHandle?.set("contactFromProvider", contact)
            navController.popBackStack()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_READ_CONTACTS) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setUpRecyclerView()
            } else {
                Toast.makeText(
                    context,
                    "Разрешение к контактам не предоставлено",
                    Toast.LENGTH_SHORT
                ).show()
                parentFragmentManager.popBackStack()
            }
        }
    }


}
