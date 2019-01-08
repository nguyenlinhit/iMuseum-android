package vn.edu.tdmu.imuseum.listener;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by nvulinh on 3/3/18.
 *
 */

public class TranslateAsyncTask extends AsyncTask<Void, Void,String> {
    private TaskCompleted callback;

    public TranslateAsyncTask(TaskCompleted callback){
        this.callback = callback;
    }


    @Override
    protected String doInBackground(Void... voids) {
        return translate("Hello World", "en","vi");
    }

    @Override
    protected void onPostExecute(String s) {
        callback.onTaskComplete(s);
    }


    private String translate(String text, String sourceLang, String targetLang) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20180303T065416Z.8b0cb060dea9b957.1fd0f6f00b48d12308ecace9dcdaa7febdbd5c09&text="+text+"&lang="+sourceLang+"-"+targetLang;

        Request request = new Request.Builder().url(url).build();
        try(Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}