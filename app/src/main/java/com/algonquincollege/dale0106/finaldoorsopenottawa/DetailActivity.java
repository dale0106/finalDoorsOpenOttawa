package com.algonquincollege.dale0106.finaldoorsopenottawa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.app.Activity;

import com.algonquincollege.dale0106.finaldoorsopenottawa.model.Building;

import org.w3c.dom.Text;

public class DetailActivity extends Activity {

    private TextView buildingName;
    private TextView buildingAddress;
    private TextView buildingDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        buildingName = (TextView) findViewById( R.id.buildingName);
        buildingDescription = (TextView) findViewById(R.id.buildingDescription);





        Bundle bundle = getIntent().getExtras();

        String buildingNameFromMainActivity = bundle.getString("Building");
        String buildingAddressFromMainActivity = bundle.getString("Address");
        String buildingDescriptionFromMainActivity = bundle.getString("Description");

        buildingName.setText(buildingNameFromMainActivity);
        buildingDescription.setText(buildingDescriptionFromMainActivity);


    }
}
