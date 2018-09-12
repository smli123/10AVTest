package com.example.record;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;


@SuppressWarnings("deprecation")
public class textRecord extends Activity { // implements OnClickListener {
   Button recordButton,stopButton,exitButton;
   SeekBar skbVolume;
   
   boolean isRecording = false;
   static final int frequency = 44100;
   static final int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
   static final int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
   
   int recBufSize, playBufSize;
   AudioRecord audioRecord;
   AudioTrack audioTrack;
   
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("助听器");
        
        //用getMinBufferSize()方法得到采集数据所需要的最小缓冲区的大小
        recBufSize = AudioRecord.getMinBufferSize(frequency, channelConfiguration, audioEncoding);
        playBufSize = AudioTrack.getMinBufferSize(frequency, channelConfiguration, audioEncoding);
        
        //实例化AudioRecord(声音来源，采样率，声道设置，采样声音编码，缓存大小）
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, frequency,channelConfiguration, audioEncoding, recBufSize);
        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,frequency, channelConfiguration, audioEncoding, playBufSize, AudioTrack.MODE_STREAM);
        
        recordButton =(Button)this.findViewById(R.id.recordButton);
        stopButton =(Button) this.findViewById(R.id.stopButton);
        exitButton =(Button)this.findViewById(R.id.exitButton);
        
        recordButton.setOnClickListener(new ClickEvent());
        stopButton.setOnClickListener(new ClickEvent());
        exitButton.setOnClickListener(new ClickEvent());
        
        skbVolume = (SeekBar)this.findViewById(R.id.sb_volume);
        skbVolume.setMax(100);
        skbVolume.setProgress(70);

        // 设置声音大小
        audioTrack.setStereoVolume(0.7f, 0.7f);
        skbVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

		   public void onStopTrackingTouch(SeekBar seekBar) {
				float vol = (float)(seekBar.getProgress())/(float)(seekBar.getMax());
				audioTrack.setStereoVolume(vol, vol);
		   }
		   
		   @Override
		   public void onStartTrackingTouch(SeekBar seekBar) {
		    
		   }
		   
		   @Override
		   public void onProgressChanged(SeekBar seekBar, int progress,
		     boolean fromUser) {
		    
		   }
		  });
    }
    
    protected void onDestroy(){
     	super.onDestroy();
		// 杀死当前进程
     	android.os.Process.killProcess(android.os.Process.myPid());
    }
    
    class ClickEvent implements View.OnClickListener{
	     public void onClick(View v){
	    	 if(v == recordButton){
	    		 isRecording = true;
	    		 new RecordPlayThread().start();
	    	 }else if(v == stopButton){
	    		 isRecording = false;
	    	 }else if(v== exitButton){
	    		 isRecording = false;
	    		 textRecord.this.finish();
	    	 }
	     }
    }
    
    class RecordPlayThread extends Thread{
    	public void run(){
    		try{
    			//byte 文件来存储声音
    			byte[] buffer = new byte[recBufSize];

    			//开始采集声音
    			audioRecord.startRecording();

    			//播放声音
    			audioTrack.play();
    			
    			while(isRecording){
    				//从MIC存储到缓存区
    				int bufferReadResult = audioRecord.read(buffer,0, recBufSize);
    				byte[] tmpBuf = new byte[bufferReadResult];
    				System.arraycopy(buffer, 0, tmpBuf, 0, bufferReadResult);
    				//播放缓存区的数据
    				audioTrack.write(tmpBuf, 0, tmpBuf.length);
    			}
    			
    			audioTrack.stop();
    			audioRecord.stop();
    		}catch(Throwable t){
    			Toast.makeText(textRecord.this, t.getMessage(), 1000);
    		}
    	}
    };
}