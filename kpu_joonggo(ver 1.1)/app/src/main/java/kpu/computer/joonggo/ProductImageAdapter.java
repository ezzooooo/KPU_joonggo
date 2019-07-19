package kpu.computer.joonggo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ProductImageAdapter extends PagerAdapter {

    private LayoutInflater inflater;
    private Context context;
    private String images[] = new String[3];
    private Integer count = 0;

    public ProductImageAdapter(Context context, String image1, String image2, String image3){
        this.context = context;
        this.images[0] = image1;
        this.images[1] = image2;
        this.images[2] = image3;

        for(int i=0; i<3; i++) {
            if(! (images[i].equals("")) )
                count++;
        }
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (View)o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.image_slider, container, false);
        ImageView imageView = (ImageView)v.findViewById(R.id.vp_imageView);
        TextView textView = (TextView)v.findViewById(R.id.vp_textView);
        Glide.with(context).load("http://114.204.73.214/productImage/" + images[position]).into(imageView);
        textView.setText((position+1) + " / " + getCount());
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.invalidate();
    }

}
