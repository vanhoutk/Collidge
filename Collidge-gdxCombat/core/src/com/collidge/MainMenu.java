package com.collidge;

/*import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.Toast;*/

public class MainMenu //extends Activity
{

   /* private int contentView;
    private MediaPlayer easteregg;
    DisplayMetrics screen;
    private Toast toast;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        screen=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(screen);
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
/*
    public boolean onTouchEvent(MotionEvent event)
    {



        int action = event.getAction();
        System.out.println();
        float x=(event.getX()/(screen.widthPixels/2))-1;
        float y=(event.getY()/(screen.heightPixels/2))-1;
        //System.out.println(x+","+y);

        switch(action) {
            case (MotionEvent.ACTION_DOWN) :


                if (x<0.118 && x>-0.3 && y>.4 && contentView == 0)    //presses the New Game Button
                {
                    //alters the content view to the new game layout
                    setContentView(R.layout.new_game_xml);
                    contentView = 1;
                }

                if (x<-0.538  &&y>.4 && contentView == 0)  //presses the Load Game Button
                {
                    //alters the content view to the load game layout
                    // setContentView(R.layout.activity_main_menu);
                    // contentView = 0;
                    easteregg.start();
                    easteregg.setLooping(true);
                    toast=Toast.makeText(this,"NEVER GONNA GIVE YOU UP! (This will load the game later)",Toast.LENGTH_LONG);
                    toast.show();
                }

                if (x>0.500  && y>.5 && contentView == 0)   //Presses the Credits Button
                {
                    System.out.println("Credits");
                    toast=Toast.makeText(this,"Lets Imagine the button said credits ;)",Toast.LENGTH_LONG);
                    toast.show();
                }

                if (x>0.4 && y>0.5 && contentView == 1)  //presses the Continue Button on Character Generation
                {
                    EditText buffer = (EditText)findViewById(R.id.editText);       //Character Name
                    String name = buffer.getText().toString();
                    if (name == null)
                        name = "John Doe";
                    System.out.println(name);

                    toast=Toast.makeText(this, "Welcome to our Colidge, " + name+".", Toast.LENGTH_LONG);

                    toast.show();

                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("Name", name);
                    contentView = 0;
                    startActivity(intent);

                    setContentView(R.layout.activity_main_menu);
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
    }*/
}
