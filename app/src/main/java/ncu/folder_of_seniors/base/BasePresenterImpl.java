package ncu.folder_of_seniors.base;

/**
 * @author oywj 标记接口
 */
public interface BasePresenterImpl<V extends BaseView> {

    void attach(V view);

    void detach  ();
}
