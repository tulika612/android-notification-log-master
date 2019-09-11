package org.hcilab.projects.nlogx;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.hcilab.projects.nlogx.misc.db_handler;
import org.hcilab.projects.nlogx.ui.not;

import java.util.ArrayList;
import java.util.Iterator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class notif extends AppCompatActivity {

    ListView listView;
    TextView textView;
    String listitem;
    private static final String LOG="pp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);
        listView=(ListView)findViewById(R.id.listView);
        textView=(TextView)findViewById(R.id.textView);
        db_handler dh = new db_handler(this);
        ArrayList<not> list=dh.get_list();
        ArrayList<String> st=new ArrayList<String>();
        Iterator it = list.iterator();
        int total=0;
        //Log.v(LOG,"hello"+list.toString());
       while (it.hasNext())
        {
            not n = (not) it.next();
            String l = n.getCount()+" notification from "+n.getApp();
            total=total+n.getCount();
            st.add(l);
            //textView.setText(l);
            Log.v(LOG,l);
        }
        String last = "Total count: "+total;
       st.add(last);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, st);
        listView.setAdapter(adapter);

    }
}
