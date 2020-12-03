package com.mj.mvvm.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.mj.mvvm.Data.Contact
import com.mj.mvvm.Data.ContactRepository

class ContactViewModel(application: Application): AndroidViewModel(application){

    private val repository = ContactRepository(application)
    private val contacts = repository.getAll()

    fun getAll(): LiveData<List<Contact>> {

        return contacts
    }

    fun insert(contact: Contact){
        repository.insert(contact)
    }

    fun delete(contact: Contact){
        repository.delete(contact)
    }
}