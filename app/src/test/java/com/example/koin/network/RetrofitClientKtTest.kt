package com.example.koin.network

import com.example.koin.BaseTest
import io.mockk.unmockkAll
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito.mock

class RetrofitClientKtTest : BaseTest() {


    @Before
    override fun setUp() {
        super.setUp()
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun provideRetrofitTest() {
        val okHttpClient =  mock(OkHttpClient::class.java)
        val retrofitClient= provideRetrofit(okHttpClient)
        assertEquals(retrofitClient.baseUrl().isHttps, true)
        assertNotNull(retrofitClient)
    }

    @Test
    fun provideOkHttpClientTest() {
        val interceptor = HttpLoggingInterceptor()
        val client = provideOkHttpClient(interceptor)
        assertNotNull(client)
        assertTrue(client.interceptors().contains(interceptor))
    }

    @Test
    fun provideLoggingInterceptorTest() {
        val interceptor =  provideLoggingInterceptor()
        assertNotNull(interceptor)
        assertEquals(interceptor.level, HttpLoggingInterceptor.Level.BODY)
    }

    @Test
    fun provideUserApiTest() {
        val interceptor =  provideLoggingInterceptor()
        val client = provideOkHttpClient(interceptor)
        val retrofit = provideRetrofit(client)
        val api = provideUserApi(retrofit)
        assertNotNull(api)
    }
}