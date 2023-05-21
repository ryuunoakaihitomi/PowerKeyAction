package github.ryuunoakaihitomi.powerkeyaction;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import rikka.shizuku.Shizuku;

public class MainActivity extends Activity implements Shizuku.OnRequestPermissionResultListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Shizuku.isPreV11()) {
            showDeniedInfo();
            finish();
            return;
        }
        Shizuku.addRequestPermissionResultListener(this);
        Shizuku.requestPermission(1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Shizuku.removeRequestPermissionResultListener(this);
    }

    @Override
    public void onRequestPermissionResult(int requestCode, int grantResult) {
        if (grantResult != PackageManager.PERMISSION_GRANTED) {
            showDeniedInfo();
            finish();
            return;
        }
        doWork();
    }

    private void showDeniedInfo() {
        Toast.makeText(this, android.R.string.cancel, Toast.LENGTH_LONG).show();
    }

    private void doWork() {
        var connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                try {
                    IMyOp.Stub.asInterface(service).press();
                } catch (RemoteException e) {
                    Log.e(TAG, "onServiceConnected: ", e);
                    showDeniedInfo();
                }
                finish();
            }


            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        };
        var opName = MyOp.class.getName();
        var serviceArgs = new Shizuku.UserServiceArgs(new ComponentName(this, opName))
                .processNameSuffix(opName)
                .daemon(false);
        Shizuku.bindUserService(serviceArgs, connection);
    }
}
