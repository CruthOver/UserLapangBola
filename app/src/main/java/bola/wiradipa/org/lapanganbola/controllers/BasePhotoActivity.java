package bola.wiradipa.org.lapanganbola.controllers;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import bola.wiradipa.org.lapanganbola.R;
import bola.wiradipa.org.lapanganbola.helpers.ImageFilePath;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class BasePhotoActivity extends BaseActivity {
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static final int REQUEST_CHOOSE_PHOTO = 2;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 99;
    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 100;
    protected String mCurrentPhotoPath;

    protected void whichImage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(R.array.choose_images, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if(checkPermission(MY_PERMISSIONS_REQUEST_CAMERA))
                            dispatchTakePictureIntent();
                    } else
                        dispatchTakePictureIntent();
                } else {
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if(checkPermission(MY_PERMISSIONS_REQUEST_STORAGE))
                            dispatchChoosePictureIntent();
                    } else
                        dispatchChoosePictureIntent();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (checkAllPermission()) {
                        dispatchTakePictureIntent();
                    }

                } else {
                    showSnackbar(R.string.error_permission_denied);
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (checkAllPermission()) {
                        dispatchChoosePictureIntent();
                    }
                } else {
                    showSnackbar(R.string.error_permission_denied);
                }
                return;
            }
        }
    }

    private void dispatchChoosePictureIntent(){
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, getString(R.string.title_choose_image)),
                REQUEST_CHOOSE_PHOTO);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "bola.wiradipa.org.lapanganbola.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            setPic();
        }

        if (requestCode == REQUEST_CHOOSE_PHOTO && resultCode == RESULT_OK && data != null){
            mCurrentPhotoPath = ImageFilePath.getPath(context, data.getData());
            setPic();
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void setPic() {
        //
    }

    protected MultipartBody.Part createPartPhoto(String urlFile, String partName){
        if(urlFile==null)
            return null;
        if(partName==null)
            return null;
        File photo = new File(urlFile);
        if(!photo.exists())
            return null;

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"),
                        photo
                );

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, photo.getName(), requestFile);
    }
}
