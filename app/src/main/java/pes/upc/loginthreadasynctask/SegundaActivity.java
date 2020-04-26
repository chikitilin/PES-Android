package pes.upc.loginthreadasynctask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SegundaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);

        TextView usernameText = (TextView) findViewById(R.id.Nombreusuario2);
        usernameText.setText(Singleton.getInstancia().usuarioSingleton);
        TextView  passwordText= (TextView) findViewById(R.id.password2);
        passwordText.setText((Singleton.getInstancia().passwordSingleton));
    }

}
