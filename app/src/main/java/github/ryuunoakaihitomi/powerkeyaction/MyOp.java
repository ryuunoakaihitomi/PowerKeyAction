package github.ryuunoakaihitomi.powerkeyaction;

import android.app.UiAutomation;
import android.app.UiAutomationConnection;
import android.app.UiAutomationHidden;
import android.os.Looper;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;

import dev.rikka.tools.refine.Refine;

public class MyOp extends IMyOp.Stub {

    private static final String TAG = "MyOp";

    @Override
    public void press() throws RemoteException {
        Log.d(TAG, "press() called");
        var uah = new UiAutomationHidden(Looper.getMainLooper(), new UiAutomationConnection());
        uah.connect(UiAutomation.FLAG_DONT_SUPPRESS_ACCESSIBILITY_SERVICES);
        try {
            runUiAutomation(Refine.unsafeCast(uah));
        } catch (Throwable e) {
            Log.e(TAG, "press: [runUiAutomation]", e);
        }
        uah.disconnect();
    }

    @SuppressWarnings("RedundantThrows")
    private void runUiAutomation(UiAutomation ua) throws Throwable {
        final long now = SystemClock.uptimeMillis();
        // From InputShellCommand
        var event = new KeyEvent(now, now, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_POWER, 0, 0, KeyCharacterMap.VIRTUAL_KEYBOARD, 0, 0, InputDevice.SOURCE_UNKNOWN);
        ua.injectInputEvent(event, true);
        SystemClock.sleep(100);
        ua.injectInputEvent(KeyEvent.changeAction(event, KeyEvent.ACTION_UP), true);
    }
}
