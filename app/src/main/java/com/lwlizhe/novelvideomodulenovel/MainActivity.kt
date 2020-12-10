package com.lwlizhe.novelvideomodulenovel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lwlizhe.module.novel.content.ui.view.NovelContentActivity
import com.lwlizhe.module.novel.content.ui.view.NovelMainActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novel_content)
    }

    override fun onPostResume() {
        super.onPostResume()
        startActivity(Intent(this, NovelMainActivity::class.java))
    }
}
