package com.example.easycarpoolapp.fragment.chat

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
import com.example.easycarpoolapp.databinding.FragmentChatHomeBinding

class ChatHomeFragment : Fragment() {

    companion object{
        public fun getInstance() : ChatHomeFragment = ChatHomeFragment()
    }

    private lateinit var binding : FragmentChatHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat_home,container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = ChatAdapter(createTestData())
        return binding.root
    }//onCreateView


    private fun createTestData() : ArrayList<String> {
        val temp = ArrayList<String>()

        for(i in 0..10){
            temp.add(i.toString())
        }
        return temp
    }


    inner class ChatHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val nickname : TextView = itemView.findViewById(R.id.text_nickname)
        val content : TextView = itemView.findViewById(R.id.text_content)

        fun bind(item : String){
            nickname.text = item
            content.text = item
        }

    }// ChatHolder


    inner class ChatAdapter(val items : ArrayList<String>) : RecyclerView.Adapter<ChatHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
            val view = layoutInflater.inflate(R.layout.item_chat_home_layout, parent, false)
            return ChatHolder(view)
        }

        override fun onBindViewHolder(holder: ChatHolder, position: Int) {
            holder.bind(items.get(position))
        }

        override fun getItemCount(): Int {
            return items.size
        }

    }    //ChatAdapter










}