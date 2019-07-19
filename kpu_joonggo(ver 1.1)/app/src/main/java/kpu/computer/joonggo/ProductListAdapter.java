package kpu.computer.joonggo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ProductListAdapter extends BaseAdapter{

    private Context context;
    private List<Product> productList;
    private View v;

    public ProductListAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int i) {
        return productList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        v = View.inflate(context, R.layout.product, null);
        TextView nameText = (TextView) v.findViewById(R.id.productname);
        TextView priceText = (TextView) v.findViewById(R.id.productprice);
        TextView statText = (TextView) v.findViewById(R.id.productstat);
        ImageView imageView = (ImageView) v.findViewById(R.id.productimage);

        nameText.setText(productList.get(i).getName());
        priceText.setText("" + productList.get(i).getPrice() + "원");
        statText.setText(productList.get(i).getStat());

        Glide.with(context).load("http://114.204.73.214/productImage/" + productList.get(i).getImagepath1()).into(imageView);

        v.setTag(productList.get(i).getName());
        return v;
    }
}
