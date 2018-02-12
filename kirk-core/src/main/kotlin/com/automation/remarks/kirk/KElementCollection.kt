package com.automation.remarks.kirk

import com.automation.remarks.kirk.conditions.CollectionCondition
import com.automation.remarks.kirk.conditions.ElementCondition
import com.automation.remarks.kirk.conditions.empty
import com.automation.remarks.kirk.conditions.sizeAtLeast
import com.automation.remarks.kirk.locators.CachedElementCollectionLocator
import com.automation.remarks.kirk.locators.CachedWebElementLocator
import com.automation.remarks.kirk.locators.ElementLocator
import com.automation.remarks.kirk.locators.WebElementListLocator
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

/**
 * Created by sergey on 28.06.17.
 */
class KElementCollection(locator: ElementLocator<List<WebElement>>,
                         driver: WebDriver) :
        Element<List<WebElement>>(locator, driver), Collection<KElement> {

    constructor(locator: By, driver: WebDriver)
            : this(WebElementListLocator(locator, driver), driver)

    val webElements: List<WebElement>
        get() = locator.find()

    override val size: Int
        get() = webElements.size

    infix fun should(condition: CollectionCondition) {
        super.should(condition)
    }

    infix fun shouldNot(condition: CollectionCondition) {
        super.shouldNotBe(empty)
        super.shouldNot(condition)
    }

    fun waitUntil(condition: CollectionCondition, timeout: Int = this.waitTimeout): KElementCollection {
        this.should(condition, timeout)
        return this
    }

    override fun isEmpty(): Boolean {
        return webElements.isEmpty()
    }

    override fun iterator(): Iterator<KElement> = object : Iterator<KElement> {
        var index = 0

        override fun hasNext(): Boolean {
            return webElements.size > this.index
        }

        override fun next(): KElement {
            val indexedElement = get(index)
            this.index += 1
            return indexedElement
        }
    }

    override fun toString(): String {
        return locator.description
    }

    override fun contains(element: KElement): Boolean {
        throw NotImplementedError()
    }

    override fun containsAll(elements: Collection<KElement>): Boolean {
        throw NotImplementedError()
    }

    operator fun get(index: Int): KElement {
        shouldHave(sizeAtLeast(index))
        return KElement(CachedWebElementLocator(locator, index), driver)
    }

    fun filterBy(condition: ElementCondition): KElementCollection {
        return KElementCollection(CachedElementCollectionLocator(this.filter { condition.matches(it.webElement) }, "$this and filtered by $condition"), driver)
    }
}