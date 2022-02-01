package com.example.volleyapp

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getVolley()
        postVolley()
        getJSONVolley()
        getImageVolley()
    }

    private fun getImageVolley(){
        val queue = Volley.newRequestQueue(this)
        val url = "https://i.imgur.com/7spzG.png"
        val img = findViewById<ImageView>(R.id.img)
        val imgReq = ImageRequest(
            url,
            {
                img.setImageBitmap(it)
            },
            0,
            0,
            ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565,
            {
                Toast.makeText(this,it.message,Toast.LENGTH_LONG).show()
            }
        )
        queue.add(imgReq)
    }

    private fun getVolley(){
        val queue = Volley.newRequestQueue(this@MainActivity)
        val url = "https://private-4c0e8-simplestapi3.apiary-mock.com/message"
        val txt = findViewById<TextView>(R.id.text)
        val strReq = StringRequest(Request.Method.GET,
            url,
            { response ->
                txt.text = "Get request: $response"
            },
            {
                txt.text = it.toString()
            })
        queue.add(strReq)
    }

    private fun postVolley(){
        val queue = Volley.newRequestQueue(this@MainActivity)
        val url = "https://private-4c0e8-simplestapi3.apiary-mock.com/message"
        val txt = findViewById<TextView>(R.id.text1)
        val requestBody = "id=1" + "&msg=test_msg"
        val stringReq = object : StringRequest(Method.POST, url,
                Response.Listener { response ->
                    txt.text = "Post request: $response"
                },
                Response.ErrorListener { error ->
                  txt.text=error.toString()
                }
            ){
            override fun getParams(): MutableMap<String, String> {
                val params: MutableMap<String,String> = mutableMapOf()
                params["id"]="1"
                params["msg"]="test_msg"
                return params
            }
//                override fun getBody(): ByteArray {
//                    return requestBody.toByteArray(Charset.defaultCharset())
//                }
            }
        queue.add(stringReq)
    }

    private fun getJSONVolley(){
        val queue = Volley.newRequestQueue(this)
        val url = " https://api.github.com/search/users?q=coder-1234"
        val txt = findViewById<TextView>(R.id.text2)
        val outputArr :MutableList<String> = mutableListOf()
        val jsonReq = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {
                val jsonArr = it.getJSONArray("items")
                for(i in 0 until jsonArr.length()){
                    val tmpObj = jsonArr.getJSONObject(i).getString("login")
                    outputArr.add(tmpObj)
                }
                txt.text = "JSON request: $outputArr"
            },
            {
                txt.text = it.toString()
            }
        )
        queue.add(jsonReq)
    }
}