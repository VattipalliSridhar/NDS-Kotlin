package com.apps.nemsapp.view.ui.java.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.nemsapp.databinding.NalaDailyInnerListAdapterLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class RecyclerDailyListInnerAdapterJava extends RecyclerView.Adapter<RecyclerDailyListInnerAdapterJava.ViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;
    private ArrayList<NalaDailyModel.Detail.FromTo> list = new ArrayList<>();

    public RecyclerDailyListInnerAdapterJava(Context context, ArrayList<NalaDailyModel.Detail.FromTo> list) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        NalaDailyInnerListAdapterLayoutBinding view = NalaDailyInnerListAdapterLayoutBinding.inflate(inflater, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.binding.nalaFromTxt.setText(list.get(position).getFrom());
        holder.binding.nalaToTxt.setText(list.get(position).getTo());

    }

    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final NalaDailyInnerListAdapterLayoutBinding binding;


        public ViewHolder(@NonNull NalaDailyInnerListAdapterLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
