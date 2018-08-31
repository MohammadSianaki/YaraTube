package com.yaratech.yaratube.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.model.Category;
import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.data.source.StoreRepository;
import com.yaratech.yaratube.data.source.UserDataSource;
import com.yaratech.yaratube.data.source.UserRepository;
import com.yaratech.yaratube.data.source.local.LocalDataSource;
import com.yaratech.yaratube.data.source.local.UserLoginInfo;
import com.yaratech.yaratube.data.source.prefs.AppPreferencesHelper;
import com.yaratech.yaratube.data.source.remote.StoreRemoteDataSource;
import com.yaratech.yaratube.data.source.remote.UserRemoteDataSource;
import com.yaratech.yaratube.ui.category.CategoryFragment;
import com.yaratech.yaratube.ui.comment.CommentDialogFragment;
import com.yaratech.yaratube.ui.gridcategory.GridCategoryFragment;
import com.yaratech.yaratube.ui.home.HomeFragment;
import com.yaratech.yaratube.ui.home.header.HeaderItemsFragment;
import com.yaratech.yaratube.ui.login.LoginDialogFragment;
import com.yaratech.yaratube.ui.productdetails.ProductDetailsFragment;
import com.yaratech.yaratube.ui.profile.ProfileFragment;
import com.yaratech.yaratube.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseActivity extends AppCompatActivity implements
        CategoryFragment.OnCategoryFragmentInteractionListener,
        OnRequestedProductItemClickListener,
        HeaderItemsFragment.OnHeaderItemsInteractionListener,
        NavigationView.OnNavigationItemSelectedListener,
        ProductDetailsFragment.OnProductDetailsInteraction {

    public static final int PERMISSION_REQUEST_CODE = 1234;
    private static final String TAG = "BaseActivity";
    //------------------------------------------------------------------------------------------------


    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.nav_view)
    NavigationView navigationView;


    private UserRepository userRepository;
    private StoreRepository storeRepository;
    private LocalDataSource localDataSource;
    private StoreRemoteDataSource storeRemoteDataSource;
    private UserRemoteDataSource userRemoteDataSource;
    private AppPreferencesHelper appPreferencesHelper;
    private CompositeDisposable compositeDisposable;
    //------------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Log.i(TAG, "onCreate: BaseActivity");
        ButterKnife.bind(this);
        ActivityUtils.checkAndSetRtl(this);
        requestPermissions();
        initDependencies();

        navigationView.setNavigationItemSelectedListener(this);

        BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.fl_base_activity_content);
        if (fragment == null) {
            Log.i(TAG, "onCreate: content fragment is null");
            fragment = BaseFragment.newInstance();
            fragment.setCompositeDisposable(compositeDisposable);
            fragment.setStoreRepository(storeRepository);
            fragment.setUserRepository(userRepository);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.fl_base_activity_content);
        }
    }

    private void initDependencies() {
        this.appPreferencesHelper = new AppPreferencesHelper(getApplicationContext());
        this.userRemoteDataSource = new UserRemoteDataSource(getApplicationContext());
        this.storeRemoteDataSource = new StoreRemoteDataSource(getApplicationContext());
        this.localDataSource = LocalDataSource.getINSTANCE(this);
        this.userRepository = UserRepository.getINSTANCE(userRemoteDataSource, localDataSource, appPreferencesHelper);
        this.storeRepository = StoreRepository.getINSTANCE(storeRemoteDataSource);
        this.compositeDisposable = new CompositeDisposable();
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1 &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS};
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
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
        compositeDisposable.clear();
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
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void showProductsOfRequestedCategoryItem(Category item) {
        int categoryId = item.getId();
        getSupportFragmentManager()
                .beginTransaction()
                .hide(getSupportFragmentManager().findFragmentById(R.id.fl_base_activity_content));
        GridCategoryFragment gridCategoryFragment = GridCategoryFragment.newInstance(categoryId);
        gridCategoryFragment.setStoreRepository(storeRepository);
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
        detailsFragment.setStoreRepository(storeRepository);
        detailsFragment.setUserRepository(userRepository);
        detailsFragment.setCompositeDisposable(compositeDisposable);
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
        detailsFragment.setStoreRepository(storeRepository);
        detailsFragment.setUserRepository(userRepository);
        detailsFragment.setCompositeDisposable(compositeDisposable);
        ActivityUtils.addFragmentToActivity(
                getSupportFragmentManager(),
                detailsFragment,
                R.id.fl_base_activity_content,
                true, ProductDetailsFragment.class.getSimpleName());

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_profile_item) {
            userRepository.checkIfUserIsAuthorized(new UserDataSource.ReadFromDatabaseCallback() {
                @Override
                public void onUserLoginInfoLoaded(UserLoginInfo userLoginInfo) {
                    if (userLoginInfo.getIsAuthorized() == 1) {
                        Log.d(TAG, "onUserLoginInfoLoaded: User Is Authorized");
                        showProfileFragment();
                    } else {
                        showLoginDialog();
                    }
                }

                @Override
                public void onAddedToCompositeDisposable(Disposable disposable) {
                    compositeDisposable.add(disposable);
                }

                @Override
                public void onFailureMessage(String message) {
                    Log.d(TAG, "onFailureMessage() called with: message = [" + message + "]");
                }

                @Override
                public void onNotFoundUserInDatabase() {
                    showLoginDialog();
                }
            });
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showProfileFragment() {
        ProfileFragment profileFragment = ProfileFragment.newInstance();
        ActivityUtils
                .addFragmentToActivity(
                        getSupportFragmentManager(),
                        profileFragment,
                        R.id.fl_base_activity_content,
                        true,
                        ProfileFragment.class.getSimpleName());
    }


    @Override
    public void showLoginDialogToInsertComment() {
        showLoginDialog();
    }

    @Override
    public void showCommentDialog(String token, int productId) {
        CommentDialogFragment commentDialogFragment = CommentDialogFragment.newInstance(token, productId);
        commentDialogFragment.setUserRepository(userRepository);
        commentDialogFragment.show(getSupportFragmentManager(), CommentDialogFragment.class.getSimpleName());
    }

    private void showLoginDialog() {
        LoginDialogFragment loginFragment = LoginDialogFragment.newInstance();
        loginFragment.setCompositeDisposable(compositeDisposable);
        loginFragment.setUserRepository(userRepository);
        loginFragment.show(getSupportFragmentManager(), LoginDialogFragment.class.getSimpleName());
    }
}
