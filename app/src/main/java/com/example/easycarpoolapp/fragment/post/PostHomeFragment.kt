package com.example.easycarpoolapp.fragment.post

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentHomeBinding
import com.example.easycarpoolapp.databinding.FragmentPostHomeBinding


class PostHomeFragment : Fragment() {

    companion object{
        public fun getInstance() : PostHomeFragment{
            return PostHomeFragment()
        }
    }

    private lateinit var binding : FragmentPostHomeBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_home, container, false)


        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = PostAdapter(createTestItems())


        return binding.root
    }

    private fun createTestItems(): ArrayList<String> {
        val arr = ArrayList<String>()
        for(i in 0..20){
            arr.add(i.toString())
        }

        return arr
    }



    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val textView : TextView = itemView.findViewById(R.id.item_text)


        public fun bind(item : String){
            textView.text = item
        }
    }


    inner class PostAdapter(val items : ArrayList<String>) : RecyclerView.Adapter<PostViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
            val view = layoutInflater.inflate(R.layout.post_item_layout, parent, false)
            return PostViewHolder(view)
        }

        override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
            holder.bind(items.get(position))
        }

        override fun getItemCount(): Int {
            return items.size
        }

    }
}