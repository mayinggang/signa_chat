package chat.signa.net.pingfang.signa_chat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends Activity {

    ImageView volume;
    private LinearLayout voice_rcd_hint_rcding;
    private SoundMeter mSensor;
    private Button button,button2;
    private Handler mHandler ;
    private LinearLayout ll_progress_bar_containerp;
    //Handler mHandler = new Handler();
    private int recLen = 0;
    private Timer mTimer;
    String result;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensor = new SoundMeter();
        volume=(ImageView)findViewById(R.id.volume);
        button=(Button)findViewById(R.id.mains);
        button2=(Button)findViewById(R.id.mains2);

        ll_progress_bar_containerp=(LinearLayout)findViewById(R.id.ll_progress_bar_containerp_luyin);


        button.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int eventCode = event.getAction();
                switch (eventCode) {
                    case MotionEvent.ACTION_DOWN:
                        recLen = 0;
                        setTimerTask();
                        ll_progress_bar_containerp.setVisibility(View.VISIBLE);

                        return true;
                    case MotionEvent.ACTION_UP:

                        mTimer.cancel();
                        ll_progress_bar_containerp.setVisibility(View.GONE);
                        if (recLen < 4) {
                            Toast.makeText(getApplicationContext(), "录音时间太短才" + recLen + "秒", Toast.LENGTH_LONG).show();
                        }

                        return true;
                }
                return false;
            }
        });





    }

    /**
     *  PackageInfo info = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(),0);
     ApplicationInfo appInfo = info.applicationInfo;
     String sourceDir = appInfo.sourceDir;
     File file = new File(sourceDir);
     Uri sourceUri = Uri.fromFile(file);
     Intent sharingIntent = new Intent();

     sharingIntent.setAction(Intent.ACTION_SEND);
     sharingIntent.setType("application/*");
     sharingIntent.setPackage("com.android.bluetooth");
     sharingIntent.putExtra(Intent.EXTRA_STREAM, sourceUri);
     startActivity(Intent.createChooser(sharingIntent, "Share Application"));
     Toast.makeText(getContext(), sourceDir, Toast.LENGTH_SHORT).show();
     */
    public void BlueInfo(){
        try {
            PackageInfo info=getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(),0);
            ApplicationInfo appInfo=info.applicationInfo;
            String sourceDir=appInfo.sourceDir;
            File file=new File(sourceDir);
            Uri sourceUri=Uri.fromFile(file);
            Intent sharingIntent=new Intent();
            sharingIntent.setAction(Intent.ACTION_SEND);
            sharingIntent.setType("application/*");
            sharingIntent.setPackage("com.android.buletooth");
            sharingIntent.putExtra(Intent.EXTRA_STREAM, sourceUri);
            startActivity(Intent.createChooser(sharingIntent,"Share Application"));
            Toast.makeText(getApplicationContext(), sourceDir, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void initOkhttp(){
        OkHttpCommonUtil.Param[] params=new OkHttpCommonUtil.Param[]{
                new OkHttpCommonUtil.Param("id","13530745127"),
                new OkHttpCommonUtil.Param("password","123456")
        };
        OkHttpCommonUtil okHttp=OkHttpCommonUtil.newInstance(getApplicationContext());
        okHttp.getRequest("",params,new HttpBaseCallback(){
            @Override
            public void onResponse(Response response) throws IOException {
                super.onResponse(response);
                 result=response.body().toString();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Request request, IOException e) {
                super.onFailure(request, e);
            }
        });

    }


    private void setTimerTask() {
        mTimer=new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                recLen++;
            }
        }, 1000, 1000/* 表示1000毫秒之後，每隔1000毫秒執行一次 */);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
