package com.example.finin.db

import com.example.finin.BaseTest

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class DataTest : BaseTest() {

    lateinit var data: Data
    lateinit var ad: Ad

    @Before
    override fun setUp() {
        ad = Ad("", "", "")
        data = Data(ad, emptyList(), 2, 3, 4, 5)

    }

    @Test
    fun getAd() {
        assertEquals(data.ad, ad)
        assertNotNull(data.ad)
    }

    @Test
    fun getUsers() {
        assertEquals(data.users.size, 0)
        assertNotNull(data.users)
    }

    @Test
    fun getPage() {
        assertEquals(data.page, 2)
        assertNotNull(data.page)
    }

    @Test
    fun getPerPage() {
        assertEquals(data.perPage, 3)
        assertNotNull(data.perPage)
    }

    @Test
    fun getTotal() {
        assertEquals(data.total, 4)
        assertNotNull(data.total)
    }

    @Test
    fun getTotalPages() {
        assertEquals(data.totalPages, 5)
        assertNotNull(data.totalPages)
    }
}