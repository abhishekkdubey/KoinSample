package com.example.koin.db

import com.example.koin.BaseTest
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class UserDatabaseTest : BaseTest() {


    @MockK
    lateinit var userDatabase: UserDatabase

    lateinit var userDao: UserDao

    @Before
    override fun setUp() {
        super.setUp()
        userDao= UserDaoImpl()
        every { userDatabase.userDao() } returns userDao
    }

    @Test
    fun userDao() {
      val userDao=  userDatabase.userDao()
        assertNotNull(userDao)
        userDao.insertUserList(listOf(User(1,"https://test.com/avatar.png", "abc@gm.co", "Abhi", "Dube")))
        assertEquals(userDao.getUser().size, 1)
    }


    inner class UserDaoImpl: UserDao{

        var userList =  ArrayList<User>()
        override fun getUser(): List<User> {
             return userList
        }

        override fun insertUserList(users: List<User>) {
            userList.addAll(users)
        }

    }


}