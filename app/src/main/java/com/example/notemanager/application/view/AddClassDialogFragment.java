package com.example.notemanager.application.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.notemanager.R;
import com.example.notemanager.application.listener.Listener;
import com.example.notemanager.application.model.Classes;

import java.util.UUID;

public class AddClassDialogFragment extends BaseDialogFragment {
    private EditText classNameEditText;
    private Listener.OnClassAddedListener listener;

    public static AddClassDialogFragment newInstance() {
        return new AddClassDialogFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.add_class_fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        classNameEditText = view.findViewById(R.id.course_title);
        Button addButton = view.findViewById(R.id.submit_id);
        addButton.setOnClickListener(v -> {
            String className = classNameEditText.getText().toString();
            if (!className.isEmpty()) {
                Classes newClass = new Classes(className, UUID.randomUUID());
                if (listener != null) {
                    listener.onCourseAdded(newClass);
                }
            }

            dismiss();
        });
    }

    public void setOnClassAddedListener(Listener.OnClassAddedListener listener) {
        this.listener = listener;
    }
}
