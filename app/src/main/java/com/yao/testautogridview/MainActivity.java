package com.yao.testautogridview;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Tips:
 * GridView需固定高度
 * gird_item和GirdView同样的高度
 */
public class MainActivity extends AppCompatActivity {

    private MGridView mGridView;
    // 当前滚动次数
    private int count = 0;
    // 数据size, 一定要是girdview中numColumns的倍数。
    // 当前numColums为3，所以3*6=18
    private int data_size = 18;

    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayUtils.init(this);

        //准备要添加的数据条目
        List<Map<String, Object>> items = new ArrayList<>();
        for (int i = 0; i < data_size; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("imageItem", R.mipmap.ic_launcher);//添加图像资源的ID
            item.put("textItem", "icon" + i);//按序号添加ItemText
            items.add(item);
        }

        //实例化一个适配器
        SimpleAdapter adapter = new SimpleAdapter(this,
                items,
                R.layout.grid_item,
                new String[]{"imageItem", "textItem"},
                new int[]{R.id.image_item, R.id.text_item});

        //获得GridView实例
        mGridView = (MGridView) findViewById(R.id.gridView);
        //为GridView设置适配器
        mGridView.setAdapter(adapter);
        // item点击事件
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, "position:" + i, Toast.LENGTH_SHORT).show();
            }
        });

        timer = new CountDownTimer(Integer.MAX_VALUE, 2000) {
            @Override
            public void onTick(long l) {
                if (count != 0 && count % (data_size / mGridView.getNumColumns()) == 0) {
                    mGridView.setSelection(0);
                }
                count++;
                mGridView.smoothScrollBy(DisplayUtils.dp2Px(100), 800);
            }

            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this, "结束", Toast.LENGTH_SHORT).show();
            }
        };
        // timer页面销毁时记得cancel()
        timer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != timer) {
            timer.cancel();
        }
    }
}
