package com.apps.nemsapp.view.ui.java.update;

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

import com.apps.nemsapp.databinding.ActivityNalaDailyUpdateBinding;
import com.apps.nemsapp.view.base.BaseActivity;
import com.apps.nemsapp.view.base.SharedPreferConstant;
import com.apps.nemsapp.view.ui.activities.MainActivity;
import com.apps.nemsapp.view.ui.java.view.NalaDailyModel;

import java.util.ArrayList;

public class NalaDailyUpdateActivity extends BaseActivity {

    private ActivityNalaDailyUpdateBinding binding;
    private RecyclerDailyListInnerAdapterUpdate adapterUpdate;
    private ArrayList<NalaDailyModel.Detail.FromTo> list = new ArrayList<>();

    private NalaDailyUpdateViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNalaDailyUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = ViewModelProviders.of(this).get(NalaDailyUpdateViewModel.class);
        updateView();

        createLocationCallback();
        createLocationRequest();
        buildLocationSettingsRequest();
        startLocationUpdates();
        updateUI();

        binding.formToRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.formToRecyclerview.setHasFixedSize(true);
        adapterUpdate = new RecyclerDailyListInnerAdapterUpdate(this, list);
        binding.formToRecyclerview.setAdapter(adapterUpdate);


        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewModel.getDailyUpdateStatus().observe(this, new Observer<NalaDailyUpdateModel>() {
            @Override
            public void onChanged(NalaDailyUpdateModel nalaDailyUpdateModel) {

                if (nalaDailyUpdateModel.getStatusCode().equalsIgnoreCase("200")) {
                    showPopupSuccess(nalaDailyUpdateModel.getStatusMessage());
                } else {
                    showPopupError(nalaDailyUpdateModel.getStatusMessage());
                }
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

        binding.formSubBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                if (validation()) {
                    showPopupConform();
                }
            }
        });
    }

    private void showPopupConform() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Do you want submit ?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                        showDialog();

                        viewModel.getUpdateDailyData(
                                getPreferLogin(SharedPreferConstant.user_id),
                                getPreferLogin(SharedPreferConstant.ulbId),
                                getPreferLogin(SharedPreferConstant.ward_id),
                                getPreferLogin(SharedPreferConstant.circle_id),
                                getVersionName(),
                                getPreference(SharedPreferConstant.nalaStatus),
                                getPreferLogin(SharedPreferConstant.id),
                                getPreferLogin(SharedPreferConstant.zone_id),
                                binding.totQtyDoneTodayEdit.getText().toString().trim(),
                                binding.totLengthDoneTodayEdit.getText().toString().trim(),
                                binding.totWidthDoneTodayEdit.getText().toString().trim(),
                                binding.totDepthDoneTodayEdit.getText().toString().trim(),
                                getPreference(SharedPreferConstant.Workid),
                                String.valueOf(getPreference(SharedPreferConstant.total_qty)),
                                getDate(),
                                getPreferLogin(SharedPreferConstant.LATTITUDE),
                                getPreferLogin(SharedPreferConstant.LONGITUDE),
                                NalaDailyUpdateActivity.this

                        );

                    }
                });
        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public String getVersionName() {
        String packagename = "";
        PackageManager packageManager = this.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(this.getPackageName(), 0);
            packagename = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packagename;
    }

    private boolean validation() {
        if (binding.totQtyDoneTodayEdit.getText().toString().trim().isEmpty()) {
            binding.totQtyDoneTodayEdit.requestFocus();
            snakeBarView(binding.totQtyDoneTodayEdit, "Enter Total Qty of Desilting done today");
            return false;
        }
        if (binding.totLengthDoneTodayEdit.getText().toString().trim().isEmpty()) {
            binding.totLengthDoneTodayEdit.requestFocus();
            snakeBarView(binding.totLengthDoneTodayEdit, "Enter Total Length completed today");
            return false;
        }
        if (binding.totWidthDoneTodayEdit.getText().toString().trim().isEmpty()) {
            binding.totWidthDoneTodayEdit.requestFocus();
            snakeBarView(binding.totWidthDoneTodayEdit, "Enter Total Width completed today");
            return false;
        }
        if (binding.totDepthDoneTodayEdit.getText().toString().trim().isEmpty()) {
            binding.totDepthDoneTodayEdit.requestFocus();
            snakeBarView(binding.totDepthDoneTodayEdit, "Enter Total Width completed today");
            return false;
        }
        return true;
    }

    private void updateView() {
        list.clear();
        Intent mIntent = getIntent();
        list.addAll(mIntent.getParcelableArrayListExtra("list"));
        Log.e("size", String.valueOf(list));

        binding.nallaNameText.setText(getPreference(SharedPreferConstant.Workno));
        binding.totQtyToBeDoneTxt.setText(getPreference(SharedPreferConstant.total_qty));
        binding.totQtyDoneYesterTxt.setText(getPreference(SharedPreferConstant.today_total_qty));
        binding.totLengthDoneYesterTxt.setText(getPreference(SharedPreferConstant.total_length_today));
        binding.totWidthDoneYesterTxt.setText(getPreference(SharedPreferConstant.total_width_today));
        binding.totDepthDoneYesterTxt.setText(getPreference(SharedPreferConstant.total_depth_today));
        binding.nalaSeekBar.setMax(100);
        double progress = Double.parseDouble(getPreference(SharedPreferConstant.nalaProgerssValue));
        binding.nalaSeekBar.setProgress((int) progress);
        binding.nalaPercentageTxt.setText(String.valueOf(progress) + "%");
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

    private void showPopupSuccess(String msg) {

        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(msg);
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            arg0.dismiss();
                            resetForm();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    private void resetForm() {
        clearPreferences();
        getViewModelStore().clear();
        setPreferLogin(SharedPreferConstant.LATTITUDE, "");
        setPreferLogin(SharedPreferConstant.LONGITUDE, "");
        Intent intent = new Intent(NalaDailyUpdateActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

}