package com.techparew.x_files.view.fragment;

import android.content.Context;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.techparew.x_files.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuickSetupPreferenceSexOption.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuickSetupPreferenceSexOption#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuickSetupPreferenceSexOption extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "position";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;
    private CardView nextButton;
    private CardView backButton;
    boolean female = true;
    boolean male = false;
    boolean transgender = false;
    private ImageView femaleIconImageView;
    private ImageView maleIconImageView;
    private ImageView transgenderIconImageView;
    private TextView femaleTextView;
    private TextView maleTextView;
    private TextView headerTextView;
    private TextView questionTextView;
    private TextView transgenderTextView;
    private LinearLayout linearLayoutFemale;
    private LinearLayout linearLayoutMale;
    private LinearLayout linearLayoutTransgender;

    View rootView;

    private OnFragmentInteractionListener mListener;

    public QuickSetupPreferenceSexOption() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment QuickSetupPreferenceSexOption.
     */
    // TODO: Rename and change types and number of parameters
    public static QuickSetupPreferenceSexOption newInstance(int position,OnFragmentInteractionListener listener) {
        QuickSetupPreferenceSexOption fragment = new QuickSetupPreferenceSexOption();
        Bundle args = new Bundle();
        args.putInt("position",position);
        fragment.setArguments(args);
        fragment.setmListener(listener);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_quick_setup_preference_sex_option, container, false);


        findingViews();
        reapplyColor(female,male,transgender);
        toggleGender();
        setButtons();


        return rootView;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int position);
    }


    public OnFragmentInteractionListener getmListener() {
        return mListener;
    }



    public void setmListener(OnFragmentInteractionListener mListener){
        this.mListener =mListener;
    }

    private void setButtons(){
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(male|female|transgender){

                mListener.onFragmentInteraction(mParam1+1);

                }else {
                    Toast.makeText(rootView.getContext(),getResources().getString(R.string.warningSelectGender),Toast.LENGTH_LONG).show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity()!=null){
                    if(mParam1==0) {
                        getActivity().finish();
                    }else{
                        mListener.onFragmentInteraction(mParam1-1);
                    }
                }
            }
        });
    }


    private void reapplyColor(boolean female,boolean male,boolean transgender){

        if(!female) {

            int color = ContextCompat.getColor(rootView.getContext(), R.color.grey);
            femaleIconImageView.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            femaleTextView.setTextColor(getResources().getColor(R.color.grey));
            femaleTextView.setShadowLayer(0, 0, 0, getResources().getColor(R.color.white));

        }else {
            int color = ContextCompat.getColor(rootView.getContext(), R.color.white);
            femaleIconImageView.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            femaleTextView.setTextColor(getResources().getColor(R.color.white));
            femaleTextView.setShadowLayer(20,0,0,getResources().getColor(R.color.grey));
        }
        if(!male){

            int color = ContextCompat.getColor(rootView.getContext(), R.color.grey);
            maleIconImageView.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            maleTextView.setTextColor(getResources().getColor(R.color.grey));
            maleTextView.setShadowLayer(0, 0, 0, getResources().getColor(R.color.white));

        }else{
            int color = ContextCompat.getColor(rootView.getContext(), R.color.white);
            maleIconImageView.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            maleTextView.setTextColor(getResources().getColor(R.color.white));
            maleTextView.setShadowLayer(20,0,0,getResources().getColor(R.color.grey));

        }

        if(!transgender){

            int color = ContextCompat.getColor(rootView.getContext(), R.color.grey);
            transgenderIconImageView.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            transgenderTextView.setTextColor(getResources().getColor(R.color.grey));
            transgenderTextView.setShadowLayer(0, 0, 0, getResources().getColor(R.color.white));

        }else{
            int color = ContextCompat.getColor(rootView.getContext(), R.color.white);
            transgenderIconImageView.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            transgenderTextView.setTextColor(getResources().getColor(R.color.white));
            transgenderTextView.setShadowLayer(20,0,0,getResources().getColor(R.color.grey));

        }


    }

    protected void findingViews(){

        femaleIconImageView = (ImageView) rootView.findViewById(R.id.iconFemale);
        maleIconImageView = (ImageView)  rootView.findViewById(R.id.iconMale);
        transgenderIconImageView = (ImageView) rootView.findViewById(R.id.iconTransgender);
        femaleTextView = (TextView) rootView.findViewById(R.id.textViewFemale);
        maleTextView = (TextView) rootView.findViewById(R.id.textViewMale);
        transgenderTextView = (TextView) rootView.findViewById(R.id.textViewTransgender);
        linearLayoutFemale = (LinearLayout) rootView.findViewById(R.id.linearLayoutFemale);
        linearLayoutMale = (LinearLayout) rootView.findViewById(R.id.linearLayoutMale);
        linearLayoutTransgender = (LinearLayout) rootView.findViewById(R.id.linearLayoutTransgender);
        nextButton = (CardView) rootView.findViewById(R.id.quickSetupAcceptButton);
        backButton = (CardView) rootView.findViewById(R.id.quickSetupDeclineButton);
        headerTextView = (TextView) rootView.findViewById(R.id.quickSetupGenderHeader);
        questionTextView = (TextView) rootView.findViewById(R.id.quickSetupGenderQuestion);

        if(mParam1==1){
            headerTextView.setText(R.string.quickSetupGenderHeader2);
            questionTextView.setText(R.string.quickSetupGenderQuestion2);
        }


    }



    private void toggleGender(){

        if(mParam1==0){
            linearLayoutTransgender.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!transgender){
                        male=false;
                        female=false;
                        transgender=true;
                        reapplyColor(female,male,transgender);
                    }else{
                        transgender=false;
                        reapplyColor(female,male,transgender);

                    }
                }
            });

            linearLayoutFemale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!female){
                        transgender=false;
                        male = false;
                        female=true;
                        reapplyColor(female,male,transgender);

                    }else{
                        female=false;
                        reapplyColor(female,male,transgender);

                    }
                }
            });


            linearLayoutMale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!male){
                        male=true;
                        female=false;
                        transgender=false;
                        reapplyColor(female,male,transgender);

                    }else{
                        male=false;
                        reapplyColor(female,male,transgender);

                    }
                }
            });
        }else {
            linearLayoutTransgender.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!transgender){
                        transgender=true;
                        reapplyColor(female,male,transgender);
                    }else{
                        transgender=false;
                        reapplyColor(female,male,transgender);

                    }
                }
            });

            linearLayoutFemale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!female){

                        female=true;
                        reapplyColor(female,male,transgender);

                    }else{
                        female=false;
                        reapplyColor(female,male,transgender);

                    }
                }
            });


            linearLayoutMale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!male){
                        male=true;
                        reapplyColor(female,male,transgender);

                    }else{
                        male=false;
                        reapplyColor(female,male,transgender);

                    }
                }
            });
        }
    }
}
