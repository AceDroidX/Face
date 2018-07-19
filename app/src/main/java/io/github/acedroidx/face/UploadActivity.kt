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
import android.os.Handler
import android.os.Message
import android.util.JsonReader
import org.json.JSONObject
import java.util.HashMap


class UploadActivity : AppCompatActivity() {
    lateinit var json: JSONObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        join.isEnabled=false
        editText.isEnabled=false
        val appInfo = this.packageManager
                .getApplicationInfo(this.packageName,
                        PackageManager.GET_META_DATA)
        val key = appInfo.metaData.getString("API_Key")
        val secret = appInfo.metaData.getString("API_Secret")
        val outer_id = appInfo.metaData.getString("outer_id")
        if (intent != null) {
            Log.d("wxxDebug", "upload")
            val FILENAME = intent.extras.getString("name")
            val dir = cacheDir.toString() + "/" + FILENAME
            val bitmap = BitmapFactory.decodeFile(dir)
            imageView.setImageBitmap(bitmap)
            upload.setOnClickListener {
                join.isEnabled=true
                editText.isEnabled=true
                var ft = FaceBase()
                ft.main(this, FILENAME, key, secret)
            }
            join.setOnClickListener {
                upload.isEnabled=false
                val faceToken = json.optJSONArray("faces").optJSONObject(0).optString("face_token")//将face_token提取出来
                var af = AddFace()
                af.main(this, key, secret, outer_id ,faceToken)
            }
            setid.setOnClickListener {

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

class AddFace : FaceBase() {
    fun main(activity: UploadActivity, key: String, secret: String, outer_id: String,faceToken:String) {
        val url = "https://api-cn.faceplusplus.com/facepp/v3/faceset/addface"
        val map = HashMap<String, String>()
        map["api_key"] = key
        map["api_secret"] = secret
        map["outer_id"] = outer_id
        map["face_tokens"] = faceToken
        val handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                val bundle = msg.data
                val result = bundle.getString("data")
                activity.getResult(result!!)
            }
        }
        object : Thread() {
            override fun run() {
                //这里写入子线程需要做的工作
                try {
                    var bacd = ByteArray(0)
                    bacd = post(url, map)
                    val str = String(bacd)
                    println(str)
                    Log.e("wxxDebug", str)
                    val bundle = Bundle()
                    bundle.putString("data", str)
                    val message = Message()
                    message.data = bundle
                    handler.sendMessage(message)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }.start()
    }
}
