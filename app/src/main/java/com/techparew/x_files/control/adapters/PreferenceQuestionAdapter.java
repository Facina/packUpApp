package com.techparew.x_files.control.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.techparew.x_files.model.PreferenceQuestion;
import com.techparew.x_files.R;

import java.util.ArrayList;
import java.util.List;

public class PreferenceQuestionAdapter extends RecyclerView.Adapter<PreferenceQuestionAdapter.WordViewHolder> {
    private ArrayList<PreferenceQuestion> preferenceQuestionList ;

    //private static RecyclerViewClickListener itemListener;
    private Context context;
    private SharedPreferences user_account;
    String preferences = "USER_PREFERENCES";

//    public interface RecyclerViewClickListener
//    {
//        public void recyclerViewListClicked(View v, int position);
//    }


   // public PreferenceQuestionAdapter(Context applicationContext, ArrayList<PreferenceQuestion> questions, RecyclerViewClickListener itemListener){
      public PreferenceQuestionAdapter(Context applicationContext, ArrayList<PreferenceQuestion> questions){

            this.context = applicationContext;
        this.preferenceQuestionList = questions;
       // this.itemListener = itemListener;
    }


    public class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView questionBinary,questionRating,questionEditable;
        public ToggleButton answerBinary;
        private LinearLayout binaryQuestionLinearLayout,ratingQuestionLinearLayout,editableQuestionLinearLayout;
        public RatingBar ratingBar;

        public WordViewHolder(View view) {
            super(view);
            ratingQuestionLinearLayout = (LinearLayout) view.findViewById(R.id.ratingQuestionItemTypeLinearLayout);
            questionRating = (TextView)  view.findViewById(R.id.ratingListItemTextView);
            questionEditable = (TextView)  view.findViewById(R.id.editableListItemTextView);
            editableQuestionLinearLayout = (LinearLayout) view.findViewById(R.id.editableQuestionItemTypeLinearLayout);
            ratingBar = (RatingBar) view.findViewById(R.id.ratingListItemRatingBar);
            binaryQuestionLinearLayout = (LinearLayout) view.findViewById(R.id.binaryQuestionItemTypeLinearLayout);
            questionBinary = (TextView)  view.findViewById(R.id.questionListItemTextView);
            answerBinary = (ToggleButton) view.findViewById(R.id.questionListItemBinaryToggleButton);
            //view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //itemListener.recyclerViewListClicked(view, this.getLayoutPosition());

        }
    }




    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_list_item, parent, false);

        return new WordViewHolder(itemView);

    }

    public void updateReceiptsList(List<PreferenceQuestion> newlist) {
        preferenceQuestionList.clear();
        preferenceQuestionList.addAll(newlist);
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final WordViewHolder holder, int position) {

        PreferenceQuestion question = preferenceQuestionList.get(position);

        if(question.getQuestionType()==1) {
            holder.editableQuestionLinearLayout.setVisibility(View.GONE);
            holder.ratingQuestionLinearLayout.setVisibility(View.GONE);
            holder.binaryQuestionLinearLayout.setVisibility(View.VISIBLE);

            holder.answerBinary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    if(holder.answerBinary.getRotation()==180) holder.answerBinary.animate().rotation(0).start();
                    else holder.answerBinary.animate().rotation(180).start();

                //    Log.d("PreferenceQuestionAdap:"," state = " + holder.answerBinary.isChecked());
                }
            });

            holder.questionBinary.setText(question.getQuestion());
        }else if (question.getQuestionType()==2){
            holder.questionRating.setText(question.getQuestion());
            holder.ratingQuestionLinearLayout.setVisibility(View.VISIBLE);
            holder.binaryQuestionLinearLayout.setVisibility(View.GONE);
            holder.editableQuestionLinearLayout.setVisibility(View.GONE);

        }else{

            holder.questionEditable.setText(question.getQuestion());
            holder.editableQuestionLinearLayout.setVisibility(View.VISIBLE);
            holder.ratingQuestionLinearLayout.setVisibility(View.GONE);
            holder.binaryQuestionLinearLayout.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return preferenceQuestionList.size();
    }





}
