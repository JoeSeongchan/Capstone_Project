package com.android.chatver5.activity;


import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.android.chatver5.R;
import com.android.chatver5.briefchatgroup.adapter.BriefChatGroupAdapter;
import com.android.chatver5.briefchatgroup.adapter.BriefChatGroupAdapter.BriefChatGroupDiff;
import com.android.chatver5.db.data.BriefChatGroup;
import com.android.chatver5.db.data.ChatGroup;
import com.android.chatver5.db.firedbmanager.BriefChatGroupDbManager;
import com.android.chatver5.db.firedbmanager.ChatGroupDbManager;
import com.android.chatver5.db.roomdb.viewmodel.BriefChatGroupViewModel;

public class ChatGroupActivity extends AppCompatActivity {

  public static final String SWIPE_RIGHT_COLOR = "#FF9800";
  private static final boolean DEVELOPER_MODE = true;
  private String myNick;
  private BriefChatGroupViewModel model;
  private RecyclerView rv;
  private BriefChatGroupAdapter adapter;
  private BriefChatGroupDbManager briefGroupDbManager;
  private ChatGroupDbManager groupDbManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setUi();
    getNick();
    setDb();
  }

  private void onItemClick(String groupId) {
    Intent intent = new Intent(this, ChatActivity.class);
    intent.putExtra("groupId", groupId);
    intent.putExtra("myNick", myNick);
    startActivity(intent);
  }

  private void setUi() {
    setContentView(R.layout.activity_chat_group);

    // set recycler view.
    this.rv = findViewById(R.id.recyclerView);
    this.adapter = new BriefChatGroupAdapter(new BriefChatGroupDiff(), this::onItemClick);

    this.rv.setAdapter(this.adapter);
    this.rv.setLayoutManager(new LinearLayoutManager(this));
    rv.setItemAnimator(new DefaultItemAnimator() {
      @Override
      public boolean animateAdd(ViewHolder holder) {
        dispatchAddFinished(holder);
        return true;
      }
    });
    setSwipe();
  }

  private void setSwipe() {
    ItemTouchHelper.SimpleCallback swipeClbk = new ItemTouchHelper.SimpleCallback(0,
        ItemTouchHelper.LEFT) {

      @Override
      public boolean onMove(@NonNull RecyclerView recyclerView,
          @NonNull RecyclerView.ViewHolder viewHolder,
          @NonNull RecyclerView.ViewHolder target) {
        return false;
      }

      @Override
      public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        if (direction == ItemTouchHelper.LEFT) {
          briefGroupDbManager.deleteItem(adapter.getItem(position));
          groupDbManager.deleteItem(adapter.getItem(position).getPrimaryKey());
        }
      }

      @Override
      public void onChildDraw(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView,
          @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState,
          boolean isCurrentlyActive) {
        Paint paint = new Paint();
        Drawable icon;
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
          View itemView = viewHolder.itemView;
          int height = itemView.getBottom() - itemView.getTop();
          int width = itemView.getRight() - itemView.getLeft();
          float iconH = getResources().getDisplayMetrics().density * 28;
          float iconW = getResources().getDisplayMetrics().density * 28;

          if (dX < 0) {
            RectF background = new RectF((float) itemView.getRight() + dX,
                (float) itemView.getTop(), (float) itemView.getRight(),
                (float) itemView.getBottom());
            paint.setColor(Color.parseColor("#E91E63"));
            canvas.drawRect(background, paint);

            icon = ContextCompat.getDrawable(getBaseContext(), android.R.drawable.ic_menu_delete);
            icon.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

            float rate = Math.abs(dX) / width;

            int iconLeft = (int) (itemView.getRight() - width / 3 * rate);
            int iconTop = (int) (itemView.getTop() + height / 2 - iconH / 2);
            int iconRight = (int) (itemView.getRight() + iconW - width / 3 * rate);
            int iconBottom = (int) (itemView.getBottom() - height / 2 + iconH / 2);
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
            icon.draw(canvas);
          }
        }
        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState,
            isCurrentlyActive);
      }
    };
    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeClbk);
    itemTouchHelper.attachToRecyclerView(this.rv);
  }

  private void getNick() {
    myNick = getIntent().getStringExtra("nick");
    // display user id.
    TextView tvLoginId = findViewById(R.id.tvLoginId);
    String msg = tvLoginId.getText().toString().replace("login_id", myNick);
    tvLoginId.setText(msg);
  }

  // function to set db.
  public void setDb() {
    model = new ViewModelProvider(this).get(BriefChatGroupViewModel.class);
    model.getAll().observe(this,
        briefChatGroups -> this.adapter.submitList(briefChatGroups));
    briefGroupDbManager = new BriefChatGroupDbManager(
        "user_list/" + myNick + "/chat_group_list/",
        getApplication());
    groupDbManager = new ChatGroupDbManager("chat_group_list/", getApplication());
    briefGroupDbManager.getUpdateInRealTime();
    groupDbManager.getUpdateInRealTime();
  }

  // add group 버튼 클릭시 할 행동.
  public void btnAddGroupLis(View v) {
    ChatGroup newChatGroup = new ChatGroup("dum01", "dum01", 10, "dum01", "dum01");
    groupDbManager.addItem(newChatGroup);
    BriefChatGroup newData = new BriefChatGroup(newChatGroup);
    briefGroupDbManager.addItem(newData);
  }
}