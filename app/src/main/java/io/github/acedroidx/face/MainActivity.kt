package io.github.acedroidx.face

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.system.Os.close


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        start.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, CameraActivity::class.java)
            intent.putExtra("type", "start")  //放入数据
            startActivity(intent)  //开始跳转
        }

        upload.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, CameraActivity::class.java)
            intent.putExtra("type", "upload")  //放入数据
            startActivity(intent)  //开始跳转
        }

        about.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, AboutActivity::class.java)
            //intent.putExtra("type", "upload")  //放入数据
            startActivity(intent)  //开始跳转
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        System.exit(0);
    }
}
