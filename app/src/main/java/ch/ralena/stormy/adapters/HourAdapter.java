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
	Hour[] mHours;

	public HourAdapter(Hour[] hours) {
		mHours = hours;
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
				mTemperatureLabel.setText(hour.getTemperature()+"");
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


/*
	public class HourViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		public TextView mTimeLabel;
		public ImageView mIconImageView;
		public TextView mTemperatureLabel;
		public TextView mSummaryLabel;

		public HourViewHolder(View itemView) {
			super(itemView);

			mTimeLabel = (TextView) itemView.findViewById(R.id.timeLabel);
			mIconImageView = (ImageView) itemView.findViewById(R.id.iconImageView);
			mTemperatureLabel = (TextView) itemView.findViewById(R.id.temperatureLabel);
			mSummaryLabel = (TextView) itemView.findViewById(R.id.summaryLabel);
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			String time, temperature, summary;
			time = mTimeLabel.getText().toString();
			temperature = mTemperatureLabel.getText().toString();
			summary = mSummaryLabel.getText().toString();

			String message = String.format("At %s, it will be %s and %s",time, temperature, summary);
			Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
		}
	}
}
*/
