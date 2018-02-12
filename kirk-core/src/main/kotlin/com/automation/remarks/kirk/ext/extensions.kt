@file: JvmName("Extensions")

package com.automation.remarks.kirk.ext

import com.automation.remarks.kirk.*
import com.automation.remarks.kirk.core.*
import org.apache.commons.io.FileUtils
import org.openqa.selenium.*
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.logging.LogEntries
import org.openqa.selenium.remote.UnreachableBrowserException
import java.io.File
import kotlin.reflect.KClass

/**
 * Created by sergey on 09.07.17.
 */
@JvmOverloads
fun WebDriver.autoClose(enabled: Boolean = true): WebDriver {
    if (enabled) {
        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() = quit()
        })
    }
    return this
}

fun WebDriver.saveScreenshot(path: String = "${System.getProperty("user.dir")}/build/reports/screen_${System.currentTimeMillis()}.png"): File {
    val scrFile = (this as TakesScreenshot).getScreenshotAs(OutputType.FILE)
    val screenshot = File(path)
    FileUtils.copyFile(scrFile, screenshot)
    screenshots[Thread.currentThread().id] = screenshot
    return screenshot
}

fun WebDriver.isAlive(): Boolean {
    return try {
        title
        true
    } catch (e: UnreachableBrowserException) {
        false
    } catch (e: NoSuchWindowException) {
        false
    } catch (e: NoSuchSessionException) {
        false
    }
}

val WebElement.classes: List<String>
    get() = this.getAttribute("class").split(" ")

fun <T : Configuration> Kirk.Companion.withConfig(klazz: KClass<T>): Kirk.Companion {
    configuration = loadConfig(klazz)
    return this
}

fun Browser.select(cssLocator: String): Select {
    return select(By.cssSelector(cssLocator))
}

fun Browser.select(by: By): Select {
    return Select(element(by))
}

fun Browser.atFrame(cssLocator: String): Browser {
    return toFrame(cssLocator)
}

fun Actions.hover(element: KElement) {
    this.moveToElement(element.webElement)
}

fun Actions.click(element: KElement) {
    this.click(element.webElement)
}

fun WebDriver.logs(logType: String): LogEntries {
    if (this is ChromeDriver) {
        return this.manage().logs().get(logType)
    } else {
        throw UnsupportedOperationException()
    }
}

fun Browser.s(locator: String): KElement {
    return if (isXpath(locator))
        element(By.xpath(locator))
    else
        element(locator)
}

fun Browser.ss(locator: String): KElementCollection {
    return if (isXpath(locator))
        all(By.xpath(locator))
    else
        all(locator)
}

fun KElement.s(locator: String): KElement {
    return if (isXpath(locator))
        element(By.xpath(locator))
    else
        element(locator)
}

fun KElement.ss(locator: String): KElementCollection {
    return if (isXpath(locator))
        all(By.xpath(locator))
    else
        all(locator)
}