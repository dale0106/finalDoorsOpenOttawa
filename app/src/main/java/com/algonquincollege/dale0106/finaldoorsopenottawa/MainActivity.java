package com.algonquincollege.dale0106.finaldoorsopenottawa;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ListView;

import com.algonquincollege.dale0106.finaldoorsopenottawa.model.Building;
import com.algonquincollege.dale0106.finaldoorsopenottawa.parsers.BuildingJSONParser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity implements AdapterView.OnItemClickListener {

    // URL to my RESTful API Service hosted on my Bluemix account.
    public static final String REST_URI = "https://doors-open-ottawa-hurdleg.mybluemix.net/buildings";
    public static final String IMAGES_BASE_URL = "https://doors-open-ottawa-hurdleg.mybluemix.net/";
    private static final String ABOUT_DIALOG_TAG;

    private DialogFragment mAboutDialog;

    private ProgressBar pb;
    private List<MyTask> tasks;
    private List<Building> buildingList;

    static {
        ABOUT_DIALOG_TAG = "About Dialog";

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Showing activity_main screen
        setContentView(R.layout.activity_main);
        pb = (ProgressBar) findViewById(R.id.progressBar1);
        //Set the progress bar to become invisible
        pb.setVisibility(View.INVISIBLE);
        tasks = new ArrayList<>();

        mAboutDialog = new AboutDialogFragment();


        //This handles the ListView item's onclick stuff
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.menu, menu);


        if (isOnline()) {
            // If isOnline() returns true, run requestData();
            requestData(REST_URI);
        } else {
            //otherwise send a toast error message
            Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
        }

        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_about) {

            mAboutDialog.show(getFragmentManager(), ABOUT_DIALOG_TAG);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //TODO: THIS IS HOW IM GOING TO GET THE CURRENT BUILDING INFO. GET POSITION THEN ACCESS ITS NEEDED METHODS
        Building theSelectedBuilding = buildingList.get(position);

        //Get building Name and store it
        String buildingName = theSelectedBuilding.getName();

        //Store and get building address
        String buildingAddress = theSelectedBuilding.getAddress();

        //Get building description
        String buildingDescription = theSelectedBuilding.getDescription();

        Toast.makeText(this, buildingName, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, buildingAddress, Toast.LENGTH_SHORT).show();

        //TODO: Intent that will create a new activity showing details of each list Item
        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Building", buildingName);
        intent.putExtra("Address", buildingAddress);
        intent.putExtra("Description", buildingDescription);
        startActivity(intent);

    }

    private void requestData(String uri) {
        MyTask task = new MyTask();
        task.execute(uri);

    }

    protected void updateDisplay() {
        BuildingAdapter adapter = new BuildingAdapter(this, R.layout.item_building, buildingList);
        setListAdapter(adapter);
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    private class MyTask extends AsyncTask<String, String, List<Building>> {

        @Override
        protected void onPreExecute() {
            if (tasks.size() == 0) {
                pb.setVisibility(View.VISIBLE);
            }
            tasks.add(this);
        }

        @Override
        protected List<Building> doInBackground(String... params) {

            String content = HttpManager.getData(params[0]);
            buildingList = BuildingJSONParser.parseFeed(content);
            return buildingList;
        }

        @Override
        protected void onPostExecute(List<Building> result) {

            tasks.remove(this);
            if (tasks.size() == 0) {
                pb.setVisibility(View.INVISIBLE);
            }

            if (result == null) {
                Toast.makeText(MainActivity.this, "Web service not available", Toast.LENGTH_LONG).show();
                return;
            }

            buildingList = result;
            updateDisplay();
        }
    }
}