package com.techparew.x_files.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.techparew.x_files.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuickSetupPreferenceLocation.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuickSetupPreferenceLocation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuickSetupPreferenceLocation extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM1 = "position";
    private static final String ARG_PARAM2 = "param2";
    private View rootview;
    private int fragmentPosition;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private Spinner stateSpinner;
    private Spinner citySpinner;

    public QuickSetupPreferenceLocation() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment QuickSetupPreferenceLocation.
     */
    // TODO: Rename and change types and number of parameters
    public static QuickSetupPreferenceLocation newInstance(int position,OnFragmentInteractionListener listener) {
         QuickSetupPreferenceLocation fragment = new QuickSetupPreferenceLocation();
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
            fragmentPosition = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_quick_setup_preference_location, container, false);

        stateSpinner = (Spinner) rootview.findViewById(R.id.stateSpinner);
        citySpinner = (Spinner) rootview.findViewById(R.id.citySpinner);
        ArrayList<String> cityList = new ArrayList<>();
        cityList.clear();
        cityList.add("Uberlandia");
//        cityList.add("Belo Horizonte");
//        cityList.add("Uberaba");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(rootview.getContext(),R.array.state_array,android.R.layout.simple_list_item_1);
        ArrayAdapter<String> adapterCity= new ArrayAdapter<String>(rootview.getContext(),android.R.layout.simple_list_item_1,cityList);
        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(adapter);
        stateSpinner.setEnabled(false);
        citySpinner.setAdapter(adapterCity);
        citySpinner.setEnabled(false);

        return rootview;

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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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

}
