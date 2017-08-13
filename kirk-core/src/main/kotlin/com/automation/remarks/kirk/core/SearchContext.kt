package com.automation.remarks.kirk.core

import com.automation.remarks.kirk.KElement
import com.automation.remarks.kirk.KElementCollection
import org.openqa.selenium.By

/**
 * Created by sergey on 09.07.17.
 */
val XPATH_EXPRESSION_PATTERN = Regex(".*\\/\\/.*\$")

interface SearchContext {

    fun element(locator: String): KElement {
        if (XPATH_EXPRESSION_PATTERN.matches(locator))
            return element(By.xpath(locator))
        else
            return element(By.cssSelector(locator))
    }

    fun element(by: By): KElement

    fun all(locator: String): KElementCollection {
        if (XPATH_EXPRESSION_PATTERN.matches(locator))
            return all(By.xpath(locator))
        else
            return all(By.cssSelector(locator))
    }

    fun all(by: By): KElementCollection
}
