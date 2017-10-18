package ru.rougsig.ambercard.custom.map

import android.annotation.SuppressLint
import android.content.Context
import android.os.Environment
import android.util.AttributeSet
import android.webkit.WebView
import android.os.Environment.getExternalStorageDirectory
import java.io.File
import android.webkit.JavascriptInterface




/**
 * Created by rougs on 18-Oct-17.
 */
@SuppressLint("SetJavaScriptEnabled")
class YandexMap(context: Context, attrs: AttributeSet) : WebView(context, attrs) {
    init {
        settings.javaScriptEnabled = true
        addJavascriptInterface(JavaScript(), "java")
        loadUrl("file:///android_asset/map/index.html")
    }
}

internal class JavaScript {
    @JavascriptInterface
    fun processHTML(html: String) {
        println(html)
    }
}