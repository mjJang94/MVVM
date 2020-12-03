package com.mj.mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.mj.mvvm.Data.Contact
import com.mj.mvvm.ViewModel.ContactViewModel
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity() {

    private lateinit var contactViewModel: ContactViewModel
    private var id: Long? = null

    companion object {
        const val EXTRA_CONTACT_NAME = "EXTRA_CONTACT_NAME"
        const val EXTRA_CONTACT_NUMBER = "EXTRA_CONTACT_NUMBER"
        const val EXTRA_CONTACT_ID = "EXTRA_CONTACT_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)

        if (intent != null && intent.hasExtra(EXTRA_CONTACT_NAME) && intent.hasExtra(
                EXTRA_CONTACT_NUMBER
            ) && intent.hasExtra(EXTRA_CONTACT_ID)
        ) {

            add_edittext_name.setText(intent.getStringExtra(EXTRA_CONTACT_NAME))
            add_edittext_number.setText(intent.getStringExtra(EXTRA_CONTACT_NUMBER))
            id = intent.getLongExtra(EXTRA_CONTACT_ID, -1)
        }

        add_button.setOnClickListener {
            val name = add_edittext_name.text.toString().trim()
            val number = add_edittext_number.text.toString()

            if (name.isEmpty() || number.isEmpty()) {
                Toast.makeText(this, "정보를 입력해주세요", Toast.LENGTH_SHORT).show()

            } else {
                val initial = name[0].toUpperCase()
                val contact = Contact(id, name, number, initial)
                contactViewModel.insert(contact)
                finish()
            }
        }
    }
}