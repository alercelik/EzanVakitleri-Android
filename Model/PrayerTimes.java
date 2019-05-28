package com.alercelik.ezanvakitleri.Model;

//Triggered when an I/O error occurs
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

//important imports
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.text.DateFormat;

//Represents your XML document and contains useful methods
import org.jdom2.Document;
//Represents XML elements and contains useful methods
import org.jdom2.Element;
//Top level JDOM exception
import org.jdom2.JDOMException;
//Creates a JDOM document parsed using SAX Simple API for XML
import org.jdom2.input.SAXBuilder;


public class PrayerTimes {
    private String[] mVakitler;
    private String[] mAdlar;
    private Date[] mTimes;
    private Locale mLocale;

    private static final long GMTDIFFERENCE = 2*1000*60*60;

    public String[] getVakitler() {
        return mVakitler;
    }

    public String[] getAdlar() {
        return mAdlar;
    }

    public Date[] getTimes(){
        return mTimes;
    }

    public Date getSpecificTime(int index) {
        index = (index + mAdlar.length) % mAdlar.length;
        return mTimes[index];
    }

    public String getSpecificAd(int index) {
        index = (index + mAdlar.length) % mAdlar.length;
        return mAdlar[index];
    }

    public String getSpecificVakit(int index) {
        index = (index + mAdlar.length) % mAdlar.length;
        return mVakitler[index];
    }

    //constructor
    public PrayerTimes (InputStream inputStream, Locale locale, int today) {
        mLocale = locale;
        setAdlar();
        readXML(inputStream, today); //sets Vakitler
    }

    private void setAdlar() {
        mAdlar = new String[6];

        mAdlar[0] = "İmsak";
        mAdlar[1] = "Güneş";
        mAdlar[2] = "Öğle";
        mAdlar[3] = "İkindi";
        mAdlar[4] = "Akşam";
        mAdlar[5] = "Yatsı";
        //mAdlar[6] = "Kerahat";
    }

    private void readXML(InputStream inputStream, int today) {
        SAXBuilder builder = new SAXBuilder();
        try {
            Document readDoc = builder.build(inputStream);
            Element root = readDoc.getRootElement();

            String[] tumVakitler = new String[14];
            for(Element current : root.getChildren("prayertimes")) {
                if ( Integer.parseInt(current.getAttributeValue("dayofyear")) == today){
                    String temp = current.getText();
                    temp = temp.replaceAll("\\s+", ",");
                    temp = temp.replaceAll(",", " ");
                    temp = temp.substring(1, temp.length() - 5);
                    temp += " ";
                    Date forDate;

                    for(int i = 0; i < 14; i++) {
                        try {
                            forDate = new SimpleDateFormat("H:mm").parse(temp);
                            tumVakitler[i] = new SimpleDateFormat("H:mm").format(forDate);
                            temp = temp.substring(tumVakitler[i].length() + 1, temp.length());
                        }	catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } //end for

                    break;
                } //end if
            } //end outer for


            mVakitler = new String[6];
            mVakitler[0] = "0" + tumVakitler[1]; //imsak
            mVakitler[1] = "0" + tumVakitler[2]; //güneş
            mVakitler[2] = tumVakitler[5]; //öğle
            mVakitler[3] = tumVakitler[6]; //ikindi
            mVakitler[4] = tumVakitler[9]; //akşam
            mVakitler[5] = tumVakitler[11]; //yatsı
            //mVakitler[6] = tumVakitler[8]; //kerahat

            mTimes = new Date[6];
            for (int i = 0; i < 6; i++) {
                try {
                    mTimes[i] = new SimpleDateFormat("HH:mm").parse(mVakitler[i]);
                    mTimes[i].setTime(mTimes[i].getTime() + GMTDIFFERENCE*3/2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            //Setting times the same as Diyanet's
            mTimes[0].setTime(mTimes[0].getTime() - 2 * 1000*60);
            mTimes[1].setTime(mTimes[1].getTime() + 1000*60);
            mTimes[2].setTime(mTimes[2].getTime() - 4 *1000*60);
            mTimes[3].setTime(mTimes[3].getTime() - 7 *1000*60);
            mTimes[4].setTime(mTimes[4].getTime());
            mTimes[5].setTime(mTimes[5].getTime() - 9 *1000*60);

            for (int i = 0; i < 6; i++) {
                mVakitler[i] = DateFormat.getTimeInstance(DateFormat.SHORT, mLocale).format( new Date(mTimes[i].getTime() - GMTDIFFERENCE));
            }

            Calendar calendar = Calendar.getInstance();
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

            for (Date curTime: mTimes) {
                curTime.setTime(curTime.getTime() + calendar.getTimeInMillis());
            }


        }
        catch (JDOMException | IOException e) {
            e.printStackTrace();
        }
    }
}
