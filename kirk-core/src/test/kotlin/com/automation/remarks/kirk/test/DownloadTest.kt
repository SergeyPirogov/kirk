package com.automation.remarks.kirk.test

import com.automation.remarks.kirk.Configuration
import com.automation.remarks.kirk.Kirk
import com.automation.remarks.kirk.core.configuration
import com.automation.remarks.kirk.core.loadConfig
import com.automation.remarks.kirk.ext.download
import me.tatarka.assertk.assertions.hasClass
import me.tatarka.assertk.assertions.hasMessageStartingWith
import org.aeonbits.owner.Config
import org.testng.annotations.Test
import java.io.File
import java.io.FileNotFoundException

/**
 * Created by ArtyomAnohin on 12.08.17.
 */

class DownloadTest : BaseTest() {
    @Test fun testCanDownloadCorrectFile() {
        Kirk.drive {
            to(url)
            s("#download").download()
            val path = s("#download").attr("href")
            me.tatarka.assertk.assert(File(path).exists())
            me.tatarka.assertk.assert(File(path).absolutePath).equals(configuration.outputPath())
        }
    }

    @Test fun testCanDownloadCorrectFileToFolder() {
        configuration = loadConfig(CustomOutputPath::class)
        Kirk.drive {
            to(url)
            s("#download").download()
            val path = s("#download").attr("href")
            me.tatarka.assertk.assert(File(path).exists())
            me.tatarka.assertk.assert(File(path).absolutePath).equals(configuration.outputPath())
        }
    }

    @Test fun testCanNotDownloadIncorrectFile() {
        Kirk.drive {
            to(url)
            me.tatarka.assertk.assert {
                s("#download_bad").download()
            }.throwsError {
                it.hasClass(FileNotFoundException::class)
                it.hasMessageStartingWith("Destination file not found!")
            }
        }
    }

    @Test fun testCanNotDownloadFile() {
        Kirk.drive {
            to(url)
            me.tatarka.assertk.assert {
                s("#confirm_btn").download()
            }.throwsError {
                it.hasClass(NotImplementedError::class)
                it.hasMessageStartingWith("Download in beta mode.\n" +
                        " Possible to download file only from href param.")
            }
        }
    }
}

interface CustomOutputPath : Configuration {
    @Config.Key("kirk.output")
    @Config.DefaultValue("./build/download_new")
    override fun outputPath(): String
}