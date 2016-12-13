package ch.ralena.stormy.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ch.ralena.stormy.R;
import ch.ralena.stormy.weather.Day;
import ch.ralena.stormy.weather.Forecast;

/**
 * Created by crater-windoze on 10/25/2016.
 */

public class DayAdapter extends ListAdapter {
	private static final String TAG = DayAdapter.class.getSimpleName();
	private Day[] mDays;
	private boolean mIsFahrenheit;

	public DayAdapter(Day[] days) {
		if (days != null) {
			mDays = days;
		} else
			mDays = new Day[0];
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_daily, parent, false);
		return new ListViewHolder(view);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		((ListViewHolder)holder).onBindView(mDays[position]);
	}

	@Override
	public int getItemCount() {
		return mDays.length;
	}

	@Override
	public void updateWeather(Forecast forecast) {
		mDays = forecast.getDailyForecast();
		mIsFahrenheit = forecast.isFahrenheit();
	}

	private class ListViewHolder extends RecyclerView.ViewHolder {
		private ImageView mIconImageView;
//		private ImageView mCircleImageView;
		private TextView mTemperatureLabel;
		private TextView mDayLabel;

		public ListViewHolder(View itemView) {
			super(itemView);
			mIconImageView = (ImageView) itemView.findViewById(R.id.iconImageView);
//			mCircleImageView = (ImageView) itemView.findViewById(R.id.circleImageView);
			mTemperatureLabel = (TextView) itemView.findViewById(R.id.temperatureLabel);
			mDayLabel = (TextView) itemView.findViewById(R.id.dayNameLabel);
		}

		public void onBindView(Day day) {
			mIconImageView.setImageResource(day.getIconId());
//			mCircleImageView.setImageResource(day.getIconId());
			mTemperatureLabel.setText(day.getTemperatureMax(mIsFahrenheit)+"");
			mDayLabel.setText(day.getDayOfTheWeek());
		}
	}


/*
	private Context mContext;
	private Day[] mDays;

	public DayAdapter(Context context, Day[] days) {
		mContext = context;
		mDays = days;
	}


	@Override
	public int getCount() {
		return mDays.length;
	}

	@Override
	public Object getItem(int position) {
		return mDays[position];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView==null) {
			// there was no previous view to erase
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_daily, null);
			holder = new ViewHolder();
			holder.iconImageView = (ImageView) convertView.findViewById(R.id.iconImageView);
			holder.circleImageView = (ImageView) convertView.findViewById(R.id.circleImageView);
			holder.temperatureLabel = (TextView) convertView.findViewById(R.id.temperatureLabel);
			holder.dayLabel = (TextView) convertView.findViewById(R.id.dayNameLabel);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Day day = mDays[position];

		holder.iconImageView.setImageResource(day.getIconId());
		holder.circleImageView.setImageResource(R.drawable.bg_temperature);
		holder.temperatureLabel.setText(day.getTemperatureMax()+"");
		if(position==0) {
			holder.dayLabel.setText("Today");
		} else {
			holder.dayLabel.setText(day.getDayOfTheWeek());
		}
		return convertView;
	}

	private static class ViewHolder {
		ImageView iconImageView;
		ImageView circleImageView;
		TextView temperatureLabel;
		TextView dayLabel;
	}
*/
}
