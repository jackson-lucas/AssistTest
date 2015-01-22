package app.testbuilder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Date;
import java.sql.Timestamp;

public class Main extends ActionBarActivity {

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

        btnIniciar = (Button) findViewById(R.id.btnTeste);

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main.this, CadastroUI.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
        Handle action bar item clicks here, The action bar will automatically handle clicks on the
        Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
         */
        int id = item.getItemId();

        if (id == R.id.action_send_data) {


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
