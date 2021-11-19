package com.android.chatver5.activity;


import android.Manifest.permission;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.chatver5.R;
import com.android.chatver5.chat.adapter.ChatAdapter;
import com.android.chatver5.chat.adapter.ChatAdapter.ChatDiff;
import com.android.chatver5.db.data.Chat;
import com.android.chatver5.db.firedbmanager.ChatDbManager;
import com.android.chatver5.db.roomdb.viewmodel.ChatViewModel;
import com.android.chatver5.utilities.Utilities;
import com.android.chatver5.utilities.Utilities.LogType;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class ChatActivity extends AppCompatActivity {

  // 접근 권한 코드.
  private static final int REQ_CODE_PERMISSION = 0;
  private String myNick = "이슬"; // 첫번째 단말기의 닉네임.
  private ChatViewModel model;
  private RecyclerView rv;
  private ChatAdapter adapter;
  private ChatDbManager dbManager;
  private EditText etMsg;
  private boolean isEtFocused = false;
  private CompositeDisposable disps;
  private String groupId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityCompat.requestPermissions(this, new String[]{permission.READ_EXTERNAL_STORAGE},
        REQ_CODE_PERMISSION);
  }

  // 권한 요청할 때 할 행동.
  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == REQ_CODE_PERMISSION) {
      for (int grantResult : grantResults) {
        if (grantResult == PackageManager.PERMISSION_DENIED) {
          finish();
          Utilities.log(LogType.w, "permission is denied. finish.");
          return;
        }
      }
      this.groupId = getIntent().getStringExtra("groupId");
      this.myNick = getIntent().getStringExtra("myNick");
      setupUI();
      setupDb();
    }
  }

  // ui 설정하는 함수.
  private void setupUI() {
    setContentView(R.layout.activity_chat);
    etMsg = findViewById(R.id.editText_chat);

    etMsg.setOnKeyListener((v, keyCode, event) -> {
      if (keyCode == KeyEvent.KEYCODE_ENTER
          && event.getAction() == KeyEvent.ACTION_DOWN) {
        sendMsg();
        Utilities.log(LogType.d, "enter pressed.");
        return true;
      }
      return false;
    });

    // recycler view 세팅.
    rv = findViewById(R.id.recyclerView);
    adapter = new ChatAdapter(new ChatDiff(), myNick);
    rv.setAdapter(adapter);
    rv.setLayoutManager(new LinearLayoutManager(this));
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
          dbManager.deleteItem(adapter.getItem(position));
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

  // db 설정하는 함수.
  public void setupDb() {

    model = new ViewModelProvider(this).get(ChatViewModel.class);
    model.getAll().observe(this,
        chatList -> {
          this.adapter.submitList(chatList);
        });
    // group id = dum_id 임시 설정. (테스트)
    dbManager = new ChatDbManager(
        groupId,
        "chat_group_list/" + groupId + "/chat_list",
        getApplication());
    dbManager.getUpdateInRealTime();
  }

  // send 버튼 클릭시 할 행동.
  public void btnSendClickLis(View v) {
    sendMsg();
  }

  private void sendMsg() {
    String msg = etMsg.getText().toString();
    if (msg.length() == 0) {
      return;
    }
    Chat item = new Chat(groupId, myNick, msg);
    dbManager.addItem(item);
    etMsg.setText("");
  }
}