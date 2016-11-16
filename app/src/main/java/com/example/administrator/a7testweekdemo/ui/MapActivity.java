package com.example.administrator.a7testweekdemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.baidu.location.Address;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.administrator.a7testweekdemo.R;
import com.example.administrator.a7testweekdemo.baidu.mapapi.overlayutil.PoiOverlay;

import java.util.List;

public class MapActivity extends AppCompatActivity implements BDLocationListener, OnGetPoiSearchResultListener {
    private MapView mMapView;
    private EditText cityEditText;
    private EditText keywordEditText;

    private BaiduMap mBaiduMap;

    //定位需要LocationClient
    private LocationClient mLocationClient;
    //检索实例
    private PoiSearch mPoiSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);

        initView();
        //初始化LocationClient
        initLocationClient();
        //初始化PoiSearch
        initPoiSearch();

    }

    private void initPoiSearch() {
        //实例化检索类
        mPoiSearch= PoiSearch.newInstance();

        //设置监听
        mPoiSearch.setOnGetPoiSearchResultListener(this);
    }

    private void initLocationClient() {
        //开启定位图层
        mBaiduMap.setMyLocationEnabled(true);

        //1、实例化对象：LocationClient
        mLocationClient=new LocationClient(getApplicationContext());

        //2、设置监听
        mLocationClient.registerLocationListener(this);

        //3、设置定位的参数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;//定位间隔
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);

        //开启定位
        mLocationClient.start();
    }

    private void initView() {
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        cityEditText = ((EditText) findViewById(R.id.activity_map_cityId));
        keywordEditText = ((EditText) findViewById(R.id.activity_map_keyword));

        mBaiduMap = mMapView.getMap();
    }


    //点击按钮进行搜索
    public void searchClick(View view) {
        String city = cityEditText.getText().toString().trim();
        String keyword = keywordEditText.getText().toString().trim();

        PoiCitySearchOption poiCitySearchOption = new PoiCitySearchOption();

        //设置
        poiCitySearchOption.city(city);
        poiCitySearchOption.keyword(keyword);
        poiCitySearchOption.pageCapacity(10);//每页展示10个数据
        poiCitySearchOption.pageNum(10);//展示10页

        mPoiSearch.searchInCity(poiCitySearchOption);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();

        //取消定位的监听
        mLocationClient.unRegisterLocationListener(this);
        //销毁检索的实例
        mPoiSearch.destroy();
    }

    //定位成功的回调
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        //获取当前位置
        Address address = bdLocation.getAddress();
        //获取当前城市
        String city = bdLocation.getCity();
        //获取当前省份
        String province = bdLocation.getProvince();

        //显示定位的位置
        MyLocationData.Builder builder = new MyLocationData.Builder();

        //设置纬度
        builder.latitude(bdLocation.getLatitude());
        //设置经度
        builder.longitude(bdLocation.getLongitude());
        //设置精确度
        builder.accuracy(bdLocation.getRadius());

        MyLocationData myLocationData = builder.build();
        mBaiduMap.setMyLocationData(myLocationData);

        //将百度地图移动到定位点
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude()));
        mBaiduMap.animateMapStatus(mapStatusUpdate);
    }


    //获取检索的结果
    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        if (poiResult==null||poiResult.error== SearchResult.ERRORNO.RESULT_NOT_FOUND){
            return;
        }
        if (poiResult.error==SearchResult.ERRORNO.NO_ERROR){
            mBaiduMap.clear();

            //创建PoiOverlay
            PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
            //设置overlay可以处理标注点击事件
            mBaiduMap.setOnMarkerClickListener(overlay);
            //设置PoiOverlay数据
            overlay.setData(poiResult);
            //添加PoiOverlay到地图中
            overlay.addToMap();
            overlay.zoomToSpan();
        }
    }

    //检索结果的详情，可以通过webView来展示
    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
        if (poiDetailResult==null||poiDetailResult.error==SearchResult.ERRORNO.RESULT_NOT_FOUND){
            return;
        }
        if (poiDetailResult.error==SearchResult.ERRORNO.NO_ERROR){
            Intent intent=new Intent(this,PoisearchDetailActivity.class);

            //intent携带网址到详情页中
            //网址在poiDetailResult中
            poiDetailResult.getPrice();
            poiDetailResult.getShopHours();

            String detailUrl = poiDetailResult.getDetailUrl();
            intent.putExtra("detailUrl",detailUrl);
            startActivity(intent);
        }
    }

    //室内检索
    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
    }

    private class MyPoiOverlay extends PoiOverlay {

        /**
         * 构造函数
         *
         * @param baiduMap 该 PoiOverlay 引用的 BaiduMap 对象
         */
        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int i) {
            //详情点击
            List<PoiInfo> allPoi = getPoiResult().getAllPoi();
            //所点击的poi的信息
            PoiInfo poiInfo = allPoi.get(i);
            //点击搜索结果的某一个poi之后
            mPoiSearch.searchPoiDetail(new PoiDetailSearchOption().poiUid(poiInfo.uid));

            //该方法执行之后将回调onGetPoiDetailResult()方法
            return super.onPoiClick(i);
        }
    }
}
