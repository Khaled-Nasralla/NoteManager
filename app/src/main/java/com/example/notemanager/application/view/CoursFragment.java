package com.example.notemanager.application.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.notemanager.R;
import com.example.notemanager.application.listener.Listener;
import com.example.notemanager.application.model.Course;
import com.example.notemanager.application.viewmodel.CourseViewModel;

import java.util.UUID;

public class CoursFragment extends Fragment {

    private CourseViewModel coursViewModel;
    private LinearLayout mCourseListLayout;
    private UUID courseId;
    private Listener.OnTitleChangeListener mListener;

    public CoursFragment(UUID courseId, Listener.OnTitleChangeListener listener) {
        this.courseId = courseId;
        this.mListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cours, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        coursViewModel = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);
        mCourseListLayout = view.findViewById(R.id.cours_list);
        updateUI();
    }

    private void updateUI() {
        coursViewModel.getAllCourses(courseId).observe(getViewLifecycleOwner(), courses -> {
            mCourseListLayout.removeAllViews();
            for (Course course : courses) {
                mCourseListLayout.addView(getCourseView(course));
            }
        });
    }


    @SuppressLint("SetTextI18n")
    private View getCourseView(final Course course) {
        LinearLayout columnForCourse = getLinearLayout();
        LinearLayout buttonLayout = new LinearLayout(requireContext());
        buttonLayout.setBackgroundColor(getResources().getColor(R.color.greenblue));
        TextView courseName = getTextView(course.getName());
        TextView studentsButton = getTextView(String.valueOf(course.getCredit()));

        // Set layout parameters to align the buttonLayout to the right
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
        );
        courseName.setLayoutParams(params);


        studentsButton.setText(course.getCredit() +"C");
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        buttonParams.gravity = Gravity.END;
        buttonLayout.setLayoutParams(buttonParams);

        studentsButton.setOnClickListener(v -> {
            // Implement your logic here
        });
        buttonLayout.addView(studentsButton);
        columnForCourse.addView(courseName);
        columnForCourse.addView(buttonLayout);
        return columnForCourse;
    }

    private LinearLayout getLinearLayout() {
        LinearLayout linearLayout = new LinearLayout(requireContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setPadding(8, 15, 8, 15);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        linearLayout.setOnClickListener(v -> {
           SubEvaluationFragments subEvaluationFragments = new SubEvaluationFragments(courseId);
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, subEvaluationFragments)
                    .addToBackStack(null)
                    .commit();
            mListener.onFragmentsChange(getString(R.string.title_fragment4), "Evaluation", courseId);
           });
        params.setMargins(10, 0, 10, 10);
        linearLayout.setLayoutParams(params);
        linearLayout.setBackgroundResource(R.drawable.border);
        linearLayout.setBackgroundColor(getResources().getColor(R.color.greenblue));
        return linearLayout;
    }

    private TextView getTextView(String text) {
        TextView textView = new TextView(requireContext());
        textView.setTextSize(20);
        textView.setPadding(8, 8, 8, 8);
        textView.setText(text);
        textView.setTextColor(getResources().getColor(R.color.white));
        return textView;
    }




}