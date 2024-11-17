package com.example.notemanager.application.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.notemanager.R;
import com.example.notemanager.application.listener.Listener;
import com.example.notemanager.application.model.SubEvaluation;
import com.example.notemanager.application.viewmodel.CourseViewModel;

import java.util.UUID;

public class AddSubEvaluationDialog extends BaseDialogFragment {

    private UUID courseId;
    private LinearLayout mSubEvaluationListLayout;
    Listener.OnEvaluationAddedListener mListener;

    public AddSubEvaluationDialog() {

    }

    public static AddSubEvaluationDialog newInstance(UUID courseId) {
        AddSubEvaluationDialog fragment = new AddSubEvaluationDialog();
        Bundle args = new Bundle();
        args.putSerializable("courseId", courseId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.add_subevaluation;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            courseId = (UUID) getArguments().getSerializable("courseId");
        }

    }


    @SuppressLint("SetTextI18n")
    private View getSubEvaluationView(final SubEvaluation subEvaluation) {
        LinearLayout columnForSubEvaluation = getLinearLayout();
        TextView subEvaluationName = getTextView();
        TextView subEvaluationDate = getTextView();
        columnForSubEvaluation.addView(subEvaluationName);
        columnForSubEvaluation.addView(subEvaluationDate);
        columnForSubEvaluation.setTag(subEvaluation);
        return columnForSubEvaluation;
}

    private TextView getTextView() {
        TextView textView = new TextView(requireContext());
        textView.setTextSize(20);
        textView.setPadding(8, 8, 8, 8);
        textView.setGravity(Gravity.LEFT);
        return textView;
    }

    private LinearLayout getLinearLayout() {
        LinearLayout linearLayout = new LinearLayout(requireContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setPadding(8, 8, 8, 8);
        return linearLayout;
    }


    public void setOnEvaluationAddedListener(Listener.OnEvaluationAddedListener mListener) {
      this.mListener = mListener;
    }
}
