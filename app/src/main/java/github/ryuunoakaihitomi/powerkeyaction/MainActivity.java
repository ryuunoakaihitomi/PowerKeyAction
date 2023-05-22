package github.ryuunoakaihitomi.powerkeyaction;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.topjohnwu.superuser.Shell;

import rikka.shizuku.Shizuku;

public class MainActivity extends Activity implements Shizuku.OnRequestPermissionResultListener, Shell.ResultCallback {

    private static final String TAG = "MainActivity";

    @SuppressWarnings("SpellCheckingInspection")
    private static final String CMD = "input keyevent KEYCODE_POWER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Shizuku.pingBinder() || Shizuku.isPreV11()) {
            if (Boolean.TRUE.equals(Shell.isAppGrantedRoot()) || Shell.getShell().isRoot()) {
                Shell.cmd(CMD).submit(this);
                return;
            }
            showDeniedInfo();
            finish();
            return;
        }
        if (Shizuku.checkSelfPermission() != PackageManager.PERMISSION_GRANTED) {
            Shizuku.addRequestPermissionResultListener(this);
            Shizuku.requestPermission(1);
        } else doSzkWork();
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
        doSzkWork();
    }

    @Override
    public void onResult(@NonNull Shell.Result out) {
        if (out.getCode() != 0) showDeniedInfo();
        finish();
    }

    private void showDeniedInfo() {
        Toast.makeText(this, android.R.string.cancel, Toast.LENGTH_LONG).show();
    }

    private void doSzkWork() {
        Log.i(TAG, "doSzkWork");
        var serviceArgs = new Shizuku.UserServiceArgs(new ComponentName(this, MyOp.class))
                .processNameSuffix("operation")
                .daemon(false);
        var connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                try {
                    IMyOp.Stub.asInterface(service).press();
                } catch (RemoteException e) {
                    Log.e(TAG, "onServiceConnected: ", e);
                    if (!(e instanceof DeadObjectException)) showDeniedInfo();
                }
                Shizuku.unbindUserService(serviceArgs, this, true);
                finish();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        };
        Shizuku.bindUserService(serviceArgs, connection);
    }
}
