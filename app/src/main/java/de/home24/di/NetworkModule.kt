package de.home24.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import de.home24.BaseApp
import de.home24.data.remote.Endpoints
import de.home24.data.remote.api.ArticleService
import de.home24.utils.AppConstants.Companion.CONNECTION_TIMEOUT
import de.home24.utils.AppConstants.Companion.MEM_CACHE_SIZE
import de.home24.utils.AppConstants.Companion.MEM_CACHE_SUFFIX
import de.home24.utils.AppConstants.Companion.READ_TIMEOUT
import de.home24.utils.AppConstants.Companion.WRITE_TIMEOUT
import de.home24.utils.ApplicationJsonAdapterFactory
import de.home24.utils.InstantAdapter
import de.home24.utils.Memory
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by suyashg on 19/07/18.
 */
@Module
open class NetworkModule {

    open fun buildOkHttpClient(app: BaseApp): OkHttpClient =
            OkHttpClient.Builder()
                    .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .cache(Cache(File(app.cacheDir, MEM_CACHE_SUFFIX),
                            Memory.calcCacheSize(app, MEM_CACHE_SIZE)))
                    .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(app: BaseApp): OkHttpClient = buildOkHttpClient(app)

    @Singleton
    @Provides
    fun provideMoshi() = Moshi.Builder()
            .add(ApplicationJsonAdapterFactory.INSTANCE)
            .add(InstantAdapter.INSTANCE)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit = Retrofit.Builder()
            .baseUrl(Endpoints.SERVER_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideArticleService(retrofit: Retrofit): ArticleService =
            retrofit.create(ArticleService::class.java)
}