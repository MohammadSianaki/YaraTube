package com.yaratech.yaratube.ui.comment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.AppDataManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CommentDialogFragment extends DialogFragment implements CommentContract.View {

    private static final String KEY_TOKEN = "KEY_TOKEN";
    private static final String KEY_PRODUCT_ID = "KEY_PRODUCT_ID";
    private CommentContract.Presenter mPresenter;
    private AppDataManager appDataManager;
    private Unbinder mUnBinder;

    @BindView(R.id.comment_dialog_fragment_rating_bar)
    RatingBar ratingBar;

    @BindView(R.id.comment_dialog_fragment_edit_text)
    EditText editText;

    @BindView(R.id.comment_dialog_fragment_button_submit)
    Button button;

    //---------------------------------------------------------------------------------------------------


    public CommentDialogFragment() {
        // no-arg constructor
    }

    public static CommentDialogFragment newInstance(String token, int productId) {

        Bundle args = new Bundle();
        args.putString(KEY_TOKEN, token);
        args.putInt(KEY_PRODUCT_ID, productId);
        CommentDialogFragment fragment = new CommentDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_comment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
        mPresenter = new CommentPresenter(appDataManager);
        mPresenter.attachView(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button.setOnClickListener(view -> mPresenter.submitCommentToProduct(
                getArguments().getInt(KEY_PRODUCT_ID),
                ratingBar.getNumStars(),
                null,
                editText.getText().toString(),
                TextUtils.concat("Token ", getArguments().getString(KEY_TOKEN)).toString()
        ));
    }

    @Override
    public void onDestroyView() {
        mUnBinder.unbind();
        mPresenter.unSubscribe();
        mPresenter.detachView();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void setAppDataManager(AppDataManager appDataManager) {
        this.appDataManager = appDataManager;
    }

    @Override
    public void closeDialog() {
        dismiss();
    }
}
