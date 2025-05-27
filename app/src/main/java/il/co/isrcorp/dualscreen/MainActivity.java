package il.co.isrcorp.dualscreen;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Display[] displays = ((DisplayManager) getSystemService(Context.DISPLAY_SERVICE)).getDisplays();

        if(displays.length < 2){
            finish();
        }

        setExternalScreenAsPrimary();


        Intent externalScreenIntent = new Intent(this, ExternalScreenActivity.class);
        externalScreenIntent.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);


        displayActivity(displays[0],new Intent(this, MainScreenActivity.class));
        displayActivity(displays[1],externalScreenIntent);
    }

    private void setExternalScreenAsPrimary(){
        /*
         persist.external.screen.as=primary
                  ==>
                  primary screen: landscape
                  secondary:portrait

        */


        String prop = getSystemProp("persist.external.screen.as", "secondary");

        if(!prop.equals("primary")){
            setSystemProp("persist.external.screen.as","primary");
            Intent intent = new Intent("android.ext.screen.action.CHANGED");
            getApplicationContext().sendBroadcast(intent);
        }
    }

    private void displayActivity(Display display, Intent intent){

        if(display == null)
            return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startActivity(intent, ActivityOptions
                    .makeBasic()
                    .setLaunchDisplayId(display.getDisplayId())
                    .toBundle());
        }
    }

    private String getSystemProp(String key, String prop) {

        BufferedReader reader;
        String propertyValue = "";
        try {
            String cmd = "getprop " + key;
            Process process = Runtime.getRuntime().exec(cmd);
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            propertyValue = reader.readLine().trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return propertyValue;
    }

    private void setSystemProp(String key, String prop) {
        try {
            String cmd = "setprop " + key + " " + prop;
            Process process = Runtime.getRuntime().exec(cmd);
            process.wait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}