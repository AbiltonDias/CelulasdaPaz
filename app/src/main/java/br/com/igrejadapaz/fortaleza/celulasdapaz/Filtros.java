package br.com.igrejadapaz.fortaleza.celulasdapaz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class Filtros extends AppCompatActivity {

    private String txtFiltroDia = "0";
    private String txtFiltroTipo = "0";
    private String para;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtros);

        para = getIntent().getExtras().getString("para");

        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.hide();
    }

    public void buttonDiaTodos(View view) {
        txtFiltroDia = "0";
    }

    public void buttonTipoTodos(View view) {
        txtFiltroTipo = "0";
    }

    public void buttonDiaSemana(View view) {
        txtFiltroDia = "1";
    }

    public void buttonDiaSabado(View view) {
        txtFiltroDia = "2";
    }

    public void buttonTipoCriancas(View view) {
        txtFiltroTipo = "1";
    }

    public void buttonTipoAdolecentes(View view) {
        txtFiltroTipo = "2";
    }

    public void buttonTipoJovens(View view) {
        txtFiltroTipo = "3";
    }

    public void buttonTipoAdultos(View view) {
        txtFiltroTipo = "4";
    }

    public void continuar(View view){
        Intent intent;
        Bundle parametros = new Bundle();
        parametros.putString("filtroDia", txtFiltroDia);
        parametros.putString("filtroTipo", txtFiltroTipo);

        switch (para){
            case "mapa":
                Toast.makeText(Filtros.this, "Carregando Mapa...",
                        Toast.LENGTH_LONG).show();
                intent = new Intent(Filtros.this, MapsCelulas.class);
                intent.putExtras(parametros);
                intent.putExtra("latitude",getIntent().getExtras().getDouble("latitude"));
                intent.putExtra("longitude",getIntent().getExtras().getDouble("longitude"));
                startActivity(intent);
                break;
            case "lista":
                intent = new Intent(Filtros.this, ListaCelulas.class);
                intent.putExtras(parametros);
                startActivity(intent);
                break;
        }
    }
}
