package com.example.samplesearch;

import java.util.ArrayList;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.support.v4.widget.CursorAdapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;

public class MainActivity extends SherlockActivity implements SearchView.OnQueryTextListener,
SearchView.OnSuggestionListener{

	private static final String[] COLUMNS = {
		BaseColumns._ID,
		SearchManager.SUGGEST_COLUMN_TEXT_1,
	};

	ArrayList<String> testList = new ArrayList<String>();

	private SuggestionsAdapter mSuggestionsAdapter;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//Used to put dark icons on light action bar
		boolean isLight = SampleList.THEME == R.style.Theme_Sherlock_Light;

		testList.add("Android");
		testList.add("iOS");
		testList.add("Windows");
		testList.add("HTML5");
		testList.add("Blackberry");

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				MainActivity.this, R.layout.list_item, testList);

		ListView listView = (ListView)findViewById(R.id.listView1);
		listView.setAdapter(adapter);


		//Create the search view
		SearchView searchView = new SearchView(getSupportActionBar().getThemedContext());
		searchView.setQueryHint("Search for an item");
		searchView.setOnQueryTextListener(this);
		searchView.setOnSuggestionListener(this);

		if (mSuggestionsAdapter == null) {
			MatrixCursor cursor = new MatrixCursor(COLUMNS);
			cursor.addRow(new String[]{"1", "'Murica"});
			cursor.addRow(new String[]{"2", "Canada"});
			cursor.addRow(new String[]{"3", "Denmark"});
			mSuggestionsAdapter = new SuggestionsAdapter(getSupportActionBar().getThemedContext(), cursor);
		}

		//searchView.setSuggestionsAdapter(mSuggestionsAdapter);

		menu.add("Search")
		//.setIcon(isLight ? R.drawable.ic_search_inverse : R.drawable.abs__ic_search)
		.setActionView(searchView)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

		return true;
	}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT<Build.VERSION_CODES.HONEYCOMB)
		{
			requestWindowFeature(Window.FEATURE_NO_TITLE);
		}
		setTheme(R.style.Theme_Sherlock_Light);
		setContentView(R.layout.activity_main);

	}

	@Override
	public boolean onSuggestionSelect(int position) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onSuggestionClick(int position) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub

		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {

		ArrayList<String> nlist = new ArrayList<String>();

		if(newText.equals("")) {
			nlist = (ArrayList<String>)testList.clone();
		}else{
			for(String s : testList){
				if(s.contains(newText.toLowerCase()) || s.contains(newText.toUpperCase())){
					nlist.add(s);
				}
			}
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				MainActivity.this, R.layout.list_item, nlist);

		ListView listView = (ListView)findViewById(R.id.listView1);
		listView.setAdapter(adapter);

		listView.invalidateViews();


		return false;
	}

	private class SuggestionsAdapter extends CursorAdapter {

		public SuggestionsAdapter(Context context, Cursor c) {
			super(context, c, 0);
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			LayoutInflater inflater = LayoutInflater.from(context);
			View v = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
			return v;
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			TextView tv = (TextView) view;
			final int textIndex = cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1);
			tv.setText(cursor.getString(textIndex));
		}
	}

}
