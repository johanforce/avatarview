package com.jarvis.avatarview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.well.avatar.ViewAvatar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val avatarView = this.findViewById<ViewAvatar>(R.id.avatar)
        avatarView.setDataAvatar(false, "L D", "")

    }
}