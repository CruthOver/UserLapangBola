package bola.wiradipa.org.lapanganbola;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

import bola.wiradipa.org.lapanganbola.controllers.BasePhotoActivity;
import bola.wiradipa.org.lapanganbola.helpers.ImageFilePath;
import bola.wiradipa.org.lapanganbola.helpers.apihelper.UtilsApi;
import bola.wiradipa.org.lapanganbola.models.Player;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditProfileActivity extends BasePhotoActivity {
    private ImageView ivAvatar;
    private Picasso picasso;
    private EditText etName, etUsername, etPhone, etStudent;
    private Player player;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        checkSession();
        picasso = Picasso.get();

        etName = findViewById(R.id.name);
        etUsername = findViewById(R.id.username);
        etPhone = findViewById(R.id.phone);
        etStudent = findViewById(R.id.student);
        ivAvatar = findViewById(R.id.avatar);

        player = new Gson().fromJson(getStringExtraData("data"), Player.class);

        initData();
    }

    private void initData(){
        if(player==null)
            return;
        etName.setText(player.getName());
        etUsername.setText(player.getUsername());
        etPhone.setText(player.getPhone());
        etStudent.setText(R.string.label_nonstudent);
        if(player.getStudentStatus()==1) {
            etStudent.setText(R.string.label_student);
        }
        picasso.load(UtilsApi.BASE_URL+player.getPhotoUrl())
                .fit()
                .error(R.drawable.user)
                .placeholder(R.drawable.user)
                .into(ivAvatar);
    }

    public void onClickEdit(View view){
        switch (view.getId()){
            case R.id.submit:
                submitData();
                break;
            case R.id.cancel:
                finishWithWarning();
                break;
            case R.id.edit_photo:
                changePhoto();
                break;
        }
    }



    private void submitData(){
        if(mCurrentPhotoPath==null)
            finish();
        String auth_token = getUserToken();
        long id = getUserSession().getId();

        MultipartBody.Part body = createPartPhoto(mCurrentPhotoPath, "photo");

        RequestBody rb_auth_token =
                RequestBody.create(
                        MediaType.parse("text/plain"), auth_token);

        showProgressBar(true);
        mApiService.editProfile(id, rb_auth_token, body)
                .enqueue(new ApiCallback() {
                    @Override
                    public void onApiSuccess(String result) {
                        showProgressBar(false);
                        showToast(R.string.submit_ok);
                        finish();
                    }

                    @Override
                    public void onApiFailure(String errorMessage) {
                        showProgressBar(false);
                        showSnackbar(errorMessage);
                    }
                }.build());
    }

    @Override
    public void onBackPressed() {
        finishWithWarning();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finishWithWarning();
        }

        return super.onOptionsItemSelected(item);
    }

    private void changePhoto(){
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setRequestedSize(400, 400)
                .setAspectRatio(4,4)
                .setCropShape(CropImageView.CropShape.OVAL)
                .setFixAspectRatio(true)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                uri = result.getUri();
                mCurrentPhotoPath = ImageFilePath.getPath(context, uri);
                ivAvatar.setImageURI(uri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                showToast(error.getMessage());
            }
        }
    }
}
