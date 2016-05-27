package org.androidtown.location;

import android.Manifest;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MapView.CurrentLocationEventListener, MapReverseGeoCoder.ReverseGeoCodingResultListener, MapView.POIItemEventListener, MapView.MapViewEventListener {
    TextView textView;
    MapView mapView;
    String textmessage;
    int i = 0, j = 0, k = 0, z = 0;
    MapPoint[] MARKER_POINT = new MapPoint[10];
    double[] capacity = new double[10];
    double[] la1 = new double[10];
    double[] lo1 = new double[10];
boolean full=false;
    MapPOIItem[] Marker = new MapPOIItem[10];
    String jSon;

    double la;
    double lo;
    double lat1;
    double log1;
    double lat2;
    double log2;
    double lat3;
    double log3;
    StringBuffer result1;
    StringBuffer result2;
    StringBuffer result3;



    //NOTIFICATION?ㅼ젙

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    private GoogleApiClient client;
    private final String urlPath = "http://172.20.10.2/putdata.php";
    private final String TAG = "PHPTEST";
    Bitmap bmImg;
    //phpDown[] task=new phpDown[10];
    phpDown task1;
    phpDown task2;
    phpDown task3;
    phpDown task4;
    phpDown task5;
    phpDown task6;
    phpDown task7;


    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }

        };


        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setRationaleMessage("we need permission for read contact and find your location")
                .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .check();


        task1 = new phpDown();
        task2 = new phpDown();
        task3 = new phpDown();

        task4 = new phpDown();
        task5 = new phpDown();
        task6 = new phpDown();
        task7 = new phpDown();

        task1.execute("http://172.20.10.2/putdata.php");
        //doJSONParser();
        task2.execute("http://172.20.10.2/putdata2.php");
        task3.execute("http://172.20.10.2/putdata3.php");

        task4.execute("http://172.20.10.2/putdata4.php");
        task5.execute("http://172.20.10.2/putdata5.php");
        task6.execute("http://172.20.10.2/putdata6.php");


        task7.execute("http://172.20.10.2/putdata7.php");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //留듬럭?앹꽦
        mapView = new MapView(this);
        mapView.setDaumMapApiKey("628faa3102922f3999d2a1f7352d4065");
        RelativeLayout container = (RelativeLayout) findViewById(R.id.map_view);
        container.addView(mapView);


        mapView.setCurrentLocationEventListener(this);  //?몃옒?밸え?쒕? ?꾪븳 ?대깽??由ъ뒪??
        mapView.setZoomLevel(2, true);                  //以뚮젅踰⑥꽕??

        mapView.setMapViewEventListener(this);
        mapView.setPOIItemEventListener(this);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();



    }

    public void doJSONParser() {
        try {

            JSONObject jObject = new JSONObject(jSon);

            JSONArray jarray = jObject.getJSONArray("capacity");
            capacity[i++] = jarray.getDouble(0);
            JSONArray jarray2 = jObject.getJSONArray("la");
            la1[j++] = jarray2.getDouble(0);
            JSONArray jarray3 = jObject.getJSONArray("lg");
            lo1[k++] = jarray3.getDouble(0);
            MARKER_POINT[z++] = MapPoint.mapPointWithGeoCoord(la1[j - 1], lo1[k - 1]);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();

        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://org.androidtown.location/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://org.androidtown.location/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();
            la = latitude;
            lo = longitude;


        }


        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    public void createDefaultMarker(MapView mapView) {
        textmessage = "";
        for (int i = 0; i < Marker.length; i++) {
            Marker[i] = new MapPOIItem();
            Marker[i].setItemName(String.valueOf(i + 1) + "번 쓰레기통\n 용량" + String.valueOf(capacity[i]) + "%");
            Marker[i].setTag(1);
            Marker[i].setMapPoint((MARKER_POINT[i]));
            Marker[i].setMarkerType(MapPOIItem.MarkerType.CustomImage);
            if(capacity[i]>80){
                Marker[i].setCustomImageResourceId(R.drawable.custom_marker_red);
            }
            else {
                Marker[i].setCustomImageResourceId(R.drawable.custom_marker_black);

            }
            Marker[i].setCustomImageAutoscale(true);
            Marker[i].setCustomImageAnchor(0.5f, 1.0f);
            mapView.addPOIItem(Marker[i]);
            mapView.selectPOIItem(Marker[i], true);
            if(capacity[i]>80) {


                textmessage = textmessage + String.valueOf(i + 1) + "번 ";

                full=true;
            }
        }

        textmessage = textmessage + "이 꽉 찼습니다";
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch (id) {
            case R.id.action_settings: {

                createDefaultMarker(mapView);
                mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
               if(full) {
                   Noti.Notify(getApplication(), "쓰레기통 관리 시스템", textmessage);
               }

            }

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            return true;
        }
        return onOptionsItemSelected(item);
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {

    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }

    @Override
    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {

    }

    @Override
    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {

    }

    public double  calDistance(double lat1, double lon1, double lat2, double lon2) {

        double theta, dist;
        theta = lon1 - lon2;
        dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);

        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;    // ?⑥쐞 mile ?먯꽌 km 蹂?솚.
        dist = dist * 1000.0;      // ?⑥쐞  km ?먯꽌 m 濡?蹂?솚

        return dist;
    }

    // 二쇱뼱吏???degree) 媛믪쓣 ?쇰뵒?몄쑝濡?蹂?솚
    private double  deg2rad(double deg) {
        return (double) (deg * Math.PI / (double) 180d);
    }

    // 二쇱뼱吏??쇰뵒??radian) 媛믪쓣 ??degree) 媛믪쑝濡?蹂?솚
    private double  rad2deg(double rad) {
        return (double) (rad * (double) 180d / Math.PI);
    }

    public static int partition(double arr[], int left, int right) {

        double pivot = arr[(left + right) / 2];

        while (left < right) {
            while ((arr[left] < pivot) && (left < right))
                left++;
            while ((arr[right] > pivot) && (left < right))
                right--;

            if (left < right) {
                double temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
            }
        }

        return left;
    }

    public static void quickSort(double arr[], int left, int right) {

        if (left < right) {
            int pivotNewIndex = partition(arr, left, right);

            quickSort(arr, left, pivotNewIndex - 1);
            quickSort(arr, pivotNewIndex + 1, right);
        }

    }
/*
    public static void qucksortb(double start1, double start2, double point[][],double dst1,double dst2, MapPolyline polyline) {
        double dist[] = new double[10];

        polyline.addPoint(MapPoint.mapPointWithGeoCoord(start1, start2));
        for (int i = 0; i < 10; i++) {

            dist[i] = calDistance(start1, start2, point[i][0], point[i][1]);

        }
        quickSort(dist, 0, dist.length - 1);

    for(int i=0; i<10; i++) {
        if (dist[i] == calDistance(start1, start2,point[i][0], point[i][1])) {
            if(point[i][0]==dst1&&point[i][1]==dst2){
                break;
            }
            polyline.addPoint(MapPoint.mapPointWithGeoCoord(point[i][0], point[i][1]));
            start1 = point[i][0];
            start2 = point[i][1];
            point[i][0] = point[i][0] * 2;
            point[i][1] = point[i][1] * 2;
        }


    }

    }*/







    public class back extends AsyncTask<String, Integer,Bitmap>{




        @Override

        public Bitmap doInBackground(String... urls) {

            // TODO Auto-generated method stub

            try{

                URL myFileUrl = new URL(urls[0]);

                HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();

                conn.setDoInput(true);

                conn.connect();

                //String json = DownloadHtml("http://?쒕쾭二쇱냼/appdata.php");

                InputStream is = conn.getInputStream();









            }catch(IOException e){

                e.printStackTrace();

            }
            return bmImg;

        }







    }
   public class phpDown extends AsyncTask<String, Integer,String>{







        @Override

        public String doInBackground(String... urls) {

            StringBuilder jsonHtml = new StringBuilder();

            try{

                // ?곌껐 url ?ㅼ젙

                URL url = new URL(urls[0]);

                // 而ㅻ꽖??媛앹껜 ?앹꽦

                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                // ?곌껐?섏뿀?쇰㈃.

                if(conn != null){

                    conn.setConnectTimeout(10000);

                    conn.setUseCaches(false);

                    // ?곌껐?섏뿀??肄붾뱶媛?由ы꽩?섎㈃.

                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){

                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                        for(;;){

                            // ?뱀긽??蹂댁뿬吏?뒗 ?띿뒪?몃? ?쇱씤?⑥쐞濡??쎌뼱 ??옣.

                            String line = br.readLine();

                            if(line == null) break;

                            // ??옣???띿뒪???쇱씤??jsonHtml??遺숈뿬?ｌ쓬

                            jsonHtml.append(line + "\n");

                        }

                        br.close();

                    }

                    conn.disconnect();

                }

            } catch(Exception ex){

                ex.printStackTrace();

            }



            return jsonHtml.toString();

        }

       protected void onPostExecute(String str){
           jSon=str;
           doJSONParser();
       }





    }







}













