package com.techparew.x_files.view.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techparew.x_files.control.adapters.PreferenceQuestionAdapter;
import com.techparew.x_files.control.SQLite.contracts.PreferenceAnswerContract;
import com.techparew.x_files.model.PreferenceQuestion;
import com.techparew.x_files.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuickSetupPreferenceQuestions.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuickSetupPreferenceQuestions#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuickSetupPreferenceQuestions extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "position";
    private static final String ARG_PARAM2 = "param2";
    private View rootView;
    // TODO: Rename and change types of parameters
    private int fragmentPosition;
    private String mParam2;
    private RecyclerView questionsRecyclerView;
    private ArrayList<PreferenceQuestion> questionList = new ArrayList<>();
    private PreferenceQuestionAdapter preferenceQuestionAdapter;
    private CardView nextButton;
    private CardView backButton;
    SQLiteDatabase db;
    PreferenceAnswerContract.PreferenceAnswerDbHelper dbHelper;

    private OnFragmentInteractionListener mListener;

    public QuickSetupPreferenceQuestions() {
        // Required empty public constructor
    }


    public static QuickSetupPreferenceQuestions newInstance(int position,OnFragmentInteractionListener listener) {
        QuickSetupPreferenceQuestions fragment = new QuickSetupPreferenceQuestions();
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
        rootView = inflater.inflate(R.layout.fragment_quick_setup_preference_questions, container, false);

        findingViews();
        fetchQuestions();
        setRecycleView();

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
        if(dbHelper!=null)dbHelper.close();
        mListener = null;

        super.onDetach();
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


    protected void findingViews(){

    questionsRecyclerView = rootView.findViewById(R.id.quickSetupQuestionsRecyclerView);
    nextButton = (CardView) rootView.findViewById(R.id.quickSetupAcceptButton);
    backButton = (CardView) rootView.findViewById(R.id.quickSetupDeclineButton);

    }


    private void setButtons(){
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAnswer();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity()!=null){
                    if(fragmentPosition==0) {
                        getActivity().finish();
                    }else{
                        mListener.onFragmentInteraction(fragmentPosition-1);
                    }
                }
            }
        });
    }



    private void setRecycleView(){
        preferenceQuestionAdapter = new PreferenceQuestionAdapter(rootView.getContext(),questionList);
        questionsRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext(),LinearLayoutManager.VERTICAL,false));
        questionsRecyclerView.setAdapter(preferenceQuestionAdapter);
    }

    private void fetchQuestions(){

        questionList.clear();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.publicInformation),Context.MODE_PRIVATE);

        int amountOfQuestions = sharedPreferences.getInt(getResources().getString(R.string.numberOfPreferenceQuestions),0);

        for(int i=0;i<amountOfQuestions;i++){
            int id = sharedPreferences.getInt(getResources().getString(R.string.tagIdPreferenceQuestion)+i,1);
            String question = sharedPreferences.getString(getResources().getString(R.string.tagQuestion)+i,"No question");

            int questionType = sharedPreferences.getInt(getResources().getString(R.string.tagQuestionType)+i,1);

            questionList.add(new PreferenceQuestion(id,question,questionType));
        }
//
//        questionList.add(new PreferenceQuestion(1,"Você gosta de sair?",1));
//        questionList.add(new PreferenceQuestion(1,"Quão organizado gostaria que seu colega fosse?",2));
//        questionList.add(new PreferenceQuestion(1,"Qual nivel sonoro do seu colega é aceitável?",2));
//        questionList.add(new PreferenceQuestion(1,"Com que frequências seu colega pode trazer visitas?",2));
//        questionList.add(new PreferenceQuestion(1,"Frase de impacto:",3));

    }

    private void saveAnswer(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.userInformation),Context.MODE_PRIVATE);
        int idAccount = sharedPreferences.getInt(getResources().getString(R.string.serverIdAccount),-1);
        dbHelper = new PreferenceAnswerContract.PreferenceAnswerDbHelper(getContext());
    // Gets the data repository in write mode
    db = dbHelper.getWritableDatabase();


    dbHelper.onUpgrade(db,1,1);

    for(int i=0;i<questionList.size();i++) {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        PreferenceQuestionAdapter.WordViewHolder holder = (PreferenceQuestionAdapter.WordViewHolder) questionsRecyclerView.getChildViewHolder(questionsRecyclerView.getChildAt(i));
        int test =1-(int) (holder.answerBinary.getRotation()/180);
        int rating =(int) holder.ratingBar.getRating();
        values.put(PreferenceAnswerContract.PreferenceAnswer.COLUMN_ID_ACCOUNT, idAccount);
        values.put(PreferenceAnswerContract.PreferenceAnswer.COLUMN_ID_PREFERENCE_QUESTION, questionList.get(i).getIdPreferenceQuestion());

        if(questionList.get(i).getQuestionType()==1) {
            Log.e("test=", " " + test);
            values.put(PreferenceAnswerContract.PreferenceAnswer.COLUMN_ANSWER, ""+test);

        }else if(questionList.get(i).getQuestionType()==2){
            Log.e("test="," "+rating);
            values.put(PreferenceAnswerContract.PreferenceAnswer.COLUMN_ANSWER, ""+rating);

        }
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(PreferenceAnswerContract.PreferenceAnswer.TABLE_NAME, null, values);
    }


    //READING ALL


        SQLiteDatabase db2 = dbHelper.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                PreferenceAnswerContract.PreferenceAnswer.COLUMN_ANSWER,
                PreferenceAnswerContract.PreferenceAnswer.COLUMN_ID_PREFERENCE_ANSWER
        };

// Filter results WHERE "title" = 'My Title'
        String selection = PreferenceAnswerContract.PreferenceAnswer.COLUMN_ID_ACCOUNT + " = ?";
        String[] selectionArgs = { "My Title" };

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                PreferenceAnswerContract.PreferenceAnswer.COLUMN_ID_PREFERENCE_ANSWER + " ASC";

        Cursor cursor = db2.query(
                PreferenceAnswerContract.PreferenceAnswer.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );


        while(cursor.moveToNext()) {
            String itemId = cursor.getString(
                    cursor.getColumnIndexOrThrow(PreferenceAnswerContract.PreferenceAnswer.COLUMN_ANSWER));
            int idAnswer = cursor.getInt(
                    cursor.getColumnIndexOrThrow(PreferenceAnswerContract.PreferenceAnswer.COLUMN_ID_PREFERENCE_ANSWER));
            int idAcount = cursor.getInt(
                    cursor.getColumnIndexOrThrow(PreferenceAnswerContract.PreferenceAnswer.COLUMN_ID_ACCOUNT));
            int idQuestion = cursor.getInt(
                    cursor.getColumnIndexOrThrow(PreferenceAnswerContract.PreferenceAnswer.COLUMN_ID_PREFERENCE_QUESTION));
            Log.e("list"," "+idAcount+" "+ idQuestion+ " "+ idAnswer+" "+ itemId ) ;

        }
        cursor.close();
    }

}
