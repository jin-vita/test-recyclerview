package com.jinvita.testrecyclerview

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jinvita.testrecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { BasicAdapter() }

    private val dummy1 = listOf(
        BasicObject(name = "철수", description = "철이랑 수"),
        BasicObject(name = "영희", description = "영이랑 희"),
        BasicObject(name = "연수", description = "연이랑 수"),
        BasicObject(name = "강혁", description = "강이랑 혁"),
        BasicObject(name = "용찬", description = "용이랑 찬"),
    )
    private val dummy2 = listOf(
        BasicObject(name = "철이랑 수", description = "철이랑 수철이랑 수"),
        BasicObject(name = "영이랑 희", description = "영이랑 희영이랑 희"),
        BasicObject(name = "연이랑 수", description = "연이랑 수연이랑 수"),
        BasicObject(name = "강이랑 혁", description = "강이랑 혁강이랑 혁"),
        BasicObject(name = "용이랑 찬", description = "용이랑 찬용이랑 찬"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initButton()
        initAdapter()
    }

    private fun initButton() = with(binding) {
        btnAdd.setOnClickListener { adapter.add(BasicObject(name = "추가", description = "추랑 가")) }
        btnSet1.setOnClickListener { adapter.update(dummy1) }
        btnSet2.setOnClickListener { adapter.update(dummy2) }
        btnClear.setOnClickListener { adapter.clear() }
    }

    private fun initAdapter() {
        binding.recyclerview.layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerview.adapter = adapter
        adapter.listener = object : BasicAdapter.Listener {
            override fun onItemClick(holder: BasicAdapter.ViewHolder, view: View, position: Int) {
                if (position == -1) return
                val item = adapter.getItem(position)
                Toast.makeText(
                    applicationContext,
                    "${position + 1}번째 아이템 선택 : ${item.name}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}