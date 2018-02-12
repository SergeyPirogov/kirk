package com.automation.remarks.kirk

import com.automation.remarks.kirk.conditions.Condition
import com.automation.remarks.kirk.conditions.not
import com.automation.remarks.kirk.core.KirkEventListener
import com.automation.remarks.kirk.core.waitFor
import com.automation.remarks.kirk.locators.ElementLocator
import org.openqa.selenium.WebDriver

/**
 * Created by sergey on 09.07.17.
 */
abstract class Element<out T>(protected val locator: ElementLocator<T>,
                              protected val driver: WebDriver) {
    var waitTimeout: Int = 4000
    var waitPoolingInterval: Double = 0.1
    var eventListener: KirkEventListener? = null


    protected fun should(condition: Condition<T>, waitTimeout: Int) {
        try {
            waitFor(driver, this.locator, condition, waitTimeout, waitPoolingInterval)
        } catch (ex: Exception) {
            eventListener?.onFail(ex)
            throw ex
        }
    }

    protected fun should(condition: Condition<T>) {
        should(condition, waitTimeout)
    }

    protected fun shouldNot(condition: Condition<T>) {
        should(not(condition))
    }

    infix fun shouldHave(condition: Condition<T>) {
        should(condition)
    }

    fun shouldBe(condition: Condition<T>) {
        should(condition)
    }

    fun shouldNotHave(condition: Condition<T>) {
        shouldNot(condition)
    }

    fun shouldNotBe(condition: Condition<T>) {
        shouldNot(condition)
    }
}