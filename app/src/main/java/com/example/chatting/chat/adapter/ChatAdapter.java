package com.example.chatting.chat.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;
import com.example.chatting.R;
import com.example.chatting.ShowFullChatTextActivity;
import com.example.chatting.chat.data.ChatData;
import com.example.chatting.chat.viewholder.ChatViewHolder;
import com.example.chatting.chat.viewholder.ellipsed.EllipsedChatViewHolder;
import com.example.chatting.chat.viewholder.ellipsed.MyEllipsedChatViewHolder;
import com.example.chatting.chat.viewholder.ellipsed.YourEllipsedChatViewHolder;
import com.example.chatting.chat.viewholder.noellipsed.MyChatViewHolder;
import com.example.chatting.chat.viewholder.noellipsed.NoEllipsedChatViewHolder;
import com.example.chatting.chat.viewholder.noellipsed.YourChatViewHolder;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {

  // view holder 의 type.
  private static final int TYPE_MY_CHAT = 0;
  private static final int TYPE_YOUR_CHAT = 1;
  private static final int TYPE_MY_CHAT_ELLIPSED = 2;
  private static final int TYPE_YOUR_CHAT_ELLIPSED = 3;
  // msg 최대 길이. 이 이상이면 ellipsed 된다.
  private static final int MSG_MAX_LENGTH = 30;
  // 데이터.
  private final List<ChatData> dataList;  // 데이터 목록
  private final String myNick;            // 채팅 보낸 사람의 닉네임.
  // 디스플레이 크기.
  private final float DISPLAY_HEIGHT_PIXEL;
  private final float DISPLAY_WIDTH_PIXEL;

  private Context context;

  @Nullable
  private SelectionTracker<Long> selectionTracker;

  // 데이터 설정.
  public ChatAdapter(List<ChatData> dataList, String myNick, Context context) {
    this.dataList = dataList;
    this.myNick = myNick;
    this.context = context;
    this.DISPLAY_HEIGHT_PIXEL = context.getResources().getDisplayMetrics().heightPixels;
    this.DISPLAY_WIDTH_PIXEL = context.getResources().getDisplayMetrics().widthPixels;
    // id 를 사용해 item 식별.
    setHasStableIds(true);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  // 새로운 chat view holder 만드는 함수.
  @NonNull
  @Override
  public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view;
    switch (viewType) {
      // my chat 인 경우,
      case TYPE_MY_CHAT:
        view = LayoutInflater.from(context).inflate(R.layout.my_chat, parent, false);
        Log.d("Create_view_holder", "My chat");
        return new MyChatViewHolder(view);
      // your chat 인 경우,
      case TYPE_YOUR_CHAT:
        view = LayoutInflater.from(context).inflate(R.layout.your_chat, parent, false);
        Log.d("Create_view_holder", "Your chat");
        return new YourChatViewHolder(view);
      // my ellipsed chat 인 경우,
      case TYPE_MY_CHAT_ELLIPSED:
        view = LayoutInflater.from(context).inflate(R.layout.my_chat_ellipsed, parent, false);
        Log.d("Create_view_holder", "My chat - ellipsed");
        return new MyEllipsedChatViewHolder(view);
      // your ellipsed chat 인 경우,
      case TYPE_YOUR_CHAT_ELLIPSED:
      default:
        view = LayoutInflater.from(context).inflate(R.layout.your_chat_ellipsed, parent, false);
        Log.d("Create_view_holder", "Your chat - ellipsed");
        return new YourEllipsedChatViewHolder(view);
    }
  }

  // chat view holder 의 내용 수정하는 함수. (layout manager 에 의해 호출.)
  @Override
  public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
    if (holder instanceof NoEllipsedChatViewHolder) {
      if (holder instanceof MyChatViewHolder) {
        ((MyChatViewHolder) holder).bind(dataList.get(position));
        Log.d("Bind_view_holder", "My chat" +
            "\nType of holder : " + holder.getClass().getName() +
            "\nType of data located in #" + position + " : " + getItemViewType(position) +
            "\nContents of data located in #" + position + " : " + dataList.get(position).getMsg() +
            "\n.");
      } else if (holder instanceof YourChatViewHolder) {
        ((YourChatViewHolder) holder).bind(dataList.get(position));
        Log.d("Bind_view_holder", "Your chat" +
            "\nType of holder : " + holder.getClass().getName() +
            "\nType of data located in #" + position + " : " + getItemViewType(position) +
            "\nContents of data located in #" + position + " : " + dataList.get(position).getMsg() +
            "\n.");
      }
    } else if (holder instanceof EllipsedChatViewHolder) {
      EllipsedChatViewHolder ellipsedChatViewHolder = (EllipsedChatViewHolder) holder;
      ellipsedChatViewHolder.setBtnFullTextClickListener(view -> {
//        Toast toast = Toast
//            .makeText(this.context, ellipsedChatViewHolder.getMsgfullText(), Toast.LENGTH_LONG);
//        toast.show();
        String msgFullText = ellipsedChatViewHolder.getMsgfullText();
        Intent intent = new Intent(this.context, ShowFullChatTextActivity.class);
        intent.putExtra("msgFullText", msgFullText);
        this.context.startActivity(intent);
      });
      if (holder instanceof MyEllipsedChatViewHolder) {
        ((MyEllipsedChatViewHolder) holder).bind(dataList.get(position));
        Log.d("Bind_view_holder", "My ellipsed chat" +
            "\nType of holder : " + holder.getClass().getName() +
            "\nType of data located in #" + position + " : " + getItemViewType(position) +
            "\nContents of data located in #" + position + " : " + dataList.get(position).getMsg()
            +
            "\n.");
      } else if (holder instanceof YourEllipsedChatViewHolder) {
        ((YourEllipsedChatViewHolder) holder).bind(dataList.get(position));
        Log.d("Bind_view_holder", "Your ellipsed chat" +
            "\nType of holder : " + holder.getClass().getName() +
            "\nType of data located in #" + position + " : " + getItemViewType(position) +
            "\nContents of data located in #" + position + " : " + dataList.get(position).getMsg()
            +
            "\n.");
      }
    }
    setMsgViewWidthLimit(holder, dataList.get(position).getMsg().length());
    holder.setSelectionTracker(selectionTracker);
  }

  // 데이터 목록의 크기 구하는 함수.
  @Override
  public int getItemCount() {
    return dataList.size();
  }

  // 데이터 목록 안 데이터의 타입 구하는 함수.
  // 내 채팅 or 상대방 채팅.
  @Override
  public int getItemViewType(int position) {
    ChatData chatData = getChatData(position);
    if (chatData.getNickname().compareTo(myNick) == 0) {
      if (checkIfMsgTextShouldBeEllipsed(chatData.getMsg())) {
        return TYPE_MY_CHAT_ELLIPSED;
      } else {
        return TYPE_MY_CHAT;
      }
    } else {
      if (checkIfMsgTextShouldBeEllipsed(chatData.getMsg())) {
        return TYPE_YOUR_CHAT_ELLIPSED;
      } else {
        return TYPE_YOUR_CHAT;
      }
    }
  }

  private ChatData getChatData(final int position) {
    return this.dataList.get(position);
  }

  // 채팅 추가
  public void addChat(ChatData chat) {
    dataList.add(chat);
    // notify the position of newly added item
    notifyItemInserted(dataList.size() - 1);
  }

  // msg view 의 폭 제한하는 함수.
  private void setMsgViewWidthLimit(@NonNull final ChatViewHolder holder, final int textLength) {
    // text가 15자 이상이면,
    if (textLength > 15) {
      // view 최대 폭을 디바이스 전체 폭의 절반 값으로 설정한다.
      holder.setTextViewMsgWidth((int) DISPLAY_WIDTH_PIXEL / 2);
    }
  }

  private boolean checkIfMsgTextShouldBeEllipsed(@NonNull final String msgFullText) {
    boolean isEllipsed;
    if (msgFullText == null) {
      isEllipsed = false;
    } else if (msgFullText.length() > MSG_MAX_LENGTH) {
      isEllipsed = true;
    } else {
      isEllipsed = false;
    }
    return isEllipsed;
  }

  public void setSelectionTracker(final SelectionTracker<Long> selectionTracker) {
    this.selectionTracker = selectionTracker;
  }
}
