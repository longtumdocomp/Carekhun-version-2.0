package co.app.longtumdo.carekhun.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import co.app.longtumdo.carekhun.FirebaseUserProfile;
import co.app.longtumdo.carekhun.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private List<FirebaseUserProfile> user;

    protected Context context;

    public RecyclerViewAdapter(Context context, List<FirebaseUserProfile> user) {
        this.user = user;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolders viewHolder = null;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_data_list, parent, false);
        viewHolder = new RecyclerViewHolders(layoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.profileHeader.setText(user.get(position).getHeader());
        holder.profileContent.setText(user.get(position).getProfileContent());
    }

    @Override
    public int getItemCount() {
        return this.user.size();
    }

}
