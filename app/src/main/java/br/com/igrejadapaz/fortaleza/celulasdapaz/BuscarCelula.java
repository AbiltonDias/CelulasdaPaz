package br.com.igrejadapaz.fortaleza.celulasdapaz;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

import br.com.igrejadapaz.fortaleza.celulasdapaz.Dao.CelulaDao;

public class BuscarCelula extends MainActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText editTextEndereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_celula);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setTitle("Buscar Células");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        editTextEndereco = (EditText) findViewById(R.id.editTextEndereco);
        editTextEndereco.setFocusableInTouchMode(true);
        editTextEndereco.requestFocus();

        editTextEndereco.setOnKeyListener(new View.OnKeyListener() {
            /**
             * Called when a hardware key is dispatched to a view. This allows listeners to
             * get a chance to respond before the target view.
             * <p>Key presses in software keyboards will generally NOT trigger this method,
             * although some may elect to do so in some situations. Do not assume a
             * software input method has to be key-based; even if it is, it may use key presses
             * in a different way than you expect, so there is no way to reliably catch soft
             * input key presses.
             *
             * @param v       The view the key has been dispatched to.
             * @param keyCode The code for the physical key that was pressed
             * @param event   The KeyEvent object containing full information about
             *                the event.
             * @return True if the listener has consumed the event, false otherwise.
             */
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    irMapa();
                    return true;
                }
                return false;
            }
        });


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map3);
        mapFragment.getMapAsync(this);
    }

    public void verNoMapa(View view) {
        if (editTextEndereco == null || editTextEndereco.getText().toString().isEmpty()) {
            Intent intent = new Intent(BuscarCelula.this, Filtros.class);
            String para = "mapa";
            intent.putExtra("para", para);
            startActivity(intent);
        } else
            irMapa();
    }

    public void irMapa() {
        editTextEndereco = (EditText) findViewById(R.id.editTextEndereco);
        String endereco = editTextEndereco.getText().toString() + " Fortaleza, Ceará";
        Double latitude = null;
        Double longitude = null;
        try {
            latitude = getLatLngFromAddress(endereco).latitude;
            longitude = getLatLngFromAddress(endereco).longitude;
            String para = "mapa";
            Intent intent = new Intent(BuscarCelula.this, Filtros.class);
            intent.putExtra("para", para);
            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(BuscarCelula.this, "Não foi possível localizar o endereço: " + editTextEndereco.getText() + ". Favor tente novamente.", Toast.LENGTH_SHORT).show();
        }
    }

    public LatLng getLatLngFromAddress(String endereco) {
        Geocoder geocoder = new Geocoder(this);
        List<Address> listaEnderecos = null;
        Double latitude = null;
        Double longitude = null;
        try {
            listaEnderecos = geocoder.getFromLocationName(endereco, 3);
            if (listaEnderecos == null) {
                return null;
            }
            Address address = listaEnderecos.get(0);
            latitude = address.getLatitude();
            longitude = address.getLongitude();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new LatLng(latitude, longitude);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
//        mMap.setMapType(4);
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.setContentDescription("Celulas em Fortaleza");
        mMap.stopAnimation();
        LatLng fortaleza = new LatLng(-3.7913514, -38.5192009);
        CameraPosition cameraPosition = CameraPosition.builder().target(fortaleza).zoom(11).bearing(360).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 3000, null);
        CelulaDao dao = new CelulaDao(BuscarCelula.this);
        dao.getMarkers(mMap);
//        mMap.stopAnimation();

    }

}
