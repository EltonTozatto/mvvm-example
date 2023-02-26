package br.com.eltontozatto.mvvmexemplo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.eltontozatto.mvvmexemplo.adapters.RecyclerAdapter
import br.com.eltontozatto.mvvmexemplo.models.Blog
import br.com.eltontozatto.mvvmexemplo.viewModels.MainViewModel
import br.com.eltontozatto.mvvmexemplo.viewModels.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private var viewManager = LinearLayoutManager(this)
    private lateinit var viewModel: MainViewModel
    private lateinit var mainRecycler: RecyclerView
    private lateinit var addButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainRecycler = findViewById(R.id.recycler)
        val application = requireNotNull(this).application
        val factory = MainViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        adicionarListeners()
        initialiseAdapter()
    }

    private fun initialiseAdapter() {
        mainRecycler.layoutManager = viewManager
        observeData()
    }

    private fun observeData() {
        viewModel.lst.observe(this, Observer {
            mainRecycler.adapter = RecyclerAdapter(viewModel, it, this)
        })
    }

    private fun adicionarListeners() {
        addButton = findViewById<Button>(R.id.button)
        addButton.setOnClickListener {
            addData()
        }
    }

    private fun addData() {
        val txtPlce = findViewById<EditText>(R.id.txtTitle)
        val title = txtPlce.text.toString()
        if (title.isBlank()) {
            Toast.makeText(this, getString(R.string.erro_campo_vazio), Toast.LENGTH_LONG).show()
        } else {
            var blog = Blog(title)
            viewModel.add(blog)
            txtPlce.text.clear()
            mainRecycler.adapter?.notifyDataSetChanged()
        }
    }
}