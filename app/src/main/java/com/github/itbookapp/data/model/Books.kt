package com.github.itbookapp.data.model

import com.google.gson.annotations.SerializedName

data class Books(
    @SerializedName("authors")
    val authors: String?,
    @SerializedName("desc")
    val desc: String?,
    @SerializedName("error")
    val error: String?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("isbn10")
    val isbn10: String?,
    @SerializedName("isbn13")
    val isbn13: String?,
    @SerializedName("pages")
    val pages: String?,
    @SerializedName("pdf")
    val pdf: Map<String, String>?,
    @SerializedName("price")
    val price: String?,
    @SerializedName("publisher")
    val publisher: String?,
    @SerializedName("rating")
    val rating: String?,
    @SerializedName("subtitle")
    val subtitle: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("year")
    val year: String?
) {

    override fun equals(other: Any?): Boolean {
        return if (other is Books) {
            authors.equals(other.authors) &&
                    desc.equals(other.desc) &&
                    error.equals(other.error) &&
                    image.equals(other.image) &&
                    isbn10.equals(other.isbn10) &&
                    isbn13.equals(other.isbn13) &&
                    pages.equals(other.pages) &&
                    pdf.mapEquals(other.pdf) &&
                    price.equals(other.price) &&
                    publisher.equals(other.publisher) &&
                    rating.equals(other.rating) &&
                    subtitle.equals(other.subtitle) &&
                    title.equals(other.title) &&
                    url.equals(other.url) &&
                    year.equals(other.year)
        } else {
            false
        }
    }

    private fun Map<String, String>?.mapEquals(other: Any?): Boolean {
        if (this == null || other == null) return false
        if (this === other) return true
        if (javaClass != other.javaClass) return false

        (other as? Map<String, String>)?.let {
            if (this.size != other.size) return false
            for ((key, value) in this) {
                val otherValue = other[key]
                if (value != otherValue) return false
            }
            return true
        } ?: return false
    }

    override fun hashCode(): Int {
        var result = authors?.hashCode() ?: 0
        result = 31 * result + (desc?.hashCode() ?: 0)
        result = 31 * result + (error?.hashCode() ?: 0)
        result = 31 * result + (image?.hashCode() ?: 0)
        result = 31 * result + (isbn10?.hashCode() ?: 0)
        result = 31 * result + (isbn13?.hashCode() ?: 0)
        result = 31 * result + (pages?.hashCode() ?: 0)
        result = 31 * result + (pdf?.hashCode() ?: 0)
        result = 31 * result + (price?.hashCode() ?: 0)
        result = 31 * result + (publisher?.hashCode() ?: 0)
        result = 31 * result + (rating?.hashCode() ?: 0)
        result = 31 * result + (subtitle?.hashCode() ?: 0)
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (url?.hashCode() ?: 0)
        result = 31 * result + (year?.hashCode() ?: 0)
        return result
    }
}
