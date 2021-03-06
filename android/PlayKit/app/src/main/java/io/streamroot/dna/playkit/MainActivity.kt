package io.streamroot.dna.playkit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.launchButton).setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, PlayerActivity::class.java)
            val streamEditText: AutoCompleteTextView = findViewById(R.id.streamEditText)
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("streamUrl", streamEditText.text.toString())
            applicationContext.startActivity(intent)
        })
    }
}
