package android.app;

import android.os.Looper;

import dev.rikka.tools.refine.RefineAs;

@RefineAs(UiAutomation.class)
@SuppressWarnings("unused")
public class UiAutomationHidden {

    public UiAutomationHidden(Looper looper, IUiAutomationConnection connection) {
    }

    // @TargetApi(Build.VERSION_CODES.N)
    public void connect(int flag) {
    }

    public void disconnect() {
    }
}