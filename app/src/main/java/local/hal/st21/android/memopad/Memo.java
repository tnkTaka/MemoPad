package local.hal.st21.android.memopad;

import java.sql.Timestamp;

/**
 * メモ情報を格納するエンティティクラス。
 */
public class Memo {
    /**
     * 主キーのID値。
     */
    private int _id;
    /**
     * タイトル。
     */
    private String _title;
    /**
     * メモ内容
     */
    private String _content;
    /**
     * 更新日時
     */
    private Timestamp _updateAt;

    //以下アクセサメソッド

    public int getId() {
        return _id;
    }
    public void setId(int id) {
        _id = id;
    }
    public String getTitle() {
        return _title;
    }
    public void setTitle(String title) {
        _title = title;
    }
    public String getContent() {
        return _content;
    }
    public void setContent(String content) {
        _content = content;
    }
    public Timestamp getUpdateAt() {
        return _updateAt;
    }
    public void setUpdateAt(Timestamp updateAt) {
        _updateAt = updateAt;
    }
}
