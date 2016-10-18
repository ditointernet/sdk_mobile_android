package ioasys.com.br.sacapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import ioasys.com.br.sacapp.helperClasses.PinnedSectionListView;
import ioasys.com.br.sacapp.model.HeaderItem;

/**
 * Created by ioasys on 26/08/15.
 */
public class ChatPinnedAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {
    private List<Item> servicings;
    private List<HeaderItem> mItens;
    private Context mctx;


    public ChatPinnedAdapter(Context context, List<HeaderItem> mItens){
        this.mItens = mItens;
        this.mctx = context;
        servicings = new ArrayList<>();

        generateDataSet(mItens, false);
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    public void generateDataSet(List<HeaderItem> headerItenses, boolean clear){

        final int sectionsNumber = headerItenses.size();

        prepareSections(sectionsNumber);

        int sectionPosition = 0;
        int listPosition = 0;

        for (int i = 0; i < sectionsNumber; i++){
            Item section = new Item(Item.SECTION, mItens.get(i).getmImgTitle(), mItens.get(i).getmText().get(0), null, mItens.get(i).ismIsResponding());
            section.sectionPosition = sectionPosition;
            section.listPosition = listPosition++;
            onSectionAdded(section, sectionPosition);
            add(section);

            final int itemsNumber = mItens.get(i).getmText().size();

            //here I do not iniciate in the first position becouse it has beeing used to the title
            for (int j = 1; j < itemsNumber; j++){
                Item item = new Item(Item.ITEM, 0, "", mItens.get(i).getmText().get(j), mItens.get(i).ismIsResponding() );
                item.sectionPosition = sectionPosition;
                item.listPosition = listPosition++;
                add(item);
            }
            sectionPosition++;
        }
    }

    public void add(int position, Item item) {
        servicings.add(position, item);

        notifyDataSetChanged();
    }

    public void add( Item item) {
        servicings.add(item);

        notifyDataSetChanged();
    }

    protected void onSectionAdded(Item section, int sectionPosition) { }
    protected void prepareSections(int sectionsNumber) { }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return false;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        return null;
    }

    public void remove(String item) {
        int position = servicings.indexOf(item);
        servicings.remove(position);
        notifyDataSetChanged();
    }

    static class Item{
        public static final int ITEM = 0;
        public static final int SECTION = 1;

        public final boolean isResponding;

        public final int type;
        public final String textHeader;
        public final int imgHeader;

        public final String msgItem;

        public int sectionPosition;
        public int listPosition;

        public Item(int type, int imgHeader, String textHeader, String msgItem, boolean isResponding) {
            this.type = type;
            this.textHeader = textHeader;
            this.msgItem = msgItem;
            this.imgHeader = imgHeader;
            this.isResponding = isResponding;
        }

        @Override
        public String toString() {
            return textHeader;
        }
    }
}
