package com.example.collidge.collidge;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.MotionEventCompat;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.io.File;


public class MainActivity extends Activity
{
    /**
     * Deidre -- start
     */
    //Button createAlert;
    /**
     * Deirdre -- end
     */

    /**
     * Dan -- start
     */
    Player player;
    Fight battle;
    DisplayMetrics screen;

    FightMenu menu;

    /**
     * Dan -- end
     */


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        /**
         * Deidre -- start
         */
//        setContentView(R.layout.activity_main);
//
//        //this creates a button that when pressed, will display a dialog
//        createAlert = (Button)findViewById(R.id.dialogAlert);
//        //show();
//        createAlert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                show();
//            }
//
//        });
        /**
         * Deirdre -- end
         */

        /**
         * Dan -- start
         */
        player = new Player();
        menu=new FightMenu(player);
        battle=new Fight();
        screen=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(screen);
        /**
         * Dan -- end
         */
    }

    /**
     * Dan -- start
     */
    @Override
    protected void onStart()
    {
        super.onStart();
        battle.start(player,player.getItemList());
    }


    public boolean onTouchEvent(MotionEvent event)
    {
        int action = event.getAction();
        float x=(event.getX()/(screen.widthPixels/2))-1;
        float y=(event.getY()/(screen.heightPixels/2))-1;
        //System.out.println(x+","+y);

        switch(action) {
            case (MotionEvent.ACTION_DOWN) :
                //finger down on screen
                battle.touchEvent(x,y,event);
                //menu.touchEvent(x,y,event);
                return true;
            case (MotionEvent.ACTION_MOVE) :
                //movement, but finger on screen before
                return true;
            case (MotionEvent.ACTION_UP) :
                //finger removed from screen
                return true;
            case (MotionEvent.ACTION_CANCEL) :
                System.out.println("Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE) :
                System.out.println( "Movement occurred outside bounds " +
                        "of current screen element");
                return true;
            default :
                return super.onTouchEvent(event);
        }
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
    /**
     * Dan -- end
     */

    /**
     * Deirdre -- start
     */

//    void show() {
//        // DialogFragment.show() will take care of adding the fragment
//        // in a transaction.  We also want to remove any currently showing
//        // dialog, so make our own transaction and take care of that here.
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
//        if (prev != null) {
//            ft.remove(prev);
//        }
//
//        // Create and show the dialog.
//        android.app.DialogFragment newFragment =  new BasicDialogFragment();
//        ((BasicDialogFragment)newFragment).setContext(this);
//        newFragment.show(ft, "dialog");
//    }
//
//    public Intent getIntent(){
//        return new Intent(this, Yes.class);
//    }
//
//
//    public void open_library(View view) {
//
//        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//        builder.setTitle("Add Photo!");
//        builder.setItems(options, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//                if (options[item].equals("Capture Image")) {
//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
//                    startActivityForResult(intent, 1);
//                } else if (options[item].equals("Choose from Gallery")) {
//                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(intent, 2);
//                } else if (options[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
//    }

    /**
     * Deirdre -- End
     */
}