package com.pawanyadav497.kbcwithpawan.ui.home;

//import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pawanyadav497.kbcwithpawan.MainActivity;
import com.pawanyadav497.kbcwithpawan.data.Modelclass;
import com.pawanyadav497.kbcwithpawan.R;
import com.pawanyadav497.kbcwithpawan.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    CountDownTimer countDownTimer;             //Extra 1
    int timervalue=45;                //Extra 2
   ProgressBar progressBar;          //Extra 4

    public static Integer correctCountScore=0;
//will change to private
    ArrayList<Modelclass> list;//Making of List

 //   DatabaseReference databaseReference;

    List<Modelclass> qustionlist;
    Modelclass oneQuestion;
    int index=0;
    TextView question;
    Button option1,option2,option3,option4;

//    @Override                  //Adhoora code
//    public void onCreate(@Nullable Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        NavController navController= NavHostFragment.findNavController(this);
//
//        NavBackStackEntry navBackStackEntry=navController.getCurrentBackStackEntry();
//        SavedStateHandle savedStateHandle =navBackStackEntry.getSavedStateHandle();
//        savedStateHandle.getLiveData(LoginFragment.login_succesful).observe(navBackStackEntry,(Observer<Boolean>) success);
//    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//        homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);

        homeViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(HomeViewModel.class);

        final NavController navController= Navigation.findNavController(container);

        final MediaPlayer mediaPlayerTimer = MediaPlayer.create(getActivity().getApplicationContext(),R.raw.quiztimer);

        MediaPlayer mediaPlayerCorrect=MediaPlayer.create(getActivity().getApplicationContext(),R.raw.correct);
        MediaPlayer mediaPlayerWrong=MediaPlayer.create(getActivity().getApplicationContext(),R.raw.wrong);

        MediaPlayer mediaPlayerTimesup=MediaPlayer.create(getActivity().getApplicationContext(),R.raw.timesup);
//        MainActivity.binding.appBarMain1.fab.hide();

//   will use this code
        homeViewModel.getUserData().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser!=null){
                    Snackbar.make(container,"welcome",Snackbar.LENGTH_LONG).show();
                }else{
                    navController.navigate(R.id.loginFragment);
                    Snackbar.make(container,"Going to login fragment",Snackbar.LENGTH_LONG).show();
                }
            }
        });

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ConstraintLayout constraintLayout=binding.homeLayout;
        AnimationDrawable earth=(AnimationDrawable)constraintLayout.getBackground();
        earth.start();


        progressBar= binding.pbquizTimer;//Extra 5

        mediaPlayerTimer.start();
        mediaPlayerTimer.setLooping(true);
        countDownTimer = new CountDownTimer(45000,1000) {
            @Override
            public void onTick(long l) {
                timervalue-=1;
                progressBar.setProgress(timervalue);
            }

            @Override
            public void onFinish() {

                mediaPlayerTimer.stop();
                mediaPlayerTimesup.start();
                navController.navigate(R.id.scoreboardFragment);
            }
        }.start();



            defineID();

            list = new ArrayList<>();//Making of List

