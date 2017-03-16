package ir.apptune.coffechi;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.apptune.coffechi.adapters.MainRecyclerViewAdapter;
import ir.apptune.coffechi.models.MainRecyclerViewModel;
import ir.apptune.coffechi.models.ParamsWebserviceModel;
import ir.apptune.coffechi.webServiceClass.WebServiceClass;

import static ir.apptune.coffechi.models.ConstantsClass.*;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.main_recycler_view)
    RecyclerView recyclerView;
    String userNumber;
    JSONArray responseList;
    MainRecyclerViewModel model;
    ArrayList<MainRecyclerViewModel> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        userNumber = PreferenceManager.getDefaultSharedPreferences(this).getString(USER_PHONE_NUMBER, "NA");
        list = new ArrayList<>();
        model = new MainRecyclerViewModel();
        if (userNumber.equals("NA")) {
            startActivity(new Intent(this, Welcome.class));
            finish();
        }
        WebServiceClass webService = new WebServiceClass();
        ParamsWebserviceModel paramsWebserviceModel = new ParamsWebserviceModel("PageID", "1");
        ArrayList<ParamsWebserviceModel> params = new ArrayList<>();
        params.add(paramsWebserviceModel);
        JSONObject response = webService.getData(MAIN_RESTAURANT_DATA_URL, params, USER_NAME, PASS);

        try {
            responseList = response.getJSONArray("List");
            for (int i = 0; i < responseList.length(); i++) {
                JSONObject object = responseList.getJSONObject(i);
                model.setRestaurantImage(object.getString("Photo"));
                //  model.setDistance(object.getString(""));
                model.setRestaurantName(object.getString("Title"));
                model.setRestaurantRate(object.getString("Content"));
                list.add(model);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainRecyclerViewAdapter adapter = new MainRecyclerViewAdapter(this,new OnItemClickListener() {
            @Override
            public void OnClickItem(MainRecyclerViewModel model) {
                Toast.makeText(MainActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();
            }
        },list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

}
