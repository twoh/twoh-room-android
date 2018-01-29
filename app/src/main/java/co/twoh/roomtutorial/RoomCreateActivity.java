package co.twoh.roomtutorial;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import co.twoh.roomtutorial.data.factory.AppDatabase;
import co.twoh.roomtutorial.model.Barang;

/**
 * Created by Herdi_WORK on 21.01.18.
 */

public class RoomCreateActivity extends AppCompatActivity {

    private AppDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "barangdb").build();

        final EditText etNamaBarang   = findViewById(R.id.et_namabarang);
        final EditText etMerkBarang   = findViewById(R.id.et_merkbarang);
        final EditText etHargaBarang  = findViewById(R.id.et_hargabarang);
        Button btSubmit         = findViewById(R.id.bt_submit);

        final Barang barang = (Barang) getIntent().getSerializableExtra("data");

        if(barang!=null){
            etNamaBarang.setText(barang.getNamaBarang());
            etMerkBarang.setText(barang.getMerkBarang());
            etHargaBarang.setText(barang.getHargaBarang());
            btSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    barang.setNamaBarang(etNamaBarang.getText().toString());
                    barang.setMerkBarang(etMerkBarang.getText().toString());
                    barang.setHargaBarang(etHargaBarang.getText().toString());

                    updateBarang(barang);
                }
            });
        }else{
            btSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Barang b = new Barang();
                    b.setHargaBarang(etHargaBarang.getText().toString());
                    b.setMerkBarang(etMerkBarang.getText().toString());
                    b.setNamaBarang(etNamaBarang.getText().toString());
                    insertData(b);
                }
            });
        }
    }

    private void updateBarang(final Barang barang){
        new AsyncTask<Void, Void, Long>(){
            @Override
            protected Long doInBackground(Void... voids) {
                long status = db.barangDAO().updateBarang(barang);
                return status;
            }

            @Override
            protected void onPostExecute(Long status) {
                Toast.makeText(RoomCreateActivity.this, "status row "+status, Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

    private void insertData(final Barang barang){

        new AsyncTask<Void, Void, Long>(){
            @Override
            protected Long doInBackground(Void... voids) {
                long status = db.barangDAO().insertBarang(barang);
                return status;
            }

            @Override
            protected void onPostExecute(Long status) {
                Toast.makeText(RoomCreateActivity.this, "status row "+status, Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

    public static Intent getActIntent(Activity activity) {
        // kode untuk pengambilan Intent
        return new Intent(activity, RoomCreateActivity.class);
    }
}
