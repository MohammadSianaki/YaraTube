package com.yaratech.yaratube.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.AppDataManager;
import com.yaratech.yaratube.data.model.other.Category;
import com.yaratech.yaratube.data.model.other.Product;
import com.yaratech.yaratube.data.source.local.db.AppDbHelper;
import com.yaratech.yaratube.data.source.local.prefs.AppPreferencesHelper;
import com.yaratech.yaratube.data.source.remote.AppApiHelper;
import com.yaratech.yaratube.ui.category.CategoryFragment;
import com.yaratech.yaratube.ui.comment.CommentDialogFragment;
import com.yaratech.yaratube.ui.gridcategory.GridCategoryFragment;
import com.yaratech.yaratube.ui.home.HomeFragment;
import com.yaratech.yaratube.ui.home.header.HeaderItemsFragment;
import com.yaratech.yaratube.ui.login.LoginDialogFragment;
import com.yaratech.yaratube.ui.productdetails.ProductDetailsFragment;
import com.yaratech.yaratube.utils.ActivityUtils;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class BaseActivity extends AppCompatActivity implements
        CategoryFragment.OnCategoryFragmentInteractionListener,
        OnRequestedProductItemClickListener,
        HeaderItemsFragment.OnHeaderItemsInteractionListener,
        ProductDetailsFragment.OnProductDetailsInteraction {

    private static final String TAG = "BaseActivity";
    //------------------------------------------------------------------------------------------------

    private CompositeDisposable compositeDisposable;
    private AppDbHelper appDbHelper;
    private AppApiHelper appApiHelper;
    private AppPreferencesHelper appPreferencesHelper;
    private AppDataManager appDataManager;

    //------------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Log.i(TAG, "onCreate: BaseActivity");
        ButterKnife.bind(this);
        ActivityUtils.checkAndSetRtl(this);
        initDependencies();

        BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.fl_base_activity_content);
        if (fragment == null) {
            Log.i(TAG, "onCreate: content fragment is null");
            fragment = BaseFragment.newInstance();
            fragment.setAppDataManager(appDataManager);
            ActivityUtils
                    .addFragmentToActivity(
                            getSupportFragmentManager(),
                            fragment,
                            R.id.fl_base_activity_content);
        }
    }

    private void initDependencies() {
        this.compositeDisposable = new CompositeDisposable();
        this.appPreferencesHelper = new AppPreferencesHelper(this);
        this.appDbHelper = new AppDbHelper(this);
        this.appApiHelper = new AppApiHelper(this);
        this.appDataManager = new AppDataManager(appPreferencesHelper, appDbHelper, appApiHelper);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
        compositeDisposable.clear();
        super.onStop();
        Log.i(TAG, "onStop: BaseActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: BaseActivity");
    }

    @Override
    public void showProductsOfRequestedCategoryItem(Category item) {
        int categoryId = item.getId();
        getSupportFragmentManager()
                .beginTransaction()
                .hide(getSupportFragmentManager().findFragmentById(R.id.fl_base_activity_content));
        GridCategoryFragment gridCategoryFragment = GridCategoryFragment.newInstance(categoryId);
        gridCategoryFragment.setAppDataManager(appDataManager);
        ActivityUtils.addFragmentToActivity(
                getSupportFragmentManager(),
                gridCategoryFragment,
                R.id.fl_base_activity_content, true, null);
        Log.i(TAG, "onProductItemClicked: <<<<  " + item.getTitle() + "\t" + item.getId() + " >>>>");
    }

    @Override
    public void showRequestedHeaderItemDetails(Product item) {
        if (item.getFiles() == null) {
            Log.d(TAG, "showRequestedHeaderItemDetails: file is null");
        } else {
            Log.d(TAG, "showRequestedHeaderItemDetails: else : file size is : " + item.getFiles().size());
        }
        ProductDetailsFragment detailsFragment = ProductDetailsFragment.newInstance(item);
        detailsFragment.setAppDataManager(appDataManager);

        ActivityUtils.addFragmentToActivity(
                getSupportFragmentManager(),
                detailsFragment,
                R.id.fl_base_activity_content,
                true, ProductDetailsFragment.class.getSimpleName());
    }

    @Override
    public void showProductDetailsOfRequestedProductItem(Product item) {
        Log.i(TAG, "onProductItemClicked: <<<<  " + item.getName() + "\t" + item.getId() + "  >>>>");
        ProductDetailsFragment detailsFragment = ProductDetailsFragment.newInstance(item);
        detailsFragment.setAppDataManager(appDataManager);
        ActivityUtils.addFragmentToActivity(
                getSupportFragmentManager(),
                detailsFragment,
                R.id.fl_base_activity_content,
                true, ProductDetailsFragment.class.getSimpleName());

    }

    @Override
    public void showLoginDialogToInsertComment() {
        showLoginDialog();
    }

    @Override
    public void showCommentDialog(String token, int productId) {
        CommentDialogFragment commentDialogFragment = CommentDialogFragment.newInstance(token, productId);
        commentDialogFragment.setAppDataManager(appDataManager);
        commentDialogFragment.show(getSupportFragmentManager(), CommentDialogFragment.class.getSimpleName());
    }

    private void showLoginDialog() {
        LoginDialogFragment loginFragment = LoginDialogFragment.newInstance();
        loginFragment.setAppDataManager(appDataManager);
        loginFragment.show(getSupportFragmentManager(), LoginDialogFragment.class.getSimpleName());
    }

}
