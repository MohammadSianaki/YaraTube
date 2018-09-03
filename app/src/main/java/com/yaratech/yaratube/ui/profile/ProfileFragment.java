package com.yaratech.yaratube.ui.profile;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yaratech.yaratube.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements ProfileContract.View {
    //-------------------------------------------------------------------------------------------
    private static final String TAG = "ProfileFragment";
    private static final int PICK_IMAGE_REQUEST_CODE = 100;

    private ProfileContract.Presenter mPresenter;
    private Unbinder mUnBinder;
    private UserRepository userRepository;

    @BindView(R.id.profile_fragment_toolbar)
    Toolbar toolbar;

    @BindView(R.id.profile_fragment_avatar_image_view)
    ImageView avatarImageView;

    //-------------------------------------------------------------------------------------------

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {

        Bundle args = new Bundle();

        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
        toolbar.setTitle(R.string.profile_fragment_toolbar_title);
        mPresenter = new ProfilePresenter(userRepository);
        mPresenter.attachView(this);
        String avatarPath = mPresenter.getUserProfileImageAvatarPath();
        Log.d(TAG, "onViewCreated: avatar path is : " + avatarPath);
        if (avatarPath != null) {
            Log.d(TAG, "onViewCreated: Uri.parse : " + Uri.parse(avatarPath));
            Glide
                    .with(this)
                    .load(Uri.parse(avatarPath))
                    .apply(RequestOptions.circleCropTransform())
                    .into(avatarImageView);
        } else {
            Glide
                    .with(this)
                    .load(R.drawable.images_avatar_circular)
                    .into(avatarImageView);
        }
        avatarImageView.setOnClickListener(v -> {
            loadImageAvatarFromGallery();
        });
    }

    private void loadImageAvatarFromGallery() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Picture");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE_REQUEST_CODE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroy: ProfileFragment");
        mUnBinder.unbind();
        mPresenter.detachView();
        super.onDestroyView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(data.getData());
                Log.d(TAG, "onActivityResult: <<<< profile >>>> data.getData() = " + data.getData());
                Log.d(TAG, "onActivityResult: file decoded path is : " + data.getData().getPath());
                Log.d(TAG, "onActivityResult: file  encoded path is : " + data.getData().getEncodedPath());
                mPresenter.saveUserProfileImageAvatarPath(data.getData().toString());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                if (bitmap != null) {
                    Glide

                            .with(this)
                            .load(bitmap)
                            .apply(RequestOptions.encodeQualityOf(100))
                            .apply(RequestOptions.circleCropTransform())
                            .into(avatarImageView);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ProfileFragment");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
