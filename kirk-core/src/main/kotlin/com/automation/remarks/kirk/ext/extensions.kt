@file: JvmName("Extensions")

package com.automation.remarks.kirk.ext

import com.automation.remarks.kirk.Browser
import com.automation.remarks.kirk.Configuration
import com.automation.remarks.kirk.KElement
import com.automation.remarks.kirk.Kirk
import com.automation.remarks.kirk.core.*
import org.apache.commons.io.FileUtils
import org.openqa.selenium.*
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.logging.LogEntries
import org.openqa.selenium.logging.LogType
import org.openqa.selenium.remote.UnreachableBrowserException
import java.io.File
import java.io.FileNotFoundException
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
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
    try {
        title
        return true
    } catch (e: UnreachableBrowserException) {
        return false
    } catch (e: NoSuchWindowException) {
        return false
    } catch (e: NoSuchSessionException) {
        return false
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

fun KElement.download(){
    val path: String
    val destination: Path
    try {
        path = this.attr("href")
    } catch (e: IllegalStateException) {
        throw NotImplementedError("Download in beta mode.\n Possible to download file only from href param.")
    }
    val filename = Paths.get(path).fileName.toString()
    if (configuration.outputPath().isBlank())
        destination = Paths.get(System.getProperty("user.dir") +
                File.separator + "build" + File.separator + "download", filename)
    else
        destination = Paths.get(configuration.outputPath(), filename)
    try {

        URL(path).openStream().use({ target ->
            Files.createDirectories(destination.parent)
            Files.copy(target, destination, StandardCopyOption.REPLACE_EXISTING)
        })
    } catch (e: FileNotFoundException) {
        throw FileNotFoundException("Destination file not found!\n" + "href=$path\n" + this.toString())
    }
    println("Download file: $destination")
}