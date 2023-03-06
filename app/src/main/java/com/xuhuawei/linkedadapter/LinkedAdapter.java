package com.xuhuawei.linkedadapter;

import android.view.View;
import android.widget.ListView;

import com.xuhuawei.linkedadapter.base.LifeCircleContext;
import com.xuhuawei.linkedadapter.base.MyListBaseAdapter;

import java.util.List;

/*
 * User: xuhuawei
 * Date: 2023/2/16
 * Desc:
 */
public class LinkedAdapter extends MyListBaseAdapter<LinkedViewHolder, LinkedBean> {
    private ListView listView;
    private MyLinkedList.Node<LinkedBean> lastNode;
    private MyLinkedList<LinkedBean> realList;
    private int lastPositon = 0;
    private boolean isOptim = true;

    public LinkedAdapter(LifeCircleContext context, List<LinkedBean> arrayList) {
        super(context, arrayList);
        if (arrayList instanceof MyLinkedList) {
            realList = (MyLinkedList<LinkedBean>) arrayList;
        }
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.adapter_linked_layout;
    }

    private boolean isChange = false;

    @Override
    public void notifyDataSetChanged() {
        isChange = true;
        super.notifyDataSetChanged();
    }

    @Override
    public LinkedBean getItem(int position) {
        if (isOptim) {
            if (realList != null) {
                // 如果有notify刷新的话 那么重新去构建数据
                if (isChange) {
                    lastPositon = -1;
                }
                if (listView != null) {
                    // 获取当前最上面的那个view 是位置号，
                    int currentFirstPositon = listView.getFirstVisiblePosition();
                    // 但是listview有一个问题 getFirstVisiblePosition方法获取的是展示的第一个完整的位置；
                    // 如果出现一部分 就出现了位置不准问题 因此增加一个兼容
                    if (position < currentFirstPositon) {
                        currentFirstPositon = position;
                    }
                    // 如果是刷新notifydata 或者首次进入 那么需要重头开始查询位置
                    if (lastPositon == currentFirstPositon || lastPositon == -1) {
                        isChange = false;
                        lastPositon = currentFirstPositon;
                        if (isChange || lastNode == null) {
                            lastNode = realList.getNode(position);
                            return lastNode.item;
                        } else {
                            // 如果没有滑动 那么通过headBeanNode这个基准 就不变
                            // lastFirstPositon和headBeanNode是一堆映射 ；通过diff去链表上查询
                            int diff = position - lastPositon;
                            MyLinkedList.Node<LinkedBean> item = realList.getNode(lastNode, diff);
                            if (item != null) {
                                return item.item;
                            }
                        }
                    } else {
                        // 如果如果滑动了 那么需要更新基准的lastFirstPositon和headBeanNode基准
                        int diff1 = currentFirstPositon - lastPositon;
                        MyLinkedList.Node<LinkedBean> diffItem = realList.getNode(lastNode, diff1);
                        lastNode = diffItem;
                        lastPositon = currentFirstPositon;
                        // 基准更新完了 重复上面的相同的方法 通过diff去查找
                        int diff2 = position - lastPositon;
                        MyLinkedList.Node<LinkedBean> item = realList.getNode(lastNode, diff2);
                        if (item != null) {
                            return item.item;
                        }
                    }
                }
                LinkedBean bean = super.getItem(position);
                return bean;
            } else {
                LinkedBean bean = super.getItem(position);
                return bean;
            }
        } else {
            LinkedBean bean = super.getItem(position);
            return bean;
        }
    }

    @Override
    public LinkedViewHolder getViewHolder(View view) {
        return new LinkedViewHolder(this, view);
    }
}
