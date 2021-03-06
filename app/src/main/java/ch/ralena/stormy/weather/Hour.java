package ch.ralena.stormy.weather;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by crater-windoze on 10/22/2016.
 */

public class Hour implements Parcelable {
	private long mTime;
	private String mSummary;
	private double mTemperature;
	private String mIcon;
	private String mTimezone;

	public long getTime() {
		return mTime;
	}

	public String getHour() {
		SimpleDateFormat formatter = new SimpleDateFormat("h a");
		formatter.setTimeZone(TimeZone.getTimeZone(mTimezone));
		return formatter.format(new Date(mTime*1000));
	}

	public void setTime(long time) {
		mTime = time;
	}

	public String getSummary() {
		return mSummary;
	}

	public void setSummary(String summary) {
		mSummary = summary;
	}

	public int getTemperature(boolean isFahrenheit) {
		int temp;
		if (isFahrenheit) {
			temp = (int) Math.round(mTemperature);
		} else {
			temp = (int) Math.round((mTemperature-32)*5/9);
		}
		return temp;
	}

	public void setTemperature(double temperature) {
		mTemperature = temperature;
	}

	public String getIcon() {
		return mIcon;
	}

	public int getIconId() {
		return Forecast.getIconId(mIcon);
	}

	public void setIcon(String icon) {
		mIcon = icon;
	}

	public String getTimezone() {
		return mTimezone;
	}

	public void setTimezone(String timezone) {
		mTimezone = timezone;
	}

	private Hour(Parcel in) {
		mTime = in.readLong();
		mSummary = in.readString();
		mTemperature = in.readDouble();
		mIcon = in.readString();
		mTimezone = in.readString();
	}

	public Hour () {}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(mTime);
		dest.writeString(mSummary);
		dest.writeDouble(mTemperature);
		dest.writeString(mIcon);
		dest.writeString(mTimezone);
	}

	public static final Creator<Hour> CREATOR = new Creator<Hour>() {
		@Override
		public Hour createFromParcel(Parcel source) {
			return new Hour(source);
		}

		@Override
		public Hour[] newArray(int size) {
			return new Hour[size];
		}
	};
}
