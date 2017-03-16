package ir.apptune.coffechi;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.apptune.coffechi.adapters.WelcomeFragmentAdapter;
import me.relex.circleindicator.CircleIndicator;

public class Welcome extends AppCompatActivity {
   @BindView(R.id.view_pager_welcome) ViewPager pager;
    WelcomeFragmentAdapter welcomeFragmentAdapter;
    Boolean backPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wolcome);
        ButterKnife.bind(this);
        welcomeFragmentAdapter = new WelcomeFragmentAdapter(getSupportFragmentManager());
        pager.setAdapter(welcomeFragmentAdapter);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(pager);

    }

    @Override
    public void onBackPressed() {
        if (backPressed) {
            super.onBackPressed();
            return;
        }
        backPressed = true;
        Toast.makeText(this, R.string.press_back_again_to_quit, Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backPressed = false;
            }
        }, 2000);

    }
}
