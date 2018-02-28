package com.automation.remarks.kirk.conditions

import com.automation.remarks.kirk.core.configuration
import com.automation.remarks.kirk.core.waitFor
import com.automation.remarks.kirk.ex.ConditionMismatchException
import com.automation.remarks.kirk.locators.ElementLocator
import org.apache.commons.lang3.StringUtils
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

/**
 * Created by sergey on 24.06.17.
 */
class ConditionAssert {

    companion object {
        fun <T> evaluate(item: T, matcher: Condition<T>) {
            if (!matcher.matches(item)) {
                throw ConditionMismatchException(matcher.description(item).toString())
            }
        }
    }
}

abstract class Condition<in T> {

    abstract fun matches(item: T): Boolean

    abstract fun description(item: T): Description

    override fun toString(): String {
        return StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(this.javaClass.simpleName), " ").toLowerCase()
    }
}

