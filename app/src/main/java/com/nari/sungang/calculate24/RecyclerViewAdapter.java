package com.nari.sungang.calculate24;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sungang on 2016/1/3.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.PokerViewHolder> {

    private static int POKER_NUM = 4;
    private int[] pokers;
    private Context context;


    public RecyclerViewAdapter(int[] pokers, Context context) {
        this.pokers = pokers;
        this.context = context;
    }


    static class PokerViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView appCompatImageView;

        public PokerViewHolder(final View itemView) {
            super(itemView);
            appCompatImageView = (AppCompatImageView) itemView.findViewById(R.id.poker);
        }
    }

    @Override
    public PokerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.poker_item, parent, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(parent.getMeasuredWidth() / 4, //width
                ViewGroup.LayoutParams.WRAP_CONTENT);//height
        view.setLayoutParams(lp);
        PokerViewHolder pokerViewHolder = new PokerViewHolder(view);
        return pokerViewHolder;
    }

    @Override
    public void onBindViewHolder(PokerViewHolder holder, final int position) {
        holder.appCompatImageView.setImageResource(pokers[position]);
    }

    @Override
    public int getItemCount() {
        return POKER_NUM;
    }

    public void resetPokers(int[] pokers) {
        this.pokers = pokers;
        notifyDataSetChanged();
    }
}
