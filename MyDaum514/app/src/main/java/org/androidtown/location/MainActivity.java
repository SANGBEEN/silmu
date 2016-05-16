package org.androidtown.location;

import android.Manifest;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
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

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;
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
  int i=0,j=0,k=0,z=0;
    MapPoint[] MARKER_POINT=new MapPoint[10];
    double[] capacity = new double[10];
    double[] la1 =  new double[10];
    double[] lo1 = new double[10];

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


    //NOTIFICATION설정

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    private GoogleApiClient client;
    private final String urlPath = "http://127.0.0.1/putdata.php";
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

        task1.execute("http://192.168.0.16//putdata.php");
        //doJSONParser();
        task2.execute("http://192.168.0.16//putdata2.php");
        task3.execute("http://192.168.0.16//putdata3.php");

        task4.execute("http://192.168.0.16//putdata4.php");
        task5.execute("http://192.168.0.16//putdata5.php");
        task6.execute("http://192.168.0.16//putdata6.php");
        task7.execute("http://192.168.0.16//putdata7.php");


        Noti.Notify(getApplication());







        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //맵뷰생성
        mapView = new MapView(this);
        mapView.setDaumMapApiKey("628faa3102922f3999d2a1f7352d4065");
        RelativeLayout container = (RelativeLayout) findViewById(R.id.map_view);
        container.addView(mapView);


        mapView.setCurrentLocationEventListener(this);  //트래킹모드를 위한 이벤트 리스너
        mapView.setZoomLevel(2, true);                  //줌레벨설정

        mapView.setMapViewEventListener(this);
        mapView.setPOIItemEventListener(this);




        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


        double dist[] = new double[5];
        double start1, start2, r1, r2, p1x, p1y, p2x, p2y, p3x, p3y, p4x, p4y, p5x, p5y;
        r1 = 0;
        r2 = 0;
        start1 = 37.374614;
        start2 = 126.632863;
        p1x = 37.374946;
        p1y = 126.633282;
        p2x = 37.374609;
        p2y = 126.632933;
        p3x = 37.374861;
        p3y = 126.632525;
        p4x = 37.375138;
        p4y = 126.632788;
        p5x = 37.375232;
        p5y = 126.633566;
        dist[0] = calDistance(start1, start2, p1x, p1y);
        dist[1] = calDistance(start1, start2, p2x, p2y);
        dist[2] = calDistance(start1, start2, p3x, p3y);
        dist[3] = calDistance(start1, start2, p4x, p4y);
        dist[4] = calDistance(start1, start2, p5x, p5y);
        quickSort(dist, 0, dist.length - 1);


        MapPolyline polyline = new MapPolyline();
        polyline.setTag(1000);
        polyline.setLineColor(Color.argb(128, 255, 51, 0)); // Polyline 컬러 지정.

