package kpu.computer.joonggo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class MessageListAdapter extends BaseAdapter {

    private Context context;
    private List<Message> messageList;
    private View v;
    private Boolean check;

    public MessageListAdapter(Context context, List<Message> messageList, Boolean check) {
        this.context = context;
        this.messageList = messageList;
        this.check = check;
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int i) {
        return messageList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = View.inflate(context, R.layout.message, null);
        TextView content = (TextView)v.findViewById(R.id.message_content_TV);
        TextView userID = (TextView)v.findViewById(R.id.message_userID_TV);
        TextView time = (TextView)v.findViewById(R.id.message_time_TV);

        if(check)
            userID.setText(messageList.get(i).getSender());
        else
            userID.setText(messageList.get(i).getReceiver());

        content.setText(messageList.get(i).getContent());
        time.setText(messageList.get(i).getTime());

        return v;
    }
}
