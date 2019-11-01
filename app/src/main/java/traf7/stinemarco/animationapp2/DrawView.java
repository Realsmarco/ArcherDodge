package traf7.stinemarco.animationapp2;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DrawView extends View {
    Paint paint=new Paint();
    Sprite sprite = new Sprite();
    Sprite s1,s2,s3,s4,s5;
    ArrayList<Sprite> spri;
    int score;
    Canvas c;
    private static final int MAX_STREAMS=100;
    private int soundIdExplosion;
    private int soundIdBackground;
    private boolean soundPoolLoaded;
    private SoundPool soundPool;
Color cc;
    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //note, at this point, getWidth() and getHeight() will have access the the dimensions
        spri=new ArrayList<Sprite>();
        spri.add(generateSprite());
        spri.add(generateSprite());
        spri.add(generateSprite());
        spri.add(generateSprite());
        spri.add(generateSprite());



        sprite.grow(100);
        sprite.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.capture));
        initSoundPool();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        c=canvas;
        super.onDraw(canvas);
        paint.setColor(Color.GRAY);//set paint to gray
        canvas.drawRect(getLeft(),0,getRight(),getBottom(),paint);//paint background gray
        paint.setColor(Color.RED);//set paint to red
        //sprite updates itself
        sprite.update(canvas);
        for(Sprite s:spri)
        {
            s.update(canvas);
        }


        if(sprite.right<0) {
            sprite.grow(100);
score--;
        }
        for(Sprite s:spri) {
            if(RectF.intersects(sprite, s)){
                playSoundExplosion();
                sprite.grow(-5);
            }
        }



        //sprite draws itself
        sprite.draw(canvas);
        for(Sprite s:spri)
        {
            s.draw(canvas);
        }

        invalidate();  //redraws screen, invokes onDraw()
    }
    private Sprite generateSprite(){
        float x = (float)(Math.random()*(getWidth()-.1*getWidth()));
        float y = (float)(Math.random()*(getHeight()-.1*getHeight()));
        int dX = (int)(Math.random()*11-5);
        int dY = (int)(Math.random()*11-5);
        return new Sprite(x,y,x+.1f*getWidth(),y+.1f*getWidth(),dX,dY,Color.MAGENTA);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            if(sprite.contains(event.getX(),event.getY())){
                score++;
            }
        }
        return true;
    }
    private void initSoundPool()  {
        // With Android API >= 21.
        if (Build.VERSION.SDK_INT >= 21 ) {
            AudioAttributes audioAttrib = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            SoundPool.Builder builder= new SoundPool.Builder();
            builder.setAudioAttributes(audioAttrib).setMaxStreams(MAX_STREAMS);
            this.soundPool = builder.build();
        }
        // With Android API < 21
        else {
            // SoundPool(int maxStreams, int streamType, int srcQuality)
            this.soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        }
        // When SoundPool load complete.
        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPoolLoaded = true;
                // Playing background sound.
                playSoundBackground();
            }
        });
        // Load the sound background.mp3 into SoundPool
        soundIdBackground= soundPool.load(this.getContext(), R.raw.background,1);
        // Load the sound explosion.wav into SoundPool
        soundIdExplosion = soundPool.load(this.getContext(), R.raw.explosion,1);
    }
    public void playSoundExplosion()  {
        if(soundPoolLoaded) {
            float leftVolumn = 0.8f;
            float rightVolumn =  0.8f;
            // Play sound explosion.wav
            int streamId = this.soundPool.play(this.soundIdExplosion,leftVolumn, rightVolumn, 1, 0, 1f);
        }
    }
    public void playSoundBackground()  {
        if(soundPoolLoaded) {
            float leftVolumn = 0.8f;
            float rightVolumn =  0.8f;
            // Play sound background.mp3
            int streamId = this.soundPool.play(this.soundIdBackground,leftVolumn, rightVolumn, 1, -1, 1f);
        }
    }
}