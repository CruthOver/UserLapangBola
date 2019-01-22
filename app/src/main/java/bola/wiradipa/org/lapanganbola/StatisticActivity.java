package bola.wiradipa.org.lapanganbola;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;

import bola.wiradipa.org.lapanganbola.controllers.BaseActivity;
import bola.wiradipa.org.lapanganbola.helpers.apihelper.BaseApiService;
import bola.wiradipa.org.lapanganbola.helpers.apihelper.UtilsApi;
import bola.wiradipa.org.lapanganbola.models.PlayerMatch;

public class StatisticActivity extends BaseActivity {
    private ImageView ivAttack1,ivAttack2,ivDefence1,ivDefence2,ivGoalkeeper,ivHolder;
    private TextView tvStat1, tvStat2, tvStat3, tvStat4, tvStatValue1, tvStatValue2,
            tvStatValue3, tvStatValue4, tvPlayer, tvPosition, tvClub, tvHomeName, tvAwayName,
            tvHomeScore, tvAwayScore;
    private Picasso picasso;
    private PlayerMatch playerMatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        checkSession();

        ivAttack1 = findViewById(R.id.attack1);
        ivAttack2 = findViewById(R.id.attack2);
        ivDefence1 = findViewById(R.id.defence1);
        ivDefence2 = findViewById(R.id.defence2);
        ivGoalkeeper = findViewById(R.id.goalkeeper);
        tvStat1 = findViewById(R.id.stat_1);
        tvStat2 = findViewById(R.id.stat_2);
        tvStat3 = findViewById(R.id.stat_3);
        tvStat4 = findViewById(R.id.stat_4);
        tvStatValue1 = findViewById(R.id.stat_value_1);
        tvStatValue2 = findViewById(R.id.stat_value_2);
        tvStatValue3 = findViewById(R.id.stat_value_3);
        tvStatValue4 = findViewById(R.id.stat_value_4);
        tvPlayer = findViewById(R.id.player);
        tvPosition = findViewById(R.id.position);
        tvClub = findViewById(R.id.club);
        tvHomeName = findViewById(R.id.home_team);
        tvAwayName = findViewById(R.id.away_team);
        tvHomeScore = findViewById(R.id.home_goal);
        tvAwayScore = findViewById(R.id.away_goal);
        ivHolder = findViewById(R.id.holder);
        playerMatch = new PlayerMatch();
        picasso = Picasso.get();

