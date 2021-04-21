package com.apps.nemsapp.view.ui.java.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.nemsapp.databinding.NalaDailyListAdapterLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class RecyclerDailyListAdapterJava extends RecyclerView.Adapter<RecyclerDailyListAdapterJava.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<NalaDailyModel.Detail> list = new ArrayList<>();
    private RecyclerDailyListInnerAdapterJava recyclerInnerAdapter;
    private NalaDailyListActivityJava nalaDailyListActivityJava;

    public RecyclerDailyListAdapterJava(Context context, ArrayList<NalaDailyModel.Detail> list,
                                        NalaDailyListActivityJava nalaDailyListActivityJava) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        this.nalaDailyListActivityJava = nalaDailyListActivityJava;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        NalaDailyListAdapterLayoutBinding view = NalaDailyListAdapterLayoutBinding.inflate(inflater, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        NalaDailyModel.Detail model = list.get(position);

        holder.adapterBinding.nallaNameText.setText(model.getWorkno());
        holder.adapterBinding.statusText.setText(model.getStatusDescription());
        holder.adapterBinding.nalaLocationTxt.setText(model.getNallaLocation());

        holder.adapterBinding.formToRecyclerview.setLayoutManager(new LinearLayoutManager(context));
        holder.adapterBinding.formToRecyclerview.setHasFixedSize(true);
        recyclerInnerAdapter = new RecyclerDailyListInnerAdapterJava(context, model.getFromTo());
        holder.adapterBinding.formToRecyclerview.setAdapter(recyclerInnerAdapter);

      /*  if (model.getStatus() != null && model.getStatus() == 3) {
            holder.adapterBinding.updateButton.setText("" + model.getStatusDescription());
        } else {
            holder.adapterBinding.updateButton.setText("" + model.getStatusDescription());
            holder.adapterBinding.updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nalaDailyListActivityJava.updateData(position);
                }
            });
        }*/
       // holder.adapterBinding.updateButton.setText("" + model.getStatusDescription());
        holder.adapterBinding.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nalaDailyListActivityJava.updateData(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private NalaDailyListAdapterLayoutBinding adapterBinding;

        public ViewHolder(@NonNull NalaDailyListAdapterLayoutBinding adapterBinding) {
            super(adapterBinding.getRoot());
            this.adapterBinding = adapterBinding;
        }
    }
}
