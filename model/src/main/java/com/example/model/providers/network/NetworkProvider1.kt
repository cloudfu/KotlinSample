//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import okhttp3.Interceptor
//import okhttp3.OkHttpClient
//import okhttp3.Response
//import retrofit2.Retrofit
//import retrofit2.converter.moshi.MoshiConverterFactory
//import timber.log.Timber
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//internal object NetworkModule {
//
//    @Provides
//    @Singleton
//    fun provideOkHttpClient(): OkHttpClient {
//        return OkHttpClient.Builder()
//            .addInterceptor(HttpRequestInterceptor())
//            .build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
//        return Retrofit.Builder()
//            .client(okHttpClient)
//            .baseUrl("https://pokeapi.co/api/v2/")
//            .addConverterFactory(MoshiConverterFactory.create())
////            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
//            .build()
//    }
//}
//
//class HttpRequestInterceptor : Interceptor {
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val originalRequest = chain.request()
//        val request = originalRequest.newBuilder().url(originalRequest.url).build()
//        Timber.d(request.toString())
//        return chain.proceed(request)
//    }
//}