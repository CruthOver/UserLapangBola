package bola.wiradipa.org.lapanganbola;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import bola.wiradipa.org.lapanganbola.controllers.BaseActivity;
import bola.wiradipa.org.lapanganbola.helpers.apihelper.UtilsApi;
import bola.wiradipa.org.lapanganbola.models.Player;
import bola.wiradipa.org.lapanganbola.models.User;

public class ProfileActivity extends BaseActivity {

    private Button btnEdit, btnUploadCard;
    private Player player;
    private TextView tvName, tvUsername, tvPhone, tvStudent;
    private ImageView ivAvatar;
    private Picasso picasso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        checkSession();

        picasso = Picasso.get();

        ivAvatar = findViewById(R.id.avatar);
        tvName = findViewById(R.id.name);
        tvUsername = findViewById(R.id.username);
        tvPhone = findViewById(R.id.phone);
        tvStudent = findViewById(R.id.student);
        btnEdit = findViewById(R.id.edit);
        btnUploadCard = findViewById(R.id.upload_card);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditProfileActivity.class);
                intent.putExtra("data", new Gson().toJson(player));
                startActivity(intent);
            }
        });
        btnUploadCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UploadCardActivity.class);
                intent.putExtra("data", new Gson().toJson(player));
                startActivity(intent);
            }
        });

        showProgressBar(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showProgressBar(true);
        mApiService.getPlayerDetail(getUserToken()).enqueue(new ApiCallback() {
            @Override
            public void onApiSuccess(String result) {
                showProgressBar(false);
                player = new Gson().fromJson(result, Player.class);
                User user = getUserSession();
                user.setPhotoUrl(player.getPhotoUrl());
                user.setName(player.getName());
                user.setUsername(player.getUsername());
                user.setEmail(player.getEmail());
                user.setPhoneNumber(player.getPhone());
                appSession.updateSession(user);
                initData();
            }

            @Override
            public void onApiFailure(String errorMessage) {
                showProgressBar(false);
                showSnackbar(errorMessage);
            }
        }.build());
    }

    private void initData (){
        if(player==null)
            return;
        tvName.setText(player.getName());
        tvUsername.setText(player.getUsername());
        tvPhone.setText(player.getPhone());
        tvStudent.setText(R.string.label_nonstudent);
        if(player.getStudentStatus()==1) {
            tvStudent.setText(R.string.label_student);
            btnUploadCard.setVisibility(View.GONE);
        }
        picasso.load(UtilsApi.BASE_URL+player.getPhotoUrl())
                .fit()
                .centerCrop()
                .error(R.drawable.user)
                .placeholder(R.drawable.user)
                .into(ivAvatar);
    }
}
