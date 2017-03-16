package ir.apptune.coffechi.webServiceClass;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import ir.apptune.coffechi.models.ParamsWebserviceModel;


public class WebServiceClass {
    JSONObject res;

    public JSONObject getData(String url, ArrayList<ParamsWebserviceModel> params, String user, String pass) {
        RequestParams param = new RequestParams();
        SyncHttpClient client = new SyncHttpClient();
        if (params != null) {
            for (ParamsWebserviceModel model : params) {
                param.put(model.getKey(), model.getValue());
            }
        }
        if (user != null && pass != null) {
            client.setBasicAuth(user, pass);

        }

        client.setConnectTimeout(20000);
        client.setTimeout(20000);
        client.post(url, param, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                res = response;
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                res=null;
            }
        });

        return res;
    }
}

