package com.example.roompage;

public interface IListChangeCallback<T> {
    void onChanged(T sender);

    void onItemRangeChanged(T sender, int positionStart, int itemCount);

    void onItemRangeInserted(T sender, int positionStart, int itemCount);

    void onItemRangeMoved(T sender, int fromPosition, int toPosition, int itemCount);

    void onItemRangeRemoved(T sender, int positionStart, int itemCount);
}