package com.mbcdev.nextluas.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.mbcdev.nextluas.R;


public class ProblemActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);

        TextView txtIncorrectTimes = findViewById(R.id.txtIncorrectTimes);
        TextView txtMissingTimes = findViewById(R.id.txtMissingTimes);
        TextView txtOtherProblem = findViewById(R.id.txtOtherProblem);

        txtIncorrectTimes.setMovementMethod(LinkMovementMethod.getInstance());
        txtIncorrectTimes.setText(Html.fromHtml(getString(R.string.incorrect_times_html)));

        txtMissingTimes.setMovementMethod(LinkMovementMethod.getInstance());
        txtMissingTimes.setText((Html.fromHtml(getString(R.string.missing_times_html))));

        txtOtherProblem.setMovementMethod(LinkMovementMethod.getInstance());
        txtOtherProblem.setText(Html.fromHtml(getString(R.string.other_problem_html)));
    }

}
