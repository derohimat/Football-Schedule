package net.derohimat.footballschedule.injection.module

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import net.derohimat.footballschedule.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Singleton

@Module
class NetworkModule(private val context: Context) {

    private fun getBaseUrl() = BuildConfig.BASE_API_URL + "/api/v1/json/${BuildConfig.TEST_API_KEY}/"

    @Provides
    @Singleton
    internal fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor,
                                     chuckInterceptor: ChuckInterceptor,
                                     stethoInterceptor: StethoInterceptor): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.addInterceptor(httpLoggingInterceptor)
        httpClientBuilder.addInterceptor(chuckInterceptor)
        httpClientBuilder.addNetworkInterceptor(stethoInterceptor)
        return httpClientBuilder.build()

    }

    @Provides
    @Singleton
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message ->
            Timber.d(message)
        }.setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    internal fun provideChuckInterceptor(): ChuckInterceptor {
        return ChuckInterceptor(context)
    }

    @Provides
    @Singleton
    internal fun provideStetho(): StethoInterceptor {
        return StethoInterceptor()
    }

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        return GsonBuilder()
                .serializeNulls()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
    }

}