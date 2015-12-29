package br.com.tvglobo.proximity;

import android.os.AsyncTask;

public class AsyncTaskSound extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
        while(true)
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPostExecute(String result) {
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }
}