// Polyline 좌표 지정.
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(start1, start2));
        for (int i = 0; i < dist.length; i++) {
            if (dist[i] == calDistance(start1, start2, p1x, p1y)) {
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(p1x, p1y));
                r1 = p1x;
                r2 = p1y;
                p1x = p1x * 2;
                p1y = p1y * 2;
                break;
            }
            if (dist[i] == calDistance(start1, start2, p2x, p2y)) {
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(p2x, p2y));
                r1 = p2x;
                r2 = p2y;
                p2x = p2x * 2;
                p2y = p2y * 2;
                break;
            }

            if (dist[i] == calDistance(start1, start2, p3x, p3y)) {
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(p3x, p3y));
                r1 = p3x;
                r2 = p3y;
                p3x = p3x * 2;
                p3y = p3y * 2;
                break;
            }
            if (dist[i] == calDistance(start1, start2, p4x, p4y)) {
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(p4x, p4y));
                r1 = p4x;
                r2 = p4y;
                p4x = p4x * 2;
                p4y = p4y * 2;
                break;
            }
            if (dist[i] == calDistance(start1, start2, p5x, p5y)) {

                polyline.addPoint(MapPoint.mapPointWithGeoCoord(p5x, p5y));
                r1 = p5x;
                r2 = p5y;
                p5x = p5x * 2;
                p5y = p5y * 2;
                break;
            }
        }
        dist[0] = calDistance(r1, r2, p1x, p1y);
        dist[1] = calDistance(r1, r2, p2x, p2y);
        dist[2] = calDistance(r1, r2, p3x, p3y);
        dist[3] = calDistance(r1, r2, p4x, p4y);
        dist[4] = calDistance(r1, r2, p5x, p5y);
        quickSort(dist, 0, dist.length - 1);

        for (int i = 0; i < dist.length; i++) {
            if (dist[i] == calDistance(r1, r2, p1x, p1y)) {
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(p1x, p1y));
                r1 = p1x;
                r2 = p1y;
                p1x = p1x * 2;
                p1y = p1y * 2;
                break;
            }
            if (dist[i] == calDistance(r1, r2, p2x, p2y)) {
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(p2x, p2y));
                r1 = p2x;
                r2 = p2y;
                p2x = p2x * 2;
                p2y = p2y * 2;
                break;
            }

            if (dist[i] == calDistance(r1, r2, p3x, p3y)) {
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(p3x, p3y));
                r1 = p3x;
                r2 = p3y;
                p3x = p3x * 2;
                p3y = p3y * 2;
                break;
            }
            if (dist[i] == calDistance(r1, r2, p4x, p4y)) {
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(p4x, p4y));
                r1 = p4x;
                r2 = p4y;
                p4x = p4x * 2;
                p4y = p4y * 2;
                break;
            }
            if (dist[i] == calDistance(r1, r2, p5x, p5y)) {

                polyline.addPoint(MapPoint.mapPointWithGeoCoord(p5x, p5y));
                r1 = p5x;
                r2 = p5y;
                p5x = p5x * 2;
                p5y = p5y * 2;
                break;
            }
        }
        dist[0] = calDistance(r1, r2, p1x, p1y);
        dist[1] = calDistance(r1, r2, p2x, p2y);
        dist[2] = calDistance(r1, r2, p3x, p3y);
        dist[3] = calDistance(r1, r2, p4x, p4y);
        dist[4] = calDistance(r1, r2, p5x, p5y);
        quickSort(dist, 0, dist.length - 1);

        for (int i = 0; i < dist.length; i++) {
            if (dist[i] == calDistance(r1, r2, p1x, p1y)) {
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(p1x, p1y));
                r1 = p1x;
                r2 = p1y;
                p1x = p1x * 2;
                p1y = p1y * 2;
                break;
            }
            if (dist[i] == calDistance(r1, r2, p2x, p2y)) {
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(p2x, p2y));
                r1 = p2x;
                r2 = p2y;
                p2x = p2x * 2;
                p2y = p2y * 2;
                break;
            }

            if (dist[i] == calDistance(r1, r2, p3x, p3y)) {
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(p3x, p3y));
                r1 = p3x;
                r2 = p3y;
                p3x = p3x * 2;
                p3y = p3y * 2;
                break;
            }
            if (dist[i] == calDistance(r1, r2, p4x, p4y)) {
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(p4x, p4y));
                r1 = p4x;
                r2 = p4y;
                p4x = p4x * 2;
                p4y = p4y * 2;
                break;
            }
            if (dist[i] == calDistance(r1, r2, p5x, p5y)) {

                polyline.addPoint(MapPoint.mapPointWithGeoCoord(p5x, p5y));
                r1 = p5x;
                r2 = p5y;
                p5x = p5x * 2;
                p5y = p5y * 2;
                break;
            }
        }
        dist[0] = calDistance(r1, r2, p1x, p1y);
        dist[1] = calDistance(r1, r2, p2x, p2y);
        dist[2] = calDistance(r1, r2, p3x, p3y);
        dist[3] = calDistance(r1, r2, p4x, p4y);
        dist[4] = calDistance(r1, r2, p5x, p5y);
        quickSort(dist, 0, dist.length - 1);

        for (int i = 0; i < dist.length; i++) {
            if (dist[i] == calDistance(r1, r2, p1x, p1y)) {
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(p1x, p1y));
                r1 = p1x;
                r2 = p1y;
                p1x = p1x * 2;
                p1y = p1y * 2;
                break;
            }
            if (dist[i] == calDistance(r1, r2, p2x, p2y)) {
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(p2x, p2y));
                r1 = p2x;
                r2 = p2y;
                p2x = p2x * 2;
                p2y = p2y * 2;
                break;
            }

            if (dist[i] == calDistance(r1, r2, p3x, p3y)) {
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(p3x, p3y));
                r1 = p3x;
                r2 = p3y;
                p3x = p3x * 2;
                p3y = p3y * 2;
                break;
            }
            if (dist[i] == calDistance(r1, r2, p4x, p4y)) {
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(p4x, p4y));
                r1 = p4x;
                r2 = p4y;
                p4x = p4x * 2;
                p4y = p4y * 2;
                break;
            }
            if (dist[i] == calDistance(r1, r2, p5x, p5y)) {

                polyline.addPoint(MapPoint.mapPointWithGeoCoord(p5x, p5y));
                r1 = p5x;
                r2 = p5y;
                p5x = p5x * 2;
                p5y = p5y * 2;
                break;
            }
        }
        dist[0] = calDistance(r1, r2, p1x, p1y);
        dist[1] = calDistance(r1, r2, p2x, p2y);
        dist[2] = calDistance(r1, r2, p3x, p3y);
        dist[3] = calDistance(r1, r2, p4x, p4y);
        dist[4] = calDistance(r1, r2, p5x, p5y);
        quickSort(dist, 0, dist.length - 1);

        for (int i = 0; i < dist.length; i++) {
            if (dist[i] == calDistance(r1, r2, p1x, p1y)) {
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(p1x, p1y));
                r1 = p1x;
                r2 = p1y;
                p1x = p1x * 2;
                p1y = p1y * 2;
                break;
            }
            if (dist[i] == calDistance(r1, r2, p2x, p2y)) {
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(p2x, p2y));
                r1 = p2x;
                r2 = p2y;
                p2x = p2x * 2;
                p2y = p2y * 2;
                break;
            }

            if (dist[i] == calDistance(r1, r2, p3x, p3y)) {
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(p3x, p3y));
                r1 = p3x;
                r2 = p3y;
                p3x = p3x * 2;
                p3y = p3y * 2;
                break;
            }
            if (dist[i] == calDistance(r1, r2, p4x, p4y)) {
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(p4x, p4y));
                r1 = p4x;
                r2 = p4y;
                p4x = p4x * 2;
                p4y = p4y * 2;
                break;
            }
            if (dist[i] == calDistance(r1, r2, p5x, p5y)) {

                polyline.addPoint(MapPoint.mapPointWithGeoCoord(p5x, p5y));
                r1 = p5x;
                r2 = p5y;
                p5x = p5x * 2;
                p5y = p5y * 2;
                break;
            }
        }
