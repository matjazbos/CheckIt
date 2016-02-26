package com.mbostic;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class GameActivity extends Activity {

    private  int time = 0;
    private  boolean running = false;
    boolean fromWrongB = false;

    boolean cB2 = false;

    public void stopTimer() {
        running = false;
        if (!fromWrongB) {
            try {
                final SQLiteDatabase db = new DatabaseHelper(this).getReadableDatabase();
                Cursor cursor = db.query("ScoreList",
                        new String[]{"TIME"},
                        null, null, null, null, "TIME");


                int position=0;
                boolean notEnoughEntries = false;
                if (cursor.moveToFirst()) {
                    for (int i = 0; i < 9; i++) {
                        if (!cursor.moveToNext()) {notEnoughEntries=true; break;}
                    }
                    if (!notEnoughEntries) {
                        String record10 = cursor.getString(0);
                        cursor.close();
                        int s = time / 10;
                        int d = time % 10;
                        String timeStr1 = String.format("%d:%d", s, d);
                        position = timeStr1.compareTo(record10);
                    }
                } else notEnoughEntries = true;


                    if (notEnoughEntries || (position < 0)) {            //if cursor reached 10th record and record is not higher than 10th record, skip name entry

                        final RelativeLayout r = (RelativeLayout) findViewById(R.id.layout);
                        EditText t = new EditText(this);
                        t.setId(R.id.setText);
                        t.setHint(R.string.yourName);
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(300, 90);
                        params.topMargin = 500;
                        params.leftMargin = 200;
                        RelativeLayout.LayoutParams paramsB = new RelativeLayout.LayoutParams(150, 90);
                        paramsB.topMargin = 600;
                        paramsB.leftMargin = 300;
                        r.addView(t, params);
                        Button b = new Button(this);
                        b.setText("OK");
                        b.setOnClickListener(new OnClickListener() {
                            public void onClick(View v) {
                                try {

                                    int s = time / 10;
                                    int d = time % 10;
                                    String timeStr = String.format("%d:%d", s, d);
                                    EditText te = (EditText) findViewById(R.id.setText);
                                    String name = te.getText().toString();
                                    DatabaseHelper.newScore(db, name, timeStr);

                                    db.close();
                                } catch (SQLiteException e) {
                                    errorToast();
                                    e.printStackTrace();
                                }
                                EditText te = (EditText) findViewById(R.id.setText);
                                r.removeView(te);
                                r.removeView(v);
                            }

                        });
                        r.addView(b, paramsB);
                    }
                    cbPos = position();
                    rbPos = position();
                    wrongBtPos = position();

            } catch (SQLiteException e) {
                errorToast();
                e.printStackTrace();
            }
        }
    }
public void errorToast(){
    Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
    toast.show();
}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
/*
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        System.out.println(Integer.toString(width));
        System.out.println(Integer.toString(height));
        System.out.println("test");
*/

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                runTimer();
                running = true;
                button();
            }
        }, 1000);

    }

    public int position() {
        return (int) Math.floor(Math.random() * 19);}

    public static int randx(){
        return (int) Math.floor(Math.random() * 600);
    }
    public static int randy(){
        return (int) Math.floor(Math.random() * 1100 + 90);
    }

    private void runTimer() {
        final TextView timeView = (TextView) findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int s = time / 10;
                int d = time % 10;
                String timeStr = String.format("%d:%d", s, d);
                timeView.setText(timeStr);

                if (cB2 && time % 5 == 0) {  //move boxes every 0.5 sec
                    cBox2();
                }

                if (running) {
                    time++;
                }
                handler.postDelayed(this, 100);
            }
        });
    }


    public void start(View v) {
        removeB();
        time = 0;
        running = true;
        fromWrongB= false;
        button();
    }

    int cbPos = position();
    int rbPos = position();
    int wrongBtPos = position();

    int btNum = 0;
    public void button() {

        Button b = new Button(this);
        b.setId(R.id.button_id);
        b.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                removeB();
                if (cbPos==rbPos || wrongBtPos==rbPos)rbPos++;
                if(cbPos==wrongBtPos)cbPos++;
                if (cbPos==btNum)checkbox();
                else if (rbPos==btNum)radiobutton();
                else if (wrongBtPos==btNum)wrongButton();
                else button();
            }

        });
        RelativeLayout r = (RelativeLayout) findViewById(R.id.layout);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(90, 90);
        params.leftMargin = randx();
        params.topMargin = randy();
        r.addView(b, params);
        btNum++;


        if (btNum == 20) {
            removeB();
            btNum=0;
            stopTimer();
        }

    }

    public void removeB() {
        RelativeLayout r = (RelativeLayout) findViewById(R.id.layout);
        Button b1 = (Button) findViewById(R.id.button_id);
        r.removeView(b1);
        TextView te = (TextView) findViewById(R.id.textView);
        te.setText("");
    }

