package traf7.stinemarco.animationapp2;

import androidx.appcompat.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    DrawView drawView;
    TextView top;
    Button grow;
    SeekBar seek;
    Button add;
    Button remove;
    RatingBar rate;
    Button blue,red;
    int cc=Color.MAGENTA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        drawView=findViewById(R.id.drawView);
        blue=findViewById(R.id.blue);
        red=findViewById(R.id.red);
        add=findViewById(R.id.add);
top=findViewById(R.id.welcomeText);
grow=findViewById(R.id.button);

remove=findViewById(R.id.remove);
grow.setText("Grow");
seek=findViewById(R.id.s);


blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              for(Sprite s:drawView.spri)
                  s.setColor(Color.BLUE);
                cc=Color.BLUE;
            }
        });
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Sprite s:drawView.spri)
                    s.setColor(Color.RED);
                cc=Color.RED;
            }
        });
add.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        float x = (float)(Math.random()*(drawView.getWidth()-.1*drawView.getWidth()));
        float y = (float)(Math.random()*(drawView.getHeight()-.1*drawView.getHeight()));
        int dX = (int)(Math.random()*11-5);
        int dY = (int)(Math.random()*11-5);

        drawView.spri.add(new Sprite(x,y,x+.1f*drawView.getWidth(),y+.1f*drawView.getWidth(),dX,dY,cc));
    }
});
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawView.spri.size()>0)
              drawView.spri.remove(0);

            }
        });
seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        for(Sprite s:drawView.spri)
        {
            s.dX+=progress;
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
});
grow.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        drawView.sprite.grow(100);
        drawView.score=0;
        drawView.sprite.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.capture));
        setColor();
    }
});

    }

    public void moveLeft(View view) {
//top.setText(top.getText()+""+drawView.score);
        drawView.sprite.setdX(-3);//set horizontal speed to move left
    }

    public void moveRight(View view) {
     //   top.setText(top.getText()+""+drawView.score);
        drawView.sprite.setdX(3);//set horizontal speed to move right
    }
    public void redCheckBoxClicked(View view) {
        setColor();
    }

    public void greenCheckBoxClicked(View view) {
        setColor();
    }
    public void setColor(){
        CheckBox greenCheckBox = findViewById(R.id.greenCheckBox);
        CheckBox redCheckBox = findViewById(R.id.redCheckBox);
        if(redCheckBox.isChecked()){
            if(greenCheckBox.isChecked())
                drawView.sprite.setColor(Color.YELLOW);
            else drawView.sprite.setColor(Color.RED);
        }else if(greenCheckBox.isChecked())
            drawView.sprite.setColor(Color.GREEN);
        else drawView.sprite.setColor(Color.BLUE);
    }
    public void sendMessage(View view)
    {
        Intent intent = new Intent(this, Next2Activity.class);


        startActivity(intent);
    }

}