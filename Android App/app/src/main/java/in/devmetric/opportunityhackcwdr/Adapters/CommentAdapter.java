package in.devmetric.opportunityhackcwdr.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import in.devmetric.opportunityhackcwdr.R;

/**
 * Created by Satyam on 13/11/2016.
 */

public class CommentAdapter extends RecyclerView.Adapter
        <CommentHolder>{

    ArrayList<String> commentsList;

    public CommentAdapter(ArrayList<String> commentsList) {
        this.commentsList = commentsList;
    }

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_block, parent, false);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentHolder holder, int position) {
        holder.tvComment.setText(commentsList.get(position));
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }
}

class CommentHolder extends RecyclerView.ViewHolder {

    TextView tvComment;

    public CommentHolder(View itemView) {
        super(itemView);
        tvComment = (TextView) itemView.findViewById(R.id.tvComment);
    }
}