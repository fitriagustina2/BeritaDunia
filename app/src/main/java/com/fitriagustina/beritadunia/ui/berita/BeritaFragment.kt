package com.fitriagustina.beritadunia.ui.berita

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.fitriagustina.beritadunia.R
import com.fitriagustina.beritadunia.data.network.model.BeritaModel
import com.fitriagustina.beritadunia.data.room.entity.BeritaEntity
import com.fitriagustina.beritadunia.databinding.FragmentBeritaBinding
import com.fitriagustina.beritadunia.ui.detail_berita.DetailBeritaActivity
import org.koin.android.ext.android.inject

class BeritaFragment : Fragment() {

    private lateinit var binding: FragmentBeritaBinding

    private val viewModel: BeritaViewModel by inject() //inisialisasi viewmodel ke fragment
    private val beritaAdapter: BeritaAdapter by lazy { BeritaAdapter() } //inisialisasi adapter untuk recyclerview

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBeritaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setup untuk recyclerview dengan orientasi vertical
        binding.rvBerita.layoutManager = LinearLayoutManager(requireContext())
        //memasang adapter ke dalam recyclerview ui
        binding.rvBerita.adapter = beritaAdapter

        //memasang listener untuk mengecek saat item didalam recyclerview diklik
        beritaAdapter.setOnItemClickListener(object : BeritaAdapter.OnItemClickListener{
            override fun onClick(data: BeritaEntity) {
                //jika salah satu item diklik maka akan menuju ke halaman DetailBeritaActivity dengan membawa data berita yg diklik tadi
                val intent = Intent(requireContext(), DetailBeritaActivity::class.java)
                intent.putExtra("data_berita", data)
                startActivity(intent)
            }

        })

        //memanggil fungsi getBerita untuk request data berita
        getBerita()
    }

    //fungsi untuk request data berita ke rest api
    private fun getBerita(){
        //memanggil fungsi getBerita di viewModel untuk request data berita ke rest api
        viewModel.getBerita()

        //observe livedata beritaResponse dari viewModel jika sudah mendapatkan data dari rest api
        viewModel.beritaResponse.observe(viewLifecycleOwner) {
            val respon = it.body()!!

            //data response berita dari rest api ditambahkan ke adapter untuk ditampilkan ke recyclerview
            beritaAdapter.addItems(respon.articles)
        }

        //observe livedata errorMessage dari viewModel jika terdapat error maka akan menampilkan toast error
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        //observe livedata loading dari viewModel untuk set status visible dari progress bar
        viewModel.loading.observe(viewLifecycleOwner){
            binding.progressbar.visibility = if (it) View.VISIBLE else View.GONE
        }
    }
}