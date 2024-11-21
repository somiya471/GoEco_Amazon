package com.example.goeco_amazon.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.viewpager2.widget.ViewPager2;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.goeco_amazon.R;
import com.example.goeco_amazon.activities.PickupMetricsActivity;
import com.example.goeco_amazon.adapters.ImageSliderAdapter;
import com.example.goeco_amazon.adapters.ProductListRecyclerAdapter;
import com.example.goeco_amazon.interfaces.ProductBuyOnClick;
import com.example.goeco_amazon.models.PickupPointModel;
import com.example.goeco_amazon.models.ProductModel;
import com.example.goeco_amazon.responsemodels.NearbyPickupResponse;
import com.example.goeco_amazon.responsemodels.ProductResponse;
import com.example.goeco_amazon.viewmodels.GetProductsViewModel;
import com.example.goeco_amazon.viewmodels.NearbyPickupViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HomeFragment extends Fragment {
    Button button;
    NearbyPickupViewModel viewModel;
    ArrayList<PickupPointModel> arrayList;
    RecyclerView productrecycler;
    ProductListRecyclerAdapter productListRecyclerAdapter;
    GetProductsViewModel getProductsViewModel;
    ArrayList<ProductModel> products;
    String name,desc,image;
    int price;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ViewPager2 viewPager = view.findViewById(R.id.image_slider);

        List<String> images = Arrays.asList(
                "https://img.etimg.com/thumb/msid-93051525,width-1070,height-580,imgsize-2243475,overlay-economictimes/photo.jpg",
                "https://images-eu.ssl-images-amazon.com/images/G/31/img22/Wireless/devjyoti/PD23/Launches/Updated_ingress1242x550_3.gif",
                "https://images-eu.ssl-images-amazon.com/images/G/31/img23/Books/BB/JULY/1242x550_Header-BB-Jul23.jpg"
        );


        // Set Adapter
        ImageSliderAdapter adapter = new ImageSliderAdapter(getContext(), images);
        viewPager.setAdapter(adapter);

        getProductsViewModel = new ViewModelProvider((ViewModelStoreOwner) getContext()).get(GetProductsViewModel.class);
        getProductsViewModel.getUserLiveData().observe((LifecycleOwner) getContext(), new Observer<ProductResponse>() {
            @Override
            public void onChanged(ProductResponse response) {
                if (response != null) {
                    products = response.getProducts();
                    productrecycler = view.findViewById(R.id.products_recycler);
                    productrecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                    productListRecyclerAdapter = new ProductListRecyclerAdapter(getContext(), products, new ProductBuyOnClick() {
                        @Override
                        public void onclick(ProductModel productModel,int quantity) {
                            name = productModel.getName();
                            price = productModel.getPrice();
                            desc = productModel.getDesc();
                            image = productModel.getImage();
                            viewModel = new ViewModelProvider((ViewModelStoreOwner) getContext()).get(NearbyPickupViewModel.class);
                            viewModel.getUserLiveData().observe((LifecycleOwner) getContext(), new Observer<NearbyPickupResponse>() {
                                @Override
                                public void onChanged(NearbyPickupResponse response) {
                                    if (response != null) {
                                        arrayList = response.getPickupPointModels();
                                        Intent intent = new Intent(getContext(), PickupMetricsActivity.class);
                                        intent.putExtra("quantity",quantity);
                                        intent.putExtra("productname",name);
                                        intent.putExtra("productdesc",desc);
                                        intent.putExtra("productimg",image);
                                        intent.putExtra("productprice",price);
                                        intent.putParcelableArrayListExtra("pickupPointsList", arrayList);
                                        startActivity(intent);

                                    }
                                }
                            });

                        }
                    });

                }
            }
        });




        return view;
    }
}