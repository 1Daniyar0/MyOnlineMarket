package com.example.domain.models

import com.google.gson.annotations.SerializedName


data class ListProduct(
    @SerializedName("items" )
    var items: ArrayList<Product> = arrayListOf()
)


data class Product (
    @SerializedName("id")
    var id: String? = "",
    @SerializedName("title")
    var title: String? = "",
    @SerializedName("subtitle")
    var subtitle: String? = "",
    @SerializedName("price")
    var price: Price? = Price(),
    @SerializedName("feedback")
    var feedback: Feedback? = Feedback(),
    @SerializedName("tags")
    var tags: ArrayList<String> = arrayListOf(),
    @SerializedName("available")
    var available: Int? = null,
    @SerializedName("description")
    var description: String? = "",
    @SerializedName("info")
    var info: ArrayList<Info> = arrayListOf(),
    @SerializedName("ingredients" )
    var ingredients: String? = ""
)

data class Info (
    @SerializedName("title")
    var title: String? = "",
    @SerializedName("value")
    var value: String? = ""
)

data class Feedback (
    @SerializedName("count")
    var count  : Int? = null,
    @SerializedName("rating")
    var rating : Double? = null
)

data class Price (
    @SerializedName("price")
    var price: String? = "",
    @SerializedName("discount")
    var discount: Int? = null,
    @SerializedName("priceWithDiscount")
    var priceWithDiscount: String? = "",
    @SerializedName("unit")
    var unit: String? = ""

)

