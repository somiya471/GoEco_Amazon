package com.example.goeco_amazon.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.goeco_amazon.R;
import com.example.goeco_amazon.activities.ConfirmationActivity;
import com.example.goeco_amazon.activities.LoginActivity;
import com.example.goeco_amazon.activities.PickupMetricsActivity;
import com.example.goeco_amazon.adapters.ImageSliderAdapter;
import com.example.goeco_amazon.adapters.ProductListRecyclerAdapter;
import com.example.goeco_amazon.interfaces.ProductBuyOnClick;
import com.example.goeco_amazon.models.PickupPointModel;
import com.example.goeco_amazon.models.ProductModel;
import com.example.goeco_amazon.responsemodels.NearbyPickupResponse;
import com.example.goeco_amazon.responsemodels.ProductResponse;
import com.example.goeco_amazon.utils.LoginManager;
import com.example.goeco_amazon.viewmodels.GetProductsViewModel;
import com.example.goeco_amazon.viewmodels.NearbyPickupViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HomeFragment extends Fragment {
    Button button;
    ArrayList<PickupPointModel> arrayList;
    RecyclerView productrecycler;
    ProductListRecyclerAdapter productListRecyclerAdapter;
    GetProductsViewModel getProductsViewModel;
    ArrayList<ProductModel> products;
    String name,desc,image,id;
    int price;
    LoginManager loginManager;
    ViewPager2 imageSlider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize ViewPager2 for image slider
        imageSlider = view.findViewById(R.id.image_slider);
        setupImageSlider();

        loginManager = new LoginManager(getContext());
        setupProducts(view);

        return view;
    }

    private void setupImageSlider() {
        // Disable touch events being passed to parent ViewPager2
        imageSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // Optional: Add any page change handling here
            }
        });

        // Important: Prevent horizontal touch events from being stolen by parent ViewPager2
        View parent = (View) imageSlider.getParent();
//        parent.requestDisallowInterceptTouchEvent(true);

        imageSlider.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        List<String> images = Arrays.asList(
                "https://img.etimg.com/thumb/msid-93051525,width-1070,height-580,imgsize-2243475,overlay-economictimes/photo.jpg",
                "https://images-eu.ssl-images-amazon.com/images/G/31/img22/Wireless/devjyoti/PD23/Launches/Updated_ingress1242x550_3.gif",
                "https://images-eu.ssl-images-amazon.com/images/G/31/img23/Books/BB/JULY/1242x550_Header-BB-Jul23.jpg"
        );

        ImageSliderAdapter adapter = new ImageSliderAdapter(getContext(), images);
        imageSlider.setAdapter(adapter);

        // Optional: Add auto-scroll functionality
        setupAutoScroll();
    }

    private void setupAutoScroll() {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (imageSlider == null) return;
                int currentItem = imageSlider.getCurrentItem();
                int count = imageSlider.getAdapter().getItemCount();
                imageSlider.setCurrentItem((currentItem + 1) % count, true);
                handler.postDelayed(this, 3000); // Change image every 3 seconds
            }
        };
        handler.postDelayed(runnable, 3000);
    }

    private void setupProducts(View view) {
        getProductsViewModel = new ViewModelProvider((ViewModelStoreOwner) getContext()).get(GetProductsViewModel.class);
        getProductsViewModel.getUserLiveData().observe((LifecycleOwner) getContext(), response -> {
            if (response != null) {
                products = response.getProducts();
                productrecycler = view.findViewById(R.id.products_recycler);
                productrecycler.setLayoutManager(new LinearLayoutManager(getContext()) {
                    @Override
                    public boolean canScrollVertically() {
                        return false; // Disable vertical scrolling in RecyclerView
                    }
                });
                productListRecyclerAdapter = new ProductListRecyclerAdapter(getContext(), products, (productModel, quantity) -> {
                    Log.d("HomeFragment", "Buy button clicked for product: " + productModel.getName());
                    name = productModel.getName();
                    price = productModel.getPrice();
                    desc = productModel.getDesc();
                    image = productModel.getImage();
                    id = productModel.get_id();
                    Intent intent = new Intent(getActivity(), ConfirmationActivity.class);
                    intent.putExtra("quantity", quantity);
                    intent.putExtra("productname", name);
                    intent.putExtra("productdesc", desc);
                    intent.putExtra("productimg", image);
                    intent.putExtra("productid", id);
                    intent.putExtra("productprice", price);
                    startActivity(intent);
                });
                productrecycler.setAdapter(productListRecyclerAdapter);
            }
        });
        getProductsViewModel.nearbypickup(requireActivity().getApplication());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Clean up any handlers
        imageSlider = null;
    }
}