package com.example.data.data_models

import io.realm.kotlin.types.RealmObject

class UserDataModel(): RealmObject {
    var name: String  = ""
    var surname: String  = ""
    var phone: String  = ""
}