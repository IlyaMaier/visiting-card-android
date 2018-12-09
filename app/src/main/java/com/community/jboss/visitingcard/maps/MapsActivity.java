package com.community.jboss.visitingcard.maps;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.community.jboss.visitingcard.R;
import com.community.jboss.visitingcard.data.CardsDatabase;
import com.community.jboss.visitingcard.data.VisitingCard;
import com.community.jboss.visitingcard.data.VisitingCardDao;
import com.community.jboss.visitingcard.savedcards.SavedCardsActivity;
import com.community.jboss.visitingcard.savedcards.SavedCardsAdapter;
import com.community.jboss.visitingcard.visitingcard.ViewVisitingCard;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // TODO: Replace the TextView with a ListView containing list of Visiting cards in that locality using geo-fencing

        // TODO: List item click should result in launching of ViewVisitingCard Acitivity with the info of the tapped Visiting card.

        findViewById(R.id.btn_saved_cards).setOnClickListener(view -> {
            Intent i = new Intent(MapsActivity.this, SavedCardsActivity.class);
            i.putExtra("favourite", false);
            startActivity(i);
        });

        findViewById(R.id.btn_favourite_cards).setOnClickListener(view -> {
            Intent i = new Intent(MapsActivity.this, SavedCardsActivity.class);
            i.putExtra("favourite", true);
            startActivity(i);
        });

        //TODO: Create Custom pins for the selected location
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mInitRV();

        //TODO: Implement geo-fencing(NOT AS A WHOLE) just visual representation .i.e., a circle of an arbitrary radius with the PIN being the centre of it.
        //TODO: Make the circle color as @color/colorAccent
    }


    // TODO: Replace the stating location with user's current location.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = new LatLng(-34, 151);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"))
                .setIcon(
                        BitmapDescriptorFactory.fromResource(R.drawable.custom_pin)
                );
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void mInitRV() {
        RecyclerView recyclerView = findViewById(R.id.rv_maps);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SavedCardsAdapter mAdapter = new SavedCardsAdapter();
        mAdapter.updateData(mGetData(), this, true);
        recyclerView.setAdapter(mAdapter);
    }

    private List<VisitingCard> mGetData() {
        List<VisitingCard> visitingCards = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            visitingCards.add(new VisitingCard(
                    i + "",
                    "Example" + i,
                    "+0123456789",
                    "email@email.com",
                    "",
                    "",
                    "",
                    null,
                    0
            ));
        }
        return visitingCards;
    }

}
