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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final int FILE_CHOOSER_REQUEST_CODE = 200;
    private ValueCallback<Uri[]> mFilePathCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Request permissions
        requestNecessaryPermissions();

        // Start foreground service to keep app alive
        Intent serviceIntent = new Intent(this, KeepAliveService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent);
        } else {
            startService(serviceIntent);
        }

        webView = findViewById(R.id.webview);
        
        // WebView settings
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        
        // Enable zoom controls
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDisplayZoomControls(false);
        
        // Set WebView client
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                // Auto-grant WebView permissions for camera, audio, etc.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    request.grant(request.getResources());
                }
            }
            
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
                                            FileChooserParams fileChooserParams) {
                // Handle file selection for image upload
                if (mFilePathCallback != null) {
                    mFilePathCallback.onReceiveValue(null);
                }
                mFilePathCallback = filePathCallback;

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                
                // Allow multiple selection
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                
                try {
                    startActivityForResult(
                        Intent.createChooser(intent, "Select Image"),
                        FILE_CHOOSER_REQUEST_CODE
                    );
                } catch (Exception e) {
                    mFilePathCallback = null;
                    return false;
                }
                return true;
            }
        });
        
        // Load HTML from assets
        webView.loadUrl("file:///android_asset/index.html");
    }

    private void requestNecessaryPermissions() {
        String[] permissions;
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) { // Android 14+
            permissions = new String[] {
                Manifest.permission.CAMERA,
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VIDEO,
                Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED,
                Manifest.permission.POST_NOTIFICATIONS
            };
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13
            permissions = new String[] {
                Manifest.permission.CAMERA,
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VIDEO,
                Manifest.permission.POST_NOTIFICATIONS
            };
        } else {
            permissions = new String[] {
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
            };
        }

        boolean needsPermission = false;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) 
                    != PackageManager.PERMISSION_GRANTED) {
                needsPermission = true;
                break;
            }
        }

        if (needsPermission) {
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, 
                                          int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            // Permissions granted or denied - app will continue either way
            // You can add logic here to handle denied permissions if needed
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == FILE_CHOOSER_REQUEST_CODE) {
            if (mFilePathCallback == null) return;
            
            Uri[] results = null;
            
            // Check if response is positive
            if (resultCode == RESULT_OK && data != null) {
                String dataString = data.getDataString();
                if (dataString != null) {
                    results = new Uri[]{Uri.parse(dataString)};
                }
            }
            
            mFilePathCallback.onReceiveValue(results);
            mFilePathCallback = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Keep WebView alive when app goes to background
        if (webView != null) {
            webView.onPause();
            // Don't pause timers - keeps Firebase connection alive
            // webView.pauseTimers();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Resume WebView when app comes to foreground
        if (webView != null) {
            webView.onResume();
            // webView.resumeTimers();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.destroy();
            webView = null;
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
