package com.example.newstestapi

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.annotation.VisibleForTesting
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.newstestapi.manager.Navigator
import com.example.newstestapi.viewModel.NewsViewModel
import java.util.concurrent.Executor

@VisibleForTesting
open class MainActivity : FragmentActivity() {

    private lateinit var executor: Executor
    private val viewModel = NewsViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        executor = ContextCompat.getMainExecutor(this)

        val biometricManager = BiometricManager.from(this)

        val canAuthenticate = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)

        if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS) {
            showBiometricPrompt {
                launchAppUI()
            }
        } else {
            // No biometric hardware or not enrolled: skip auth
            launchAppUI()
        }
    }

    protected fun launchAppUI() {
        viewModel.fetchArticles()

        setContent {
            val articles by viewModel.articles.collectAsState()

            Navigator(
                articles = articles,
                onGetArticleByUrl = { url -> viewModel.getArticleByUrl(url) }
            )
        }
    }

    protected open fun showBiometricPrompt(onSuccess: () -> Unit) {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.require_fingerPrint))
            .setSubtitle(getString(R.string.require_fingerPrint_subtitle))
            .setNegativeButtonText("Cancel")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
            .build()

        val biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onSuccess()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(applicationContext, getString(R.string.error_fingerPrint_check), Toast.LENGTH_SHORT).show()
                    finish()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, getString(R.string.failed_fingerPrint_check), Toast.LENGTH_SHORT).show()
                }
            })

        biometricPrompt.authenticate(promptInfo)
    }
}
