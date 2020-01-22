package geekspocket.farziengineers.com;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

import geekspocket.farziengineers.com.geekspocket.R;

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    List<Data> dataList;
    Context context;

    public RecyclerAdapter(Context context, List<Data> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        final View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        Data data = dataList.get(position);

        viewHolder.keyword.setText(data.getKeyword());
        viewHolder.keywordDefination.setText(data.getKeywordDefination());
        Glide.with(context)
                .load(data.getLanguageLogo())
                .asBitmap()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                        viewHolder.languageLogo.setImageBitmap(bitmap);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

   public class ViewHolder extends RecyclerView.ViewHolder {

        TextView keyword, keywordDefination;
        ImageView languageLogo;

        public ViewHolder(final View itemView) {
            super(itemView);

            keyword = (TextView) itemView.findViewById(R.id.keyword);
            keywordDefination = (TextView) itemView.findViewById(R.id.defination);
            languageLogo = itemView.findViewById(R.id.logo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(itemView.getContext(), SecondScreen.class);
                    Data currentDataItem = dataList.get(position);
                    intent.putExtra("keyword", currentDataItem.getKeyword());
                    intent.putExtra("Defination", currentDataItem.getKeywordDefination());
                    intent.putExtra("example", currentDataItem.getKeywordExample());
                    itemView.getContext().startActivity(intent);
                }
            });

        }
    }
}
