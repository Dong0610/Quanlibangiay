package btl.n01.quanlibangiay.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import btl.n01.quanlibangiay.base.BaseAdapter;
import btl.n01.quanlibangiay.databinding.ItemListReviewBinding;
import btl.n01.quanlibangiay.model.Review;

public class ReviewAdapter extends BaseAdapter<Review, ItemListReviewBinding> {
    @Override
    protected ItemListReviewBinding createBinding(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return ItemListReviewBinding.inflate(inflater,parent,false);
    }

    @Override
    protected void bind(ItemListReviewBinding binding, Review item, int position) {
        binding.rating.setRating(item.getReviewStar());
        binding.txtName.setText(item.getReviewUserName());
        binding.contentCm.setText(item.getReviewComment());
        binding.txtTime.setText(item.getReviewTime().toString());
    }
}
