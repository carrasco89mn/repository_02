package com.example.movieplayer;

import java.util.ArrayList;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieListAdapter extends BaseAdapter {

	/** Context instance. */
	private final Context context;
	/** Movie list. */
	private final ArrayList<Movie> movieList;
	
	/**
	* Constructor.
	*
	* @param context
	* context instance.
	* @param movieList
	* movie list.
	*/
	public MovieListAdapter(Context context, ArrayList<Movie> movieList) {
	this.context = context;
	this.movieList = movieList;
	}
	
	/**
	* Gets the number of elements in movie list.
	*
	* @see BaseAdapter#getCount()
	**/
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return movieList.size();
	}

	/**
	* Gets the movie item at given position.
	*
	* @param poisition
	* item position
	* @see BaseAdapter#getItem(int)
	*/	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return movieList.get(position);
	}
	
	/**
	* Gets the movie id at given position.
	*
	* @param position
	* item position
	* @return movie id
	* @see BaseAdapter#getItemId(int)
	*/
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/**
	* Gets the item view for given position.
	*
	* @param position
	* item position.
	* @param convertView
	* existing view to use.
	* @param parent
	* parent view.
	*/
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// Check if convert view exists or inflate the layout
		if (convertView == null) {
		LayoutInflater layoutInflater = (LayoutInflater) context
		.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = layoutInflater.inflate(R.layout.movie_item, null);
		}
		
		// Get the movie at given position
		Movie movie = (Movie) getItem(position);
		
		// Set thumbnail
		ImageView thumbnail = (ImageView) convertView
				              .findViewById(R.id.thumbnail);
		
		if (movie.getThumbnailPath() != null) {
			thumbnail.setImageURI(Uri.parse(movie.getThumbnailPath()));
			} else {
			thumbnail.setImageResource(R.drawable.ic_launcher);
			}
		
		// Set title
		TextView title = (TextView) convertView.findViewById(R.id.title);
		title.setText(movie.getTitle());
		// Set duration
		TextView duration = (TextView) convertView.findViewById(R.id.duration);
		duration.setText(getDurationAsString(movie.getDuration()));
		return convertView;
	}
	
	/**
	* Gets the given duration as string.
	*
	* @param duration
	* duration value.
	* @return duration string.
	*/
	private static String getDurationAsString(long duration) {
		// Calculate milliseconds
		long milliseconds = duration % 1000;
		long seconds = duration / 1000;
		// Calculate seconds
		long minutes = seconds / 60;
		seconds %= 60;
		// Calculate hours and minutes
		long hours = minutes / 60;
		minutes %= 60;
		// Build the duration string
		String durationString = String.format("%1$02d:%2$02d:%3$02d.%4$03d",
		hours, minutes, seconds, milliseconds);
		return durationString;
		}

}
