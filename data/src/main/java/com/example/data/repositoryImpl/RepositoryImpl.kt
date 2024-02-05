package com.example.data.repositoryImpl

import com.example.data.data_models.FeedbackData
import com.example.data.data_models.InfoData
import com.example.data.data_models.PriceData
import com.example.data.data_models.ProductDataModel
import com.example.data.data_models.UserDataModel
import com.example.data.remote.ApiService
import com.example.domain.models.ListProduct
import com.example.domain.models.Product
import com.example.domain.models.User
import com.example.domain.repository.Repository
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.query.RealmResults

class RepositoryImpl(private val realm: Realm, private val api: ApiService): Repository {

    override suspend fun addUserToDataBase(user: User){
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

    override suspend fun addProductToDataBase(product: Product){
        realm.writeBlocking {
            copyToRealm(ProductDataModel().apply {
                id = product.id
                title = product.title
                subtitle = product.subtitle
                price = PriceData().apply {
                    price = product.price.price
                    discount = product.price.discount
                    priceWithDiscount = product.price.priceWithDiscount
                    unit = product.price.unit
                }
                feedback = FeedbackData().apply {
                    count = product.feedback.count
                    rating = product.feedback.rating
                }
                tags.addAll(product.tags)
                available = product.available
                description = product.description
                info.addAll(product.info.map {
                    InfoData().apply {
                        title = it.title
                    }
                })
                ingredients = product.ingredients
            })
        }
    }

    override suspend fun deleteProductFromDataBase(product: Product) {
        TODO("Not yet implemented")
    }

    override suspend fun getProductFromDataBase() {
        TODO("Not yet implemented")
    }

}