package il.co.isrcorp.dualscreen;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Display[] displays = ((DisplayManager) getSystemService(Context.DISPLAY_SERVICE)).getDisplays();

        if(displays.length < 2){
            finish();
        }



        Intent externalScreenIntent = new Intent(this, ExternalScreenActivity.class);
        externalScreenIntent.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);


        displayActivity(displays[0],new Intent(this, MainScreenActivity.class));
        displayActivity(displays[1],externalScreenIntent);
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

}