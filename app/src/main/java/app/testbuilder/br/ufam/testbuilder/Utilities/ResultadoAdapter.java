package app.testbuilder.br.ufam.testbuilder.Utilities;

/**
 * Created by jacksonlima on 1/23/15.
 */
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import app.testbuilder.R;
import app.testbuilder.br.com.TestBuilder.Model.Substancia;

public class ResultadoAdapter extends BaseAdapter  {


    private List<Substancia> listSubstancias;

    //Classe utilizada para instanciar os objetos do XML
    private LayoutInflater inflater;

    public ResultadoAdapter(Context context, List<Substancia> listSubstancias) {
        this.listSubstancias = listSubstancias;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(final Substancia item) {
        this.listSubstancias.add(item);
        //Atualizar a lista caso seja adicionado algum item
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listSubstancias.size();
    }

    @Override
    public Object getItem(int position) {
        return listSubstancias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        //Pega o registro da lista e trasnfere para o objeto estadoVO
        Substancia substancia = listSubstancias.get(position);

        //Utiliza o XML estado_row para apresentar na tela
        convertView = inflater.inflate(R.layout.resultado_row, null);

        //Instância os objetos do XML
        ImageView bandeira = (ImageView)convertView.findViewById(R.id.bandeira);
        TextView tvEstado = (TextView)convertView.findViewById(R.id.tvEstado);
        TextView tvCapital = (TextView)convertView.findViewById(R.id.tvCapital);


        //bandeira.setImageResource(substancia.getBandeira());
        tvEstado.setText(substancia.getNome());
        tvCapital.setText(substancia.getResultado()+"");

        return convertView;
    }
}