//        databaseReference= homeViewModel.getQuestionref();
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
//                    Modelclass modelclass = dataSnapshot.getValue(Modelclass.class);
//                    list.add(modelclass);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

            list.add(new Modelclass("In which year did the British come to India as traders?", "1500", "1600", "1700", "1800", "1600"));
            list.add(new Modelclass("Which was the first country to have a preamble?", "USA", "England", "United Kingdom", "South Africa", "USA"));
            list.add(new Modelclass("Who among the following can remove the governor of a state from office?", "Legislative Assembly", "Parliament", "President", "Supreme Court", "President"));
            list.add(new Modelclass("From which country, the method of Impeachment of the President of India is adopted from?", "UK", "UN", "USSR", "USA", "USA"));
            list.add(new Modelclass("In which year, the first meeting of Rajya Sabha was held in Independent India?", "1950", "1952", "1953", "1954", "1952"));
            list.add(new Modelclass("By which among the following acts, the East India Company lost its monopoly of trade with China?", "Pitts India act 1784", "The Charter Act of 1813", "The Charter Act of 1833", "The Charter Act of 1853", "The Charter Act of 1833"));
            list.add(new Modelclass("Which among the following is the most appropriate definition of Political liberty of Citizens of India ?", "Right to participate in the government and assume equal opportunity to assume highest office", "right to cast vote and participate in the election process", "Equal opportunity to freely move in the Political territories of India", "None of the above", "Right to participate in the government and assume equal opportunity to assume highest office"));
            list.add(new Modelclass("Arrange Himachal Pradesh, Manipur, Kerala, Maharastra in correct chronological order of creation?", "Maharastra, Kerala, Himachal Pradesh, Manipur", "Kerala, Maharastra, Manipur, Himachal Pradesh", "Kerala, Maharastra, Himachal Pradesh, Manipur", "Maharastra, Himachal Pradesh, Manipur, kerala", "Kerala, Maharastra, Himachal Pradesh, Manipur"));
            list.add(new Modelclass("Who among the following presidents of India was the first Chief Minister of Modern states in India?", "Dr. Shankar Dayal Sharma", "Neelam Sanjeeva Reddy", "Dr. Rajendra Prasad", "Dr. BD Jatti", "Neelam Sanjeeva Reddy"));
            list.add(new Modelclass("Which among the following term correctly represents the Zonal Councils?", "Advisory Bodies", "Actually Working Bodies", "Law Making authorities", "Administrative Bodies", "Advisory Bodies"));
            list.add(new Modelclass("The constitution of India mandates that State may reserve any post or appointment in favour of any backward class of citizens who are not adequately represented in the services under that State. Who decides whether such class of citizens is adequately represented or not?", "Governor", "President of India", "Supreme Court of India", "State itself", "State itself"));
            list.add(new Modelclass("Who among the following was not among the seven members of the Drafting Committee of the Constituent Assembly?", "Alladi Krishnaswamy Ayyar", "Dr. K M Munshi", "Syed Mohammad Saadullah", "G. V. Mavalankar", "G. V. Mavalankar"));
            list.add(new Modelclass("What was the Constitutional status of India when the Constitution of India came into force on 26th January, 1950?", "A Secular Republic", "A Socialist Secular Democratic Republic", "A Sovereign Democratic Republic", "A Sovereign Socialist Secular Democratic Republic", "A Sovereign Democratic Republic"));
            list.add(new Modelclass("Which Schedules contain the provisions regarding administration of Scheduled areas and Tribal areas?", "4th and 5th Schedules", "5th and 6th Schedules", "6th and 7th Schedules", "5th and 7th Schedules", "5th and 6th Schedules"));
            list.add(new Modelclass("In which year was the first Inter-State Council was set up?", "1990", "1991", "1992", "1993", "1990"));
            list.add(new Modelclass("Who is the common chairman of the five zonal councils?", "Union Home Minister", "Prime Minister", "President", "Governor", "Union Home Minister"));
            list.add(new Modelclass("Which of the following Statements are correct with respect to National Emergency?", "1. The Centre becomes entitled to give executive directions to a state on ‘any’ matter", "2. Parliament becomes empowered to make laws on any subject mentioned in the State List", "3. Prime Minister can modify the constitutional distribution of revenues between the centre and the states", "Only 1 and 2", "Only 1 and 2"));
            list.add(new Modelclass("In how many states the government headed by Morarji Desai imposed President’s Rule in 1977?", "9", "8", "6", "7", "9"));
            list.add(new Modelclass("What is the maximum period of operation of a Financial Emergency?", "Three months", "Six months", "One year", "Indefinitely till it is revoked", "Indefinitely till it is revoked"));
            list.add(new Modelclass("Who said that the “emergency financial provisions pose a serious threat to the financial autonomy of the States”?", "T.T. Krishnamachar", "H.N. Kunzru", "H.V. Kamath", "G. V. Mavalankar", "H.N. Kunzru"));
            list.add(new Modelclass("Who amongst the following is associated with judicial activism in India?", "Justice P.N. Bhagwati", "Justice O. Chinnappa Reddy", "Justice D.A. Desai", "All of the above", "All of the above"));
            list.add(new Modelclass("Who allocates and reshuffles the portfolios among ministers of state legislative assembly?", "Parliament", "Chief Minister", "Governor", "President", "Chief Minister"));
            list.add(new Modelclass("Which of the following leads to termination of session of house?", "Adjournment", "Adjournment sine die", "Prorogation", "Dissolution", "Prorogation"));
            list.add(new Modelclass("Which act regulates the procedure relating to the removal of a judge of a high court by the process of impeachment?", "Judges Enquiry Act (1948)", "Judges Enquiry Act (1958)", "Judges Enquiry Act (1960)", "Judges Enquiry Act (1968)", "Judges Enquiry Act (1968)"));
            list.add(new Modelclass("Who does the Finance Commission make recommendations to with regards to the financial position of the municipalities?", "Chief Minister", "MLA", "Governor", "Prime Minister", "Governor"));
            list.add(new Modelclass("Which article of the constitution provides control of the Union over the administration of Scheduled Areas and the welfare of Scheduled Tribes?", "244", "244A", "339", "340", "339"));
            list.add(new Modelclass("Who appoints the chairperson and members of State Human Rights Commission?", "Governor", "State Legislature", "President", "Cabinet", "Governor"));
            list.add(new Modelclass("When was the Customs Excise and Service Tax Appellate Tribunal (CESTAT) constituted?", "1992", "1982", "1972", "2002", "1982"));
            list.add(new Modelclass("In which year the two posts of election commissioners created in 1989 were abolished?", "1993", "1990", "1995", "2000", "1990"));
            list.add(new Modelclass("Which of the following fixes a ceiling on election expenses?", "Election commission of India", "Union Government", "Cabinet", "Supreme Court", "Union Government"));
            list.add(new Modelclass("Which of the following launched a robust Election Expenditure Monetary System mechanism to curb the influence of money power in elections?", "Election Commission", "Parliament", "President", "Supreme Court", "Election Commission"));
            list.add(new Modelclass("Which of the following act had set provisions for Electoral Trusts under the Income Tax Act 1961?", "Finance Act of 2006", "Finance Act of 2009", "Finance Act of 2011", "Finance Act of 2015", "Finance Act of 2009"));
            list.add(new Modelclass("In which year a Central Public Service Commission was set up and entrusted with the task of recruiting civil servants?", "1926", "1930", "1940", "1946", "1926"));
            list.add(new Modelclass("Which of the following are  the functions of the State Public Service Commission?\n" +
                    "1. It conducts examinations for appointments to the services of the state.\n" +
                    "2. It is consulted on matters related to personnel management.\n" +
                    "Select the correct option from the codes given below:", "Only 1", "Only 2", "Both 1 & 2", "Neither 1 & 2", "Both 1 & 2"));
            list.add(new Modelclass("The National Commission for Scheduled Tribes presents an annual report to which of the following?", "President", "Supreme Court", "High Court", "Lok Sabha", "President"));
            list.add(new Modelclass("Who appoints the Chief Information Commissioner of the Central Information Commission?", "Rajya Sabha", "A committee of Lok Sabha", "President", "Prime Minister", "President"));
            list.add(new Modelclass("The administrative expenses of the office of the CAG are charged upon which of the following?", "Contingency Fund of India", "Consolidated Fund of India", "Public Accounts of India", "Contingency Fund of State", "Consolidated Fund of India"));
            list.add(new Modelclass("How many offices are attached to NITI Aayog?", "1", "2", "3", "4", "2"));
            list.add(new Modelclass("Who appoints the Central Vigilance Commissioner?", "Supreme court", "President", "Speaker", "Parliament", "President"));
            list.add(new Modelclass("Which of the following articles of the constitution mention special provisions for the scheduled castes (SCs), the scheduled tribes (STs), the backward classes (BCs) and the Anglo-Indians?", "Articles 330 to 342A", "Articles 344 to 352A", "Articles 350 to 352A", "Articles 360 to 362A", "Articles 330 to 342A"));
            list.add(new Modelclass("On which of the following basis the seats are reserved for the SCs and STs in the Lok Sabha and the state legislative assemblies?", "Area", "Population", "Gender", "Locality", "Population"));
            list.add(new Modelclass("Environment Protection Act, 1986 consists of how many sections?", "25", "26", "23", "24", "26"));
            list.add(new Modelclass("Animal Welfare Board was established in which year?", "1962", "1967", "1963", "1964", "2"));
            list.add(new Modelclass("What is “Dhruv Mk – III MR”?", "ICAR’s new rice variety", "Advanced Light Helicopter", "Surface to Surface Missile", "Pulse doppler Radar", "Advanced Light Helicopter"));
            list.add(new Modelclass("Which state leads in sweet potato production across India?", "Andhra Pradesh", "Tamil Nadu", "Odisha", "Karnataka", "Odisha"));
            list.add(new Modelclass("Which state is to implement ‘Smart kitchen scheme’?", "Goa", "Karnataka", "Kerala", "Odisha", "Kerala"));
            list.add(new Modelclass("Which part of constitution deals with Fundamental Rights?", "1", "2", "3", "4", "3"));
            list.add(new Modelclass("Which part of constitution deals with Union Territory?", "7", "8", "5", "4", "8"));
            list.add(new Modelclass("Which part of constitution deals with Citizenship?", "1", "2", "3", "4", "2"));
            list.add(new Modelclass("Which part of constitution deals with Panchayats?", "8", "7", "9", "6", "9"));

            //mujhe external list daalni h isliye list array ko usse replace krunga
            qustionlist = list;
            Collections.shuffle(qustionlist);
            oneQuestion = list.get(index);

            setAllQuestionPattern();

