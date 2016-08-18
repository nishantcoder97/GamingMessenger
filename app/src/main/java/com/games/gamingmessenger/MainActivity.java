package com.games.gamingmessenger;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

//    private static final String TAG = "SignInActivity";
    public static final int RC_SIGN_IN=2429;
    GoogleSignIn signIn;
    ProgressDialog dialog;

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            Intent i=new Intent(this,ChatList.class);
            startActivity(i);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i2;
        i2 = getIntent();
        if (i2 != null) {
            if(i2.getBooleanExtra("EXIT", false))
                finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().hide();

        SignInButton btn= (SignInButton) findViewById(R.id.sign_in_button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSignIn();
            }
        });
    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN)
        {
            Intent i=signIn.handleSignInResult(data);
            if (i!=null) startActivity(i);
        }

    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    public void getSignIn()
    {

        GoogleApiClient apiClient;
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        apiClient=new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();
        signIn=new GoogleSignIn(apiClient,this);
        startActivityForResult(signIn.signIn(),RC_SIGN_IN);
    }

    public void showProgressDialog()
    {
        if(dialog==null)
        {
            dialog=new ProgressDialog(this);
            dialog.setMessage("Loading");
            dialog.setIndeterminate(true);
        }
        dialog.show();
    }
    public void hideProgressDialog()
    {
        if(dialog!=null)
        {
            if(dialog.isShowing())
                dialog.hide();
        }
    }


}

