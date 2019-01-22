package bola.wiradipa.org.lapanganbola;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

import bola.wiradipa.org.lapanganbola.controllers.BasePhotoActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadCardActivity extends BasePhotoActivity {
    private ImageView ivCard;
    private Picasso picasso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_card);

        checkSession();

        ivCard = findViewById(R.id.card);
        picasso = Picasso.get();

        whichImage();
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.edit_photo:
                whichImage();
                break;
            case R.id.submit:
                submitData();
                break;
        }
    }

    @Override
    public void setPic() {
        File image = new File(mCurrentPhotoPath);
        picasso.load(image)
                .fit()
                .centerCrop()
                .into(ivCard);
    }

    private void submitData(){
        if(mCurrentPhotoPath==null)
            finish();
        String auth_token = getUserToken();

        MultipartBody.Part body = createPartPhoto(mCurrentPhotoPath, "student_card");

        RequestBody rb_auth_token =
                RequestBody.create(
                        MediaType.parse("text/plain"), auth_token);

        showProgressBar(true);
        mApiService.uploadCard(rb_auth_token, body)
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
}
