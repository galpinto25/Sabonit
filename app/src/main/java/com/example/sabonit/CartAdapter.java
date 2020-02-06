package com.example.sabonit;
//******************imports*******************************/

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

//******************pizza adapter*******************************/


/**
 * A class which represents an adapter of Pizza objects, which extends
 * the RecyclerView.Adapter<CartAdapter.ViewHolder> class.
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    // class private variables declaration:
    private final List<Order> data;
    private final LayoutInflater inflater;
    private ItemClickListener clickListener;

    // data is passed into the constructor
    CartAdapter(Context context, List<Order> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    /**
     * inflates the row layout from xml when needed
     * @return a new single pizza view
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.product_cart_view, parent, false);
        return new ViewHolder(view);
    }

    /**
     *  binds the data to the View objects in each row
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // sets the product attributes of each product in the cart
        Order order = data.get(position);
        holder.productTitle.setText(order.getProduct().getDepartment());
        holder.productLiters.setText("Liters: " + order.getLiters() + "L");
        holder.productSmell.setText("Scent: " + order.getProduct().getSmell());
        NumberFormat formatter = new DecimalFormat("#0.00");
        SpannableString productPriceString = new SpannableString("Price: " +
                formatter.format(order.calculatePrice()) + "₪");
        productPriceString.setSpan(new RelativeSizeSpan(0.8f),
                productPriceString.length() - 1, productPriceString.length(), 0);
        holder.productPrice.setText(productPriceString);
//        holder.bottleImage.setImageDrawable(order.getProduct().getDrawable());
    }

    /**
     * @return the number of rows.
     */
    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * Stores and recycles views as they are scrolled off screen.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView productTitle;
        final TextView productLiters;
        final TextView productSmell;
        final TextView productPrice;
        final ImageView bottleImage;
        final ImageButton editButton;
        final ImageButton deleteButton;

        ViewHolder(View itemView) {
            super(itemView);

            // finds ids of TextView objects
            productTitle = itemView.findViewById(R.id.product_title);
            productLiters = itemView.findViewById(R.id.product_liters);
            productSmell = itemView.findViewById(R.id.product_smell);
            productPrice = itemView.findViewById(R.id.product_price);

            // finds ids of ImageView objects
            bottleImage = itemView.findViewById(R.id.product_bottle_image);

            // finds ids of ImageButton objects
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);

            // sets onClickListeners
            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
//            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            if (view.getId() == itemView.getId()) {
//                Context context = view.getContext();
//                Intent intent = new Intent(context, PizzaDetailsActivity.class);
//                intent.putExtra("pizza_number", getAdapterPosition());
//                context.startActivity(intent);
//            }
            if (view.getId() == editButton.getId())
            {
                if (clickListener != null) {
                    clickListener.onProductEditClick(view, getAdapterPosition());
                }
            }
            else if (view.getId() == deleteButton.getId())
            {
                if (clickListener != null) {
                    clickListener.onProductDeleteClick(view, getAdapterPosition());
                }
            }
        }
    }


    /**
     * allows clicks events to be caught
     */
    void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    /**
     * parent activity will implement this method to respond to click events
      */
    public interface ItemClickListener {
        void onProductEditClick(View view, int position);
        void onProductDeleteClick(View view, int position);
    }

}
