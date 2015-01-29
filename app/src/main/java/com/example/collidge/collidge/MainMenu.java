package com.example.collidge.collidge;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.EditText;

public class MainMenu extends Activity
{

    private int contentView;
    private MediaPlayer easteregg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (easteregg != null)
            easteregg.start();
        easteregg = MediaPlayer.create(this, R.drawable.soundtrack);
        setContentView(R.layout.activity_main_menu);
        contentView = 0;
    }
    protected void onDestroy()
    {
        super.onDestroy();
        if (easteregg != null) {
            easteregg.pause();
            easteregg.release();
            easteregg = null;
        }
    }
    protected void onPause()
    {
        super.onPause();
        if (easteregg != null)
        {
            easteregg.pause();
        }
    }

/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            System.out.println("Settings Button");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    public boolean onTouchEvent(MotionEvent event){
        DisplayMetrics screen;
        screen=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(screen);
        int action = event.getAction();
        System.out.println();
        float x=(event.getX()/(screen.widthPixels/2))-1;
        float y=(event.getY()/(screen.heightPixels/2))-1;
        System.out.println(x+","+y);

        switch(action) {
            case (MotionEvent.ACTION_DOWN) :

                if (x<0.118 && x>-0.127 && y<0.833 && y>0.625 && contentView == 0)    //presses the New Game Button
                {
                    //alters the content view to the new game layout
                    setContentView(R.layout.new_game_xml);
                    contentView = 1;
                }

                if (x<-0.538 && x>-0.782 && y<0.833 && y>0.625 && contentView == 0)  //presses the Load Game Button
                {
                    //alters the content view to the load game layout
                    // setContentView(R.layout.activity_main_menu);
                    // contentView = 0;
                    easteregg.start();
                    easteregg.setLooping(true);
                }

                if (x>0.500 && x<0.720 && y<0.833 && y>0.625 && contentView == 0)   //Presses the Credits Button
                {
                    System.out.println("Credits");
                }

                if (x<0.745 && x>0.535 && y<0.887 && y>0.707 && contentView == 1)  //presses the Continue Button on Character Generation
                {
                    EditText buffer = (EditText)findViewById(R.id.editText);       //Character Name
                    String name = buffer.getText().toString();
                    if (name == null)
                        name = "John Doe";
                    System.out.println(name);
                }
                return true;
            case (MotionEvent.ACTION_MOVE) :
                //movement, but finger on screen before
                //no use for this action here, might be worthwhile deleting it
                return true;
            case (MotionEvent.ACTION_UP) :
                //finger removed from screen
                //no use for this action here, might be worthwhile deleting it
                return true;
            default :
                return super.onTouchEvent(event);
        }
    }
}
