package com.pawanyadav497.kbcwithpawan.repository;

import static android.content.ContentValues.TAG;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AuthenticationRepository {
    private Application application;

    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private FirebaseAuth mAuth;
    private MutableLiveData<Boolean>userLoggedMutableLiveData;

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public MutableLiveData<Boolean> getUserLoggedMutableLiveData() {
        return userLoggedMutableLiveData;
    }


    private DatabaseReference reference;
    private DatabaseReference ref;

//    public DatabaseReference getReferencequestion() {
//        return referencequestion;
//    }
//
//    private DatabaseReference referencequestion;//Extra

    public DatabaseReference getReference() {
        return reference;
    }

    public AuthenticationRepository(Application application) {
        this.application = application;

        firebaseUserMutableLiveData=new MutableLiveData<>();

        userLoggedMutableLiveData=new MutableLiveData<>();

        mAuth=FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser()!=null){

            firebaseUserMutableLiveData.postValue(mAuth.getCurrentUser());
//            user = new MutableLiveData<>();
//            user.setValue("User");
        }

        reference= FirebaseDatabase.getInstance().getReference();
//        referencequestion=FirebaseDatabase.getInstance().getReference("Model Class Data");
    }
    public void register(String name,String email,String password){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d(TAG, "createUserWithEmail: Succes");
                    firebaseUserMutableLiveData.postValue(mAuth.getCurrentUser());
                    uploadData(name,email); //name add krne k liye
                    Toast.makeText(application,"Sign Up Succesful!", Toast.LENGTH_SHORT).show();

                }
                else{
                    Log.w(TAG,"createUserWithEmail:failure", task.getException());

                    Toast.makeText(application,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void login(String email,String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d(TAG, "signInWithEmail: success");
                    firebaseUserMutableLiveData.postValue(mAuth.getCurrentUser());
                    Toast.makeText(application,"Login Succesful!", Toast.LENGTH_SHORT).show();

                }else{
                    Log.w(TAG,"signWithEmail:failure", task.getException());

                    Toast.makeText(application,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void sign_out(){
        mAuth.signOut();
        userLoggedMutableLiveData.postValue(true);
    }
    private void uploadData(String name,String email){
       String uid=mAuth.getCurrentUser().getUid();
        ref=reference.child("users");
        if (uid!=null){
        String key=ref.push().getKey();

        HashMap<String,String> user = new HashMap<>();
        user.put("UID",uid);
        user.put("Name",name);
        user.put("Email",email); //add field you want for user

        ref.child(uid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    Toast.makeText(application,"User Created", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(application,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });}
    }
}
