package bola.wiradipa.org.lapanganbola;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import bola.wiradipa.org.lapanganbola.controllers.BaseActivity;
import bola.wiradipa.org.lapanganbola.helpers.ImageFilePath;

public class ImageActivity extends BaseActivity {
    private ImageView ivHolder;
    private Button btnChange, btnNext;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        checkSession();

        ivHolder = findViewById(R.id.holder);
        btnNext = findViewById(R.id.submit);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uri!=null) {
                    Intent intent = new Intent(context, StatisticActivity.class);
                    intent.putExtra(EXTRA_ID, getLongExtraData(EXTRA_ID));
                    intent.putExtra(EXTRA_IMAGE, ImageFilePath.getPath(context, uri));
                    startActivity(intent);
                }
            }
        });
        btnChange = findViewById(R.id.change);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePhoto();
            }
        });

        changePhoto();
    }

    private void changePhoto(){
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setRequestedSize(400, 400)
                .setAspectRatio(4,4)
                .setFixAspectRatio(true)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                uri = result.getUri();
                ivHolder.setImageURI(uri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                showToast(error.getMessage());
            }
        }
    }
}
