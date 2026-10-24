package Minhag.basher

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private var swipe: SwipeRefreshLayout? = null

    private fun dp(v: Int): Int {
        return (v * resources.displayMetrics.density).toInt()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splash = LinearLayout(this)
        splash.orientation = LinearLayout.VERTICAL
        splash.gravity = Gravity.CENTER
        splash.setBackgroundColor(Color.parseColor("#0B1020"))
        val icon = ImageView(this)
        icon.setImageResource(R.mipmap.ic_launcher)
        val iconSize = dp(120)
        val gd = GradientDrawable()
        gd.cornerRadius = dp(24).toFloat()
        gd.setColor(Color.parseColor("#0B1020"))
        icon.background = gd
        splash.addView(icon, LinearLayout.LayoutParams(iconSize, iconSize))
        val loaderKind = "spinner"
        if (loaderKind != "none") {
            val bar = ProgressBar(this, null, if (loaderKind == "bar") android.R.attr.progressBarStyleHorizontal else android.R.attr.progressBarStyleLarge)
            bar.isIndeterminate = true
            val indSize = dp(36)
            val gapParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            gapParams.topMargin = dp(16)
            if (loaderKind == "bar") {
                bar.layoutParams = LinearLayout.LayoutParams(indSize * 3, dp(8))
            } else {
                bar.layoutParams = LinearLayout.LayoutParams(indSize, indSize)
            }
            splash.addView(bar, gapParams)
        }
        setContentView(splash)
        Handler(Looper.getMainLooper()).postDelayed({ showMain() }, 1200)
    }

    private fun showMain() {
        val root = LinearLayout(this)
        root.orientation = LinearLayout.VERTICAL
        val frame = FrameLayout(this)
        frame.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f)
        webView = WebView(this)
        val ws: WebSettings = webView.settings
        ws.javaScriptEnabled = true
        ws.domStorageEnabled = true
        ws.mediaPlaybackRequiresUserGesture = false
        ws.allowFileAccess = true
        var topBar: ProgressBar? = null
        if (true) {
            topBar = ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal)
            topBar.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp(4))
            root.addView(topBar)
        }
        root.addView(frame)
        if (true) {
            swipe = SwipeRefreshLayout(this)
            swipe!!.addView(webView, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT))
            swipe!!.setOnRefreshListener { webView.reload() }
            frame.addView(swipe)
        } else {
            frame.addView(webView)
        }
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                if (topBar != null) {
                    topBar!!.progress = newProgress
                    topBar!!.visibility = if (newProgress >= 100) android.view.View.GONE else android.view.View.VISIBLE
                }
                if (newProgress >= 100) {
                    swipe?.isRefreshing = false
                }
            }
        }
        webView.webViewClient = WebViewClient()
        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState)
        } else {
            webView.loadUrl("file:///android_asset/index.html")
        }
        if (true) {
            if (android.os.Build.VERSION.SDK_INT >= 30) {
                window.insetsController?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
            } else {
                window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
            }
        }
        setContentView(root)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (::webView.isInitialized) {
            webView.saveState(outState)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (::webView.isInitialized && webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
