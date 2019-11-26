package com.automation.remarks.kirk.test.example.todo

import com.automation.remarks.kirk.Browser
import com.automation.remarks.kirk.Page
import com.automation.remarks.kirk.core.byXpath
import com.automation.remarks.kirk.ext.hover

/**
 * Created by sergey on 09.07.17.
 */
// tag::TodoPage[]
class TodoPage(browser: Browser) : Page(browser) {
    override val url: String?
        get() = "http://todomvc.com/examples/angularjs/"

    val counter = element(".todo-count strong")
    val taskList = all("label.ng-binding")

    fun addTasks(vararg tasks: String) {
        for (task in tasks) {
            element("input.new-todo")
                    .setValue(task)
                    .pressEnter()
        }
    }

    fun deleteTask(name: String) {
        browser.interact { hover(element(".todo-list li div input")) }
        element(byXpath("//label[text()='$name']/following-sibling::button"))
                .click()
    }

    fun deactivateTask(vararg tasks: String) {
        for (task in tasks) {
            element(byXpath("//label[text()='$task']/preceding-sibling::input"))
                    .webElement //instead of KElemenet.click() because opacity:0 consequently webElement.isDisplayed() == false
                    .click()
        }
    }

    fun goToCompletedTab() {
        element(".filters li:nth-child(3) a").click()
    }
}
// end::TodoPage[]