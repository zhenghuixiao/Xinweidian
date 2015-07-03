package com.xinkaishi.apple.xinweidian.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xinkaishi.apple.xinweidian.Adapter.Adapter_goods_center;
import com.xinkaishi.apple.xinweidian.Adapter.Adapter_goods_menugrid;
import com.xinkaishi.apple.xinweidian.Bean.ListBean.ListGoods;
import com.xinkaishi.apple.xinweidian.Bean.ListBean.ListState;
import com.xinkaishi.apple.xinweidian.Bean.MenuBean.MenuParent;
import com.xinkaishi.apple.xinweidian.Bean.MenuBean.MenuState;
import com.xinkaishi.apple.xinweidian.DAO.ImgDAO;
import com.xinkaishi.apple.xinweidian.DAO.MenuDAO;
import com.xinkaishi.apple.xinweidian.DAO.ShoppingcartDAO;
import com.xinkaishi.apple.xinweidian.R;
import com.xinkaishi.apple.xinweidian.Until.DataAnalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Goods_centerActivity extends ActionBarActivity {
    public static Goods_centerActivity instance = null;
    //选择的分类 子分类 商品数量
    private TextView tv_goods_center_menu1, tv_goods_center_menu2, tv_goods_center_counts;
    //三个排序
    private TextView tv_goods_center_sales, tv_goods_center_profit, tv_goods_center_pricein;

    private List<MenuParent> listmp;//菜单列表
    private LinearLayout ll_goods_center_down; //菜单栏 layout
    private MenuState menu;//数据对象
    private RelativeLayout rl_goods_center_menu;//隐藏子菜单栏
    private GridView gv_goods_center_grid;
    private ArrayList<HashMap<String, Object>> menulist;
    private Adapter_goods_menugrid adapter_goods_menugrid;
    private Adapter_goods_center adapter_goods_center;

    private int menuchecked;//刷新菜单时选中的position
    private int checked;//刷新菜单时选中的大类 12345
    private boolean isopen;//菜单开关状态
    private int checkedID;

    private RadioGroup gr_goods_center_menu; //菜单栏
    private RadioButton rb_goods_center_digital, rb_goods_center_appliance,
            rb_goods_center_infant, rb_goods_center_clothing,rb_goods_center_5; //菜单栏

    private ListView lv_goods_center_list;
    private ListState listState;
    private ArrayList<HashMap<String, Object>> list;// 数据
    private View darkview;//暗色背景
    private ImgDAO imgdao;
    private MenuDAO menuDAO;
    private ShoppingcartDAO shoppingcartDAO;


    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_center);

        instance = this;
        initActionBar();
        initView(); //加载控件
        new initList("").execute();
        initMain();//主界面的控件显示
        initMenu();
        setFragmentLlistener(); //监听菜单栏

//        setListView();
    }

    private void initActionBar() {
        // 显示导航按钮
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.pay_nav_ret);//设置返回键图标

        //        //TODO 自定义布局 标题
