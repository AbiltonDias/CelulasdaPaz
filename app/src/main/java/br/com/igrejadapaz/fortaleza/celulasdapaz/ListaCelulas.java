package br.com.igrejadapaz.fortaleza.celulasdapaz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.igrejadapaz.fortaleza.celulasdapaz.Bean.CelulaBean;
import br.com.igrejadapaz.fortaleza.celulasdapaz.Dao.CelulaDao;

public class ListaCelulas extends MainActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private ListView listViewCelula;
    private CelulaBean celulaSelecionada;
    private List<CelulaBean> registrosCelula;
    private ArrayAdapter<CelulaBean> adaptadorLista;
    private int adptadorLayout = android.R.layout.simple_list_item_1;
    private String filtro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_celulas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setTitle("Lista das Células");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        filtro = getIntent().getExtras().getString("filtroDia") + getIntent().getExtras().getString("filtroTipo");

        listViewCelula = (ListView) findViewById(R.id.listView);
        registerForContextMenu(listViewCelula);
        CelulaDao dao = new CelulaDao(ListaCelulas.this);

        registrosCelula = carregarCelulas(dao);
        adaptadorLista = new ArrayAdapter<CelulaBean>(this, adptadorLayout, registrosCelula);
        listViewCelula.setAdapter(adaptadorLista);

        listViewCelula.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                celulaSelecionada = (CelulaBean) adaptadorLista.getItem(position);
                Intent intent = new Intent(ListaCelulas.this, DetalhesCelula.class);
                intent.putExtra("celulaSelecionada", (Parcelable) celulaSelecionada);
                startActivity(intent);
            }
        });
        listViewCelula.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                celulaSelecionada = (CelulaBean) adaptadorLista.getItem(position);
                return false;
            }
        });
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_contexto, menu);

        ListView lv = (ListView) v;
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final CelulaBean celulaBean = (CelulaBean) lv.getItemAtPosition(acmi.position);
        String nome = celulaBean.getNome();
        menu.setHeaderTitle(nome);
    }

    public boolean onContextItemSelected(MenuItem item) {
        Intent intent = null;
        if (item.getItemId() == R.id.itemInfor) {
            intent = new Intent(ListaCelulas.this, DetalhesCelula.class);
            intent.putExtra("celulaSelecionada", (Parcelable) celulaSelecionada);
            startActivity(intent);
        }
        return super.onContextItemSelected(item);
    }


    private List<CelulaBean> carregarCelulas(CelulaDao dao) {

        List<CelulaBean> list = null;

        switch (filtro) {
            case "00":
                list = dao.consultarCelulas();
                break;
            case "01":
                list = dao.consultarCelulasTipoId(1);
                break;
            case "02":
                list = dao.consultarCelulasTipoId(2);
                break;
            case "03":
                list = dao.consultarCelulasTipoId(3);
                break;
            case "04":
                list = dao.consultarCelulasTipoId(4);
                break;
            case "10":
                list = dao.consultarCelulasSemana();
                break;
            case "11":
                list = dao.consultarCelulasSemanaTipoId(1);
                break;
            case "12":
                list = dao.consultarCelulasSemanaTipoId(2);
                break;
            case "13":
                list = dao.consultarCelulasSemanaTipoId(3);
                break;
            case "14":
                list = dao.consultarCelulasSemanaTipoId(4);
                break;
            case "20":
                list = dao.consultarCelulasSabado();
                break;
            case "21":
                list = dao.consultarCelulasSabadoTipoId(1);
                break;
            case "22":
                list = dao.consultarCelulasSabadoTipoId(2);
                break;
            case "23":
                list = dao.consultarCelulasSabadoTipoId(3);
                break;
            case "24":
                list = dao.consultarCelulasSabadoTipoId(4);
                break;
            default:
                list = dao.consultarCelulas();
                Toast.makeText(ListaCelulas.this, "Filtros não aplicados ",
                        Toast.LENGTH_SHORT).show();
                break;
        }
        return list;
    }

}
