package com.automation.remarks.kirk

import org.aeonbits.owner.Config
import org.aeonbits.owner.Config.*
import java.io.File

/**
 * Created by sergey on 25.06.17.
 */
@Sources("classpath:kirk.properties")
interface Configuration : Config {

    @Key("kirk.browser")
    @DefaultValue("chrome")
    fun browserName(): String

    @Key("kirk.timeout")
    @DefaultValue("4000")
    fun timeout(): Int

    @Key("kirk.poolingInterval")
    @DefaultValue("0.1")
    fun poolingInterval(): Double

    @Key("kirk.autoClose")
    @DefaultValue("true")
    fun autoClose(): Boolean

    @DefaultValue("")
    @Key("kirk.screenSize")
    fun screenSize(): List<Int>

    @Key("kirk.baseUrl")
    fun baseUrl(): String?

    /**
     * For use <headless> chrome options, set property "kirk.startMaximized=false"
     * */
    @Separator(",")
    @DefaultValue("")
    @Key("kirk.chrome.args")
    fun chromeArgs(): List<String>

    /**
     * Chrome binary property example: -Dkirk.chrome.bin="path/to/your/chrome/bin"
     * */
    @Key("kirk.chrome.binary")
    fun chromeBin(): String

    @Separator(",")
    @DefaultValue("")
    @Key("kirk.chrome.extensions")
    fun chromeExtensions(): List<File>

    @Key("kirk.report.dir")
    fun reportDir(): String

    /**
     * Highlight Element style
     * */
    @Key("kirk.highlight.border")
    @DefaultValue("true")
    fun highlightBorder(): Boolean

    @Key("kirk.highlight.style")
    @DefaultValue("dotted")
    fun highlightStyle(): String

    @Key("kirk.highlight.size")
    @DefaultValue("2px")
    fun highlightSize(): String

    @Key("kirk.highlight.color")
    @DefaultValue("red")
    fun highlightColor(): String

    @Separator(",")
    @DefaultValue("")
    @Key("kirk.desired.capabilities")
    fun capabilities(): List<String>

    @Key("kirk.remote.url")
    @DefaultValue("")
    fun remoteUrl(): String

    @Key("kirk.listener")
    @DefaultValue("")
    fun listenerClass(): String
}