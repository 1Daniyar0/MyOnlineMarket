package com.example.data.data_models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject

class ProductDataModel: RealmObject {
    var id: String? = ""
    var title: String? = ""
    var subtitle: String? = ""
    var price: PriceData? = null
    var feedback: FeedbackData? = null
    var tags: RealmList<String> = realmListOf()
    var available: Int? = null
    var description: String? = ""
    var info: RealmList<InfoData> = realmListOf()
    var ingredients: String? = ""
}

class InfoData: EmbeddedRealmObject{
    var title: String? = ""
    var value: String? = ""
}

class FeedbackData: EmbeddedRealmObject {
    var count: Int? = null
    var rating: Double? = null
}


class PriceData: EmbeddedRealmObject {
    var price: String? = ""
    var discount: Int? = null
    var priceWithDiscount: String? = ""
    var unit: String? = ""
}