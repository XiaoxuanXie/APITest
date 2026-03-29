package com.example.myapplication

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentGithubProfileBinding

class GitHubProfileFragment : Fragment() {

    private var _binding: FragmentGithubProfileBinding? = null
    private val binding get() = _binding!!

    private var mainFrameLoadFailed = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGithubProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureWebView()
        binding.retryButton.setOnClickListener {
            loadProfileUrl()
        }
        loadProfileUrl()
    }

    private fun configureWebView() {
        binding.webviewGithub.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
            cacheMode = WebSettings.LOAD_DEFAULT
            userAgentString = CHROME_MOBILE_USER_AGENT
        }

        binding.webviewGithub.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                mainFrameLoadFailed = false
                showLoadingState()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                binding.progressGithub.isVisible = false
                if (!mainFrameLoadFailed) {
                    binding.errorContainer.isVisible = false
                }
            }

            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest,
                error: WebResourceError
            ) {
                if (request.isForMainFrame) {
                    mainFrameLoadFailed = true
                    binding.progressGithub.isVisible = false
                    binding.errorContainer.isVisible = true
                    binding.errorMessage.text = getString(
                        R.string.github_webview_load_error,
                        error.description.toString()
                    )
                }
            }

            override fun onReceivedHttpError(
                view: WebView,
                request: WebResourceRequest,
                errorResponse: WebResourceResponse
            ) {
                if (request.isForMainFrame && errorResponse.statusCode >= 400) {
                    mainFrameLoadFailed = true
                    binding.progressGithub.isVisible = false
                    binding.errorContainer.isVisible = true
                    binding.errorMessage.text = getString(
                        R.string.github_webview_load_error,
                        "${errorResponse.statusCode}"
                    )
                }
            }
        }
    }

    private fun showLoadingState() {
        binding.progressGithub.isVisible = true
        binding.errorContainer.isVisible = false
    }

    private fun loadProfileUrl() {
        mainFrameLoadFailed = false
        showLoadingState()
        binding.webviewGithub.loadUrl(getString(R.string.github_profile_url))
    }

    override fun onDestroyView() {
        binding.webviewGithub.apply {
            loadUrl("about:blank")
            clearHistory()
            (parent as? ViewGroup)?.removeView(this)
            destroy()
        }
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val CHROME_MOBILE_USER_AGENT =
            "Mozilla/5.0 (Linux; Android 13; Mobile) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/120.0.0.0 Mobile Safari/537.36"
    }
}
