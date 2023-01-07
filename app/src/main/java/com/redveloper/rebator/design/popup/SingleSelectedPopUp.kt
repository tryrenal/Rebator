package com.redveloper.rebator.design.popup

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.redveloper.rebator.R

class SingleSelectedPopUp : BottomSheetDialogFragment() {

    private lateinit var rvItem: RecyclerView
    private lateinit var btnSave: AppCompatButton
    private lateinit var tvTitle: TextView

    private lateinit var adapter: AdapterSingleSelectedPopUp
    private var items: List<Pair<String, String>> = listOf()
    var listener: Listener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.design_single_selected_layout, container, false)
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        val contentView = View.inflate(context, R.layout.design_single_selected_layout, null)
        dialog.setContentView(contentView)
        (contentView.parent as View).setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.transparent))
    }

    override fun show(manager: FragmentManager, tag: String?) {
        if (!isAdded && !manager.isDestroyed){
            super.show(manager, tag)
        }
    }

    fun safeShow(fragmentManager: FragmentManager, tag: String?){
        if (!isAdded){
            show(fragmentManager, tag)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        rvItem = requireView().findViewById(R.id.rv_item)
        btnSave = requireView().findViewById(R.id.btn_save)
        tvTitle = requireView().findViewById(R.id.tv_title)

        arguments?.let {
            tvTitle.text = it.getString(KEY_TITLE, null)
            items = it.getSerializable(KEY_DATA) as ArrayList<Pair<String, String>>
        }

        adapter = AdapterSingleSelectedPopUp()
        rvItem.layoutManager = LinearLayoutManager(requireActivity())
        adapter.submitList(items)
        rvItem.adapter = adapter
        adapter.selectionChanged = {
            it?.let {
                btnSave.isEnabled = true
            }
        }

        btnSave.setOnClickListener {
            dismiss()
            val finalItem = items.first { adapter.selectionItem.contains(it.first) }
            if (parentFragment is Listener){
                (parentFragment as Listener).itemChoose(finalItem)
            } else if (activity is Listener){
                (activity as Listener).itemChoose(finalItem)
            } else {
                listener?.itemChoose(finalItem)
            }
        }
    }

    interface Listener{
        fun itemChoose(item: Pair<String, String>)
    }

    companion object{
        private const val KEY_DATA = "key data"
        private const val KEY_TITLE = "key title"

        fun create(title: String, list: ArrayList<Pair<String, String>>): SingleSelectedPopUp{
            val fragment = SingleSelectedPopUp()
            val bundle = Bundle()
            bundle.putString(KEY_TITLE, title)
            bundle.putSerializable(KEY_DATA, list)
            fragment.arguments = bundle
            return fragment
        }
    }
}