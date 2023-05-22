package github.ryuunoakaihitomi.powerkeyaction;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;

public class InfoActivity extends Activity {

    private static final String URL = "https://github.com/ryuunoakaihitomi/PowerKeyAction";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(URL)));
        } catch (Throwable ignored) {
        }
        finish();
    }
}
