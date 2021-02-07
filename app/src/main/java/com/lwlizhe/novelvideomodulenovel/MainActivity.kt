package com.lwlizhe.novelvideomodulenovel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lwlizhe.module.novel.content.ui.view.activity.NovelContentActivity
import com.lwlizhe.module.novel.content.ui.view.activity.NovelMainActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novel_content)
    }

    override fun onPostResume() {
        super.onPostResume()
        startActivity(Intent(this, NovelContentActivity::class.java))
    }
}
