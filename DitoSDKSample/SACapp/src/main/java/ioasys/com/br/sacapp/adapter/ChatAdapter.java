package ioasys.com.br.sacapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.levelupstudio.recyclerview.ExpandableRecyclerView;

import java.util.List;

import ioasys.com.br.sacapp.R;
import ioasys.com.br.sacapp.model.ChatItem;

/**
 * Created by ioasys on 26/08/15.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder>{
    private Context mCtx;
    private List<ChatItem> mItems;


    public ChatAdapter(Context mCtx, List<ChatItem> mItems){
        this.mCtx = mCtx;
        this.mItems = mItems;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.chat_adapter, null);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder chatViewHolder, int i) {

        ChatItem aux = mItems.get(i);

        if (aux.isResponding()){
            if (aux.isTitle()){
                chatViewHolder.imgSending.setVisibility(View.GONE);
                chatViewHolder.imgResponse.setVisibility(View.VISIBLE);
                chatViewHolder.msg.setText(aux.getmTxtMsg());
            } else {
                chatViewHolder.imgSending.setVisibility(View.GONE);
                chatViewHolder.imgResponse.setVisibility(View.INVISIBLE);
                chatViewHolder.msg.setText(aux.getmTxtMsg());
            }
        } else {
            if (aux.isTitle()){
                chatViewHolder.imgSending.setVisibility(View.VISIBLE);
                chatViewHolder.imgResponse.setVisibility(View.GONE);
                chatViewHolder.msg.setText(aux.getmTxtMsg());
            } else {
                chatViewHolder.imgSending.setVisibility(View.INVISIBLE);
                chatViewHolder.imgResponse.setVisibility(View.GONE);
                chatViewHolder.msg.setText(aux.getmTxtMsg());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ChatViewHolder extends ExpandableRecyclerView.ViewHolder{
        public ImageView imgResponse;
        public ImageView imgSending;
        public TextView msg;

        public ChatViewHolder(View itemView) {
            super(itemView);

            imgResponse = (ImageView) itemView.findViewById(R.id.img_response);
            imgSending = (ImageView) itemView.findViewById(R.id.img_sending);
            msg = (TextView) itemView.findViewById(R.id.msg);
        }
    }
}
