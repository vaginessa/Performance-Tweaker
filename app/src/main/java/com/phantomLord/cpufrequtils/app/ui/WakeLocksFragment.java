package com.phantomLord.cpufrequtils.app.ui;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.phantomLord.cpufrequtils.app.R;
import com.phantomLord.cpufrequtils.app.adapters.AlarmTriggerAdapter;
import com.phantomLord.cpufrequtils.app.adapters.CpuWakelocksAdapter;
import com.phantomLord.cpufrequtils.app.adapters.KernelWakelockAdapter;

public class WakeLocksFragment extends Fragment implements
        ActionBar.OnNavigationListener {

    ListView wakelockList;
    ActionBar actionBar;
    View view;
    Context context;
    TextView timeSince;
    BaseAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.wakelocksfragment, container, false);
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        context = getActivity().getBaseContext();
        actionBar.setTitle("");
        wakelockList = (ListView) view
                .findViewById(R.id.wakelock_data_listview1);
        timeSince = (TextView) view.findViewById(R.id.stats_since);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item,
                context.getResources().getStringArray(
                        R.array.wakelock_actionbar_spinner_items));
        actionBar.setListNavigationCallbacks(adapter, this);
        actionBar.setSelectedNavigationItem(0);
    }

    @Override
    public void onDestroyView() {
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        super.onDestroyView();
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        BaseAdapter adapter = null;

        switch (itemPosition) {
            case 0:
                adapter = new KernelWakelockAdapter(context);
                break;
            case 1:
                adapter = new CpuWakelocksAdapter(context);
                break;
            case 2:
                adapter = new AlarmTriggerAdapter(context);
                break;
        }
        if (adapter.getCount()>0 ) {
            wakelockList.setVisibility(View.VISIBLE);
            timeSince.setTextSize(15);
            wakelockList.setAdapter(adapter);
            timeSince.setText("");

        } else {
            wakelockList.setVisibility(View.GONE);
            timeSince.setTextSize(20);
            timeSince.setGravity(Gravity.CENTER);
            timeSince.setText(getString(R.string.stats_not_available));
        }
        return true;
    }
}
