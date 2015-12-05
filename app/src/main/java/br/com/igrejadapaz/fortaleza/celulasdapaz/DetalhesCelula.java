package br.com.igrejadapaz.fortaleza.celulasdapaz;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import br.com.igrejadapaz.fortaleza.celulasdapaz.Bean.CelulaBean;

public class DetalhesCelula extends MainActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private CelulaBean celulaSelecionada;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_celula);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setTitle("Lista das Células");
//        bar.hide();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        celulaSelecionada = getIntent().getExtras().getParcelable("celulaSelecionada");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetalhesCelula.this, MapsCelulas.class);
                Bundle parametros = new Bundle();
                parametros.putString("filtroDia", "9");
                parametros.putString("filtroTipo", "9");
                intent.putExtras(parametros);
                intent.putExtra("celulaSelecionada", (Parcelable) celulaSelecionada);
                startActivity(intent);

            }
        });


        TextView nome = (TextView) findViewById(R.id.textViewNome);
        nome.setText(celulaSelecionada.getNome());
        TextView endereco = (TextView) findViewById(R.id.textViewEndereco);
        endereco.setText(celulaSelecionada.getEndereco());
        TextView lider = (TextView) findViewById(R.id.textViewNomeLider);
        lider.setText(celulaSelecionada.getLiderNome());
        TextView telefone = (TextView) findViewById(R.id.textViewTelefone);
        telefone.setText(celulaSelecionada.getTelefoneInformacao());
        TextView dia = (TextView) findViewById(R.id.textViewDiaHora);
        dia.setText(celulaSelecionada.getDiaHora());
        TextView tipo = (TextView) findViewById(R.id.textViewTipo);
        tipo.setText("Célula de " + celulaSelecionada.getTipo() + " - " + celulaSelecionada.getRede());


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

    }


    public void ligar(View view) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel: " + celulaSelecionada.getTelefoneInformacao()));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }

    public void toShared(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, celulaSelecionada.toShared());
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        startActivity(sendIntent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(4);
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.setContentDescription("Celulas em Fortaleza");
        mMap.stopAnimation();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(celulaSelecionada.getPosicao(), 17));
        mMap.addMarker(celulaSelecionada.getMarkerOptions());
        mMap.stopAnimation();

    }


}
