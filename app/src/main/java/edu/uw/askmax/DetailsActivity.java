package edu.uw.askmax;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.tutti.android.bottomsheet.BottomSheetActivity;
import edu.uw.askmax.model.Location;

public class DetailsActivity extends BottomSheetActivity {

    public static final String EXTRA_LOCATION = "location";

    @Bind(R.id.details_description) TextView descriptionView;
    @Bind(R.id.details_url) TextView urlView;
    @Bind(R.id.details_image) ImageView imageView;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras.containsKey(EXTRA_LOCATION)) {
            String json = extras.getString(EXTRA_LOCATION);
            Location location = Location.fromJson(json);

            setBottomSheetTitle(location.getTitle());

            descriptionView.setText(location.getDescription());
            urlView.setText(location.getUrl());
            Picasso.with(this).load(location.getImg()).into(imageView);
        }
    }
}
