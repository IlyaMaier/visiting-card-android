package com.community.jboss.visitingcard.savedcards;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.community.jboss.visitingcard.R;
import com.community.jboss.visitingcard.data.CardsDatabase;
import com.community.jboss.visitingcard.data.VisitingCard;
import com.community.jboss.visitingcard.data.VisitingCardDao;
import com.google.gson.Gson;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class SavedCardsAdapter extends RecyclerView.Adapter<SavedCardsAdapter.ViewHolder> {

    public static int RC_POP_UP = 4567;
    private List<VisitingCard> mVisitingCards;
    private Activity mActivity;
    private boolean mMaps = false;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.saved_card, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mBind(mVisitingCards.get(i));
    }

    @Override
    public int getItemCount() {
        return mVisitingCards.size();
    }

    public void updateData(List<VisitingCard> visitingCards, Activity activity, boolean maps) {
        mVisitingCards = visitingCards;
        mActivity = activity;
        mMaps = maps;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CircularImageView mPhoto;
        private TextView mName;
        private Context mContext;
        private VisitingCard mVisitingCard;
        private Button mButton;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            mPhoto = itemView.findViewById(R.id.card_photo);
            mName = itemView.findViewById(R.id.card_name);
            mButton = itemView.findViewById(R.id.card_btn_save);
            mContext = itemView.getContext();

            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(mContext, PopUpActivity.class);
                if (mMaps)
                    intent.putExtra("card", new Gson().toJson(mVisitingCard, VisitingCard.class));
                else intent.putExtra("id", mVisitingCard.getId());
                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(mActivity, new Pair<>(mPhoto, mContext.getString(R.string.photo)),
                                new Pair<>(mName, mContext.getString(R.string.name)));
                mActivity.startActivityForResult(intent, RC_POP_UP, options.toBundle());
            });

            if (mMaps) {
                mButton.setVisibility(View.VISIBLE);
                mButton.setOnClickListener(view -> {
                    VisitingCardDao dao = CardsDatabase.getInstance(mContext)
                            .getVisitingCardDao();
                    dao.insertAll(mVisitingCard);
                });
            } else itemView.setOnLongClickListener(view -> {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Deleting card")
                        .setMessage("Are you sure you want to delete this card?")
                        .setPositiveButton("Yes", (dialogInterface, i) -> {
                            VisitingCardDao dao = CardsDatabase.getInstance(view.getContext())
                                    .getVisitingCardDao();
                            dao.delete(mVisitingCard);
                            mVisitingCards.remove(mVisitingCard);
                            notifyDataSetChanged();
                            dialogInterface.cancel();
                        }).setNegativeButton("No", (dialogInterface, i) -> dialogInterface.cancel())
                        .create().show();
                return true;
            });
        }

        private void mBind(VisitingCard visitingCard) {
            this.mVisitingCard = visitingCard;
            if (visitingCard.getPhoto() != null)
                Glide.with(mContext).load(bytesToBitmap(visitingCard.getPhoto())).into(mPhoto);
            mName.setText(visitingCard.getName());
        }

    }

    public static Bitmap bytesToBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

}
