package ir.apptune.coffechi;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.apptune.coffechi.models.InsideCoffeModel;
import ir.apptune.coffechi.models.ParamsWebserviceModel;
import ir.apptune.coffechi.utilities.UtilityClass;
import ir.apptune.coffechi.webServiceClass.WebServiceClass;

import static ir.apptune.coffechi.models.ConstantsClass.INSIDE_RESTAURANT_DATA_URL;
import static ir.apptune.coffechi.models.ConstantsClass.PASS;
import static ir.apptune.coffechi.models.ConstantsClass.TAG;
import static ir.apptune.coffechi.models.ConstantsClass.USER_NAME;

public class InsideCoffeeActivity extends AppCompatActivity {
    String id;
    String mainPhoto;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.slider)
    SliderLayout sliderShow;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.img_restaurant)
    ImageView imgRestaurant;
    InsideCoffeModel coffeModel;
    List<InsideCoffeModel.CafePhotoBean> sliderPhotoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_coffee);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        collapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT);
        collapsingToolbar.setCollapsedTitleTextColor(Color.TRANSPARENT);
        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        mainPhoto = intent.getStringExtra("MainPhoto");
        Picasso.with(this).load(mainPhoto).resize(100, 100).into(imgRestaurant);
        setJsonToView();

    }

    public void setJsonToView() {
        if (!new UtilityClass().isNetworkAvailable(this)) {
            showTryAgainAlert();
            return;
        }
        new AsyncTask<String, String, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... strings) {
                WebServiceClass webService = new WebServiceClass();
                ParamsWebserviceModel model = new ParamsWebserviceModel("ID", id);
                ArrayList<ParamsWebserviceModel> list = new ArrayList<>();
                list.add(model);
                JSONObject mainJson = webService.getData(INSIDE_RESTAURANT_DATA_URL, list, USER_NAME, PASS);
                if (mainJson != null && !mainJson.equals("")) {
                    return mainJson.toString();
                } else {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                if (s == null) {
                    showTryAgainAlert();
                    return;
                }
                super.onPostExecute(s);
                Gson gson = new Gson();
                coffeModel = gson.fromJson(s, InsideCoffeModel.class);
                sliderPhotoes = coffeModel.getCafePhoto();
                for (int i = 0; i < sliderPhotoes.size(); i++) {
                    TextSliderView textSliderView = new TextSliderView(InsideCoffeeActivity.this);
                    textSliderView
                            .description(sliderPhotoes.get(i).getName())
                            .image(sliderPhotoes.get(i).getPhoroUrl());
                    Log.d(TAG, "onPostExecute: " + sliderPhotoes.get(i).getPhoroUrl());
                    sliderShow.addSlider(textSliderView);
                }
            }
        }.execute();
    }

    public void showTryAgainAlert() {
        new AlertDialog.Builder(this).setMessage(getString(R.string.please_check_your_connection)).setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setJsonToView();
            }
        }).setCancelable(false).show();

    }


    @Override
    protected void onStop() {
        sliderShow.stopAutoCycle();
        super.onStop();
    }
}
