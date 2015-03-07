package bitsykolayers.layersthemewrapper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;

import com.stericson.RootTools.RootTools;

/**
 * Created by lovejoy777 & bgill55 on 3/6/15.
 */
public class Settings extends Activity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    private static boolean mShowHiddenFiles;
    private static boolean mRootAccess;
    public static String defaultdir;

    public static void updatePreferences(Context context) {
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(context);

        mShowHiddenFiles = p.getBoolean("displayhiddenfiles", true);
        mRootAccess = p.getBoolean("enablerootaccess", true);
        defaultdir = p.getString("defaultdir", Environment
                .getExternalStorageDirectory().getPath());

        rootAccess();
    }

    public static boolean showHiddenFiles() {
        return mShowHiddenFiles;
    }

    public static boolean rootAccess() {
        return mRootAccess && RootTools.isAccessGiven();
    }
}
