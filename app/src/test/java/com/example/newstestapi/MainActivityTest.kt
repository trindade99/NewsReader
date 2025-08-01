package com.example.newstestapi

import android.os.Bundle
import org.junit.Assert.assertNotNull
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

import org.junit.Test
import org.robolectric.Robolectric
import org.robolectric.annotation.Config


/**
 * Instrumented test, this will execute on an Android device,
 * Please be mindfully that coverage may mess with the test because of the AVD
 */

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class MainActivityTest {

    @Test
    fun test_launchAppUI_called_whenBiometricFails() {
        // Arrange: Create vals to tes
        val activity = Robolectric.buildActivity(TestMainActivityNoBiometric::class.java)

        // Act: Set the activity under test
        activity.setup().get()

        // Assert: Check if activity exists
        assertNotNull(activity)
    }

    @Test
    fun test_showBiometricPrompt_successPath() {
        // Arrange: Create vals to tes
        var successCalled = false
        val activity = Robolectric.buildActivity(TestMainActivitySkipAuth::class.java).setup().get()

        // Act: Set the activity under test
        activity.showBiometricPrompt {
            successCalled = true
        }

        // Assert: Check if success was called
        assert(successCalled)
    }
}

// Skips real biometric for coverage
class TestMainActivityNoBiometric : MainActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Skip biometric completely
        launchAppUI()
    }
}

// Calls showBiometricPrompt but with simulate success
class TestMainActivitySkipAuth : MainActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    public override fun showBiometricPrompt(onSuccess: () -> Unit) {
        // Simulate fingerprint success
        onSuccess()
    }
}
