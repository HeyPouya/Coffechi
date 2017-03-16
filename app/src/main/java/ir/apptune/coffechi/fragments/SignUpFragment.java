package ir.apptune.coffechi.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.apptune.coffechi.R;
import ir.apptune.coffechi.utilities.UtilityClass;
import ir.apptune.coffechi.VerificationActivity;
import ir.apptune.coffechi.webServiceClass.WebServiceClass;
import ir.apptune.coffechi.models.ParamsWebserviceModel;

import static ir.apptune.coffechi.models.ConstantsClass.PASS;
import static ir.apptune.coffechi.models.ConstantsClass.SIGN_UP_URL;
import static ir.apptune.coffechi.models.ConstantsClass.USER_NAME;

public class SignUpFragment extends Fragment {
    @BindView(R.id.edt_number)
    EditText edtNumber;
    @BindView(R.id.btn_get_number)
    Button btnGetNumber;
    @BindView(R.id.txt_signup_error)
    TextView txtSignUpError;
    @BindView(R.id.edt_number_layout)
    TextInputLayout edtNumberLayout;
    String number;
    ProgressDialog pd;
    String type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pd = new ProgressDialog(getActivity());


        btnGetNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number = edtNumber.getText().toString();
                UtilityClass utility = new UtilityClass();
                if (number.length() < 11 || number.length() == 0) {
                    edtNumberLayout.setError(getString(R.string.please_check_your_number));
                    return;
                }
                utility.closeKeyBoard(getActivity());
                if (utility.isNetworkAvailable(getActivity())) {

                    new AsyncTask<Void, Void, String>() {

                        @Override
                        protected void onPreExecute() {
                            pd.setMessage(getString(R.string.please_wait));
                            pd.show();
                            pd.setCancelable(false);
                            super.onPreExecute();
                        }

                        @Override
                        protected String doInBackground(Void... voids) {
                            WebServiceClass registeringClass = new WebServiceClass();
                            ArrayList<ParamsWebserviceModel> list = new ArrayList<>();
                            ParamsWebserviceModel model = new ParamsWebserviceModel("Phone", number);
                            list.add(model);
                            JSONObject res = registeringClass.getData(SIGN_UP_URL, list, USER_NAME, PASS);
                            if (res != null) {
                                try {
                                    type = res.getString("StatusNumber");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            return type;


                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            if (s != null) {
                                switch (Integer.parseInt(s)) {
                                    case 1:
                                        pd.dismiss();
                                        Intent intent = new Intent(getActivity(), VerificationActivity.class);
                                        intent.putExtra("number", number);
                                        startActivity(intent);
                                        break;
                                    case 2:
                                        pd.dismiss();
                                        Intent intent1 = new Intent(getActivity(), VerificationActivity.class);
                                        intent1.putExtra("number", number);
                                        startActivity(intent1);
                                        break;
                                    case 3:
                                        pd.dismiss();
                                        txtSignUpError.setText("SomeThing Strange Happend! Please Try Again");
                                        break;
                                    case 4:
                                        pd.dismiss();
                                        txtSignUpError.setText("We Have Just Sent u a sms. PLease try again in 1 Minute");
                                        break;
                                    case 5:
                                        pd.dismiss();
                                        txtSignUpError.setText("You Have registered already! Please Log In.");
                                        break;
                                    case 0:
                                        pd.dismiss();
                                        txtSignUpError.setText("SomeThing Strange Happend! Please Try Again");
                                        break;
                                }
                            } else {
                                pd.dismiss();
                                txtSignUpError.setText("SomeThing Strange Happend! Please Try Again");
                                return;
                            }

                        }
                    }.execute();
                } else {
                    Snackbar.make(view, R.string.please_check_your_connection, Snackbar.LENGTH_LONG).show();
                }


            }
        });
    }

}