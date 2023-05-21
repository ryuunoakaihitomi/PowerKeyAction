package android.app;

import android.os.Looper;

import dev.rikka.tools.refine.RefineAs;

@RefineAs(UiAutomation.class)
public class UiAutomationHidden {

    public UiAutomationHidden(Looper looper, IUiAutomationConnection connection) {
    }

    public void connect(int flag) {
    }

    public void disconnect() {
    }
}