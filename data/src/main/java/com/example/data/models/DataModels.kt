package com.example.data.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class UserDataModel(): RealmObject {
    var name: String  = ""
    var surname: String  = ""
    var phone: String  = ""
}