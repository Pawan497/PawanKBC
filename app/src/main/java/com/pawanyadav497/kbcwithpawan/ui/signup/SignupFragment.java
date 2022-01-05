package com.pawanyadav497.kbcwithpawan.ui.signup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentStateManagerControl;
import androidx.lifecycle.Observer;
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
import com.pawanyadav497.kbcwithpawan.databinding.FragmentLoginBinding;
import com.pawanyadav497.kbcwithpawan.databinding.FragmentSignupBinding;
import com.pawanyadav497.kbcwithpawan.ui.home.HomeViewModel;

public class SignupFragment extends Fragment {
    private EditText etNewEmailAddress, etNewPassword, etConfirmPassword2,etPlayerName;
    private Button btnCreateAccount;
    private TextView btnloginNow;
  //  private FirebaseAuth fbaseA;

    private FragmentSignupBinding binding;

    private HomeViewModel homeViewModel;

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        homeViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(HomeViewModel.class);
//
//    }
//will use this code
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//        homeViewModel=new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        homeViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(HomeViewModel.class);
        binding = FragmentSignupBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
    //    fbaseA=FirebaseAuth.getInstance();

        etNewEmailAddress=binding.etNewEmailAddress;
        etNewPassword=binding.etNewPassword;
        etConfirmPassword2=binding.etConfirmPassword2;
        btnCreateAccount=binding.btnCreateAccount;
        btnloginNow=binding.btnloginNow;
        etPlayerName=binding.etPlayerName;

        NavController navController= Navigation.findNavController(container);

        homeViewModel.getUserData().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser!=null){
                    navController.navigate(R.id.navigate_to_loginFragment);
                }
            }
        });

        btnloginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.navigate_to_loginFragment);
            }
        });
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newEmail=etNewEmailAddress.getText().toString();
                String newPassword=etNewPassword.getText().toString();
                String confirmPassword=etConfirmPassword2.getText().toString();

                String name = etPlayerName.getText().toString();
                if (newEmail.isEmpty()||newPassword.isEmpty()||confirmPassword.isEmpty()){
                    Snackbar.make(view,"Email/Password/Confirm Password is Empty",Snackbar.LENGTH_LONG).show();
                }
                else if (confirmPassword.equals(newPassword)){
                    //Signup method
                    Snackbar.make(container,"Sign in succesful",Snackbar.LENGTH_LONG).show();
                    homeViewModel.register(name,newEmail,newPassword);

                }
                else{
                    Snackbar.make(view,"Confirm Password is not same as Password ",Snackbar.LENGTH_LONG).show();
                    }
            }
        });
        return root;
    }

}