package com.cimg.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.cimg.android.R;
import com.cimg.android.utils.CacheUtils;
import com.cimg.android.utils.NativeUtils;

import java.io.File;

/**
 * Created by seven on 8/17/13.
 */
public class ChangeColorBalanceActivity extends Activity {
    private static final String SOURCE_BUNDLE = "SOURCE_BUNDLE";
    private static final int RESULT_LOAD_IMAGE = 1;
    private final static String RESULT_IMAGE = "change_color_balance.jpg";
    private String imageSourcePath;
    private String imageResultPath;

    private TextView imagePathTextView;
    private ImageView image;
    private Button chooseImageButton;
    private Button changeColorBalanceButton;

    private SeekBar redSeekBar;
    private SeekBar greenSeekBar;
    private SeekBar blueSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_color_balance_layout);

        imageResultPath = CacheUtils.getCachePath(this) + RESULT_IMAGE;

        redSeekBar = (SeekBar) findViewById(R.id.red_seek_bar);
        greenSeekBar = (SeekBar) findViewById(R.id.green_seek_bar);
        blueSeekBar = (SeekBar) findViewById(R.id.blue_seek_bar);
        setHandlersSeekBars();

        imagePathTextView = (TextView) findViewById(R.id.image_path);
        image = (ImageView) findViewById(R.id.image);

        chooseImageButton = (Button) findViewById(R.id.choose_image_button);
        chooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlerChooseImageButton();
            }
        });

        changeColorBalanceButton = (Button) findViewById(R.id.change_color_balance_button);
        changeColorBalanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlerChangeBalanceButton();
            }
        });

        if (savedInstanceState != null && savedInstanceState.containsKey(SOURCE_BUNDLE)){
            changeColorBalanceButton.setClickable(true);
            imageSourcePath = savedInstanceState.getString(SOURCE_BUNDLE);
        } else {
            changeColorBalanceButton.setClickable(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("XXX", "onActivityResult");
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(
                    selectedImage,
                    filePathColumn,
                    null,
                    null,
                    null
            );

            try {
                if (cursor.moveToNext()) {
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageSourcePath = cursor.getString(columnIndex);
                    imagePathTextView.setText(imageSourcePath);
                    image.setImageURI(Uri.parse(imageSourcePath));
                    changeColorBalanceButton.setClickable(true);
                }
            } finally {
                cursor.close();
            }

        }
    }

    private void handlerChangeBalanceButton() {
        AsyncTask<Float,Void,Void> changeColorBalanceTask = new AsyncTask<Float, Void, Void>() {
            @Override
            protected Void doInBackground(Float... args) {
                NativeUtils.changeColorBalance(
                        imageSourcePath,
                        imageResultPath,
                        args[0],
                        args[1],
                        args[2]
                );
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                image.setImageBitmap(BitmapFactory.decodeFile(imageResultPath));
                System.gc();
                Log.i("XXX","Render OK:" + imageResultPath);
                Toast.makeText(ChangeColorBalanceActivity.this,"Render OK",Toast.LENGTH_SHORT).show();
            }
        };

        changeColorBalanceTask.execute(
                convertValue(redSeekBar.getProgress()),
                convertValue(greenSeekBar.getProgress()),
                convertValue(blueSeekBar.getProgress())
        );

    }

    private void handlerChooseImageButton() {
        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }

    private void setHandlersSeekBars() {
        SeekBar.OnSeekBarChangeListener listener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                showSeekToast(seekBar);
            }
        };

        redSeekBar.setOnSeekBarChangeListener(listener);
        greenSeekBar.setOnSeekBarChangeListener(listener);
        blueSeekBar.setOnSeekBarChangeListener(listener);
    }

    private void showSeekToast(SeekBar seekBar) {
        Toast.makeText(
                this,
                Float.toString(convertValue(seekBar.getProgress())),
                Toast.LENGTH_SHORT
        ).show();
    }

    private float convertValue(int value){
        return (value - 1f) / 10f;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SOURCE_BUNDLE, imageSourcePath);
    }
}
