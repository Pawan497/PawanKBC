package com.pawanyadav497.kbcwithpawan.ui.home;

import static android.content.ContentValues.TAG;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.pawanyadav497.kbcwithpawan.repository.AuthenticationRepository;

public class HomeViewModel extends AndroidViewModel {

//    private MutableLiveData<String> mText;

    public MutableLiveData<FirebaseUser> getUserData() {
        return userData;
    }

    public MutableLiveData<Boolean> getLoggedStatus() {
        return loggedStatus;
    }

    public static  MutableLiveData<String>user;

    private MutableLiveData<Integer> score;

    public void setScore(Integer score) {
        this.score.postValue(score);
    }

    public LiveData<Integer> getScore() {
        return score;
    }

    private AuthenticationRepository repository;
    private MutableLiveData<FirebaseUser>userData;
    private MutableLiveData<Boolean>loggedStatus;

    public DatabaseReference getUserdatabaseReference() {
        return userdatabaseReference;
    }

//    public DatabaseReference getQuestionref() {
//        return questionref;
//    }
//
//    private DatabaseReference questionref;

    private DatabaseReference userdatabaseReference;

    public HomeViewModel(Application application) {
        super(application);
        repository=new AuthenticationRepository(application);

       userData = repository.getFirebaseUserMutableLiveData();
       loggedStatus = repository.getUserLoggedMutableLiveData();


       userdatabaseReference=repository.getReference();

     //  questionref=repository.getReferencequestion();

       score=new MutableLiveData<>();
       score.setValue(16);
  //      mText = new MutableLiveData<>();
  //      mText.setValue("This is home fragment");

    }

  //  public LiveData<String> getText() {
  //      return mText;
  //  }

    public void register(String name,String email,String password){
        repository.register(name,email,password);
    }
   public void signIn(String email,String password){
        repository.login(email, password);
   }
   public void signOut(){
        repository.sign_out();
   }

}