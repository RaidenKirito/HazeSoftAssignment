package com.example.hazesoftassignment.feature.shared.base

import androidx.recyclerview.widget.RecyclerView
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseAdapter<V : BaseViewHolder> : RecyclerView.Adapter<V>() {
    protected var compositeDisposable: CompositeDisposable? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        compositeDisposable = CompositeDisposable()
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        compositeDisposable?.dispose()
        super.onDetachedFromRecyclerView(recyclerView)
    }
}