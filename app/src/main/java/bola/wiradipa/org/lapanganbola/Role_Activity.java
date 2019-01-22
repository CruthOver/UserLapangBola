package bola.wiradipa.org.lapanganbola;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Role_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_);

        TextView personal = (TextView) findViewById(R.id.personal_regis);
        TextView klub = (TextView) findViewById(R.id.klub_regis);
        TextView pemilik = (TextView) findViewById(R.id.pemilik_regis);

        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Role_Activity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        klub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Role_Activity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        pemilik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Role_Activity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
