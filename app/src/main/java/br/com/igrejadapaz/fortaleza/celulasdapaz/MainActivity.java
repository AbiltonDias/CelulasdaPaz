package br.com.igrejadapaz.fortaleza.celulasdapaz;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.igrejadapaz.fortaleza.celulasdapaz.Bean.CelulaBean;
import br.com.igrejadapaz.fortaleza.celulasdapaz.Dao.CelulaDao;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<CelulaBean> listaDeCelulas = new ArrayList<CelulaBean>();
    private String para;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        cadastrosDeCelulas();
    }

    public void cadastrosDeCelulas(){
        CelulaDao dao = new CelulaDao(MainActivity.this);

        /** Cadastrar Celulas aqui!
         *  parametros do construtor (String nome, String endereco, String liderNome,
         *  String telefoneInformacao, String diaHora, Double latitude, Double longitude,
         *  int semanaID, int tipoID, int redeID)
         */

        listaDeCelulas.add(new CelulaBean("Célula MDA", "Av A, nº20 - José Walter", "Samuel", "99150-5007", "Sábados as 16h", -3.82568256, -38.55116218, 7, 3, 1));
        listaDeCelulas.add(new CelulaBean("Célula Geração Eleita", "Av Desembargador Praxedes, nº1441 - Montese", "Renata Granjeiro", "98821-7272", "Quintas as 20h", -3.7633416, -38.54193, 5, 3, 1));
        listaDeCelulas.add(new CelulaBean("Célula Eleitos por Cristo", "Rua Prof Isaías Gomes, nº157 - Edson Queiroz", "Cezar Peixoto", "99922-3309", "Quartas as 20h", -3.7588539, -38.4808816, 4, 4, 2));
        listaDeCelulas.add(new CelulaBean("Célula Crianças pra Cristo", "Av Desembargador Praxedes, nº1441 - Montese", "Rosemary", "98821-7272", "Quartas as 20h", -3.7633416, -38.54193, 5, 1, 1));
        listaDeCelulas.add(new CelulaBean("Célula AdoleSantos", "Rua 23, Jose Walter", "Camylla Oliveira", "98198-9323", "Sábados as 16h", -3.8249069, -38.5511938, 7, 2, 2));
        listaDeCelulas.add(new CelulaBean("Célula Sementes da Fé", "Rua Dr. Francisco Gadelha, 765 - Luciano Cavalcante", "Rodrigo", "99760-3510", "Sábados as 17h", -3.7751283, -38.4903772, 7, 3, 3));
        listaDeCelulas.add(new CelulaBean("Célula Família Real", "Rua Prof. Jacinto Botelho, 1080, Apt. 1205 - Guararapes", "Barreto Neto", "99929-8681", "Quintas as 19h", -3.7630173, -38.4917747, 5, 4, 1));


        if (dao.consultarCelulas().size() < listaDeCelulas.size()) {
            dao.reset();
            Toast.makeText(MainActivity.this, "Banco de dados atualizados",
                    Toast.LENGTH_SHORT).show();
            for (int i = 0; i < listaDeCelulas.size(); i++) {
                dao.inserirRegistro(listaDeCelulas.get(i));
            }
        }

        dao.close();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_maps) {
            para = "mapa";
            Intent intent = new Intent(MainActivity.this, Filtros.class);
            intent.putExtra("para", para);
            startActivity(intent);
        } else if (id == R.id.nav_list) {
            para = "lista";
            Intent intent = new Intent(MainActivity.this, Filtros.class);
            intent.putExtra("para", para);
            startActivity(intent);

        } else if (id == R.id.nav_buscar) {
            Intent intent = new Intent(MainActivity.this, BuscarCelula.class);
            startActivity(intent);

//        } else if (id == R.id.nav_manage) {

//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
