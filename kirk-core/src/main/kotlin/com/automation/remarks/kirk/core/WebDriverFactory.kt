package com.automation.remarks.kirk.core

import com.automation.remarks.kirk.Configuration
import com.automation.remarks.kirk.ext.autoClose
import com.automation.remarks.kirk.ext.isAlive
import io.github.bonigarcia.wdm.ChromeDriverManager
import io.github.bonigarcia.wdm.FirefoxDriverManager
import io.github.bonigarcia.wdm.InternetExplorerDriverManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.URI
import java.util.concurrent.ConcurrentHashMap


/**
 * Created by sergey on 25.06.17.
 */
class WebDriverFactory {

    private val driverContainer: MutableMap<Long, WebDriver> = ConcurrentHashMap(4)

    private val CHROME = "chrome"
    private val FIREFOX = "firefox"
    private val INTERNET_EXPLORER = "ie"

    private fun createDriver(): WebDriver {
        val browser = configuration.browserName()
        if (configuration.remoteUrl().isNotBlank()) {
            return createRemoteDriver(browser)
        }

        when (browser) {
            CHROME -> return createChromeDriver()
            FIREFOX -> return createFireFoxDriver()
            INTERNET_EXPLORER -> return createInternetExplorerDriver()

            else -> throw IllegalArgumentException("$browser browserName doesn't support!")
        }
    }

    private fun createChromeDriver(): WebDriver {
        ChromeDriverManager.getInstance().setup()
        val capabilities = getCapabilities()
        return ChromeDriver(capabilities.merge(getOptions()))
    }

    private fun createFireFoxDriver(): WebDriver {
        FirefoxDriverManager.getInstance().setup()
        return FirefoxDriver(getCapabilities())
    }

    private fun createInternetExplorerDriver(): WebDriver {
        InternetExplorerDriverManager.getInstance().setup()
        return InternetExplorerDriver(getCapabilities())
    }

    private fun createRemoteDriver(browser: String): WebDriver {
        val remoteUrl = configuration.remoteUrl()
        var capabilities = DesiredCapabilities()
        when (browser) {
            CHROME -> capabilities = DesiredCapabilities.chrome().merge(getOptions())
            FIREFOX -> capabilities = DesiredCapabilities.firefox()
            INTERNET_EXPLORER -> capabilities = DesiredCapabilities.internetExplorer()
        }
        try {
            return RemoteWebDriver(URI.create(remoteUrl).toURL(), capabilities
                    .merge(getCapabilities()))
        } catch (ex: Exception) {
            System.err.println("""
                ====== Error to start RemoteWebdriver session ======
                $ex
                ====================================================
            """)
            throw ex
        }
    }

    fun setWebDriver(webDriver: WebDriver): WebDriver {
        driverContainer.put(Thread.currentThread().id, webDriver)
        return webDriver
    }

    fun getDriver(): WebDriver {
        val driver = driverContainer[Thread.currentThread().id]
        if (driver != null && driver.isAlive()) {
            return driver
        }
        val newDriver = createDriver()
        newDriver.autoClose(configuration.autoClose())
        return setWebDriver(newDriver)
    }

    private fun getOptions(): DesiredCapabilities {
        val options = ChromeOptions()
        if (configuration.chromeArgs().isNotEmpty()) options.addArguments(configuration.chromeArgs())
        if (!configuration.chromeBin().isNullOrEmpty()) options.setBinary(configuration.chromeBin())
        if (configuration.chromeExtensions().isNotEmpty()) options.addExtensions(configuration.chromeExtensions())

        val capabilities = DesiredCapabilities()
        capabilities.setCapability(ChromeOptions.CAPABILITY, options)

        return capabilities
    }

    private fun getCapabilities(): DesiredCapabilities {
        val capabilities = configuration.capabilities()

        val map = HashMap<String, Any>()
        capabilities
                .map { it.split("=") }
                .forEach { map[it[0]] = convert(it[1]) }

        return DesiredCapabilities(map)
    }

    private fun convert(value: String): Any {
        if (value == "true" || value == "false")
            return value.toBoolean()
        return value
    }
}

val driverFactory = WebDriverFactory()

var configuration: Configuration = loadConfig(Configuration::class)

fun getDriver(): WebDriver {
    return driverFactory.getDriver()
}

fun getListener(): KirkEventListener {
    val listenerClass = configuration.listenerClass()
    return if (listenerClass.isBlank()) {
        AbstractKirkEventListener()
    } else {
        Class.forName(listenerClass).newInstance() as KirkEventListener
    }
}