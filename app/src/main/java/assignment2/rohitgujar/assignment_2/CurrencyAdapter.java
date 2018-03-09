package assignment2.rohitgujar.assignment_2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Rohit Gujar on 07-03-2018.
 */
public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.MyViewHolder> {

    private List<Currency> currencyList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCurrencyDenomination, tvCurrencyCount, tvCurrencyTotal;


        public MyViewHolder(View view) {
            super(view);
            tvCurrencyDenomination = (TextView) view.findViewById(R.id.tvCurrencyDenomination);
            tvCurrencyCount = (TextView) view.findViewById(R.id.tvCurrencyCount);
            tvCurrencyTotal = (TextView) view.findViewById(R.id.tvCurrencyTotal);
        }
    }


    public CurrencyAdapter(List<Currency> currencyList) {
        this.currencyList = currencyList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.each_currency, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Currency currency = currencyList.get(position);
        holder.tvCurrencyDenomination.setText("" + currency.getCurrencyDenomination());
        holder.tvCurrencyCount.setText("" + currency.getCurrencyCount());
        holder.tvCurrencyTotal.setText("" + (currency.getCurrencyDenomination() * currency.getCurrencyCount()));
    }

    @Override
    public int getItemCount() {
        return currencyList.size();
    }
}