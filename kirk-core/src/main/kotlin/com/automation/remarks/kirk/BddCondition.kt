package com.automation.remarks.kirk

import com.automation.remarks.kirk.conditions.Condition
import com.automation.remarks.kirk.conditions.Not
import com.automation.remarks.kirk.conditions.Text
import com.automation.remarks.kirk.conditions.Visibility
import com.automation.remarks.kirk.core.waitFor
import com.automation.remarks.kirk.locators.ElementLocator
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class KElementCondition(private val driver: WebDriver,
                        private val locator: ElementLocator<WebElement>,
                        private val waitTimeout: Int,
                        private val waitPoolingInterval: Double) {

    private var inverted: Boolean = false

    val and: KElementCondition
        get() {
            return this
        }

    val not: KElementCondition
        get() {
            inverted = true
            return this
        }

    val have: KElementCondition
        get() {
            return this
        }

    val be: KElementCondition
        get() {
            return this
        }

    fun text(text: String) {
        verify(Text(text))
    }

    private fun verify(condition: Condition<WebElement>) {
        var elementCondition = condition
        if (inverted) {
            elementCondition = Not(condition)
        }

        try {
            waitFor(driver, locator, elementCondition, waitTimeout, waitPoolingInterval)
        } catch (ex: Exception) {
            //eventListener?.onFail(ex)
            throw ex
        }
    }

    val visible: KElementCondition
        get() {
            Visibility()
            return this
        }

}