package tr.firefighters.hobilandgelecegiyazanlar.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import tr.firefighters.hobilandgelecegiyazanlar.R;

import static android.app.ProgressDialog.show;
import static android.content.ContentValues.TAG;

public class LoginFragment extends BaseFragment implements GoogleApiClient.OnConnectionFailedListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText editPass,editEmail;
    private Button btnLogin,btnLoginGoogle;
    private TextView tv_notRegistered;
    private static final int RC_SIGN_IN = 9001;
    GoogleApiClient mGoogleApiClient;
    ProgressDialog progress ;


    public LoginFragment() {}

    @Override
    protected int getFID() {
        return R.layout.fragment_login;
    }

    @Override
    protected void init() {
        mAuth = FirebaseAuth.getInstance();
        editPass = (EditText) getActivity().findViewById(R.id.editText_password);
        editEmail = (EditText) getActivity().findViewById(R.id.editText_email);
        btnLogin = (Button) getActivity().findViewById(R.id.button_login);
        btnLoginGoogle = (Button) getActivity().findViewById(R.id.button_login_google);
        tv_notRegistered = (TextView) getActivity().findViewById(R.id.textView_not_registered);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("48522867023-rlrjd4ig0bkegq29ejseld077om0ng8b.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    protected void handlers() {
        try
        {
            AuthListener();
            LoginButtonListener();
            tvNotRegisteredListener();
            GoogleLoginListener();
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    protected void AuthListener()
    {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    listener.onFragmentChange(FragmentDesignFactory.HOBBIES);
                }
            }
        };
    }

    protected void LoginButtonListener()
    {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress = ProgressDialog.show(getActivity(),"Giriş yapılıyor.","Lütfen bekleyiniz.",true);
                String email = editEmail.getText().toString().trim();
                String password = editPass.getText().toString().trim();
                if (!email.isEmpty() && !password.isEmpty())
                {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(getActivity(), task.getException().getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                        progress.dismiss();
                                    }
                                    else
                                    {
                                        progress.dismiss();
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(getActivity(),"Boş alan bırakmayınız.",Toast.LENGTH_SHORT).show();
                    progress.dismiss();
                }
            }
        });
    }

    protected void GoogleLoginListener()
    {
        btnLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress = ProgressDialog.show(getActivity(),"Google ile giriş yapılıyor.","Lütfen bekleyiniz.");
                signInwithGoogle();
            }
        });
    }

    protected void tvNotRegisteredListener()
    {
        tv_notRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onFragmentChange(FragmentDesignFactory.REGISTER);
            }
        });
    }

    protected void signInwithGoogle(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess())
            {
                GoogleSignInAccount acct = result.getSignInAccount();
                firebaseAuthWithGoogle(acct);
            } else {
                Toast.makeText(getActivity(),"Bir hata oluştu tekrar deneyiniz.",Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        mAuth = FirebaseAuth.getInstance();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progress.dismiss();
                            try
                            {
                                listener.onFragmentChange(FragmentDesignFactory.MAIN);
                            }
                            catch (NullPointerException ep)
                            {
                                throw ep;
                            }
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }
}
