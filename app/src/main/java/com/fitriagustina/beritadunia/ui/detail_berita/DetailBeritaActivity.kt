package com.fitriagustina.beritadunia.ui.detail_berita

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.fitriagustina.beritadunia.R
import com.fitriagustina.beritadunia.data.room.entity.BeritaEntity
import com.fitriagustina.beritadunia.databinding.ActivityDetailBeritaBinding
import org.koin.android.ext.android.inject

class DetailBeritaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBeritaBinding

    private val viewModel: DetailBeritaViewModel by inject()
    private var dataBerita: BeritaEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBeritaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //mengambil data berita yang diklik pada halaman sebelumnya untuk disimpan ke variable dataBerita
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            dataBerita = intent.getParcelableExtra("data_berita", BeritaEntity::class.java)
        } else {
            dataBerita = intent.getParcelableExtra("data_berita")
        }

        //action pada navigation back pada toolbar
        binding.toolbar.setNavigationOnClickListener { finish() }

        //jika pada dataBerita tidak kosong maka view akan diset sesuai data berita yang ada
        dataBerita?.let { berita ->
            //menampilkan gambar dari berita menggunakan library glide
            Glide.with(this@DetailBeritaActivity)
                .load(berita.urlToImage)
                .into(binding.ivImage)

            //mengset judul berita
            binding.tvTitle.text = berita.title
            //mengset isi berita
            binding.tvDesc.text = berita.content

            //memanggil fungsi checkBookmarkStatus untuk mengecek apakah berita yang tampil itu ter bookmark atau tidak
            checkBookmarkStatus(berita.title)

            //jika button bookmark diklik
            binding.btnBookmark.setOnClickListener {
                //mengecek status isBookmarked pada viewModel, jika value dari isBookmarked nya true maka akan menghapus data berita pada bookmark di room database
                //jika value dari isBookmarked nya false, maka akan menambah data berita di halaman ini ke dalam bookmark di room databaase
                if (viewModel.isBookmarked){
                    deleteBookmark(berita.title)
                } else {
                    addBookmark(berita)
                }
            }
        }
    }

    //fungsi untuk menyimpan data berita ke room database dengan memanggil fungsi addBookmark dari viewModel
    private fun addBookmark(beritaEntity: BeritaEntity){
        viewModel.addBookmark(beritaEntity, {
            checkBookmarkStatus(beritaEntity.title)
        }) {
            Log.d(DetailBeritaActivity::class.java.simpleName, it.message.toString())
        }
    }

    //fungsi untuk menghapus data berita di bookmark pada room database dengan memanggil fungsi deleteBookmark dari viewModel
    private fun deleteBookmark(title: String){
        viewModel.deleteBookmark(title, {
            checkBookmarkStatus(title)
        }){
            Log.d(DetailBeritaActivity::class.java.simpleName, it.message.toString())
        }
    }

    //fungsi untuk mengecek status bookmark atau tidak
    private fun checkBookmarkStatus(title: String){
        //jika setelah dicari ternyata data berita terdapat pada room daatabase, maka akan terdeteksi sebagai bookmarked dengan mengganti icon bookmark sebagai icon bookmark berwarna biru
        viewModel.getBookmark(title, {
            binding.btnBookmark.setImageDrawable(ContextCompat.getDrawable(this@DetailBeritaActivity, R.drawable.ic_bookmark_active))
        }) {
            //jika ternyata di room database tidak ada berita yang dicari maka akan terdeteksi sebagai tidak terbookmark maka icon bookmark akan diganti dengan icon bookmark berwarna hitam
            binding.btnBookmark.setImageDrawable(ContextCompat.getDrawable(this@DetailBeritaActivity, R.drawable.ic_bookmark_inactive))
        }
    }
}