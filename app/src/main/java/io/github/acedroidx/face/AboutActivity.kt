package io.github.acedroidx.face

import android.support.v4.app.Fragment
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity


/**
 * Created by AceDroidX on 2018/2/4.Updated on 2018/7/19.
 */

class AboutActivity : AppCompatActivity() {

    // 获取packagemanager的实例
    // getPackageName()是你当前类的包名，0代表是获取版本信息
    val versionName: String
        get() {
            val packageManager = this.packageManager
            val packInfo = packageManager?.getPackageInfo(this.packageName, 0)
            Log.d("wxxDebugAbout", packInfo!!.versionName)
            return packInfo.versionName
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        val versionText = findViewById<TextView>(R.id.textView_version)
        try {
            versionText.text = "V$versionName"
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val copyrightText = findViewById<TextView>(R.id.textView_copyright)
        if (copyrightText.text != "By AceDroidX") {
            copyrightText.text = "By AceDroidX"
            Log.d("wxxDebugAbout", "原程序已被修改！！！！By wxx")
        }

        val websiteText = findViewById<TextView>(R.id.textView_website)
        val appInfo = this.packageManager
                .getApplicationInfo(this.packageName,
                        PackageManager.GET_META_DATA)
        val url = appInfo.metaData.getString("Website")
        websiteText.text = url
        websiteText.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            websiteText.text = url
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }
}
