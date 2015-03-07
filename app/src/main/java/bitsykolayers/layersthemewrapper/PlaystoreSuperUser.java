package bitsykolayers.layersthemewrapper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

/**
 * Created by lovejoy777 & bgill55 on 3/6/15.
 */

public class PlaystoreSuperUser extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        final String appPackageName = "com.noshufou.android.su"; // getPackageName() from Context or Activity object
        try {

            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        }

        catch (android.content.ActivityNotFoundException anfe) {

            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
}

