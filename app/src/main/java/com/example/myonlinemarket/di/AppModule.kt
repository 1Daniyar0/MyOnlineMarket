package com.example.myonlinemarket.di

import com.example.data.data_models.UserDataModel
import com.example.data.remote.ApiService
import com.example.data.repositoryImpl.RepositoryImpl
import com.example.domain.repository.Repository
import com.example.domain.usecase.AddProductFavoritesDbUseCase
import com.example.domain.usecase.AddUserToDatabaseUseCase
import com.example.domain.usecase.CheckUserInDataBaseUseCase
import com.example.domain.usecase.DeleteProductFavoritesDbUseCase
import com.example.domain.usecase.GetListOfProductUseCase
import com.example.domain.usecase.GetProductListFavoritesUseCase
import com.example.myonlinemarket.constant.BASE_URL
import com.example.myonlinemarket.viewModel.MarketViewModel
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val useCaseModule = module{
    single { AddUserToDatabaseUseCase(get()) }
    single { CheckUserInDataBaseUseCase(get()) }
    single { GetListOfProductUseCase(get()) }
    single { GetProductListFavoritesUseCase(get()) }
    single { DeleteProductFavoritesDbUseCase(get()) }
    single { AddProductFavoritesDbUseCase(get()) }
}

val viewModelModule = module {
    viewModel {MarketViewModel(get(),get(),get(),get(),get(),get(),)}
}

val netModule = module{
    single { createOkHttpClient()}
    single { createRetrofit(get()) }
    single<ApiService> {get<Retrofit>().create(ApiService::class.java)}
}

val dataBaseModule = module{
    val config = RealmConfiguration.create(schema = setOf(UserDataModel::class))
    val realm: Realm = Realm.open(config)
    single<Realm> {realm}
}

val repositoryModule = module{
    single<Repository>{RepositoryImpl(get(),get())}
}



private fun createOkHttpClient(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
}


fun createRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

