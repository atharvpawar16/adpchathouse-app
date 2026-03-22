package com.adpchathouse.app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.webkit.PermissionRequest;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * MainActivity — primary activity that hosts the ADP Chathouse web app inside a WebView.
 *
 * <p>Responsibilities:
 * <ul>
 *   <li>Initializes and configures the WebView with required settings</li>
 *   <li>Injects {@link AndroidBridge} as a JavaScript interface</li>
 *   <li>Handles image file selection via the system file chooser</li>
 *   <li>Manages runtime permissions for camera and media access</li>
 *   <li>Handles back navigation within the WebView history stack</li>
 * </ul>
 *
 * @author Atharv Pawar
 * @version 1.0.0
 */
public class MainActivity extends AppCompatActivity {

    private WebView webView;

    /** Request code used when asking for runtime permissions. */
    private static final int PERMISSION_REQUEST_CODE = 100;

    /** Callback to deliver selected file URIs back to the WebView file chooser. */
    private ValueCallback<Uri[]> mFilePathCallback;

    /**
     * Activity result launcher for the system image picker.
     * Delivers the selected image URI to the pending WebView file chooser callback.
     */
    private final ActivityResultLauncher<Intent> fileChooserLauncher =
        registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (mFilePathCallback == null) return;
            Uri[] results = null;
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                String dataString = result.getData().getDataString();
                if (dataString != null) {
                    results = new Uri[]{ Uri.parse(dataString) };
                }
            }
            mFilePathCallback.onReceiveValue(results);
            mFilePathCallback = null;
        });

    /**
     * Called when the activity is first created.
     * Sets up the WebView, configures settings, injects the JS bridge,
     * and loads the bundled web app from assets.
     *
     * @param savedInstanceState previously saved instance state, or null
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestNecessaryPermissions();

        webView = findViewById(R.id.webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDisplayZoomControls(false);

        webView.setWebViewClient(new WebViewClient());
        webView.addJavascriptInterface(new AndroidBridge(this), "AndroidBridge");

        webView.setWebChromeClient(new WebChromeClient() {
            /**
             * Grants all permission requests from the web app (camera, microphone, etc.).
             *
             * @param request the permission request from the web content
             */
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                request.grant(request.getResources());
            }

            /**
             * Handles file chooser requests from the web app (e.g. image upload).
             *
             * @param webView            the WebView instance
             * @param filePathCallback   callback to deliver selected file URIs
             * @param fileChooserParams  parameters describing the file chooser request
             * @return true if the request was handled
             */
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
                                             FileChooserParams fileChooserParams) {
                if (mFilePathCallback != null) {
                    mFilePathCallback.onReceiveValue(null);
                }
                mFilePathCallback = filePathCallback;

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);

                try {
                    fileChooserLauncher.launch(Intent.createChooser(intent, "Select Image"));
                } catch (Exception e) {
                    mFilePathCallback = null;
                    return false;
                }
                return true;
            }
        });

        webView.loadUrl("file:///android_asset/index.html");

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (webView != null && webView.canGoBack()) {
                    webView.goBack();
                } else {
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                }
            }
        });
    }

    /**
     * Requests camera and media permissions appropriate for the current Android API level.
     *
     * <ul>
     *   <li>API 34+ (UPSIDE_DOWN_CAKE): requests granular media permissions including
     *       {@code READ_MEDIA_VISUAL_USER_SELECTED}</li>
     *   <li>API 33 (TIRAMISU): requests {@code READ_MEDIA_IMAGES} and {@code READ_MEDIA_VIDEO}</li>
     *   <li>Below API 33: requests legacy {@code READ_EXTERNAL_STORAGE}</li>
     * </ul>
     */
    private void requestNecessaryPermissions() {
        String[] permissions;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            permissions = new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VIDEO,
                Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
            };
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions = new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VIDEO
            };
        } else {
            permissions = new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
            };
        }

        boolean needsPermission = false;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                needsPermission = true;
                break;
            }
        }

        if (needsPermission) {
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /** Pauses the WebView when the activity goes to background. */
    @Override
    protected void onPause() {
        super.onPause();
        if (webView != null) webView.onPause();
    }

    /** Resumes the WebView when the activity returns to foreground. */
    @Override
    protected void onResume() {
        super.onResume();
        if (webView != null) webView.onResume();
    }

    /** Destroys the WebView and releases resources when the activity is destroyed. */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.destroy();
            webView = null;
        }
    }
}
