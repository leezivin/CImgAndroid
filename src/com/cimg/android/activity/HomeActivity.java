package com.cimg.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cimg.android.R;

public class HomeActivity extends Activity {
    private Button drawPrimitivesButton;
    private Button changeColorBalanceButton;
    private Button blockFilterButton;

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

        blockFilterButton = (Button) findViewById(R.id.block_filter_button);
        blockFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlerBlockFilterButton();
            }
        });
    }

    private void handlerBlockFilterButton() {
        Intent intent = new Intent(this, BlockFilterActivity.class);
        startActivity(intent);
    }

    private void handlerChangeColorBalanceButton() {
        Intent intent = new Intent(this, ChangeColorBalanceActivity.class);
        startActivity(intent);
    }

    private void handlerDrawPrimitivesButton() {
        Intent intent = new Intent(this, DrawPrimitivesActivity.class);
        startActivity(intent);

    }

}
