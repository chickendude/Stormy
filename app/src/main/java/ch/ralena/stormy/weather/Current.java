package ch.ralena.stormy.weather;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Current implements Parcelable {
	private String mIcon;
	private long mTime;
	private double mTemp;
	private double mHumidity;
	private double mPrecipChance;
	private String mSummary;
	private String mTimeZone;

	public Current() {
	}

	public int getIconId() {
		return Forecast.getIconId(mIcon);
	}

	public String getFormattedTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
		formatter.setTimeZone(TimeZone.getTimeZone(mTimeZone));
		return formatter.format(new Date(mTime * 1000));
	}

	public String getTimeZone() {
		return mTimeZone;
	}

	public void setTimeZone(String timeZone) {
		mTimeZone = timeZone;
	}

	public String getIcon() {
		return mIcon;
	}

	public void setIcon(String icon) {
		mIcon = icon;
	}

	public long getTime() {
		return mTime;
	}

	public void setTime(long time) {
		mTime = time;
	}

	public int getTemp() {
		return (int) Math.round(mTemp);
	}

	public int getTemp(boolean isFahrenheit) {
		int temp;
		if (isFahrenheit) {
			temp = (int) Math.round(mTemp);
		} else {
			temp = (int) Math.round((mTemp-32)*5/9);
		}
		return temp;
	}

	public void setTemp(double temp) {
		mTemp = temp;
	}

	public double getHumidity() {
		return mHumidity;
	}

	public void setHumidity(double humidity) {
		mHumidity = humidity;
	}

	public int getPrecipChance() {
		return (int) Math.round(mPrecipChance * 100);
	}

	public void setPrecipChance(double precipChance) {
		mPrecipChance = precipChance;
	}

	public String getSummary() {
		return mSummary;
	}

	public void setSummary(String summary) {
		mSummary = summary;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mIcon);
		dest.writeString(mTimeZone);
		dest.writeString(mSummary);
		dest.writeLong(mTime);
		dest.writeDouble(mTemp);
		dest.writeDouble(mHumidity);
		dest.writeDouble(mPrecipChance);
	}

	protected Current(Parcel in) {
		mIcon = in.readString();
		mTimeZone = in.readString();
		mSummary = in.readString();
		mTime = in.readLong();
		mTemp = in.readDouble();
		mHumidity = in.readDouble();
		mPrecipChance = in.readDouble();
	}

	public static final Creator<Current> CREATOR = new Creator<Current>() {
		@Override
		public Current createFromParcel(Parcel in) {
			return new Current(in);
		}

		@Override
		public Current[] newArray(int size) {
			return new Current[size];
		}
	};
}
