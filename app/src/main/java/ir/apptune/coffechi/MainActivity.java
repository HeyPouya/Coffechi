package ir.apptune.coffechi;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import ir.apptune.coffechi.utilities.UtilityClass;
import ir.apptune.coffechi.webServiceClass.WebServiceClass;

import static ir.apptune.coffechi.models.ConstantsClass.MAIN_RESTAURANT_DATA_URL;
import static ir.apptune.coffechi.models.ConstantsClass.USER_PHONE_NUMBER;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.main_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.swipe_to_refresh)
    SwipeRefreshLayout swipeToRefresh;
    String userNumber;
    JSONArray responseList;
    MainRecyclerViewModel model;
    ArrayList<MainRecyclerViewModel> list;
    ProgressDialog pd;
    ArrayList<ParamsWebserviceModel> params;
    MainRecyclerViewAdapter adapter;
    UtilityClass utility;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        userNumber = PreferenceManager.getDefaultSharedPreferences(this).getString(USER_PHONE_NUMBER, "NA");
        if (userNumber.equals("NA")) {
            startActivity(new Intent(this, Welcome.class));
            finish();
        }

        swipeToRefresh.setOnRefreshListener(this);

        utility = new UtilityClass();
        list = new ArrayList<>();
        pd = new ProgressDialog(this);
        setSupportActionBar(toolbar);
        ParamsWebserviceModel paramsWebserviceModel = new ParamsWebserviceModel("PageID", "1");
        params = new ArrayList<>();
        params.add(paramsWebserviceModel);
        adapter = new MainRecyclerViewAdapter(this, new OnItemClickListener() {
            @Override
            public void OnClickItem(MainRecyclerViewModel model) {
                Toast.makeText(MainActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();
            }
        }, list);
        showData();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void showData() {
        if (utility.isNetworkAvailable(this)) {
            new AsyncTask<String, String, JSONObject>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    pd.setMessage(getString(R.string.please_wait));
                    pd.setCancelable(false);
                    pd.show();
                }

                @Override
                protected JSONObject doInBackground(String... strings) {
                    WebServiceClass webService = new WebServiceClass();
                    JSONObject response = webService.getData(MAIN_RESTAURANT_DATA_URL, params, null, null);
                    return response;
                }

                @Override
                protected void onPostExecute(JSONObject response) {
                    super.onPostExecute(response);
                    if (swipeToRefresh.isRefreshing()) {
                        swipeToRefresh.setRefreshing(false);
                    }
                    if (response != null) {
                        try {
                            responseList = response.getJSONArray("List");
                            for (int i = 0; i < responseList.length(); i++) {
                                model = new MainRecyclerViewModel();
                                JSONObject object = responseList.getJSONObject(i);
                                model.setRestaurantImage(object.getString("Photo"));
                                //  model.setDistance(object.getString(""));
                                model.setRestaurantName(object.getString("Title"));
                                model.setRestaurantRate(object.getString("Content"));
                                list.add(model);
                                pd.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        pd.dismiss();
                        showTryAgainAlert();
                    }

                }
            }.execute();
        } else {
            showTryAgainAlert();
        }

    }

    public void showTryAgainAlert() {
        new AlertDialog.Builder(this).setMessage(getString(R.string.please_check_your_connection)).setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                showData();
            }
        }).setCancelable(false).show();

    }

    @Override
    public void onRefresh() {
        showData();
    }
}
