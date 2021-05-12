package com.xujiajie.wanandroid;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import java.util.List;
import java.util.Locale;

/**
 * 创建日期 2020/10/31
 * 描述：
 */
public class LocationHelp implements LifecycleObserver {
    private static String TAG = "LocationHelp";
    private ActivityResultRegistry registry;
    private Context mContext;
    public LocationListener locationListener = new LocationListener() {
        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        // Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {
        }

        // Provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {
        }

        //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                //如果位置发生变化，重新显示地理位置经纬度

                Toast.makeText(mContext, location.getLongitude() + " " +
                        location.getLatitude() + "", Toast.LENGTH_SHORT).show();
                Log.v(TAG, "监视地理位置变化-经纬度：" + location.getLongitude() + "   " + location.getLatitude());
            }
        }
    };

    /*public LocationHelp(ActivityResultRegistry registry) {
        this.registry = registry;
    }*/
    private ActivityResultLauncher launcher;
    private LocationManager locationManager;
    private String locationProvider = null;

    public LocationHelp(ActivityResultRegistry registry, Context context) {
        this.registry = registry;
        this.mContext = context;
    }

    public ActivityResultLauncher getLauncher() {
        return launcher;
    }

    public void onCreate(LifecycleOwner owner) {
        launcher = registry.register("Location", owner, new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if (result) {
                    getLocation();
                    getLocationHavePer();
                }
                Log.d(TAG, "onActivityResult: " + result);
            }
        });

    }

    private void getLocation() {
        //1.获取位置管理器
        Log.d(TAG, "getLocation: ");
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        //2.获取位置提供器，GPS或是NetWork
        List<String> providers = locationManager.getProviders(true);

        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER;
//            Log.v("TAG", "定位方式GPS");
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
//            Log.v("TAG", "定位方式Network");
        } else {
//            Toast.makeText(this, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
            return;
        }

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
       /* Location location = locationManager.getLastKnownLocation(locationProvider);
        if (location != null) {
            *//*InsertHelp.setLatitude(String.valueOf(location.getLatitude()));
            InsertHelp.setLongitude(String.valueOf(location.getLongitude()));*//*
         *//* Toast.makeText(this, location.getLongitude() + " " +
                        location.getLatitude() + "", Toast.LENGTH_SHORT).show();*//*
                Log.v("TAG", "获取上次的位置-经纬度："+location.getLongitude()+"   "+location.getLatitude());
            getAddress(location);

        } else {
            //监视地理位置变化，第二个和第三个参数分别为更新的最短时间minTime和最短距离minDistace
            locationManager.requestLocationUpdates(locationProvider, 3000, 1, locationListener);
        }*/

    }

    private void getLocationHavePer() {
        try {
            List<String> providers = locationManager.getProviders(true);
            if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
                //如果是Network
                locationProvider = LocationManager.NETWORK_PROVIDER;
            } else if (providers.contains(LocationManager.GPS_PROVIDER)) {
                //如果是GPS
                locationProvider = LocationManager.GPS_PROVIDER;
            }
            Location location = locationManager.getLastKnownLocation(locationProvider);
            if (location != null) {
                getAddress(location);
                Log.v(TAG, "获取上次的位置-经纬度：" + location.getLongitude() + "   " + location.getLatitude() + "   "+location.toString());
            } else {
                // 监视地理位置变化，第二个和第三个参数分别为更新的最短时间minTime和最短距离minDistace
                locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
            }

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    //获取地址信息:城市、街道等信息
    private List<Address> getAddress(Location location) {
        List<Address> result = null;
        try {
            if (location != null) {
                Geocoder gc = new Geocoder(mContext, Locale.getDefault());
                result = gc.getFromLocation(location.getLatitude(),
                        location.getLongitude(), 1);
                Log.v(TAG, "获取地址信息：" + result.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
