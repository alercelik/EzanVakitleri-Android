package com.alercelik.ezanvakitleri.Model;

public class CurrentProgress {
    private PrayerTimes mPrayerTimes;
    private DatesTimesNames mDatesTimesNames;

    public int getCurrentTimeAsIndex() {
        /* 0 = "imsak"
         * 1 = "öğle"
         * 2 = "ikindi"
         * 3 = "kerahat"
         * 4 = "akşam"
         * 5 = "yatsı"
         */

        int currentTimeAsIndex = 0;

        String stringTime = mDatesTimesNames.getTime();

        return currentTimeAsIndex;
    }

}
