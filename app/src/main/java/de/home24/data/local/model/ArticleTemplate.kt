package de.home24.data.local.model

import android.os.Parcel
import android.os.Parcelable
import de.home24.utils.AppConstants

/**
 *  this data class provides the data to used by the UI interface
 *  add more data if requried
 */
data class ArticleTemplate(val title: String? = AppConstants.EMPTY, val imageURL: String? = AppConstants.EMPTY): Parcelable {
    var isLiked: Boolean = false

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString()) {
        isLiked = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(title)
        dest?.writeString(imageURL)
        dest?.writeByte(if (isLiked) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ArticleTemplate> {
        override fun createFromParcel(parcel: Parcel): ArticleTemplate {
            return ArticleTemplate(parcel)
        }

        override fun newArray(size: Int): Array<ArticleTemplate?> {
            return arrayOfNulls(size)
        }
    }
}