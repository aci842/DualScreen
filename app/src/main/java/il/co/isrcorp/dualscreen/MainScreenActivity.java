package il.co.isrcorp.dualscreen;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        Button btn = findViewById(R.id.btn);
        TextView textView = findViewById(R.id.console);
        btn.setOnClickListener(v -> {
            textView.setText("Button clicked");
            new Handler().postDelayed(() -> textView.setText(""), 2000);
        });
    }
}