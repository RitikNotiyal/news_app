package com.ritik.aktk

import android.app.DownloadManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import androidx.browser.customtabs.CustomTabsIntent




class MainActivity : AppCompatActivity(), ItemClicked {
    private lateinit var mAdapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager=LinearLayoutManager(this)
        fetchData()
        mAdapter= NewsListAdapter( this)
        recyclerView.adapter = mAdapter
    }

    private fun fetchData(){
        val queue = Volley.newRequestQueue(this)
       val url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=9c1790d4c45b403c946e534825804548"
        val getRequest: JsonObjectRequest = object : JsonObjectRequest(
           Request.Method.GET, url, null,

           Response.Listener{
               val newsArr = it.getJSONArray("articles")
               val newsArray = ArrayList<News>()
               for (i in 0 until newsArr.length()){
                   val newsJSONObject = newsArr.getJSONObject(i)
                   val news = News(
                       newsJSONObject.getString("title"),
                       newsJSONObject.getString("author"),
                       newsJSONObject.getString("url"),
                       newsJSONObject.getString("urlToImage")
                   )
                   newsArray.add(news)

               }
               mAdapter.updateNews(newsArray)
           },
           Response.ErrorListener { error ->

           }
       )
           {
               @Throws(AuthFailureError::class)
               override fun getHeaders(): Map<String, String> {
                   val params: MutableMap<String, String> = HashMap()
                   params["User-Agent"] = "Mozilla/5.0"
                   return params
               }
           }
              queue.add(getRequest)
    }

    override fun onItemClicked(item: News) {
       // val url = "https://google.com/"
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }
}