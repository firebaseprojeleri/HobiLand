package tr.firefighters.hobilandgelecegiyazanlar.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;

import tr.firefighters.hobilandgelecegiyazanlar.R;

public class RegisterFragment extends BaseFragment {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText editPw, editPwa, editEmail;
    private Button register;
    private TextView tv_Registered;


    public RegisterFragment() {}

    @Override
    protected int getFID() {
        return R.layout.fragment_register;
    }

    @Override
    protected void init() {
        mAuth = FirebaseAuth.getInstance();
        register = (Button) getActivity().findViewById(R.id.button_register);
        editEmail = (EditText) getActivity().findViewById(R.id.editText_email);
        editPw = (EditText) getActivity().findViewById(R.id.editText_password);
        editPwa = (EditText) getActivity().findViewById(R.id.editText_password2);
        tv_Registered = (TextView) getActivity().findViewById(R.id.textView_registered);
    }

    @Override
    protected void handlers() {
        AuthListener();
        RegisterListener();
        tvRegisteredListener();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onResume() {
        super.onResume();
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
                    Log.d(TAG, "Giriş yapıldı. UİD: " + user.getUid());
                } else {
                    Log.d(TAG, "Çıkış yapıldı.");
                }
            }
        };
    }
    private void RegisterListener()
    {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = editPw.getText().toString().trim();
                String passwordAgain = editPwa.getText().toString().trim();
                String email = editEmail.getText().toString().trim();

                if (!password.isEmpty() && !passwordAgain.isEmpty() && !email.isEmpty()) {
                    if (password.equals(passwordAgain)) {
                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                                        if (!task.isSuccessful()) {
                                            Toast.makeText(getActivity(), task.getException().getMessage(),
                                                    Toast.LENGTH_SHORT).show();
                                        } else {
                                            listener.onFragmentChange(FragmentDesignFactory.MAIN);
                                        }
                                    }
                                });
                    }
                }
            }
        });
    }

    protected void tvRegisteredListener()
    {
        tv_Registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onFragmentChange(FragmentDesignFactory.LOGIN);
            }
        });
    }

}
