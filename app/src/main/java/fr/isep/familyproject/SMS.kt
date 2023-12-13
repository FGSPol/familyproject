package fr.isep.familyproject

import android.os.Bundle
import android.provider.ContactsContract
import android.telephony.SmsManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import fr.isep.myapplication.R

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
        val phoneNumber = getPhoneNumberForContact(contactName)
        if (phoneNumber != null) {
            val message = smsEditText.text.toString()
            sendMessage(phoneNumber, message)
        }
    }

    private fun sendMessage(phoneNumber: String, message: String) {
        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(phoneNumber, null, message, null, null)
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
                val columnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                return cursor.getString(columnIndex)
            }
        }
        return null
    }
}
