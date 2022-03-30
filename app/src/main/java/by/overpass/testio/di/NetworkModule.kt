package by.overpass.testio.di

import by.overpass.testio.BuildConfig
import by.overpass.testio.data.api.AuthTestioApi
import by.overpass.testio.data.api.AuthTestioApiImpl
import by.overpass.testio.data.api.TIMEOUT_CONNECT_SEC
import by.overpass.testio.data.api.TIMEOUT_READ_SEC
import by.overpass.testio.data.api.TIMEOUT_WRITE_SEC
import by.overpass.testio.data.api.TestioApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

	@Provides
	@Singleton
	fun httpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()

	@Provides
	@Singleton
	fun okHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder()
		.apply {
			if (BuildConfig.DEBUG) {
				addInterceptor(httpLoggingInterceptor)
			}
		}
		.connectTimeout(TIMEOUT_CONNECT_SEC, TimeUnit.SECONDS)
		.readTimeout(TIMEOUT_READ_SEC, TimeUnit.SECONDS)
		.writeTimeout(TIMEOUT_WRITE_SEC, TimeUnit.SECONDS)
		.build()

	@Provides
	@Singleton
	fun gsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

	@Provides
	@Singleton
	fun retrofit(
		okHttpClient: OkHttpClient,
		gsonConverterFactory: GsonConverterFactory,
	): Retrofit = Retrofit.Builder()
		.baseUrl(TestioApi.BASE_URL)
		.client(okHttpClient)
		.addConverterFactory(gsonConverterFactory)
		.build()

	@Provides
	@Singleton
	fun testioApi(retrofit: Retrofit): TestioApi = retrofit.create()

	@Provides
	@Singleton
	fun authTestioApi(testioApi: TestioApi): AuthTestioApi = AuthTestioApiImpl(testioApi)
}