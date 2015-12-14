package br.com.igrejadapaz.fortaleza.celulasdapaz.Bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.igrejadapaz.fortaleza.celulasdapaz.R;

/**
 * Created by Samuel Santiago on 05/12/2015.
 */
public class CelulaBean implements Parcelable {

    private int id;
    private String nome;
    private String endereco;
    private String liderNome;
    private String telefoneInformacao;
    private String diaHora;
    private Double latitude;
    private Double longitude;

    private int semanaID; // numero correspondente ao dia da semana
    private int tipoID; // numero correspondente ao tiopo ver setTipo()
    private int redeID;  // numero correspondente a cor da rede ver setRede()

    private LatLng posicao; // auto gerado
    private MarkerOptions markerOptions; // auto gerado
    private String tipo; // auto gerado
    private String rede; // auto gerado


    public CelulaBean(Parcel in) {
        readFromParcelable(in);
    }

    public CelulaBean() {
        // TODO
    }

    public CelulaBean(String nome, String endereco, String liderNome, String telefoneInformacao, String diaHora, Double latitude, Double longitude, int semanaID, int tipoID, int redeID) {
        setNome(nome);
        this.endereco = endereco;
        this.liderNome = liderNome;
        this.telefoneInformacao = telefoneInformacao;
        this.diaHora = diaHora;
        this.latitude = latitude;
        this.longitude = longitude;
        this.semanaID = semanaID;
        this.tipoID = tipoID;
        this.redeID = redeID;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    private void readFromParcelable(Parcel in){
        id = in.readInt();
        nome = in.readString();
        endereco = in.readString();
        liderNome = in.readString();
        telefoneInformacao = in.readString();
        diaHora = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        tipoID = in.readInt();
        redeID = in.readInt();
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nome);
        dest.writeString(endereco);
        dest.writeString(liderNome);
        dest.writeString(telefoneInformacao);
        dest.writeString(diaHora);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeInt(tipoID);
        dest.writeInt(redeID);
    }

    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public CelulaBean createFromParcel(Parcel in) {
            return new CelulaBean(in);
        }

        public CelulaBean[] newArray(int size) {
            return new CelulaBean[size];
        }
    };

    public String toShared() {
        String ao;
        if (semanaID == 7){
            ao =  "aos ";
        }else {
            ao = "as ";
        }
        return "Que tal fazer novas amizades, dar boas risadas e se divertir\nenquanto conhece um pouco mais de Deus?\nGostaria muito de ter você na nossa célula!\nVamos?" +
                "\nA " + getNome() + " que acontece " + ao + getDiaHora() + " na " + getEndereco() +
                "\n \nhttps://www.google.com/maps/place//@" + getLatitude() + "," + getLongitude() + ",18z/";
//                "\n\n https://www.google.com/maps?q=" + getLatitude() + "," + getLongitude() + "&z=18";
    }

    public String toString(){
        return nome + " - " + diaHora;
    }

    public static Parcelable.Creator getCREATOR() {
        return CREATOR;
    }

    public LatLng getPosicao() {
        setPosicao();
        return posicao;
    }

    public void setPosicao() {
        LatLng posicao = new LatLng(this.latitude, this.longitude);
        this.posicao = posicao;
    }

    public MarkerOptions getMarkerOptions() {
        setMarkerOptions();
        return markerOptions;
    }

    public void setMarkerOptions() {
        MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)).position(getPosicao()).title(getNome()).snippet(getDiaHora());
        this.markerOptions = markerOptions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLiderNome() {
        return liderNome;
    }

    public void setLiderNome(String liderNome) {
        this.liderNome = liderNome;
    }

    public String getTelefoneInformacao() {
        return telefoneInformacao;
    }

    public void setTelefoneInformacao(String telefoneInformacao) {
        this.telefoneInformacao = telefoneInformacao;
    }

    public String getDiaHora() {
        return diaHora;
    }

    public void setDiaHora(String diaHora) {
        this.diaHora = diaHora;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public int getSemanaID() {
        return semanaID;
    }

    public void setSemanaID(int semanaID) {
        this.semanaID = semanaID;
    }

    public int getTipoID() {
        return tipoID;
    }

    public void setTipoID(int tipoID) {
        this.tipoID = tipoID;
    }

    public int getRedeID() {
        return redeID;
    }

    public void setRedeID(int redeID) {
        this.redeID = redeID;
    }

    public String getTipo() {
        setTipo();
        return tipo;
    }

    public void setTipo() {
        switch (this.tipoID) {
            case 1:
                this.tipo = "Crianças";
                break;
            case 2:
                this.tipo = "Adolecantes";
                break;
            case 3:
                this.tipo = "Jovens";
                break;
            case 4:
                this.tipo = "Adultos";
                break;
            default:
                this.tipo = "Tipo não definido";
                break;
        }
    }


    public String getRede() {
        setRede();
        return rede;
    }

    public void setRede() {
        switch (this.getRedeID()) {
            case 1:
                this.rede = "Rede Larranja";
                break;
            case 2:
                this.rede = "Rede Vinho Novo";
                break;
            case 3:
                this.rede = "Rede Vermenlha";
                break;
            case 4:
                this.rede = "Rede Verde";
                break;
            case 5:
                this.rede = "Rede Amarelha";
                break;
            default:
                this.rede = "Definir nome da Rede";
                break;
        }
    }

}
