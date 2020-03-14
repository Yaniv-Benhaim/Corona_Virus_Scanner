package tech.gamedev.coronavirusscanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RewardedVideoAdListener {

    Button scanNow;
    FrameLayout frameLayout,fullframe, loadingDialog;
    Camera camera;
    ShowCamera showCamera;
    ObjectAnimator animator;
    View scannerLayout;
    View scannerBar;
    ConstraintLayout mainlayout, main_info_constraint;
    LinearLayout navbar;
    TextView scanning, framing;
    AdView adView1;

    ImageView main_img_info;
    private static final String TAG = "MainActivity";
    private RewardedVideoAd mRewardedVideoAd;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*MobileAds.initialize(this, new OnInitializationCompleteListener() {
         //   @Override
         //   public void onInitializationComplete(InitializationStatus initializationStatus) {

         //       }
       // });


        MobileAds.initialize(this, "ca-app-pub-7226067785329539/8034143085");
       // Use an activity context to get the rewarded video instance.
       mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
       mRewardedVideoAd.setRewardedVideoAdListener(this);
       mRewardedVideoAd.loadAd("ca-app-pub-7226067785329539/8034143085", new AdRequest.Builder().build());
       */





        scanNow = findViewById(R.id.scan_btn);
        frameLayout = findViewById(R.id.cameraView);
        mainlayout = findViewById(R.id.main_layout);
        navbar = findViewById(R.id.navbar);
        fullframe = findViewById(R.id.scan_img);
        loadingDialog = findViewById(R.id.progress_frame_layout);
        scanning = findViewById(R.id.scanning);
        framing = findViewById(R.id.framing);
        adView1 = findViewById(R.id.ad_view1);
        main_img_info = findViewById(R.id.main_img_info);
        main_info_constraint = findViewById(R.id.constrain_info);

        MobileAds.initialize(this,"ca-app-pub-7226067785329539/9122195177");
        AdRequest adRequest = new AdRequest.Builder().build();
        adView1.loadAd(adRequest);



        scannerLayout = findViewById(R.id.scannerLayout);
        scannerBar = findViewById(R.id.scannerBar);


        animator = null;

        ViewTreeObserver vto = scannerLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                scannerLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    scannerLayout.getViewTreeObserver().
                            removeGlobalOnLayoutListener(this);

                } else {
                    scannerLayout.getViewTreeObserver().
                            removeOnGlobalLayoutListener(this);
                }

                float destination = (float)(scannerLayout.getY() +
                        scannerLayout.getHeight());

                animator = ObjectAnimator.ofFloat(scannerBar, "translationY",
                        scannerLayout.getY(),
                        destination);

                animator.setRepeatMode(ValueAnimator.REVERSE);
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.setDuration(3000);
                animator.start();

            }
        });




        scanNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionListener permissionListener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        loadingDialog.setVisibility(View.VISIBLE);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                               Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                               startActivity(intent);
                               finish();
                           }
                        },9000);
                        //if(mRewardedVideoAd.isLoaded())
                       // {
                       //     mRewardedVideoAd.show();
                       // }
                        main_info_constraint.setVisibility(View.INVISIBLE);
                        main_img_info.setVisibility(View.INVISIBLE);
                        scanning.setVisibility(View.VISIBLE);
                        framing.setVisibility(View.VISIBLE);
                        mainlayout.setBackgroundResource(R.drawable.redcoronabg);
                        navbar.setBackgroundColor(R.drawable.navbar_red);
                        fullframe.setVisibility(View.VISIBLE);
                        camera = camera.open();
                        showCamera = new ShowCamera(MainActivity.this,camera);
                         frameLayout.addView(showCamera);


                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {
                        Toast.makeText(MainActivity.this,"Permission not granted",Toast.LENGTH_SHORT).show();
                    }
                };
                TedPermission.with(MainActivity.this).setPermissionListener(permissionListener)
                        .setPermissions(Manifest.permission.CAMERA).check();
            }
        });

        MobileAds.initialize(this);


    }




    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }
}
