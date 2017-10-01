package si.project.speech;


import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;



public class MainActivity extends ActionBarActivity implements OnInitListener {

  private Button bStart;
  private Button bLlamar;
  private Button bCamara;
  private Button bAlarma;
  private Button bSms;
  private Button bBuscar;
  private Button bNavegar;
  
  private Llamar llamar;          // code = 1
  private Camara camara;          // code = 2
  private Sms sms;                // code = 3
  private Alarma alarma;          // code = 4
  private Navegar navegar;        // code = 5
  private Buscar buscar;          // code = 6
  
  private GeneradorTexto tts;
  private Speech speech;

  @Override 
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    speech = new Speech(this);
    tts = new GeneradorTexto(this);
    tts.onCreate(savedInstanceState);
    cargarLayout();
  }
  
  public void cargarLayout() {
    setContentView(R.layout.fragment_main);
    bStart = (Button)findViewById(R.id.button1);
    bLlamar = (Button)findViewById(R.id.bLlamar);
    bCamara = (Button)findViewById(R.id.bCamara);
    bAlarma = (Button)findViewById(R.id.bAlarma);
    bSms = (Button)findViewById(R.id.bMensaje);
    bBuscar= (Button)findViewById(R.id.bBuscar);
    bNavegar = (Button)findViewById(R.id.bNavegar);
    
    bStart.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        tts.playText("¿Qué desea hacer?");
        while(tts.isSpeaking());
        //Lanzamos el reconoimiento de voz
        speech.setMessage("Por jemplo: sms, cámara, llamar a...");
        speech.startVoiceRecognitionActivity();
      }
    });
    
    bLlamar.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        speech.setCode(1);
        speech.setSubCode(2);
        onActivityResult(1, 0, null);
      }
    });
    
    bCamara.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        speech.setCode(2);
        onActivityResult(1, 0, null);
      }
    });
    
    bAlarma.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        speech.setCode(4);
        speech.setSubCode(1);
        onActivityResult(1, 0, null);
      }
    });
    
    bSms.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        speech.setCode(3);
        speech.setSubCode(2);
        onActivityResult(1, 0, null);
      }
    });
    
    bBuscar.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        speech.setCode(6);
        speech.setSubCode(1);
        tts.playText("¿Qué desea buscar?");
        while(tts.isSpeaking());
        speech.startVoiceRecognitionActivity();
      }
    });
    
    bNavegar.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        speech.setCode(5);
        speech.setSubCode(2);
        onActivityResult(1, 0, null);
      }
    });
  }
  
  //Inicia TTS
  public void onInit(int status) {
    tts.onInit(status);
  }
  
  @Override
  //Recogemos los resultados del reconocimiento de voz
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
   speech.onActivityResult(requestCode, resultCode, data);

   String [] palabras = speech.getResult();

   switch(speech.getCode()) {
     case 1:   //Llamada
       switch(speech.getSubCode()) {
         case 1:  
           String nombre = speech.parseName();
           if (llamar == null)
             llamar = new Llamar(this);
           String numero = llamar.getNumber(nombre);
           if(!numero.equals("null")) {
             tts.playText("Llamando a " + nombre);
             speech.resetCodes();
             llamar.realizarLlamada(numero);
           }
           else {
             tts.playText("No se han encontrado coincidencias");
             speech.resetCodes();
           }
           break;
           
         case 2:
           tts.playText("¿A quién desea llamar?");
           while(tts.isSpeaking());
           speech.setMessage("Diga el nombre de la persona. Ejemplo: Carlos");
           speech.startVoiceRecognitionActivity();
           speech.resetSubcode();
           break;
           
         default:
           String nombre1 = speech.getOtherResult();
           Log.e("nombre", nombre1);
           if (llamar == null)
             llamar = new Llamar(this);
           String numero1 = llamar.getNumber(nombre1);
           if(!numero1.equals("null")) {
             tts.playText("Llamando a " + nombre1);
             speech.resetCodes();
             llamar.realizarLlamada(numero1);
           }
           else {
             tts.playText("No se han encontrado coincidencias");
             speech.resetCodes();
           }
           break;
       }
       break;
       
     case 2: // cámara
       if (camara == null)
         camara = new Camara(this);
       camara.hacerFoto();
       speech.resetCodes();
       break;
       
     case 3: //sms
       switch(speech.getSubCode()) {
         case 1:  //Leer sms
           if (sms == null)
             sms = new Sms(this);
           if (sms.isEmpty())
             tts.playText("No hay sms en su historial");
           else {
             String[] sms1 = sms.getSms(sms.getIndex());
             tts.playText("Mensaje de " + sms1[0] + ", " + sms1[1]);
             while(tts.isSpeaking());
             tts.playText("¿Desea leer el siguiente mensaje?");
             while(tts.isSpeaking());
             speech.setMessage("Diga sí o no.");
             speech.startVoiceRecognitionActivity();
           }
           break;
           
         case 2:  //Preguntar a quien enviar el sms
           if (sms == null)
             sms = new Sms(this);
           tts.playText("¿A quién quiere enviar el mensaje?");
           while(tts.isSpeaking());
           speech.setSubCode(3);
           speech.setMessage("Diga el nombre de la persona. Ejemplo: Carlos");
           speech.startVoiceRecognitionActivity();
           break;
           
         case 3: // Redactar el contenido del mensaje
           sms.setName(speech.getOtherResult());
           if (!sms.validarContacto()) {
             sms.setName(null);
             tts.playText("No se han encontrado coincidencias");
             while(tts.isSpeaking());
             speech.resetCodes();
           }
           else {
             sms.activitySms();
             tts.playText("Redacte el mensaje que quiere enviar");
             speech.setMessage("Diga el contenido del mensaje");
             while(tts.isSpeaking());
             speech.setSubCode(4);
             speech.startVoiceRecognitionActivity();
           }
           break;
           
         case 4:  //Verificar mensaje
           sms.setMessage(speech.getOtherResult());
           tts.playText("Diga aceptar, cancelar o redacte de nuevo el mensaje");
           while(tts.isSpeaking());
           speech.setMessage("Diga aceptar, cancelar o redacte de nuevo");
           speech.startVoiceRecognitionActivity();
           break;
           
         case 5: // Enviar
           //sms.sendSms();
           cargarLayout();
           tts.playText("Mensaje enviado");
           speech.resetCodes();
           break;
           
         case 6: // Cancelar
           speech.resetCodes();
           cargarLayout();
           break;
           
         default:
           speech.resetCodes();
           break;
       }
       break;
     
     case 4:  //Alarma
       switch (speech.getSubCode()) {
         case 1:
           if (alarma == null)
             alarma = new Alarma(this);
           tts.playText("¿A qué hora quiere poner la alarma?");
           while(tts.isSpeaking());
           speech.setSubCode(2);
           speech.setMessage("Diga la hora. Ejemplo: a las 14 y 48");
           speech.startVoiceRecognitionActivity();
           break;
           
         case 2:
           alarma.parserHour(speech.getOtherResult());
           alarma.createAlarm();
           break;
           
         default:
           break;
       }
       break;
       
     case 5:   //Navegar
       switch (speech.getSubCode()) {
         case 1:
           if (navegar == null)
             navegar = new Navegar(this);
           navegar.loadGPS();
           navegar.showMap();
           speech.resetCodes();
           break;
           
         case 2:
           if (navegar == null)
             navegar = new Navegar(this);
           tts.playText("¿A dónde desea ir?");
           while(tts.isSpeaking());
           speech.setSubCode(3);
           speech.setMessage("Diga una ubicación. Ejemplo: Santa Cruz");
           speech.startVoiceRecognitionActivity();
           break;
           
         case 3:
           navegar.setDestiny(speech.getOtherResult());
           navegar.showMapDestiny();
           speech.resetCodes();
           break;
           
         default:
           break;
       }
       break;
       
     case 6:  // Buscar
       switch (speech.getSubCode()) {
         case 1:
           if (buscar == null)
             buscar = new Buscar(this);
           buscar.setQuery(speech.parseSearch());
           buscar.searchWeb();
           speech.resetCodes();
           break;
           
         default:
           break;
       }
       break;
       
     default:
       if (speech.isResultOK()) {
         tts.playText("No le he entendido, por favor inténtelo de nuevo");
         while(tts.isSpeaking());
         speech.startVoiceRecognitionActivity();
       }
       break;
   }
 }
}
