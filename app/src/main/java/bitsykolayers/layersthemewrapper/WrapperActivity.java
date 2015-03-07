package bitsykolayers.layersthemewrapper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.exceptions.RootDeniedException;
import com.stericson.RootTools.execution.CommandCapture;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeoutException;

import bitsykolayers.layersthemewrapper.commands.RootCommands;

/**
 * Created by lovejoy777 & bgill55 on 3/6/15.
 */

public class WrapperActivity extends Activity {
    static final String TAG = "copyingFile";

    Button button;

    public void onCreate(Bundle savedInstanceState) {
        if (RootTools.isAccessGiven()) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_wrapper);

            button = (Button) findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    runtask1();
                }
            });
        } else {
            Toast.makeText(WrapperActivity.this, "Your device doesn't seem to be rooted", Toast.LENGTH_LONG).show();
            Intent intent0 = new Intent();
            intent0.setClass(this, bitsykolayers.layersthemewrapper.PlaystoreSuperUser.class);
            startActivity(intent0);
            finish();
        }
    }



    public void runtask1() {


        // setContentView(R.layout.activity_main);


        // THEME NAME CHANGE THIS TO YOUR THEME NAME
        String themename = getString(R.string.themename);


        // RENAME END STRING TO YOUR LAYERS FOLDER NAME
        String wrapperTemplatePath = getApplicationInfo().dataDir + "/" + themename;

        // DIR COPY NAME
        String copywrapperTemplatePath = getApplicationInfo().dataDir + "/" + themename + "/" + themename;

        // FINAL NAME
        String finalname = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + themename + ".zip";

        // DIR COPY NAME
        String newname = "/" + themename;

        // CREATES SDCARD DIR FOR LAYERS THEME
        SimpleUtils.createDir(getApplicationInfo().dataDir, "" + themename);

        try {
            // CHANGE PERMISSIONS OF SDCARD FOLDER
            CommandCapture command = new CommandCapture(0, "chmod 777 " +  getApplicationInfo().dataDir + "/" + themename);
            RootTools.getShell(true).add(command);
            while (!command.isFinished()) {
                Thread.sleep(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (RootDeniedException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int id = this.getResources().getIdentifier(themename, "raw", this.getPackageName());

        // COPIES THEME FILE TO SDCARD FOLDER
        InputStream in = getResources().openRawResource(id);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(copywrapperTemplatePath);
        } catch (FileNotFoundException e) {
            Log.e(TAG, "fileoutputstream is null", e);
        }
        byte[] buff = new byte[1024];
        int read = 0;

        try {
            try {
                while ((read = in.read(buff)) > 0) {
                    if (out != null) {
                        out.write(buff, 0, read);
                    }
                }
            } catch (IOException e) {
                Log.e(TAG, "out write is null", e);
            }
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                Log.e(TAG, "in is null", e);
            }

            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "out is null", e);
            }
        }

        // CHANGES PERMS ON TEMP FILE
        try {
            // CHANGE PERMISSIONS OF SDCARD FOLDER
            CommandCapture command = new CommandCapture(0, "chmod -R 777 " +  getApplicationInfo().dataDir + "/" + themename);
            RootTools.getShell(true).add(command);
            while (!command.isFinished()) {
                Thread.sleep(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (RootDeniedException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // RENAMES THEME FILE TO END WITH .ZIP
        SimpleUtils.renameTarget(copywrapperTemplatePath, themename + ".zip");

        // COPIES FILE FROM /DATA/DATA/ TO SDCARD
        RootTools.copyFile(copywrapperTemplatePath + ".zip", finalname, true, true);

        RootCommands.DeleteFileRoot(wrapperTemplatePath);

        finish();

        Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.lovejoy777.rroandlayersmanager");
        startActivity(LaunchIntent);

    }
}