package app.testbuilder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Date;
import java.sql.Timestamp;

public class Main extends Activity {

    Button btnIniciar;

    private static Date getDateTime() {
        return new Timestamp(new Date().getTime());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // START Retrieve data from another activity
        Intent intent = getIntent();

        if(intent != null) {
            // Não funcionando, o porquê eu não sei
            Boolean isClean = intent.getBooleanExtra("INTEGRO", false);
            Log.d("isClean", isClean+"");
            if(isClean) {
                Toast.makeText(getApplicationContext(), "Parabéns por não utilizar drogas!", Toast.LENGTH_LONG);
            }
        }

        btnIniciar = (Button) findViewById(R.id.btnTeste);

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main.this, CadastroUI.class);
                startActivity(i);
            }
        });
    }
}
