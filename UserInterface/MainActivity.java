package com.alercelik.ezanvakitleri.UserInterface;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alercelik.ezanvakitleri.Model.DatesTimesNames;
import com.alercelik.ezanvakitleri.R;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //Class Variables
    private DatesTimesNames mDatesTimesNames;

    //Layout
    private TextView lTextView_Main;
    private RelativeLayout lMainRelativeLayout;

    private TextView[] lNameTextViews;
    private TextView[] lTimeTextViews;
    private ImageView[] lVakitImageViews;
    private RelativeLayout[] lRelativeLayouts;

    private TextView lCurrentNameAndTimeTextView;
    private TextView lCurrentNextTimeTextView;
    private TextView lCurrentTimeReaminingTextView;
    private ImageView lCurrentImageView;
    private RelativeLayout lCurrentRelativeLayout;

    //private Handler mCurrentTimeHandler;
    private Handler mTimeRemainingHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializes all layout components
        initializer();

//        mCurrentTimeHandler = new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                mCurrentTimeTextView.setText(mDatesTimesNames.getTime());
//            }
//        };

        mTimeRemainingHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                lCurrentTimeReaminingTextView.setText(mDatesTimesNames.getTimeRemainingAsString());
                /*if (mDatesTimesNames.getTimeRemaining() < 1000*60*60*15) {
                    lCurrentRelativeLayout.setBackgroundColor(Color.parseColor("#88550000"));
                }*/
            }
        };
        /* file input */
        InputStream inputStream = null;
        try {
            inputStream = getAssets().open("ezanxml.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //class variables
        mDatesTimesNames = new DatesTimesNames(inputStream);

        toScreen();
        Log.v("Sa", "Şimdi = " + new Date().toString());
        Log.v("Sa", "Şimdi = " + DateFormat.getTimeInstance(DateFormat.SHORT, new Locale("TR", "tr")).format(new Date()));
        Log.v("Sa", "İkindi = " + mDatesTimesNames.getSpecificDate(3).toString());
        Log.v("Sa", "Öğle = " + mDatesTimesNames.getSpecificDate(2).toString());
        }

    private void toScreen() {
        currentTimeBlock();
        //currentTimeDisplayer();
        dateAndCityToScreen();
        prayerTimesToScreen();
        prayerNamesToScreen();
        prayerImagesToScreen();
    }

    private void currentTimeBlock() {
        lCurrentNameAndTimeTextView.setText(mDatesTimesNames.getCurrentNameAndTime());
        lCurrentNextTimeTextView.setText(mDatesTimesNames.getNextTime());
        lCurrentImageView.setImageResource(mDatesTimesNames.getCurrentImageID());

        RemainingTimeDisplayer();   //A thread
    }

    private void prayerImagesToScreen() {
        //lVakitImageViews[0].setImageDrawable(getDrawable(R.drawable.imsak_simge_400));
        //lVakitImageViews[1].setImageResource(R.drawable.g_ne__simge___kirpilmamis);
    }

/*
private void currentTimeDisplayer() {
Runnable runnie = new Runnable() {
@Override
public void run() {
while (true){
synchronized (this) {
mCurrentTimeHandler.sendEmptyMessage(0);

try {
wait(1000);
} catch (InterruptedException e) {
e.printStackTrace();
}
}
}
}
};
Thread timeThread = new Thread(runnie);
timeThread.start();
}
*/

    private void RemainingTimeDisplayer() {
        Runnable runi = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (this) {
                        mTimeRemainingHandler.sendEmptyMessage(0);

                        try {
                            wait(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        Thread remainThread = new Thread(runi);
        remainThread.start();
    }

    private void dateAndCityToScreen() {
        String city = "İstanbul";
        String date = mDatesTimesNames.getDate();

        String stringToScreen = date + " - " + city;
        lTextView_Main.setText(stringToScreen);
    }

    private void prayerNamesToScreen() {
        String[] tempAdlar = mDatesTimesNames.getAdlar();

        for (int i = 0; i < tempAdlar.length; i++) {
            lNameTextViews[i].setText(tempAdlar[i]);
        }
    }

    private void prayerTimesToScreen() {
        String[] tempVakitler = mDatesTimesNames.getVakitler();

        for (int i = 0; i < tempVakitler.length; i++) {
            lTimeTextViews[i].setText(tempVakitler[i]);
        }
    }

    private void initializer() {
        lTextView_Main = (TextView) findViewById(R.id.textView_Main);
        lMainRelativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout_Main);

        lNameTextViews = new TextView[6];
        lNameTextViews[0] = (TextView) findViewById(R.id.nameTextView_1);
        lNameTextViews[1] = (TextView) findViewById(R.id.nameTextView_2);
        lNameTextViews[2] = (TextView) findViewById(R.id.nameTextView_3);
        lNameTextViews[3] = (TextView) findViewById(R.id.nameTextView_4);
        lNameTextViews[4] = (TextView) findViewById(R.id.nameTextView_5);
        lNameTextViews[5] = (TextView) findViewById(R.id.nameTextView_6);

        lTimeTextViews = new TextView[6];
        lTimeTextViews[0] = (TextView) findViewById(R.id.timeTextView_1);
        lTimeTextViews[1] = (TextView) findViewById(R.id.timeTextView_2);
        lTimeTextViews[2] = (TextView) findViewById(R.id.timeTextView_3);
        lTimeTextViews[3] = (TextView) findViewById(R.id.timeTextView_4);
        lTimeTextViews[4] = (TextView) findViewById(R.id.timeTextView_5);
        lTimeTextViews[5] = (TextView) findViewById(R.id.timeTextView_6);

        lVakitImageViews = new ImageView[6];
        lVakitImageViews[0] = (ImageView) findViewById(R.id.vakitImageView_1);
        lVakitImageViews[1] = (ImageView) findViewById(R.id.vakitImageView_2);
        lVakitImageViews[2] = (ImageView) findViewById(R.id.vakitImageView_3);
        lVakitImageViews[3] = (ImageView) findViewById(R.id.vakitImageView_4);
        lVakitImageViews[4] = (ImageView) findViewById(R.id.vakitImageView_5);
        lVakitImageViews[5] = (ImageView) findViewById(R.id.vakitImageView_6);

        lRelativeLayouts = new RelativeLayout[6];
        lRelativeLayouts[0] = (RelativeLayout) findViewById(R.id.relativeLayout_1);
        lRelativeLayouts[1] = (RelativeLayout) findViewById(R.id.relativeLayout_2);
        lRelativeLayouts[2] = (RelativeLayout) findViewById(R.id.relativeLayout_3);
        lRelativeLayouts[3] = (RelativeLayout) findViewById(R.id.relativeLayout_4);
        lRelativeLayouts[4] = (RelativeLayout) findViewById(R.id.relativeLayout_5);
        lRelativeLayouts[5] = (RelativeLayout) findViewById(R.id.relativeLayout_6);

        lCurrentNameAndTimeTextView = (TextView) findViewById(R.id.nameAndTimeTextView_Current);
        lCurrentNextTimeTextView = (TextView) findViewById(R.id.nextTimeTextView_Current);
        lCurrentTimeReaminingTextView = (TextView) findViewById(R.id.timeRemaniningTextView_Current);
        lCurrentImageView = (ImageView) findViewById(R.id.imageView_Current);
        lCurrentRelativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout_Current);
    }
}