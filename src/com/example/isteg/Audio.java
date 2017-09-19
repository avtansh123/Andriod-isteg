package com.example.isteg;



import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class Audio extends ActionBarActivity {
	TextView textTargetUri;
    Uri targetUri;
    RadioGroup radioGroup;
    EditText editText;
    EditText editText2;
    File file;
    Button buttonGo;
    byte[] music;
    byte[] imagebyte;
    InputStream is = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio);
        
        Button buttonLoadAudio = (Button)findViewById(R.id.button1);
        textTargetUri = (TextView)findViewById(R.id.textView3);
        buttonGo = (Button)findViewById(R.id.button3);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup1);
        editText = (EditText)findViewById(R.id.editText1);
        editText2 = (EditText)findViewById(R.id.editText2);

        
        buttonLoadAudio.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View arg0) 
            {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }});
    }

    public static byte[] encrypt(byte[] demotext, byte[] demoimage)
    {
        int i, k;
        int j = 0;
        byte temp1, temps = 0;
        for (k = 0; k < 4; k++)
        {
            for (i = 6; i >= 0; i = i - 2)                      //calculate encrypted value
            {
                temps = (byte) (demotext[k] >> i);
                temps = (byte) (temps & 0x03);
                temp1 = (byte) (demoimage[j] & 0xfc);
                demoimage[j] = (byte) (temp1 | temps);
                j++;
            }
        }
        return demoimage;

    }

    public static byte[] decrypt(byte[] demoimage) {
        int i, j, k;
        i = j = 0;
        byte temp2 = 0;
        byte temp5[] = new byte[20];
        for (k = 0; k < 4; k++) 
        {
            for (i = 6; i >= 0; i = i - 2) 
            {
                temp2 = (byte) (demoimage[j] & 0x03);
                temp2 = (byte) (temp2 << i);
                temp5[k] = (byte) (temp5[k] | temp2);
                j++;
            }


        }
        return temp5;
    }

   
   
      public void onActivityResult(int requestCode, int resultCode, Intent data) 
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK && null != data) 
        {
          try {
              Uri selectedAudio = data.getData();
              
              Uri selectedImage = data.getData();
              textTargetUri.setText(data.getData().toString());


              String[] filePathColumn = { MediaStore.Audio.Media.DATA };

              Cursor cursor = getContentResolver().query(selectedImage,
                      filePathColumn, null, null, null);
              cursor.moveToFirst();

              int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
              String picturePath = cursor.getString(columnIndex);
              cursor.close();

              //Toast.makeText(this, "File Name & PATH are:" + file_name + "\n" + file_path, Toast.LENGTH_LONG).show();
              file = new File(picturePath);
              

              

          }
          catch(Exception e)
          {
                Toast.makeText(getApplicationContext(), "Exc "+e.getMessage(), Toast.LENGTH_LONG).show();

          }
        }
  
        buttonGo.setOnClickListener(new Button.OnClickListener()
        {
            @Override
            public void onClick(View arg0) 
            {
                // TODO Auto-generated method stub
                if (radioGroup.getCheckedRadioButtonId() == (R.id.radio0))
                {
                    Toast.makeText(getApplicationContext(), "Lets Encode", Toast.LENGTH_LONG).show();
                    String text = editText.getText().toString();
                    String pass = editText2.getText().toString();
                	int key=Integer.parseInt(pass);
                    try {
                    	  is = new FileInputStream(file);
                    	  File f = new File("/storage/emulated/0/Music/m.wav");
                          

                          if(f.exists())
                              f.delete();
                       f.createNewFile();
                              Toast.makeText(getApplicationContext(), "Encode created", Toast.LENGTH_SHORT).show();
                          
                          FileOutputStream outs=new FileOutputStream (f,true);
                          

                          MediaScannerConnection.scanFile(getApplicationContext(), new String[] { "/storage/emulated/0/Music/m.wav"}, null, new MediaScannerConnection.OnScanCompletedListener() {

                              public void onScanCompleted(String path, Uri uri) {
                                  // TODO Auto-generated method stub

                              }
                          });
                          for(int c=0;c<key;c++)
                          {
                           int ch=is.read();
                           outs.write(ch);
                          }

                          BufferedInputStream bis = new BufferedInputStream(is);
                          DataInputStream dis = new DataInputStream(bis);
                          int musicLength = (int) (file.length());
                          music = new byte[musicLength];
                          Toast.makeText(getApplicationContext(), "Length" + musicLength, Toast.LENGTH_SHORT).show();
                          int i = 0;
                   
                              while (dis.available() > 0) {
                                 // music[musicLength - 1 - i] = dis.readByte();
                                  music[i] = dis.readByte();
                                  i++;
                              }

                        int textLength = text.trim().length();
                        imagebyte = music;//bytes array
                        byte[] auxtext;
                        byte[] auximg;
                        byte[] auxbyte;

                        if (textLength % 4 == 1)
                            text = text + " " + " " + " ";
                        else if (textLength % 4 == 2)
                            text = text + " " + " ";
                        else if (textLength % 4 == 3)
                            text = text + " ";
                        byte[] textbyte = text.getBytes();
                        int passes = ((textbyte.length) / 4);
                        //Toast.makeText(getApplicationContext(), "value at " + ":" + textbyte[0] + textbyte[1] + textbyte[2] + textbyte[3] + textbyte[4] + textbyte[5] + textbyte[6] + textbyte[7] + textbyte[8], Toast.LENGTH_SHORT).show();

                        for (int z = 0; z < passes; z++) 
                        {
                            auxtext = new byte[4];
                            auximg = new byte[16];
                            auxbyte = new byte[16];
                            for (int i1 = 0; i1 < 4; i1++) 
                            {
                                auxtext[i1] = textbyte[(z * 4) + i1];
                            }
                            for (int i1 = 0; i1 < 16; i1++) 
                            {
                                auximg[i1] = imagebyte[(z * 16) + i1 + 600];
                            }
                            auxbyte = encrypt(auxtext, auximg);
                            for (int i1 = 0; i1 < 16; i1++) 
                            {
                                imagebyte[(z * 16) + i1 + 600] = auxbyte[i1];
                            }
                        }
                       
                       
                        outs.write(imagebyte);
                        outs.flush();
                        outs.close();
                        

                    } 
                    catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "value err " + "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
//============================================================================================================================
                }
                    else if (radioGroup.getCheckedRadioButtonId() == (R.id.radio1)) {
                    Toast.makeText(getApplicationContext(), "Lets Decode", Toast.LENGTH_LONG).show();
                    byte[] auxtext = new byte[1500];
                    byte[] auxbyte;
                    byte[] auximg;
                    String pass = editText2.getText().toString();
                	int key=Integer.parseInt(pass);
                    
                    try {
                  	  is = new FileInputStream(file);
                  	 for(int i=0;i<key;i++)
                     {
                      is.read();
                     }
                      BufferedInputStream bis = new BufferedInputStream(is);
                      DataInputStream dis = new DataInputStream(bis);
                      int musicLength = (int) (file.length());
                      music = new byte[musicLength];
                      Toast.makeText(getApplicationContext(), "Length" + musicLength, Toast.LENGTH_SHORT).show();
                      int i = 0;
               
                          while (dis.available() > 0) {
                             // music[musicLength - 1 - i] = dis.readByte();
                              music[i] = dis.readByte();
                              i++;
                          }

                        for (int z = 0; z < (1500 / 16) - 1; z++) {
                            auxbyte = new byte[16];
                            auximg = new byte[4];
                            for (int i1 = 0; i1 < 16; i1++) {
                                auxbyte[i1] = music[(z * 16) + i1 + 600];
                            }
                            auximg = decrypt(auxbyte);
                            for (int i1 = 0; i1 < 4; i1++) {
                                auxtext[z * 4 + i1] = auximg[i1];
                            }
                        }
                        //Toast.makeText(getApplicationContext(), "making byte " + auxtext[0] + auxtext[1] + auxtext[2] + auxtext[3] + auxtext[4] + auxtext[5] + auxtext[6] + auxtext[7] + auxtext[8], Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getApplicationContext(), "generating", Toast.LENGTH_SHORT).show();
                        String s = new String(auxtext);
                        Toast.makeText(getApplicationContext(), "Decoded Text:" + s.trim(), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Exception" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

    }
}