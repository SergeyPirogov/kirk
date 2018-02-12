package com.automation.remarks.kirk.test

import com.automation.remarks.kirk.Kirk
import com.automation.remarks.kirk.conditions.attr
import com.automation.remarks.kirk.conditions.elementWithText
import com.automation.remarks.kirk.conditions.visible
import me.tatarka.assertk.assertions.hasClass
import me.tatarka.assertk.assertions.hasMessageStartingWith
import org.openqa.selenium.TimeoutException
import org.testng.annotations.Test

/**
 * Created by sergey on 24.07.17.
 */
class CollectionConditionsTest : BaseTest() {

    @Test
    fun testNotCondition() {
        assertThat {
            Kirk.drive {
                to(url)
                all(".list li").shouldNotHave(elementWithText("Один"))
            }
        }.throwsError {
            it.hasClass(TimeoutException::class)
            it.hasMessageStartingWith("""
            failed while waiting 4 seconds
            to assert collection have element with text
            for collection located {By.cssSelector: .list li}
            reason: condition did not match
                expected not: Один
                actual: [Один, Два, Три]
            """)
        }
    }

    @Test
    fun testCollectionMinimumSizConditionThenBoundaryValueMore() {
        assertThat {
            Kirk.drive {
                to(url)
                all("li")[10].shouldBe(visible)
            }
        }.throwsError {
            it.hasClass(TimeoutException::class)
            it.hasMessageStartingWith("""
            failed while waiting 4 seconds
            to assert collection minimum size
            for collection located {By.cssSelector: li}
            reason: required index 9 exceeds collections size
                expected: [10]
                actual: [9]
            """)
        }
    }

    @Test
    fun testCollectionMinimumSizConditionBoundaryValue() {
        Kirk.drive {
            to(url)
            all("li")[9].shouldBe(visible)
        }
    }

    @Test
    fun testCollectionMinimumSizConditionThenBoundaryValueLess() {
        Kirk.drive {
            to(url)
            all("li")[8].shouldBe(visible)
        }
    }

    @Test
    fun testCanHandleExceptionForIndexedElement() {
        assertThat {
            Kirk.drive {
                to(url)
                all("input[type='button']")[2].shouldHave(attr("value", "Запустит"))
            }
        }.throwsError {
            it.hasMessageStartingWith("""
            failed while waiting 4 seconds
            to assert attribute {value}
            for collection located {By.cssSelector: input[type='button']}[2]
            reason: condition did not match
                expected: Запустит[]
                actual: Запустит[ь]
            """)
        }

    }
}