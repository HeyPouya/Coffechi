package ir.apptune.coffechi.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.apptune.coffechi.OnItemClickListener;
import ir.apptune.coffechi.R;
import ir.apptune.coffechi.models.MainRecyclerViewModel;


public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MyViewHolder> {
    Context context;
    OnItemClickListener listener;
    ArrayList<MainRecyclerViewModel> model;

    public MainRecyclerViewAdapter(Context context, OnItemClickListener listener, ArrayList<MainRecyclerViewModel> model) {
        this.context = context;
        this.listener = listener;
        this.model = model;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.main_recycler_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(model.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_restaurant_name)
        TextView txtRestaurantName;
        @BindView(R.id.img_restaurant)
        ImageView imgRestaurant;
        @BindView(R.id.show_rate_restaurant)
        RatingBar showRateRestaurant;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void bind(final MainRecyclerViewModel model, final OnItemClickListener listener) {
            txtRestaurantName.setText(model.getRestaurantName());
            showRateRestaurant.setRating(Float.parseFloat(model.getRestaurantRate()) / 20);
            Picasso.with(context).load(model.getRestaurantImage()).resize(105,105).into(imgRestaurant);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnClickItem(model);
                }
            });
        }
    }
}
