package com.ritik.aktk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsListAdapter(private val listener: ItemClicked):RecyclerView.Adapter<NewsViewHolder>() {

    private val items: ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
       val viewHolder = NewsViewHolder(view)
        view.setOnClickListener{
           listener.onItemClicked(items[viewHolder.bindingAdapterPosition])
       }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val current = items[position]
        holder.titleView.text = current.title
        holder.author.text = current.author
        Glide.with(holder.itemView.context).load(current.imageUrl).into(holder.image)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateNews(updatedNews: ArrayList<News>){
        items.clone()
        items.addAll(updatedNews)

        notifyDataSetChanged()
    }

}
class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleView: TextView = itemView.findViewById(R.id.title)
    val image: ImageView = itemView.findViewById(R.id.image)
    val author: TextView = itemView.findViewById(R.id.author)
}

interface ItemClicked{
    fun onItemClicked(item: News)
}