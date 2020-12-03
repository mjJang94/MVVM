package com.mj.mvvm.Data

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ContactRepository(application: Application) {

    private val TAG = this::class.qualifiedName

    private val contactDatabase = ContactDatabase.getInstance(application)
    private val contactDao: ContactDao = contactDatabase!!.contactDao()
    private val contacts: LiveData<List<Contact>> = contactDao.getAll()

    private val handler = CoroutineExceptionHandler { coroutineContext, exception ->

        Log.e(TAG, "예외발생 : $exception")
    }

    fun getAll(): LiveData<List<Contact>> {
        return contacts
    }


    fun insert(contact: Contact) {

        GlobalScope.launch(Dispatchers.IO + handler) {
            contactDao.insert(contact)
        }
    }

    fun delete(contact: Contact) {
        GlobalScope.launch(Dispatchers.IO + handler) {
            contactDao.delete(contact)
        }
    }

}