package com.example.eva3_2_handlers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView principalText;
    Thread thText;
    //HANDLER manejador encargado de manejar la pila de mensajes
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            /*Aqui se puede interactuar con la interfaz grafica
             *Este es un metodo del hilo principal
             * Cosas sencillas
              */
            String finalMsg = (String) msg.obj;
            principalText.append(finalMsg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        principalText = findViewById(R.id.txtPrincipal);

        thText = new Thread(){
            @Override
            public void run() {
                super.run();
                int i = 0;
                while(true){
                    try {
                        Thread.sleep(500);
                        String m = "i: " + i +"\n"; //Se crea el objeto a enviar
                        Message msn = handler.obtainMessage(1,m); //Se crea el mensaje con el objeto a enviar
                        handler.sendMessage(msn);//Se envia el mensaje
                        Log.wtf("Thread",m);
                        i++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        };
        thText.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        thText.interrupt();
    }
}
