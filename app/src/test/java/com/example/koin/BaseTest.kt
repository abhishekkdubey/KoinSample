package com.example.koin

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Before

open class BaseTest {

    @MockK
    lateinit var mContext: Context

    @MockK
    lateinit var mResource: Resources


    private val sharedPref = mockk<SharedPreferences> {
        every { getString(any(), any()) } returns ""
        every { edit().putString(any(), any()).apply() } just Runs
    }


    protected fun mockAnnotations(aRelaxUnitFun: Boolean = true) {
        MockKAnnotations.init(this, relaxUnitFun = aRelaxUnitFun)
    }


    @Before
    open fun setUp()
    {
        mockAnnotations()
        mockContext()

        every {
            mContext.getSharedPreferences(
                any(), Context.MODE_PRIVATE
            )
        } returns sharedPref

    }


    protected fun mockContext()
    {
        every { mContext.applicationContext } returns mContext
        every { mContext.resources } returns mResource
    }

}