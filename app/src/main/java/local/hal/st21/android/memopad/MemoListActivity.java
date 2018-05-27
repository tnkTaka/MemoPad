package local.hal.st21.android.memopad;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * 第1画面表示用アクティビティクラス。
 * メモリストを表示する。
 */

public class MemoListActivity extends AppCompatActivity {
    /**
     * 新規登録モードを表す定数フィールド。
     */
    static final int MODE_INSERT = 1;
    /**
     * 更新モードを表す定数フィールド。
     */
    static final int MODE_EDIT = 2;
    /**
     * メモリスト耀ListView。
     */
    private ListView _lvMemoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_list);

        _lvMemoList = findViewById(R.id.lvMemoList);
        _lvMemoList.setOnItemClickListener(new ListItemClickListener());
    }

    @Override
    public void onResume() {
        super.onResume();
        DatabaseHelper helper = new DatabaseHelper(MemoListActivity.this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = DataAccess.findAll(db);
        String[] from = {"title"};
        int[] to = {android.R.id.text1};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(MemoListActivity.this, android.R.layout.simple_list_item_1, cursor, from, to, 0);
        _lvMemoList.setAdapter(adapter);
    }

    /**
     * 新規ボタンが押されたときのイベント処理用メソッド。
     *
     * @param view 画面部品。
     */
    public void onNewButtonClick(View view) {
        Intent intent = new Intent(MemoListActivity.this, MemoEditActivity.class);
        intent.putExtra("mode", MODE_INSERT);
        startActivity(intent);
    }

    /**
     * リストがクリックされたときのリスナクラス。
     */
    private class ListItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            Cursor item = (Cursor) parent.getItemAtPosition(position);
            int idxId = item.getColumnIndex("_id");
            int idNo =  item.getInt(idxId);

            Intent intent = new Intent(MemoListActivity.this, MemoEditActivity.class);
            intent.putExtra("mode", MODE_EDIT);
            intent.putExtra("idNo", idNo);
//          intent.putExtra("idNo", "int"; id);
            startActivity(intent);
        }
    }
}