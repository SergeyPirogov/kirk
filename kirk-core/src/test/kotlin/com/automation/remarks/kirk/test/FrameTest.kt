package com.automation.remarks.kirk.test

import com.automation.remarks.kirk.Kirk.Companion.drive
import com.automation.remarks.kirk.conditions.visible
import com.automation.remarks.kirk.ext.atFrame
import org.testng.annotations.Test

/**
 * Created by sepi on 26.08.17.
 */
class FrameTest : BaseTest() {

    @Test
    fun testCanSwitchToFrameElement() {
        drive {
            open(url)
            toFrame(element("#test_frame"))
            element(".genres").shouldBe(visible)
        }
    }

    @Test
    fun testCanSwitchToFrameLocator() {
        drive {
            open(url)
            atFrame("#test_frame")
                    .element(".genres")
                    .shouldBe(visible)
        }
    }

}