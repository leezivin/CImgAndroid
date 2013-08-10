package com.example.cimg_android;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.net.URI;

public class HomeActivity extends Activity {

    private static final int RESULT_LOAD_IMAGE = 1;

    private ImageView image;
    private Button chooseImageButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        initImage();
        initChooseImageButton();
    }

    private void initChooseImageButton() {
        chooseImageButton = (Button) findViewById(R.id.choose_image_button);
        chooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlerChooseImageButton();
            }
        });
    }

    private void handlerChooseImageButton() {
        Log.i("XXX","StartIntent");
        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    private void initImage() {
        image = (ImageView) findViewById(R.id.image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("XXX","onActivityResult");
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(
                    selectedImage,
                    filePathColumn,
                    null,
                    null,
                    null
            );

            String imagePath = null;
            try{
                if (cursor.moveToNext()){
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imagePath = cursor.getString(columnIndex);
                }
            } finally {
                cursor.close();
            }

            Uri uriImage = Uri.fromFile(new File(imagePath));
            image.setImageURI(uriImage);
        }
    }
}
