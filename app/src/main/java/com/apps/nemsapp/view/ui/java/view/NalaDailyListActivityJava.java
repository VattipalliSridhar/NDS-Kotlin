package com.apps.nemsapp.view.ui.java.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.apps.nemsapp.databinding.ActivityNalaDailyListBinding;
import com.apps.nemsapp.model.NalaListModel;
import com.apps.nemsapp.view.base.BaseActivity;
import com.apps.nemsapp.view.base.SharedPreferConstant;
import com.apps.nemsapp.view.ui.java.update.NalaDailyUpdateActivity;

import java.util.ArrayList;
import java.util.List;

public class NalaDailyListActivityJava extends BaseActivity {

    private ActivityNalaDailyListBinding binding;
    private RecyclerDailyListAdapterJava adapter;
    private NalaDailyListViewModel viewModel;
    private ArrayList<NalaDailyModel.Detail> detailList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNalaDailyListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = ViewModelProviders.of(this).get(NalaDailyListViewModel.class);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerview.setHasFixedSize(true);
        adapter = new RecyclerDailyListAdapterJava(this, detailList, this);
        binding.recyclerview.setAdapter(adapter);

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewModel.getDailyList().observe(this, new Observer<NalaDailyModel>() {
            @Override
            public void onChanged(NalaDailyModel nalaDailyModel) {
                if (nalaDailyModel.getDetails() != null && nalaDailyModel.getDetails().size() > 0) {
                    detailList.clear();
                    detailList.addAll(nalaDailyModel.getDetails());
                }
                adapter.notifyDataSetChanged();
                dismissDialog();
            }
        });
        viewModel.getMessageToShow().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dismissDialog();
                showPopupError(s);
            }
        });

        if (isNetworkAvailable()) {
             getDailyNalaData();
        } else {
            showToastMessage("Check your internet connection");
        }

    }

    private void getDailyNalaData() {
        showDialog();
        viewModel.getDailyData(getPreferLogin(SharedPreferConstant.user_id),
                getPreferLogin(SharedPreferConstant.ulbId),
                getPreferLogin(SharedPreferConstant.ward_id),
                getPreferLogin(SharedPreferConstant.circle_id),
                getVersionName(),
                NalaDailyListActivityJava.this
                );
    }

    public void updateData(int position) {
        hideKeyboard();
        Intent intent = new Intent(this, NalaDailyUpdateActivity.class);
        setPreference(SharedPreferConstant.nalaArray,detailList.get(position).getFromTo().toString());
        setPreference(SharedPreferConstant.total_qty,String.valueOf(detailList.get(position).getTotalQty()));
        setPreference(SharedPreferConstant.today_total_qty,detailList.get(position).getTodayTotalQty());
        setPreference(SharedPreferConstant.total_length_today,detailList.get(position).getTotalLengthToday());
        setPreference(SharedPreferConstant.total_width_today,detailList.get(position).getTotalWidthToday());
        setPreference(SharedPreferConstant.total_depth_today,detailList.get(position).getTotalDepthToday());
        setPreference(SharedPreferConstant.nalaProgerssValue,detailList.get(position).getNallaProgress());
        setPreference(SharedPreferConstant.Workid,String.valueOf(detailList.get(position).getWorkid()));
        setPreference(SharedPreferConstant.Workno,detailList.get(position).getWorkno());
        setPreference(SharedPreferConstant.nalaStatus, String.valueOf(detailList.get(position).getStatus()));
        intent.putParcelableArrayListExtra("list", detailList.get(position).getFromTo());
        Log.e("tot",String.valueOf(detailList.get(position).getTotalQty()));
        startActivity(intent);
    }

    private void showPopupError(String msg) {

        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(msg);
            alertDialogBuilder.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            arg0.dismiss();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public String getVersionName(){
        String packagename="";
       PackageManager packageManager = this.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo =  packageManager.getPackageInfo(this.getPackageName(),0);
            packagename = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packagename;
    }
}