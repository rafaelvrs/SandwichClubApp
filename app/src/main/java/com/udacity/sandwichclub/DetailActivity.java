package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView mAlsoKnownTitleTV;
    TextView mAlsoKnownTV;
    TextView mIngredientsTV;
    TextView mDescriptionTV;
    TextView mOriginTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        mAlsoKnownTitleTV = findViewById(R.id.also_known_title_tv);
        mAlsoKnownTV = findViewById(R.id.also_known_tv);
        mIngredientsTV = findViewById(R.id.ingredients_tv);
        mDescriptionTV = findViewById(R.id.description_tv);
        mOriginTV = findViewById(R.id.origin_tv);

        showAlsoKnown();

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            Log.e(DetailActivity.class.getName(),"EXTRA_POSITION not found in intent");
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            Log.e(DetailActivity.class.getName(),"Sandwich data unavailable");
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .error(R.drawable.no_image)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        Log.e(DetailActivity.class.getName(),"Closing Activity!");
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        if (sandwich == null) {
            closeOnError();
        } else {

            if (sandwich.getAlsoKnownAs().isEmpty()) {
                hideAlsoKnown();
            } else {
                mAlsoKnownTV.setText(listToStringLineBreak(sandwich.getAlsoKnownAs()));
            }

             mIngredientsTV.setText(listToStringSameLine(sandwich.getIngredients()));
             mDescriptionTV.setText(sandwich.getDescription());

             if (sandwich.getPlaceOfOrigin().equals("") || sandwich.getPlaceOfOrigin() == null) {
                 mOriginTV.setText(R.string.detail_unknown);
             } else {
                 mOriginTV.setText(sandwich.getPlaceOfOrigin());

             }
        }
    }

    private void hideAlsoKnown() {
        mAlsoKnownTitleTV.setVisibility(View.GONE);
        mAlsoKnownTV.setVisibility(View.GONE);
    }

    private void showAlsoKnown() {
        mAlsoKnownTitleTV.setVisibility(View.VISIBLE);
        mAlsoKnownTV.setVisibility(View.VISIBLE);
    }

    private String listToStringSameLine(List<String> list) {
        String str = "";
        for (String s: list) {
            if(list.indexOf(s) == (list.size() -1)){
                str += s;
            } else {
                str += s + ", ";
            }
        }
        return str;
    }

    private String listToStringLineBreak(List<String> list) {
        String str = "";
        for (String s: list) {
            if(list.indexOf(s) == (list.size() -1)){
                str += s;
            } else {
                str += s + "\n";
            }
        }
        return str;
    }
}
