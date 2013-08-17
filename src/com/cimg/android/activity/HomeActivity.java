package com.cimg.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.cimg.android.R;
import com.cimg.android.utils.NativeUtils;

public class HomeActivity extends Activity {
    private Button drawPrimitivesButton;
    private Button changeColorBalanceButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        drawPrimitivesButton = (Button) findViewById(R.id.draw_primitives_button);
        drawPrimitivesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlerDrawPrimitivesButton();
            }
        });

        changeColorBalanceButton = (Button) findViewById(R.id.change_color_balance_button);
        changeColorBalanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlerChangeColorBalanceButton();
            }
        });
    }

    private void handlerChangeColorBalanceButton() {
        Intent intent = new Intent(this, ChangeColorBalanceActivity.class);
        startActivity(intent);
    }

    private void handlerDrawPrimitivesButton() {
/*
        Intent intent = new Intent(this, DrawPrimitivesActivity.class);
        startActivity(intent);
*/
        Toast.makeText(this, NativeUtils.getMessage(),Toast.LENGTH_SHORT);
    }

}
