package com.automation.remarks.kirk

import com.automation.remarks.kirk.core.PageClass
import com.automation.remarks.kirk.core.SearchContext
import com.automation.remarks.kirk.core.Select
import com.automation.remarks.kirk.ext.select


/**
 * Created by sergey on 24.06.17.
 */
abstract class Page(var browser: Browser) : SearchContext by browser {

    open val url: String? = null
    open val isAt: Browser.() -> Boolean = { true }
    val currentUrl: String
        get() = browser.currentUrl

    fun select(cssLocator: String): Select {
        return browser.select(cssLocator)
    }

    fun <T : Page> page(pageClass: PageClass<T>): T {
        return browser.at(pageClass)
    }
}
