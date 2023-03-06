package com.xuhuawei.linkedadapter;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xuhuawei.linkedadapter.base.LifeCircleContext;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LifeCircleContext {
    private ListView listView;
    MyLinkedList<LinkedBean> linkedList;
    private LinkedAdapter adapter;
    private List<LinkedBean> arrayList;
    private boolean isLinkedMode = false;

    private long totalTime = 0;
    private int invokeNum = 0;

    private static final int MAX_ARRAY_SIZE = 100000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linkedList = new MyLinkedList<LinkedBean>();
        arrayList = new ArrayList<>();


        listView = findViewById(R.id.listView);

        long lastTime = System.currentTimeMillis();
        if (isLinkedMode) {
            for (int i = 0; i < MAX_ARRAY_SIZE; i++) {
                LinkedBean bean = new LinkedBean(i);
                linkedList.addLast(bean);
            }
            long currentTime = System.currentTimeMillis();
//            Log.e("xhw", "requestService takeTime=" + (currentTime - lastTime));
        } else {
            long currentTime = System.currentTimeMillis();
            for (int i = 0; i < MAX_ARRAY_SIZE; i++) {
                LinkedBean bean = new LinkedBean(i);
                arrayList.add(bean);
            }
            long currentTime2 = System.currentTimeMillis();
//            Log.e("xhw", "requestService takeTime=" + (currentTime2 - currentTime));
        }

        if (isLinkedMode) {
            adapter = new LinkedAdapter(this, linkedList);
        } else {
            adapter = new LinkedAdapter(this, arrayList);
        }
        adapter.setListView(listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < 100; i++) {
                    long lastTime = System.currentTimeMillis();
                    if (isLinkedMode) {
                        linkedList.remove(position);
                    } else {
                        arrayList.remove(position);
                    }
                    long currentTime = System.currentTimeMillis();
                    long itemTime = currentTime - lastTime;
                    totalTime += itemTime;
                    invokeNum++;
                }
                    Log.e("xhw",
                            "delete=" + position + ",avenageTime=" + (totalTime * 1.0f / invokeNum) + "," +
                                    "invokeNum=" + invokeNum);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isLinkedMode) {
            linkedList.clear();
        } else {
            arrayList.clear();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }
}