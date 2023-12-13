package fr.isep.familyproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import fr.isep.myapplication.R

class MainScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.`main_screen`)

        val phone_button: Button = findViewById(R.id.phone)
        val sms_button: Button = findViewById(R.id.sms)
        val button3: Button = findViewById(R.id.button3)
        val button4: Button = findViewById(R.id.button4)

        // Add click listeners or perform actions here
        sms_button.setOnClickListener() {
            val intent = Intent(this, SMS::class.java);
            startActivity(intent);
        }
    }
}
