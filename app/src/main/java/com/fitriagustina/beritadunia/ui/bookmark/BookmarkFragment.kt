package com.fitriagustina.beritadunia.ui.bookmark

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.fitriagustina.beritadunia.R
import com.fitriagustina.beritadunia.data.room.entity.BeritaEntity
import com.fitriagustina.beritadunia.databinding.FragmentBookmarkBinding
import com.fitriagustina.beritadunia.ui.berita.BeritaAdapter
import com.fitriagustina.beritadunia.ui.detail_berita.DetailBeritaActivity
import org.koin.android.ext.android.inject

class BookmarkFragment : Fragment() {

    private lateinit var binding: FragmentBookmarkBinding

    private val viewModel: BookmarkViewModel by inject()
    private val bookmarkAdapter: BookmarkAdapter by lazy { BookmarkAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setup untuk recyclerview dengan orientasi vertical
        binding.rvBookmark.layoutManager = LinearLayoutManager(requireContext())
        //memasang adapter ke dalam recyclerview ui
        binding.rvBookmark.adapter = bookmarkAdapter

        //memasang listener untuk mengecek saat item didalam recyclerview diklik
        bookmarkAdapter.setOnItemClickListener(object : BookmarkAdapter.OnItemClickListener{
            override fun onClick(data: BeritaEntity) {
                //jika salah satu item diklik maka akan menuju ke halaman DetailBeritaActivity dengan membawa data berita yg diklik tadi
                val intent = Intent(requireContext(), DetailBeritaActivity::class.java)
                intent.putExtra("data_berita", data)
                startActivity(intent)
            }
        })
    }

    //fungsi untuk mengambil data bookmark dengan memanggil fungsi getBookmarks dari viewModel
    private fun getBookmark(){
        viewModel.getBookmarks({
            //jika berhasil mengambil data bookmark maka kemudian ditambahkan ke adapter untuk ditampilkan oleh recyclerview
            bookmarkAdapter.addItems(it)
        }, {
            //jika gagal dalam mengambil data bookmark maka akan menampilkan toast error
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
        })
    }

    //setiap kali halaman di resume/dibuka maka akan memanggil fungsi getBookmark untuk update dalam menampilkan list bookmark
    override fun onResume() {
        super.onResume()
        getBookmark()
    }
}