package com.pawanyadav497.kbcwithpawan.ui.intro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pawanyadav497.kbcwithpawan.R;
import com.pawanyadav497.kbcwithpawan.databinding.FragmentGalleryBinding;
import com.pawanyadav497.kbcwithpawan.databinding.FragmentIntroBinding;
import com.pawanyadav497.kbcwithpawan.ui.home.HomeViewModel;

public class IntroFragment extends Fragment {

    private FragmentIntroBinding binding;
    private Button btnBegin; //1)Button class ka object btnBegin

    private HomeViewModel homeViewModel;

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        homeViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(HomeViewModel.class);
//
//    }
//will use this code
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentIntroBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
      //  homeViewModel=new ViewModelProvider(requireActivity()).get(HomeViewModel.class); //isko commentout krunga

        homeViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(HomeViewModel.class);

        final NavController navController= Navigation.findNavController(container);
        btnBegin = binding.btnBegin; //2) Xml k btnBegin ko class Button k object btnBegin se connect kiya
        final boolean[] nav = {false};
        homeViewModel.getUserData().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser!=null){
                    nav[0] =true;
                    Snackbar.make(container,"Navigate to home",Snackbar.LENGTH_LONG).show();
                }else{
                    nav[0] =false;
                    Snackbar.make(container,"Navigate to login fragment",Snackbar.LENGTH_LONG).show();
                }
            }
        });

        btnBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nav[0]){
                    navController.navigate(R.id.navigate_to_home);
                }else{
                    navController.navigate(R.id.navigate_to_loginFragment);
                }
            }
        });

        return root;
    }


}