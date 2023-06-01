package com.example.newanimals.fragment;

import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.newanimals.R;
import com.example.newanimals.db.ForumData;
import com.example.newanimals.utils.SPHelper;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;

public class ForumChatFragment extends BaseFragment {
    @BindView(R.id.fab)
    FloatingActionButton button;
    @BindView(R.id.input)
    EditText input;
    @BindView(R.id.list_of_messages)
    ListView listOfMessage;
    @BindView(R.id.message_text)
    TextView messageText;
    @BindView(R.id.message_user)
    TextView messageUser;
    @BindView(R.id.message_time)
    TextView messageTime;
    private FirebaseListAdapter<ForumData> adapter;
    @Override
    protected void initViews() {
        super.initViews();
        button.setOnClickListener(v->{
            FirebaseDatabase.getInstance()
                    .getReference()
                    .push()
                    .setValue(new ForumData(SPHelper.getName()+" "+SPHelper.getSurname().substring(0,1)+".",
                            input.getText().toString(),SPHelper.getUrlPhoto()));
        input.setText("");
        });

        adapter = new FirebaseListAdapter<ForumData>(getActivity(), ForumData.class, R.layout.forum_chat_item, FirebaseDatabase.getInstance().getReference()) {
            @Override
            protected void populateView(View v, ForumData model, int position) {
                messageText.setText(model.getText());
                messageUser.setText(model.getName());
                messageTime.setText(DateFormat.format("dd-MM HH:mm",model.getTime()));
            }
        };
        listOfMessage.setAdapter(adapter);
    }

    @Override
    protected int layoutId() {
        return R.layout.forum_chat_layout;
    }
}
