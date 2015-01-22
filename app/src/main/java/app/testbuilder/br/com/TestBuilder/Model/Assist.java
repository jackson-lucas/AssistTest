package app.testbuilder.br.com.TestBuilder.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by jcaf on 27/12/2014.
 */


public class Assist implements Parcelable {
    // Parceable é necessário para passar Objetos da Classe entre Activities

    // Labels table name
    public static final String TABLE="assist";

    // Labels Table Columns names
    public static final String KEY_ID ="id";
    public static final String KEY_TESTE ="teste_id";
    public static final String KEY_P1 ="p1";
    public static final String KEY_P2 ="p2";
    public static final String KEY_P3 ="p3";
    public static final String KEY_P4 ="p4";
    public static final String KEY_P5 ="p5";
    public static final String KEY_P6 ="p6";
    public static final String KEY_P7 ="p7";
    public static final String KEY_P8 ="p8";

    int id;
    int teste_id;
    String p1;
    String p2;
    String p3;
    String p4;
    String p5;
    String p6;
    String p7;
    String p8;
    int[] resultado = new int[10];

    public Assist() {
    }

    public Assist(int id) {

        this.id = id;
        this.resultado = new int[10];
    }

    public Assist(int id, int teste_id, String p1, String p2, String p3, String p4, String p5, String p6, String p7, String p8) {
        this.id = id;
        this.teste_id = teste_id;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        this.p5 = p5;
        this.p6 = p6;
        this.p7 = p7;
        this.p8 = p8;
        this.resultado = new int[10];
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeste_id() {
        return teste_id;
    }

    public void setTeste_id(int teste_id) {
        this.teste_id = teste_id;
    }

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }

    public String getP2() {
        return p2;
    }

    public void setP2(String p2) {
        this.p2 = p2;
    }

    public String getP3() {
        return p3;
    }

    public void setP3(String p3) {
        this.p3 = p3;
    }

    public String getP4() {
        return p4;
    }

    public void setP4(String p4) {
        this.p4 = p4;
    }

    public String getP5() {
        return p5;
    }

    public void setP5(String p5) {
        this.p5 = p5;
    }

    public String getP6() {
        return p6;
    }

    public void setP6(String p6) {
        this.p6 = p6;
    }

    public String getP7() {
        return p7;
    }

    public void setP7(String p7) {
        this.p7 = p7;
    }

    public String getP8() {
        return p8;
    }

    public void setP8(String p8) {
        this.p8 = p8;
    }

    public int[] getResultado() {
        somarRespostas();
        return resultado;
    }

    public void somarRespostas() {
        String[] respostas = {p2, p3, p4, p5, p6, p7};
        int index;

        // Limpa resultado para evitar bugs com o caso que tenha sido executado anteriormente
        for(index = 0; index < resultado.length; index++) {
            resultado[index] = 0;
        }

        //TODO Não esquecer do tabaco na questão 5(ver documento)
        for(String resposta : respostas) {
            for(index = 0; index < resposta.length(); index++) {
                resultado[index] += ((int) resposta.charAt(index)-'0');
            }
        }
    }

    @Override
    public String toString() {
        return "Assist{" +
                "id=" + id +
                ", teste_id=" + teste_id +
                ", p1='" + p1 + '\'' +
                ", p2='" + p2 + '\'' +
                ", p3='" + p3 + '\'' +
                ", p4='" + p4 + '\'' +
                ", p5='" + p5 + '\'' +
                ", p6='" + p6 + '\'' +
                ", p7='" + p7 + '\'' +
                ", p8='" + p8 + '\'' +
                ", resultado='" + resultado + '\'' +
                '}';
    }





    /**
     * Constructor to use when re-constructing object from a parcel
     *
     * @param in a parcel from which to read this object
     */
    public Assist(Parcel in) {
        readFromParcel(in);
    }

    // Para satisfazer implementação de Parceable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        // A ordem que for escrita aqui é a ordem que deve ser lida em readFromParcel()
        parcel.writeInt(id);
        parcel.writeInt(teste_id);
        parcel.writeString(p1);
        parcel.writeString(p2);
        parcel.writeString(p3);
        parcel.writeString(p4);
        parcel.writeString(p5);
        parcel.writeString(p6);
        parcel.writeString(p7);
        parcel.writeString(p8);
    }

    private void readFromParcel(Parcel input) {
        id = input.readInt();
        teste_id = input.readInt();
        p1 = input.readString();
        p2 = input.readString();
        p3 = input.readString();
        p4 = input.readString();
        p5 = input.readString();
        p6 = input.readString();
        p7 = input.readString();
        p8 = input.readString();
    }

    /**
     * This field is needed for Android to be able to create new objects, individually or as arrays.
     * This also means that you can use use the default constructor to create the object and
     * use another method to hydrate it as necessary.
     */
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Assist createFromParcel(Parcel in) {
            return new Assist(in);
        }
        public Assist[] newArray(int size) {
            return new Assist[size];
        }
    };
}