public void wrongButton(){
    Button b = new Button(this);
    b.setText(":(");
    b.setId(R.id.button_id);
    b.setOnClickListener(new OnClickListener() {
        public void onClick(View v) {
            removeB();
            TextView t = (TextView)findViewById(R.id.textView);
            t.setText("Game Over!");
            btNum=0;
            fromWrongB = true;
            stopTimer();
        }
    });
    RelativeLayout r = (RelativeLayout) findViewById(R.id.layout);
    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(90, 90);
    params.leftMargin = randx();
    params.topMargin = randy();
    r.addView(b, params);
    btNum++;
    final Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
        @Override
        public void run() {
            if (!fromWrongB){
            removeB();
            button();}
        }
    }, 1000);
}

    CheckBox[] cb = new CheckBox[10];
    public void checkbox() {
        RelativeLayout r = (RelativeLayout) findViewById(R.id.layout);
        int[] xLoc = locX();
        int[] yLoc = locY();
        for (int  j = 0; j < 10; j++) {
            cb[j] = new CheckBox(this);
            CheckBox c =cb[j];
            int y = yLoc[j];
            int x = xLoc[j];

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(90, 90);
            params.leftMargin = x;
            params.topMargin = y;
            c.setId(j);

            c.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    boolean c = true;
                    for (CheckBox chb : cb) {

                        if (!chb.isChecked()) {
                            c = false;
                        }
                    }

                    if (c) {
                        RelativeLayout r = (RelativeLayout) findViewById(R.id.layout);

                        for (CheckBox box: cb) {
                        r.removeView(box);
                        }
                        button();

                    }
                }
            });
            r.addView(c, params);

        }

    }
    public int[] locX() {
        int[] randx = new int[10];
        for(int j=0;j<10;j++){
            int r = randx();

            for (int i=0;i<10;i++){
                if(Math.abs(randx[i] - r)<30){r=randx(); i=0;}
            }
            randx[j]=r;
        }
        return randx;
    }
    public int[] locY() {
        int[] randy = new int[10];
        for(int j=0;j<10;j++){
            int r = randy();

            for (int i=0;i<10;i++){
                if(Math.abs(randy[i] - r)<30){r=randy(); i=0;}
            }
            randy[j]=r;
        }
        return randy;
    }



public void radiobutton(){
    final int cRb= (int) Math.floor(Math.random() * 10);
    final RadioButton[] rb = new RadioButton[10];
    RelativeLayout r = (RelativeLayout) findViewById(R.id.layout);
    int[] xLoc = locX();
    int[] yLoc = locY();
    for (int  j = 0; j < 10; j++) {

        rb[j] = new RadioButton(this);
        RadioButton rB = rb[j];
        int y = yLoc[j];
        int x = xLoc[j];
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(90, 90);
        params.leftMargin = x;
        params.topMargin = y;
        rB.setId(j);
        rB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int id = v.getId();
                for (int i = 0; i < 10; i++) {

                    if (i == id) {
                        continue;
                    }
                    RadioButton radio = rb[i];
                    radio.setChecked(false);
                }
                if (cRb == id) {
                    for (int i = 0; i < 10; i++) {
                        RelativeLayout r = (RelativeLayout) findViewById(R.id.layout);
                        r.removeView(rb[i]);
                    }
                    button();
                    }
                }
            }

            );
            r.addView(rB, params);
    }
}


    CheckBox[] checkB;
    int[] cbPosX;
    int[] cbPosY;
    int[] dX;
    int[] dY;
    boolean[] isChecked = new boolean[] {false,false,false,false};
    public void checkBox2(View v) {
        cB2= true;             //runTimer was run from checkBox2
        runTimer();
        checkB = new CheckBox[4];
        cbPosX = new int[]{350, 350, 350, 350};
        cbPosY = new int[]{550, 550, 550, 550};
        dX = new int[4];
        dY = new int[4];
        for(int i = 0; i<4;i++){
            dX[i] = (int) (-10 + Math.random()*20);
            dY[i] = (int) (-14 + Math.random()*28);
        }

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(90, 90);
        params.leftMargin = 350;
        params.topMargin = 550;
        RelativeLayout r = (RelativeLayout) findViewById(R.id.layout);
       for(int i = 0; i<4;i++){
           checkB[i]=new CheckBox(this);
           checkB[i].setId(i);
           checkB[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton compoundButton, boolean isC) {
                   if (isC) {
                       isChecked[compoundButton.getId()] = true;
                   }
               }
           });

                r.addView(checkB[i], params);
            }

       }


    public void cBox2(){
        RelativeLayout r = (RelativeLayout) findViewById(R.id.layout);
        for (int j=0; j<4;j++){

            CheckBox box = checkB[j];
            cbPosX[j]+=dX[j];
            cbPosY[j]+=dY[j];
            r.removeView(box);
            RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(90, 90);
            p.leftMargin = cbPosX[j];
            p.topMargin = cbPosY[j];
            checkB[j] = new CheckBox(this);
            if(isChecked[j]){
                checkB[j].setChecked(true);
            }
            r.addView( checkB[j], p);
            }

        for(int i = 0; i<4;i++){
            if(cbPosX[i]<0 || cbPosX[i]>720 || cbPosY[i]<100 || cbPosY[i]>1280){
                r.removeView(checkB[i]);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(90, 90);
                params.leftMargin = 350;
                params.topMargin = 550;
                cbPosX[i]=350;
                cbPosY[i]=550;
                dX[i] = (int) (-10 + Math.random()*20);
                dY[i] = (int) (-14 + Math.random()*28);
                checkB[i]=new CheckBox(this);
                r.addView(checkB[i], params);
            }
        }
    }
}
