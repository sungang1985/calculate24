package com.nari.sungang.calculate24;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.myscript.atk.math.widget.MathWidgetApi;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements
        MathWidgetApi.OnConfigureListener,
        MathWidgetApi.OnRecognitionListener {
    private static final String TAG = "calculate24";
    private static Random random = new Random();
    private static int[] pokers = {
            R.drawable.clbace, R.drawable.clb02, R.drawable.clb03, R.drawable.clb04, R.drawable.clb05,
            R.drawable.clb06, R.drawable.clb07, R.drawable.clb08, R.drawable.clb09,
            R.drawable.clb10, R.drawable.clbjack, R.drawable.clbqueen, R.drawable.clbking,
            R.drawable.hrtace, R.drawable.hrt02, R.drawable.hrt03, R.drawable.hrt04, R.drawable.hrt05,
            R.drawable.hrt06, R.drawable.hrt07, R.drawable.hrt08, R.drawable.hrt09,
            R.drawable.hrt10, R.drawable.hrtjack, R.drawable.hrtqueen, R.drawable.hrtking,
            R.drawable.spdace, R.drawable.spd02, R.drawable.spd03, R.drawable.spd04, R.drawable.spd05,
            R.drawable.spd06, R.drawable.spd07, R.drawable.spd08, R.drawable.spd09,
            R.drawable.spd10, R.drawable.spdjack, R.drawable.spdqueen, R.drawable.spdking,
            R.drawable.dndace, R.drawable.dnd02, R.drawable.dnd03, R.drawable.dnd04, R.drawable.dnd05,
            R.drawable.dnd06, R.drawable.dnd07, R.drawable.dnd08, R.drawable.dnd09,
            R.drawable.dnd10, R.drawable.dndjack, R.drawable.dndqueen, R.drawable.dndking};

    private String resultAsText = "";
    private MathWidgetApi widget;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.action_submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        initRecyclerView();
        widget = (MathWidgetApi) findViewById(R.id.math_widget);
        if (!widget.registerCertificate(MyCertificate.getBytes())) {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage("Please use a valid certificate.");
            dlgAlert.setTitle("Invalid certificate");
            dlgAlert.setCancelable(false);
            dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //dismiss the dialog
                }
            });
            dlgAlert.create().show();
            return;
        }

        // Listen to widget events (see onConfigureEnd and onRecognitionEnd APIs)
        widget.setOnConfigureListener(this);
        widget.setOnRecognitionListener(this);

        // references assets directly from the APK to avoid extraction in application
        // file system
        widget.addSearchDir("zip://" + getPackageCodePath() + "!/assets/conf");

        // The configuration is an asynchronous operation. Callbacks are provided to
        // monitor the beginning and end of the configuration process and update the UI
        // of the input method accordingly.
        //
        // "math" references the conf/math/math.conf file in your assets.
        // "standard" references the configuration name in math.conf
        widget.configure("math", "standard");
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        adapter = new RecyclerViewAdapter(new int[]{pokers[random.nextInt(52)], pokers[random.nextInt(52)], pokers[random.nextInt(52)], pokers[random.nextInt(52)]},
                MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    protected void onDestroy() {
        widget.setOnRecognitionListener(null);
        widget.setOnConfigureListener(null);

        // release widget's resources
        widget.release();
        super.onDestroy();
    }

    @Override
    public void onConfigureBegin(MathWidgetApi widget) {
    }

    @Override
    public void onConfigureEnd(MathWidgetApi widget, boolean success) {
        if (!success) {
            Toast.makeText(getApplicationContext(), widget.getErrorString(), Toast.LENGTH_LONG).show();
            Log.e(TAG, "Unable to configure the Math Widget: " + widget.getErrorString());
            return;
        }
        Toast.makeText(getApplicationContext(), "Math Widget Configured", Toast.LENGTH_SHORT).show();
        if (BuildConfig.DEBUG)
            Log.d(TAG, "Math Widget configured!");
    }

    @Override
    public void onRecognitionBegin(MathWidgetApi widget) {
    }

    @Override
    public void onRecognitionEnd(MathWidgetApi widget) {
        //Toast.makeText(getApplicationContext(), "Recognition update", Toast.LENGTH_SHORT).show();
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "Math Widget recognition: " + widget.getResultAsText());
        }

        resultAsText = widget.getResultAsText();
        if (widget.getResultAsText().equals(resultAsText) && resultAsText.contains("=24")) {
            adapter.resetPokers(new int[]{pokers[random.nextInt(52)], pokers[random.nextInt(52)], pokers[random.nextInt(52)], pokers[random.nextInt(52)]});
            return;
        }
    }
}
