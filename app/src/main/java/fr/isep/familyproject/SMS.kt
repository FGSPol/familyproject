package fr.isep.familyproject

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.telephony.SmsManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import fr.isep.familyproject.R

class SMS : AppCompatActivity() {

    private lateinit var smsEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sms_screen)

        smsEditText = findViewById(R.id.smsEditText)

        val sendToPaulButton: Button = findViewById(R.id.buttonPaul)
        val sendToLuanButton: Button = findViewById(R.id.buttonLuan)
        val sendToSonButton: Button = findViewById(R.id.buttonSon)
        val sendToDaughterButton: Button = findViewById(R.id.buttonDaughter)

        sendToPaulButton.setOnClickListener {
            sendSmsToContact("Paul")
        }

        sendToLuanButton.setOnClickListener {
            sendSmsToContact("Luan")
        }

        sendToSonButton.setOnClickListener {
            sendSmsToContact("Son")
        }

        sendToDaughterButton.setOnClickListener {
            sendSmsToContact("Daughter")
        }
    }

    private fun sendSmsToContact(contactName: String) {
        // Check if the READ_CONTACTS permission is not granted
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request the permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS),
                MY_PERMISSIONS_REQUEST_READ_CONTACTS
            )
        } else {
            // Check if the SEND_SMS permission is not granted
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.SEND_SMS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Request the permission
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.SEND_SMS),
                    MY_PERMISSIONS_REQUEST_SEND_SMS
                )
            } else {
                // Permissions already granted, proceed with contacts access and sending SMS
                val phoneNumber = getPhoneNumberForContact(contactName)
                if (phoneNumber != null) {
                    val message = smsEditText.text.toString()
                    sendMessage(phoneNumber, message)
                }
            }
        }
    }

    private fun sendMessage(phoneNumber: String, message: String) {
        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(phoneNumber, null, message, null, null)
        Toast.makeText(this, "SMS sent to $phoneNumber", Toast.LENGTH_SHORT).show()
    }

    private fun getPhoneNumberForContact(contactName: String): String? {
        val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER)
        val selection = "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} = ?"
        val selectionArgs = arrayOf(contactName)

        contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            null
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                val columnIndex =
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                return cursor.getString(columnIndex)
            }
        }
        return null
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_CONTACTS -> {
                handleReadContactsPermissionResult()
            }
            MY_PERMISSIONS_REQUEST_SEND_SMS -> {
                handleSendSmsPermissionResult()
            }
        }
    }

    private fun handleReadContactsPermissionResult() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val contactName = "Paul"
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.SEND_SMS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.SEND_SMS),
                    MY_PERMISSIONS_REQUEST_SEND_SMS
                )
            } else {
                val phoneNumber = getPhoneNumberForContact(contactName)
                if (phoneNumber != null) {
                    val message = smsEditText.text.toString()
                    sendMessage(phoneNumber, message)
                }
            }
        } else {
            Toast.makeText(
                this,
                "Permission denied. Cannot access contacts.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun handleSendSmsPermissionResult() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.SEND_SMS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val contactName = "Paul"
            val phoneNumber = getPhoneNumberForContact(contactName)
            if (phoneNumber != null) {
                val message = smsEditText.text.toString()
                sendMessage(phoneNumber, message)
            }
        } else {
            Toast.makeText(
                this,
                "Permission denied. Cannot send SMS.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        private const val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 123
        private const val MY_PERMISSIONS_REQUEST_SEND_SMS = 456
    }
}