// Polyline 지도에 올리기.
        mapView.addPolyline(polyline);

// 지도뷰의 중심좌표와 줌레벨을 Polyline이 모두 나오도록 조정.
        MapPointBounds mapPointBounds = new MapPointBounds(polyline.getMapPoints());
        int padding = 100; // px
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));


    }

    public void doJSONParser(){
        try {

            JSONObject jObject = new JSONObject(jSon);

            JSONArray jarray = jObject.getJSONArray("capacity");
            capacity[i++] = jarray.getDouble(0);
            JSONArray jarray2 = jObject.getJSONArray("la");
            la1[j++] = jarray2.getDouble(0);
            JSONArray jarray3 = jObject.getJSONArray("lg");
            lo1[k++] = jarray3.getDouble(0);
            MARKER_POINT[z++]=MapPoint.mapPointWithGeoCoord(la1[j-1],lo1[k-1]);

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

        for(int i=0;i< Marker.length;i++){
            Marker[i] = new MapPOIItem();
            Marker[i].setItemName("용량" + String.valueOf(capacity[i]) + "%");
            Marker[i].setTag(1);
            Marker[i].setMapPoint((MARKER_POINT[i]));
            Marker[i].setMarkerType(MapPOIItem.MarkerType.CustomImage);
            Marker[i].setCustomImageResourceId(R.drawable.custom_marker_black);
            Marker[i].setCustomImageAutoscale(true);
            Marker[i].setCustomImageAnchor(0.5f, 1.0f);
            mapView.addPOIItem(Marker[i]);
            mapView.selectPOIItem(Marker[i], true);

        }

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


            }
            case R.id.action_settings2:{

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

    public double calDistance(double lat1, double lon1, double lat2, double lon2) {

        double theta, dist;
        theta = lon1 - lon2;
        dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);

        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;    // 단위 mile 에서 km 변환.
        dist = dist * 1000.0;      // 단위  km 에서 m 로 변환

        return dist;
    }

    // 주어진 도(degree) 값을 라디언으로 변환
    private double deg2rad(double deg) {
        return (double) (deg * Math.PI / (double) 180d);
    }

    // 주어진 라디언(radian) 값을 도(degree) 값으로 변환
    private double rad2deg(double rad) {
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






    public class back extends AsyncTask<String, Integer,Bitmap>{




        @Override

        public Bitmap doInBackground(String... urls) {

            // TODO Auto-generated method stub

            try{

                URL myFileUrl = new URL(urls[0]);

                HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();

                conn.setDoInput(true);

                conn.connect();

                //String json = DownloadHtml("http://서버주소/appdata.php");

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

                // 연결 url 설정

                URL url = new URL(urls[0]);

                // 커넥션 객체 생성

                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                // 연결되었으면.

                if(conn != null){

                    conn.setConnectTimeout(10000);

                    conn.setUseCaches(false);

                    // 연결되었음 코드가 리턴되면.

                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){

                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                        for(;;){

                            // 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.

                            String line = br.readLine();

                            if(line == null) break;

                            // 저장된 텍스트 라인을 jsonHtml에 붙여넣음

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













