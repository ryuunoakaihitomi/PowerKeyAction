package github.ryuunoakaihitomi.powerkeyaction;

import android.service.quicksettings.TileService;

public class MyQsTileService extends TileService {

    @Override
    public void onClick() {
        startActivityAndCollapse(getPackageManager().getLaunchIntentForPackage(getPackageName()));
    }
}
