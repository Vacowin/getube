package com.vacowin.getube.intents

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.intent.matcher.IntentMatchers.toPackage
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import com.vacowin.getube.MainActivity
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created by Nguyen Cong Van on 2020-05-17.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityIntentTest {

    @get:Rule
    var mActivityRule = IntentsTestRule(MainActivity::class.java)

    private lateinit var device: UiDevice

    private val LAUNCH_TIMEOUT = 5000L

    @Before
    fun setUp() {
        device = UiDevice.getInstance(getInstrumentation())

        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage("com.vacowin.getube").apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "https://www.youtube.com/playlist?list=PLknSwrodgQ72X4sKpzf5vT8kY80HKcUSe")
        }

        context.startActivity(intent)
        device.wait(Until.hasObject(By.pkg("com.vacowin.getube").depth(0)), LAUNCH_TIMEOUT)

    }

    @Test
    fun testIntentReceive() {
        //Log.d("VCN", "Aaa00")
        intended(allOf(
            toPackage("com.vacowin.getube"),
            hasExtra(Intent.EXTRA_TEXT, "https://www.youtube.com/playlist?list=PLknSwrodgQ72X4sKpzf5vT8kY80HKcUSe")
        ));
    }
}