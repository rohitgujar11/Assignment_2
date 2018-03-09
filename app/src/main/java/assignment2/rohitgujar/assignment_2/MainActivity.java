package assignment2.rohitgujar.assignment_2;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import assignment2.rohitgujar.assignment_2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;
    private StringBuilder mDisplayText;
    private double mCurrencyDenomination[] = {2000, 1000, 500, 200, 100, 50, 20, 10, 5, 1, 0.50, 0.25};
    private int mCurrencyCount[] = {20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20};
    private List<Currency> currencyList = new ArrayList<>();
    private CurrencyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setActivity(this);
        mBinding.etWithdrawableAmount.addTextChangedListener(new WatchText(mBinding.etWithdrawableAmount));
        mAdapter = new CurrencyAdapter(currencyList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mBinding.recyclerView.setLayoutManager(mLayoutManager);
        mBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        mBinding.recyclerView.setAdapter(mAdapter);
    }

    private class WatchText implements TextWatcher {
        private EditText view;

        private WatchText(EditText view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String text = editable.toString();

            mDisplayText = new StringBuilder();
            currencyList.clear();
            if (text == null || text.length() == 0) {
                refreshList("Details will appear here");
                return;
            } else if (text.equalsIgnoreCase(".")) {
                view.setText("0.");
                view.setSelection(view.getText().length());
                refreshList("Please Enter proper amount");
                return;
            } else if (!isValidString(text)) {
                view.setText(text.substring(0, text.length() - 1));
                view.setSelection(view.getText().length());
                refreshList("Please Enter proper amount");
                return;
            } else if (Double.parseDouble(view.getText().toString().trim()) % mCurrencyDenomination[(mCurrencyDenomination.length - 1)] != 0) {
                refreshList("Please Enter proper amount");
                return;
            }
            double withdrawAmount = Double.parseDouble(view.getText().toString().trim());
            int i = 0, r;

            while (withdrawAmount > 0) {
                if (i >= mCurrencyCount.length) {
                    refreshList("Don't have sufficient money...");
                    return;
                }
                r = (int) (withdrawAmount / mCurrencyDenomination[i]);
                if (r <= mCurrencyCount[i]) {
                    withdrawAmount = withdrawAmount % mCurrencyDenomination[i];
                    if (r > 0) {
                        currencyList.add(new Currency(mCurrencyDenomination[i], r));
                        // mDisplayText.append("\nCurrency Note : " + mCurrencyDenomination[i] + "\tCount : " + r);
                    }
                } else {
                    withdrawAmount = (withdrawAmount - (mCurrencyCount[i] * mCurrencyDenomination[i]));
                    if (mCurrencyCount[i] > 0) {
                        currencyList.add(new Currency(mCurrencyDenomination[i], mCurrencyCount[i]));
                        // mDisplayText.append("\nCurrency Note : " + mCurrencyDenomination[i] + "\tCount : " + mCurrencyCount[i]);
                    }
                }
                i++;
            }
            if (mBinding.tvDescription.getVisibility() == View.VISIBLE) {
                mBinding.tvDescription.setVisibility(View.GONE);
                mBinding.layoutTitle.setVisibility(View.VISIBLE);
                mBinding.layoutTotal.setVisibility(View.VISIBLE);
            }
            mAdapter.notifyDataSetChanged();
            double totalCount = 0.0;
            int totalDenomination = 0;
            for (Currency currency : currencyList) {
                totalCount += (currency.getCurrencyDenomination() * currency.getCurrencyCount());
                totalDenomination += currency.getCurrencyCount();
            }
            mBinding.tvCurrencyCountTotal.setText("" + totalDenomination);
            mBinding.tvTotal.setText("" + totalCount);
        }
    }

    private void refreshList(String str) {
        mBinding.tvDescription.setVisibility(View.VISIBLE);
        mBinding.layoutTitle.setVisibility(View.GONE);
        mBinding.layoutTotal.setVisibility(View.GONE);
        mBinding.tvDescription.setText(str);
        currencyList.clear();
        mAdapter.notifyDataSetChanged();
    }


    private boolean isValidString(String s) {
        int count = 0;
        char character = '.';
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == character) {
                count++;
            }
        }
        if (count > 1)
            return false;
        return true;
    }
}
