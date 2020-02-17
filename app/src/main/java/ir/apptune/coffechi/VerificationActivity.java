package ir.apptune.coffechi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.apptune.coffechi.models.ParamsWebserviceModel;
import ir.apptune.coffechi.utilities.UtilityClass;
import ir.apptune.coffechi.webServiceClass.WebServiceClass;

import static ir.apptune.coffechi.models.ConstantsClass.PASS;
import static ir.apptune.coffechi.models.ConstantsClass.USER_NAME;
import static ir.apptune.coffechi.models.ConstantsClass.USER_PHONE_NUMBER;
import static ir.apptune.coffechi.models.ConstantsClass.VERIFICATION_URL;

public class VerificationActivity extends AppCompatActivity {

    @BindView(R.id.app_bar)
    Toolbar toolbar;
    String number;
    @BindView(R.id.edt_code)
    TextInputEditText edtCode;
    @BindView(R.id.btn_vrify)
    Button btnVerify;
    @BindView(R.id.edt_code_layout)
    TextInputLayout edtCodeLayout;
    String status;
    ProgressDialog pd;
    WebServiceClass webService;
    ArrayList<ParamsWebserviceModel> list;
    UtilityClass utility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        pd = new ProgressDialog(this);
        webService = new WebServiceClass();
        list = new ArrayList<>();
        utility = new UtilityClass();

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();

        }
        Intent intent = getIntent();
        number = intent.getStringExtra("number");

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utility.closeKeyBoard(VerificationActivity.this);
                if (edtCode.getText().toString().length() == 4) {
                    if (utility.isNetworkAvailable(VerificationActivity.this)) {
                        ParamsWebserviceModel model = new ParamsWebserviceModel("Phone", number);
                        ParamsWebserviceModel model1 = new ParamsWebserviceModel("VerificationCode", edtCode.getText().toString());
                        list.add(model);
                        list.add(model1);
                        checkCode(view);
                    } else {
                        Snackbar.make(view, R.string.please_check_your_connection, Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    edtCodeLayout.setError("Please Enter the code");
                }
            }
        });
    }

    public void checkCode(final View view) {
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
                return webService.getData(VERIFICATION_URL, list, USER_NAME, PASS);
            }

            @Override
            protected void onPostExecute(JSONObject response) {
                super.onPostExecute(response);
                pd.dismiss();
                if (response != null) {
                    try {
                        status = response.getString("Status");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (status.equals("true")) {
                        Toast.makeText(VerificationActivity.this, "Correct!", Toast.LENGTH_LONG).show();
                        PreferenceManager.getDefaultSharedPreferences(VerificationActivity.this).edit().putString(USER_PHONE_NUMBER,number).commit();
                        startActivity(new Intent(VerificationActivity.this,MainActivity.class));
                        finish();
                    } else {
                        edtCodeLayout.setError("Please Enter the code correctly");
                    }

                } else {
                    Snackbar.make(view, "Please try again", Snackbar.LENGTH_LONG).show();

                }


            }
        }.execute();


    }
}

