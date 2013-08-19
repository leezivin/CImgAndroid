package com.cimg.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cimg.android.R;
import com.cimg.android.utils.CacheUtils;
import com.cimg.android.utils.NativeUtils;

/**
 * Created by nbukhal on 19.08.13.
 */
public class BlockFilterActivity extends Activity {
    private static final String SOURCE_BUNDLE = "SOURCE_BUNDLE";
    private static final int RESULT_LOAD_IMAGE = 1;
    private final static String RESULT_IMAGE = "block_filter.jpg";

    private String imageSourcePath;
    private String imageResultPath;

    private TextView imagePathTextView;
    private ImageView image;
    private Button chooseImageButton;
    private Button blockFilterButton;

    private static boolean block_start_asyntask = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.block_filter_layout);

        imageResultPath = CacheUtils.getCachePath(this) + RESULT_IMAGE;

        imagePathTextView = (TextView) findViewById(R.id.image_path);
        image = (ImageView) findViewById(R.id.image);

        chooseImageButton = (Button) findViewById(R.id.choose_image_button);
        chooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlerChooseImageButton();
            }
        });

        blockFilterButton = (Button) findViewById(R.id.block_filter_button);
        blockFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlerBlockFilter();
            }
        });

        if (savedInstanceState != null && savedInstanceState.containsKey(SOURCE_BUNDLE)){
            blockFilterButton.setClickable(true);
            imageSourcePath = savedInstanceState.getString(SOURCE_BUNDLE);
        } else {
            blockFilterButton.setClickable(false);
        }

    }

    private void handlerBlockFilter() {
        if (block_start_asyntask){
            Toast.makeText(this, "Wait...", Toast.LENGTH_SHORT).show();
            return;
        }

        AsyncTask<Void,Void,Void> blockFilterTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                block_start_asyntask = true;
            }

            @Override
            protected Void doInBackground(Void... args) {
                NativeUtils.processingBlocks(
                        imageSourcePath,
                        imageResultPath
                );
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                image.setVisibility(View.VISIBLE);
                image.setImageBitmap(BitmapFactory.decodeFile(imageResultPath));
                System.gc();
                Log.i("XXX", "Render OK:" + imageResultPath);
                Toast.makeText(BlockFilterActivity.this,"Render OK",Toast.LENGTH_SHORT).show();
                SystemClock.sleep(150); // for write on sdcard
                block_start_asyntask = false;
            }
        };

        blockFilterTask.execute();

    }

    private void handlerChooseImageButton() {
        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
                    image.setVisibility(View.VISIBLE);
                    image.setImageURI(Uri.parse(imageSourcePath));
                    blockFilterButton.setClickable(true);
                }
            } finally {
                cursor.close();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SOURCE_BUNDLE, imageSourcePath);
    }

}
