/*package fr.isep.familyproject

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.Button
import fr.isep.familyproject.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen)

        // Find the Button using findViewById
        //val btnCallDad: Button = findViewById(R.id.btnCallDad)
        //val btnCallMom: Button = findViewById(R.id.btnCallMom)

        btnCallDad.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:93232323")


            startActivity(dialIntent)
        }

        btnCallMom.setOnClickListener {

            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:93232323")

            startActivity(dialIntent)
        }
    }
}*/