        initData();
    }

    public void initData(){
        File image = new File(getStringExtraData(EXTRA_IMAGE));
        picasso.load(image)
                .fit()
                .centerCrop()
                .into(ivHolder);
        BaseApiService liveService = UtilsApi.getApiLiveService();
        showProgressBar(true);
        liveService.getPlayerMatch(getLongExtraData(EXTRA_ID), getUserPhone())
                .enqueue(new ApiCallback() {
                    @Override
                    public void onApiSuccess(String result) {
                        showProgressBar(false);
                        playerMatch = new Gson().fromJson(result, PlayerMatch.class);
                        tvPlayer.setText(playerMatch.getPlayer());
                        tvClub.setText(playerMatch.getClub());
                        tvPosition.setText(playerMatch.getPosition());
                        tvAwayName.setText(playerMatch.getAway_team());
                        tvHomeName.setText(playerMatch.getHome_team());
                        tvHomeScore.setText(""+playerMatch.getHome_goal());
                        tvAwayScore.setText(""+playerMatch.getAway_goal());
                        tvStat1.setText("Goal".toUpperCase());
                        tvStatValue1.setText(""+playerMatch.getGoal());
                        tvStat2.setText("Assist".toUpperCase());
                        tvStatValue2.setText(""+playerMatch.getAssist());
                        tvStat3.setText("Shoot on Target".toUpperCase());
                        tvStatValue3.setText(""+playerMatch.getShootOnTarget());
                        tvStat4.setText("Shoot off Target".toUpperCase());
                        tvStatValue4.setText(""+playerMatch.getShootOffTarget());
                    }

                    @Override
                    public void onApiFailure(String errorMessage) {
                        showProgressBar(false);
                        showSnackbar(errorMessage);
                    }
                }.build());
    }

    public void onClickShare(View view){
        switch (view.getId()){
            case R.id.attack1:
                ivAttack1.setSelected(!ivAttack1.isPressed());
                ivAttack1.setBackgroundResource(R.drawable.highlight);
                ivAttack2.setBackgroundResource(R.color.highlight);
                ivDefence1.setBackgroundResource(R.color.highlight);
                ivDefence2.setBackgroundResource(R.color.highlight);
                ivGoalkeeper.setBackgroundResource(R.color.highlight);
                tvStat1.setText("Goal".toUpperCase());
                tvStatValue1.setText(""+playerMatch.getGoal());
                tvStat2.setText("Assist".toUpperCase());
                tvStatValue2.setText(""+playerMatch.getAssist());
                tvStat3.setText("Shoot on Target".toUpperCase());
                tvStatValue3.setText(""+playerMatch.getShootOnTarget());
                tvStat4.setText("Shoot off Target".toUpperCase());
                tvStatValue4.setText(""+playerMatch.getShootOffTarget());
                break;
            case R.id.attack2:
                ivAttack2.setSelected(!ivAttack1.isPressed());
                ivAttack2.setBackgroundResource(R.drawable.highlight);
                ivAttack1.setBackgroundResource(R.color.highlight);
                ivDefence1.setBackgroundResource(R.color.highlight);
                ivDefence2.setBackgroundResource(R.color.highlight);
                ivGoalkeeper.setBackgroundResource(R.color.highlight);
                tvStat1.setText("Goal".toUpperCase());
                tvStatValue1.setText(""+playerMatch.getGoal());
                tvStat2.setText("Assist".toUpperCase());
                tvStatValue2.setText(""+playerMatch.getAssist());
                tvStat3.setText("Dribble Success".toUpperCase());
                tvStatValue3.setText(""+playerMatch.getDribble());
                tvStat4.setText("Shoot on Target".toUpperCase());
                tvStatValue4.setText(""+playerMatch.getShootOnTarget());
                break;
            case R.id.defence1:
                ivDefence1.setSelected(!ivAttack1.isPressed());
                ivDefence1.setBackgroundResource(R.drawable.highlight);
                ivAttack2.setBackgroundResource(R.color.highlight);
                ivAttack1.setBackgroundResource(R.color.highlight);
                ivDefence2.setBackgroundResource(R.color.highlight);
                ivGoalkeeper.setBackgroundResource(R.color.highlight);
                tvStat1.setText("Goal".toUpperCase());
                tvStatValue1.setText(""+playerMatch.getGoal());
                tvStat2.setText("Assist".toUpperCase());
                tvStatValue2.setText(""+playerMatch.getAssist());
                tvStat3.setText("Tackle".toUpperCase());
                tvStatValue3.setText(""+playerMatch.getTackle());
                tvStat4.setText("Intercept".toUpperCase());
                tvStatValue4.setText(""+playerMatch.getIntercept());
                break;
            case R.id.defence2:
                ivDefence2.setSelected(!ivAttack1.isPressed());
                ivDefence2.setBackgroundResource(R.drawable.highlight);
                ivAttack2.setBackgroundResource(R.color.highlight);
                ivDefence1.setBackgroundResource(R.color.highlight);
                ivAttack1.setBackgroundResource(R.color.highlight);
                ivGoalkeeper.setBackgroundResource(R.color.highlight);
                tvStat1.setText("Clearence".toUpperCase());
                tvStatValue1.setText(""+playerMatch.getClrearence());
                tvStat2.setText("Tackle".toUpperCase());
                tvStatValue2.setText(""+playerMatch.getTackle());
                tvStat3.setText("Intercept".toUpperCase());
                tvStatValue3.setText(""+playerMatch.getIntercept());
                tvStat4.setText("Foul".toUpperCase());
                tvStatValue4.setText(""+playerMatch.getFoul());
                break;
            case R.id.goalkeeper:
                ivGoalkeeper.setSelected(!ivAttack1.isPressed());
                ivGoalkeeper.setBackgroundResource(R.drawable.highlight);
                ivAttack2.setBackgroundResource(R.color.highlight);
                ivDefence1.setBackgroundResource(R.color.highlight);
                ivDefence2.setBackgroundResource(R.color.highlight);
                ivAttack1.setBackgroundResource(R.color.highlight);
                tvStat1.setText("Save".toUpperCase());
                tvStatValue1.setText(""+playerMatch.getSave());
                tvStat2.setText("Block Cross".toUpperCase());
                tvStatValue2.setText(""+playerMatch.getBlock());
                tvStat3.setText("Assist".toUpperCase());
                tvStatValue3.setText(""+playerMatch.getAssist());
                tvStat4.setText("Goal".toUpperCase());
                tvStatValue4.setText(""+playerMatch.getGoal());
                break;
            case R.id.share:
                showProcessBar(true);
                new ShareImage().execute("");
                break;
        }
    }

    private class ShareImage extends AsyncTask<String, Void, Uri> {
        protected Uri doInBackground(String... data) {
            return share();
        }

        protected void onPostExecute(Uri uri) {
            showProcessBar(false);
            if(uri==null) {
                showSnackbar(R.string.error_create_image);
                return;
            }
            try {
                final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                intent.setType("image/png");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(intent, "Share image via"));
            } catch (Exception e) {
                e.printStackTrace();
                showSnackbar(e.getMessage());
            }
        }
    }


    private Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        }   else{
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return returnedBitmap;
    }

    private Uri share(){
        Bitmap bitmap =getBitmapFromView(findViewById(R.id.canvas));
        try {
            File file = new File(this.getExternalCacheDir(),"lapangbola.png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            Uri uri = FileProvider.getUriForFile(
                    context,
                    context.getApplicationContext()
                            .getPackageName() + ".fileprovider", file);
            return uri;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
