package de.home24.ui.article

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.MenuItem
import com.tripplanner.ui.base.BaseActivity
import de.home24.R
import de.home24.databinding.ActivityArticleBinding
import de.home24.ui.article.selection.SelectionFragment
import de.home24.utils.ext.replaceFragment

/**
 * Created by suyashg on 23/07/18.
 *
 * Article related fragment comes under this activity
 */
class ArticleActivity : BaseActivity() {

    val binding: ActivityArticleBinding by lazy {
        DataBindingUtil.setContentView<ActivityArticleBinding>(this, R.layout.activity_article)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentContainerId = binding.flArticleContent.id
        replaceFragment(fragmentContainerId, :: SelectionFragment)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.getItemId() === android.R.id.home) {
            onBackPressed()// close this fragment
        }
        return super.onOptionsItemSelected(item)
    }
}
