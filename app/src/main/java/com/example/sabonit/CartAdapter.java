/* ********* Imports: ********* */
package com.example.sabonit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * A class which represents an adapter of cart objects, which extends
 * the RecyclerView.Adapter<CartAdapter.ViewHolder> class.
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    /* ********* Attributes: ********* */
    // The products that was chosen by the user
    private final List<Order> data;
    // The surface of the products view
    private final LayoutInflater inflater;
    // Listener of the edit button
    private ItemClickListener clickListener;

    /* ********* Constructors: ********* */

    /**
     * Default constructor, initializes the inflater and the data to put in it.
     *
     * @param data - the products that was chosen by the user
     */
    CartAdapter(Context context, List<Order> data) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    /* ********* Functions: ********* */

    /**
     * Inflates the row layout from xml when needed.
     *
     * @return CANT_ORDER_EMPTY_CART new single pizza view
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.product_cart_view, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Binds the data to the View objects in each row.
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // sets the product attributes of each product in the cart
        Order order = data.get(position);
        // handles the displaying of the product's details
        holder.productTitle.setText(order.getProduct().getDepartment());
        holder.productLiters.setText("Liters: " + order.getLiters() + "L");
        holder.productSmell.setText("Scent: " + order.getProduct().getSmell());
        NumberFormat formatter = new DecimalFormat("#0.00");
        SpannableString productPriceString = new SpannableString("Price: " +
                formatter.format(order.calculatePrice()) + "₪");
        productPriceString.setSpan(new RelativeSizeSpan(0.8f),
                productPriceString.length() - 1, productPriceString.length(), 0);
        holder.productPrice.setText(productPriceString);
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
        final ImageButton editButton;
        final ImageButton deleteButton;

        ViewHolder(View itemView) {
            super(itemView);

            // finds ids of TextView objects
            productTitle = itemView.findViewById(R.id.product_title);
            productLiters = itemView.findViewById(R.id.product_liters);
            productSmell = itemView.findViewById(R.id.product_smell);
            productPrice = itemView.findViewById(R.id.product_price);

            // finds ids of ImageButton objects
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);

            // sets onClickListeners
            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
//            itemView.setOnClickListener(this);
        }

        /**
         * Response on clicking on edit or delete buttons, respectively.
         */
        @Override
        public void onClick(View view) {
            if (view.getId() == editButton.getId()) {
                if (clickListener != null) {
                    editButton.setImageResource(R.drawable.ic_edit_product_red);
                    clickListener.onProductEditClick(view, getAdapterPosition());
                }
            } else if (view.getId() == deleteButton.getId()) {
                if (clickListener != null) {
                    deleteButton.setImageResource(R.drawable.ic_delete_product_red);
                    clickListener.onProductDeleteClick(view, getAdapterPosition());
                }
            }
        }
    }

    /**
     * Allows clicks events to be caught.
     */
    void setClickListener(ItemClickListener itemClickListener)
    {
        this.clickListener = itemClickListener;
    }

    /**
     * Parent activity will implement this method to respond to click events.
     */
    public interface ItemClickListener
    {
        void onProductEditClick(View view, int position);
        void onProductDeleteClick(View view, int position);
    }

}


