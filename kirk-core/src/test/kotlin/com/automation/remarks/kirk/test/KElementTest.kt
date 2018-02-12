package com.automation.remarks.kirk.test

import com.automation.remarks.kirk.Browser
import com.automation.remarks.kirk.Kirk.Companion.drive
import com.automation.remarks.kirk.conditions.cssClass
import com.automation.remarks.kirk.conditions.exactText
import com.automation.remarks.kirk.conditions.size
import com.automation.remarks.kirk.conditions.text
import com.automation.remarks.kirk.ext.hover
import com.automation.remarks.kirk.ext.s
import com.automation.remarks.kirk.ext.ss
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
        Browser().apply {
            to(url)
            // tag::composition[]
            element("ul.list").all("li").shouldHave(size(3))
            // end::composition[]
        }
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

    @Test
    fun testCanTest() {
        drive {
            to(url)
            element("#invisible_link")
                    .should
                    .have
                    .text("Invisible link")
        }
    }
}


val KElement.should: ExpectAny<KElement>
    get() {
        return ExpectAny(this)
    }

class ExpectAny<T>(private val subject: KElement) {

    val have: ExpectAny<T>
        get() {
            return this
        }

    fun text(text: String) {
        subject.shouldHave(com.automation.remarks.kirk.conditions.text(text))
    }
}


    @Test fun testCanRecognizeLocator() {
        drive {
            to(url)
            s("#header").shouldHave(text("Kirk"))
            s("//*[@id='header']").shouldHave(text("Kirk"))
            assertThat(s("//*[@class='inner_link']/parent::div")).equals(element("#parent_div"))
            assertThat(s(".list > li:nth-child(1)").text).isEqualTo("Один")
            assertThat(s(".//*[@class='list']/li[1]").text).isEqualTo("Один")
            s("ul.list").all("li").shouldHave(size(3))
            s(".//ul[@class='list']").ss(".//li").shouldHave(size(3))
            ss(".//*[@class='list']/li").shouldHave(size(3))
        }
    }
}