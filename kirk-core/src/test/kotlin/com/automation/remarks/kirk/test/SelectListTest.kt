package com.automation.remarks.kirk.test

import com.automation.remarks.kirk.Kirk.Companion.drive
import com.automation.remarks.kirk.conditions.multiple
import com.automation.remarks.kirk.conditions.options
import com.automation.remarks.kirk.conditions.selected
import com.automation.remarks.kirk.conditions.size
import com.automation.remarks.kirk.ext.select
import me.tatarka.assertk.assertions.isEqualTo
import org.testng.annotations.Test

/**
 * Created by sergey on 25.07.17.
 */
class SelectListTest : BaseTest() {

    val secondPage = "${url}second_page.html"

    @Test
    fun testCanSelectOptionByText() {
        drive {
            to(secondPage)
            val select = select(".genres")
            select.selectOption("Alt folk")
            select.shouldHave(selected("Alt folk"))
        }
    }

    @Test
    fun testCanSelectOptionByIndex() {
        drive {
            to(secondPage)
            val select = select(".genres")
            select.selectOption(3)
            select.shouldHave(selected("G-Funk"))
        }
    }

    @Test
    fun testSelectIsMultiple() {
        drive {
            to(secondPage)
            select(".genres").shouldBe(multiple)
        }
    }

    @Test
    fun testCanGetAllOption() {
        drive {
            to(secondPage)
            select(".genres").shouldHave(options("Alt folk", "Chiptunes", "Electroclash", "G-Funk", "Hair metal"))
        }
    }
    @Test fun testCanSelectOptionByValue() {
        drive {
            to(secondPage)
            val select = select(".genres")
            select.selectOptionByValue("2")
            select.selectOptionByValue("3")
            me.tatarka.assertk.assert(select.firstSelectedOption.text).isEqualTo("Chiptunes")
        }
    }
    @Test fun testCanDeselectOptionByValue() {
        drive {
            to(secondPage)
            val select = select(".genres")
            select.selectOption(3)
            select.deselect(3)
            me.tatarka.assertk.assert(select.allSelectedOptions).equals(size(0))
        }
    }
    @Test fun testCanDeselectOptionByIndex() {
        drive {
            to(secondPage)
            val select = select(".genres")
            select.selectOptionByValue("2")
            select.deselectOptionByValue("2")
            me.tatarka.assertk.assert(select.allSelectedOptions).equals(size(0))
        }
    }
    @Test fun testCanDeselectOptionByText() {
        drive {
            to(secondPage)
            val select = select(".genres")
            select.selectOption("G-Funk")
            select.deselect("G-Funk")
            me.tatarka.assertk.assert(select.allSelectedOptions).equals(size(0))
        }
    }
    @Test fun testCanDeselectAll() {
        drive {
            to(secondPage)
            val select = select(".genres")
            select.selectOption("Alt folk")
            select.selectOptionByValue("2")
            select.selectOption(3)
            me.tatarka.assertk.assert(select.allSelectedOptions).equals(size(3))
            select.deselectAll()
            me.tatarka.assertk.assert(select.allSelectedOptions).equals(size(0))
        }
    }
}