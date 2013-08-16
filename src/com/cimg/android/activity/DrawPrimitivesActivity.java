package com.cimg.android.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.cimg.android.R;
import com.cimg.android.utils.CacheUtils;
import com.cimg.android.utils.NativeUtils;

/**
 * Created by seven on 8/17/13.
 */
public class DrawPrimitivesActivity extends Activity {

    private final static String PRIMITIVES_IMAGE = "primitives.jpg";
    private String imagePath;
    private ProgressBar progressBar;
    private ImageView imageView;

    private final AsyncTask<Void,Void,Void> drawPrimitivesTask = new AsyncTask<Void, Void, Void>() {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.INVISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.i("XXX","image:" + imagePath);
            NativeUtils.generatePrimitives(imagePath);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageURI(Uri.parse(imagePath));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draw_primitives_layout);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        imageView = (ImageView) findViewById(R.id.image);
        imagePath = CacheUtils.getCachePath(this) + PRIMITIVES_IMAGE;

        drawPrimitivesTask.execute();
    }
}
