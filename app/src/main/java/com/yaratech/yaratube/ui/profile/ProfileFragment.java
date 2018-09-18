package com.yaratech.yaratube.ui.profile;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.yaratech.yaratube.R;
import com.yaratech.yaratube.data.AppDataManager;
import com.yaratech.yaratube.data.model.api.GetProfileResponse;
import com.yaratech.yaratube.utils.AppConstants;
import com.yaratech.yaratube.utils.FileUtils;
import com.yaratech.yaratube.utils.SnackbarUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements ProfileContract.View {
    //-------------------------------------------------------------------------------------------
    private static final String TAG = "ProfileFragment";
    private static final int PICK_IMAGE_REQUEST_CODE = 100;
    private static final int PICK_IMAGE_PERMISSION_REQUEST_CODE = 102;
    private static final String[] PICK_IMAGE_PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static final String GALLERY_IMAGE_SOURCE = "GALLERY_IMAGE_SOURCE";
    private ProfileContract.Presenter mPresenter;
    private Unbinder mUnBinder;
    private PersianDatePickerDialog datePickerDia;
    private AppDataManager appDataManager;
    private Uri cropPictureUri;
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
    private Uri cameraUri;
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
    public void onResume() {
        super.onResume();
        avatarImageView.setOnClickListener(v -> {
            if (checkMarshMellowPermission()) {
                // check if camera and gallery permission are granted
                if (isAllPermissionsGranted()) {
                    loadImageFromGalleryOrCamera();
                } else {
                    // request denied permission
                    requestPermissions(PICK_IMAGE_PERMISSIONS, PICK_IMAGE_PERMISSION_REQUEST_CODE);
                }
            } else {
                loadImageFromGalleryOrCamera();
            }
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

    private boolean isAllPermissionsGranted() {
        // Gallery Permission
        return ActivityCompat
                .checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                // Camera Permissions
                ActivityCompat
                        .checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat
                        .checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat
                        .checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkMarshMellowPermission() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView() called");
        mPresenter.unSubscribe();
        mUnBinder.unbind();
        mPresenter.detachView();
        super.onDestroyView();
    }

    private void loadImageFromGalleryOrCamera() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        galleryIntent.putExtra(MediaStore.EXTRA_OUTPUT, true);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            cameraIntent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        }
        cameraUri = FileUtils.createImageUri(getContext());
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        Intent[] intents = new Intent[]{cameraIntent};

        Intent chooserIntent = Intent.createChooser(galleryIntent, "انتخاب تصویر");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents);
        startActivityForResult(chooserIntent, PICK_IMAGE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST_CODE &&
                resultCode == RESULT_OK) {
            if (data != null) {
                Log.d(TAG, "onActivityResult: data not null");
                if (data.getData() != null) {
                    Log.d(TAG, "onActivityResult: getData() not null");
                    handleGalleryResult(data);
                }
            } else {
                Log.d(TAG, "onActivityResult: camera result");
                handleCameraResult(cameraUri);
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                mPresenter.uploadUserProfileImageAvatar(
                        new File(resultUri.getPath())
                );
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e(TAG, "onActivityResult: ", error);
            }
        }
    }

    // Change this method(edited)
    public void handleGalleryResult(Intent data) {
        File sourceFile = null;
        File destinationFile = null;
        try {
            String realPathFromURI = FileUtils.getRealPathFromURI(getContext(), data.getData());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                sourceFile = new File(realPathFromURI == null ? getImageUrlWithAuthority(getContext(), (data.getData())) : realPathFromURI);
            } else {
            }
        } catch (Exception e) {
            Log.v(TAG, "GENERAL ERROR");
            Toast.makeText(getContext(), "Error on Read Image", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return;
        }
        try {
            destinationFile = FileUtils.createImageTempFile();
            Log.d(TAG, "handleGalleryResult() called with: data = [" + data + "]");
            cropPictureUri = Uri.fromFile(destinationFile);

            if (sourceFile.exists()) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    showCropperDialog(FileProvider.getUriForFile(
                            getContext(),
                            getContext().getPackageName() + ".provider",
                            sourceFile),
                            cropPictureUri);
                } else {
                    showCropperDialog(Uri.fromFile(sourceFile), cropPictureUri);
                }

            } else {
                showCropperDialog(data.getData(), cropPictureUri);
            }

        } catch (Exception e) {
            if (destinationFile != null) {
                destinationFile.delete();
            }
            Toast.makeText(getContext(), "Error on Write Image", Toast.LENGTH_SHORT).show();
            Log.v(TAG, "GENERAL ERROR");
            e.printStackTrace();
        }
    }

    public void handleCameraResult(Uri cameraPictureUrl) {
        try {
            cropPictureUri = Uri.fromFile(createImageFile());
            showCropperDialog(cameraPictureUrl, cropPictureUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getImageUrlWithAuthority(Context context, Uri uri) {
        InputStream is = null;
        if (uri.getAuthority() != null) {
            try {
                is = context.getContentResolver().openInputStream(uri);
                Bitmap bmp = BitmapFactory.decodeStream(is);
                return writeToTempImageAndGetPathUri(context, bmp).toString();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static Uri writeToTempImageAndGetPathUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    //---------------------------------------------------------------------------------------------


    //---------------------------------------------------------------------------------------------

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
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

    private void showCropperDialog(Uri sourceImage, Uri destinationImage) {
        CropImage
                .activity(sourceImage)
                .setAllowFlipping(true)
                .setAllowRotation(true)
                .setAllowCounterRotation(true)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setOutputCompressFormat(Bitmap.CompressFormat.JPEG)
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
