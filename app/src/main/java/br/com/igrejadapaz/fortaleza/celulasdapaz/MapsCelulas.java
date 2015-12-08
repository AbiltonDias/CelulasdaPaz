package br.com.igrejadapaz.fortaleza.celulasdapaz;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import br.com.igrejadapaz.fortaleza.celulasdapaz.Bean.CelulaBean;
import br.com.igrejadapaz.fortaleza.celulasdapaz.Dao.CelulaDao;

public class MapsCelulas extends MainActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private String filtro;
    private GoogleMap mMap;
    private LatLng endereco;
    private CelulaBean celulaSelecionada;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_celulas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setTitle("Mapas das Células");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        Bundle args = intent.getExtras();
        filtro = args.getString("filtroDia") + args.getString("filtroTipo");
        endereco = new LatLng(getIntent().getExtras().getDouble("latitude"),getIntent().getExtras().getDouble("longitude"));

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (celulaSelecionada == null) {
                    Snackbar.make(view, "Selecione uma Célula para mais informações", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Intent intent1 = new Intent(MapsCelulas.this, DetalhesCelula.class);
                    intent1.putExtra("celulaSelecionada", (Parcelable) celulaSelecionada);
                    startActivity(intent1);
                }
            }
        });
        fab.hide();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.setContentDescription("Celulas em Fortaleza");


        final LatLng igreja = new LatLng(-3.8129413, -38.449650);
        mMap.addMarker(new MarkerOptions().position(igreja).title("Igreja da Paz").snippet("Sede regional").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_ig_paz_55x55)));


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(igreja, 30));

        if(endereco == null || endereco.latitude == 0.0) {
            LatLng fortaleza = new LatLng(-3.7913514, -38.5192009);
            CameraPosition cameraPosition = CameraPosition.builder().target(fortaleza).zoom(11).bearing(360).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 5000, null);
        }else {
            CameraPosition cameraPosition = CameraPosition.builder().target(endereco).zoom(14).bearing(360).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 5000, null);
            mMap.addMarker(new MarkerOptions().position(endereco).title(getIntent().getExtras().getString("enderecoDigitado")));
        }

        marcarCelulas();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                CelulaDao dao = new CelulaDao(MapsCelulas.this);
                if (marker.getPosition().latitude != igreja.latitude){
                    celulaSelecionada = dao.getCelulaPosition(marker.getPosition());
                }else {
                    celulaSelecionada=null;
                }
                fab.show();
                dao.close();
                return false;
            }
        });

    }

    public void marcarCelulas() {

        CelulaDao celulaDao = new CelulaDao(MapsCelulas.this);

        switch (filtro) {
            case "00":
                celulaDao.getMarkers(mMap);
                break;
            case "01":
                celulaDao.getMarkersTipoId(mMap, 1);
                break;
            case "02":
                celulaDao.getMarkersTipoId(mMap, 2);
                break;
            case "03":
                celulaDao.getMarkersTipoId(mMap, 3);
                break;
            case "04":
                celulaDao.getMarkersTipoId(mMap, 4);
                break;
            case "10":
                celulaDao.getMarkersSemana(mMap);
                break;
            case "11":
                celulaDao.getMarkersSemanaTipoId(mMap, 1);
                break;
            case "12":
                celulaDao.getMarkersSemanaTipoId(mMap, 2);
                break;
            case "13":
                celulaDao.getMarkersSemanaTipoId(mMap, 3);
                break;
            case "14":
                celulaDao.getMarkersSemanaTipoId(mMap, 4);
                break;
            case "20":
                celulaDao.getMarkersSabado(mMap);
                break;
            case "21":
                celulaDao.getMarkersSabadoTipoId(mMap, 1);
                break;
            case "22":
                celulaDao.getMarkersSabadoTipoId(mMap, 2);
                break;
            case "23":
                celulaDao.getMarkersSabadoTipoId(mMap, 3);
                break;
            case "24":
                celulaDao.getMarkersSabadoTipoId(mMap, 4);
                break;

            case "99":
                CelulaBean celulaSelecionada = getIntent().getExtras().getParcelable("celulaSelecionada");
                mMap.addMarker(celulaSelecionada.getMarkerOptions());
                mMap.stopAnimation();
                CameraPosition cameraPosition = CameraPosition.builder().target(celulaSelecionada.getPosicao()).zoom(18).bearing(360).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 3000, null);
                break;
            default:
                celulaDao.getMarkers(mMap);
                Toast.makeText(MapsCelulas.this, "Filtros não aplicados ",
                        Toast.LENGTH_SHORT).show();
                break;
        }


        celulaDao.close();
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


}
