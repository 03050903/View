package com.example.warren.view.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.warren.view.R;
import com.example.warren.view.ui.custom.AddToCartHelper;
import com.example.warren.view.utils.ScreenUtils;

/**
 * Created by warren on 3/23/16.
 */
public class AddToCartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_to_cart);
        init();
    }

    private void init() {

        Button btn_addToCart = (Button) findViewById(R.id.btn_add_to_buy_list);
        btn_addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add2CartAnim();
            }
        });
        ImageButton ib_left = (ImageButton) findViewById(R.id.ib_left);
        ib_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /*加入购物车动画*/
    private void Add2CartAnim( ) {
        /* 起点 */
        int[] startXY = new int[2];
        int fx = ScreenUtils.getScreenWidth(this) - 50;
        int fy = ScreenUtils.getScreenHeight(this) - 50;
        startXY[0] = fx;
        startXY[1] = fy;
        final ImageView animImg = new ImageView(this);
        animImg.setImageResource(R.drawable.circle_orange_bg);

        ViewGroup anim_mask_layout = AddToCartHelper.createAnimLayout(this);
        anim_mask_layout.addView(animImg);
        final View v = AddToCartHelper.addViewToAnimLayout(this, anim_mask_layout, animImg, startXY, true);
        if (v == null) {
            return;
        }
        /* 终点 */
        final View cartView = findViewById(R.id.tv_superscript);
        int[] endXY = new int[2];
        cartView.getLocationInWindow(endXY);
        int tx = endXY[0] + cartView.getWidth() / 2 - 16;
        int ty = endXY[1] + cartView.getHeight() / 2 - 16;
        /* 中点 */
        int mx = (tx + fx) / 2;
        int my = (ty + fy) / 2 - ScreenUtils.getScreenHeight(this) / 5;
        AddToCartHelper.startAnimation(v, 0, 0, fx, fy, mx, my, tx, ty, new AddToCartHelper.AnimationListener() {
            @Override
            public void onAnimationEnd() {
               //动画结束，做一下别的
            }
        });
    }

}
