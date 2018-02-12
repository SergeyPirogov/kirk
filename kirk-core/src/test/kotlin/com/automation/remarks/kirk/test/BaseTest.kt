package com.automation.remarks.kirk.test

import com.automation.remarks.kirk.test.helpers.JettyServer
import io.github.bonigarcia.wdm.FirefoxDriverManager
import me.tatarka.assertk.Assert
import me.tatarka.assertk.AssertBlock
import me.tatarka.assertk.assertions.hasClass
import org.testng.annotations.AfterSuite
import org.testng.annotations.BeforeSuite
import kotlin.reflect.KClass

/**
 * Created by sepi on 6/27/2017.
 */
abstract class BaseTest {

    val jetty = JettyServer(8086)
    val url: String
        get() = jetty.url


    @BeforeSuite
    fun runServer() {
        FirefoxDriverManager.getInstance().setup()
        jetty.runServer()
    }

    @AfterSuite
    fun tearDown() {
        jetty.stop()
    }

    fun <T : Any> assertExceptionThrown(kclass: KClass<out T>, closure: () -> Unit) {
        me.tatarka.assertk.assert {
            closure()
        }.throwsError {
            it.hasClass(kclass)
        }
    }

    fun <T> assertThat(f: () -> T): AssertBlock<T> {
        return me.tatarka.assertk.assert(f)
    }

    fun <T> assertThat(actual: T): Assert<T> {
        return me.tatarka.assertk.assert(actual)
    }
}