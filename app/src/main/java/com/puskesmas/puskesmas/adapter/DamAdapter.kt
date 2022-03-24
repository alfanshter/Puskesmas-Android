package com.puskesmas.puskesmas.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.puskesmas.puskesmas.R
import com.puskesmas.puskesmas.model.TpmModel

class DamAdapter(
    private val notesList: MutableList<TpmModel>,
    private val context: Context,

    ) : RecyclerView.Adapter<DamAdapter.ViewHolder>() {

    //database
    private var dialog: Dialog? = null


    interface Dialog {
        fun onClick(position: Int, note : TpmModel)
        fun onEdit(position: Int, id : Int,note : TpmModel)
        fun onHapus(position: Int, id : Int)
    }

    fun setDialog(dialog: Dialog) {
        this.dialog = dialog
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var tpm: TextView
        internal var desa: TextView
        internal var nama: TextView
        internal var edit: MaterialButton
        internal var hapus: MaterialButton


        init {
            tpm= view.findViewById(R.id.txttpm)
            desa = view.findViewById(R.id.txtdesa)
            nama = view.findViewById(R.id.txtnama)
            edit = view.findViewById(R.id.btnedit)
            hapus = view.findViewById(R.id.btnhapus)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listtpm, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val note = notesList[position]
        holder.tpm.text = note.dam.toString()
        holder.desa.text = note.desa.toString()
        holder.nama.text = note.pemilik.toString()
        holder.itemView.setOnClickListener {
            if(dialog != null){
                dialog!!.onClick(holder.layoutPosition,note)
            }
        }

        holder.edit.setOnClickListener {
            if(dialog != null){
                dialog!!.onEdit(holder.layoutPosition,note.id!!,note)
            }
        }
        holder.hapus.setOnClickListener {
            if(dialog != null){
                dialog!!.onHapus(holder.layoutPosition,note.id!!)
            }
        }
    }

}
