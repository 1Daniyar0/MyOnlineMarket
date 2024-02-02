package com.example.data.repositoryImpl

import com.example.data.models.UserDataModel
import com.example.data.remote.ApiService
import com.example.domain.models.ListProduct
import com.example.domain.models.User
import com.example.domain.repository.Repository
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults

class RepositoryImpl(private val realm: Realm, private val api: ApiService): Repository {

    override suspend fun addUserToDatabase(user: User){
        realm.writeBlocking {
            copyToRealm(UserDataModel().apply {
                name = user.name
                surname = user.surname
                phone = user.phone
            })
        }
    }
    override suspend fun checkUserDataBase():Boolean{
        val itemUserPhone: RealmResults<UserDataModel> =
            realm.query<UserDataModel>()
                .find()
        return itemUserPhone.isNotEmpty()
    }

    override suspend fun getListOfProduct(): ListProduct {
        return api.getListOfProduct()
    }

}