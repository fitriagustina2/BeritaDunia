package com.fitriagustina.beritadunia.ui.berita

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fitriagustina.beritadunia.data.network.model.BeritaModel
import com.fitriagustina.beritadunia.data.room.entity.BeritaEntity
import com.fitriagustina.beritadunia.databinding.ItemBeritaBinding

class BeritaAdapter() : RecyclerView.Adapter<BeritaAdapter.ViewHolder>() {
    private var newsList = arrayListOf<BeritaEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
        return ViewHolder(ItemBeritaBinding.inflate(view, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    open inner class ViewHolder(
        val binding: ItemBeritaBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: BeritaEntity){
            binding.apply {
                Glide.with(ivImage.rootView)
                    .load(data.urlToImage)
                    .into(ivImage)
                tvTitle.text = data.title
                tvDesc.text = data.description

                root.setOnClickListener {
                    mListener!!.onClick(data)
                }
            }
        }
    }

    //fungsi untuk menambahkan data ke adapter
    fun addItems(data: List<BeritaEntity>){
        this.newsList.clear()
        this.newsList.addAll(data)
        notifyDataSetChanged()
    }

    //iterface jika item di click
    interface OnItemClickListener {
        fun onClick(data: BeritaEntity)
    }
    fun setOnItemClickListener(listener: OnItemClickListener?) {
        mListener = listener
    }

    companion object {
        private var mListener: OnItemClickListener? = null
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}
