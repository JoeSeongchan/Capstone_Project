package com.android.chatver4.chat.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.android.chatver4.R;
import com.android.chatver4.chat.data.Chat;
import com.android.chatver4.chat.viewholder.ellipsed.EllipsedChatViewHolder;
import com.android.chatver4.chat.viewholder.ellipsed.MyEllipsedChatViewHolder;
import com.android.chatver4.chat.viewholder.ellipsed.YourEllipsedChatViewHolder;
import com.android.chatver4.chat.viewholder.noellipsed.MyChatViewHolder;
import com.android.chatver4.chat.viewholder.noellipsed.YourChatViewHolder;
import com.android.chatver4.event.MyChatSentEvent;
import com.android.chatver4.event.listener.OnMyChatSentEventListener;
import com.android.chatver4.parentadapter.ParentAdapter;
import com.android.chatver4.parentholder.ParentViewHolder;
import com.android.chatver4.utilities.Utilities;
import java.util.List;

public class ChatAdapter extends ParentAdapter<Chat> {

  public static final String TAG = "ChatAdapter_R";

  // view holder 의 type.
  private static final int TYPE_MY_CHAT = 0;
  private static final int TYPE_YOUR_CHAT = 1;
  private static final int TYPE_MY_CHAT_ELLIPSED = 2;
  private static final int TYPE_YOUR_CHAT_ELLIPSED = 3;

  // msg 최대 길이. 이 이상이면 ellipsed 된다.
  private static final int MSG_MAX_LENGTH = 30;

  // 데이터.
  private final String myNick;            // 채팅 보낸 사람의 닉네임.

  // 디스플레이 크기.
  private final float DISPLAY_HEIGHT_PIXEL;
  private final float DISPLAY_WIDTH_PIXEL;

  // 이벤트 리스너.
  private OnMyChatSentEventListener onMyChatSentEventListener;

  public ChatAdapter(List<Chat> dataList, Context context, String myNick) {
    super(dataList, context);
    this.myNick = myNick;
    this.DISPLAY_HEIGHT_PIXEL = context.getResources().getDisplayMetrics().heightPixels;
    this.DISPLAY_WIDTH_PIXEL = context.getResources().getDisplayMetrics().widthPixels;
    // id 를 사용해 item 식별.
    setHasStableIds(true);
  }

  public void setOnMyChatSentEventListener(OnMyChatSentEventListener listener) {
    this.onMyChatSentEventListener = listener;
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  // 새로운 chat view holder 만드는 함수.
  @NonNull
  @Override
  public ParentViewHolder<Chat> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    final String FUNC_TAG = Utilities.makeFuncTag();
    View view;
    switch (viewType) {
      // my chat 인 경우,
      case TYPE_MY_CHAT:
        view = LayoutInflater.from(context).inflate(R.layout.my_chat, parent, false);
        Log.d(TAG, FUNC_TAG + "\nMy chat");
        return new MyChatViewHolder(view);
      // your chat 인 경우,
      case TYPE_YOUR_CHAT:
        view = LayoutInflater.from(context).inflate(R.layout.your_chat, parent, false);
        Log.d(TAG, FUNC_TAG + "\nYour chat");
        return new YourChatViewHolder(view);
      // my ellipsed chat 인 경우,
      case TYPE_MY_CHAT_ELLIPSED:
        view = LayoutInflater.from(context).inflate(R.layout.my_chat_ellipsed, parent, false);
        Log.d(TAG, FUNC_TAG + "\nMy chat - ellipsed");
        return new MyEllipsedChatViewHolder(view);
      // your ellipsed chat 인 경우,
      case TYPE_YOUR_CHAT_ELLIPSED:
      default:
        view = LayoutInflater.from(context).inflate(R.layout.your_chat_ellipsed, parent, false);
        Log.d(TAG, FUNC_TAG + "\nYour chat - ellipsed");
        return new YourEllipsedChatViewHolder(view);
    }
  }

  // chat view holder 의 내용 수정하는 함수. (layout manager 에 의해 호출.)
  @Override
  public void onBindViewHolder(@NonNull ParentViewHolder<Chat> holder, int position) {
    final String FUNC_TAG = Utilities.makeFuncTag();
    if (holder instanceof EllipsedChatViewHolder) {
      EllipsedChatViewHolder ellipsed = (EllipsedChatViewHolder) holder;
      ellipsed.setBtnFullTextClickListener(view -> {
        String msgFullText = ellipsed.getMsgFullText();
//        Intent intent = new Intent(this.context, ShowFullChatTextActivity.class);
//        intent.putExtra("msgFullText", msgFullText);
//        this.context.startActivity(intent);
        Log.d(TAG, FUNC_TAG + "full msg : " + msgFullText);
      });
    }
    holder.bind(dataList.get(position));
    Log.d(TAG, FUNC_TAG +
        "\ntype of holder : " + holder.getClass().getSimpleName() +
        "\nlocation : " + position +
        "\ntype : " + getItemViewType(position) +
        "\ncontents : " + dataList.get(position).getMsg());
    setMsgViewWidthToLimit(holder, position);
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
    Chat chat = getData(position);
    if (chat.getNickname().compareTo(myNick) == 0) {
      if (isEllipsed(chat.getMsg())) {
        return TYPE_MY_CHAT_ELLIPSED;
      } else {
        return TYPE_MY_CHAT;
      }
    } else {
      if (isEllipsed(chat.getMsg())) {
        return TYPE_YOUR_CHAT_ELLIPSED;
      } else {
        return TYPE_YOUR_CHAT;
      }
    }
  }

  // 채팅 데이터 조작 함수.

  public void addData(Chat chat) {
    dataList.add(chat);
    // notify the position of newly added item
    notifyItemInserted(dataList.size() - 1);

    if (onMyChatSentEventListener != null && chat.getNickname().compareTo(myNick) == 0) {
      MyChatSentEvent event = new MyChatSentEvent(this);
      onMyChatSentEventListener.onChatSent(event);
    }
  }

  public void modifyData(Chat chat, int pos) {
    dataList.set(pos, chat);
    notifyItemChanged(pos);
  }

  public void deleteData(int pos) {
    dataList.remove(pos);
    notifyItemRemoved(pos);
    notifyItemRangeChanged(pos, getItemCount());
  }


  // msg view 의 폭 제한하는 함수.
  private void setMsgViewWidthToLimit(@NonNull ParentViewHolder<Chat> holder, int pos) {
    if (!(holder instanceof EllipsedChatViewHolder)) {
      return;
    }
    ((EllipsedChatViewHolder) holder).setTextViewMsgWidth((int) DISPLAY_WIDTH_PIXEL / 2);
  }

  private boolean isEllipsed(@NonNull final String msg) {
    if (msg == null || msg.length() <= MSG_MAX_LENGTH) {
      return false;
    } else {
      return true;
    }
  }
}
