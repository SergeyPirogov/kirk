package com.automation.remarks.kirk

import com.automation.remarks.kirk.conditions.ElementCondition
import com.automation.remarks.kirk.conditions.visible
import com.automation.remarks.kirk.ext.classes
import com.automation.remarks.kirk.locators.ElementLocator
import com.automation.remarks.kirk.locators.InnerListWebElementLocator
import com.automation.remarks.kirk.locators.InnerWebElementLocator
import com.automation.remarks.kirk.locators.WebElementLocator
import org.openqa.selenium.*
import java.io.File


/**
 * Created by sergey on 24.06.17.
 */
class KElement(locator: ElementLocator<WebElement>,
               driver: WebDriver) : Element<WebElement>(locator, driver) {

    constructor(locator: By, driver: WebDriver) :
            this(WebElementLocator(locator, driver), driver)

    val webElement: WebElement
        get() = locator.find()

    val text: String
        get() = webElement.text

    val tagName: String
        get() = webElement.tagName

    val isEnabled: Boolean
        get() = webElement.isEnabled

    val isDisplayed: Boolean
        get() = webElement.isDisplayed

    val isSelected: Boolean
        get() = webElement.isSelected

    val location: Point
        get() = webElement.location

    val classes: List<String>
        get() = webElement.classes

    fun attr(name: String): String {
        return webElement.getAttribute(name)
    }

    fun click() {
        execute { click() }
    }

    fun clear() {
        execute { clear() }
    }

    fun sendKeys(vararg keysToSend: CharSequence) {
        execute { sendKeys(*keysToSend) }
    }

    fun uploadFile(name: String) {
        val resource = Thread.currentThread().contextClassLoader.getResource(name)
        this.setValue(File(resource.toURI()).canonicalPath)
    }

    fun pressEnter() {
        sendKeys(Keys.ENTER)
    }

    fun children(locator: String = "*"): KElementCollection {
        return this.all(locator)
    }

    fun firstChild(): KElement {
        return this.element(":first-child")
    }

    fun lastChild(): KElement {
        return this.element(":last-child")
    }

    fun parent(): KElement {
        return this.element(By.xpath(".."))
    }

    infix fun should(condition: ElementCondition) {
        super.should(condition)
    }

    infix fun shouldNot(condition: ElementCondition) {
        super.shouldNot(condition)
    }

    fun waitUntil(condition: ElementCondition, timeout: Int = this.waitTimeout): KElement {
        this.should(condition, timeout)
        return this
    }

    fun setValue(value: String): KElement {
        return execute {
            clear()
            sendKeys(value)
        }
    }

    fun execute(commands: WebElement.() -> Unit): KElement {
        super.shouldBe(visible)
        webElement.commands()
        return this
    }

    override fun toString(): String {
        return locator.description
    }

    fun element(byCss: String): KElement {
        return element(By.cssSelector(byCss))
    }

    fun element(by: By): KElement {
        return KElement(InnerWebElementLocator(by, this), driver)
    }

    fun all(byCss: String): KElementCollection {
        return all(By.cssSelector(byCss))
    }

    fun all(by: By): KElementCollection {
        return KElementCollection(InnerListWebElementLocator(by, this), driver)
    }
}