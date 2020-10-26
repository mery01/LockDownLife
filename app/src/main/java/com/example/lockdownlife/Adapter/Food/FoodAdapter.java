package com.example.lockdownlife.Adapter.Food;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


import com.example.lockdownlife.Views.Food.Recipe;
import com.example.lockdownlife.R;
import com.squareup.picasso.Picasso;



import java.util.List;

public class FoodAdapter extends ArrayAdapter {

    private Context context;
    private int res;
    private List list;

    public FoodAdapter(Context context,int res, List list) {
        super(context,res,list);
        this.context = context;
        this.res = res;
        this.list = list;
    }
    public final Context getContext() {
        return this.context;
    }

    public View getView(int pos, View cView, ViewGroup parent) {

        FoodRecipeViewHolder holder;

        if (cView == null) {
            holder = new FoodRecipeViewHolder();
            cView = LayoutInflater.from(context).inflate(res, parent, false);

            holder.title = cView.findViewById(R.id.title_recipe);
            holder.iv_food = cView.findViewById(R.id.iv_food);
            holder.tv_ingredients = cView.findViewById(R.id.ingredients_recipe);
            holder.but_href = cView.findViewById(R.id.but_link);

            final Recipe recipe = (Recipe) getItem(pos);

            //final Recipe recipe = (Recipe) this.list.get(pos);
            Log.d("aquii adapter",String.valueOf(pos));

            Log.d("aquii adapter",String.valueOf(list.get(pos)));
            Log.d("aquiii adapter",String.valueOf(list.size()));
            holder.title.setText(recipe.getTitle());
            Log.d("aquiiii adapter",recipe.getTitle());
            holder.tv_ingredients.setText(recipe.getIngredients());

            if (!TextUtils.isEmpty(recipe.getThumbnail())) {
                Picasso.with(this.context)
                        .load(recipe.getThumbnail())
                        .placeholder(17301579)
                        .error(17301579).into(holder.iv_food);
            } else {
                Picasso.with(this.context).load(-700076).into(holder.iv_food);
            }

            holder.but_href.setOnClickListener(new View.OnClickListener() {
                public final void onClick(View it) {

                    /*Intent intent = new Intent(FoodAdapter.this.getContext(), FoodLink.class);
                    intent.putExtra("href", String.valueOf(recipe.getHref()));
                    FoodAdapter.getContext().startActivity(intent);*/

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(String.valueOf(recipe.getHref())));
                    FoodAdapter.this.getContext().startActivity(intent);
                }
            });
            cView.setTag(holder);
        } else {
            holder = (FoodRecipeViewHolder) cView.getTag();
        }

        return cView;
    }
}
