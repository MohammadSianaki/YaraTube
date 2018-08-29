package com.yaratech.yaratube.ui.productdetails;

import android.util.Log;

import com.yaratech.yaratube.data.model.Comment;
import com.yaratech.yaratube.data.model.Product;
import com.yaratech.yaratube.data.source.StoreDataSource;
import com.yaratech.yaratube.data.source.StoreRepository;
import com.yaratech.yaratube.data.source.UserDataSource;
import com.yaratech.yaratube.data.source.UserRepository;
import com.yaratech.yaratube.data.source.local.UserLoginInfo;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class DetailsPresenter implements DetailsContract.Presenter {

    //---------------------------------------------------------------------------------------------
    private static final String TAG = "DetailsPresenter";
    private StoreRepository repository;
    private UserRepository userRepository;
    private CompositeDisposable compositeDisposable;
    private DetailsContract.View mView;
    //---------------------------------------------------------------------------------------------


    public DetailsPresenter(StoreRepository storeRepository, UserRepository userRepository, CompositeDisposable compositeDisposable) {
        this.repository = storeRepository;
        this.userRepository = userRepository;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void attachView(DetailsContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public boolean isAttached() {
        return mView != null;
    }

    @Override
    public void fetchProductDetails(int productId) {
        if (isAttached()) {
            mView.showProgressBarLoading();
            repository.fetchProductDetailsByProductId(new StoreDataSource.ApiResultCallback() {
                @Override
                public void onDataLoaded(Object response) {
                    Product product = (Product) response;
                    if (mView != null) {
                        mView.finishProgressBarLoading();
                        mView.showLoadedData(product);
                    }
                }

                @Override
                public void onDataNotAvailable() {
                    if (mView != null) {
                        mView.finishProgressBarLoading();
                        mView.showDataNotAvailableToast();
                    }
                }

                @Override
                public void onNetworkNotAvailable() {
                    if (mView != null) {
                        mView.finishProgressBarLoading();
                        mView.showNetworkNotAvailableToast();
                    }
                }
            }, productId);
        }
    }

    @Override
    public void fetchProductComments(int productId) {
        if (isAttached()) {
            mView.showCommentLoading();
            repository.fetchCommentsOfProductByProductId(new StoreDataSource.ApiResultCallback() {
                @Override
                public void onDataLoaded(Object response) {
                    List<Comment> commentList = (List<Comment>) response;
                    if (mView != null) {
                        mView.hideCommentLoading();
                        mView.showLoadedComments(commentList);
                    }
                }

                @Override
                public void onDataNotAvailable() {
                    if (mView != null) {
                        mView.hideCommentLoading();
                        mView.showDataNotAvailableToast();
                    }
                }

                @Override
                public void onNetworkNotAvailable() {
                    if (mView != null) {
                        mView.hideCommentLoading();
                        mView.showNetworkNotAvailableToast();
                    }
                }
            }, productId);
        }
    }

    @Override
    public void cancelProductCommentApiRequest() {

    }

    @Override
    public void cancelProductDetailsApiRequest() {
        repository.cancelProductDetailsByProductIdApiRequest();
    }

    @Override
    public void isUserLogin() {
        userRepository.checkIfUserIsAuthorized(new UserDataSource.ReadFromDatabaseCallback() {
            @Override
            public void onUserLoginInfoLoaded(UserLoginInfo userLoginInfo) {
                mView.showCommentDialog(userLoginInfo.getUser().getToken());
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
                mView.showLoginDialog();
            }
        });
    }

    @Override
    public void isUserLoginToPlay() {
        userRepository.checkIfUserIsAuthorized(new UserDataSource.ReadFromDatabaseCallback() {
            @Override
            public void onUserLoginInfoLoaded(UserLoginInfo userLoginInfo) {
                mView.goToPlayerActivity();
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
                mView.showLoginDialog();
            }
        });
    }
}
