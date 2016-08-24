package com.monigarr.servicefusionrealmdemo.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.monigarr.servicefusionrealmdemo.R;
import com.monigarr.servicefusionrealmdemo.model.Disco;

import io.realm.RealmResults;

/**
 * Created by monigarr on 8/22/16.
 */

public class DiscoAdapter extends RecyclerView.Adapter<DiscoAdapter.DiscoViewHolder> {

    private OnItemClickListener onItemClickListener;
    private RealmResults<Disco> discos;

    public DiscoAdapter(RealmResults<Disco> discos) {
        this.discos = discos;
    }

    @Override
    public DiscoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_disco, parent, false);
        return new DiscoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DiscoViewHolder holder, int position) {
        holder.tvDiscoName.setText(discos.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return discos.size();
    }

    public class DiscoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvDiscoName;

        public DiscoViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            tvDiscoName = (TextView) itemView.findViewById(R.id.tv_name_disco);
        }

        @Override
        public void onClick(View v) {
            Disco disco = discos.get(getAdapterPosition());
            onItemClickListener.onItemClick(disco.getId());
        }
    }

    public interface OnItemClickListener{
        void onItemClick(String id);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
