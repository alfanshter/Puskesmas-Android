package com.puskesmas.puskesmas.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.puskesmas.puskesmas.R
import com.puskesmas.puskesmas.model.UsersModel

class UsersAdapter(
    private val notesList: MutableList<UsersModel>,
    private val context: Context,

    ) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    //database
    private var dialog: Dialog? = null


    interface Dialog {
        fun onClick(position: Int, note : UsersModel)
        fun onEdit(position: Int, id : Int,note : UsersModel)
        fun onHapus(position: Int, id : Int)
    }

    fun setDialog(dialog: Dialog) {
        this.dialog = dialog
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var nama: TextView
        internal var alamat: TextView
        internal var username: TextView
        internal var hapus: MaterialButton


        init {
            nama= view.findViewById(R.id.txtpegawai)
            alamat = view.findViewById(R.id.txtalamat)
            username = view.findViewById(R.id.txtusername)
            hapus = view.findViewById(R.id.btnhapus)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listpegawai, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val note = notesList[position]
        holder.nama.text = note.nama.toString()
        holder.alamat.text = note.alamat.toString()
        holder.username.text = "username : ${note.username.toString()}"
        holder.itemView.setOnClickListener {
            if(dialog != null){
                dialog!!.onClick(holder.layoutPosition,note)
            }
        }

        holder.hapus.setOnClickListener {
            if(dialog != null){
                dialog!!.onHapus(holder.layoutPosition,note.id!!)
            }
        }
    }

}
