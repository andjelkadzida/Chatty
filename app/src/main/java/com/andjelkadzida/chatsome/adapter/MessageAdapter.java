package com.andjelkadzida.chatsome.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.andjelkadzida.chatsome.R;
import com.andjelkadzida.chatsome.model.Chat;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>
{
    private Context context;
    private List<Chat> chats;
    private String imageUrl;

    FirebaseUser firebaseUser;

    public static final int MESSAGE_TYPE_LEFT = 0;
    public static final int MESSAGE_TYPE_RIGHT = 1;

    public MessageAdapter(Context context, List<Chat> chats, String imageUrl)
    {
        this.context = context;
        this.chats = chats;
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if(viewType == MESSAGE_TYPE_RIGHT)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position)
    {
        Chat chat = chats.get(position);

        holder.showMessage.setText(chat.getMessage());

        if(imageUrl.equals("default"))
        {
            holder.imageView.setImageResource(R.drawable.user_ico);
        }
        else
        {
            Glide.with(context).load(imageUrl).into(holder.imageView);
        }

        if(position == chats.size()-1)
        {
            if(chat.isStatusSeen())
            {
                holder.seenViewer.setText(R.string.seen + chat.getDateTimeSeen());
            }
            else
            {
                holder.seenViewer.setText(R.string.delivered + chat.getDateTimeSent());
            }
        }
        else
        {
            holder.seenViewer.setVisibility(View.GONE);
        }

        holder.showMessage.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext(), R.style.AlertDialogCustom);

                alertDialog.setTitle(R.string.msgStatus);
                alertDialog.setIcon(R.drawable.ic_chat);
                alertDialog.setMessage(chat.getMessage() + "\n" + context.getResources().getString(R.string.delivered) + chat.getDateTimeSent() + "\n" + context.getResources().getString(R.string.seen) + chat.getDateTimeSeen());
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton(R.string.btnOk, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                       dialog.dismiss();
                    }
                });
                alertDialog.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return chats.size();
    }

   public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView showMessage;
        public CircleImageView imageView;
        public TextView seenViewer;

        public ViewHolder(View itemView)
        {
            super(itemView);

            showMessage = itemView.findViewById(R.id.showMessage);
            imageView = itemView.findViewById(R.id.profilePic);
            seenViewer = itemView.findViewById(R.id.statusSeenView);
        }
    }

    @Override
    public int getItemViewType(int position)
    {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(chats.get(position).getSender().equals(firebaseUser.getUid()))
        {
            return MESSAGE_TYPE_RIGHT;
        }
        else
        {
            return MESSAGE_TYPE_LEFT;
        }
    }
}
