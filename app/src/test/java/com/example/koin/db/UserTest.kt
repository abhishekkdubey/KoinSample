package com.example.koin.db

import com.example.koin.BaseTest

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class UserTest : BaseTest() {


    lateinit var user: User

    @Before
    override fun setUp() {
        user= User(1, "http://img.url","abc@gm.co", "Xyz", "Pqr")
    }

    @Test
    fun getId() {
        assertEquals(user.id, 1)
        assertNotNull(user.id)

    }

    @Test
    fun getAvatar() {
        assertEquals(user.avatar, "http://img.url")
        assertNotNull(user.avatar)
    }

    @Test
    fun getEmail() {
        assertEquals(user.email, "abc@gm.co")
        assertNotNull(user.email)
    }

    @Test
    fun getFirstName() {
        assertEquals(user.firstName, "Xyz")
        assertNotNull(user.firstName)
    }

    @Test
    fun getLastName() {
        assertEquals(user.lastName, "Pqr")
        assertNotNull(user.lastName)
    }
}