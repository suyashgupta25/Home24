package de.home24.ui.article.selection

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import de.home24.R
import de.home24.data.local.model.ArticleTemplate
import kotlinx.android.synthetic.main.item_csv_article.view.*

/**
 * Created by suyashg on 23/07/18.
 */
class SelectionAdapter(val context: Context, val data: MutableList<ArticleTemplate>?) : BaseAdapter() {

    //datalist
    private val dataList = mutableListOf<ArticleTemplate>()

    init {
        if (data != null) {
            dataList.addAll(data)
        }
    }

    /**
     * provides view holder for binding data
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val holder: DataViewHolder
        var view = convertView

        if (view == null) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_csv_article, parent, false)
            holder = DataViewHolder(view)
            view?.tag = holder
        } else {
            holder = view.tag as DataViewHolder
        }
        holder.bindData(context, getItem(position))
        return view
    }

    /**
     * Static view items holder
     */
    class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var picture = view.imv_article
        internal fun bindData(context: Context, data: ArticleTemplate) {
            val uri = data.imageURL
            val dimension = context.resources.getDimension(R.dimen.radius_card_corner)
            val transforms = RequestOptions()
                    .transforms(CenterInside(), RoundedCorners(dimension.toInt()))
                    .error(R.drawable.image_placeholder)
                    .placeholder(R.drawable.image_placeholder)
            Glide.with(context)
                    .load(uri)
                    .apply(transforms)
                    .into(picture)
        }

    }

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): ArticleTemplate {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}