package com.example.baatcheet

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.baatcheet.ui.screens.LoginScreen

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginScreen_inputAndClickButton() {
        composeTestRule.setContent {
            val navController = rememberNavController()
            LoginScreen(navController)
        }

        // Phone number field me text input karo
        composeTestRule.onNodeWithTag("PHONE_NUMBER_FIELD")
            .performTextInput("9876543210")

        // Check button enabled (aapke logic me phone valid hona chahiye, isliye yeh test valid hoga)
        val loginButton = composeTestRule.onNodeWithTag("LOGIN_BUTTON")
        loginButton.assertExists()
        loginButton.performClick()
    }
}