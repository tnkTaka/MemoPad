package local.hal.st21.android.memopad;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 第2画面表示用アクティビティクラス。
 * メモ情報編集画面を表示する。
 */
public class MemoEditActivity extends AppCompatActivity {
    /**
     * 新規登録モードか更新モードかを表す定数フィールド。
     */
    private int _mode = MemoListActivity.MODE_INSERT;
    /**
     * 更新モードの際、現在表示しているメモ情報データベース上の主キー値。
    */
    private int _idNo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_edit);

        Intent intent = getIntent();
        _mode = intent.getIntExtra("mode", MemoListActivity.MODE_INSERT);

        if(_mode == MemoListActivity.MODE_INSERT) {
            TextView tvTitleEdit = findViewById(R.id.tvTitleEdit);
            tvTitleEdit.setText(R.string.tv_title_insert);

            Button btnSave = findViewById(R.id.btnSave);
            btnSave.setText(R.string.btn_insert);

            Button btnDelete = findViewById(R.id.btnDelete);
        }
        else {
            _idNo = intent.getIntExtra("idNo", 0);
            DatabaseHelper helper = new DatabaseHelper(MemoEditActivity.this);
            SQLiteDatabase db = helper.getWritableDatabase();
            try {
                Memo memoData = DataAccess.findByPK(db, _idNo);

                EditText etInputTitle = (EditText) findViewById(R.id.etInputTitle);
                etInputTitle.setText(memoData.getTitle());

                EditText etInputContent = findViewById(R.id.etInputContent);
                etInputContent.setText(memoData.getContent());
            }
            catch(Exception ex) {
                Log.e("ERROR", ex.toString());
            }
            finally {
                db.close();
            }
        }
    }

        /**
         * 登録・更新ボタンが押されたときのイベント処理用メソッド
         *
         * @param view 画面部品。
         */
        public void onSaveButtonClick(View view) {
            EditText etInputTitle = findViewById(R.id.etInputTitle);
            String inputTitle = etInputTitle.getText().toString();
            if(inputTitle.equals("")) {
                Toast.makeText(MemoEditActivity.this, R.string.msg_input_title, Toast.LENGTH_SHORT).show();
            } else {
                EditText etInputContent = (EditText) findViewById(R.id.etInputContent);
                String inputContent = etInputContent.getText().toString();
                DatabaseHelper helper = new DatabaseHelper(MemoEditActivity.this);
                SQLiteDatabase db = helper.getWritableDatabase();
                try {
                    if(_mode == MemoListActivity.MODE_INSERT) {
                        DataAccess.insert(db, inputTitle, inputContent);
                    } else {
                        DataAccess.update(db, _idNo, inputTitle, inputContent);
                    }
                } catch(Exception ex) {
                    Log.e("ERROR", ex.toString());
                } finally {
                    db.close();
                }
                finish();
            }
        }
    /**
     * 戻るボタンが押されたときのイベント処理用メソッド。
     *
     * @param view 画面部品。
     */
    public void onBackButtonClick(View view) {
        finish();
    }
    /**
     * 削除ボタンが押されたときのイベント処理用メソッド。
     *
     * @param view 画面部品。
     */
    public void onDeleteButtonClick(View view) {
        DatabaseHelper helper = new DatabaseHelper(MemoEditActivity.this);
        SQLiteDatabase db = helper.getWritableDatabase();
        try{
            DataAccess.delete(db, _idNo);
        }
        catch(Exception ex) {
            Log.e("ERROR",ex.toString());
        }
        finally {
            db.close();
        }
        finish();
    }
}