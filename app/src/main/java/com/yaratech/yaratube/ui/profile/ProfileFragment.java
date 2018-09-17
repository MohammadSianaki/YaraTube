package com.yaratech.yaratube.ui.profile;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
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
import com.yaratech.yaratube.utils.SnackbarUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

import static android.app.Activity.RESULT_OK;
import static com.yaratech.yaratube.utils.AppConstants.BASE_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements ProfileContract.View {
    //-------------------------------------------------------------------------------------------
    private static final String TAG = "ProfileFragment";
    private static final int PICK_IMAGE_REQUEST_CODE = 100;
    private static final int CROP_IMAGE_REQUEST_CODE = 101;
    public static final int xAspectRatio = 16;
    public static final int yAspectRatio = 9;

    private ProfileContract.Presenter mPresenter;
    private Unbinder mUnBinder;
    private AppDataManager appDataManager;
    private PersianDatePickerDialog datePickerDialog;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: +++++ <<<< test >>>>");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: +++++ <<<< test >>>>");
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated() +++++ called <<<< Test >>>>");
        mUnBinder = ButterKnife.bind(this, view);
        nameEditText.setEnabled(false);
        genderSpinner.setEnabled(false);
        mPresenter = new ProfilePresenter(appDataManager, new CompositeDisposable());
        mPresenter.attachView(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: +++++ <<<< test >>>>");
        mPresenter.loadUserProfileInfo();
        String avatarPath = mPresenter.getUserProfileImageAvatarPath();
        loadDefaultImageAvatar(avatarPath);

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: +++++ <<<< test >>>>");
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume: +++++ <<<< test >>>>");
        super.onResume();
        avatarImageView.setOnClickListener(v -> {
            loadImageAvatarFromGallery();
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
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: +++++ <<<< test >>>>");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: +++++ <<<< test >>>>");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: +++++ <<<< test >>>>");
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView: +++++ <<<< test >>>>");
        mPresenter.unSubscribe();
        mUnBinder.unbind();
        mPresenter.detachView();
        super.onDestroyView();
    }


    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: +++++ <<<< test >>>>");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "onDetach: +++++ <<<< test >>>>");
        super.onDetach();
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

    @Override
    public void showLoadedUserProfileInformation(GetProfileResponse getProfileResponse) {
        Log.d(TAG, "showLoadedUserProfileInformation() called with: getProfileResponse = [" + getProfileResponse + "]");
        nameEditText.setText(getProfileResponse.getNickname());
        nameEditText.setEnabled(false);
        if (getProfileResponse.getDateOfBirth() != null) {
            birthdayTextView.setText(getProfileResponse.getDateOfBirth().replace("-", "/"));
        }
        mPresenter.saveUserProfileImageAvatarPath(BASE_URL + getProfileResponse.getAvatar());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST_CODE)
            if (resultCode == RESULT_OK &&
                    data != null &&
                    data.getData() != null &&
                    getContext().getContentResolver() != null) {
                Log.d(TAG, "onActivityResult: <<<< pick image >>>>");
                Uri selectedImage = data.getData();
                showCropperDialog(selectedImage);
            }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "onActivityResult: <<<< crop image >>> with result = ok");
                Uri resultUri = result.getUri();
                Log.d(TAG, "onActivityResult: resultUri = [" + resultUri.getPath() + "]");
                mPresenter.saveUserProfileImageAvatarPath(resultUri.getPath());
                mPresenter.uploadUserProfileImageAvatar(new File(resultUri.getPath()));
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Log.d(TAG, "onActivityResult: <<<< crop image >>> with result = error");
                Exception error = result.getError();
                Log.e(TAG, "onActivityResult: ", error);
            }
        }
    }

    private void loadDefaultImageAvatar(String avatarPath) {
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
    }

    private void showDatePickerDialog() {
        datePickerDialog = new PersianDatePickerDialog(getContext())
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
        datePickerDialog.show();
    }

    private void loadImageAvatarFromGallery() {
        Log.d(TAG, "loadImageAvatarFromGallery() called");
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Picture");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE_REQUEST_CODE);
    }

    private void showCropperDialog(Uri filePath) {
        Log.d(TAG, "showCropperDialog: source uri = [" + filePath.toString() + "]");
        Log.d(TAG, "showCropperDialog: destination uri = [" + Uri.parse(filePath.toString() + System.currentTimeMillis()) + "]");
        CropImage
                .activity(filePath)
                .setAllowFlipping(true)
                .setAllowRotation(true)
                .setCropShape(CropImageView.CropShape.OVAL)
                .setNoOutputImage(false)
                .setMaxCropResultSize(1024, 1024)
                .start(getContext(), this);
    }

    private String getSelectedImageFilePath(Uri selectedImage) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContext().getContentResolver().query(
                selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }

    public void setAppDataManager(AppDataManager appDataManager) {
        this.appDataManager = appDataManager;
    }
}
