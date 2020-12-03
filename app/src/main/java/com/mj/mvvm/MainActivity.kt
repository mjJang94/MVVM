package com.mj.mvvm

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mj.mvvm.Data.Contact
import com.mj.mvvm.ViewModel.ContactViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var contactViewModel: ContactViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adpater = ContactAdapter({ contact ->

            val intent = Intent(this, AddActivity::class.java)
            intent.putExtra(AddActivity.EXTRA_CONTACT_NAME, contact.name)
            intent.putExtra(AddActivity.EXTRA_CONTACT_NUMBER, contact.number)
            intent.putExtra(AddActivity.EXTRA_CONTACT_ID, contact.id)
            startActivity(intent)

        }, { contact ->
            deleteDialog(contact)
        })

        val layoutManager = LinearLayoutManager(this)
        main_recycleview.adapter = adpater
        main_recycleview.layoutManager = layoutManager
        main_recycleview.setHasFixedSize(true)

        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        contactViewModel.getAll().observe(this, Observer<List<Contact>> { contacts ->
            adpater.setContacts(contacts)
        })

        main_button.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
    }

    private fun deleteDialog(contact: Contact) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Delete selected contact?")
            .setNegativeButton("NO") { _, _ -> }
            .setPositiveButton("YES") { _, _ ->
                contactViewModel.delete(contact)
            }
        builder.show()
    }
}
