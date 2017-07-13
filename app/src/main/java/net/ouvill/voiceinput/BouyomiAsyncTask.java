package net.ouvill.voiceinput;

import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by youhei on 2017/01/22.
 */
public class BouyomiAsyncTask extends AsyncTask<Object, Object, Void> {
    ArrayList<String> results;

    BouyomiAsyncTask( ArrayList<String> results) {
        this.results = results;
    }

    @Override
    protected Void doInBackground(Object... params) {
        BouyomiChan4J bouyomi = new BouyomiChan4J("192.168.3.112",50001);
        bouyomi.talk(results.get(0));

        return null;
    }
}