//        Color.rgb(201, 248, 255)
        GradientDrawable backgroundshape1 = (GradientDrawable)option1.getBackground();
        backgroundshape1.setColor(Color.rgb(103, 58, 183));

        GradientDrawable backgroundshape2 = (GradientDrawable)option2.getBackground();
        backgroundshape2.setColor(Color.rgb(103, 58, 183));

        GradientDrawable backgroundshape3 = (GradientDrawable)option3.getBackground();
        backgroundshape3.setColor(Color.rgb(103, 58, 183));

        GradientDrawable backgroundshape4 = (GradientDrawable)option4.getBackground();
        backgroundshape4.setColor(Color.rgb(103, 58, 183));


 //           option1.setBackgroundColor(Color.rgb(156, 39, 176));
//            option2.setBackgroundColor(Color.rgb(156, 39, 176));
//            option3.setBackgroundColor(Color.rgb(156, 39, 176));
//            option4.setBackgroundColor(Color.rgb(156, 39, 176));



        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(oneQuestion.getAns().trim().toLowerCase(Locale.ROOT).equals(option1.getText().toString().trim().toLowerCase())){
                    //option1.setBackgroundColor(Color.rgb(128,255,128));
                    backgroundshape1.setColor(Color.rgb(128,255,128));
                    mediaPlayerCorrect.start();
                    correct();
                }
                else{
                    backgroundshape1.setColor(Color.rgb(255,128,128));
                    mediaPlayerWrong.start();
                    wrong();
                    mediaPlayerTimer.stop();
                    navController.navigate(R.id.scoreboardFragment);
                }
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(oneQuestion.getAns().trim().toLowerCase(Locale.ROOT).equals(option2.getText().toString().trim().toLowerCase())){
                    backgroundshape2.setColor(Color.rgb(128,255,128));
                    mediaPlayerCorrect.start();
                    correct();
                     }
                else{
                    backgroundshape2.setColor(Color.rgb(255,128,128));
                    mediaPlayerWrong.start();
                    wrong();
                    mediaPlayerTimer.stop();
                    navController.navigate(R.id.scoreboardFragment);
                }
            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(oneQuestion.getAns().trim().toLowerCase(Locale.ROOT).equals(option3.getText().toString().trim().toLowerCase())){
                    backgroundshape3.setColor(Color.rgb(128,255,128));
                    mediaPlayerCorrect.start();
                    correct();
                }
                else{
                    backgroundshape3.setColor(Color.rgb(255,128,128));
                    mediaPlayerWrong.start();
                    wrong();
                    mediaPlayerTimer.stop();
                    navController.navigate(R.id.scoreboardFragment);
                }
            }
        });

        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(oneQuestion.getAns().trim().toLowerCase(Locale.ROOT).equals(option4.getText().toString().trim().toLowerCase())){
                    backgroundshape4.setColor(Color.rgb(128,255,128));
                    mediaPlayerCorrect.start();
                    correct();
                }
                else{
                    backgroundshape4.setColor(Color.rgb(255,128,128));
                    mediaPlayerWrong.start();
                    wrong();
                    mediaPlayerTimer.stop();
                    navController.navigate(R.id.scoreboardFragment);
                }
            }
        });
        if(correctCountScore==16){
            correctCountScore=0;
            navController.navigate(R.id.scoreboardFragment);
        }



        return root;
    }


    private void setAllQuestionPattern() {
        question.setText(oneQuestion.getQuestion());
        option1.setText(oneQuestion.getoA());
        option2.setText(oneQuestion.getoB());
        option3.setText(oneQuestion.getoC());
        option4.setText(oneQuestion.getoD());

    }

    private void defineID(){
        question=binding.tvquestion;
        option1=binding.btnoption1;
        option2= binding.btnoption2;
        option3= binding.btnoption3;
        option4= binding.btnoption4;

       // playAgain=getView().findViewById(R.id.btnplay_again);
    }

    private void correct(){

        correctCountScore++;
        homeViewModel.setScore(correctCountScore);
        index++;
        timervalue=45;
        progressBar.setProgress(timervalue);
        GradientDrawable backgroundshape1 = (GradientDrawable)option1.getBackground();
        backgroundshape1.setColor(Color.rgb(103, 58, 183));

        GradientDrawable backgroundshape2 = (GradientDrawable)option2.getBackground();
        backgroundshape2.setColor(Color.rgb(103, 58, 183));

        GradientDrawable backgroundshape3 = (GradientDrawable)option3.getBackground();
        backgroundshape3.setColor(Color.rgb(103, 58, 183));

        GradientDrawable backgroundshape4 = (GradientDrawable)option4.getBackground();
        backgroundshape4.setColor(Color.rgb(103, 58, 183));
        oneQuestion=list.get(index); //model class is one question
        setAllQuestionPattern();

    }
    private void wrong(){

        if(oneQuestion.getAns().trim().toLowerCase(Locale.ROOT).equals(option1.getText().toString().trim().toLowerCase())) {

            GradientDrawable backgroundshape1 = (GradientDrawable)option1.getBackground();
            backgroundshape1.setColor(Color.rgb(128, 255, 128));
            //option1.setBackgroundColor(Color.rgb(128, 255, 128));
        }else if(oneQuestion.getAns().trim().toLowerCase(Locale.ROOT).equals(option2.getText().toString().trim().toLowerCase())) {
            GradientDrawable backgroundshape2 = (GradientDrawable)option2.getBackground();
            backgroundshape2.setColor(Color.rgb(128, 255, 128));
        }else if(oneQuestion.getAns().trim().toLowerCase(Locale.ROOT).equals(option3.getText().toString().trim().toLowerCase())) {
            GradientDrawable backgroundshape3 = (GradientDrawable)option3.getBackground();
            backgroundshape3.setColor(Color.rgb(128, 255, 128));
        }else if(oneQuestion.getAns().trim().toLowerCase(Locale.ROOT).equals(option4.getText().toString().trim().toLowerCase())) {
            GradientDrawable backgroundshape4 = (GradientDrawable)option4.getBackground();
            backgroundshape4.setColor(Color.rgb(128, 255, 128));
        }

       // correctCountScore=0;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}