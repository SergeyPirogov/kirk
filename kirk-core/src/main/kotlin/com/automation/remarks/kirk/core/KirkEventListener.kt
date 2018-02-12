package com.automation.remarks.kirk.core

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

/**
 * Created by sergey on 30.07.17.
 */
interface KirkEventListener {
    fun onStart()
    fun beforeQuit()
    fun beforeNavigation(url: String, driver: WebDriver)
    fun afterNavigation(url: String, driver: WebDriver)
    fun beforeElementLocation(by: By, driver: WebDriver)
    fun onFail(exception: Exception)
}