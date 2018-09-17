package com.yaratech.yaratube.ui.profile;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.AppDataManager;
import com.yaratech.yaratube.data.model.api.GetProfileResponse;
import com.yaratech.yaratube.utils.AppConstants;
import com.yaratech.yaratube.utils.SnackbarUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

import static android.app.Activity.RESULT_OK;

;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements ProfileContract.View {
    //-------------------------------------------------------------------------------------------
    private static final String TAG = "ProfileFragment";
    private static final int PICK_IMAGE_REQUEST_CODE = 100;

    private ProfileContract.Presenter mPresenter;
    private Unbinder mUnBinder;
    private AppDataManager appDataManager;
    private PersianDatePickerDialog datePickerDia;

    private String birthday;

    @BindView(R.id.profile_fragment_toolbar)
    Toolbar toolbar;

    @BindView(R.id.profile_fragment_avatar_image_view)
    ImageView avatarImageView;

    @BindView(R.id.profile_fragment_edit_name_image_view)
    ImageView editNameImageView;

    @BindView(R.id.profile_fragment_edit_gender_image_view)
    ImageView editGenderImageView;

    @BindView(R.id.profile_fragment_edit_birthday_image_view)
    ImageView editBirthdayImageView;

    @BindView(R.id.profile_fragment_birthday_value_text_view)
    TextView birthdayTextView;

    @BindView(R.id.profile_fragment_first_and_last_name_edit_text)
    EditText nameEditText;

    @BindView(R.id.profile_fragment_gender_value_spinner)
    AppCompatSpinner genderSpinner;

    @BindView(R.id.profile_fragment_cancel_button)
    Button cancelButton;

    @BindView(R.id.profile_fragment_submit_changes_button)
    Button submitChangeButton;

    @BindView(R.id.profile_fragment_coordinator)
    CoordinatorLayout coordinatorLayout;
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
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach() called with: context = [" + context + "]");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView() called with: inflater = [" + inflater + "], container = [" + container + "], savedInstanceState = [" + savedInstanceState + "]");
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated() called");
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
        nameEditText.setEnabled(false);
        genderSpinner.setEnabled(false);
        mPresenter = new ProfilePresenter(appDataManager, new CompositeDisposable());
        mPresenter.attachView(this);
        mPresenter.loadUserProfileInfo();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called with visible = [" + isVisible() + "]");
        Log.d(TAG, "onResume() called with hidden = [" + isHidden() + "]");
        Log.d(TAG, "onResume() called with resumed = [" + isResumed() + "]");
        avatarImageView.setOnClickListener(v -> {
            loadImageFromGalleryOrCamera();
        });
        editNameImageView.setOnClickListener(v -> {
            if (nameEditText.isEnabled()) {
                nameEditText.setEnabled(false);
            } else {
                nameEditText.setEnabled(true);
            }
        });
        editBirthdayImageView.setOnClickListener(v -> {
            showDatePickerDialog();
        });

        editGenderImageView.setOnClickListener(v -> {
            if (genderSpinner.isEnabled()) {
                genderSpinner.setEnabled(false);
            } else {
                genderSpinner.setEnabled(true);
            }
        });
        cancelButton.setOnClickListener(v -> {
            getActivity()
                    .getSupportFragmentManager()
                    .popBackStack();
        });
        submitChangeButton.setOnClickListener(v -> {
            mPresenter.uploadUserProfileInfo(
                    nameEditText.getText().toString(),
                    birthdayTextView.getText().toString(), genderSpinner.getSelectedItem().toString()
            );
        });

    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView() called");
        mPresenter.unSubscribe();
        mUnBinder.unbind();
        mPresenter.detachView();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach() called");
    }

    private void loadImageFromGalleryOrCamera() {
        Intent pickImageIntent = getPickImageIntent(getContext());
        startActivityForResult(pickImageIntent, PICK_IMAGE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST_CODE &&
                resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                Uri pickedImage = data.getData();
                showCropperDialog(pickedImage);
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Log.d(TAG, "onActivityResult: <<<< before upload >>>>");
                mPresenter.uploadUserProfileImageAvatar(
                        new File(resultUri.getPath())
                );
                Log.d(TAG, "onActivityResult: <<<< after upload >>>>");
                Log.d(TAG, "onActivityResult: <<<< before set image >>>>");
                Log.d(TAG, "onActivityResult: <<<< after set image >>>>");
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e(TAG, "onActivityResult: ", error);
            }
        }
    }

    public Intent getPickImageIntent(Context context) {
        Intent chooserIntent = null;

        List<Intent> intentList = new ArrayList<>();

        Intent pickIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePhotoIntent.putExtra("return-data", true);
        intentList = addIntentsToList(context, intentList, pickIntent);
        intentList = addIntentsToList(context, intentList, takePhotoIntent);

        if (intentList.size() > 0) {
            chooserIntent = Intent.createChooser(intentList.remove(intentList.size() - 1),
                    context.getString(R.string.pick_image_intent_text));
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toArray(new Parcelable[]{}));
        }

        return chooserIntent;
    }

    private List<Intent> addIntentsToList(Context context, List<Intent> list, Intent intent) {
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resInfo) {
            String packageName = resolveInfo.activityInfo.packageName;
            Intent targetedIntent = new Intent(intent);
            targetedIntent.setPackage(packageName);
            list.add(targetedIntent);
        }
        return list;
    }


    @Override
    public void showLoadedUserProfileInformation(GetProfileResponse response) {
        Log.d(TAG, "showLoadedUserProfileInformation() called with: response = [" + response.getNickname() + "]");
        nameEditText.setText(response.getNickname());
        nameEditText.setEnabled(false);
        if (response.getDateOfBirth() != null) {
            birthdayTextView.setText(response.getDateOfBirth().replace("-", "/"));
        }
        loadUserProfileAvatar(response.getAvatar());
    }

    @Override
    public void loadImageAvatarAfterUpload(String path) {
        Glide
                .with(this)
                .load(path)
                .apply(RequestOptions.circleCropTransform())
                .into(avatarImageView);
    }

    private void loadUserProfileAvatar(String avatarPath) {
        Log.d(TAG, "loadUserProfileAvatar() called with: avatarPath = [" + avatarPath + "]");
        if (avatarPath == null) {
            Glide
                    .with(this)
                    .load(R.drawable.images_avatar_circular)
                    .into(avatarImageView);

        } else {
            Glide
                    .with(this)
                    .load(AppConstants.BASE_URL + avatarPath)
                    .apply(RequestOptions.circleCropTransform())
                    .into(avatarImageView);
        }
    }

    private void showDatePickerDialog() {
        datePickerDia = new PersianDatePickerDialog(getContext())
                .setPositiveButtonResource(R.string.date_picker_dialog_positive_button)
                .setNegativeButtonResource(R.string.date_picker_dialog_negative_button)
                .setTodayButtonResource(R.string.date_picker_dialog_today_button)
                .setTodayButtonVisible(true)
                .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                .setMinYear(1300)
                .setActionTextColor(Color.GRAY)
                .setListener(new Listener() {
                    @Override
                    public void onDateSelected(PersianCalendar persianCalendar) {
                        String date = persianCalendar.getPersianYear()
                                + "/" + persianCalendar.getPersianMonth()
                                + "/" + persianCalendar.getPersianDay();
                        birthdayTextView.setText(date);

                    }

                    @Override
                    public void onDismissed() {
                        Log.d(TAG, "onDismissed() called");
                    }
                });
        datePickerDia.show();
    }

    private void showCropperDialog(Uri filePath) {
        CropImage
                .activity(filePath)
                .setAllowFlipping(true)
                .setAllowRotation(true)
                .setCropShape(CropImageView.CropShape.OVAL)
                .setMaxCropResultSize(1024, 1024)
                .start(getContext(), this);
    }

    @Override
    public void showDataNotAvailableMessage() {
        SnackbarUtils.showServerConnectionFailureSnackbar(coordinatorLayout, new SnackbarUtils.SnackbarCallback() {
            @Override
            public void onRetryAgainPressed() {
                mPresenter.uploadUserProfileInfo(
                        nameEditText.getText().toString(),
                        birthdayTextView.getText().toString(), genderSpinner.getSelectedItem().toString());
            }
        });
    }

    @Override
    public void showSubmitSuccessfulMessage() {
        SnackbarUtils
                .showSuccessfulMessage(coordinatorLayout, getString(R.string.successful_post_profile_information_message));
    }

    public void setAppDataManager(AppDataManager appDataManager) {
        this.appDataManager = appDataManager;
    }
}
