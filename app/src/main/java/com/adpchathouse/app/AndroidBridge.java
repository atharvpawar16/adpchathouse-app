package com.adpchathouse.app;

import android.content.Context;
import android.webkit.JavascriptInterface;

/**
 * JavaScript interface injected into the WebView as "AndroidBridge".
 * Provides hooks called by JS on room join/leave.
 */
public class AndroidBridge {

    private final Context context;

    public AndroidBridge(Context context) {
        this.context = context;
    }

    /** Called by JS after the user joins a room */
    @JavascriptInterface
    public void onRoomJoined(String room, String username) {
        // no-op — notifications disabled
    }

    /** Called by JS when the user leaves the room */
    @JavascriptInterface
    public void onRoomLeft() {
        // no-op — notifications disabled
    }
}
