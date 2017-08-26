package jp.techacademy.miyuki.manabe.taskapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

public class AddCategoryActivity extends AppCompatActivity {


    private EditText mName, mId;
    private Realm mRealm;
    private RealmChangeListener mRealmListener = new RealmChangeListener() {
        @Override
        public void onChange(Object element) {
            reloadListView();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        // ActionBarを設定する
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mId = (EditText)findViewById(R.id.id_edit_text);
        mName= (EditText)findViewById(R.id.category_edit_text);
        findViewById(R.id.done_button).setOnClickListener(mOnDoneClickListener);

        // Realmの設定
        mRealm = Realm.getDefaultInstance();
        mRealm.addChangeListener(mRealmListener);

        reloadListView();


    }

    private View.OnClickListener mOnDoneClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addTask(Integer.parseInt(mId.getText().toString()), mName.getText().toString());
        }
    };

    private void addTask(int id, String name) {
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        Category mCategory = new Category();
        mCategory.setId(id);
        mCategory.setName(name);
        realm.copyToRealmOrUpdate(mCategory);
        realm.commitTransaction();
        realm.close();

    }

    private void reloadListView() {


        RealmResults<Category> categoryRealmResults = mRealm.where(Category.class).findAllSorted("id", Sort.ASCENDING);
        CategoryAdapter categoryAdapter =new CategoryAdapter(AddCategoryActivity.this);
        categoryAdapter.setCategoryList(mRealm.copyFromRealm(categoryRealmResults));

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(categoryAdapter);
        // 表示を更新するために、アダプターにデータが変更されたことを知らせる
        categoryAdapter.notifyDataSetChanged();

        // 入力値クリア
        mId.setText("");
        mName.setText("");
    }
}
