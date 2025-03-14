package com.example.doomchit_doomchit;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sdsmdg.harjot.crollerTest.Croller;
import com.sdsmdg.harjot.crollerTest.OnCrollerChangeListener;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.example.doomchit_doomchit.RecordingServicePop.ACTION_STOP;

public class BeatMakingPopActivity extends AppCompatActivity {
    // 녹음을 위한
    private final static boolean DEBUG = true;
    public static String BROADCAST_WAVEFORM = "com.example.doomchit_doomchit.waveform";
    public static String BROADCAST_EXTRA_DATA = "com.example.doomchit_doomchit.waveform_data";
    private ImageButton record_btn, guide_btn;
    private boolean isRecording = false;
    private Long durationTime = Long.valueOf(0);

    private SoundPool sound_pool1;
    private SoundPool sound_pool2;
    private SoundPool sound_pool3;
    private SoundPool sound_pool4;
    private SoundPool sound_pool5;
    private SoundPool sound_pool6;
    private SoundPool sound_pool78;
    ImageButton back; // 뒤로가기

    // 1트랙
    int beat1, beat2, beat3, beat4, beat5, beat6;
    ImageButton beatvolum1; ImageButton beat1_off, beat2_off, beat3_off, beat4_off, beat5_off, beat6_off;
    ImageButton beat1_on, beat2_on, beat3_on, beat4_on, beat5_on, beat6_on;

    // 2트랙
    int synth1, choir, whistle, synth2, piano, mbira;
    ImageButton beatvolum2; ImageButton two1_off, two2_off, two3_off, two4_off, two5_off, two6_off;
    ImageButton two1_on, two2_on, two3_on, two4_on, two5_on, two6_on;

    // 3트랙
    int keys1, strings1, mallets, pluck, keys2, flute;
    ImageButton beatvolum3; ImageButton three1_off, three2_off, three3_off, three4_off, three5_off, three6_off;
    ImageButton three1_on, three2_on, three3_on, three4_on, three5_on, three6_on;

    // 4트랙
    int bass1, synth3, pad1, brass1, strings2, bass2;
    ImageButton beatvolum4; ImageButton four1_off, four2_off, four3_off, four4_off, four5_off, four6_off;
    ImageButton four1_on, four2_on, four3_on, four4_on, four5_on, four6_on;

    // 5트랙
    int strings3, pad2, synth4, synth5, brass2, bass3;
    ImageButton beatvolum5; ImageButton five1_off, five2_off, five3_off, five4_off, five5_off, five6_off;
    ImageButton five1_on, five2_on, five3_on, five4_on, five5_on, five6_on;

    // 6트랙
    int vox7, vox8, vox9, vox10, beat7, beat8;
    ImageButton beatvolum6; ImageButton six1_off, six2_off, six3_off, six4_off, six5_off, six6_off;
    ImageButton six1_on, six2_on, six3_on, six4_on, six5_on, six6_on;

    // 7트랙
    int fx1, fx2, fx3, fx4, fx5, fx6;
    ImageButton seven1_off, seven2_off, seven3_off, seven4_off, seven5_off, seven6_off;
    ImageButton seven1_on, seven2_on, seven3_on, seven4_on, seven5_on, seven6_on;

