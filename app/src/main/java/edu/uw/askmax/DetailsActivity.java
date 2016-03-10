package edu.uw.askmax;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.uw.askmax.model.Location;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_LOCATION = "location";

    @Bind(R.id.details_title) TextView titleView;
    @Bind(R.id.details_description) TextView descriptionView;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras.containsKey(EXTRA_LOCATION)) {
            String json = extras.getString(EXTRA_LOCATION);
            Location location = Location.fromJson(json);

            titleView.setText(location.getTitle());
            descriptionView.setText(location.getDescription());
        }
    }
}
