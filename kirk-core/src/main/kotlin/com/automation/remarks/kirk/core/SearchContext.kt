package com.automation.remarks.kirk.core

import com.automation.remarks.kirk.KElement
import com.automation.remarks.kirk.KElementCollection
import org.openqa.selenium.By

/**
 * Created by sergey on 09.07.17.
 */
private val STARTING_BRACES = "(\\s*\\(\\s*)*"
private val XPATH_AXES = "ancestor|ancestor-or-self|attribute|child|descendant|descendant-or-self|following|following-sibling|parent|preceding|preceding-sibling|self"
private val XPATH_EXPRESSION_PATTERN = Regex("$STARTING_BRACES(/|($XPATH_AXES)::).*")

interface SearchContext {

    fun element(locator: String): KElement {
        if (XPATH_EXPRESSION_PATTERN.matches(locator))
            return element(By.xpath(locator))
        else
            return element(By.cssSelector(locator))
    }

    fun element(by: By): KElement

    fun all(cssLocator: String) = all(By.cssSelector(cssLocator))

    fun all(by: By): KElementCollection
}
