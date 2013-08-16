package com.cimg.android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.Toast;
import com.cimg.android.R;

/**
 * Created by seven on 8/17/13.
 */
public class ChangeColorBalanceActivity extends Activity {

    private SeekBar redSeekBar;
    private SeekBar greenSeekBar;
    private SeekBar blueSeekBar;

    private float r;
    private float g;
    private float b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_color_balance_layout);

        redSeekBar = (SeekBar) findViewById(R.id.red_seek_bar);
        greenSeekBar = (SeekBar) findViewById(R.id.green_seek_bar);
        blueSeekBar = (SeekBar) findViewById(R.id.blue_seek_bar);

        setHandlersSeekBars();
    }

    private void setHandlersSeekBars() {
        redSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
        });
        greenSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
        });
        blueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
        });
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
}
