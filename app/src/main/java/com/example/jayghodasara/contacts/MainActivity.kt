package com.example.jayghodasara.contacts

import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var list: ArrayList<user> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var permissioncheck: Int = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_CONTACTS)
        var permissioncheck2: Int = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
        if (permissioncheck != PackageManager.PERMISSION_GRANTED && permissioncheck2 != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_CONTACTS, android.Manifest.permission.READ_CONTACTS), 1)
        } else {
            Toast.makeText(applicationContext, " Permission granted", Toast.LENGTH_LONG).show()
        }
        getContacts.setOnClickListener(View.OnClickListener {

            loadContacts()
        })

        addcontact.setOnClickListener(View.OnClickListener {

            var intent: Intent = Intent(this, AddContact::class.java)
            startActivity(intent)
        })


    }


    fun loadContacts() {


        var contentResolver: ContentResolver = contentResolver
        var cursor: Cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        while (cursor.moveToNext()) {
            var id: String = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
            var name: String = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))


            var cursor2: Cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", arrayOf(id), null)

            while (cursor2.moveToNext()) {
                var phoneno: String = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                var userobj: user = user(name, phoneno)
                list.add(userobj)


            }
            cursor2.close()

            var layman: RecyclerView.LayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

            recycle.layoutManager = layman
            recycle.adapter = adapter(this, list)

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {

            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {


                    if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(applicationContext, "All Permissions Granted", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(applicationContext, " Permission Denied", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


}
