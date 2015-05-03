package com.mbcdev.nextluas.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.mbcdev.nextluas.R;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class ProblemActivity extends Activity {

    @InjectView(R.id.txtIncorrectTimes)
    TextView txtIncorrectTimes;

    @InjectView(R.id.txtMissingTimes)
    TextView txtMissingTimes;

    @InjectView(R.id.txtOtherProblem)
    TextView txtOtherProblem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);

        ButterKnife.inject(this);

        txtIncorrectTimes.setMovementMethod(LinkMovementMethod.getInstance());
        txtIncorrectTimes.setText(Html.fromHtml(getString(R.string.incorrect_times_html)));

        txtMissingTimes.setMovementMethod(LinkMovementMethod.getInstance());
        txtMissingTimes.setText((Html.fromHtml(getString(R.string.missing_times_html))));

        txtOtherProblem.setMovementMethod(LinkMovementMethod.getInstance());
        txtOtherProblem.setText(Html.fromHtml(getString(R.string.other_problem_html)));
    }

}
