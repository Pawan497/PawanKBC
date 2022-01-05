package com.pawanyadav497.kbcwithpawan.ui.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pawanyadav497.kbcwithpawan.R;
import com.pawanyadav497.kbcwithpawan.databinding.FragmentIntroBinding;
import com.pawanyadav497.kbcwithpawan.databinding.FragmentLoginBinding;
import com.pawanyadav497.kbcwithpawan.ui.home.HomeViewModel;

public class LoginFragment extends Fragment {

    //public static String login_succesful="Login Successful";

    private HomeViewModel homeViewModel;
    //private SavedStateHandle savedStateHandle;

    private EditText etEmailAddress, etPassword;
    private Button btnLogin;
    private TextView btnSignup;

    private FragmentLoginBinding binding;

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        homeViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(HomeViewModel.class);
//
//    }
// will use this code
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//        homeViewModel=new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        homeViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(HomeViewModel.class);
        //savedStateHandle = Navigation.findNavController(container).getPreviousBackStackEntry().getSavedStateHandle();

        //savedStateHandle.set(login_succesful,false);

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        etEmailAddress = binding.etEmailAddress;
        etPassword=binding.etPassword;
        btnLogin=binding.btnLogin;
        btnSignup=binding.btnSignup;

        NavController navController= Navigation.findNavController(container);

        homeViewModel.getUserData().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser!=null){
                    navController.navigate(R.id.navigate_to_home);
                    Snackbar.make(container,"Navigate to home",Snackbar.LENGTH_LONG).show();
                }else{
                    Snackbar.make(container,"welcome to login",Snackbar.LENGTH_LONG).show();
                }
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.navigate_to_signupFragment);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=etEmailAddress.getText().toString();
                String password = etPassword.getText().toString();

                //Agar Email ya password field empty ho toh ye message print krenge
               if (email.isEmpty()||password.isEmpty()){
                   //Toast kyu use kiya?
                    Snackbar.make(view,"Email/Password is Empty!",Snackbar.LENGTH_LONG).show();
                }
                else {
                    homeViewModel.signIn(email,password);
                }
            }
        });
       return root;
    }

}