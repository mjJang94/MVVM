package com.mj.mvvm

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mj.mvvm.API.RetrofitConnection
import com.mj.mvvm.ContactData.Contact
import com.mj.mvvm.ViewModel.ContactViewModel
import com.mj.mvvm.Vo.DataVo
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), View.OnClickListener {

    //view modle
    private lateinit var contactViewModel: ContactViewModel

    //koin, inject Dependency
    private val api: RetrofitConnection by inject()

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



        getJsonData()

    }


    private fun getJsonData() {
        api.getNumber().enqueue(object : Callback<DataVo?> {

            override fun onResponse(call: Call<DataVo?>, response: Response<DataVo?>) {
                if (response.isSuccessful) {
                    val result = response.body() as DataVo
                    textview1.text = result.name
                    textview2.text = result.age
                } else {
                    Toast.makeText(this@MainActivity, "fail", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DataVo?>, t: Throwable) {
                Toast.makeText(this@MainActivity, "fail", Toast.LENGTH_SHORT).show()
            }
        })
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
