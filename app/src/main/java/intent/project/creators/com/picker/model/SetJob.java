package intent.project.creators.com.picker.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ngohaihue on 9/19/17.
 */

public class SetJob {
    private String mTitle;
    private String mDiscription;
    private Date mDateFinish;
    private Date mHourFinish;

    public SetJob() {
    }

    public SetJob(String mTitle, String mDiscription, Date mDateFinish, Date mHourFinish) {
        this.mTitle = mTitle;
        this.mDiscription = mDiscription;
        this.mDateFinish = mDateFinish;
        this.mHourFinish = mHourFinish;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDiscription() {
        return mDiscription;
    }

    public void setmDiscription(String mDiscription) {
        this.mDiscription = mDiscription;
    }

    public Date getmDateFinish() {
        return mDateFinish;
    }

    public void setmDateFinish(Date mDateFinish) {
        this.mDateFinish = mDateFinish;
    }

    public Date getmHourFinish() {
        return mHourFinish;
    }

    public void setmHourFinish(Date mHourFinish) {
        this.mHourFinish = mHourFinish;
    }
    public String getDateFormat(Date d)
    {
        SimpleDateFormat dft=new
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return dft.format(d);
    }
    public String getHourFormat(Date d)
    {
        SimpleDateFormat dft=new
                SimpleDateFormat("hh:mm a", Locale.getDefault());
        return dft.format(d);
    }

    @Override
    public String toString() {
        return this.mTitle+"-"+getDateFormat(this.mDateFinish)+getHourFormat(this.mHourFinish);
    }


}