    // 8트랙
    int vox1, vox2, vox3, vox4, vox5, vox6;
    ImageButton eight1_off, eight2_off, eight3_off, eight4_off, eight5_off, eight6_off;
    ImageButton eight1_on, eight2_on, eight3_on, eight4_on, eight5_on, eight6_on;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_original);

        //Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.glow_anim);
        sound_pool1 = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        sound_pool2 = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        sound_pool3 = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        sound_pool4 = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        sound_pool5 = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        sound_pool6 = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        sound_pool78 = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

        FindViewByid();
        Raw_Loder();
        visible_Gone();
        Touch();
        final Croller croller1 = findViewById(R.id.beatvolum1);
        final Croller croller2 = findViewById(R.id.beatvolum2);
        final Croller croller3 = findViewById(R.id.beatvolum3);
        final Croller croller4 = findViewById(R.id.beatvolum4);
        final Croller croller5 = findViewById(R.id.beatvolum5);
        final Croller croller6 = findViewById(R.id.beatvolum6);
        croller1.setOnCrollerChangeListener(
                new OnCrollerChangeListener() {
                    @Override
                    public void onProgressChanged(Croller croller,
                                                  int progress) {
                        float volume = (float)(1-Math.log(100 - croller1.getProgress()) / Math.log(100));
                        sound_pool1.setVolume(beat1, volume, volume);
                        sound_pool1.setVolume(beat2, volume, volume);
                        sound_pool1.setVolume(beat3, volume, volume);
                        sound_pool1.setVolume(beat4, volume, volume);
                        sound_pool1.setVolume(beat5, volume, volume);
                        sound_pool1.setVolume(beat6, volume, volume);
                    }
                    // when the user is starting to change the progress
                    // this function gets invoked automatically.
                    @Override
                    public void onStartTrackingTouch(Croller croller) {
                    }
                    // when the user stops to change the progress
                    // this function gets invoked automatically.
                    @Override
                    public void onStopTrackingTouch(Croller croller) {
                    }
                });
        croller2.setOnCrollerChangeListener(
                new OnCrollerChangeListener() {
                    @Override
                    public void onProgressChanged(Croller croller,
                                                  int progress) {
                        float volume = (float)(1-Math.log(100 - croller2.getProgress()) / Math.log(100));
                        sound_pool2.setVolume(synth1 , volume, volume);
                        sound_pool2.setVolume(choir , volume, volume);
                        sound_pool2.setVolume(whistle , volume, volume);
                        sound_pool2.setVolume(synth2 , volume, volume);
                        sound_pool2.setVolume(piano , volume, volume);
                        sound_pool2.setVolume(mbira , volume, volume);
                    }
                    // when the user is starting to change the progress
                    // this function gets invoked automatically.
                    @Override
                    public void onStartTrackingTouch(Croller croller) {
                    }
                    // when the user stops to change the progress
                    // this function gets invoked automatically.
                    @Override
                    public void onStopTrackingTouch(Croller croller) {
                    }
                });
        croller3.setOnCrollerChangeListener(
                new OnCrollerChangeListener() {
                    @Override
                    public void onProgressChanged(Croller croller,
                                                  int progress) {
                        float volume = (float)(1-Math.log(100 - croller3.getProgress()) / Math.log(100));
                        sound_pool3.setVolume(keys1  , volume, volume);
                        sound_pool3.setVolume(strings1  , volume, volume);
                        sound_pool3.setVolume(mallets  , volume, volume);
                        sound_pool3.setVolume(pluck  , volume, volume);
                        sound_pool3.setVolume(keys2  , volume, volume);
                        sound_pool3.setVolume(flute  , volume, volume);
                    }
                    // when the user is starting to change the progress
                    // this function gets invoked automatically.
                    @Override
                    public void onStartTrackingTouch(Croller croller) {
                    }
                    // when the user stops to change the progress
                    // this function gets invoked automatically.
                    @Override
                    public void onStopTrackingTouch(Croller croller) {
                    }
                });
        croller4.setOnCrollerChangeListener(
                new OnCrollerChangeListener() {
                    @Override
                    public void onProgressChanged(Croller croller,
                                                  int progress) {
                        float volume = (float)(1-Math.log(100 - croller4.getProgress()) / Math.log(100));
                        sound_pool4.setVolume(bass1  , volume, volume);
                        sound_pool4.setVolume(synth3  , volume, volume);
                        sound_pool4.setVolume(pad1   , volume, volume);
                        sound_pool4.setVolume(brass1  , volume, volume);
                        sound_pool4.setVolume(strings2  , volume, volume);
                        sound_pool4.setVolume(bass2  , volume, volume);
                    }
                    // when the user is starting to change the progress
                    // this function gets invoked automatically.
                    @Override
                    public void onStartTrackingTouch(Croller croller) {
                    }
                    // when the user stops to change the progress
                    // this function gets invoked automatically.
                    @Override
                    public void onStopTrackingTouch(Croller croller) {
                    }
                });
        croller5.setOnCrollerChangeListener(
                new OnCrollerChangeListener() {
                    @Override
                    public void onProgressChanged(Croller croller,
                                                  int progress) {
                        float volume = (float)(1-Math.log(100 - croller5.getProgress()) / Math.log(100));
                        sound_pool5.setVolume(strings3  , volume, volume);
                        sound_pool5.setVolume(pad2  , volume, volume);
                        sound_pool5.setVolume(synth4  , volume, volume);
                        sound_pool5.setVolume(synth5  , volume, volume);
                        sound_pool5.setVolume(brass2  , volume, volume);
                        sound_pool5.setVolume(bass3  , volume, volume);
                    }
                    // when the user is starting to change the progress
                    // this function gets invoked automatically.
                    @Override
                    public void onStartTrackingTouch(Croller croller) {
                    }
                    // when the user stops to change the progress
                    // this function gets invoked automatically.
                    @Override
                    public void onStopTrackingTouch(Croller croller) {
                    }
                });
        croller6.setOnCrollerChangeListener(
                new OnCrollerChangeListener() {
                    @Override
                    public void onProgressChanged(Croller croller,
                                                  int progress) {
                        float volume = (float)(1-Math.log(100 - croller6.getProgress()) / Math.log(100));
                        sound_pool6.setVolume(vox7  , volume, volume);
                        sound_pool6.setVolume(vox8  , volume, volume);
                        sound_pool6.setVolume(vox9  , volume, volume);
                        sound_pool6.setVolume(vox10  , volume, volume);
                        sound_pool6.setVolume(beat7  , volume, volume);
                        sound_pool6.setVolume(beat8  , volume, volume);
                    }
                    // when the user is starting to change the progress
                    // this function gets invoked automatically.
                    @Override
                    public void onStartTrackingTouch(Croller croller) {
                    }
                    // when the user stops to change the progress
                    // this function gets invoked automatically.
                    @Override
                    public void onStopTrackingTouch(Croller croller) {
                    }
                });
        // when there is a change in the progress of croller
        // this function get invoked automatically

        BeatBtn1(beat1_off, beat1_on, beat2_on, beat3_on, beat4_on, beat5_on, beat6_on, sound_pool1, beat1);
        BeatBtn1(beat2_off, beat2_on, beat1_on, beat3_on, beat4_on, beat5_on, beat6_on, sound_pool1, beat2);
        BeatBtn1(beat3_off, beat3_on, beat2_on, beat1_on, beat4_on, beat5_on, beat6_on, sound_pool1, beat3);
        BeatBtn1(beat4_off, beat4_on, beat2_on, beat3_on, beat1_on, beat5_on, beat6_on, sound_pool1, beat4);
        BeatBtn1(beat5_off, beat5_on, beat2_on, beat3_on, beat4_on, beat1_on, beat6_on, sound_pool1, beat5);
        BeatBtn1(beat6_off, beat6_on, beat2_on, beat3_on, beat4_on, beat5_on, beat1_on, sound_pool1, beat6);

        BeatBtn2(two1_off, two1_on, two2_on, two3_on, two4_on, two5_on, two6_on, sound_pool2, synth1);
        BeatBtn2(two2_off, two2_on, two1_on, two3_on, two4_on, two5_on, two6_on, sound_pool2, choir);
        BeatBtn2(two3_off, two3_on, two2_on, two1_on, two4_on, two5_on, two6_on, sound_pool2, whistle);
        BeatBtn2(two4_off, two4_on, two2_on, two3_on, two1_on, two5_on, two6_on, sound_pool2, synth2);
        BeatBtn2(two5_off, two5_on, two2_on, two3_on, two4_on, two1_on, two6_on, sound_pool2, piano);
        BeatBtn2(two6_off, two6_on, two2_on, two3_on, two4_on, two5_on, two1_on, sound_pool2, mbira);

        BeatBtn3(three1_off, three1_on, three2_on, three3_on, three4_on, three5_on, three6_on, sound_pool3, keys1);
        BeatBtn3(three2_off, three2_on, three1_on, three3_on, three4_on, three5_on, three6_on, sound_pool3, strings1);
        BeatBtn3(three3_off, three3_on, three2_on, three1_on, three4_on, three5_on, three6_on, sound_pool3, mallets);
        BeatBtn3(three4_off, three4_on, three2_on, three3_on, three1_on, three5_on, three6_on, sound_pool3, pluck);
        BeatBtn3(three5_off, three5_on, three2_on, three3_on, three4_on, three1_on, three6_on, sound_pool3, keys2);
        BeatBtn3(three6_off, three6_on, three2_on, three3_on, three4_on, three5_on, three1_on, sound_pool3, flute);

        BeatBtn4(four1_off, four1_on, four2_on, four3_on, four4_on, four5_on, four6_on, sound_pool4, bass1);
        BeatBtn4(four2_off, four2_on, four1_on, four3_on, four4_on, four5_on, four6_on, sound_pool4, synth3);
        BeatBtn4(four3_off, four3_on, four2_on, four1_on, four4_on, four5_on, four6_on, sound_pool4, pad1);
        BeatBtn4(four4_off, four4_on, four2_on, four3_on, four1_on, four5_on, four6_on, sound_pool4, brass1);
        BeatBtn4(four5_off, four5_on, four2_on, four3_on, four4_on, four1_on, four6_on, sound_pool4, strings2);
        BeatBtn4(four6_off, four6_on, four2_on, four3_on, four4_on, four5_on, four1_on, sound_pool4, bass2);

        BeatBtn5(five1_off, five1_on, five2_on, five3_on, five4_on, five5_on, five6_on, sound_pool5, strings3);
        BeatBtn5(five2_off, five2_on, five1_on, five3_on, five4_on, five5_on, five6_on, sound_pool5, pad2);
        BeatBtn5(five3_off, five3_on, five2_on, five1_on, five4_on, five5_on, five6_on, sound_pool5, synth4);
        BeatBtn5(five4_off, five4_on, five2_on, five3_on, five1_on, five5_on, five6_on, sound_pool5, synth5);
        BeatBtn5(five5_off, five5_on, five2_on, five3_on, five4_on, five1_on, five6_on, sound_pool5, brass2);
        BeatBtn5(five6_off, five6_on, five2_on, five3_on, five4_on, five5_on, five1_on, sound_pool5, bass3);

        BeatBtn6(six1_off, six1_on, six2_on, six3_on, six4_on, six5_on, six6_on, sound_pool6, vox7);
        BeatBtn6(six2_off, six2_on, six1_on, six3_on, six4_on, six5_on, six6_on, sound_pool6, vox8);
        BeatBtn6(six3_off, six3_on, six2_on, six1_on, six4_on, six5_on, six6_on, sound_pool6, vox9);
        BeatBtn6(six4_off, six4_on, six2_on, six3_on, six1_on, six5_on, six6_on, sound_pool6, vox10);
        BeatBtn6(six5_off, six5_on, six2_on, six3_on, six4_on, six1_on, six6_on, sound_pool6, beat7);
        BeatBtn6(six6_off, six6_on, six2_on, six3_on, six4_on, six5_on, six1_on, sound_pool6, beat8);
        
        //가이드 영상
        guide_btn = findViewById(R.id.guide);
        guide_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://youtu.be/XAPHrxQOmzs"));
                startActivity(intent);
            }
        });
        
        //녹음
        record_btn = findViewById(R.id.record_btn);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, makeIntentFilter());

        record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRecording) {
                    if (ContextCompat.checkSelfPermission(BeatMakingPopActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(BeatMakingPopActivity.this, new String[]{Manifest.permission.RECORD_AUDIO},
                                1000);
                    } else {
                        MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
                        if (mediaProjectionManager != null) {
                            startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), 2000);
                        }
                    }
                } else {
                    final Intent broadcast = new Intent(ACTION_STOP);
                    sendBroadcast(broadcast);
                    record_btn.setImageResource(R.drawable.record_btn);
                    isRecording = false;
                }
            }
        });


        //뒤로가기
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveActivity(BeatSelectActivity.class);
                track1_stop();
                track2_stop();
                track3_stop();
                track4_stop();
                track5_stop();
                track6_stop();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    //Toast.makeText(BeatMakingActivity.this, "닿음", Toast.LENGTH_SHORT).show();
                    switch (v.getId()) {
                        // 7트랙 fx1, fx2, fx3, fx4, fx5, fx6;
                        case R.id.seven1_off:
                            //beat1.startAnimation(startAnimation);
                            seven1_on.setVisibility(View.VISIBLE);
                            if (seven1_on.getVisibility() == View.VISIBLE) {
                                sound_pool78.play(fx1, 100, 100, 0, 0, 1f);
                            }
                            //Toast.makeText(BeatMakingActivity.this, "7-1", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.seven2_off:
                            //beat2.startAnimation(startAnimation);
                            seven2_on.setVisibility(View.VISIBLE);
                            if (seven2_on.getVisibility() == View.VISIBLE) {
                                sound_pool78.play(fx2, 100, 100, 0, 0, 1f);
                            }
                            break;
                        case R.id.seven3_off:
                            //beat3.startAnimation(startAnimation);
                            seven3_on.setVisibility(View.VISIBLE);
                            if (seven3_on.getVisibility() == View.VISIBLE) {
                                sound_pool78.play(fx3, 100, 100, 0, 0, 1f);
                            }
                            break;
                        case R.id.seven4_off:
                            //beat4.startAnimation(startAnimation);
                            seven4_on.setVisibility(View.VISIBLE);
                            if (seven4_on.getVisibility() == View.VISIBLE) {
                                sound_pool78.play(fx4, 100, 100, 0, 0, 1f);
                            }
                            break;
                        case R.id.seven5_off:
                            seven5_on.setVisibility(View.VISIBLE);
                            if (seven5_on.getVisibility() == View.VISIBLE) {
                                sound_pool78.play(fx5, 100, 100, 0, 0, 1f);
                            }
                            break;
                        case R.id.seven6_off:
                            seven6_on.setVisibility(View.VISIBLE);
                            if (seven6_on.getVisibility() == View.VISIBLE) {
                                sound_pool78.play(fx6, 100, 100, 0, 0, 1f);
                            }
                            break;

                        // 8트랙 vox1, vox2, vox3, vox4, vox5, vox6;
                        case R.id.eight1_off:
                            //beat1.startAnimation(startAnimation);
                            eight1_on.setVisibility(View.VISIBLE);
                            if (eight1_on.getVisibility() == View.VISIBLE) {
                                sound_pool78.play(vox1, 100, 100, 0, 0, 1f);
                            }
                            break;
                        case R.id.eight2_off:
                            //beat2.startAnimation(startAnimation);
                            eight2_on.setVisibility(View.VISIBLE);
                            if (eight2_on.getVisibility() == View.VISIBLE) {
                                sound_pool78.play(vox2, 100, 100, 0, 0, 1f);
                            }
                            break;
                        case R.id.eight3_off:
                            //beat3.startAnimation(startAnimation);
                            eight3_on.setVisibility(View.VISIBLE);
                            if (eight3_on.getVisibility() == View.VISIBLE) {
                                sound_pool78.play(vox3, 100, 100, 0, 0, 1f);
                            }
                            break;
                        case R.id.eight4_off:
                            //beat4.startAnimation(startAnimation);
                            eight4_on.setVisibility(View.VISIBLE);
                            if (eight4_on.getVisibility() == View.VISIBLE) {
                                sound_pool78.play(vox4, 100, 100, 0, 0, 1f);
                            }
                            break;
                        case R.id.eight5_off:
                            eight5_on.setVisibility(View.VISIBLE);
                            if (eight5_on.getVisibility() == View.VISIBLE) {
                                sound_pool78.play(vox5, 100, 100, 0, 0, 1f);
                            }
                            break;
                        case R.id.eight6_off:
                            eight6_on.setVisibility(View.VISIBLE);
                            if (eight6_on.getVisibility() == View.VISIBLE) {
                                sound_pool78.play(vox6, 100, 100, 0, 0, 1f);
                            }
                            break;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    // 7트랙 fx1, fx2, fx3, fx4, fx5, fx6;
                    switch (v.getId()) {
                        case R.id.seven1_off:
                            //beat1.startAnimation(startAnimation);
                            seven1_on.setVisibility(View.GONE);
                            sound_pool78.stop(fx1);
                            break;
                        case R.id.seven2_off:
                            //beat2.startAnimation(startAnimation);
                            seven2_on.setVisibility(View.GONE);
                            sound_pool78.stop(fx2);
                            break;
                        case R.id.seven3_off:
                            //beat3.startAnimation(startAnimation);
                            seven3_on.setVisibility(View.GONE);
                            sound_pool78.stop(fx3);
                            break;
                        case R.id.seven4_off:
                            //beat4.startAnimation(startAnimation);
                            seven4_on.setVisibility(View.GONE);
                            sound_pool78.stop(fx4);
                            break;
                        case R.id.seven5_off:
                            seven5_on.setVisibility(View.GONE);
                            sound_pool78.stop(fx5);
                            break;
                        case R.id.seven6_off:
                            seven6_on.setVisibility(View.GONE);
                            sound_pool78.stop(fx6);
                            break;

                        // 8트랙 vox1, vox2, vox3, vox4, vox5, vox6;
                        case R.id.eight1_off:
                            //beat1.startAnimation(startAnimation);
                            eight1_on.setVisibility(View.GONE);
                            sound_pool78.stop(vox1);
                            break;
                        case R.id.eight2_off:
                            //beat2.startAnimation(startAnimation);
                            eight2_on.setVisibility(View.GONE);
                            sound_pool78.stop(vox2);
                            break;
                        case R.id.eight3_off:
                            //beat3.startAnimation(startAnimation);
                            eight3_on.setVisibility(View.GONE);
                            sound_pool78.stop(vox3);
                            break;
                        case R.id.eight4_off:
                            //beat4.startAnimation(startAnimation);
                            eight4_on.setVisibility(View.GONE);
                            sound_pool78.stop(vox4);
                            break;
                        case R.id.eight5_off:
                            eight5_on.setVisibility(View.GONE);
                            sound_pool78.stop(vox5);
                            break;
                        case R.id.eight6_off:
                            eight6_on.setVisibility(View.GONE);
                            sound_pool78.stop(vox6);
                            break;
                        default:
                            break;
                    }
                default:
                    break;
            }
            return true;
        }
    };
    private void stopAudio(MediaPlayer media) {
        if(media != null && media.isPlaying()){
            media.stop();
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    public void Touch() {
        seven1_off.setOnTouchListener(onTouchListener);
        seven2_off.setOnTouchListener(onTouchListener);
        seven3_off.setOnTouchListener(onTouchListener);
        seven4_off.setOnTouchListener(onTouchListener);
        seven5_off.setOnTouchListener(onTouchListener);
        seven6_off.setOnTouchListener(onTouchListener);
        eight1_off.setOnTouchListener(onTouchListener);
        eight2_off.setOnTouchListener(onTouchListener);
        eight3_off.setOnTouchListener(onTouchListener);
        eight4_off.setOnTouchListener(onTouchListener);
        eight5_off.setOnTouchListener(onTouchListener);
        eight6_off.setOnTouchListener(onTouchListener);
    }

    // 액티비티 이동 메서드
    public void MoveActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
    //track1/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //멈춤
    public void track1_stop() {
        sound_pool1.stop(beat1);
        sound_pool1.stop(beat2);
        sound_pool1.stop(beat3);
        sound_pool1.stop(beat4);
        sound_pool1.stop(beat5);
        sound_pool1.stop(beat6);
    }
    //on버튼
    public void On1(final ImageButton on, ImageButton off1, ImageButton off2, ImageButton off3, ImageButton off4, ImageButton off5, final SoundPool s, final int m) {
        on.setVisibility(View.VISIBLE);
        off1.setVisibility(View.GONE);
        off2.setVisibility(View.GONE);
        off3.setVisibility(View.GONE);
        off4.setVisibility(View.GONE);
        off5.setVisibility(View.GONE);
        track1_stop();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (on.getVisibility() == View.VISIBLE) {
                    track1_stop();
                    final Croller croller1 = findViewById(R.id.beatvolum1);
                    float volume = (float)(1-Math.log(100 - croller1.getProgress()) / Math.log(100));
                    s.play(m, volume, volume, 0, -1, 1f);
                }
            }
        }, 1000); //딜레이 타임 조절
        if (on.getVisibility() == View.GONE) {
            track1_stop();
        }
    }
    //off버튼
    public void Off1(ImageButton on) {
        track1_stop();
        if(on.getVisibility() == View.VISIBLE) {
            on.setVisibility(View.GONE);
        } if (on.getVisibility() == View.GONE) {
            track1_stop();
        }
    }
    //on,off합침
    public void BeatBtn1(ImageButton off_btn, final ImageButton on, final ImageButton off1, final ImageButton off2, final ImageButton off3, final ImageButton off4, final ImageButton off5, final SoundPool s, final int m) {
        off_btn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                On1(on, off1, off2, off3, off4, off5, s, m);
            }
        });
        on.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Off1(on);
            }
        });
    }


    //track2/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void track2_stop() {
        sound_pool2.stop(synth1);
        sound_pool2.stop(choir);
        sound_pool2.stop(whistle);
        sound_pool2.stop(synth2);
        sound_pool2.stop(piano);
        sound_pool2.stop(mbira);
    }
    //on버튼
    public void On2(final ImageButton on, ImageButton off1, ImageButton off2, ImageButton off3, ImageButton off4, ImageButton off5, final SoundPool s, final int m) {
        on.setVisibility(View.VISIBLE);
        off1.setVisibility(View.GONE);
        off2.setVisibility(View.GONE);
        off3.setVisibility(View.GONE);
        off4.setVisibility(View.GONE);
        off5.setVisibility(View.GONE);
        track2_stop();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (on.getVisibility() == View.VISIBLE) {
                    track2_stop();
                    final Croller croller2 = findViewById(R.id.beatvolum2);
                    float volume = (float)(1-Math.log(100 - croller2.getProgress()) / Math.log(100));
                    s.play(m, volume, volume, 0, -1, 1f);
                }
            }
        }, 1000); //딜레이 타임 조절
        if (on.getVisibility() == View.GONE) {
            track2_stop();
        }
    }
    //off버튼
    public void Off2(ImageButton on) {
        track2_stop();
        if(on.getVisibility() == View.VISIBLE) {
            on.setVisibility(View.GONE);
        } if (on.getVisibility() == View.GONE) {
            track2_stop();
        }
    }
    //on,off합침
    public void BeatBtn2(ImageButton off_btn, final ImageButton on, final ImageButton off1, final ImageButton off2, final ImageButton off3, final ImageButton off4, final ImageButton off5, final SoundPool s, final int m) {
        off_btn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                On2(on, off1, off2, off3, off4, off5, s, m);
            }
        });
        on.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Off2(on);
            }
        });
    }


    //track3/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void track3_stop() {
        sound_pool3.stop(keys1);
        sound_pool3.stop(strings1);
        sound_pool3.stop(mallets);
        sound_pool3.stop(pluck);
        sound_pool3.stop(keys2);
        sound_pool3.stop(flute);
    }
    //on버튼
    public void On3(final ImageButton on, ImageButton off1, ImageButton off2, ImageButton off3, ImageButton off4, ImageButton off5, final SoundPool s, final int m) {
        on.setVisibility(View.VISIBLE);
        off1.setVisibility(View.GONE);
        off2.setVisibility(View.GONE);
        off3.setVisibility(View.GONE);
        off4.setVisibility(View.GONE);
        off5.setVisibility(View.GONE);
        track3_stop();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (on.getVisibility() == View.VISIBLE) {
                    track3_stop();
                    final Croller croller3 = findViewById(R.id.beatvolum3);
                    float volume = (float)(1-Math.log(100 - croller3.getProgress()) / Math.log(100));
                    s.play(m, volume, volume, 0, -1, 1f);
                }
            }
        }, 1000); //딜레이 타임 조절
        if (on.getVisibility() == View.GONE) {
            track3_stop();
        }
    }
    //off버튼
    public void Off3(ImageButton on) {
        track3_stop();
        if(on.getVisibility() == View.VISIBLE) {
            on.setVisibility(View.GONE);
        } if (on.getVisibility() == View.GONE) {
            track3_stop();
        }
    }
    //on,off합침
    public void BeatBtn3(ImageButton off_btn, final ImageButton on, final ImageButton off1, final ImageButton off2, final ImageButton off3, final ImageButton off4, final ImageButton off5, final SoundPool s, final int m) {
        off_btn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                On3(on, off1, off2, off3, off4, off5, s, m);
            }
        });
        on.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Off3(on);
            }
        });
    }

    //track4/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void track4_stop() {
        sound_pool4.stop(bass1);
        sound_pool4.stop(synth3);
        sound_pool4.stop(pad1);
        sound_pool4.stop(brass1);
        sound_pool4.stop(strings2);
        sound_pool4.stop(bass2);
    }
    //on버튼
    public void On4(final ImageButton on, ImageButton off1, ImageButton off2, ImageButton off3, ImageButton off4, ImageButton off5, final SoundPool s, final int m) {
        on.setVisibility(View.VISIBLE);
        off1.setVisibility(View.GONE);
        off2.setVisibility(View.GONE);
        off3.setVisibility(View.GONE);
        off4.setVisibility(View.GONE);
        off5.setVisibility(View.GONE);
        track4_stop();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (on.getVisibility() == View.VISIBLE) {
                    track4_stop();
                    final Croller croller4 = findViewById(R.id.beatvolum4);
                    float volume = (float)(1-Math.log(100 - croller4.getProgress()) / Math.log(100));
                    s.play(m, volume, volume, 0, -1, 1f);
                }
            }
        }, 3000); //딜레이 타임 조절
        if (on.getVisibility() == View.GONE) {
            track4_stop();
        }
    }
    //off버튼
    public void Off4(ImageButton on) {
        track4_stop();
        if(on.getVisibility() == View.VISIBLE) {
            on.setVisibility(View.GONE);
        } if (on.getVisibility() == View.GONE) {
            track4_stop();
        }
    }
    //on,off합침
    public void BeatBtn4(ImageButton off_btn, final ImageButton on, final ImageButton off1, final ImageButton off2, final ImageButton off3, final ImageButton off4, final ImageButton off5, final SoundPool s, final int m) {
        off_btn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                On4(on, off1, off2, off3, off4, off5, s, m);
            }
        });
        on.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Off4(on);
            }
        });
    }

    //track5/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void track5_stop() {
        sound_pool5.stop(strings3);
        sound_pool5.stop(pad2);
        sound_pool5.stop(synth4);
        sound_pool5.stop(synth5);
        sound_pool5.stop(brass2);
        sound_pool5.stop(bass3);
    }
    //on버튼
    public void On5(final ImageButton on, ImageButton off1, ImageButton off2, ImageButton off3, ImageButton off4, ImageButton off5, final SoundPool s, final int m) {
        on.setVisibility(View.VISIBLE);
        off1.setVisibility(View.GONE);
        off2.setVisibility(View.GONE);
        off3.setVisibility(View.GONE);
        off4.setVisibility(View.GONE);
        off5.setVisibility(View.GONE);
        track5_stop();Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (on.getVisibility() == View.VISIBLE) {
                    track5_stop();
                    final Croller croller5 = findViewById(R.id.beatvolum5);
                    float volume = (float)(1-Math.log(100 - croller5.getProgress()) / Math.log(100));
                    s.play(m, volume, volume, 0, -1, 1f);
                }
            }
        }, 1000); //딜레이 타임 조절
        if (on.getVisibility() == View.GONE) {
            track5_stop();
        }
    }
    //off버튼
    public void Off5(ImageButton on) {
        track5_stop();
        if(on.getVisibility() == View.VISIBLE) {
            on.setVisibility(View.GONE);
        } if (on.getVisibility() == View.GONE) {
            track5_stop();
        }
    }
    //on,off합침
    public void BeatBtn5(ImageButton off_btn, final ImageButton on, final ImageButton off1, final ImageButton off2, final ImageButton off3, final ImageButton off4, final ImageButton off5, final SoundPool s, final int m) {
        off_btn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                On5(on, off1, off2, off3, off4, off5, s, m);
            }
        });
        on.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Off5(on);
            }
        });
    }

    //track6/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void track6_stop() {
        sound_pool6.stop(vox7);
        sound_pool6.stop(vox8);
        sound_pool6.stop(vox9);
        sound_pool6.stop(vox10);
        sound_pool6.stop(beat7);
        sound_pool6.stop(beat8);
    }
    //on버튼
    public void On6(final ImageButton on, ImageButton off1, ImageButton off2, ImageButton off3, ImageButton off4, ImageButton off5, final SoundPool s, final int m) {
        on.setVisibility(View.VISIBLE);
        off1.setVisibility(View.GONE);
        off2.setVisibility(View.GONE);
        off3.setVisibility(View.GONE);
        off4.setVisibility(View.GONE);
        off5.setVisibility(View.GONE);
        track6_stop();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (on.getVisibility() == View.VISIBLE) {
                    track6_stop();
                    final Croller croller6 = findViewById(R.id.beatvolum6);
                    float volume = (float)(1-Math.log(100 - croller6.getProgress()) / Math.log(100));
                    s.play(m, volume, volume, 0, -1, 1f);
                }
            }
        }, 1000); //딜레이 타임 조절
        if (on.getVisibility() == View.GONE) {
            track6_stop();
        }
    }
    //off버튼
    public void Off6(ImageButton on) {
        track6_stop();
        if(on.getVisibility() == View.VISIBLE) {
            on.setVisibility(View.GONE);
        } if (on.getVisibility() == View.GONE) {
            track6_stop();
        }
    }
    //on,off합침
    public void BeatBtn6(ImageButton off_btn, final ImageButton on, final ImageButton off1, final ImageButton off2, final ImageButton off3, final ImageButton off4, final ImageButton off5, final SoundPool s, final int m) {
        off_btn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                On6(on, off1, off2, off3, off4, off5, s, m);
            }
        });
        on.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Off6(on);
            }
        });
    }

    //findviewbyid
    public void FindViewByid() {
        beat1_off = findViewById(R.id.beat1_off);
        beat2_off = findViewById(R.id.beat2_off);
        beat3_off = findViewById(R.id.beat3_off);
        beat4_off = findViewById(R.id.beat4_off);
        beat5_off = findViewById(R.id.beat5_off);
        beat6_off = findViewById(R.id.beat6_off);

        beat1_on = findViewById(R.id.beat1_on);
        beat2_on = findViewById(R.id.beat2_on);
        beat3_on = findViewById(R.id.beat3_on);
        beat4_on = findViewById(R.id.beat4_on);
        beat5_on = findViewById(R.id.beat5_on);
        beat6_on = findViewById(R.id.beat6_on);

        two1_off = findViewById(R.id.two1_off);
        two2_off = findViewById(R.id.two2_off);
        two3_off = findViewById(R.id.two3_off);
        two4_off = findViewById(R.id.two4_off);
        two5_off = findViewById(R.id.two5_off);
        two6_off = findViewById(R.id.two6_off);

        two1_on = findViewById(R.id.two1_on);
        two2_on = findViewById(R.id.two2_on);
        two3_on = findViewById(R.id.two3_on);
        two4_on = findViewById(R.id.two4_on);
        two5_on = findViewById(R.id.two5_on);
        two6_on = findViewById(R.id.two6_on);

        three1_off = findViewById(R.id.three1_off);
        three2_off = findViewById(R.id.three2_off);
        three3_off = findViewById(R.id.three3_off);
        three4_off = findViewById(R.id.three4_off);
        three5_off = findViewById(R.id.three5_off);
        three6_off = findViewById(R.id.three6_off);

        three1_on = findViewById(R.id.three1_on);
        three2_on = findViewById(R.id.three2_on);
        three3_on = findViewById(R.id.three3_on);
        three4_on = findViewById(R.id.three4_on);
        three5_on = findViewById(R.id.three5_on);
        three6_on = findViewById(R.id.three6_on);

        four1_off = findViewById(R.id.four1_off);
        four2_off = findViewById(R.id.four2_off);
        four3_off = findViewById(R.id.four3_off);
        four4_off = findViewById(R.id.four4_off);
        four5_off = findViewById(R.id.four5_off);
        four6_off = findViewById(R.id.four6_off);

        four1_on = findViewById(R.id.four1_on);
        four2_on = findViewById(R.id.four2_on);
        four3_on = findViewById(R.id.four3_on);
        four4_on = findViewById(R.id.four4_on);
        four5_on = findViewById(R.id.four5_on);
        four6_on = findViewById(R.id.four6_on);

        five1_off = findViewById(R.id.five1_off);
        five2_off = findViewById(R.id.five2_off);
        five3_off = findViewById(R.id.five3_off);
        five4_off = findViewById(R.id.five4_off);
        five5_off = findViewById(R.id.five5_off);
        five6_off = findViewById(R.id.five6_off);

        five1_on = findViewById(R.id.five1_on);
        five2_on = findViewById(R.id.five2_on);
        five3_on = findViewById(R.id.five3_on);
        five4_on = findViewById(R.id.five4_on);
        five5_on = findViewById(R.id.five5_on);
        five6_on = findViewById(R.id.five6_on);

        six1_off = findViewById(R.id.six1_off);
        six2_off = findViewById(R.id.six2_off);
        six3_off = findViewById(R.id.six3_off);
        six4_off = findViewById(R.id.six4_off);
        six5_off = findViewById(R.id.six5_off);
        six6_off = findViewById(R.id.six6_off);

        six1_on = findViewById(R.id.six1_on);
        six2_on = findViewById(R.id.six2_on);
        six3_on = findViewById(R.id.six3_on);
        six4_on = findViewById(R.id.six4_on);
        six5_on = findViewById(R.id.six5_on);
        six6_on = findViewById(R.id.six6_on);

        seven1_off = findViewById(R.id.seven1_off);
        seven2_off = findViewById(R.id.seven2_off);
        seven3_off = findViewById(R.id.seven3_off);
        seven4_off = findViewById(R.id.seven4_off);
        seven5_off = findViewById(R.id.seven5_off);
        seven6_off = findViewById(R.id.seven6_off);

        seven1_on = findViewById(R.id.seven1_on);
        seven2_on = findViewById(R.id.seven2_on);
        seven3_on = findViewById(R.id.seven3_on);
        seven4_on = findViewById(R.id.seven4_on);
        seven5_on = findViewById(R.id.seven5_on);
        seven6_on = findViewById(R.id.seven6_on);

        eight1_off = findViewById(R.id.eight1_off);
        eight2_off = findViewById(R.id.eight2_off);
        eight3_off = findViewById(R.id.eight3_off);
        eight4_off = findViewById(R.id.eight4_off);
        eight5_off = findViewById(R.id.eight5_off);
        eight6_off = findViewById(R.id.eight6_off);


        eight1_on = findViewById(R.id.eight1_on);
        eight2_on = findViewById(R.id.eight2_on);
        eight3_on = findViewById(R.id.eight3_on);
        eight4_on = findViewById(R.id.eight4_on);
        eight5_on = findViewById(R.id.eight5_on);
        eight6_on = findViewById(R.id.eight6_on);
    }
    //음원 로더
    public void Raw_Loder() {
//1번째 트랙
        beat1 = sound_pool1.load(this, R.raw.pop101, 1);
        beat2 = sound_pool1.load(this, R.raw.pop102, 1);
        beat3 = sound_pool1.load(this, R.raw.pop103, 1);
        beat4 = sound_pool1.load(this, R.raw.pop104, 1);
        beat5 = sound_pool1.load(this, R.raw.pop105, 1);
        beat6 = sound_pool1.load(this, R.raw.pop106, 1);

        // 2번째 트랙
        synth1 = sound_pool2.load(this, R.raw.pop201, 1);
        choir = sound_pool2.load(this, R.raw.pop202, 1);
        whistle = sound_pool2.load(this, R.raw.pop203, 1);
        synth2 = sound_pool2.load(this, R.raw.pop204, 1);
        piano = sound_pool2.load(this, R.raw.pop205, 1);
        mbira = sound_pool2.load(this, R.raw.pop206, 1);

        // 3번째 트랙
        keys1 = sound_pool3.load(this, R.raw.pop301, 1);
        strings1 = sound_pool3.load(this, R.raw.pop302, 1);
        mallets = sound_pool3.load(this, R.raw.pop303, 1);
        pluck = sound_pool3.load(this, R.raw.pop304, 1);
        keys2 = sound_pool3.load(this, R.raw.pop305, 1);
        flute = sound_pool3.load(this, R.raw.pop306, 1);

        // 4번째 트랙
        bass1 = sound_pool4.load(this, R.raw.pop401, 1);
        synth3 = sound_pool4.load(this, R.raw.pop402, 1);
        pad1 = sound_pool4.load(this, R.raw.pop403, 1);
        brass1 = sound_pool4.load(this, R.raw.pop404, 1);
        strings2 = sound_pool4.load(this, R.raw.pop405, 1);
        bass2 = sound_pool4.load(this, R.raw.pop406,1);

        // 5번째 트랙
        strings3 = sound_pool5.load(this, R.raw.pop501, 1);
        pad2 = sound_pool5.load(this, R.raw.pop502, 1);
        synth4 = sound_pool5.load(this, R.raw.pop503, 1);
        synth5 = sound_pool5.load(this, R.raw.pop504, 1);
        brass2 = sound_pool5.load(this, R.raw.pop505, 1);
        bass3 = sound_pool5.load(this, R.raw.pop506, 1);

        // 6번째 트랙
        vox7 = sound_pool6.load(this, R.raw.pop601, 1);
        vox8 = sound_pool6.load(this, R.raw.pop602, 1);
        vox9 = sound_pool6.load(this, R.raw.pop603, 1);
        vox10 = sound_pool6.load(this, R.raw.pop604, 1);
        beat7 = sound_pool6.load(this, R.raw.pop605, 1);
        beat8 = sound_pool6.load(this, R.raw.pop606, 1);

        //7번 트랙
        fx1 = sound_pool78.load(this, R.raw.pop701, 1);
        fx2 = sound_pool78.load(this, R.raw.pop702, 1);
        fx3 = sound_pool78.load(this, R.raw.pop703, 1);
        fx4 = sound_pool78.load(this, R.raw.pop704, 1);
        fx5 = sound_pool78.load(this, R.raw.pop705, 1);
        fx6 = sound_pool78.load(this, R.raw.pop706, 1);

        //8번 트랙
        vox1 = sound_pool78.load(this, R.raw.pop801, 1);
        vox2 = sound_pool78.load(this, R.raw.pop802, 1);
        vox3 = sound_pool78.load(this, R.raw.pop803, 1);
        vox4 = sound_pool78.load(this, R.raw.pop804, 1);
        vox5 = sound_pool78.load(this, R.raw.pop805, 1);
        vox6 = sound_pool78.load(this, R.raw.pop806, 1);
    }
    //뷰 Gone
    public void visible_Gone() {
        beat1_on.setVisibility(View.GONE);
        beat2_on.setVisibility(View.GONE);
        beat3_on.setVisibility(View.GONE);
        beat4_on.setVisibility(View.GONE);
        beat5_on.setVisibility(View.GONE);
        beat6_on.setVisibility(View.GONE);

        two1_on.setVisibility(View.GONE);
        two2_on.setVisibility(View.GONE);
        two3_on.setVisibility(View.GONE);
        two4_on.setVisibility(View.GONE);
        two5_on.setVisibility(View.GONE);
        two6_on.setVisibility(View.GONE);

        three1_on.setVisibility(View.GONE);
        three2_on.setVisibility(View.GONE);
        three3_on.setVisibility(View.GONE);
        three4_on.setVisibility(View.GONE);
        three5_on.setVisibility(View.GONE);
        three6_on.setVisibility(View.GONE);

        four1_on.setVisibility(View.GONE);
        four2_on.setVisibility(View.GONE);
        four3_on.setVisibility(View.GONE);
        four4_on.setVisibility(View.GONE);
        four5_on.setVisibility(View.GONE);
        four6_on.setVisibility(View.GONE);

        five1_on.setVisibility(View.GONE);
        five2_on.setVisibility(View.GONE);
        five3_on.setVisibility(View.GONE);
        five4_on.setVisibility(View.GONE);
        five5_on.setVisibility(View.GONE);
        five6_on.setVisibility(View.GONE);

        six1_on.setVisibility(View.GONE);
        six2_on.setVisibility(View.GONE);
        six3_on.setVisibility(View.GONE);
        six4_on.setVisibility(View.GONE);
        six5_on.setVisibility(View.GONE);
        six6_on.setVisibility(View.GONE);

        seven1_on.setVisibility(View.GONE);
        seven2_on.setVisibility(View.GONE);
        seven3_on.setVisibility(View.GONE);
        seven4_on.setVisibility(View.GONE);
        seven5_on.setVisibility(View.GONE);
        seven6_on.setVisibility(View.GONE);

        eight1_on.setVisibility(View.GONE);
        eight2_on.setVisibility(View.GONE);
        eight3_on.setVisibility(View.GONE);
        eight4_on.setVisibility(View.GONE);
        eight5_on.setVisibility(View.GONE);
        eight6_on.setVisibility(View.GONE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 1:
                if(grantResults.length > 0){
                    if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "녹음 권한 동의함", Toast.LENGTH_SHORT).show();
                    } else if(grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(this, "녹음 권한 거부함", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(this, "녹음 권한 획득 실패", Toast.LENGTH_SHORT).show();
                }

        }
    }

    public void track78_stop() {
        sound_pool78.stop(fx1);
        sound_pool78.stop(fx2);
        sound_pool78.stop(fx3);
        sound_pool78.stop(fx4);
        sound_pool78.stop(fx5);
        sound_pool78.stop(fx6);
        sound_pool78.stop(vox1);
        sound_pool78.stop(vox2);
        sound_pool78.stop(vox3);
        sound_pool78.stop(vox4);
        sound_pool78.stop(vox5);
        sound_pool78.stop(vox6);
    }

    @Override
    public void onBackPressed() { //뒤로가기 버튼
        super.onBackPressed();
        track1_stop();
        track2_stop();
        track3_stop();
        track4_stop();
        track5_stop();
        track6_stop();
        track78_stop();
    }
    @Override
    protected void onUserLeaveHint() {		// 홈 버튼 감지
        super.onUserLeaveHint();
        track1_stop();
        track2_stop();
        track3_stop();
        track4_stop();
        track5_stop();
        track6_stop();
        track78_stop();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    //녹음
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 2000) {
            if (data != null) {
                Intent intent = new Intent(this, RecordingServiceClassic.class);
                intent.putExtra(RecordingServiceClassic.EXTRA_CODE, resultCode);
                intent.putExtra(RecordingServiceClassic.EXTRA_DATA, data);

                ContextCompat.startForegroundService(this, intent);
                record_btn.setImageResource(R.drawable.record_btn_on);
                isRecording = true;
                durationTime = System.currentTimeMillis();
            }
        }
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            final String action = intent.getAction();
            final long time = (System.currentTimeMillis() - durationTime)/1000;
            if (BROADCAST_WAVEFORM.equals(action) && intent.getExtras() != null) {
                final File file = (File) intent.getExtras().getSerializable(BROADCAST_EXTRA_DATA);

                if(file == null)
                    return;

                if (DEBUG){
                    LinearLayout layout = new LinearLayout(BeatMakingPopActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setPadding(30,10,30,5);
                    final TextView tvt = new TextView(BeatMakingPopActivity.this);
                    final EditText title = new EditText(BeatMakingPopActivity.this);
                    final TextView tvn = new TextView(BeatMakingPopActivity.this);
                    final EditText name = new EditText(BeatMakingPopActivity.this);
                    tvt.setText("비트 제목");
                    tvn.setText("작곡가");
                    layout.addView(tvt);
                    layout.addView(title);
                    layout.addView(tvn);
                    layout.addView(name);

                    final AlertDialog.Builder alt_bld = new AlertDialog.Builder(BeatMakingPopActivity.this,R.style.MyAlertDialogStyle);
                    alt_bld.setTitle("녹음이 완료되었습니다🎶")
                            .setMessage("정보를 입력해주세요")
                            .setIcon(R.drawable.doomchit_logo)
                            .setCancelable(false)
                            .setView(layout)
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String tstr = title.getText().toString();
                                    tstr.replace("_", "");
                                    String nstr = name.getText().toString();
                                    nstr.replace("_", "");
                                    String filename = System.currentTimeMillis() / 1000 +"_"+time+"_"+tstr+"_"+nstr+ ".mp3";
                                    file.renameTo(new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),filename));
                                    uploadFirebase(filename);
                                }
                            }).show();

                }
            }
        }
    };

    private static IntentFilter makeIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BROADCAST_WAVEFORM);
        return intentFilter;
    }

    public static byte[] fileToBytes(File file) {
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }
    private void uploadFirebase(String fileName){
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        Uri file = Uri.fromFile(new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)+"/"+fileName));
        StorageReference riversRef = storageRef.child("wavFiles/"+fileName);
        riversRef.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(), "저장되었습니다. 음원리스트에서 확인하세요~", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "음원 업로드 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
