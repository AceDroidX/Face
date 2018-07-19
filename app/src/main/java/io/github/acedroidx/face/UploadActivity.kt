package io.github.acedroidx.face

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_upload.*
import java.io.FileInputStream
import android.graphics.Bitmap
import android.util.JsonReader
import org.json.JSONObject




class UploadActivity : AppCompatActivity() {
    lateinit var json:JSONObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        val appInfo = this.packageManager
                .getApplicationInfo(this.packageName,
                        PackageManager.GET_META_DATA)
        val key = appInfo.metaData.getString("API_Key")
        val secret = appInfo.metaData.getString("API_Secret")
        if (intent != null) {
            Log.d("wxxDebug", "upload")
            val FILENAME = intent.extras.getString("name")
            val dir = cacheDir.toString() + "/" + FILENAME
            val bitmap = BitmapFactory.decodeFile(dir)
            imageView.setImageBitmap(bitmap)
            upload.setOnClickListener {
                var ft = FaceTest()
                ft.main(this, FILENAME, key, secret)
            }
            join.setOnClickListener {

            }
        }
    }

    fun getResult(str: String) {
        result.text = str
        json = JSONObject(str)//将返回的数据转化为json数据格式
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent()
        intent.setClass(this, MainActivity::class.java)
        //intent.putExtra("name", FILENAME)  //放入数据
        startActivity(intent) //开始跳转
    }
}
