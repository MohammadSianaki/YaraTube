package com.yaratech.yaratube.ui;

import android.Manifest;
import android.content.IntentFilter;
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
import com.yaratech.yaratube.data.AppDataManager;
import com.yaratech.yaratube.data.DataManager;
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
import com.yaratech.yaratube.ui.login.verification.SmsListener;
import com.yaratech.yaratube.ui.login.verification.SmsReceiver;
import com.yaratech.yaratube.ui.productdetails.ProductDetailsFragment;
import com.yaratech.yaratube.ui.profile.ProfileFragment;
import com.yaratech.yaratube.utils.ActivityUtils;
import com.yaratech.yaratube.utils.TextUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

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

    private CompositeDisposable compositeDisposable;
    private AppDbHelper appDbHelper;
    private AppApiHelper appApiHelper;
    private AppPreferencesHelper appPreferencesHelper;
    private AppDataManager appDataManager;
    private boolean autoReadOtp;
    private SmsReceiver smsReceiver;

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

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1 &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS};
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == BaseActivity.PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                autoReadOtp = true;
            } else {
                autoReadOtp = false;
            }
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
        IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        smsReceiver = new SmsReceiver();
        SmsReceiver.bindListener(getSmsListener());
        registerReceiver(smsReceiver, filter);
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
        SmsReceiver.unBindListener();
        unregisterReceiver(smsReceiver);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_profile_item) {
            Disposable disposable = appDataManager.isUserAuthorized(new DataManager.LoginDatabaseResultCallback() {
                @Override
                public void onSuccess(Map<Boolean, String> map) {
                    if (map.containsKey(true)) {
                        Log.d(TAG, "onUserLoginInfoLoaded: User Is Authorized ---> token =" + map.get(true));
                        showProfileFragment();
                    } else {
                        showLoginDialog();
                    }
                }

                @Override
                public void onFailure(String message) {
                    Log.d(TAG, "onFailure() called with: message = [" + message + "]");
                }
            });
            compositeDisposable.add(disposable);
        }
        if (item.getItemId() == R.id.nav_logout_item) {
            // TODO: 9/6/2018  add logout item
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showProfileFragment() {
        ProfileFragment profileFragment = ProfileFragment.newInstance();
        profileFragment.setAppDataManager(appDataManager);
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
        commentDialogFragment.setAppDataManager(appDataManager);
        commentDialogFragment.show(getSupportFragmentManager(), CommentDialogFragment.class.getSimpleName());
    }

    private void showLoginDialog() {
        LoginDialogFragment loginFragment = LoginDialogFragment.newInstance();
        loginFragment.setAppDataManager(appDataManager);
        loginFragment.show(getSupportFragmentManager(), LoginDialogFragment.class.getSimpleName());
    }

    public SmsListener getSmsListener() {
        return new SmsListener() {
            @Override
            public void onReceivedMessage(String message) {
                String OTP = TextUtils.removeNonDigits(message);
                sendOneTimePasswordToVerificationFragment(OTP);
                Log.d(TAG, "<<<<   OTP     >>>>    onReceivedMessage(): removeNonDigits Returned : " + OTP);
            }
        };
    }

    private void sendOneTimePasswordToVerificationFragment(String otp) {
        Log.d(TAG, "<<<<   OTP     >>>>    sendOneTimePasswordToVerificationFragment() called with: otp = [" + otp + "]");
        EventBus.getDefault().post(otp);
    }

}
