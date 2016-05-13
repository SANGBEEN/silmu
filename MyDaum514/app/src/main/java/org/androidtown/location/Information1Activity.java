package org.androidtown.location;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Information1Activity extends AppCompatActivity {

    Button bn;
    TextView result;


    //접속할주소
    private final String urlPath = "http://192.168.0.2/putdata.php";
    private final String TAG = "PHPTEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       /* bn = (Button)findViewById(R.id.btn);
        result = (TextView)findViewById(R.id._result);

        bn.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new HttpTask().execute();
            }

        });*/

        bn = (Button) findViewById(R.id.btn);
        result = (TextView) findViewById(R.id._result);
      //  rvalue = Math.random() * 100;
      //  yvalue = (int)rvalue;


        bn.setOnClickListener(new Button.OnClickListener() {

            @Override

            public void onClick(View v) {
                result.setText("90");

                Toast.makeText(getApplicationContext(), "꽉차있습니다",
                        Toast.LENGTH_LONG).show();
                Vibrator tVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                tVibrator.vibrate(100);
                //result.setText(yvalue);
                /*if(yvalue >=80) {
                    Vibrator tVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    tVibrator.vibrate(1);
                    Toast.makeText(getApplicationContext(), "꽉차있습니다",
                            Toast.LENGTH_LONG).show();
                }*/
            }
        });






/*
    class HttpTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            // TODO Auto-generated method stub
            try{
                HttpPost request = new HttpPost(urlPath);
                //전달할 인자들
                Vector<NameValuePair> nameValue = new Vector<NameValuePair>();
                nameValue.add(new BasicNameValuePair("name", "홍길동"));
                nameValue.add(new BasicNameValuePair("age", "24"));
                nameValue.add(new BasicNameValuePair("sex", "male"));

                //웹 접속 - utf-8 방식으로
                HttpEntity enty = new UrlEncodedFormEntity(nameValue, HTTP.UTF_8);
                request.setEntity(enty);

                HttpClient client = new DefaultHttpClient();
                HttpResponse res = client.execute(request);
                //웹 서버에서 값받기
                HttpEntity entityResponse = res.getEntity();
                InputStream im = entityResponse.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(im, HTTP.UTF_8));

                String total = "";
                String tmp = "";
                //버퍼에있는거 전부 더해주기
                //readLine -> 파일내용을 줄 단위로 읽기
                while((tmp = reader.readLine())!= null)
                {
                    if(tmp != null)
                    {
                        total += tmp;
                    }
                }
                im.close();
                //결과창뿌려주기 - ui 변경시 에러
                //result.setText(total);
                return total;
            }catch(UnsupportedEncodingException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }
            //오류시 null 반환
            return null;
        }
        //asyonTask 3번째 인자와 일치 매개변수값 -> doInBackground 리턴값이 전달됨
        //AsynoTask 는 preExcute - doInBackground - postExecute 순으로 자동으로 실행됩니다.
        //ui는 여기서 변경
        protected void onPostExecute(String value){
            super.onPostExecute(value);
            result.setText(value);
        }
    }
*/
    }
}
