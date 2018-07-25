package de.home24.ui.article.review

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import de.home24.R
import de.home24.data.local.model.ArticleTemplate

/**
 * Created by suyashg on 24/07/18.
 */
class ReviewAdapter(val items: ArrayList<ArticleTemplate>, val context: Context, var viewType: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //view types
    companion object {
        const val VIEW_TYPE_LIST: Int = 0
        const val VIEW_TYPE_GRID: Int = 1
    }

    // Binds each item in the ArrayList to a view
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ReviewViewHolder).bindViews(items[position])
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder = when (viewType) {
            VIEW_TYPE_LIST -> ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_review_list, parent, false))
        // other view holders...
            else -> GridViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_review_grid, parent, false))
        }
        return viewHolder
    }

    // Gets the number of articles in the list
    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        if (viewType == VIEW_TYPE_LIST) {
            return VIEW_TYPE_LIST
        } else {
            return VIEW_TYPE_GRID
        }
    }

    fun setItemType(type: Int) {
        viewType = type
    }

    /**
     * this will prevent sudden jerk of the recyclerview when list to grid or vice-versa
     */
    fun notifyRemoveEach() {
        for (i in 0 until items.size) {
            notifyItemRemoved(i)
        }
    }

    /**
     * this will prevent sudden jerk of the recyclerview when list to grid or vice-versa
     */
    fun notifyAddEach() {
        for (i in 0 until items.size) {
            notifyItemInserted(i)
        }
    }

    class GridViewHolder(view: View) : RecyclerView.ViewHolder(view), ReviewViewHolder {

        override fun bindViews(article: ArticleTemplate) {
            //image
            val picture = itemView.findViewById<ImageView>(R.id.imv_article)
            val transforms = RequestOptions()
                    .error(R.drawable.image_placeholder)
                    .placeholder(R.drawable.image_placeholder)
            Glide.with(itemView.context)
                    .load(article.imageURL)
                    .apply(transforms)
                    .into(picture)

            //star
            val starImage = itemView.findViewById<ImageView>(R.id.imv_article_liked)
            if (article.isLiked) starImage.visibility = View.VISIBLE else starImage.visibility = View.GONE
        }

    }

    class ListViewHolder(view: View) : RecyclerView.ViewHolder(view), ReviewViewHolder {

        override fun bindViews(article: ArticleTemplate) {
            //image
            val picture = itemView.findViewById<ImageView>(R.id.imv_article)
            val transforms = RequestOptions()
                    .error(R.drawable.image_placeholder)
                    .placeholder(R.drawable.image_placeholder)
            Glide.with(itemView.context)
                    .load(article.imageURL)
                    .apply(transforms)
                    .into(picture)
            //title
            val tvTitle = itemView.findViewById<TextView>(R.id.tv_article_title)
            tvTitle.text = article.title
            //star
            val starImage = itemView.findViewById<ImageView>(R.id.imv_article_liked)
            if (article.isLiked) starImage.visibility = View.VISIBLE else starImage.visibility = View.GONE
        }

    }
}
