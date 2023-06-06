package com.proje1.isimehiroyunu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class SureliOyunActivity extends AppCompatActivity {

    private EditText editTxtTahmin;
    private TextView txtIlBilgi, txtIl,txtToplamPuan,txtSure;
    private String[] iller = {"Adana", "Adıyaman", "Afyon", "Ağrı", "Amasya", "Ankara", "Antalya", "Artvin", "Aydın", "Balıkesir", "Bilecik", "Bingöl",
            "Bitlis", "Bolu", "Burdur", "Bursa", "Çanakkale", "Çankırı", "Çorum", "Denizli",
            "Diyarbakır", "Edirne", "Elazığ", "Erzincan", "Erzurum", "Eskişehir", "Gaziantep", "Giresun", "Gümüşhane", "Hakkari", "Hatay", "Isparta",
            "Mersin", "İstanbul", "İzmir", "Kars", "Kastamonu", "Kayseri", "Kırklareli", "Kırşehir",
            "Kocaeli", "Konya", "Kütahya", "Malatya", "Manisa", "Kahramanmaraş", "Mardin", "Muğla", "Muş", "Nevşehir", "Niğde", "Ordu", "Rize", "Sakarya",
            "Samsun", "Siirt", "Sinop", "Sivas", "Tekirdağ", "Tokat", "Trabzon", "Tunceli", "Şanlıurfa", "Uşak", "Van",
            "Yozgat", "Zonguldak", "Aksaray", "Bayburt", "Karaman", "Kırıkkale", "Batman", "Şırnak", "Bartın", "Ardahan", "Iğdır", "Yalova", "Karabük",
            "Kilis", "Osmaniye", "Düzce"};

    private Random rndIl, rndHarf;
    private int rndIlNumber, rndNumberHarf,baslangicHarfSayisi,toplamSure=180000;
    private String gelenIl, ilBoyutu, editTxtGelenTahmin;
    private ArrayList<Character> ilHarfleri;
    private float maximumPuan=100.0f, azaltilacakPuan,toplamPuan=0,bolumToplamPuan = 0;
    private Button btnHarfAl,btnTahminEt,btnTekrarOyna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sureli_oyun);
        txtIl = (TextView) findViewById(R.id.txtViewIlS);
        txtIlBilgi = (TextView) findViewById(R.id.txtViewIlBilgiS);
        editTxtTahmin = (EditText) findViewById(R.id.editTxtTahminS);
        txtToplamPuan=(TextView) findViewById(R.id.txtViewToplamPuanS);
        txtSure=(TextView)findViewById(R.id.txtViewSureS);
        btnHarfAl=(Button)findViewById(R.id.btnHarfAlS);
        btnTahminEt=(Button)findViewById(R.id.btnTahminEtS);
        btnTekrarOyna=(Button)findViewById(R.id.btnTekrarOynaS);


        new CountDownTimer(toplamSure, 1000) {
            @Override
            public void onTick(long l) {
            txtSure.setText((l / 60000) + ":" + ((l % 60000) / 1000 ));
            }

            @Override
            public void onFinish() {
            txtSure.setText("0:00");
            editTxtTahmin.setEnabled(false);
            btnHarfAl.setEnabled(false);
            btnTahminEt.setEnabled(false);
            btnTekrarOyna.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "Oyun Bitti\nToplam Puanınız: " + bolumToplamPuan + "\nTekrar Oynamak İçin Butona Basınız.", Toast.LENGTH_LONG).show();
            }
        }.start();

        rndHarf = new Random();
        randomDegerleriBelirle();

    }

    public void btnHarfAlS(View v){
        if (ilHarfleri.size() > 0) {
            randomHarfAl();
            toplamPuan -= azaltilacakPuan;
            Toast.makeText(getApplicationContext(), "Kalan puan = " + toplamPuan, Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(getApplicationContext(),"Alınabilecek Harf kalmadı.", Toast.LENGTH_SHORT).show();
    }

    public void btnTekrarOynaS(View v){
        Intent tekrarOyna = new Intent(this,SureliOyunActivity.class);
        finish();
        startActivity(tekrarOyna);
    }

    public void btnTahminEtS(View v){
        editTxtGelenTahmin = editTxtTahmin.getText().toString();

        if (!TextUtils.isEmpty(editTxtGelenTahmin)) {
            if (editTxtGelenTahmin.equals(gelenIl)){
                bolumToplamPuan += toplamPuan;
                Toast.makeText(getApplicationContext(),"Tebrikler doğru tahminde bulundunuz.", Toast.LENGTH_SHORT).show();
                txtToplamPuan.setText("Toplam Bölüm Puanı: " + bolumToplamPuan);

                editTxtTahmin.setText("");
                randomDegerleriBelirle();

            }
            else
                Toast.makeText(getApplicationContext(), "Yanlış Tahminde Bulundunuz.", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getApplicationContext(),"Tahmin değeri boş olamaz.", Toast.LENGTH_SHORT).show();
    }

    private void randomDegerleriBelirle() {
        ilBoyutu = "";
        rndIl = new Random();
        rndIlNumber = rndIl.nextInt(iller.length);
        gelenIl = iller[rndIlNumber];
        System.out.println(rndIlNumber + "=" + gelenIl);
        txtIlBilgi.setText(gelenIl.length() + " Harfli İlimiz:");

        if(gelenIl.length() >= 5 && gelenIl.length() <= 7)
            baslangicHarfSayisi=1;
        else if(gelenIl.length() >= 8 && gelenIl.length() < 10)
            baslangicHarfSayisi=2;
        else if (gelenIl.length() >= 10)
            baslangicHarfSayisi=3;
        else
            baslangicHarfSayisi=0;

        for (int i = 0; i < gelenIl.length(); i++) {
            if (i < gelenIl.length() - 1)
                ilBoyutu += "_ ";
            else
                ilBoyutu += "_";
        }
        txtIl.setText(ilBoyutu);
        ilHarfleri = new ArrayList<>();

        for (char c : gelenIl.toCharArray())
            ilHarfleri.add(c);

        for(int c =0; c<baslangicHarfSayisi; c++)
            randomHarfAl();

        azaltilacakPuan=maximumPuan / ilHarfleri.size();
        toplamPuan=maximumPuan;

    }
    private void randomHarfAl(){
        rndNumberHarf = rndHarf.nextInt(ilHarfleri.size());
        String[] txtHarfler = txtIl.getText().toString().split(" ");
        char[] gelenIlHarfler = gelenIl.toCharArray();

        for (int i = 0; i < gelenIl.length(); i++) {
            if (txtHarfler[i].equals("_") && gelenIlHarfler[i] == ilHarfleri.get(rndNumberHarf)) {
                txtHarfler[i] = String.valueOf(ilHarfleri.get(rndNumberHarf));
                ilBoyutu = "";

                for (int j = 0; j < gelenIl.length(); j++) {
                    if (j == i)
                        ilBoyutu += txtHarfler[j] + " ";
                    else if (j < gelenIl.length() - 1)
                        ilBoyutu += txtHarfler[j] + " ";
                    else
                        ilBoyutu += txtHarfler[j];
                }
                break;
            }
        }
        txtIl.setText(ilBoyutu);
        ilHarfleri.remove(rndNumberHarf);
    }

}