//        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        actionBar.setDisplayShowCustomEnabled(true);
//        actionBar.setCustomView(R.layout.layout_actionbar_huoyuanzhongxin);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_goods_center, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.action_shoppingcart:
                Intent intent1 = new Intent(Goods_centerActivity.this, Shopping_cartActivity.class);
                startActivity(intent1);
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_order:
                Intent intent2 = new Intent(Goods_centerActivity.this, Goods_OrdersActivity.class);
                startActivity(intent2);
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_collection:
                Toast t = Toast.makeText(this, "", Toast.LENGTH_SHORT);
                t.setGravity(Gravity.CENTER, 0, 0);
                View layout = LayoutInflater.from(this).inflate(R.layout.layout_toast_style_inshop, null);
                t.setView(layout);
                t.show();
                return true;
            case R.id.action_search:
                Intent intent4 = new Intent(Goods_centerActivity.this, SearchActivity.class);
                startActivity(intent4);
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            // 导航返回键
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        imgdao.closeDB();
        menuDAO.closeDB();
        shoppingcartDAO.closeDB();
        super.onDestroy();
    }

    private void initView() {
        gr_goods_center_menu = (RadioGroup)findViewById(R.id.gr_goods_center_menu);
        rb_goods_center_digital = (RadioButton)findViewById(R.id.rb_goods_center_digital);
        rb_goods_center_appliance = (RadioButton)findViewById(R.id.rb_goods_center_appliance);
        rb_goods_center_infant = (RadioButton)findViewById(R.id.rb_goods_center_infant);
        rb_goods_center_clothing = (RadioButton)findViewById(R.id.rb_goods_center_clothing);
        rb_goods_center_5 = (RadioButton)findViewById(R.id.rb_goods_center_5);
        ll_goods_center_down = (LinearLayout)findViewById(R.id.ll_goods_center_down);
        rl_goods_center_menu = (RelativeLayout)findViewById(R.id.rl_goods_center_menu);
        lv_goods_center_list = (ListView)findViewById(R.id.lv_goods_center_list);
        gv_goods_center_grid = (GridView)findViewById(R.id.gv_goods_center_grid);
        tv_goods_center_menu1 = (TextView)findViewById(R.id.tv_goods_center_menu1);
        tv_goods_center_menu2 = (TextView)findViewById(R.id.tv_goods_center_menu2);
        tv_goods_center_counts = (TextView)findViewById(R.id.tv_goods_center_counts);
        darkview = findViewById(R.id.darkview);
        imgdao = new ImgDAO(getApplication());
        menuDAO = new MenuDAO(getApplication());
        menulist = new ArrayList<>();
        shoppingcartDAO = new ShoppingcartDAO(getApplication());
        list = new ArrayList<HashMap<String, Object>>();

        isopen = false;//默认关闭
        checkedID = 0;//默认0 无选中
        //设置图片大小
//        Drawable[] drawables = rb_goods_center_5.getCompoundDrawables();
//        drawables[1].setBounds(0,0,20,20); // X Y轴坐标，width，height
//        rb_goods_center_5.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
    }

    private void initMain(){
        tv_goods_center_menu1.setText("所有商品");
        tv_goods_center_menu2.setText("");
    }
    /**
     *
     * 获取菜单数据
     */

    private void initMenu(){
        // 1为shopid
        String json = menuDAO.getMenujson(1);;
        Gson gson = new Gson();
        menu = gson.fromJson(json, new TypeToken<MenuState>() {}.getType());
        listmp = menu.getData().getList();

        //菜单栏显示
        rb_goods_center_digital.setText(listmp.get(0).getName());
        rb_goods_center_appliance.setText(listmp.get(1).getName());
        rb_goods_center_infant.setText(listmp.get(2).getName());
        rb_goods_center_clothing.setText(listmp.get(3).getName());
        rb_goods_center_5.setText(listmp.get(4).getName());

    }
    /**
     *
     * 主菜单监听
     */
    private void setFragmentLlistener() {
        List<MenuParent> listmp = menu.getData().getList();
        rb_goods_center_digital.setOnClickListener(new Myonclick_menu(1));
        rb_goods_center_appliance.setOnClickListener(new Myonclick_menu(2));
        rb_goods_center_infant.setOnClickListener(new Myonclick_menu(3));
        rb_goods_center_clothing.setOnClickListener(new Myonclick_menu(4));
        rb_goods_center_5.setOnClickListener(new Myonclick_menu(5));
        // 数码 家电 母婴 服装
        gr_goods_center_menu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_goods_center_digital:
                    Toast.makeText(Goods_centerActivity.this, "数码", Toast.LENGTH_SHORT).show();
                    //todo
                    menulist.clear();
                    for(int a = 0; a < menu.getData().getList().get(0).getChild().size(); a ++){
                        HashMap<String, Object> hm = new HashMap<>();
                        hm.put("name", menu.getData().getList().get(0).getChild().get(a).getName());
                        menulist.add(hm);
                    }
                    if(checked == 1){ //判断是否是刷新列表时的菜单
                        adapter_goods_menugrid.setSelected(menuchecked);
                    }else{
                        adapter_goods_menugrid.setSelected(0);
                    }
                    adapter_goods_menugrid.notifyDataSetChanged();
                    break;
                case R.id.rb_goods_center_appliance:
                    Toast.makeText(Goods_centerActivity.this, "电器", Toast.LENGTH_SHORT).show();
                    menulist.clear();
                    for(int a = 0; a < menu.getData().getList().get(1).getChild().size(); a ++){
                        HashMap<String, Object> hm = new HashMap<>();
                        hm.put("name", menu.getData().getList().get(1).getChild().get(a).getName());
                        menulist.add(hm);
                    }
                    if(checked == 2){ //判断是否是刷新列表时的菜单
                        adapter_goods_menugrid.setSelected(menuchecked);
                    }else{
                        adapter_goods_menugrid.setSelected(0);
                    }
                    adapter_goods_menugrid.notifyDataSetChanged();
                    break;
                case R.id.rb_goods_center_infant:
                    Toast.makeText(Goods_centerActivity.this, "母婴", Toast.LENGTH_SHORT).show();
                    menulist.clear();
                    for(int a = 0; a < menu.getData().getList().get(2).getChild().size(); a ++){
                        HashMap<String, Object> hm = new HashMap<>();
                        hm.put("name", menu.getData().getList().get(2).getChild().get(a).getName());
                        menulist.add(hm);
                    }
                    if(checked == 3){ //判断是否是刷新列表时的菜单
                        adapter_goods_menugrid.setSelected(menuchecked);
                    }else{
                        adapter_goods_menugrid.setSelected(0);
                    }
                    adapter_goods_menugrid.notifyDataSetChanged();
                    break;
                case R.id.rb_goods_center_clothing:
                    Toast.makeText(Goods_centerActivity.this, "服装", Toast.LENGTH_SHORT).show();
                    menulist.clear();
                    for(int a = 0; a < menu.getData().getList().get(3).getChild().size(); a ++){
                        HashMap<String, Object> hm = new HashMap<>();
                        hm.put("name", menu.getData().getList().get(3).getChild().get(a).getName());
                        menulist.add(hm);
                    }
                    if(checked == 4){ //判断是否是刷新列表时的菜单
                        adapter_goods_menugrid.setSelected(menuchecked);
                    }else{
                        adapter_goods_menugrid.setSelected(0);
                    }
                    adapter_goods_menugrid.notifyDataSetChanged();
                    break;
                case R.id.rb_goods_center_5:
                    Toast.makeText(Goods_centerActivity.this, "", Toast.LENGTH_SHORT).show();
                    menulist.clear();
                    for(int a = 0; a < menu.getData().getList().get(4).getChild().size(); a ++){
                        HashMap<String, Object> hm = new HashMap<>();
                        hm.put("name", menu.getData().getList().get(4).getChild().get(a).getName());
                        menulist.add(hm);
                    }
                    if(checked == 5){ //判断是否是刷新列表时的菜单
                        adapter_goods_menugrid.setSelected(menuchecked);
                    }else{
                        adapter_goods_menugrid.setSelected(0);
                    }
                    adapter_goods_menugrid.notifyDataSetChanged();
                    break;


            }
            }
        });

    }
    class Myonclick_menu implements View.OnClickListener {
        private int id;
        public Myonclick_menu(int id){
            this.id = id;

        }

        @Override
        public void onClick(View v) {
            Log.e("点击", "checkid=" + checkedID + " id=" + id + " isopen=" + isopen);
            if (!isopen) {
                Animation animation_on = AnimationUtils.loadAnimation(Goods_centerActivity.this, R.anim.translate_menu_on);
                ll_goods_center_down.startAnimation(animation_on);
                rl_goods_center_menu.setVisibility(View.VISIBLE);
                //相同的菜单打开，显示选中的子菜单
                if(checkedID == id){
                    adapter_goods_menugrid.setSelected(menuchecked);
                }
                isopen = true;
                Log.e("打开","1111111");
            }else if(checkedID == id) {
                Log.e("关闭", "checkedID == id");
                colseMenu(0);
            }else{
                Log.e("不变","checkedID != id" + " isopen=" + isopen);
            }
            checkedID = id;
        }
    }
    private void setListView() {
        adapter_goods_center = new Adapter_goods_center(
                Goods_centerActivity.this, list, R.layout.layout_goods_center,
                new String[]{"id", "default_img", "name", "price", "profit", "import_price"},
                new int[]{R.id.iv_goodscenter_image, R.id.tv_goodscenter_title, R.id.tv_goodscenter_price_out,
                        R.id.tv_goodscenter_profit, R.id.tv_goodscenter_price_in, R.id.tv_goodscenter_shoucang,
                        R.id.tv_goodscenter_goodsIn, R.id.tv_goodscenter_getInshop}, imgdao, shoppingcartDAO, darkview);
        lv_goods_center_list.setAdapter(adapter_goods_center);
        lv_goods_center_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("goods", list.get(position));
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(Goods_centerActivity.this, Goods_detailActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.pic_left_in, R.anim.pic_left_out);
            }
        });

        setGridview();

    }
    /**
     *列表的刷新
     * 适配菜单grid 子菜单的监听
     */
    private void setGridview(){
        adapter_goods_menugrid = new Adapter_goods_menugrid(this, menulist, R.layout.layout_menu_gridview);
        gv_goods_center_grid.setAdapter(adapter_goods_menugrid);
        gv_goods_center_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter_goods_menugrid.setSelected(position);
                adapter_goods_menugrid.notifyDataSetChanged();
                menuchecked = position;
                checked = checkedID;

                //还需传url
                colseMenu(1);//1 表示是子菜单单击 触发关闭
                tv_goods_center_menu1.setText(listmp.get(checked-1).getName().toString());
                if(position == 0){
                    tv_goods_center_menu2.setText("");
                }else {
                    tv_goods_center_menu2.setText(" > " + listmp.get(checked - 1).getChild().get(position - 1).getName().toString());
                }
            }
        });
    }

    /**
     *
     * 关闭菜单
     * a为1时代表需要刷新列表
     */
    private void colseMenu(final int a){
        Animation animation_off = AnimationUtils.loadAnimation(Goods_centerActivity.this, R.anim.translate_menu_off);
        ll_goods_center_down.startAnimation(animation_off);
        isopen = false; //菜单关闭标记

        animation_off.setFillEnabled(true);   //解决移动后的闪烁问题
        animation_off.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rl_goods_center_menu.setVisibility(View.GONE);
                if(a == 1){
                    list.clear();
                    new initList("").execute();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     *
     * 显示或刷新列表
     * 提供url
     */
    private class initList extends AsyncTask<Void, Void, String> {
        private  String url;
        public initList(String url){
            this.url = url;
        }

        @Override
        protected String doInBackground(Void... params) {

            String json = null;
            try {

                json = DataAnalysis.readParse("http://pc.xinkaishi.com/shop/item/list");
                Log.e("list", json);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(String json) {
            gson = new Gson();
            listState = gson.fromJson(json, new TypeToken<ListState>() {}.getType());
            Log.e("list", listState.getMessage());
            List<ListGoods> list1 = listState.getData().getList();

            tv_goods_center_counts.setText(listState.getData().getCount() + "件"); //每次加载或刷新都跟新数量
            if(list.size() < 1){
                for(int a = 0; a < list1.size(); a ++){
                    HashMap<String, Object> hm = new HashMap<String, Object>();
                    hm.put("id", list1.get(a).getId());
                    hm.put("name", list1.get(a).getName());
                    hm.put("default_img", list1.get(a).getDefault_img());//默认展示图
                    hm.put("roll_images", list1.get(a).getRoll_images());//轮播图
                    hm.put("price", list1.get(a).getPrice());//价格
                    hm.put("import_price", list1.get(a).getImport_price());//进价
                    hm.put("sale_amount", list1.get(a).getSale_amount()); //规格
                    hm.put("profit", list1.get(a).getProfit());//利润
                    hm.put("inventory", list1.get(a).getInventory()); //库存
                    hm.put("user_collect", list1.get(a).getUser_collect());// 收藏
                    hm.put("has_add", list1.get(a).getHas_add());//是否已加入店铺 1为加入
                    hm.put("num", list1.get(a).getHas_add());//数据库数量key
                    list.add(hm);
                }
                setListView();
            }else{
                list.clear();
                for(int a = 0; a < list1.size(); a ++){
                    HashMap<String, Object> hm = new HashMap<String, Object>();
                    hm.put("id", list1.get(a).getId());
                    hm.put("name", list1.get(a).getName());
                    hm.put("default_img", list1.get(a).getDefault_img());//默认展示图
                    hm.put("roll_images", list1.get(a).getRoll_images());//轮播图
                    hm.put("price", list1.get(a).getPrice());//价格
                    hm.put("import_price", list1.get(a).getImport_price());//进价
                    hm.put("sale_amount", list1.get(a).getSale_amount()); //规格
                    hm.put("profit", list1.get(a).getProfit());//利润
                    hm.put("inventory", list1.get(a).getInventory()); //库存
                    hm.put("user_collect", list1.get(a).getUser_collect());// 收藏
                    hm.put("has_add", list1.get(a).getHas_add());//是否已加入店铺 1为加入
                    hm.put("num", list1.get(a).getHas_add());//数据库数量key
                    list.add(hm);
                }
                adapter_goods_center.notifyDataSetChanged();
            }
            Log.e("Test", listState.getData().getList().get(0).getImport_price() + "");
            super.onPostExecute(json);
        }
    }

}
