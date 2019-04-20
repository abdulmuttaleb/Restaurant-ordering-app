package com.isaiko.restaurantordering.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.isaiko.restaurantordering.Model.OrderItem;
import com.isaiko.restaurantordering.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PastOrderContentActivity extends AppCompatActivity {

    @BindView(R.id.tv_order_content)
    TextView orderContentTextView;
    List<OrderItem> passedOrdersList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_order_content);
        ButterKnife.bind(this);
        passedOrdersList = (List<OrderItem>)getIntent().getSerializableExtra("orders");
        for (OrderItem order: passedOrdersList) {
            String orderText = order.getmOrderItem().getmItemName()+"\n"+order.getmOrderItemUnitWeight()+"\nQuantity: "+order.getmOrderItemQuantity()
                    +"\n"+order.getmOrderPrice()+"\n\n\n";
            orderContentTextView.append(orderText);
        }
    }
}
