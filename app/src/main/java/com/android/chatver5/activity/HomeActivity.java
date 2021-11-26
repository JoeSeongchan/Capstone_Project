package com.android.chatver5.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.chatver5.R;
import com.android.chatver5.briefchatgroup.adapter.BriefChatGroupAdapter;
import com.android.chatver5.briefchatgroup.adapter.BriefChatGroupAdapter.BriefChatGroupDiff;
import com.android.chatver5.databinding.ActivityHomeBinding;
import com.android.chatver5.db.data.User;

public class HomeActivity extends AppCompatActivity {

  User curUser;
  private ActivityHomeBinding binding;
  private RecyclerView rv;
  private BriefChatGroupAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityHomeBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    Bundle bundle = getIntent().getExtras();
    this.curUser = (User) bundle.getSerializable("CUR_USER");
    binding.tvNickHome.setText(getString(R.string.tv_nick_chatGroup,
        curUser.getNick()));

    // recycler view 설정.
    this.rv = binding.rvPartyListHome;
    this.adapter = new BriefChatGroupAdapter(new BriefChatGroupDiff(), this::startChatAct);
    this.rv.setAdapter(this.adapter);
    this.rv.setLayoutManager(new LinearLayoutManager(this));
    this.rv.setItemAnimator(null);

  }
}