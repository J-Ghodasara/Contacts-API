package com.example.jayghodasara.contacts

import android.content.ContentProviderOperation
import android.content.ContentResolver
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_contact.*

class AddContact : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)


        var contentResolver: ContentResolver = contentResolver
        var list: ArrayList<ContentProviderOperation> = ArrayList()

        add_Contact.setOnClickListener(View.OnClickListener {

            var Name: String = name.text.toString()
            var number: String = number.text.toString()

            Log.i("Tag", Name + " " + number)

            list.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, "jayghodasara07@gmail.com")
                    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, "com.google")
                    .build())

            list.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, Name)
                    .build())

            list.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, number)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME)
                    .build())

            try {
                contentResolver.applyBatch(ContactsContract.AUTHORITY, list)
                Toast.makeText(this, "Added", Toast.LENGTH_LONG).show()
                var intent: Intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            } catch (e: Exception) {
                Log.e("Exception", e.toString())
            }
        })

        update.setOnClickListener(View.OnClickListener {

            var Name: String = name.text.toString()
            var number: String = number.text.toString()


            val where = ContactsContract.Data.DISPLAY_NAME + " = ? AND " +
                    ContactsContract.Data.MIMETYPE + " = ? AND " +
                    ContactsContract.CommonDataKinds.Phone.TYPE + " = ? "

            val params = arrayOf<String>(Name, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME.toString())

            list.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                    .withSelection(where, params)
                    .withValue(ContactsContract.CommonDataKinds.Phone.DATA, number)
                    .build())

            try {
                contentResolver.applyBatch(ContactsContract.AUTHORITY, list)
                Toast.makeText(this, "Updated", Toast.LENGTH_LONG).show()
                var intent: Intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            } catch (e: Exception) {
                Log.e("Exception", e.toString())
            }
        })

        delete.setOnClickListener(View.OnClickListener {
            var Name: String = name.text.toString()
            val where = ContactsContract.Data.DISPLAY_NAME + " = ? "
            val params = arrayOf<String>(Name)

            list.add(ContentProviderOperation.newDelete(ContactsContract.RawContacts.CONTENT_URI)
                    .withSelection(where, params)
                    .build())
            try {
                contentResolver.applyBatch(ContactsContract.AUTHORITY, list)
                Toast.makeText(this, "Deleted", Toast.LENGTH_LONG).show()
                var intent: Intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            } catch (e: Exception) {
                Log.e("Exception", e.toString())
            }
        })


    }
}
