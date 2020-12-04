package com.mj.mvvm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mj.mvvm.ContactData.Contact
import com.mj.mvvm.ViewModel.APIViewModel
import com.mj.mvvm.ViewModel.ContactViewModel
import com.mj.mvvm.Vo.DataVo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = this.javaClass.simpleName

    //내부 db viewModel
    private lateinit var contactViewModel: ContactViewModel

    //api viewModel
    private lateinit var externalViewModel: APIViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initLayout()

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


        /**
         * room
         */
        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        contactViewModel.getAll().observe(this, Observer<List<Contact>> { contacts ->
            adpater.setContacts(contacts)
        })

        /**
         * api
         */
        val androidViewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        externalViewModel = ViewModelProvider(this, androidViewModelFactory).get(APIViewModel::class.java)
        externalViewModel.exampleData.observe(this, Observer<DataVo> { it ->
            Log.v(TAG, "=====${it.name} ${it.age}")
            textview1.text = it.name
            textview2.text = it.age
        })
        externalViewModel.requestData()

    }

    private fun initLayout() {
        main_button.setOnClickListener(this)
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

    override fun onClick(view: View?) {
        when (view?.id) {

            R.id.main_button -> {
                val intent = Intent(this, AddActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
