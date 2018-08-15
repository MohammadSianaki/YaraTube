package com.yaratech.yaratube.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.Category;
import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.ui.category.CategoryFragment;
import com.yaratech.yaratube.ui.gridcategory.GridCategoryFragment;
import com.yaratech.yaratube.ui.home.HomeFragment;
import com.yaratech.yaratube.ui.productdetails.DetailsFragment;
import com.yaratech.yaratube.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity implements CategoryFragment.OnCategoryFragmentInteractionListener, OnRequestedProductItemClickListener {


    private static final String TAG = "BaseActivity";
    //------------------------------------------------------------------------------------------------

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    //------------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Log.i(TAG, "onCreate: BaseActivity");
        ButterKnife.bind(this);
        ActivityUtils.checkAndSetRtl(this);
        setupToolbar();


        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fl_base_activity_content);
        if (fragment == null) {
            Log.i(TAG, "onCreate: content fragment is null");
            fragment = BaseFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fl_base_activity_content);
        }
    }


    @Override
    public void onAttachFragment(android.support.v4.app.Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof BaseFragment) {
            Log.i(TAG, "onAttachFragment: <<<<  BaseFragment Added  >>>>");
        }
        if (fragment instanceof HomeFragment) {
            Log.i(TAG, "onAttachFragment: <<<   HomeFragment Added  >>>>");

        }
        if (fragment instanceof CategoryFragment) {
            Log.i(TAG, "onAttachFragment: <<<<  CategoryFragment Added  >>>>");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: BaseActivity");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState: BaseActivity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: BaseActivity");
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState: BaseActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: BaseActivity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: BaseActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: BaseActivity");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
    }

    private void setupToolbar() {
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
    }


    @Override
    public void showProductsOfRequestedCategoryItem(Category item) {
        int categoryId = item.getId();
        getSupportFragmentManager()
                .beginTransaction()
                .hide(getSupportFragmentManager().findFragmentById(R.id.fl_base_activity_content));
        ActivityUtils.addFragmentToActivity(
                getSupportFragmentManager(),
                GridCategoryFragment.newInstance(categoryId),
                R.id.fl_base_activity_content, true, null);
        Log.i(TAG, "onProductItemClicked: <<<<  " + item.getTitle() + "\t" + item.getId() + " >>>>");
    }

    @Override
    public void showProductDetailsOfRequestedProductItem(Product item) {
        int productId = item.getId();
        Log.i(TAG, "onProductItemClicked: <<<<  " + item.getName() + "\t" + item.getId() + "  >>>>");
        ActivityUtils.addFragmentToActivity(
                getSupportFragmentManager(),
                DetailsFragment.newInstance(productId),
                R.id.fl_base_activity_content,
                true, null);

    }

}
