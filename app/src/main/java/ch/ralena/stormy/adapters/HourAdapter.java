package ch.ralena.stormy.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ch.ralena.stormy.R;
import ch.ralena.stormy.weather.Forecast;
import ch.ralena.stormy.weather.Hour;

/**
 * Created by crater-windoze on 12/3/2016.
 */

public class HourAdapter extends ListAdapter {
	private Hour[] mHours;
	private boolean mIsFahrenheit;

	public HourAdapter(Hour[] hours, boolean isFahrenheit) {
		mHours = hours;
		mIsFahrenheit = isFahrenheit;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_hourly, parent, false);
		return new ListViewHolder(view);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		((ListViewHolder)holder).bindView(mHours[position]);
	}

	@Override
	public int getItemCount() {
		return mHours.length;
	}

	@Override
	public void updateWeather(Forecast forecast) {
		mHours = forecast.getHourlyForecast();
		mIsFahrenheit = forecast.isFahrenheit();
	}

	private class ListViewHolder extends RecyclerView.ViewHolder {
		TextView mTimeLabel;
		ImageView mIconImageView;
		TextView mTemperatureLabel;
		TextView mSummaryLabel;

		public ListViewHolder(View itemView) {
			super(itemView);
			mTimeLabel = (TextView) itemView.findViewById(R.id.timeLabel);
			mIconImageView = (ImageView) itemView.findViewById(R.id.iconImageView);
			mTemperatureLabel = (TextView) itemView.findViewById(R.id.temperatureLabel);
			mSummaryLabel = (TextView) itemView.findViewById(R.id.summaryLabel);
		}

		public void bindView(Hour hour) {
			if (hour != null) {
				mTimeLabel.setText(hour.getHour());
				mIconImageView.setImageResource(hour.getIconId());
				mTemperatureLabel.setText(hour.getTemperature(mIsFahrenheit)+"");
				mSummaryLabel.setText(hour.getSummary());
			} else {
				mTimeLabel.setText("--");
				mIconImageView.setImageResource(R.drawable.clear_day);
				mTemperatureLabel.setText("--");
				mSummaryLabel.setText("No summary data yet");
			}
		}
	}
}
