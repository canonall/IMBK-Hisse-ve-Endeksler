package com.canonal.imbkhissesenetleriveendeksler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.canonal.imbkhissesenetleriveendeksler.R;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;

    public StockAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.layoutInflater = LayoutInflater.from(context);
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_rv_stock, viewGroup, false);
        return new ViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StockAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvStockSymbolInfo;
        TextView tvStockPriceInfo;
        TextView tvStockDifferenceInfo;
        TextView tvStockVolumeInfo;
        TextView tvStockBuyInfo;
        TextView tvStockSellInfo;
        TextView tvStockFluctuationInfo;

        OnItemClickListener onItemClickListener;


        public ViewHolder(View itemView, OnItemClickListener onItemClickListener) {

            super(itemView);
            tvStockSymbolInfo = itemView.findViewById(R.id.tv_stock_symbol_info);
            tvStockPriceInfo = itemView.findViewById(R.id.tv_stock_price_info);
            tvStockDifferenceInfo = itemView.findViewById(R.id.tv_stock_difference_info);
            tvStockVolumeInfo = itemView.findViewById(R.id.tv_stock_volume_info);
            tvStockBuyInfo = itemView.findViewById(R.id.tv_stock_buy_info);
            tvStockSellInfo = itemView.findViewById(R.id.tv_stock_sell_info);
            tvStockFluctuationInfo = itemView.findViewById(R.id.tv_stock_fluctuation_info);

            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.OnItemClick(getAdapterPosition());
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }
}
