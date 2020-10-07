package com.example.koin.db

import com.example.koin.BaseTest

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class AdTest : BaseTest() {

    lateinit var ad :Ad

    @Before
    override fun setUp() {
        super.setUp()
        ad = Ad("finin", "test", "https://test.com")
    }

    @Test
    fun getCompany() {
        assertNotNull(ad.company)
        assertEquals(ad.company, "finin")
        assertNotEquals(ad.company, "finin2")
    }

    @Test
    fun getText() {
        assertNotNull(ad.text)
        assertEquals(ad.text, "test")
        assertNotEquals(ad.text, "finin2")
    }

    @Test
    fun getUrl() {
        assertNotNull(ad.url)
        assertEquals(ad.url, "https://test.com")
        assertNotEquals(ad.url, "finin2")
    }
}