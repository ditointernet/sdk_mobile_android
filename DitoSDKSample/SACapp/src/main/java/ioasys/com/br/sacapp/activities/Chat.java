package ioasys.com.br.sacapp.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.levelupstudio.recyclerview.ExpandableRecyclerView;

import java.util.ArrayList;
import java.util.List;

import ioasys.com.br.sacapp.R;
import ioasys.com.br.sacapp.adapter.ChatAdapter;
import ioasys.com.br.sacapp.helperClasses.PinnedSectionListView;
import ioasys.com.br.sacapp.model.ChatItem;

public class Chat extends BaseActivity {
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        setRecyclerView();
    }

    private void setRecyclerView(){
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new ChatAdapter(this, getList()));
    }

    private List<ChatItem> getList(){
        ChatItem aux = new ChatItem(false, 0, "Ola, sou blablablu", true);
        ChatItem aux1 = new ChatItem(false, 0, "Ola, sou blablabluasldkfsdfasdfsdfasdf\nasdfalsdk", false);
        ChatItem aux2 = new ChatItem(true, 0, "Ola, sou blablablu", true);
        ChatItem aux3 = new ChatItem(true, 0, "Ola, sou blablablasdfalskdfjalsdkfjzn\nasdlfasdlkj\nu", false);

        List<ChatItem> list = new ArrayList<>();
        list.add(aux);
        list.add(aux1);
        list.add(aux2);
        list.add(aux3);
        return list;
    }

}
