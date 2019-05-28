package com.alercelik.ezanvakitleri.Model;

import android.util.Log;

import java.io.InputStream;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import com.alercelik.ezanvakitleri.R;

public class DatesTimesNames {
    private Locale mLocale;
    private Calendar mCalendar;
    private PrayerTimes mPrayerTimes;

    public Date getSpecificDate(int index) {
        return mPrayerTimes.getSpecificTime(index);
    }

    public String[] getAdlar() {
        return mPrayerTimes.getAdlar();
    }

    public String[] getVakitler() {
        return mPrayerTimes.getVakitler();
    }

    public Date[] getTimes() {
        return mPrayerTimes.getTimes();
    }

    public Date getOriginalDate() {
        return new Date();
    }

    public DatesTimesNames(InputStream inputStream) {
        mLocale = new Locale("TR", "tr");
        mPrayerTimes = new PrayerTimes(inputStream, mLocale, getDayOfYear());
    }

    public int getDayOfYear() {
        mCalendar = Calendar.getInstance();
        return mCalendar.get(Calendar.DAY_OF_YEAR);
    }

    public String getTime() {
        return DateFormat.getTimeInstance(DateFormat.DEFAULT, mLocale).format(new Date(getOriginalDate().getTime() - 2*1000*60*60));
    }

    public String getDate() {
        return DateFormat.getDateInstance(DateFormat.FULL, mLocale).format(getOriginalDate());
    }

    public String remainFormatter(Date date) {
        String stringToReturn;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if (calendar.get(Calendar.HOUR) < 1) {
            if (calendar.get(Calendar.MINUTE) < 1) {
                stringToReturn = calendar.get(Calendar.SECOND) + " saniye";
            } else {
                stringToReturn = calendar.get(Calendar.MINUTE) + " dakika " + calendar.get(Calendar.SECOND) + " saniye";
            }
        } else {
            stringToReturn = calendar.get(Calendar.HOUR) + " saat " + calendar.get(Calendar.MINUTE) + " dakika";
        }

        return stringToReturn;
    }

    public int findCurrentTimeAsIndex() {
        int currentTimeAsIndex = 0;
        Date[] dates = getTimes();

        for(int i = 0; i < dates.length; i++) {
            if(getOriginalDate().before(dates[i])) {
                currentTimeAsIndex =  i - 1;
                Log.v("As", "CURRENT TIME AS INDEX = " + i);
                break;
            }
            currentTimeAsIndex = i;
        }

        return (currentTimeAsIndex + 6) % 6;
    }

    public String getTimeRemainingAsString() {
        int currentTimeAsIndex = findCurrentTimeAsIndex();
        long difference = mPrayerTimes.getSpecificTime(currentTimeAsIndex + 1).getTime() - getOriginalDate().getTime();
        return remainFormatter(new Date(difference - 2*1000*60*60));
    }

    public long getTimeRemaining(){
        int currentTimeAsIndex = findCurrentTimeAsIndex();
        long difference = mPrayerTimes.getSpecificTime(currentTimeAsIndex + 1).getTime() - getOriginalDate().getTime();
        return difference;
    }

    public String getNextTime() {
        String nextTime = mPrayerTimes.getSpecificAd(findCurrentTimeAsIndex() + 1);
        String suffix = "Vaktine Kalan Süre:";

        return nextTime + " " + suffix;
    }

    public String getCurrentNameAndTime() {
        String currentName = mPrayerTimes.getSpecificAd(findCurrentTimeAsIndex());
        String currentTime = mPrayerTimes.getSpecificVakit(findCurrentTimeAsIndex());

        return currentName + " - " + currentTime;
    }

    public int getCurrentImageID() {
        int id = 0;
        int currentTimeAsIndex = findCurrentTimeAsIndex();

        switch (currentTimeAsIndex){
            case 0: id = R.drawable.imsak_simge;
                    break;
            case 1: id = R.drawable.g_ne__simge;
                    break;
            case 2: id = R.drawable.__le_simge;
                    break;
            case 3: id = R.drawable.ikindi_simge;
                    break;
            case 4: id = R.drawable.ak_am_simge;
                    break;
            case 5: id = R.drawable.yats__simge;
                    break;
        }

        return id;
    }
}
