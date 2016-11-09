package com.somnus.androidscrollerdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SlideView.OnSlideListener, View.OnClickListener {

    private LinearLayout slide_delete;
    private SlideView slideView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        slideView = (SlideView)findViewById(R.id.sv_view);
        View slideContentView = View.inflate(MainActivity.this, R.layout.slide_list_item, null);
        slideView.setContentView(slideContentView);
        slideView.setButtonText("删除");
        slide_delete = (LinearLayout)findViewById(R.id.holder);
        slideView.setOnSlideListener(this);
        slide_delete.setOnClickListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
// 将事件交由slideView自身处理
        slideView.onRequireTouchEvent(event);
        return super.onTouchEvent(event);
    }
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.holder){
            Toast.makeText(MainActivity.this, "你点击了删除按钮", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSlide(View view, int status) {

    }
}
