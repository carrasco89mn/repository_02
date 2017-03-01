package com.example.movieplayer;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


public class MainActivity extends Activity implements OnItemClickListener{

	/** Log tag. */
	private static final String LOG_TAG = "MoviePlayer";
	
	/**
	* On create lifecycle method.
	*
	* @param savedInstanceState saved state.
	* @see Activity#onCreate(Bundle)
	*/
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
     ArrayList<Movie> movieList = new ArrayList<Movie>();
     
     // Media columns to query
     String[] mediaColumns = { MediaStore.Video.Media._ID,
     MediaStore.Video.Media.TITLE, MediaStore.Video.Media.DURATION,
     MediaStore.Video.Media.DATA,
     MediaStore.Video.Media.MIME_TYPE };
     
     // Thumbnail columns to query
     String[] thumbnailColumns = { MediaStore.Video.Thumbnails.DATA };
     
     // Query external movie content for selected media columns
     //managedQuery() was before getContentResolver().query()
	 Cursor mediaCursor = getContentResolver().query(
     MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediaColumns,
     null, null, null);
     
     // Loop through media results
     if ((mediaCursor != null) && mediaCursor.moveToFirst()) {
     do {
    	 
     // Get the video id
     int id = mediaCursor.getInt(mediaCursor
     .getColumnIndex(MediaStore.Video.Media._ID));
     
     // Get the thumbnail associated with the video
     //managedQuery() was before getContentResolver().query()
	 Cursor thumbnailCursor = getContentResolver().query(
     MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
     thumbnailColumns, MediaStore.Video.Thumbnails.VIDEO_ID
     + "=" + id, null, null);
     
     // New movie object from the data
     Movie movie = new Movie(mediaCursor, thumbnailCursor);
     Log.d(LOG_TAG, movie.toString());
     
     // Add to movie list
     movieList.add(movie);
     } while (mediaCursor.moveToNext());
     }
     
     // Define movie list adapter
     MovieListAdapter movieListAdapter = new MovieListAdapter(this,
     movieList);
     
     // Set list view adapter to movie list adapter
     ListView movieListView = (ListView) findViewById(R.id.movieListView);
     movieListView.setAdapter(movieListAdapter);
     
     // Set item click listener
     movieListView.setOnItemClickListener(this);
     }
     /**
     * On item click listener.
     */
    public void onItemClick(AdapterView<?> parent, View view, int position, long
    		id) {
    		// Gets the selected movie
    		Movie movie = (Movie) parent.getAdapter().getItem(position);
    		
    		// Plays the selected movie
    		Intent intent = new Intent(Intent.ACTION_VIEW);
    		intent.setDataAndType(Uri.parse(movie.getMoviePath()),
    		movie.getMimeType());
    		startActivity(intent);
    		}
    
}
