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
import static ir.apptune.coffechi.models.ConstantsClass.PASS;
import static ir.apptune.coffechi.models.ConstantsClass.USER_NAME;
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
    private int visibleThreshold = 2;
    private int lastVisibleItem, totalItemCount;
    private boolean loading = false;
    private OnLoadMoreListener onLoadMoreListener;
    int counter = 1;
    int totalItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userNumber = PreferenceManager.getDefaultSharedPreferences(this).getString(USER_PHONE_NUMBER, "NA");
        if (userNumber.equals("NA")) {
            startActivity(new Intent(this, Welcome.class));
            finish();
        }
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        onLoadMoreListener = new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                showData(counter);
            }
        };


        swipeToRefresh.setOnRefreshListener(this);

        utility = new UtilityClass();
        list = new ArrayList<>();
        pd = new ProgressDialog(this);
        setSupportActionBar(toolbar);
        adapter = new MainRecyclerViewAdapter(this, new OnItemClickListener() {
            @Override
            public void OnClickItem(MainRecyclerViewModel model) {
                Intent intent = new Intent(MainActivity.this, InsideCoffeeActivity.class);
                intent.putExtra("ID", model.getId());
                intent.putExtra("MainPhoto", model.getRestaurantImage());
                startActivity(intent);
            }
        }, list);
        showData(counter);
        recyclerView.setAdapter(adapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = layoutManager.getItemCount();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold) && totalItemCount < totalItems) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                        loading = true;
                    }
                }
            }
        });

    }

    public void showData(final int PageCount) {
        if (utility.isNetworkAvailable(this)) {
            new AsyncTask<String, String, JSONObject>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    pd.setMessage(getString(R.string.please_wait));
                    pd.setCancelable(false);
                    pd.show();
                    ParamsWebserviceModel paramsWebserviceModel = new ParamsWebserviceModel("PageID", String.valueOf(PageCount));
                    params = new ArrayList<>();
                    params.add(paramsWebserviceModel);
                }

                @Override
                protected JSONObject doInBackground(String... strings) {
                    WebServiceClass webService = new WebServiceClass();
                    JSONObject response = webService.getData(MAIN_RESTAURANT_DATA_URL, params, USER_NAME, PASS);
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
                            counter++;
                            loading = false;
                            responseList = response.getJSONArray("List");
                            totalItems = Integer.parseInt(response.getString("TotalResponseCount"));
                            for (int i = 0; i < responseList.length(); i++) {
                                model = new MainRecyclerViewModel();
                                JSONObject object = responseList.getJSONObject(i);
                                model.setRestaurantImage(object.getString("Photo"));
                                //  model.setDistance(object.getString(""));
                                model.setId(object.getString("ID"));
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
                        dismissProgressDialog();
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
                showData(counter);
            }
        }).setCancelable(false).show();

    }

    @Override
    public void onRefresh() {
        list.clear();
        counter = 1;
        showData(counter);
        loading = false;
    }

    private void dismissProgressDialog() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }


}
