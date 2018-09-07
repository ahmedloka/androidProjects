package firebaseuploud.mygdx.com.firebaseuploudexample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;
    private OnItemClickListner mListner ;

    public ImageAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        Picasso.get().load(uploadCurrent.getImageUrl()).fit().placeholder(R.mipmap.ic_launcher).centerCrop().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView textViewName;
        public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.txtViewImgName);
            imageView = itemView.findViewById(R.id.imgViewUpload);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListner != null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    mListner.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Ation");
            MenuItem doWhatEver = menu.add(Menu.NONE , 1,1,"Do what ever ");
            MenuItem delete = menu.add(Menu.NONE , 2,2 ,"Delete");

            doWhatEver.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListner != null){
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){

                    switch (item.getItemId()){
                        case 1 :
                            mListner.onItemClick(position);
                            break;
                        case 2 :
                            mListner.onDeleteClick(position);
                            break;
                    }
                }
            }
            return true;
        }
    }
    public interface OnItemClickListner {

        void onItemClick (int poisition);
        void onWhatEverClick(int poistion);
        void onDeleteClick (int position);
    }
    public void onItemClickListner (OnItemClickListner listner){

        mListner = listner ;
    }


}