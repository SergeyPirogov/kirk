package com.automation.remarks.kirk.test

import com.automation.remarks.kirk.Browser
import com.automation.remarks.kirk.KElement
import com.automation.remarks.kirk.Kirk.Companion.drive
import com.automation.remarks.kirk.conditions.*
import com.automation.remarks.kirk.ext.*
import me.tatarka.assertk.assertions.isEqualTo
import org.testng.annotations.Test

/**
 * Created by sepi on 7/11/2017.
 */
class KElementTest : BaseTest() {

    @Test fun testCanFindFirstChild() {
        // tag::child[]
        drive {
            to(url)
            s("ul").firstChild().shouldHave(text("Один"))
            s("ul").lastChild().shouldHave(text("Три"))
        }
        // end::child[]
    }

    @Test fun testCanFindLastChild() {
        drive {
            to(url)
            s("ul").lastChild().shouldHave(text("Три"))
        }
    }

    @Test fun testCanFindFirstParent() {
        // tag::parent[]
        drive {
            to(url)
            s("div.b").parent().shouldHave(cssClass("a"))
        }
        // end::parent[]
    }

    @Test fun testCanFindChildren() {
        drive {
            to(url)
            element("ul#with_children").children("li").shouldHave(exactText("1", "2", "2.1", "2.2", "3", "3.1", "3.2"))
        }
    }

    @Test fun testCanUploadFile() {
        // tag::uploadFile[]
        drive {
            element("input").uploadFile("")
        }
        // end::uploadFile[]
    }

    @Test fun testCanCompose() {
        val browser = Browser().apply { to(url) }
        // tag::composition[]
        browser.element("ul.list").all("li").shouldHave(size(3))
        // end::composition[]
    }

    @Test fun testCanScrollToElement() {
        drive {
            to(url)
            interact {
                scrollTo(element("#invisible_link"))
                hover(element("#invisible_link"))
            }
        }
    }
    @Test fun testCanRecognizeLocator() {
        drive {
            to(url)
            element("#header").shouldHave(text("Kirk"))
            element("//*[@id='header']").shouldHave(text("Kirk"))
            me.tatarka.assertk.assert(element(".//*[@class='inner_link']/parent::div")).equals(element("#parent_div"))
            me.tatarka.assertk.assert(element(".list > li:nth-child(1)").text).isEqualTo("Один")
            me.tatarka.assertk.assert(element(".//*[@class='list']/li[1]").text).isEqualTo("Один")
        }
    }
}

fun Browser.s(cssLocator: String): KElement {
    return element(cssLocator)
}